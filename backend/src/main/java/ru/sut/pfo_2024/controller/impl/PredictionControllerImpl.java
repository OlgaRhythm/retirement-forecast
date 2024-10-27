package ru.sut.pfo_2024.controller.impl;

import com.opencsv.exceptions.CsvException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.sut.pfo_2024.controller.PredictionController;
import ru.sut.pfo_2024.dto.PersonRetirementDataToAnalyze;
import ru.sut.pfo_2024.dto.response.PersonRetirementData;
import ru.sut.pfo_2024.dto.response.PredictedData;
import ru.sut.pfo_2024.service.CsvProcessorService;
import ru.sut.pfo_2024.service.FileHandlerService;
import ru.sut.pfo_2024.service.PredictionMlServiceHandler;

import java.io.File;
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

    @Autowired
    private CsvProcessorService CsvProcessorService;

    @Autowired
    private FileHandlerService fileHandlerService;

    @Autowired
    private PredictionMlServiceHandler predictionMlServiceHandler;

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
                File personalDataFile = fileHandlerService.convertMultipartFileToFile(personalData);
                File txnDataFile = fileHandlerService.convertMultipartFileToFile(txnData);

                // Отправка файлов в ML сервис
                List<PersonRetirementDataToAnalyze> analyzedData = predictionMlServiceHandler.getDataToAnalyze(personalDataFile, txnDataFile);

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
}