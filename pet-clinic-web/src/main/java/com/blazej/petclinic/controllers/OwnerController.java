package com.blazej.petclinic.controllers;

import com.blazej.petclinic.services.map.OwnerServiceMap;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/owners")
@Controller
public class OwnerController {

    private final OwnerServiceMap ownerServiceMap;

    public OwnerController(OwnerServiceMap ownerServiceMap) {
        this.ownerServiceMap = ownerServiceMap;
    }

    @RequestMapping({"", "/", "index", "index.html"})
    public String listOwners(Model model) {
        model.addAttribute("owners", ownerServiceMap.findAll());
        return "owners/index";
    }
    @RequestMapping("/find")
    public String findOwners() {
        return "notImplemented";
    }
}
