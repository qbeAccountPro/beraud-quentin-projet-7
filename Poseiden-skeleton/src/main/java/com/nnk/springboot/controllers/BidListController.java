package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.services.BidListService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

import javax.validation.Valid;

@Controller
public class BidListController {
    @Autowired
    BidListService bidService;

    /* call service find all bids to show to the view */
    @RequestMapping("/bidList/list")
    public String home(Model model) {
        model.addAttribute("bidLists", bidService.findAll());
        return "bidList/list";
    }

    @GetMapping("/bidList/add")
    public String addBidForm(BidList bid) {
        return "bidList/add";
    }

    /* check data valid and save to db, after saving return bid list */
    @PostMapping("/bidList/validate")
    public String validate(@Valid BidList bidList, BindingResult result, Model model) {
        bidService.saveBidList(bidList);
        model.addAttribute("bidLists", bidService.findAll());
        return "bidList/list";
    }

    /* get Bid by Id and to model then show to the form */
    @GetMapping("/bidList/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        Optional<BidList> bid = bidService.findById(id);
        if (bid.isPresent()) {
            model.addAttribute("bidList", bid);
            return "bidList/update";
        } else {
            return "404"; // TODO NOT FOUND
        }
    }

    /*
     * check required fields, if valid call service to update Bid and return list
     * Bid
     */
    @PostMapping("/bidList/update/{id}")
    public String updateBid(@PathVariable("id") Integer id, @Valid BidList bidList,
            BindingResult result, Model model) {
        bidService.saveBidList(bidList);
        model.addAttribute("bidLists", bidService.findAll());
        return "redirect:/bidList/list";
    }

    /*
     * Find Bid by Id and delete the bid, return to Bid list
     */
    @GetMapping("/bidList/delete/{id}")
    public String deleteBid(@PathVariable("id") Integer id, Model model) {
        bidService.deleteBidById(id);
        return "redirect:/bidList/list";
    }
}
