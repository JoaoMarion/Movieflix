package br.com.movieflix.movieflix.service;

import com.cloudinary.Cloudinary;
import io.github.cdimascio.dotenv.Dotenv;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CloudinaryService {
    private final Cloudinary cloudinary;


    public Map uploadImage(File file) {
        try {
            return cloudinary.uploader().upload(file, Map.of());
        } catch (Exception e) {
            throw new RuntimeException("Falha no upload", e);
        }
    }


}
