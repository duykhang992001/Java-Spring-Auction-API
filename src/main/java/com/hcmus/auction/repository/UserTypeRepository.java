package com.hcmus.auction.repository;

import com.hcmus.auction.model.entity.UserType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserTypeRepository extends JpaRepository<UserType, String> {
    Optional<UserType> findByName(String name);
}