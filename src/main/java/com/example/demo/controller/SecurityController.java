package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.AuthRequestModel;
import com.example.demo.services.JwtService;

@RestController
public class SecurityController {
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private JwtService jwtService;
	
	@RequestMapping(value="info",method= RequestMethod.GET)
	public String info() {
		return "The application is up";
	}
	
	@RequestMapping(value="hello",method= RequestMethod.GET)
	public String hello() {
		return "Hello PXP!";
	}
	
	@RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public String getJwtAuthToken(@RequestBody AuthRequestModel authRequestModel) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequestModel.getUserName(), authRequestModel.getPassword()));
        }catch (BadCredentialsException e){
            throw new Exception("Incorrect Username or password...", e);
        }

        UserDetails userDetails = userDetailsService.loadUserByUsername(authRequestModel.getUserName());
        final String jwt = jwtService.getJWT(userDetails);
        return jwt;
    }

}
