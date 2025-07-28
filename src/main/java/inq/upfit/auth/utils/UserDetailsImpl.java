package inq.upfit.auth.utils;

import inq.upfit.domain.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

public class UserDetailsImpl implements UserDetails {

    private final User user;

    public UserDetailsImpl(User user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(() -> user.getRole().name());
    }

    @Override
    public String getPassword(){
        return null;
    }
    @Override
    public String getUsername(){
        return user.getKakaoId();
    }
}
