package ru.sut.pfo_2024.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;


/**
 * @author iegorov
 * @since 1.0.0
 */
@Data
@Builder
@AllArgsConstructor
public class PersonRetirementData {

    // Уникальный идентификатор клиента
    private String accountId;

    // Флаг определяющий вероятность досрочного выхода на пенсию
    private Byte isRetirementEarly;
}
