package ru.sut.pfo_2024.service;

import com.opencsv.exceptions.CsvException;
import ru.sut.pfo_2024.dto.PersonRetirementDataToAnalyze;

import java.io.File;
import java.io.IOException;
import java.util.List;


/**
 * Интерфейс для обработки CSV файлов и преобразования их в объекты PersonRetirementDataToAnalyze.
 * <p>
 * Интерфейс описывает метод для чтения данных из CSV файла и преобразования их в список объектов PersonRetirementDataToAnalyze.
 * </p>
 *
 * @autor iegorov
 * @since 1.0.0
 */
public interface CsvProcessorService {

    /**
     * Преобразует CSV файл в сущности PersonRetirementDataToAnalyze.
     *
     * @param csvFile CSV файл для обработки.
     * @return Список объектов PersonRetirementDataToAnalyze, полученных из CSV файла.
     * @throws IOException если возникает ошибка при чтении файла.
     * @throws CsvException если возникает ошибка при обработке CSV данных.
     */
    List<PersonRetirementDataToAnalyze> getPersonRetirementData(File csvFile) throws IOException, CsvException;
}