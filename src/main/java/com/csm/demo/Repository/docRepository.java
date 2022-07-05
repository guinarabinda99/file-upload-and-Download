package com.csm.demo.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.csm.demo.Model.Doc;

public interface docRepository extends JpaRepository<Doc, Integer>{

}
