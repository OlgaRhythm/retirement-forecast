package ru.sut.pfo_2024.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;


/**
 * Интерфейс для обработки файлов в приложении.
 * <p>
 * Интерфейс FileHandlerService предоставляет методы для преобразования файлов
 * из одного формата в другой.
 * </p>
 *
 * @autor iegorov
 * @since 1.0.0
 */
public interface FileHandlerService {

    /**
     * Преобразует MultipartFile в объект File.
     * <p>
     * Метод принимает MultipartFile, сохраняет его как временный файл и возвращает объект File.
     * </p>
     *
     * @param file MultipartFile, который нужно преобразовать.
     * @return Объект File, представляющий сохраненный файл.
     * @throws IOException если возникает ошибка при сохранении файла.
     */
    File convertMultipartFileToFile(MultipartFile file) throws IOException;

    /**
     * Преобразует строку CSV-содержимого в объект File.
     * <p>
     * Метод принимает строку с содержимым CSV, создает временный файл и записывает в него данное содержимое.
     * </p>
     *
     * @param csvContent Строка с содержимым CSV.
     * @return Объект File, представляющий сохраненный файл.
     * @throws IOException если возникает ошибка при записи файла.
     */
    File convertStringToFile(String csvContent) throws IOException;
}
