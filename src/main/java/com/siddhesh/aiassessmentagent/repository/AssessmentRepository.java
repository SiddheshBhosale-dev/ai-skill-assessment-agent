package com.siddhesh.aiassessmentagent.repository;

import com.siddhesh.aiassessmentagent.entity.Assessment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AssessmentRepository extends JpaRepository<Assessment, Long> {
}