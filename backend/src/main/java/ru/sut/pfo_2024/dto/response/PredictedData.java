package ru.sut.pfo_2024.dto.response;

import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


/**
 *
 *
 * @author iegorov
 * @since 1.0.0
 */
@Data
public class PredictedData {

    private BigDecimal earlyRetirementCosts = BigDecimal.ZERO;

    private BigDecimal overallRetirementCosts = BigDecimal.ZERO;

    private List<PersonRetirementData> predictedRetirementData = new ArrayList<>();

    // Early retired people sum by overall or montly payment
    // earlyRetirementCosts plus in-time retired people

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

//["accnt_id", "erly_pnsn_flg", "yearly_pension_payment"]
