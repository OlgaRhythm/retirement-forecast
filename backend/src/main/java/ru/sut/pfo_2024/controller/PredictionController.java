package ru.sut.pfo_2024.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import ru.sut.pfo_2024.dto.response.PredictedData;


/**
 * @author iegorov
 * @since %CURRENT_VERSION%
 */
public interface PredictionController {

    @PostMapping("predict")
    ResponseEntity<PredictedData> predictRetirementFromPersonalAndTxnData(
            @RequestParam("personalData") MultipartFile personalData,
            @RequestParam("txnData") MultipartFile txnData
    );
}
