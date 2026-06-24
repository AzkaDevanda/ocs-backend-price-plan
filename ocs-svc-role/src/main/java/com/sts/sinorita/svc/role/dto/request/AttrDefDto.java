package com.sts.sinorita.svc.role.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class AttrDefDto {
    private static final long serialVersionUID = 8057767905473594819L;

    @NotNull(message = "")
    @Size(max = 60)
    private String tableName;

    @NotNull(message = "")
    @Size(max = 60)
    private String columnName;

    @NotNull(message = "")
    @Size(max = 60)
    private String extCode;

    private Long valueId;

    @NotNull(message = "")
    @Size(max = 1)
    private String inputType;

    private Long minLength;

    private Long maxLength;

    @NotNull(message = "")
    @Size(max = 1)
    private String nullAble;

    @Size(max = 60)
    private String displayName;

    @NotNull(message = "")
    @Size(max = 1)
    private String columnType;

    @Size(max = 4000)
    private String comments;

    private Long spId;

    private Long displayOrder;

    @Size(max = 1)
    private String isSyncAttr;

    private String displayAble;

    private AttrSrc src;

    public enum AttrSrc {
        POT("POT"),
        AD("AD");

        final String src;

        AttrSrc(String src) {
            this.src = src;
        }
    }
}
