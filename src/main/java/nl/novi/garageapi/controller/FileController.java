package nl.novi.garageapi.controller;


import nl.novi.garageapi.message.ResponseFile;
import nl.novi.garageapi.message.ResponseMessage;
import nl.novi.garageapi.model.FileDB;
import nl.novi.garageapi.model.KassaMedewerker;
import nl.novi.garageapi.service.FileStorageService;
import nl.novi.garageapi.service.KassaMedewerkerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;
import java.util.stream.Collectors;

@RestController

public class FileController {
    @Autowired

    private FileStorageService storageService;
    @Autowired
    private KassaMedewerkerService kassaMedewerkerService;


    @PostMapping(value = "/upload")
    public ResponseEntity<ResponseMessage> uploadFile(@RequestPart(value = "file", required = false) MultipartFile file,@RequestParam Long kassamedewerkerId) {
        String message = "";
        try {

            KassaMedewerker kassaMedewerker = kassaMedewerkerService.getKassamedewerkerById(kassamedewerkerId);

            if (kassaMedewerker == null) {
                message = "KassaMedewerker not found";
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(message));
            }

            storageService.store(file, kassaMedewerker);

            message = "Uploaded the file successfully: " + file.getOriginalFilename();
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
        } catch (Exception e) {
            message = "Could not upload the file: " + file.getOriginalFilename() + "!";
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
        }
    }

    @GetMapping("/files")
    public ResponseEntity<List<ResponseFile>> getListFiles() {
        List<ResponseFile> files = storageService.getAllFiles().map(dbFile -> {
            String fileDownloadUri = ServletUriComponentsBuilder
                    .fromCurrentContextPath()
                    .path("/files/")
                    .path(dbFile.getId())
                    .toUriString();

            return new ResponseFile(
                    dbFile.getName(),
                    fileDownloadUri,
                    dbFile.getType(),
                    dbFile.getData().length);
        }).collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(files);
    }
    @GetMapping("/files/{id}")
    public ResponseEntity<byte[]> getFile(@PathVariable String id) {
        FileDB fileDB = storageService.getFile(id);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileDB.getName() + "\"")
                .body(fileDB.getData());
    }


}