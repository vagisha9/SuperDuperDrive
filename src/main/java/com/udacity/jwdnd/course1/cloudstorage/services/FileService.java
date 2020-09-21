package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import org.springframework.stereotype.Service;

import java.sql.Blob;
import java.util.List;

@Service
public class FileService {

    FileMapper fileMapper;

    public FileService(FileMapper fileMapper) {
        this.fileMapper = fileMapper;
    }

    public File getFileById(Long fileId ){
        return fileMapper.getFileById(fileId);
    }

    public List<File> getFiles(Integer userid ){
        return fileMapper.getFiles(userid);
    }

    public int deleteFile(Long fileId ){
        return fileMapper.deleteFile(fileId);
    }

    public int uploadFile(File file){
        return fileMapper.insert(file);
    }
}
