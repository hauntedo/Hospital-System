package ru.itis.hauntedo.simbirtest.security.userdetails;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Service;
import ru.itis.hauntedo.simbirtest.dto.response.UserResponse;
import ru.itis.hauntedo.simbirtest.utils.enums.State;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class TokenAuthenticationUserDetailsService implements AuthenticationUserDetailsService<PreAuthenticatedAuthenticationToken> {


    @Override
    public UserDetails loadUserDetails(PreAuthenticatedAuthenticationToken preAuthenticatedAuthenticationToken) throws UsernameNotFoundException {
        return loadUserDetails(
                (UserResponse) preAuthenticatedAuthenticationToken.getPrincipal(),
                preAuthenticatedAuthenticationToken.getCredentials().toString());
    }

    private UserDetails loadUserDetails(UserResponse userResponse, String token) {
        return AccountUserDetails.builder()
                .id(userResponse.getId())
                .createDate(null)
                .authorities(Collections.singleton(new SimpleGrantedAuthority("ROLE_" + userResponse.getRole())))
                .username(userResponse.getEmail())
                .isAccountNonLocked(!userResponse.getState().equals(State.BANNED.name()))
                .isCredentialsNonExpired(true)
                .isAccountNonExpired(true)
                .token(token)
                .isEnabled(userResponse.getState().equals(State.CONFIRMED.name()))
                .build();
    }


}
