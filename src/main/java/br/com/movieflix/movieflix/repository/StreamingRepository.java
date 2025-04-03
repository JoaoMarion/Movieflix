package br.com.movieflix.movieflix.repository;

import br.com.movieflix.movieflix.entity.Streaming;
import com.sun.jdi.LongValue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StreamingRepository extends JpaRepository<Streaming, Long> {
}
