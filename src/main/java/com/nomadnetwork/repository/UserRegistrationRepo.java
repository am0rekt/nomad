package com.nomadnetwork.repository;
import com.nomadnetwork.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRegistrationRepo extends JpaRepository<User, Long> {

}
