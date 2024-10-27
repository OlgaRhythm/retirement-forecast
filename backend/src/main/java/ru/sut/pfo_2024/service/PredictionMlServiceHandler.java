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
public interface PredictionMlServiceHandler {

    List<PersonRetirementDataToAnalyze> getDataToAnalyze(File personalDataFile, File txnDataFile) throws IOException, CsvException;
}
