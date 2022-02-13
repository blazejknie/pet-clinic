package com.blazej.petclinic.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {

    @GetMapping({"", "/", "index", "index.html"})
    String index() {
        return "index";
    }

    @GetMapping("/oups")
    public String oupsHandler() {
        return "notImplemented";
    }
}
