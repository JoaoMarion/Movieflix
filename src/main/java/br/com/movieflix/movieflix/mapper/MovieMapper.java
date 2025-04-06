package br.com.movieflix.movieflix.mapper;

import br.com.movieflix.movieflix.controller.request.MovieRequest;
import br.com.movieflix.movieflix.controller.response.CategoryResponse;
import br.com.movieflix.movieflix.controller.response.MovieResponse;
import br.com.movieflix.movieflix.controller.response.StreamingResponse;
import br.com.movieflix.movieflix.entity.Category;
import br.com.movieflix.movieflix.entity.Movie;
import br.com.movieflix.movieflix.entity.Streaming;
import lombok.experimental.UtilityClass;

import java.util.List;

@UtilityClass
public class MovieMapper {
    public static Movie toMovie(MovieRequest request) {

        //pega uma lista de categorias e transforma em uma lista de categorias com somente id
        List<Category> categories = request.categories().stream()
                .map(categoryId -> Category.builder().id(categoryId).build())
                .toList();

        List<Streaming> streamings = request.streamings().stream()
                .map(streamingId -> Streaming.builder().id(streamingId).build())
                .toList();

        return Movie.builder()
                .title(request.title())
                .description(request.description())
                .releaseDate(request.releaseDate())
                .rating(request.rating())
                .categories(categories)
                .streamings(streamings)
                .build();
    }

    public static MovieResponse toMovieResponse(Movie movie) {
        List<CategoryResponse> categories = movie.getCategories().stream()
                .map(CategoryMapper::toCategoryResponse)
                .toList();

        List<StreamingResponse> streamings = movie.getStreamings().stream()
                .map(StreamingMapper::toStreamingResponse)
                .toList();


        return MovieResponse.builder()
                .id(movie.getId())
                .title(movie.getTitle())
                .description(movie.getDescription())
                .releaseDate(movie.getReleaseDate())
                .rating(movie.getRating())
                .categories(categories)
                .streamings(streamings)
                .build();
    }


}
