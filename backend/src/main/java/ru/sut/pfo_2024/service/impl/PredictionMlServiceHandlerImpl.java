package ru.sut.pfo_2024.service.impl;

import com.opencsv.exceptions.CsvException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import ru.sut.pfo_2024.dto.PersonRetirementDataToAnalyze;
import ru.sut.pfo_2024.service.CsvProcessorService;
import ru.sut.pfo_2024.service.FileHandlerService;
import ru.sut.pfo_2024.service.PredictionMlServiceHandler;

import java.io.File;
import java.io.IOException;
import java.util.List;


/**
 * @author iegorov
 * @since 1.0.0
 */
@Service("pfo.predictionMlServiceHandler")
public class PredictionMlServiceHandlerImpl implements PredictionMlServiceHandler {

    private String mlServiceUrl = "http://localhost:8071/api/predict";

    @Autowired
    private FileHandlerService fileHandlerService;

    @Autowired
    private CsvProcessorService csvProcessorService;

    @Override
    public List<PersonRetirementDataToAnalyze> getDataToAnalyze(File personalDataFile, File txnDataFile) throws IOException, CsvException {
        RestTemplate restTemplate = new RestTemplate();
        MultiValueMap<String, Object> request = new LinkedMultiValueMap<>();
        request.add("personalData", new FileSystemResource(personalDataFile));
        request.add("txnData", new FileSystemResource(txnDataFile));
        ResponseEntity<String> response = restTemplate.postForEntity(mlServiceUrl, request, String.class);
        // Получение CSV от ML сервиса и его обработка
        File receivedCsv = fileHandlerService.convertStringToFile(response.getBody());
        return csvProcessorService.getPersonRetirementData(receivedCsv);
    }
}
