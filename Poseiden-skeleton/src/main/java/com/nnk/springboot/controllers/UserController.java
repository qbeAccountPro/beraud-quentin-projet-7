package com.nnk.springboot.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.UserRepository;

/**
 * Some javadoc :
 * 
 * Controller class for managing User entities.
 * Handles CRUD operations for User objects, including listing, adding,
 * updating, and deleting users.
 */
@Controller
public class UserController {
    @Autowired
    UserRepository userRepository;

    /**
     * Some javadoc :
     * 
     * This method displays the list of all users.
     *
     * @param model the model to which attributes are added for rendering in the
     *              view.
     * @return String representing the view name for displaying the user list.
     */
    @RequestMapping("/user/list")
    public String home(Model model) {
        model.addAttribute("authentication", SecurityContextHolder.getContext().getAuthentication());
        model.addAttribute("users", userRepository.findAll());
        return "user/list";
    }

    /**
     * Some javadoc :
     * This method displays the form for adding a new user.
     *
     * @param user the User object to be added.
     * @return String representing the view name for displaying the add user form.
     */
    @GetMapping("/user/add")
    public String addUser(User user) {
        return "user/add";
    }

    /**
     * Some javadoc :
     * 
     * This method validates user data, encodes the password, saves it to the
     * database, and redirects
     * to the user list.
     *
     * @param user   the User object to be validated and saved.
     * @param result the binding result for validation errors.
     * @param model  the model to which attributes are added for rendering in the
     *               view.
     * @return String representing the view name for redirecting to the user
     *         list or displaying validation errors.
     */
    @PostMapping("/user/validate")
    public String validate(@Valid User user, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("result", result);
            return "user/add";
        } else {
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            user.setPassword(encoder.encode(user.getPassword()));
            userRepository.save(user);
            return "redirect:/user/list";
        }
    }

    /**
     * Some javadoc :
     * 
     * This method displays the form for updating an existing user.
     *
     * @param id    the ID of the user to be updated.
     * @param model the model to which attributes are added for rendering in the
     *              view.
     * @return String representing the view name for displaying the update user
     *         form.
     */
    @GetMapping("/user/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        user.setPassword("");
        model.addAttribute("user", user);
        return "user/update";
    }

    /**
     * Some javadoc :
     * 
     * This method updates user data, encodes the password, saves it to the
     * database, and redirects
     * to the user list.
     *
     * @param id     the ID of the user to be updated.
     * @param user   the User object with updated data.
     * @param result the binding result for validation errors.
     * @param model  the model to which attributes are added for rendering in the
     *               view.
     * @return String representing the view name for redirecting to the user
     *         list or displaying validation errors.
     */
    @PostMapping("/user/update/{id}")
    public String updateUser(@PathVariable("id") int id, @Valid User user,
            BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "user/update";
        }

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        user.setPassword(encoder.encode(user.getPassword()));
        user.setId(id);
        userRepository.save(user);
        model.addAttribute("users", userRepository.findAll());
        return "redirect:/user/list";
    }

    /**
     * Some javadoc :
     * 
     * This method deletes a user by its ID and redirects to the user list.
     *
     * @param id    the ID of the user to be deleted.
     * @param model the model to which attributes are added for rendering in the
     *              view.
     * @return String representing the view name for redirecting to the user
     *         list.
     */
    @GetMapping("/user/delete/{id}")
    public String deleteUser(@PathVariable("id") Integer id, Model model) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        userRepository.delete(user);
        return "redirect:/user/list";
    }
}
