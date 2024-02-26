package com.swp.server.services.admin;

import com.swp.server.dto.BlockAccountDTO;
import com.swp.server.dto.CreateAccountDTO;
import org.springframework.http.ResponseEntity;

public interface AdminService {
    public ResponseEntity<?> viewListAccounts();

    public ResponseEntity<?> createAccount(CreateAccountDTO createAccountDTO);

    public ResponseEntity<?> blockAccount(BlockAccountDTO blockAccountDTO);
}
