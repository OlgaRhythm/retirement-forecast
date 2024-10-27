package ru.sut.pfo_2024.dto.response;

import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


/**
 * @author iegorov
 * @since 1.0.0
 */
@Data
public class PredictedData {

    // Затраты на выплаты людям, которые досрочно выйдут на пенсию в ближайшие два года
    private BigDecimal earlyRetirementCosts = BigDecimal.ZERO;

    // Общие затраты на все пенсионные выплаты за два года
    private BigDecimal overallRetirementCosts = BigDecimal.ZERO;

    // Предсказание для каждого клиента о досрочном выходе на пенсию
    private List<PersonRetirementData> predictedRetirementData = new ArrayList<>();

    public void addPersonRetirementData(PersonRetirementData personRetirementData) {
        predictedRetirementData.add(personRetirementData);
    }

    public void addToEarlyRetirementCosts(BigDecimal amountToAdd) {
        this.earlyRetirementCosts.add(amountToAdd);
    }

    public void addToOverallRetirementCosts(BigDecimal amountToAdd) {
        this.overallRetirementCosts.add(amountToAdd);
    }
}