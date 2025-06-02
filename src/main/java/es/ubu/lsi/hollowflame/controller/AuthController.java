package es.ubu.lsi.hollowflame.controller;

import es.ubu.lsi.hollowflame.dto.SignUpFormDTO;
import es.ubu.lsi.hollowflame.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * Controlador encargado de gestionar y controlar el inicio de sesión y
 * registro de usuarios dentro del sistema y la base de datos.
 *
 * @author Aitor Blanco Fernández, abf1005@alu.ubu.es
 * @version 1.0.0, 02/06/2025
 */

@Controller
public class AuthController {

    // Conectamos al controlador, el servicio encargado de gestionar los usuarios.
    @Autowired
    private UserService userService;

    /**
     * Muestra por pantalla el formulario de inicio de sesión de la tienda.
     *
     * @param signInPage Pagina con el formulario de inicio de sesión.
     * @return Nombre de la vista que se debe actualizar/renderizar por pantalla.
     */
    @GetMapping("/login")
    public String showSignInForm(Model signInPage) {
        return "login";
    }

    /**
     * Muestra por pantalla el formulario de registro de la tienda.
     *
     * @param signUpPage Pagina con el formulario de registro.
     * @return Nombre de la vista que se debe actualizar/renderizar por pantalla.
     */
    @GetMapping("/register")
    public String showSignUpForm(Model signUpPage) {
        return "signup";
    }
}