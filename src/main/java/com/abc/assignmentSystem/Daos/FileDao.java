package com.abc.assignmentSystem.Daos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.abc.assignmentSystem.beans.UploadFile;

public interface FileDao extends JpaRepository<UploadFile, Integer>{

}
