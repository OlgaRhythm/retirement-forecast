package ru.sut.pfo_2024.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;


/**
 * Сущность, которая приходит из ML сервиса для последующего анализа на бэкенде
 *
 * @author iegorov
 * @since 1.0.0
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PersonRetirementDataToAnalyze {

    // Уникальный идентификатор клиента
    private String accountId;

    // Переменная отвечает за то, выйдет ли человек на пенсию раньше
    private Byte isRetirementEarly;

    /* Сумма выплан, которую необходимо выплатить человеку за год. Это может быть
    как суммой */
    private BigDecimal yearlyPayment;
}
