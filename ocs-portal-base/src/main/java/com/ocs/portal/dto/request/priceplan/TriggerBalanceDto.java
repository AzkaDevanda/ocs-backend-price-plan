package com.ocs.portal.dto.request.priceplan;

import lombok.Getter;
import lombok.Setter;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class TriggerBalanceDto {

        private String triggerType;
        private String destination;
        private LocalDate effDate;
        private LocalDate expDate;
        private String isLimitBalance;
        private String acctResIdList;
        private String offerVerId;
        private List<AcctRes> acctResList;
        private String state;
        private LocalDate stateDate;
        private int spId;
        private String triggerId;

    @Getter
    @Setter
    public static class AcctRes {


        private String acctResId;

        public String getAcctResId() {
            return acctResId;
        }

        public void setAcctResId(String acctResId) {
            this.acctResId = acctResId;
        }
    }

}
