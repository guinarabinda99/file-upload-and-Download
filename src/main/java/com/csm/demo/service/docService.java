package com.csm.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.web.multipart.MultipartFile;

import com.csm.demo.Model.Doc;

public interface docService {
	
	public Optional<Doc> getFile(Integer fileId);
	public List<Doc> getFiles();

}
