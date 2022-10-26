package com.vourapido.security;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("*")
public class HomeController {
	
	
	
	 @PreAuthorize("hasRole('USER')")
	    @GetMapping("/user")
	    public String usuario() {
	        return "/user";
	    }
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/admin")
	public String admin() {
		return "Olá admin";
	}
}
