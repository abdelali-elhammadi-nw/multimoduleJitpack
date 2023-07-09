package com.nimbleways.springboilerplate.services;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface IFileStorageService {

    public void init() throws IOException;

    public String save(MultipartFile file);

    public List<String> saveAll(List<MultipartFile> file);

    public Resource load(String filename);

}
