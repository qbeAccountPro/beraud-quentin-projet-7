package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.services.RatingService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

import javax.validation.Valid;

/**
 * Controller class for managing Rating entities.
 * Handles CRUD operations for Rating objects, including listing, adding,
 * updating, and deleting ratings.
 */
@Controller
public class RatingController {

    @Autowired
    RatingService ratingService = new RatingService();

    /**
     * Some javadoc :
     * 
     * This method displays the list of all ratings.
     *
     * @param model the model to which attributes are added for rendering in the
     *              view.
     * @return String representing the view name for displaying the rating list.
     */
    @RequestMapping("/rating/list")
    public String home(Model model) {
        model.addAttribute("authentication", SecurityContextHolder.getContext().getAuthentication());
        model.addAttribute("ratings", ratingService.findAll());
        return "rating/list";
    }

    /**
     * Some javadoc :
     * 
     * This method displays the form for adding a new rating.
     * 
     * @param rating the rating object to be added.
     *
     * @return String representing the view name for displaying the add rating form.
     */
    @GetMapping("/rating/add")
    public String addRatingForm(Rating rating) {
        return "rating/add";
    }

    /**
     * Some javadoc :
     * 
     * This method validates rating data, saves it to the database, and redirects to
     * the rating list.
     *
     * @param rating the Rating object to be validated and saved.
     * @param result the binding result for validation errors.
     * @param model  the model to which attributes are added for rendering in the
     *               view.
     * @return String representing the view name for redirecting to the rating list
     *         or displaying validation errors.
     */
    @PostMapping("/rating/validate")
    public String validate(@Valid Rating rating, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("result", result);
            return "rating/add";
        }
        ratingService.saveRating(rating);
        return "redirect:/rating/list";
    }

    /**
     * Some javadoc :
     * 
     * This method displays the form for updating an existing rating.
     *
     * @param id    the ID of the rating to be updated.
     * @param model the model to which attributes are added for rendering in the
     *              view.
     * @return String representing the view name for displaying the update rating
     *         form.
     */
    @GetMapping("/rating/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("authentication", SecurityContextHolder.getContext().getAuthentication());
        Optional<Rating> optionalRating = ratingService.findById(id);
        if (optionalRating.isPresent()) {
            model.addAttribute("rating", optionalRating.get());
            return "rating/update";
        } else {
            return "404";
        }
    }

    /**
     * Some javadoc :
     * 
     * This method updates rating data, saves it to the database, and redirects to
     * the rating list.
     *
     * @param id     the ID of the rating to be updated.
     * @param rating the Rating object with updated data.
     * @param result the binding result for validation errors.
     * @param model  the model to which attributes are added for rendering in the
     *               view.
     * @return String representing the view name for redirecting to the rating list
     *         or displaying validation errors.
     */
    @PostMapping("/rating/update/{id}")
    public String updateRating(@PathVariable("id") Integer id, @Valid Rating rating,
            BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("result", result);
            return "rating/update";
        }
        ratingService.saveRating(rating);
        return "redirect:/rating/list";
    }

    /**
     * Some javadoc :
     * 
     * This method deletes a rating by its ID and redirects to the rating list.
     *
     * @param id the ID of the rating to be deleted.
     * @return String representing the view name for redirecting to the rating list.
     */
    @GetMapping("/rating/delete/{id}")
    public String deleteRating(@PathVariable("id") Integer id) {
        ratingService.deleteRating(id);
        return "redirect:/rating/list";
    }
}
