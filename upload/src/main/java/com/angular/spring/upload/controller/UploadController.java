package com.angular.spring.upload.controller;

import com.angular.spring.upload.model.FileModel;
import com.angular.spring.upload.model.View;
import com.angular.spring.upload.repository.FileRepository;
import com.angular.spring.upload.storage.StorageService;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

//@Controller
@RestController
@CrossOrigin(origins = "*")
public class UploadController {

    @Autowired
    StorageService storageService;

    @Autowired
    FileRepository fileRepository;

    List<String> files = new ArrayList<String>();

    @PostMapping("/post")
    public String uploadMultipartFile(@RequestParam("file") MultipartFile file) {
        try {
            FileModel fileModel = new FileModel(file.getOriginalFilename(), file.getContentType(),
                    file.getBytes());
            storageService.store(file);
            files.add(file.getOriginalFilename());
            fileRepository.save(fileModel);
            return "file uploaded successfully " + file.getOriginalFilename();
        }catch (Exception e) {
            return "fail ! maybe you had uploaded the file before or the file";
        }
    }

    @JsonView(View.FileInfo.class)
    @GetMapping("/getallfiles")
    public ResponseEntity<List<String>> getListFiles() {
//        return fileRepository.findAll();
        List<String> filesNames = files
                .stream().map(fileName -> MvcUriComponentsBuilder
                .fromMethodName(UploadController.class, "getFile", fileName)
                .build().toString()).collect(Collectors.toList());

        return ResponseEntity.ok().body(filesNames);
    }

    @GetMapping("/files/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> getFile(@PathVariable String filename) {
        Resource file = storageService.loadFile(filename);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachement; filename=\""+
                        file.getFilename()+ "\"").body(file);
    }
}
