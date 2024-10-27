package ru.sut.pfo_2024.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;


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

    // Флаг, который определяет выйдет ли человек на пенсию раньше или нет
    private Byte isRetirementEarly;

    // Сумма выплан, которую необходимо выплатить человеку за год. Это может быть
    // как суммой ежемесяный платежей, так и единовременной выплатой. Логика
    // заполнения данного поля определена в модуле ML
    private BigDecimal yearlyPayment;
}