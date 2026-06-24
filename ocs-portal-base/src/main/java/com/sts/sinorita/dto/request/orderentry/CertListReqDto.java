package com.sts.sinorita.dto.request.orderentry;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.data.repository.query.Param;

import java.io.Serializable;

@Data
public class CertListReqDto implements Serializable {
   @Schema(example = "email@google.com")
   String email;
   @Schema(example = "1 or null")
   Long industryId;
   @Schema(example = "11000342 or null")
   String custCode;
   @Schema(example = "A")
   String custType;
   @Schema(example = "1 / null")
   Long certTypeId;
   @Schema(example = "67074634487")
   String certNbr;
   @Schema(example = "1000341")
   Long custId;
}
