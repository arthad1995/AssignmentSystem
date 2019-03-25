package com.abc.assignmentSystem.services;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;


import com.google.common.hash.Hashing;

import lombok.var;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.abc.assignmentSystem.Daos.FileDao;
import com.abc.assignmentSystem.beans.UploadFile;
import com.abc.assignmentSystem.http.Response;

@Service
public class FileService {
	@Autowired
	private FileDao fileDao;
	
	public Response<Integer> save(UploadFile f, MultipartFile file) throws IllegalStateException, IOException,Exception{
		

		String hash = Hashing.sha256()
		        .hashString(file.getOriginalFilename(), StandardCharsets.UTF_8).toString();
	    Timestamp ts = new Timestamp(System.currentTimeMillis());
	    long timestamp = ((ts.getTime()) / 1000) * 1000;
	    System.out.println(file.getName());
	    System.out.println(file.getContentType());
	    System.out.println(file.getOriginalFilename());
	   
	    String fileName = hash + "." + timestamp + "." + f.getUploaderId();
	  
	    File fileToSave = new File("C:/Users/Terry/Desktop/uploads", fileName);
	    System.out.println(fileToSave.getAbsolutePath());
	    if (!fileToSave.createNewFile()) {
	      // should delete image and retry
	      throw new Exception("Could not save file");
	    }
	 
	
	    file.transferTo(fileToSave);

	    String path = "C:/Users/Terry/Desktop/uploads/" + fileName;
	    f.setName(FilenameUtils.removeExtension(file.getOriginalFilename()));
	    f.setPath(path);
	    f.setType(FilenameUtils.getExtension(file.getOriginalFilename()));
	    f.setUploadTime(new Timestamp(timestamp));
	    
	    return new Response<Integer>(true, fileDao.save(f).getId()) ;
	}
	
	

	  public ResponseEntity<Resource> fileDownload( int id) throws Exception {
		  UploadFile fileToFind ;
		  System.out.println(id);
	    Optional<UploadFile> op= fileDao.findById(id);
		
	    if (!op.isPresent()) {
	    
	      return null;
	    }
	    fileToFind = op.get();
	    String originalFileName = fileToFind.getName() + "." + fileToFind.getType();

	    HttpHeaders headers = new HttpHeaders();
	    headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + originalFileName);
	    Path path = Paths.get(fileToFind.getPath());
	    File file = new File(fileToFind.getPath());
	    ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(path));
	 

	    return ResponseEntity.ok()
	        .headers(headers)
	        .contentLength(file.length())
	        .contentType(MediaType.parseMediaType("application/octet-stream")).body(resource);
	  }



	public ResponseEntity<Resource> zipDownload(List<Integer> list, int aid) throws IOException {
		if(list.size()==0)
			return null;
		HashSet<String> nameSet = new HashSet<>();
		String fileName = "C:/Users/Terry/Desktop/uploads/zip/" +"Assignment " +aid +".zip";
        FileOutputStream fos = new FileOutputStream(fileName);
        ZipOutputStream zipOut = new ZipOutputStream(fos);
		
		for(var x : list) {
			int count = 1;
			 UploadFile fileToFind =null;
		    Optional<UploadFile> op= fileDao.findById(x);
			
		    if (!op.isPresent()) {
		    
		     break;
		    }
		    fileToFind = op.get();
		String srcFile= fileToFind.getPath();
		
		String originalName = fileToFind.getName() +  "." + fileToFind.getType();
		if(!nameSet.add(originalName)) {
			 originalName = fileToFind.getName() + count+ "." + fileToFind.getType();
			 nameSet.add(originalName);
		}
		
		String[] temp = fileToFind.getPath().split("/");
		String realName = temp[temp.length-1];
		    

	            
	            Path source = Paths.get(srcFile);
	            Files.move(source, source.resolveSibling(originalName));
	            
	            

	            File fileToZip  = new File("C:/Users/Terry/Desktop/uploads/"+originalName);
	            FileInputStream fis = new FileInputStream(fileToZip);
	            ZipEntry zipEntry = new ZipEntry(fileToZip.getName());
	            zipOut.putNextEntry(zipEntry);
	 
	            byte[] bytes = new byte[1024];	
	            int length;
	            while((length = fis.read(bytes)) >= 0) {
	                zipOut.write(bytes, 0, length);
	            }
	            fis.close();
	            
	                source = Paths.get("C:/Users/Terry/Desktop/uploads/" +originalName);
	            Files.move(source, source.resolveSibling(realName));
	        }
	        zipOut.close();
	        fos.close();
	    
	        
	       
	        String name = "Assignment" +aid +".zip";
		    HttpHeaders headers = new HttpHeaders();
		    headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + name);
		    Path path = Paths.get(fileName);
		    File file = new File(fileName);
		    ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(path));
		 
		    
		    
		    return ResponseEntity.ok()
		        .headers(headers)
		        .contentLength(file.length())
		        .contentType(MediaType.parseMediaType("application/octet-stream")).body(resource);

	}
	
	
	public Response<List<UploadFile>> getFileDetail(List<Integer> list){
		List<UploadFile> l = new ArrayList<>();
		try {
		
		for(var x :list) {
			l.add(fileDao.findById(x).get());
		}
		}
		catch(Exception e) {
			return new Response<List<UploadFile>>(false, l);
		}
		return new Response<List<UploadFile>>(true, l);
	}
	
	public Response<UploadFile> getSubmissionFileDetail(int id){
		UploadFile f;
		try {
		
		
			f =fileDao.findById(id).get();
		
		}
		catch(Exception e) {
			return new Response<UploadFile>(false, null);
		}
		return new Response<UploadFile>(true, f);
	}
	
}
