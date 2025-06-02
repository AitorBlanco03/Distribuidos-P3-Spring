package es.ubu.lsi.hollowflame.service;

import es.ubu.lsi.hollowflame.dto.SignUpFormDTO;
import es.ubu.lsi.hollowflame.model.User;
import es.ubu.lsi.hollowflame.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    // Conectamos el repositorio al servicio para obtener los usuarios desde la base de datos.
    @Autowired
    private UserRepository userRepository;

    // Definimos el encargado de encriptar y verificar las contraseñas de los usuarios.
    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Intenta acceder dentro del sistema usando a través del email y la contraseña.
     *
     * @param email Email/Correo ingresado en el formulario.
     * @param password Contraseña ingresada en el formulario.
     * @return El usuario si las creedenciales son válidas, o null en caso contrario.
     */
    public User logIn(String email, String password) {
        // Buscamos si existe un usuario con ese email y contraseña.
        return userRepository.findByEmail(email)
                .filter(u -> passwordEncoder.matches(password, u.getPassword()))
                .orElse(null);
    }

    /**
     * Comprueba si existe algún otro usuario ya registrado con
     * el email proporcionado.
     *
     * @param email Email/Correo que se desea comprobar.
     * @return true si el email recibido ya se encuentra en uso, false en caso
     * contrario.
     */
    public boolean existsEmail(String email) {
        return userRepository.findByEmail(email).isPresent();
    }

    /**
     * Registra un nuevo usuario dentro del sistema y la base de datos.
     *
     * @param form Formulario con los datos ingresados de ese nuevo usuario.
     * @return true si el usuario ha sido registrado correctamente en el sistema, false
     *          en caso contrario.
     */
    public boolean registerUser(SignUpFormDTO form) {
        // Comprobamos que si el email ya se encuentra en uso.
        if (existsEmail(form.getEmail())) return false;

        // Comprobamos si ambas contraseñas coinciden dentro del formulario.
        if (!form.getPassword().equals(form.getConfirmPassword())) return false;

        // Creamos un nuevo usuario dentro del sistema.
        User user = new User();
        user.setName(form.getName());
        user.setEmail(form.getEmail());

        // Encriptamos la contraseña del usuario antes de guardar en la base de datos.
        user.setPassword(passwordEncoder.encode(form.getPassword()));

        // Guardamos el usuario en el sistema y base de datos.
        userRepository.save(user);
        return true;
    }
}