package com.example.back_end_fams.service;

import com.example.back_end_fams.config.FileStorageProperties;
import com.example.back_end_fams.exception.FileStorageException;
import com.example.back_end_fams.exception.MyFileNotFoundException;
import com.example.back_end_fams.model.entity.FileMaterial;
import com.example.back_end_fams.model.entity.TrainingContent;
import com.example.back_end_fams.model.entity.TrainingContentFileMaterial;
import com.example.back_end_fams.model.entity.User;
import com.example.back_end_fams.repository.FileMaterialRepository;
import com.example.back_end_fams.repository.TrainingContentFileMaterialRepository;
import com.example.back_end_fams.repository.UserPermissionRepository;
import com.example.back_end_fams.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
public class FileMaterialService {
    @Autowired
    FileMaterialRepository fileMaterialRepository;

    @Autowired
    TrainingContentFileMaterialRepository trainingContentFileMaterialRepository;
    private final Path fileStorageLocation;

    public TrainingContentFileMaterial saveFile(TrainingContentFileMaterial trainingContentFileMaterial){
        return trainingContentFileMaterialRepository.save(trainingContentFileMaterial);
    }



    public String storeFile(MultipartFile file) {
        // Normalize file name
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));

        try {
            // Check if the file's name contains invalid characters
            if(fileName.contains("..")) {
                throw new RuntimeException("Sorry! Filename contains invalid path sequence " + fileName);
            }

            // Copy file to the target location (Replacing existing file with the same name)
            File folder = new File(String.valueOf(this.fileStorageLocation));
            for ( File e : Objects.requireNonNull(folder.listFiles())){
                if (e.getName().equals(fileName)){
                    String suffix = fileName.substring(fileName.lastIndexOf("."));
                    String name = fileName.substring(0, fileName.lastIndexOf("."));
                    name += System.currentTimeMillis();
                    fileName = name + suffix;
                    break;
                }
            }
            Path targetLocation = this.fileStorageLocation.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            return fileName;
        } catch (IOException ex) {
            throw new RuntimeException("Could not store file " + fileName + ". Please try again!", ex);
        }
    }


    public Resource loadFileAsResource(String fileName) {
        try {
            Path filePath = fileStorageLocation.resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if(resource.exists()) {
                return resource;
            } else {
                throw new MyFileNotFoundException("File not found " + fileName);
            }
        } catch (MalformedURLException ex) {
            throw new MyFileNotFoundException("File not found " + fileName, ex);
        }
    }

    public FileMaterial uploadFile(FileMaterial fileMaterial){
        return fileMaterialRepository.save(fileMaterial);
    }

    public FileMaterialService() {
        this.fileStorageLocation = Paths.get("D:/Thuc_tap/DoAn/sourceCode/upload")
                .toAbsolutePath().normalize();
        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception ex) {
            throw new RuntimeException("Could not create the directory where the uploaded files will be stored.", ex);
        }
    }

    public List<FileMaterial> getAllMaterialTrainingContent(){
        List<FileMaterial> fileMaterialList = new ArrayList<>();
        List<TrainingContentFileMaterial> trainingContentFileMaterialList = trainingContentFileMaterialRepository.findAll();
        trainingContentFileMaterialList.forEach(trainingContentFileMaterial -> {
            fileMaterialList.add(fileMaterialRepository.findById(trainingContentFileMaterial.getFileMaterial().getFileId()).orElse(null));
        });
        return fileMaterialList;
    }
    public List<FileMaterial> findMaterialByContentId(int contentId){
        List<FileMaterial> fileMaterialList = new ArrayList<>();
        List<TrainingContentFileMaterial> trainingContentFileMaterialList = trainingContentFileMaterialRepository.findByTrainingContentId(contentId).orElse(null);
        trainingContentFileMaterialList.forEach(trainingContentFileMaterial -> {
            fileMaterialList.add(fileMaterialRepository.findById(trainingContentFileMaterial.getFileMaterial().getFileId()).orElse(null));
        });
        return fileMaterialList;
    }

    public String deleteMaterialTrainingContentByFileIdAndContentId(int fileId, int contentId){
        try{
            trainingContentFileMaterialRepository.deleteByFileAndContent(fileId, contentId);
            return "Successfully deleted";
        } catch (Exception ex){
            return "Failed";
        }
    }
}
