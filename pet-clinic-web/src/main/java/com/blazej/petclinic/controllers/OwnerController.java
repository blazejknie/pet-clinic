package com.blazej.petclinic.controllers;

import com.blazej.petclinic.model.Owner;
import com.blazej.petclinic.services.OwnerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RequestMapping("/owners")
@Controller
public class OwnerController {

    private final OwnerService ownerService;

    public OwnerController(OwnerService ownerService) {
        this.ownerService = ownerService;
    }


    @GetMapping("/find")
    public String findOwners(Model model) {
        model.addAttribute("owner", Owner.builder().build());
        return "owners/findOwners";
    }

    @GetMapping()
    public String processFindForm(Owner owner, BindingResult result, Model model) {
        if (owner.getLastName() == null) {
            owner.setLastName("");
        }
        List<Owner> foundOwners = ownerService.findAllByLastNameLike("%" + owner.getLastName() + "%");

        if (foundOwners.isEmpty()) {
            result.rejectValue("lastName", "notFound", "Not Found");
            return "owners/findOwners";
        } else if (foundOwners.size() == 1) {
            return "redirect:/owners/" + foundOwners.get(0).getId();
        } else {
            model.addAttribute("selections", foundOwners);
            return "owners/ownersList";
        }

    }

    @GetMapping("/{ownerId}")
    public ModelAndView displayOwner(@PathVariable Long ownerId) {
        ModelAndView modelAndView = new ModelAndView("owners/ownerDetails");
        modelAndView.addObject(ownerService.findById(ownerId));

        return modelAndView;
    }
}
