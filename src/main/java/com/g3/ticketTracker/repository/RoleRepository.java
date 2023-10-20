package com.g3.ticketTracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.g3.ticketTracker.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Long>{

	Role findByName(String name);
}
