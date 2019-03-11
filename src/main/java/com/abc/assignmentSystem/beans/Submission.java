package com.abc.assignmentSystem.beans;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "ABC_SUBMISSION")
public class Submission {
	@Id
	@SequenceGenerator(name="abc_submission_seq_gen", sequenceName="ABC_SUBMISSION_SEQ", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.AUTO, generator="abc_submission_seq_gen")
	private int id;
	
	@Column
	private int score;
	
	@Column
	private int studentId;
	
	@Column
	private int assignmentId;
	
	@Column
	private int graderId;
	
	@Column
	private int fileId;
	
	@Column
	private String typeRestrict;
	
	//no constructor
}
