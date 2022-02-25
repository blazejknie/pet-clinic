package com.blazej.petclinic.controllers;

import com.blazej.petclinic.model.Pet;
import com.blazej.petclinic.model.Visit;
import com.blazej.petclinic.services.PetService;
import com.blazej.petclinic.services.VisitService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

@Controller
public class VisitController {

    private static final String VIEW_VISIT_CREATE_OR_UPDATE_FORM = "pets/createOrUpdateVisitForm";

    private final VisitService visitService;
    private final PetService petService;

    public VisitController(VisitService visitService, PetService petService) {
        this.visitService = visitService;
        this.petService = petService;
    }

    @InitBinder
    public void setAllowedFields(WebDataBinder binder) {
        binder.setDisallowedFields("id");
    }

    @ModelAttribute("visit")
    public Visit loadPetVisit(@PathVariable Long petId, Model model) {
        Pet pet = petService.findById(petId);
        Visit visit = new Visit();
        pet.addVisit(visit);
        model.addAttribute("pet", pet);
        return visit;
    }

    @GetMapping("/owners/*/pets/{petId}/visits/new")
    public String initNewVisitForm(Model model) {
        return VIEW_VISIT_CREATE_OR_UPDATE_FORM;
    }

    @PostMapping("/owners/*/pets/{petId}/visits/new")
    public String processNewVisitForm(@Validated Visit visit, BindingResult result) {
        if (result.hasErrors()) {
            return VIEW_VISIT_CREATE_OR_UPDATE_FORM;
        } else {
            visitService.save(visit);
            return "redirect:/owners/" + visit.getPet().getOwner().getId() + "/pets/";
        }

    }
}
