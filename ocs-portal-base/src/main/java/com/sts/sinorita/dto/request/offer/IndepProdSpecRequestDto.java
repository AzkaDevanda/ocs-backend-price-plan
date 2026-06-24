package com.sts.sinorita.dto.request.offer;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IndepProdSpecRequestDto {
    @NotNull
    @Schema(example = "")
    private Character offerType;

    @Schema(defaultValue = "")
    public Integer offerId;

    @Schema(defaultValue = "")
    public Long offerCatgId;

    @Schema(defaultValue = "")
    public Long offerCatgMemId;

    @NotNull(message = "Indep prod spec cannot be null")
    public Long indepProdSpecId;

    @NotBlank(message = "Product name cannot be null")
    @Schema(defaultValue = "Telkomcel Prepaid Channel")
    private String offerName;

    @NotNull(message = "Effective Date cannot be null")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @Schema(type = "string", defaultValue = "2024-08-30")
    private LocalDate effDate;

    @NotNull(message = "Effective Date cannot be null")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @Schema(type = "string", defaultValue = "2024-08-30")
    private LocalDate expDate;

    @NotBlank(message = "Code cannot be null")
    @Schema(defaultValue = "MP_1000")
    private String offerCode;

    @Schema(defaultValue = "[\"805\", \"806\"]")
    private List<Character> effType;

    @Schema(defaultValue = "805")
    private Integer servType;

    @NotNull(message = "Paid flag cannot be null")
    @Schema(example = "N | Y")
    private Character paidFlag;

    @Schema(defaultValue = "5")
    private Integer lifecycleType;

    @Schema(defaultValue = "1")
    private Long brandPricePlanId;

    @Schema(example = "90D->180D->33D->90D")
    private String remarks;

    @Schema(example = "")
    private String prodType;

    @Schema(defaultValue = "0")
    private Integer spId;
}
