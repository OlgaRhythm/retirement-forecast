package ru.sut.pfo_2024.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.sut.pfo_2024.service.FileHandlerService;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;


/**
 * @author iegorov
 * @since 1.0.0
 */
@Service("pfo.fileHandlerService")
public class FileHandlerServiceImpl implements FileHandlerService {

    @Override
    public File convertMultipartFileToFile(MultipartFile file) throws IOException {
        File convFile = new File(System.getProperty("java.io.tmpdir") + "/" + file.getOriginalFilename());
        file.transferTo(convFile);
        return convFile;
    }

    @Override
    public File convertStringToFile(String csvContent) throws IOException {
        File tempFile = File.createTempFile("ml_output", ".csv");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {
            writer.write(csvContent);
        }
        return tempFile;
    }
}
