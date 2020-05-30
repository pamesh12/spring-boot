package com.pamesh.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pamesh.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

}
