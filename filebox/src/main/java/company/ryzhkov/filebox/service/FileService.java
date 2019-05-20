package company.ryzhkov.filebox.service;

import company.ryzhkov.filebox.entity.File;
import company.ryzhkov.filebox.entity.User;
import company.ryzhkov.filebox.repository.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class FileService {
    private FileRepository fileRepository;

    @Autowired
    public void setFileRepository(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    public File getById(Long id) {
        return fileRepository.findById(id).orElse(null);
    }

    public boolean delete(File file, User user) {
        fileRepository.delete(file);
        Path path = Paths.get("media", user.getDirectory(), file.getName());
        try {
            Files.delete(path);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
