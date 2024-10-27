package ru.sut.pfo_2024.service;

import com.opencsv.exceptions.CsvException;
import ru.sut.pfo_2024.dto.PersonRetirementDataToAnalyze;

import java.io.File;
import java.io.IOException;
import java.util.List;


/**
 * @author iegorov
 * @since %CURRENT_VERSION%
 */
public interface CsvProcessorService {

    List<PersonRetirementDataToAnalyze> getPersonRetirementData(File csvFile) throws IOException, CsvException;
}
