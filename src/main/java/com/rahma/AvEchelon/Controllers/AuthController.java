package com.rahma.AvEchelon.Controllers;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import com.rahma.AvEchelon.Configuration.JWTService;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*", maxAge = 3600)
public class AuthController {

private JWTService jwtService;
	
	public AuthController(JWTService jwtService) {
		this.jwtService = jwtService;
	}
	
	
	@PostMapping("/login")
	public String getToken(Authentication authentication) {
        		String token = jwtService.generateToken(authentication);
        		return token;
	}
	

}
