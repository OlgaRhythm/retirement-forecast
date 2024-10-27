package ru.sut.pfo_2024.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import ru.sut.pfo_2024.dto.response.PredictedData;


/**
 * Интерфейс PredictionController для обработки запросов на предсказание пенсионных данных.
 * <p>
 * Этот интерфейс описывает метод для получения предсказаний пенсионных данных на основе
 * предоставленных CSV файлов с личными данными и данными транзакций.
 * </p>
 *
 * @autor iegorov
 * @since 1.0.0
 */
public interface PredictionController {

    /**
     * Метод для предсказания пенсионных данных на основе CSV файлов.
     * <p>
     * Метод принимает два CSV файла (личные данные и данные транзакций), отправляет их на обработку
     * и возвращает предсказанные данные о пенсии в формате JSON.
     * </p>
     *
     * @param personalData CSV файл с личными данными.
     * @param txnData CSV файл с данными транзакций.
     * @return Ответ в формате ResponseEntity с объектом PredictedData, содержащим предсказанные данные о пенсии.
     */
    @PostMapping("predict")
    ResponseEntity<PredictedData> predictRetirementFromPersonalAndTxnData(
            @RequestParam("personalData") MultipartFile personalData,
            @RequestParam("txnData") MultipartFile txnData
    );
}
