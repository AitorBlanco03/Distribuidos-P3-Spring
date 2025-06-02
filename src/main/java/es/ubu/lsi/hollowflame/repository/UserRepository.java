package es.ubu.lsi.hollowflame.repository;

import java.util.Optional;
import es.ubu.lsi.hollowflame.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repositorio JPA encargado de gestionar y recuperar los datos de los usuarios
 * desde la base de datos.
 *
 * @author Aitor Blanco Fernández, abf1005@alu.ubu.es
 * @version 1.0.0, 02/06/2026
 */
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}