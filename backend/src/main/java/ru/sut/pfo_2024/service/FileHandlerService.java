package ru.sut.pfo_2024.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;


/**
 * @author iegorov
 * @since 1.0.0
 */
public interface FileHandlerService {

    File convertMultipartFileToFile(MultipartFile file) throws IOException;

    File convertStringToFile(String csvContent) throws IOException;
}
