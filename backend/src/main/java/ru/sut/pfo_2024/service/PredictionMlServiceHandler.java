package ru.sut.pfo_2024.service;

import com.opencsv.exceptions.CsvException;
import ru.sut.pfo_2024.dto.PersonRetirementDataToAnalyze;

import java.io.File;
import java.io.IOException;
import java.util.List;


/**
 * Интерфейс для обработки данных от ML сервиса для анализа пенсионных данных.
 * <p>
 * Этот интерфейс предоставляет метод для получения данных для анализа путем отправки файлов на ML сервис и обработки
 * полученного ответа.
 * </p>
 *
 * @autor iegorov
 * @since 1.0.0
 */
public interface PredictionMlServiceHandler {

    /**
     * Интерфейс для обработки данных от ML сервиса для анализа пенсионных данных.
     * <p>
     * Этот интерфейс предоставляет метод для получения данных для анализа путем отправки файлов на ML сервис и обработки
     * полученного ответа.
     * </p>
     *
     * @autor iegorov
     * @since 1.0.0
     */
    List<PersonRetirementDataToAnalyze> getDataToAnalyze(File personalDataFile, File txnDataFile) throws IOException, CsvException;
}
