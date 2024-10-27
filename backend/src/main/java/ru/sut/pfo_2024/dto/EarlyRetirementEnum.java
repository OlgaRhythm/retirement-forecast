package ru.sut.pfo_2024.dto;


/**
 * Перечисление с возможыми состонями выхода на пенсию.
 * Если будет необходимость можно добавить новую логику, то можно
 * создать новые значения перечисления, например, NOT_RETIRING_EARLY разделить
 * на RETIRED_LATE и RETIRED_IN_TIME и тд
 *
 * @author iegorov
 * @since 1.0.0
 */
public enum EarlyRetirementEnum {

    RETIRED_EARLY(1L),

    NOT_RETIRING_EARLY(0L);

    EarlyRetirementEnum(Long retirementValueRepresentation) {
    }
}