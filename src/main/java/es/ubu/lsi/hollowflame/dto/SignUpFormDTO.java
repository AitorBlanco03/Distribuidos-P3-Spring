package es.ubu.lsi.hollowflame.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object (DTO) para recoger y capturar los datos
 * del formulario de registro dentro de la tienda.
 *
 * @author Aitor Blanco Fernández, abf1005@alu.ubu.es
 * @version 1.0.0, 02/06/2026
 */

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class SignUpFormDTO {

    // Campo que representa el nombre del usuario en el formulario.
    private String name;

    // Campo que representa el email/correo del usuario en el formulario.
    private String email;

    // Campo que representa la contraseña del usuario en el formulario.
    private String password;

    // Campo para confirmar la contraseña del usuario en el formulario.
    private String confirmPassword;
}