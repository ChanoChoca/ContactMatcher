package com.chanochoca.app.service;

import com.chanochoca.app.entity.models.Contact;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

@Service
public class CSVReaderService {

    public List<Contact> readContactsFromCSV(Path filePath) throws IOException {
        List<Contact> contacts = new ArrayList<>();

        try (CSVParser parser = CSVFormat.DEFAULT
                .withHeader("contactID", "name", "name1", "email", "postalZip", "address")
                .withSkipHeaderRecord()
                .parse(Files.newBufferedReader(filePath))) {

            for (CSVRecord record : parser) {
                Contact contact = new Contact(
                        record.get("contactID"),
                        record.get("name"),
                        record.get("name1"),
                        record.get("email"),
                        record.get("postalZip"),
                        record.get("address")
                );
                contacts.add(contact);
            }
        }
        return contacts;
    }
}
