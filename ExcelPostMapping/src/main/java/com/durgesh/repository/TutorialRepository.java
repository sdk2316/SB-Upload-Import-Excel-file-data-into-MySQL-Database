package com.durgesh.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.durgesh.model.Tutorial;

public interface TutorialRepository extends JpaRepository<Tutorial, Long> {
}