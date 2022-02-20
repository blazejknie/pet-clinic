package com.blazej.petclinic.controllers;

import com.blazej.petclinic.model.Owner;
import com.blazej.petclinic.services.OwnerService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RequestMapping("/owners")
@Controller
public class OwnerController {

    private static final String VIEW_OWNER_CREATE_OR_UPDATE_FORM = "owners/createOrUpdateOwnerForm";
    private final OwnerService ownerService;

    public OwnerController(OwnerService ownerService) {
        this.ownerService = ownerService;
    }

    @InitBinder
    public void setAllowedFields(WebDataBinder dataBinder) {
        dataBinder.setDisallowedFields("id");
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

    @GetMapping("/new")
    public String initCreationForm(Model model) {
        model.addAttribute("owner", Owner.builder().build());
        return VIEW_OWNER_CREATE_OR_UPDATE_FORM;
    }

    @PostMapping("/new")
    public String processCreationForm(@Validated Owner owner, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return VIEW_OWNER_CREATE_OR_UPDATE_FORM;
        }else {
            Owner savedOwner = ownerService.save(owner);
            return "redirect:/owners/" + savedOwner.getId();
        }
    }

    @GetMapping("/{ownerId}/edit")
    public String initUpdateOwnerForm(Model model, @PathVariable("ownerId") Long ownerId) {
        model.addAttribute(ownerService.findById(ownerId));
        return VIEW_OWNER_CREATE_OR_UPDATE_FORM;
    }

    @PostMapping("/{ownerId}/edit")
    public String processUpdateOwnerForm(@Validated Owner owner,
                                         BindingResult bindingResult,
                                         @PathVariable("ownerId") Long ownerId) {

        if (bindingResult.hasErrors()) {
            return VIEW_OWNER_CREATE_OR_UPDATE_FORM;
        }else {
            owner.setId(ownerId);
            Owner savedOwner = ownerService.save(owner);
            return "redirect:/owners/" + savedOwner.getId();
        }
    }
}
