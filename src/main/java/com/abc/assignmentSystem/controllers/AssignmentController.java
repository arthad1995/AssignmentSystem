package com.abc.assignmentSystem.controllers;


import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.abc.assignmentSystem.beans.Assignment;
import com.abc.assignmentSystem.http.Response;
import com.abc.assignmentSystem.services.AssignmentService;


//all checked
@RestController
@RequestMapping("/assignments")
public class AssignmentController {

	@Autowired
	private AssignmentService as;
	@PostMapping
	public Response<String> addAssignement(@RequestBody Assignment assi) throws ParseException{
		
		return as.addAssignement(assi);
	}
	@PutMapping
	public Response<String> update(@RequestBody Assignment assi){
		return as.update(assi);
	}
	@DeleteMapping("/{id}")
	public Response<String> deleteAssignment(@PathVariable int id){
		return as.deleteAssignment(id);
	}
	@GetMapping("/{id}")
	public Response<Assignment> getAssignment(@PathVariable int id){
		return as.getAssignment(id);
	}
	
	@GetMapping
	public  Response<List<Assignment>> getAllAssignment(){
		return as.getAllAssignment();
	}
	
}
