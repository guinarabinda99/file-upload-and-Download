package com.csm.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.csm.demo.Model.Doc;
import com.csm.demo.Repository.docRepository;

@Service
public class docServiceImpl implements docService{
	@Autowired
	private docRepository docRepo;

	@Override
	public Optional<Doc> getFile(Integer fileId) {
		// TODO Auto-generated method stub
		return docRepo.findById(fileId);
	}

	@Override
	public List<Doc> getFiles() {
		// TODO Auto-generated method stub
		return docRepo.findAll();
	}

}
