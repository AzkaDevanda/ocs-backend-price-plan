package com.ocs.portal.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.cglib.core.Local;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Getter
@Setter
public class BallTriggerDto implements Serializable {
//@NotNull(message = "effDate cannot be null")
//@Schema(example = "2025-04-17")
//private LocalDate effDate;
//
//@Schema(example = "2025-04-17")
//private LocalDate expDate;
//
//@NotNull(message = "List balanceType cannot be null")
//private List<AcctRes> balanceType;
//
//private Character triggerMode;//triggertype
//private Character refLimit;
//
//@NotNull(message = "offerVerId cannot be null")
//private Integer offerVerId;
//
//private Character isLimit; //Y/N
//private Character destination; //1
//
//private LocalDate stateDate;
//private Character state;
//
//
//    @Getter
//    @Setter
//    public static class AcctRes{
//        private String acctResId;//balanceTypeVal;
//    }

    @Schema(description = "List of balance types")
    private List<BalanceType> balanceType;

    @Schema(description = "Destination", example = "1")
    private String destination;

    @Schema(description = "Effective date", example = "2025-06-19")
    private LocalDate effDate;

    @Schema(description = "Expiration date", example = "2025-06-18T17:00:00.000Z")
    private LocalDate expDate;

    @Schema(description = "Limit indicator", example = "N/Y")
    private String isLimit;

    @Schema(description = "Offer version ID", example = "5401")
    private int offerVerId;

//    @Schema(description = "Reference limit flag", example = "true")
//    private String refLimit;

    @Schema(description = "State", example = "A")
    private String state;

    @Schema(description = "Trigger mode", example = "1")
    private String triggerMode;

    // Inner class for balanceType items
    public static class BalanceType {
        @Schema(description = "Account Resource ID", example = "63")
        private String acctResId;

        public String getAcctResId() {
            return acctResId;
        }

        public void setAcctResId(String acctResId) {
            this.acctResId = acctResId;
        }
    }

    public String getAcctResIdAsString() {
        if (balanceType == null) return "";
        return balanceType.stream()
                .map(BalanceType::getAcctResId)
                .filter(Objects::nonNull)
                .collect(Collectors.joining(","));
    }

}
