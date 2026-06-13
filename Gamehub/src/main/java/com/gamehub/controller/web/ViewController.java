package com.gamehub.controller.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {

	@GetMapping("/")
	public String inicio() {
	    return "index";
	}

    @GetMapping("/login")
    public String login() {
        return "auth/login";
    }
    
    

    @GetMapping("/registro")
    public String registro() {
        return "auth/registro";
    }

    @GetMapping("/dashboard")
    public String dashboard() {
        return "dashboard";
    }
}