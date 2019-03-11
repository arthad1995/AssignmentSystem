package com.abc.assignmentSystem.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abc.assignmentSystem.Daos.SubmissionDao;
import com.abc.assignmentSystem.beans.Submission;
import com.abc.assignmentSystem.http.Response;

import lombok.var;

@Service
public class SubmissionService {
	@Autowired
	private SubmissionDao sd;
	
	public Response<String> submit(Submission submission){
		sd.save(submission);
		return new Response<>(true);
	}
	
	
	
	public int downloadSubmissionBySubmissionId(int id) {
		int i =-1;
		
	 Optional<Submission> s=	sd.findById(id);
	 if(!s.isPresent()) {
		 return i;
	 }
	 
	 i = s.get().getFileId();
		return i;
	}
	
	public List<Integer> downloadSubmissionByAssignment(int id){
		List<Integer> list = new ArrayList<>();
		
		List<Submission> l = sd.findByAssignmentId(id);
		for(var x : l) {
		list.add(x.getFileId());
		}
		return list;
		
	}
	
	
	public List<Integer> downloadSubmissionByStudentId(int id){
		List<Integer> list = new ArrayList<>();
		
		List<Submission> l = sd.findBystudentId(id);
		for(var x : l) {
		list.add(x.getFileId());
		}
		return list;
		
	}
	
	public Response<String> gradeAssignment(Submission sub){
		try {
			Submission s = sd.getOne(sub.getId());
			s.setGraderId(sub.getGraderId());
			s.setScore(sub.getScore());
			sd.save(s);
		}
		catch(Exception e) {
			return new Response<>(false,e.getMessage());
		}
		return new Response<>(true);
	}
}
