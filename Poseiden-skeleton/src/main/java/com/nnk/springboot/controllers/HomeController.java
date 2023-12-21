package com.nnk.springboot.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Controller class for managing home-related requests.
 * Handles requests for the home page and admin home page redirection.
 */
@Controller
public class HomeController {

	/**
	 * Some javadoc :
	 * 
	 * This method handles requests for the home page.
	 *
	 * @param model the model to which attributes are added for rendering in the
	 *              view.
	 * @return String representing the view name for displaying the home page.
	 */
	@RequestMapping("/")
	public String home(Model model) {
		return "home";
	}

	/**
	 * Some javadoc :
	 * 
	 * This method handles requests for the admin home page and redirects to the bid
	 * list.
	 *
	 * @param model the model to which attributes are added for rendering in the
	 *              view.
	 * @return String representing the view name for redirecting to the bid list.
	 */
	@RequestMapping("/admin/home")
	public String adminHome(Model model) {
		return "redirect:/bidList/list";
	}
}