package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.services.BidListService;

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
 * Controller class for managing BidList entities.
 * 
 * Handles CRUD operations for BidList objects, including listing, adding,
 * updating, and deleting bids.
 */
@Controller
public class BidListController {
    @Autowired
    BidListService bidService;

    /**
     * Some javadoc :
     * 
     * This method displays the list of all bid items.
     *
     * @param model the model to which attributes are added for rendering in the
     *              view.
     * @return String representing the view name for displaying the bid list.
     */
    @RequestMapping("/bidList/list")
    public String home(Model model) {
        model.addAttribute("authentication", SecurityContextHolder.getContext().getAuthentication());
        model.addAttribute("bidLists", bidService.findAll());
        return "bidList/list";
    }

    /**
     * Some javadoc :
     * 
     * This method displays the form for adding a new bid.
     *
     * @param bid the bidlist object to be added.
     * @return String representing the view name for displaying the add bid form.
     */
    @GetMapping("/bidList/add")
    public String addBidForm(BidList bid) {
        return "bidList/add";
    }

    /**
     * Some javadoc :
     * 
     * This method validates bid data, saves it to the database, and redirects to
     * the bid list.
     *
     * @param bidList the BidList object to be validated and saved.
     * @param result  the binding result for validation errors.
     * @param model   the model to which attributes are added for rendering in the
     *                view.
     * @return String representing the view name for redirecting to the bid list or
     *         displaying validation errors.
     */
    @PostMapping("/bidList/validate")
    public String validate(@Valid BidList bidList, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("result", result);
            return "bidList/add";
        }
        bidService.saveBidList(bidList);
        return "redirect:/bidList/list";
    }

    /**
     * Some javadoc :
     * 
     * This method displays the form for updating an existing bid.
     *
     * @param id    the ID of the bid to be updated.
     * @param model the model to which attributes are added for rendering in the
     *              view.
     * @return String representing the view name for displaying the update bid form.
     */
    @GetMapping("/bidList/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("authentication", SecurityContextHolder.getContext().getAuthentication());
        Optional<BidList> optionalBid = bidService.findById(id);
        if (optionalBid.isPresent()) {
            model.addAttribute("bidList", optionalBid.get());
            return "bidList/update";
        } else {
            return "404";
        }
    }

    /**
     * Some javadoc :
     * 
     * This method updates bid data, saves it to the database, and redirects to the
     * bid list.
     *
     * @param id      the ID of the bid to be updated.
     * @param bidList the BidList object with updated data.
     * @param result  the binding result for validation errors.
     * @param model   the model to which attributes are added for rendering in the
     *                view.
     * @return String representing the view name for redirecting to the bid list or
     *         displaying validation errors.
     */
    @PostMapping("/bidList/update/{id}")
    public String updateBid(@PathVariable("id") Integer id, @Valid BidList bidList,
            BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("result", result);
            return "bidList/update";
        }
        bidService.saveBidList(bidList);
        return "redirect:/bidList/list";
    }

    /**
     * Some javadoc :
     * 
     * This method deletes a bid by its ID and redirects to the bid list.
     *
     * @param id the ID of the bid to be deleted.
     * @return String representing the view name for redirecting to the bid list.
     */
    @GetMapping("/bidList/delete/{id}")
    public String deleteBid(@PathVariable("id") Integer id) {
        bidService.deleteBidById(id);
        return "redirect:/bidList/list";
    }
}