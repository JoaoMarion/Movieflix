package br.com.movieflix.movieflix.service;

import br.com.movieflix.movieflix.controller.response.UserResponse;
import br.com.movieflix.movieflix.entity.User;
import br.com.movieflix.movieflix.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User save(User user){
            String password = user.getPassword();
            user.setPassword(passwordEncoder.encode(password));
            return userRepository.save(user);
    }

    public Optional<UserDetails> findByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }

    public Optional<User> findById(Long id){
        return userRepository.findById(id);
    }


    public List<User> findAll(){
      return userRepository.findAll();
    }

    public void delete(Long id){
        userRepository.deleteById(id);
    }


}
