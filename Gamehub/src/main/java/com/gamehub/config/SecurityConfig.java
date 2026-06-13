package com.gamehub.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http)
            throws Exception {

        http

            .csrf(csrf -> csrf.disable())

            .authorizeHttpRequests(auth -> auth

                // públicas
            		.requestMatchers(
            		        "/", 
            		        "/login",
            		        "/registro",
            		        "/api/auth/**",
            		        
            		      

            		        // páginas públicas 
            		        "/juego/**",

            		        // recursos
            		        "/css/**",
            		        "/js/**",
            		        "/images/**",

            		        // catálogo público
            		        "/api/juegos/activos",
            		        "/api/juegos/buscar/**",
            		        

            		        // detalle juego público
            		        "/api/juegos/*",

            		     // CALIFICACIONES
            		     "/api/calificaciones/promedio/**",

            		     // COMENTARIOS
            		     "/api/comentarios/**",
            		        
            		        // SWAGGER / OPENAPI
            		        "/swagger-ui/**",
            		        "/swagger-ui.html",
            		        "/v3/api-docs/**",
            		        
            		      //Noticias
            		        
            		        "/api/noticias/activas"
            		        
            		        
            		).permitAll()

                // solo admin
                .requestMatchers("/usuarios")
                .hasRole("ADMIN")

                .requestMatchers("/juegos")
                .hasAnyRole(
                        "ADMIN",
                        "DESARROLLADOR"
                )
                
                .requestMatchers(
                	    "/estadisticas",
                	    "/api/estadisticas"
                	)
                	.hasAnyRole(
                	    "ADMIN",
                	    "DESARROLLADOR",
                	    "MODERADOR",
                	    "SOPORTE",
                	    "EDITOR"
                	)
                
                .requestMatchers("/soporte")
                .hasAnyRole(
                		"SOPORTE", 
                		"ADMIN"
                		)
                

                .requestMatchers("/tickets")
                .authenticated()
                // cualquier usuario autenticado
                .requestMatchers("/dashboard")
                .authenticated()
                
                .requestMatchers(
                        "/gestion-noticias"
                )
                .hasAnyRole(
                        "EDITOR",
                        "ADMIN"
                )
                
             // reportes
                .requestMatchers("/api/reportes/**")
                .authenticated()

                .anyRequest()
                .authenticated()
                
            )
            

            // LOGIN
            .formLogin(login -> login

                .loginPage("/login")

                .loginProcessingUrl("/login")

                .defaultSuccessUrl("/dashboard", true)

                .failureUrl("/login?error")

                .permitAll()
            )

            // LOGOUT
            .logout(logout -> logout

            	    .logoutUrl("/logout")

            	    .logoutSuccessUrl("/")

            	    .permitAll()
            	);

        return http.build();
    }

    // BCrypt
    @Bean
    public PasswordEncoder passwordEncoder() {

        return new BCryptPasswordEncoder();
    }
}