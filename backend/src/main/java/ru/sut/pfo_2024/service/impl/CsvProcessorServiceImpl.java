package ru.sut.pfo_2024.service.impl;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import org.springframework.stereotype.Service;
import ru.sut.pfo_2024.dto.PersonRetirementDataToAnalyze;
import ru.sut.pfo_2024.service.CsvProcessorService;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


/**
 * @autor iegorov
 * @since 1.0.0
 */
@Service("pfo.csvProcessorServiceImpl")
public class CsvProcessorServiceImpl implements CsvProcessorService {

    public List<PersonRetirementDataToAnalyze> getPersonRetirementData(File csvFile) throws IOException, CsvException {
        List<PersonRetirementDataToAnalyze> analyzedData = new ArrayList<>();

        try (CSVReader csvReader = new CSVReader(new FileReader(csvFile))) {

            List<String[]> records = csvReader.readAll();

            // Пропускаем первую строку, которая является заголовком
            records.remove(0);

            for (String[] record : records) {
                analyzedData.add(
                        PersonRetirementDataToAnalyze.builder()
                                .accountId(record[0])
                                .isRetirementEarly(Byte.valueOf(record[1]))
                                .yearlyPayment(new BigDecimal(record[2]))
                                .build()
                );
            }
        }

        return analyzedData;
    }
}