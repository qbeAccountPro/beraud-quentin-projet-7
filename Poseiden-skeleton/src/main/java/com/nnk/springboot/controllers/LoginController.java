package com.nnk.springboot.controllers;

import com.nnk.springboot.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Controller class for managing login-related requests.
 * Handles requests for login, displaying user articles, and handling error
 * pages.
 */
@Controller
@RequestMapping("app")
public class LoginController {

    @Autowired
    UserRepository userRepository;

    /**
     * Some javadoc :
     * 
     * This method handles requests for the login page.
     *
     * @return ModelAndView representing the view and model for the login page.
     */
    @GetMapping("login")
    public ModelAndView login() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("login");
        return mav;
    }

    /**
     * Some javadoc :
     * 
     * This method handles requests for displaying all user articles in a secure
     * manner.
     *
     * @return ModelAndView representing the view and model for displaying user
     *         articles.
     */
    @GetMapping("secure/article-details")
    public ModelAndView getAllUserArticles() {
        ModelAndView mav = new ModelAndView();
        mav.addObject("users", userRepository.findAll());
        mav.setViewName("user/list");
        return mav;
    }

    /**
     * Some javadoc :
     * 
     * This method handles requests for displaying the error page.
     *
     * @return ModelAndView representing the view and model for the error page.
     */
    @GetMapping("error")
    public ModelAndView error() {
        ModelAndView mav = new ModelAndView();
        String errorMessage = "You are not authorized for the requested data.";
        mav.addObject("errorMsg", errorMessage);
        mav.setViewName("403");
        return mav;
    }
}
