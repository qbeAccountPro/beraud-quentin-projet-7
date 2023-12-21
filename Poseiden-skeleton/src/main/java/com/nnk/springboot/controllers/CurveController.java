package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.services.CurveService;

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
 * Controller class for managing CurvePoint entities.
 * Handles CRUD operations for CurvePoint objects, including listing, adding,
 * updating, and deleting curve points.
 */
@Controller
public class CurveController {
    @Autowired
    CurveService curveService = new CurveService();

    /**
     * Some javadoc :
     * 
     * This method displays the list of all curve points.
     *
     * @param model the model to which attributes are added for rendering in the
     *              view.
     * @return String representing the view name for displaying the curve point
     *         list.
     */
    @RequestMapping("/curvePoint/list")
    public String home(Model model) {
        model.addAttribute("authentication", SecurityContextHolder.getContext().getAuthentication());
        model.addAttribute("curvePoints", curveService.findAll());
        return "curvePoint/list";
    }

    /**
     * Some javadoc :
     * 
     * This method displays the form for adding a new curve point.
     *
     * @param curvePoint the CurvePoint object to be added.
     * @return String representing the view name for displaying the add curve point
     *         form.
     */
    @GetMapping("/curvePoint/add")
    public String addCurvePointForm(CurvePoint curvePoint) {
        return "curvePoint/add";
    }

    /**
     * Some javadoc :
     * 
     * This method validates curve point data, saves it to the database, and
     * redirects to the curve point list.
     *
     * @param curvePoint the CurvePoint object to be validated and saved.
     * @param result     the binding result for validation errors.
     * @param model      the model to which attributes are added for rendering in
     *                   the view.
     * @return String representing the view name for redirecting to the curve point
     *         list or displaying validation errors.
     */
    @PostMapping("/curvePoint/validate")
    public String validate(@Valid CurvePoint curvePoint, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("result", result);
            return "curvePoint/add";
        }
        curveService.saveCurvePoint(curvePoint);
        return "redirect:/curvePoint/list";
    }

    /**
     * Some javadoc :
     * 
     * This method displays the form for updating an existing curve point.
     *
     * @param id    the ID of the curve point to be updated.
     * @param model the model to which attributes are added for rendering in the
     *              view.
     * @return String representing the view name for displaying the update curve
     *         point form.
     */
    @GetMapping("/curvePoint/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("authentication", SecurityContextHolder.getContext().getAuthentication());
        Optional<CurvePoint> optionalCurvePoint = curveService.findById(id);
        if (optionalCurvePoint.isPresent()) {
            model.addAttribute("curvePoint", optionalCurvePoint.get());
            return "curvePoint/update";
        } else {
            return "404";
        }
    }

    /**
     * Some javadoc :
     * 
     * This method updates curve point data, saves it to the database, and redirects
     * to the curve point list.
     *
     * @param id         the ID of the curve point to be updated.
     * @param curvePoint the CurvePoint object with updated data.
     * @param result     the binding result for validation errors.
     * @param model      the model to which attributes are added for rendering in
     *                   the view.
     * @return String representing the view name for redirecting to the curve point
     *         list or displaying validation errors.
     */
    @PostMapping("/curvePoint/update/{id}")
    public String updateCurvePoint(@PathVariable("id") Integer id, @Valid CurvePoint curvePoint,
            BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("result", result);
            return "curvePoint/update";
        }
        curveService.saveCurvePoint(curvePoint);
        return "redirect:/curvePoint/list";
    }

    /**
     * Some javadoc :
     * 
     * This method deletes a curve point by its ID and redirects to the curve point
     * list.
     *
     * @param id the ID of the curve point to be deleted.
     * @return String representing the view name for redirecting to the curve point
     *         list.
     */
    @GetMapping("/curvePoint/delete/{id}")
    public String deleteCurve(@PathVariable("id") Integer id) {
        curveService.deleteById(id);
        return "redirect:/curvePoint/list";
    }
}
