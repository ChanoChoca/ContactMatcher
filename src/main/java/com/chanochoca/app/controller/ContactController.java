package com.chanochoca.app.controller;

import com.chanochoca.app.service.ContactDuplicateService;
import com.chanochoca.app.entity.MatchResult;
import com.chanochoca.app.entity.models.Contact;
import com.chanochoca.app.service.CSVReaderService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@RestController
@RequestMapping("/contacts")
public class ContactController {

    private final ContactDuplicateService contactDuplicateService;
    private final CSVReaderService csvReaderService;

    public ContactController(ContactDuplicateService contactDuplicateService, CSVReaderService csvReaderService) {
        this.contactDuplicateService = contactDuplicateService;
        this.csvReaderService = csvReaderService;
    }

    @PostMapping("/upload-csv")
    public List<MatchResult> uploadCSVAndFindDuplicates(@RequestParam("file") MultipartFile file) throws IOException {
        // Guardar el archivo temporalmente
        Path tempFile = Files.createTempFile("contacts-", ".csv");
        file.transferTo(tempFile);

        // Leer contactos del CSV
        List<Contact> contacts = csvReaderService.readContactsFromCSV(tempFile);

        // Encontrar coincidencias
        return contactDuplicateService.findPotentialMatches(contacts);
    }
}
