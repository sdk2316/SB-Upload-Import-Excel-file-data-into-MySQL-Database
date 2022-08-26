package com.durgesh.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.durgesh.model.Tutorial;

public interface IExcelService {
	
	List <Tutorial> getAllTutorials();
  
   // Page <Tutorial> findPaginated(int pageNo, int pageSize);
    Page <Tutorial> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection);

}
