package ru.itis.hauntedo.simbirtest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itis.hauntedo.simbirtest.model.User;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {


    Optional<User> findOneByEmail(String email);

    User getUserByEmail(String email);

    boolean existsByEmail(String email);
    boolean existsByEmailAndPhone(String email, String phone);
}
