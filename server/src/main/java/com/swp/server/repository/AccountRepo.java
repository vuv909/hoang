package com.swp.server.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.swp.server.entities.Account;

@Repository
public interface AccountRepo extends JpaRepository<Account, Integer> {
	Optional<Account> findFirstByEmail(String email);

	Optional<Account> findFirstByEmailAndOtpCode(String email, String otpCode);

	Optional<Account> findByUsername(String username);

	Optional<Account> findFirstByUsername(String username);

	List<Account> findBy();
}
