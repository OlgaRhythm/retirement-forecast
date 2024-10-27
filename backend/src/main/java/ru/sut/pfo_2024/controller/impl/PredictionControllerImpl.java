package ru.sut.pfo_2024.controller.impl;

import com.opencsv.exceptions.CsvException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import ru.sut.pfo_2024.controller.PredictionController;
import ru.sut.pfo_2024.dto.PersonRetirementDataToAnalyze;
import ru.sut.pfo_2024.dto.response.PersonRetirementData;
import ru.sut.pfo_2024.dto.response.PredictedData;
import ru.sut.pfo_2024.service.CsvProcessorService;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;


/**
 * @author iegorov
 * @since 1.0.0
 */
@RestController
@RequestMapping("/api")
@CrossOrigin
public class PredictionControllerImpl implements PredictionController {

//    @Value("${ml.early.retirement.prediction.service.url}")
    private String mlServiceUrl = "http://localhost:8071/api/predict";

    @Autowired
    private CsvProcessorService CsvProcessorService;

    @Override
    @PostMapping("predict")
    public ResponseEntity<PredictedData> predictRetirementFromPersonalAndTxnData(
            @RequestParam("personalData") MultipartFile personalData,
            @RequestParam("txnData") MultipartFile txnData
    ) {
        {
            try {
                // Если хотя бы один из файлов пустой, то возвращаем "Bad Request"
                if (personalData.isEmpty() || txnData.isEmpty()) {
                    return ResponseEntity.badRequest().body(null);
                }

                // Конвертация MultipartFile в File
                File personalDataFile = convertMultipartFileToFile(personalData);
                File txnDataFile = convertMultipartFileToFile(txnData);

                // Отправка файлов в ML сервис
                RestTemplate restTemplate = new RestTemplate();
                MultiValueMap<String, Object> request = new LinkedMultiValueMap<>();
                request.add("personalData", new FileSystemResource(personalDataFile));
                request.add("txnData", new FileSystemResource(txnDataFile));

                ResponseEntity<String> response = restTemplate.postForEntity(mlServiceUrl, request, String.class);

                // Получение CSV от ML сервиса и его обработка
                File receivedCsv = convertStringToFile(response.getBody());
                List<PersonRetirementDataToAnalyze> analyzedData = CsvProcessorService.getPersonRetirementData(receivedCsv);

                PredictedData predictedData = new PredictedData();

                for (PersonRetirementDataToAnalyze personRetirementDataToAnalyze : analyzedData) {
                    if (personRetirementDataToAnalyze.getIsRetirementEarly() == 1) {
                        predictedData.addToEarlyRetirementCosts(personRetirementDataToAnalyze.getYearlyPayment().multiply(new BigDecimal(2)));
                    }
                    predictedData.addToOverallRetirementCosts(personRetirementDataToAnalyze.getYearlyPayment().multiply(new BigDecimal(2)));
                    predictedData.addPersonRetirementData(
                            PersonRetirementData.builder()
                                    .accountId(personRetirementDataToAnalyze.getAccountId())
                                    .isRetirementEarly(personRetirementDataToAnalyze.getIsRetirementEarly())
                                    .build()
                    );
                }

                return ResponseEntity.ok(predictedData);
            } catch (IOException | CsvException e) {
                return ResponseEntity.status(500).body(null);
            }
        }
    }

        private File convertMultipartFileToFile(MultipartFile file) throws IOException {
            File convFile = new File(System.getProperty("java.io.tmpdir") + "/" + file.getOriginalFilename());
            file.transferTo(convFile);
            return convFile;
        }

        private File convertStringToFile (String csvContent) throws IOException {
            File tempFile = File.createTempFile("ml_output", ".csv");
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {
                writer.write(csvContent);
            }
            return tempFile;
        }
    }