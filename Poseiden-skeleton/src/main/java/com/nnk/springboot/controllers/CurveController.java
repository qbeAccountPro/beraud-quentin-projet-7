package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.services.CurveService;

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
public class CurveController {
    @Autowired
    CurveService curveService = new CurveService();

    /* Call service, find all curves to show to the view */
    @RequestMapping("/curvePoint/list")
    public String home(Model model) {
        model.addAttribute("curvePoints", curveService.findAll());
        return "curvePoint/list";
    }

    @GetMapping("/curvePoint/add")
    public String addCurvePointForm(CurvePoint bid) {
        return "curvePoint/add";
    }

    /* check data valid and save to db, after saving return curve list */
    @PostMapping("/curvePoint/validate")
    public String validate(@Valid CurvePoint curvePoint, BindingResult result, Model model) {
        curveService.saveCurvePoint(curvePoint);
        model.addAttribute("bidLists", curveService.findAll());
        return "curvePoint/add";
    }

    /* get Curve by Id and to model then show to the form */
    @GetMapping("/curvePoint/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        Optional<CurvePoint> CurvePoints = curveService.findById(id);
        if (CurvePoints.isPresent()) {
            model.addAttribute("bidList", CurvePoints);
            return "curvePoint/update";
        } else {
            return "404"; // TODO NOT FOUND
        }
    }

    /*
     * check required fields, if valid call service to update curve and return list
     * curve
     */
    @PostMapping("/curvePoint/update/{id}")
    public String updateCurvePoint(@PathVariable("id") Integer id, @Valid CurvePoint curvePoint,
            BindingResult result, Model model) {
        curveService.saveCurvePoint(curvePoint);
        model.addAttribute("bidLists", curveService.findAll());
        return "redirect:/curvePoint/list";
    }

    @GetMapping("/curvePoint/delete/{id}")
    public String deleteBid(@PathVariable("id") Integer id, Model model) {
        curveService.deleteById(id);
        return "redirect:/curvePoint/list";
    }
}
