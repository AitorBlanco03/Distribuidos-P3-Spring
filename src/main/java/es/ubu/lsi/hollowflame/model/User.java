package es.ubu.lsi.hollowflame.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.io.Serializable;

/**
 * Modelo que define y representa a los usuarios dentro de nuestro sistema
 * y tienda.
 *
 * @author Aitor Blanco Fernández, abf1005@alu.ubu.es
 * @version 1.0.0, 02/06/2025.
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User implements Serializable {

    // Identificador único del usuario dentro del sistema.
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long userID;

    // Campo que representa el nombre del usuario dentro del sistema.
    @Column(nullable = false)
    private String name;

    // Campo que representa el email/correo del usuario dentro del sistema.
    @Column(nullable = false, unique = true)
    private String email;

    // Campo que reprsenta la contraseña del usuario (cifrada) dentro del sistema.
    @Column(nullable = false)
    private String password;
}