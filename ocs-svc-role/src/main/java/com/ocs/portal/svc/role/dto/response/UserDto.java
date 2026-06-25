package com.ocs.portal.svc.role.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class UserDto {

    private static final long serialVersionUID = -1183675880038602719L;

    private Long userId;

    @NotNull(message = "userName not null")
    @Size(max = 255, message = "userName max 255 ")
    private String userName;

    @Size(max = 255, message = "userCode max 255 ")
    private String userCode;

    private String pwd;

    @Size(max = 255, message = "address max 255 ")
    private String address;


    @NotBlank(message = "email wajib diisi")
    @Email(message = "format email tidak valid")
    private String email;

    @Size(max = 60, message = "phone max 60 ")
    private String phone;

    private String memo;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Schema(example = "2025-08-21 14:30:00", description = "genereated by system = null")
    private LocalDateTime userEffDate;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Schema(example = "2025-08-21 14:30:00", description = "")
    private LocalDateTime userExpDate;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Schema(example = "2025-08-21 14:30:00", description = "genereated by system = null")
    private LocalDateTime createdDate;

    private String state;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Schema(example = "2025-08-21 14:30:00", description = "genereated by system = null")
    private LocalDateTime stateDate;

    private String isLocked;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Schema(example = "2025-08-21 14:30:00", description = "genereated by system = null")
    private LocalDateTime pwdExpDate;

    private Long loginFail;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Schema(example = "2025-08-21 14:30:00", description = "genereated by system = null")
    private LocalDateTime unlockDate;

    private Long portalId;

    private String isEffectiveNow;

    private String portalName;

    private String orderFields;

    private String userType;

    private String openId;

    private String alias;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Schema(example = "2025-08-21 14:30:00", description = "genereated by system = null")
    private LocalDateTime lastLoginDate;

    private Long securityQuestionId;

    private String securityAnswer;

    private String thumbnailUri;

    private String extAttr;

    private String stateNotEquals;

    private String userTypeName;

    private String userStateName;

    private String roleNameList;

    private String roleName;


    private Long srcId;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Schema(example = "2025-08-21 14:30:00", description = "genereated by system = null")
    private LocalDateTime updateDate;

    private Long roleId;

    private String loginIp;

    private boolean exist;

    private boolean passwordExist = true;

    private Long createdId;

    private String headImg;


    private String circle;

    private Long orgId;

    private String circleName;

    private String zoneName;

    private String unit;
}
