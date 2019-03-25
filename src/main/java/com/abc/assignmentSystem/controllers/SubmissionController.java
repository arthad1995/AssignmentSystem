package com.abc.assignmentSystem.controllers;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.abc.assignmentSystem.beans.Submission;
import com.abc.assignmentSystem.beans.UploadFile;
import com.abc.assignmentSystem.http.Response;
import com.abc.assignmentSystem.services.FileService;
import com.abc.assignmentSystem.services.SubmissionService;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/submissions")
//all checked!
public class SubmissionController {

	@Autowired
	FileService fileService;
	@Autowired
	SubmissionService submissionService;
	
	@PostMapping
	public Response<String> submit(@RequestParam("submission") String s, @RequestParam("file") MultipartFile file){
		
		System.out.println(s);
		//from json string to object
		Submission submission = null;
		try {
			submission = new ObjectMapper().readValue(s, Submission.class);
		} catch (JsonParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (JsonMappingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		try {
			UploadFile uploadFile = new UploadFile();
			uploadFile.setUploaderId(submission.getStudentId());
			
			//uploadFile.setName(file.getName());
			Response<Integer>  res  =	fileService.save(uploadFile, file);
			submission.setFileId(res.getPayload());
			submissionService.submit(submission);
		}
		catch(Exception e) {
		return new Response<>(false, e.getMessage());
		}
		return new Response<String>(true);
	}
	
	
	
	//unchecked get single student all submissions
	@GetMapping("/assignment/studentSubmissions/{id}")
	public Response <List<Submission>> allByStudentSubmission(@PathVariable int id) throws Exception{
		
		return submissionService.downloadSubmissionDetailByStudentId(id);
	}
	
	
	@GetMapping
	public Response <List<Submission>> allmission() throws Exception{
		
		return submissionService.getAllSubmission();
	}
	
	//get single submission file
	@GetMapping("/{id}")
	public ResponseEntity<Resource> submissionDownload(@PathVariable int id) throws Exception{
		int fileId = submissionService.downloadSubmissionBySubmissionId(id);
		if(fileId==-1)
			return null;
		return fileService.fileDownload(fileId);
	}
	
	
	//get single submission file detail
	@GetMapping("/fileDeatil/{id}")
	public Response<UploadFile> submissionfileDetailDownload(@PathVariable int id) throws Exception{
		int fileId = submissionService.downloadSubmissionBySubmissionId(id);
		if(fileId==-1)
			return null;
		return fileService.getSubmissionFileDetail(fileId);
	}
	
	//get zip file that has all submission for that assignment
	@GetMapping("assignment/{id}")
	public ResponseEntity<Resource> assignmentSubmissionDownload(@PathVariable int id) throws Exception{

		List<Integer> list= submissionService.downloadSubmissionByAssignment(id);
		System.out.println("size is "+list.size());
		return fileService.zipDownload(list,id);
	}
	
	//get student's submission history
	@GetMapping("history/{id}")
	public Response<List<UploadFile>> previousSubmission(@PathVariable int id) throws Exception{

		List<Integer> list= submissionService.downloadSubmissionByStudentId(id);
		System.out.println(list.size());
		return fileService.getFileDetail(list);
	}
	
	@PostMapping("/grade")
	public Response<String> gradeAssignment(@RequestBody Submission sub){
		return submissionService.gradeAssignment(sub);
	}
	
	
	
	
	//this function to submit to public file, not the assignment
	@PostMapping("/uploads")
	public Response<String> upload(@RequestParam("submission") String s, @RequestParam("file") MultipartFile file){
		
		
		//from json string to object
		Submission submission = null;
		try {
			submission = new ObjectMapper().readValue(s, Submission.class);
		} catch (JsonParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (JsonMappingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		try {
			UploadFile uploadFile = new UploadFile();
			uploadFile.setUploaderId(submission.getStudentId());
			
			//uploadFile.setName(file.getName());
			Response<Integer>  res  =	fileService.save(uploadFile, file);
			submission.setFileId(res.getPayload());
			submission.setAssignmentId(-1);
			submissionService.submit(submission);
		}
		catch(Exception e) {
		return new Response<>(false, e.getMessage());
		}
		return new Response<String>(true);
	}
	
	//download the public files
	@GetMapping("/uploads")
	public Response<List<UploadFile>> getAllPublicUploadedFiles(){
List<Integer> list= submissionService.downloadSubmissionByAssignment(-1);
		System.out.println(list.size());
		return fileService.getFileDetail(list);
	}
	
}
