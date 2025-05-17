package br.com.movieflix.movieflix.config;

public class RoutePaths {

    public static final String[] ADMIN_DELETE_ROUTES = {
            "/movieflix/user/**",
            "/movieflix/category/**",
            "/movieflix/movie/**",
            "/movieflix/streaming/**",
    };

    public static final String[] ADMIN_GET_ROUTES = {
            "/actuator/**",
            "/movieflix/user"
    };

    public static final String[] ADMIN_PUT_ROUTES = {

    };

    public static final String[] ADMIN_POST_ROUTES = {

    };

    public static final String[] PUBLIC_POST_ROUTES = {
            "/movieflix/auth/login",
            "/movieflix/auth/register",
            "/movieflix/auth/refresh",
            "/movieflix/auth/verify"
    };

    public static final String[] PUBLIC_GET_ROUTES = {
            "/movieflix/auth/verifyToken"
    };
}
