package br.com.movieflix.movieflix.service;

import br.com.movieflix.movieflix.exception.UserNotConfirmedException;
import br.com.movieflix.movieflix.exception.UserNotFound;
import br.com.movieflix.movieflix.exception.UsernameOrPasswordInvalidException;
import br.com.movieflix.movieflix.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AuthService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetails user = userRepository.findUserByEmail(username)
                .orElseThrow(() -> new UsernameOrPasswordInvalidException("Usuário ou senha inválidos"));

        if (!user.isEnabled()) {
            throw new UserNotConfirmedException("Usuário não confirmado.");
        }

        return user;
    }
}
