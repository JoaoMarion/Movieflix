package br.com.movieflix.movieflix.service;

import br.com.movieflix.movieflix.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class IdentifierGeneratorService {
   private final UserRepository userRepository;

    public UUID generateUniqueIdentifier() {
        UUID identifier;
        do {
            identifier = UUID.randomUUID();
        } while (userRepository.existsByIdentifier(identifier));
        return identifier;
    }

}
