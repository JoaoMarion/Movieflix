package br.com.movieflix.movieflix.service;

import br.com.movieflix.movieflix.entity.User;
import br.com.movieflix.movieflix.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public User save(User user){
        return userRepository.save(user);
    }
}
