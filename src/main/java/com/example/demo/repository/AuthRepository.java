package com.example.demo.repository;

import com.example.demo.entity.Auth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface AuthRepository extends JpaRepository<Auth, Long>, JpaSpecificationExecutor<Auth> {
}
