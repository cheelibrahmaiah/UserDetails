package com.coding.challenge.app.repository;

import com.coding.challenge.app.entity.Email;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmailRepository extends JpaRepository<Email, Integer> {
}
