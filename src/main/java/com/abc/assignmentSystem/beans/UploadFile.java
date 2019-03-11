package com.abc.assignmentSystem.beans;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import lombok.Data;

@Data
@Entity
@Table(name = "ABC_FILE")
public class UploadFile {

	@Id
	@SequenceGenerator(name="abc_file_seq_gen", sequenceName="ABC_FILE_SEQ", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.AUTO, generator="abc_file_seq_gen")
	private int id;
	@Column
	@JsonProperty(access = Access.WRITE_ONLY)
	private String path;
	@Column
	private Timestamp uploadTime;
	@Column
	private int uploaderId;
	@Column
	private String type;
	@Column
	private String name;
	//no constructor
	
}
