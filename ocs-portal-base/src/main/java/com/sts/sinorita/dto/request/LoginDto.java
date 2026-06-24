package com.sts.sinorita.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginDto {

    @Schema(example = "admin")
    private String userName;

    @Schema(example = "Zsmart$31*")
    private String password;
}
