package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.services.RuleNameService;

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
public class RuleNameController {

    @Autowired
    RuleNameService ruleNameService = new RuleNameService();

    @RequestMapping("/ruleName/list")
    public String home(Model model) {
        model.addAttribute("ruleNames", ruleNameService.findAll());
        return "ruleName/list";
    }

    @GetMapping("/ruleName/add")
    public String addRuleForm(RuleName bid) {
        return "ruleName/add";
    }

    @PostMapping("/ruleName/validate")
    public String validate(@Valid RuleName ruleName, BindingResult result, Model model) {
        ruleNameService.saveRuleName(ruleName);
        model.addAttribute("ruleName", ruleName);
        return "ruleName/add";
    }

    @GetMapping("/ruleName/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        Optional<RuleName> ruleName = ruleNameService.findById(id);
        if (ruleName.isPresent()) {
            model.addAttribute("ruleName", ruleName);
            return "ruleName/update";

        } else {
            return "404"; // TODO NOT FOUND
        }
    }

    @PostMapping("/ruleName/update/{id}")
    public String updateRuleName(@PathVariable("id") Integer id, @Valid RuleName ruleName,
            BindingResult result, Model model) {
        ruleNameService.saveRuleName(ruleName);
        model.addAttribute("ruleNames", ruleNameService.findAll());
        return "redirect:/ruleName/list";
    }

    @GetMapping("/ruleName/delete/{id}")
    public String deleteRuleName(@PathVariable("id") Integer id, Model model) {
        ruleNameService.deleteById(id);
        return "redirect:/ruleName/list";
    }
}
