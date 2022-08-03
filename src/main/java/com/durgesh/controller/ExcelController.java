package com.durgesh.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.durgesh.helper.ExcelHelper;
import com.durgesh.model.Tutorial;
import com.durgesh.response.ResponseMessage;
import com.durgesh.service.ExcelService;


@RestController
@RequestMapping("/api/excel")
public class ExcelController {
  @Autowired
  ExcelService fileService;
  
  //http://localhost:8081/api/excel/upload
  //select count(title) from tutorials;
  @PostMapping(value="/upload")
  public ResponseEntity<ResponseMessage> uploadFile(@RequestParam("file") MultipartFile file) {
    String message = "";
    if (ExcelHelper.hasExcelFormat(file)) {
      try {
        fileService.save(file);
        message = " file record uploaded successfully: " + file.getOriginalFilename();
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
      } catch (Exception e) {
        message = "Could not upload the file: " + file.getOriginalFilename() + "!";
        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
      }
    }
    message = "Please upload an excel file!";
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(message));
  }
  
  //update
  @PutMapping(value="/update")
  public ResponseEntity<ResponseMessage> updateFile(@RequestParam("file") MultipartFile file) {
    String message = "";
    if (ExcelHelper.hasExcelFormat(file)) {
      try {
        fileService.update(file);
        message = " file record updated successfully: " + file.getOriginalFilename();
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
      } catch (Exception e) {
        message = "Could not upload the file: " + file.getOriginalFilename() + "!";
        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
      }
    }
    message = "Please upload an excel file!";
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(message));
  }
  
  
  // Delete

  @DeleteMapping("/bulk/delete")
  public String deleteTutorial(@RequestBody List<Tutorial> ids) {
	  if(!ids.isEmpty()) {
          if( fileService.deleteAll(ids)) {
              return "Deleted the Tutorial.";
          } else {
              return "Cannot delete the Tutorial.";
          }
      }
      return "The request should contain a list of Tutorial to be deleted.";
    
  }
  
  
  //http://localhost:8081/api/excel/download
  
  @GetMapping("/download")
  public ResponseEntity<Resource> getFile() {
    String filename = "tutorials.xlsx";
    InputStreamResource file = new InputStreamResource(fileService.load());
    return ResponseEntity.ok()
        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
        .contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
        .body(file);
  }
  
  //http://localhost:8081/api/excel/tutorials
  @GetMapping("/tutorials")
  public ResponseEntity<List<Tutorial>> getAllTutorials() {
    try {
      List<Tutorial> tutorials = fileService.getAllTutorials();
      if (tutorials.isEmpty()) {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
      }
      return new ResponseEntity<>(tutorials, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
  
  
  
  
//Bulk Delete
  @DeleteMapping(value="/bulkDelete")
  public ResponseEntity<ResponseMessage> bulkDelete(@RequestParam("file") MultipartFile file) {
    String message = "";
    if (ExcelHelper.hasExcelFormat(file)) {
      try {
        fileService.bulkDelete(file);
        message = " file record deleted successfully: " + file.getOriginalFilename();
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
      } catch (Exception e) {
        message = "Could not upload the file: " + file.getOriginalFilename() + "!";
        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
      }
    }
    message = "Please upload an excel file!";
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(message));
  }
}