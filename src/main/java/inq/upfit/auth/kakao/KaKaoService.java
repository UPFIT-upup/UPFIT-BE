package inq.upfit.auth.kakao;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import inq.upfit.auth.jwt.JwtProvider;
import inq.upfit.domain.master.Company;
import inq.upfit.domain.master.User;
import inq.upfit.dto.*;
import inq.upfit.repository.CompanyRepository;
import inq.upfit.repository.UserRepository;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.tags.Tag;
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

import java.time.LocalDateTime;
import java.util.Collections;

@Service
@RequiredArgsConstructor
@Tag(name = "KakaoService",description = "카카오 소셜 로그인")
public class KaKaoService {

    private final UserRepository userRepository;
    private final CompanyRepository companyRepository;
    private final JwtProvider jwtProvider;
    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();


    @Value("${kakao.client-id}")
    private String kakaoClientId;

    @Value("${kakao.redirect-uri}")
    private String kakaoRedirectUri;


    //카카오 이메일이랑 이름 리턴
    @Transactional
    public Object handleKakaoLogin(String code) {
        String accessToken = getAccessTokenFromKakao(code);
        String kakaoEmail = getKakaoEmail(accessToken);


        if(kakaoEmail ==null){
            return new KakaoLoginResponse(false, null, null, null);
        }

        return userRepository.findByKakaoEmail(kakaoEmail)
                .map(user -> {
                    TokenDto token = generateToken(user);
                    user.setRefreshToken(token.getRefreshToken());
                    return new KakaoLoginResponse(true, kakaoEmail, token,user.getRole());
                })
                .orElseGet(() -> new KakaoLoginResponse(false, kakaoEmail, null, null));

    }

    //dto에는
    @Transactional
    public TokenDto signup(SignupRequest request) {

        // 1. 카카오 Email로 유저 조회 (없으면 새로 생성)
        User user = userRepository.findByKakaoEmail(request.getKakaoEmail())
                .orElseGet(() -> {
                    User newUser = User.builder()
                            .kakaoEmail(request.getKakaoEmail())
                            .name(request.getName())
                            .role(request.getRole())

                            .build();
                    return userRepository.save(newUser);
                });


        /* 3. 회사 생성 및 저장 - 킵
        Company company = Company.builder()
                .name(request.getCompany())
                .registeredAt(LocalDateTime.now())
                .build();
        companyRepository.save(company);
        */



        // 6. 토큰 발급
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

    public String getKakaoEmail(String token) {
        String url = "https://kapi.kakao.com/v2/user/me";
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        HttpEntity<Void> request = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, request, String.class);

        System.out.println("사용자 정보 요청 토큰: " + token);
        System.out.println("카카오 사용자 정보 응답 바디: " + response.getBody());

        if (response.getBody() == null) {
            System.err.println("카카오 응답 바디가 null임. 이메일 조회 실패");
            return null; // 여기서 null 반환
        }

        try {
            JsonNode json = objectMapper.readTree(response.getBody());

            // 1순위: 이메일
            JsonNode account = json.get("kakao_account");
            if (account != null && account.has("email")) {
                return account.get("email").asText();
            }

            // 2순위: 닉네임
            if (account != null && account.has("profile") && account.get("profile").has("nickname")) {
                return account.get("profile").get("nickname").asText() + "@kakao-temp.local";
            }

            // 3순위: ID라도 넘기기
            if (json.has("id")) {
                return "kakao_" + json.get("id").asText() + "@kakao-temp.local";
            }

            return null;
        } catch (Exception e) {
            System.err.println("카카오 사용자 정보 파싱 실패: " + e.getMessage());
            return null;
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
                        user.getKakaoEmail(),
                        null,
                        Collections.singleton(new SimpleGrantedAuthority(user.getRole().name()))
                )
        );
    }

}
