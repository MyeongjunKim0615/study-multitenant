package com.daou.test;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TestRepositoty extends JpaRepository<Test, Integer> {
    Optional<Test> findByTestParam2(String testParam2);
}
