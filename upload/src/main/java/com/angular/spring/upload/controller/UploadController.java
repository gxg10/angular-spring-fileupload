package com.angular.spring.upload.controller;

import com.angular.spring.upload.storage.StorageService;
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

@Controller
@CrossOrigin(origins = "*")
public class UploadController {

    @Autowired
    StorageService storageService;

    List<String> files = new ArrayList<String>();

    @PostMapping("/post")
    public ResponseEntity<String> handleFileUpload(@RequestParam("file")MultipartFile file) {
        String message = "";
        try {
            storageService.store(file);
            files.add(file.getOriginalFilename());

            message = "Your successfully upload" + file.getOriginalFilename();

            return ResponseEntity.status(HttpStatus.OK).body(message);
        } catch (Exception e) {
            message = "Fail to upload" + file.getOriginalFilename();
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(message);
        }
    }

    @GetMapping("/getallfiles")
    public ResponseEntity<List<String>> getListFiles(Model model) {
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
