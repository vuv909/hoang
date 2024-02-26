package com.swp.server.controller;

import com.swp.server.dto.BlockAccountDTO;
import com.swp.server.dto.CreateAccountDTO;
import com.swp.server.services.admin.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin
@SecurityRequirement(name = "Authorization")
public class AdminController {

	@Autowired
	private AdminService adminService;

	@GetMapping("/hello")
	public ResponseEntity<?> hello() {
		return ResponseEntity.ok("Hello world");
	}

	@GetMapping("/accounts")
	public ResponseEntity<?> accounts(){
		return adminService.viewListAccounts();
	}

	@PostMapping("/createAccount")
	public ResponseEntity<?> createAccount(@RequestBody CreateAccountDTO createAccountDTO){
		return adminService.createAccount(createAccountDTO);
	}

	@PostMapping("/blockAccount")
	public ResponseEntity<?> blockAccount(@RequestBody BlockAccountDTO blockAccountDTO){
		return adminService.blockAccount(blockAccountDTO);
	}
}
