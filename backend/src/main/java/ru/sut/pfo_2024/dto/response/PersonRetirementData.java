package ru.sut.pfo_2024.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import ru.sut.pfo_2024.dto.EarlyRetirementEnum;

import java.util.UUID;


/**
 * @author iegorov
 * @since 1.0.0
 */
@Data
@Builder
@AllArgsConstructor
public class PersonRetirementData {

    private String accountId;

    private Byte isRetirementEarly;
}
