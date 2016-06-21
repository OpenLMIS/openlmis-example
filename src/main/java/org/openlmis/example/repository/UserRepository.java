package org.openlmis.example.repository;

import org.openlmis.example.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
  Optional<User> findOneByUsername(String username);
}
