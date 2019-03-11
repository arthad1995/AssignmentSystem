package com.abc.assignmentSystem.controllers;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.abc.assignmentSystem.beans.Submission;
import com.abc.assignmentSystem.beans.UploadFile;
import com.abc.assignmentSystem.http.Response;
import com.abc.assignmentSystem.services.FileService;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/files")
public class FileController {
	@Autowired
	FileService fileService;
	///////////////////////////never use this controller, use it in submission controller
	@PostMapping
	public Response<String> submit(@RequestParam("file") String s, @RequestParam("file") MultipartFile file){
	
		
		UploadFile m_file = null;
		//from json string to object
		try {
			m_file = new ObjectMapper().readValue(s, UploadFile.class);
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
	
			fileService.save(m_file, file);
		}
		catch(Exception e) {
		return new Response<>(false, e.getMessage());
		}
		return new Response<String>(true);
	
	}
	
	@GetMapping("/{id}")
	  public ResponseEntity<Resource> fileDownload(@PathVariable int id) throws Exception {
		return fileService.fileDownload(id);
	}
}
