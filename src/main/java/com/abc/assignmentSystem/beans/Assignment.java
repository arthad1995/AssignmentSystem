	package com.abc.assignmentSystem.beans;


import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "ABC_ASSIGNMENT")
public class Assignment {
	
	
	
@Id
@SequenceGenerator(name="abc_assignment_seq_gen", sequenceName="ABC_ASSIGNMENT_SEQ", allocationSize=1)
@GeneratedValue(strategy=GenerationType.AUTO, generator="abc_assignment_seq_gen")
private int id;

@Column
private String description;

@Column
private int posterId;

@Column
private Timestamp duedate;

@Column
private String tittle;


}
