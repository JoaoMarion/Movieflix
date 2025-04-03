import br.com.movieflix.movieflix.MovieflixApplication;
import org.springframework.boot.SpringApplication;

public class Main {
    public static void main(String[] args) {
        System.out.println("DATABASE_URL: " + System.getenv("DATABASE_URL"));
        System.out.println("DATABASE_USERNAME: " + System.getenv("DATABASE_USERNAME"));
        System.out.println("DATABASE_PASSWORD: " + System.getenv("DATABASE_PASSWORD"));

        SpringApplication.run(MovieflixApplication.class, args);
    }
}
