package br.com.movieflix.movieflix.mapper;

import br.com.movieflix.movieflix.controller.request.VerificationCodeRequest;
import br.com.movieflix.movieflix.entity.embedded.VerificationCode;
import lombok.experimental.UtilityClass;

@UtilityClass
public class VerificationCodeMapper {
    public static VerificationCode toVerificationCode(VerificationCodeRequest verificarionCodeRequest){
        return VerificationCode.builder()
                .code(verificarionCodeRequest.code())
                .build();
    }
}
