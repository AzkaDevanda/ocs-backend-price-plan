package com.sts.sinorita.dto.response.re;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetSubscriptionEvent {

    private Integer id;
    private String reName;
    private Character reType;

}
