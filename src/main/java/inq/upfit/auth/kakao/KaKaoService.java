package inq.upfit.auth.kakao;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import inq.upfit.auth.jwt.JwtProvider;
import inq.upfit.domain.*;
import inq.upfit.dto.*;
import inq.upfit.repository.CompanyRepository;
import inq.upfit.repository.UserCompanyRepository;
import inq.upfit.repository.UserInfoRepository;
import inq.upfit.repository.UserRepository;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.TransactionScoped;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Tag(name = "KakaoService",description = "카카오 소셜 로그인")
public class KaKaoService {

    private final UserRepository userRepository;
    private final UserCompanyRepository userCompanyRepository;
    private final CompanyRepository companyRepository;
    private final UserInfoRepository userInfoRepository;
    private final JwtProvider jwtProvider;
    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();


    @Value("${kakao.client-id}")
    private String kakaoClientId;

    @Value("${kakao.redirect-uri}")
    private String kakaoRedirectUri;

    @Transactional
    public Object handleKakaoLogin(String code) {
        String accessToken = getAccessTokenFromKakao(code);
        String kakaoId = getKakaoId(accessToken);

        Optional<User> userOpt = userRepository.findByKakaoId(kakaoId);


        if (userOpt.isEmpty()) {
            return new KakaoLoginResponse(false, kakaoId, null, null);
        }

        User user = userOpt.get();
        List<UserCompany> userCompanies = userCompanyRepository.findAllByUser(user);

        if (userCompanies.size() == 1) {

            UserCompany userCompany = userCompanies.get(0);
            TokenDto token = generateToken(user);
            user.setRefreshToken(token.getRefreshToken());
            userRepository.save(user);

            return new KakaoLoginResponse(true, kakaoId, token, userCompany.getCompany());

        } else {

            // 여러 계정 → 계정 리스트 응답
            List<KakaoLoginResponse> accounts = userCompanies.stream()
                    .map(uc -> {
                        TokenDto token = generateToken(user);
                        user.setRefreshToken(token.getRefreshToken());
                        userRepository.save(user);
                        return new KakaoLoginResponse(true, user.getKakaoId(), token, uc.getCompany());
                    })
                    .toList();

            return new KakaoLoginListResponse(accounts.size(), accounts);

        }
    }

    @Transactional
    public TokenDto signupOwner(OwnerSignupRequest request) {

        // 1. 카카오 ID로 유저 조회 (없으면 새로 생성)
        User user = userRepository.findByKakaoId(request.getKakaoId())
                .orElseGet(() -> {
                    User newUser = User.builder()
                            .kakaoId(request.getKakaoId())
                            .systemRole(SystemRole.NORMAL_USER)
                            .registerAt(LocalDateTime.now())

                            .build();
                    return userRepository.save(newUser);
                });

        // 2. 고유 회사 코드 생성 (중복 방지)
        // 2. 회원가입 요청에 포함된 companyCode 중복 체크
        if (companyRepository.existsByUniqueCode(request.getCompanyCode())) {
            throw new IllegalArgumentException("이미 존재하는 회사 코드입니다. 다시 시도해주세요.");
        }

        // 3. 회사 생성 및 저장
        Company company = Company.builder()
                .name(request.getCompany())
                .uniqueCode(request.getCompanyCode())
                .registeredAt(LocalDateTime.now())
                .build();
        companyRepository.save(company);

        // 4. UserInfo 생성
        UserInfo userInfo = UserInfo.builder()
                .name(request.getName())
                .email(request.getEmail())
                .telNo(request.getTel_no())
                .joinDate(LocalDateTime.now())
                .user(user)
                .build();
        userInfoRepository.save(userInfo);

        // 5. UserCompany 관계 생성 (대표로 설정)
        UserCompany userCompany = UserCompany.builder()
                .user(user)
                .company(company)
                .role(Role.COMPANY_OWNER)
                .isActive(true)
                .startDate(LocalDate.now())
                .build();
        userCompanyRepository.save(userCompany);

        // 6. 토큰 발급
        TokenDto token = generateToken(user);
        user.setRefreshToken(token.getRefreshToken());
        userRepository.save(user);

        return token;
    }


    @Transactional
    public TokenDto signupMember(MemberSignupRequest request) {

        // 1. 카카오 ID로 유저 조회 (없으면 새로 생성)
        User user = userRepository.findByKakaoId(request.getKakaoId())
                .orElseGet(() -> {
                    User newUser = User.builder()
                            .kakaoId(request.getKakaoId())
                            .systemRole(SystemRole.NORMAL_USER)
                            .registerAt(LocalDateTime.now())
                            .build();
                    return userRepository.save(newUser);
                });

        // 2. 회사명과 코드가 모두 일치하는 회사가 있는지 확인
        Company company = companyRepository.findByNameAndUniqueCode(request.getCompany(), request.getCompanyCode())
                .orElseThrow(() -> new IllegalArgumentException("회사명과 회사코드가 일치하지 않습니다."));

        // 3. 이미 해당 회사에 소속되어 있는지 확인
        boolean alreadyExists = userCompanyRepository.existsByUserAndCompany(user, company);
        if (alreadyExists) {
            throw new IllegalStateException("이미 해당 회사에 소속되어 있는 계정입니다.");
        }

        UserInfo userInfo = UserInfo.builder()
                .name(request.getName())
                .email(request.getEmail())
                .telNo(request.getTel_no())
                .joinDate(LocalDateTime.now())
                .user(user)  // User와 연관관계 설정
                .build();

        userInfoRepository.save(userInfo);

        // 4. 소속 관계 생성
        UserCompany userCompany = UserCompany.builder()
                .user(user)
                .company(company)
                .isActive(true)
                .role(Role.MEMBER)
                .startDate(LocalDate.now())
                .build();

        userCompanyRepository.save(userCompany);

        // 5. 토큰 발급
        TokenDto token = generateToken(user);
        user.setRefreshToken(token.getRefreshToken());
        userRepository.save(user);

        return token;
    }
    //인가코드를 받아서 카카오 액세스 토큰 리턴
    @Hidden
    private String getAccessTokenFromKakao(String code) {
        String url = "https://kauth.kakao.com/oauth/token";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", kakaoClientId);
        params.add("redirect_uri", kakaoRedirectUri);
        params.add("code", code);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, request, String.class);

        try {
            JsonNode json = objectMapper.readTree(response.getBody());
            return json.get("access_token").asText();
        } catch (Exception e) {
            throw new IllegalStateException("카카오 토큰 파싱 실패", e);
        }
        }


    //액세스 토큰을 바탕으로 소셜 로그인 고유 ID를 리턴함

    @Hidden
    private String getKakaoId(String accessToken) {
        String url = "https://kapi.kakao.com/v2/user/me";

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);
        HttpEntity<Void> request = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, request, String.class);


        System.out.println("사용자 정보 요청 토큰: "+ accessToken);
        System.out.println("카카오 사용자 정보 응답 바디: "+ response.getBody());

        try {
            JsonNode json = objectMapper.readTree(response.getBody());
            return json.get("id").asText();
        } catch (Exception e) {
            throw new IllegalStateException("카카오 사용자 정보 파싱 실패", e);
        }
    }

    // JWT 토큰을 만들기 위해 받아온 정보를 기반으로 authentication을 만들어서 매개변수로 주입

    private TokenDto generateToken(User user){
        return jwtProvider.generateTokenDto(
                new UsernamePasswordAuthenticationToken(
                        user.getKakaoId(),
                        null,
                        Collections.singleton(new SimpleGrantedAuthority(user.getSystemRole().name()))
                )
        );
    }

}
