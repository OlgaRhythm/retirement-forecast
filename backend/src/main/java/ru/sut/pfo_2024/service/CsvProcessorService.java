package ru.sut.pfo_2024.service;

import com.opencsv.exceptions.CsvException;
import ru.sut.pfo_2024.dto.PersonRetirementDataToAnalyze;

import java.io.File;
import java.io.IOException;
import java.util.List;


/**
 * @author iegorov
 * @since 1.0.0
 */
public interface CsvProcessorService {

    // Преобразует csv файл с ML в сущность PersonRetirementDataToAnalyze
    List<PersonRetirementDataToAnalyze> getPersonRetirementData(File csvFile) throws IOException, CsvException;
}
