package com.durgesh.service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.durgesh.helper.ExcelHelper;
import com.durgesh.model.Tutorial;
import com.durgesh.repository.TutorialRepository;

@Service
public class ExcelServiceImpl implements IExcelService{
  @Autowired
  TutorialRepository repository;
  
  // add excel
  public void save(MultipartFile file) {
    try {
      List<Tutorial> tutorials = ExcelHelper.excelToTutorials(file.getInputStream());
      repository.saveAll(tutorials);
    } catch (IOException e) {
      throw new RuntimeException("fail to store excel data: " + e.getMessage());
    }
  }
  
  
  
  // update excel
  public void update(MultipartFile file) {
    try {
      List<Tutorial> tutorials = ExcelHelper.excelToTutorialsUpdate(file.getInputStream());
      repository.saveAll(tutorials);
    } catch (IOException e) {
      throw new RuntimeException("fail to store excel data: " + e.getMessage());
    }
  }
  
  
  
  // Delete excel
  public void bulkDelete(MultipartFile file) {
    try {
      List<Tutorial> tutorials = ExcelHelper.excelToTutorialsDelete(file.getInputStream());
      repository.deleteAll(tutorials);
    } catch (IOException e) {
      throw new RuntimeException("fail to store excel data: " + e.getMessage());
    }
  }
  
//Delete
public boolean deleteAll(List<Tutorial> ids) {

    try {
        repository.deleteAll(ids);
        return true;
    } catch (Exception e) {
        return false;
    }
}
  
 
  
 //get all
  public List<Tutorial> getAllTutorials() {
    return repository.findAll();
  }
  
//download excel
  public ByteArrayInputStream load() {
	    List<Tutorial> tutorials = repository.findAll();
	    ByteArrayInputStream in = ExcelHelper.tutorialsToExcel(tutorials);
	    return in;
	  }


// for pagination
//@Override
//public Page<Tutorial> findPaginated(int pageNo, int pageSize) {
//	Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
//	 return this.repository.findAll(pageable);
//}

//for pagination
@Override
public Page<Tutorial> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection) {
    Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() :
        Sort.by(sortField).descending();

    Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
    return this.repository.findAll(pageable);
}


}