package nl.novi.garageapi.service;

import nl.novi.garageapi.model.FileDB;
import nl.novi.garageapi.model.KassaMedewerker;
import nl.novi.garageapi.repository.FileDBRepository;
import org.antlr.v4.runtime.misc.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.stream.Stream;

@Service
public class FileStorageService {
    @Autowired
    private FileDBRepository fileDBRepository;


    public FileDB store(MultipartFile file, KassaMedewerker kassaMedewerker) throws IOException {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        FileDB FileDB = new FileDB(fileName, file.getContentType(),file.getBytes(), kassaMedewerker);

        return fileDBRepository.save(FileDB);
    }
    public FileDB getFile(String id) {
        return fileDBRepository.findById(id).get();
    }
    public Stream<FileDB> getAllFiles() {
        return fileDBRepository.findAll().stream();
    }
}