package org.project.spring_security_client.repository;

import org.project.spring_security_client.entity.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface VerificationTokenRepository extends JpaRepository<VerificationToken, UUID> {
    VerificationToken findByToken(String token);
}
