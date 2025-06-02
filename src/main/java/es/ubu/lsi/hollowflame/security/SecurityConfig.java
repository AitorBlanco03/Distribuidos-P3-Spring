package es.ubu.lsi.hollowflame.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Clase que define la configuración y los elementos necesarios para garantizar
 * la seguridad de nuestra aplicación y web.
 *
 * @author Aitor Blanco Fernández, abf1005@alu.ubu.es
 * @version 1.0.0, 02/06/2025
 */

@Configuration
public class SecurityConfig {

    // Definimos el elemento encargado de codificar y comprobar las contraseñas de los usuarios.
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Definimos las rutas que son de dominio públicas y las que son necesarias iniciar sesión o registro.
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                // Definimos las rutas públicas y privadas dentro de la aplicación.
                .authorizeHttpRequests(autorization -> autorization
                        .requestMatchers("/shopping-cart").authenticated()
                        .anyRequest().permitAll()
                )

                // Definimos la que será la página de login dentro de la web.
                .formLogin(form -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/", true)
                        .permitAll()
                )

                // Definimos el proceso del logout, el cual le manda a la raiz tras cerrar sesión.
                .logout(logout -> logout
                        .logoutSuccessUrl("/")
                        .permitAll()
                );

        return http.build();
    }
}