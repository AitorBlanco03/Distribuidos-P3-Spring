package es.ubu.lsi.hollowflame.controller;

import es.ubu.lsi.hollowflame.dto.SignUpFormDTO;
import es.ubu.lsi.hollowflame.dto.LoginFormDTO;
import es.ubu.lsi.hollowflame.dto.ShoppingCartDTO;
import es.ubu.lsi.hollowflame.model.User;
import es.ubu.lsi.hollowflame.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.*;

/**
 * Controlador encargado de gestionar y controlar el inicio de sesión y
 * registro de usuarios dentro del sistema y la base de datos.
 *
 * @author Aitor Blanco Fernández, abf1005@alu.ubu.es
 * @version 1.1.0, 02/06/2025
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
        signInPage.addAttribute("loginForm", new LoginFormDTO());
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
        signUpPage.addAttribute("signUpForm", new SignUpFormDTO());
        return "signup";
    }


    /**
     * Controla y gestiona el inicio de sesión en la tienda.
     *
     * @param loginForm Formulario de inicio de sesión.
     * @return Nombre de la vista que se debe actualizar/renderizar por pantalla.
     */
    @PostMapping("/login")
    public String loginUser(@ModelAttribute("loginForm") LoginFormDTO loginForm,
                            Model model, HttpSession session) {
        // Comprobamos si las credenciales ingresadas son válidas y corresponden a un usuario.
        User user = userService.logIn(loginForm.getEmail(), loginForm.getPassword());

        // Si no son válidas, el volvemos a enseñar formulario de inicio de sesión.
        if (user == null) return "login";

        session.setAttribute("user", user);
        session.setAttribute("cart", new ShoppingCartDTO());
        return "redirect:/";
    }


    /**
     * Controla y gestiona el registro de un nuevo usuario en la tienda.
     *
     * @param signUpForm Formulario de registro de nuevos usuarios.
     * @return Nombre de la vista que se debe actualizar/renderizar por pantalla.
     */
    @PostMapping("/register")
    public String registerUser(@ModelAttribute("signUpForm") SignUpFormDTO signUpForm,
                               Model model, HttpSession session) {
        // Comprobamos que las contraseñas coinciden dentro del formulario de registro.
        if (!signUpForm.getPassword().equals(signUpForm.getConfirmPassword())) return "signup";

        // Comprobamos que el email del usuario ingresado no esta en uso dentro del sistema.
        if (userService.existsEmail(signUpForm.getEmail())) return "signup";

        // Registramos al usuario en el sistema y en la base de datos.
        User user = userService.registerUser(signUpForm);
        session.setAttribute("user", user);
        session.setAttribute("cart", new ShoppingCartDTO());
        return "redirect:/";
    }

    /**
     * Controla y gestiona el logout de un usuario dentro de la tienda.
     *
     * @return Nombre de la vista que se debe actualizar/renderizar por pantalla.
     */
    @GetMapping("/logout")
    public String logOut(HttpSession session) {
        // Eliminamos los datos de la sesión actual antes del logout.
        session.invalidate();

        // Redirigimos al usuario después del logout.
        return "redirect:/";
    }
}