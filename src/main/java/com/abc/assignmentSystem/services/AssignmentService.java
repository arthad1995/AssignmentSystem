package com.abc.assignmentSystem.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abc.assignmentSystem.Daos.AssignmentDao;
import com.abc.assignmentSystem.beans.Assignment;
import com.abc.assignmentSystem.http.Response;

@Service
public class AssignmentService {
	@Autowired
	private AssignmentDao ad;
	
	
	public Response<String> addAssignement(Assignment assi){
		try {
			ad.save(assi);
		}
		catch(Exception e) {
			return new Response<String>(false, e.getMessage());
		}
		return new Response<>(true);
	}
	
	public Response<String> update(Assignment assi){
		try {
			System.out.println(assi);
			Assignment a = ad.findById(assi.getId()).get();
			System.out.println(a);
			if(assi.getDescription()!=null) {
				a.setDescription(assi.getDescription());
			}
			if(assi.getDuedate()!=null) {
				a.setDuedate(assi.getDuedate());
			}
			if(assi.getTittle()!=null) {
				a.setTittle(assi.getTittle());;
			}
			
			ad.save(a);
		}
		catch(Exception e) {
			return new Response<String>(false, e.getMessage());
		}
		return new Response<>(true);
	}
	
	public Response<String> deleteAssignment(int id){
		try {
			ad.deleteById(id);
		}
		catch(Exception e) {
			return new Response<String>(false, e.getMessage());
		}
		return new Response<>(true);
	}
	
	public Response<Assignment> getAssignment(int id){
		Assignment a = null;
		try {
			a=ad.findById(id).get();
		}
		catch(Exception e) {
			return new Response<Assignment>(false, a);
		}
		return new Response<>(true,a);
	}
	
	public Response<List<Assignment>> getAllAssignment(){
		List<Assignment> a = null;
		try {
			a=ad.findAll();
		}
		catch(Exception e) {
			return new Response<List<Assignment>>(false, a);
		}
		return new Response<>(true,a);
	}
	
}
