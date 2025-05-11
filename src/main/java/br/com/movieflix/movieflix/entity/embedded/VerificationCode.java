package br.com.movieflix.movieflix.entity.embedded;

import jakarta.persistence.Embeddable;
import lombok.*;

import java.time.LocalDateTime;

@Embeddable
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VerificationCode {
    private int code;

    private LocalDateTime expireAt;
}
