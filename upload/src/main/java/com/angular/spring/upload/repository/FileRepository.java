package com.angular.spring.upload.repository;

import com.angular.spring.upload.model.FileModel;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;

@Transactional
public interface FileRepository extends JpaRepository<FileModel, Long> {
    public FileModel findByName(String name);
}
