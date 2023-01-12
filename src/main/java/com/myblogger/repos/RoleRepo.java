package com.myblogger.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.myblogger.entities.Role;

public interface RoleRepo extends JpaRepository<Role, Integer> {

}
