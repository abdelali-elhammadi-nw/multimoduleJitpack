package com.nimbleways.springboilerplate.services.implementations;

import com.nimbleways.springboilerplate.exceptions.ImageNotFoundException;
import com.nimbleways.springboilerplate.exceptions.InvalidImageException;
import com.nimbleways.springboilerplate.services.IFileStorageService;
import org.jetbrains.annotations.NotNull;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class FileStorageService implements IFileStorageService {

    private final Path root;
    public  FileStorageService() throws IOException {
        this.root = Paths.get("./uploads");
        this.init();
    }
    @Override
    public void init() throws IOException {

        try {
            Files.createDirectories(root);
        } catch (IOException e) {
        }
    }

    @Override
    public String save(@NotNull MultipartFile file) {

        try {
            final String fileName = System.currentTimeMillis()+ "_" + file.getOriginalFilename();
            isImage(file.getContentType());
            Files.copy(file.getInputStream(), this.root.resolve(fileName) );
            return fileName;

        } catch (Exception e) {
            throw new InvalidImageException();
        }

    }

    @Override
    public List<String> saveAll(List<MultipartFile> files) {
        List<String> filesNames = new ArrayList<String>();
        for(MultipartFile file: files){
            filesNames.add( this.save(file) );
        }

        return filesNames;
    }

    @Override   
    public Resource load(String filename) {
        try {
            Path file = root.resolve(filename);
            Resource resource = new UrlResource(file.toUri());

            if (resource.isReadable()) {
                return resource;
            } else {
                throw new ImageNotFoundException();
            }
        } catch (Exception e) {
            throw new ImageNotFoundException();
        }
    }

    public void isImage(String fileExtention){
        List<String> validExtentions = Arrays.asList("image/png","image/jpg","image/jpeg");
        if(!validExtentions.contains(fileExtention)){
            throw new InvalidImageException();
        }

    }
}
