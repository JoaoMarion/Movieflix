package br.com.movieflix.movieflix.config;

public class RoutePaths {

    public static final String[] ADMIN_DELETE_ROUTES = {
            "/movieflix/user/**",
            "/movieflix/category/**",
            "/movieflix/movie/**",
            "/movieflix/streaming/**",
    };

    public static final String[] USER_ROUTES = {
            "/movieflix/profile/**",
            "/movieflix/movies/**"
    };

    public static final String[] PUBLIC_ROUTES = {
            "/auth/login",
            "/auth/register",
    };
}
