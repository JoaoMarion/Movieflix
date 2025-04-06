package br.com.movieflix.movieflix.service;

import br.com.movieflix.movieflix.entity.Category;
import br.com.movieflix.movieflix.entity.Movie;
import br.com.movieflix.movieflix.entity.Streaming;
import br.com.movieflix.movieflix.repository.CategoryRepository;
import br.com.movieflix.movieflix.repository.MovieRepository;
import br.com.movieflix.movieflix.repository.StreamingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MovieService {
    private final MovieRepository movieRepository;
    private final CategoryRepository categoryRepository;
    private final StreamingRepository streamingRepository;

    public Movie save(Movie movie) {
        movie.setCategories(this.findCategories(movie.getCategories()));
        movie.setStreamings(this.findStreaming(movie.getStreamings()));
        return movieRepository.save(movie);
    }

    public List<Movie> findAll() {
        return movieRepository.findAll();
    }

    public Optional<Movie> findById(Long id){
        return movieRepository.findById(id);
    }

    private List<Category> findCategories(List<Category> categories) {
        List<Category> categoriesFound = new ArrayList<>();
        categories.forEach(category ->
                categoryRepository.findById(category.getId())
                        .ifPresent(categoriesFound::add));
        return categoriesFound;
    }

    private List<Streaming> findStreaming(List<Streaming> streamings) {
        List<Streaming> streamingFound = new ArrayList<>();
        streamings.forEach(streaming ->
                streamingRepository.findById(streaming.getId())
                        .ifPresent(streamingFound::add));
        return streamingFound;
    }

    public void deleteById(Long id){
        movieRepository.deleteById(id);
    }

    public Optional<Movie> update(Long moveId, Movie updateMovie){
        Optional<Movie> optionalMovie = movieRepository.findById(moveId);
    if(optionalMovie.isPresent()){
        List<Category> categories = this.findCategories(updateMovie.getCategories());
        List<Streaming> streamings = this.findStreaming(updateMovie.getStreamings());

        Movie movie = optionalMovie.get();
        movie.setTitle(updateMovie.getTitle());
        movie.setDescription(updateMovie.getDescription());
        movie.setReleaseDate(updateMovie.getReleaseDate());
        movie.setRating(updateMovie.getRating());

        movie.getCategories().clear();
        movie.getCategories().addAll(categories);

        movie.getStreamings().clear();
        movie.getStreamings().addAll(streamings);

        movieRepository.save(movie);

        return Optional.of(movie);
    }

    return Optional.empty();

    }

    public List<Movie> findByCategory(Long categoryId){
     return movieRepository.findMovieByCategories(List.of(Category.builder().id(categoryId).build()));
    }

}


