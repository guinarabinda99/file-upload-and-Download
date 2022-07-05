package com.csm.demo.Model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

@Entity
@Table(name = "doc")
public class Doc {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "doc_Id")
	private int doc;
	@Column(name = "doc_name")
	private String name;
	@Column(name = "doc_Type")
	private String docType;
	
	@Lob
	private byte[] data;

	public Doc() {
		
	}

	public Doc(String name, String docType, byte[] data) {
		this.name = name;
		this.docType = docType;
		this.data = data;
	}

	public int getDoc() {
		return doc;
	}

	public void setDoc(int doc) {
		this.doc = doc;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDocType() {
		return docType;
	}

	public void setDocType(String docType) {
		this.docType = docType;
	}

	public byte[] getData() {
		return data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}

	

}
