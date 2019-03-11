package com.abc.assignmentSystem.Daos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.abc.assignmentSystem.beans.Assignment;

public interface AssignmentDao extends JpaRepository<Assignment, Integer>{

}
