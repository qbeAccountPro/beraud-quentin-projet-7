package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.services.RuleNameService;

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
 * Controller class for managing RuleName entities.
 * Handles CRUD operations for RuleName objects, including listing, adding,
 * updating, and deleting rule names.
 */
@Controller
public class RuleNameController {

    @Autowired
    RuleNameService ruleNameService = new RuleNameService();

    /**
     * Some javadoc :
     * 
     * This method displays the list of all rule names.
     *
     * @param model the model to which attributes are added for rendering in the
     *              view.
     * @return String representing the view name for displaying the rule name list.
     */
    @RequestMapping("/ruleName/list")
    public String home(Model model) {
        model.addAttribute("authentication", SecurityContextHolder.getContext().getAuthentication());
        model.addAttribute("ruleNames", ruleNameService.findAll());
        return "ruleName/list";
    }

    /**
     * Some javadoc :
     * 
     * This method displays the form for adding a new rule name.
     *
     * @param rule the RuleName object to be added.
     * 
     * @return String representing the view name for displaying the add rule name
     *         form.
     */
    @GetMapping("/ruleName/add")
    public String addRuleForm(RuleName rule) {
        return "ruleName/add";
    }

    /**
     * Some javadoc :
     * 
     * This method validates rule name data, saves it to the database, and redirects
     * to the rule name list.
     *
     * @param ruleName the RuleName object to be validated and saved.
     * @param result   the binding result for validation errors.
     * @param model    the model to which attributes are added for rendering in the
     *                 view.
     * @return String representing the view name for redirecting to the rule name
     *         list or displaying validation errors.
     */
    @PostMapping("/ruleName/validate")
    public String validate(@Valid RuleName ruleName, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("result", result);
            return "ruleName/add";
        }
        ruleNameService.saveRuleName(ruleName);
        return "redirect:/ruleName/list";
    }

    /**
     * Some javadoc :
     * 
     * This method displays the form for updating an existing rule name.
     *
     * @param id    the ID of the rule name to be updated.
     * @param model the model to which attributes are added for rendering in the
     *              view.
     * @return String representing the view name for displaying the update rule name
     *         form.
     */
    @GetMapping("/ruleName/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("authentication", SecurityContextHolder.getContext().getAuthentication());
        Optional<RuleName> optionalRuleName = ruleNameService.findById(id);
        if (optionalRuleName.isPresent()) {
            model.addAttribute("ruleName", optionalRuleName.get());
            return "ruleName/update";
        }
        return "404";

    }

    /**
     * Some javadoc :
     * 
     * This method updates rule name data, saves it to the database, and redirects
     * to the rule name list.
     *
     * @param id       the ID of the rule name to be updated.
     * @param ruleName the RuleName object with updated data.
     * @param result   the binding result for validation errors.
     * @param model    the model to which attributes are added for rendering in the
     *                 view.
     * @return String representing the view name for redirecting to the rule name
     *         list or displaying validation errors.
     */
    @PostMapping("/ruleName/update/{id}")
    public String updateRuleName(@PathVariable("id") Integer id, @Valid RuleName ruleName,
            BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("result", result);
            return "ruleName/update";
        }
        ruleNameService.saveRuleName(ruleName);
        return "redirect:/ruleName/list";
    }

    /**
     * Some javadoc :
     * 
     * This method deletes a rule name by its ID and redirects to the rule name
     * list.
     *
     * @param id    the ID of the rule name to be deleted.
     * 
     * @return String representing the view name for redirecting to the rule name
     *         list.
     */
    @GetMapping("/ruleName/delete/{id}")
    public String deleteRuleName(@PathVariable("id") Integer id) {
        ruleNameService.deleteById(id);
        return "redirect:/ruleName/list";
    }
}
