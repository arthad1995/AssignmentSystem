package com.abc.assignmentSystem.Daos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.abc.assignmentSystem.beans.Submission;

public interface SubmissionDao extends JpaRepository<Submission, Integer>{
	public List<Submission> findByAssignmentId(int id);
	public List<Submission> findBystudentId(int id);
}
