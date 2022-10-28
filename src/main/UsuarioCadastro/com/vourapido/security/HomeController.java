package com.vourapido.security;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
@CrossOrigin(origins = "http://127.0.0.1:5173", maxAge = 3600)
public class HomeController {
	
	

	
	 @PreAuthorize("hasRole('usuario')")
	    @GetMapping("/usuario")
	    public String usuario() {
	        return "hwllou";
	    }
	
	 
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/admin")
	public String admin() {
		return "Olá admin";
	}
}
