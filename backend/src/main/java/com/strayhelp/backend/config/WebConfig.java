package com.strayhelp.backend.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

// CORS (Cross-Origin Resource Sharing) is a browser security rule: by default
// a page served from one origin (scheme+host+port) is blocked from calling an
// API on a different origin. Our frontend (e.g. http://localhost:3000) and
// this backend (http://localhost:8080) are different origins, so without this
// config the browser would reject the frontend's requests before they even
// reach a controller.
//
// For "unsafe" requests (anything but simple GET/POST with simple headers —
// e.g. our PUT/DELETE, or a POST with a JSON body) the browser first sends a
// "preflight": an automatic OPTIONS request asking "is the real request
// allowed?". Only if the server answers yes does the browser send the actual
// GET/POST/PUT/DELETE. This config is what makes the server answer yes.
//
// Origins are listed explicitly (not "*") so that only our known local
// frontends can call this API — allowing any origin would let any website
// a user happens to visit make requests against it.
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")
                .allowedOrigins(
                        "http://localhost:3000",
                        "http://localhost:5500",
                        "http://127.0.0.1:5500")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(false);
    }

    // NOTE: this WebMvcConfigurer-based CORS config only applies while there
    // is no security filter chain in front of the app. Once Phase 4 adds
    // Spring Security, its filter chain runs before this config and will
    // block the preflight/cross-origin requests on its own unless CORS is
    // also configured there (e.g. via HttpSecurity.cors(...) backed by a
    // CorsConfigurationSource) — this class alone won't be enough anymore.
}
