package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.services.TradeService;

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
 * Some javadoc :
 * 
 * Controller class for managing Trade entities.
 * Handles CRUD operations for Trade objects, including listing, adding,
 * updating, and deleting trades.
 */
@Controller
public class TradeController {

    @Autowired
    TradeService tradeService = new TradeService();

    /**
     * Some javadoc :
     * This method displays the list of all trades.
     *
     * @param model the model to which attributes are added.
     * @return String representing the view name for displaying the trade list.
     */
    @RequestMapping("/trade/list")
    public String home(Model model) {
        model.addAttribute("authentication", SecurityContextHolder.getContext().getAuthentication());
        model.addAttribute("trades", tradeService.findAll());
        return "trade/list";
    }

    /**
     * Some javadoc :
     * This method displays the form for adding a new trade.
     * 
     * @param trade the trade object to be added.
     * @return String representing the view name for displaying the add trade form.
     */
    @GetMapping("/trade/add")
    public String addTrade(Trade trade) {
        return "trade/add";
    }

    /**
     * Some javadoc :
     * This method validates trade data, saves it to the database, and redirects
     * to the trade list.
     *
     * @param trade  the Trade object to be validated and saved.
     * @param result the binding result for validation errors.
     * @param model  the model to which attributes are added for rendering in the
     *               view.
     * @return String representing the view name for redirecting to the trade
     *         list or displaying validation errors.
     */
    @PostMapping("/trade/validate")
    public String validate(@Valid Trade trade, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("result", result);
            return "trade/add";
        }
        tradeService.saveTrade(trade);
        return "redirect:/trade/list";
    }

    /**
     * Some javadoc :
     * This method displays the form for updating an existing trade.
     *
     * @param id    the ID of the trade to be updated.
     * @param model the model to which attributes are added for rendering in the
     *              view.
     * @return String representing the view name for displaying the update trade
     *         form.
     */
    @GetMapping("/trade/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("authentication", SecurityContextHolder.getContext().getAuthentication());
        Optional<Trade> optionalTrade = tradeService.findById(id);
        if (optionalTrade.isPresent()) {
            model.addAttribute("trade", optionalTrade.get());
            return "trade/update";
        } else {
            return "404";
        }
    }

    /**
     * Some javadoc :
     * This method updates trade data, saves it to the database, and redirects
     * to the trade list.
     *
     * @param id     the ID of the trade to be updated.
     * @param trade  the Trade object with updated data.
     * @param result the binding result for validation errors.
     * @param model  the model to which attributes are added for rendering in the
     *               view.
     * @return String representing the view name for redirecting to the trade
     *         list or displaying validation errors.
     */
    @PostMapping("/trade/update/{id}")
    public String updateTrade(@PathVariable("id") Integer id, @Valid Trade trade,
            BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("result", result);
            return "trade/update";
        }
        tradeService.saveTrade(trade);
        return "redirect:/trade/list";
    }

    /**
     * Some javadoc :
     * This method deletes a trade by its ID and redirects to the trade
     * list.
     *
     * @param id the ID of the trade to be deleted.
     * 
     * @return String representing the view name for redirecting to the trade
     *         list.
     */
    @GetMapping("/trade/delete/{id}")
    public String deleteTrade(@PathVariable("id") Integer id) {
        tradeService.deleteById(id);
        return "redirect:/trade/list";
    }
}
