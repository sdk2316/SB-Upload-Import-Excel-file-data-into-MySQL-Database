package com.durgesh.service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.durgesh.helper.ExcelHelper;
import com.durgesh.model.Tutorial;
import com.durgesh.repository.TutorialRepository;

@Service
public class ExcelService {
  @Autowired
  TutorialRepository repository;
  public void save(MultipartFile file) {
    try {
      List<Tutorial> tutorials = ExcelHelper.excelToTutorials(file.getInputStream());
      repository.saveAll(tutorials);
    } catch (IOException e) {
      throw new RuntimeException("fail to store excel data: " + e.getMessage());
    }
  }
  public List<Tutorial> getAllTutorials() {
    return repository.findAll();
  }
  
  public ByteArrayInputStream load() {
	    List<Tutorial> tutorials = repository.findAll();
	    ByteArrayInputStream in = ExcelHelper.tutorialsToExcel(tutorials);
	    return in;
	  }
}