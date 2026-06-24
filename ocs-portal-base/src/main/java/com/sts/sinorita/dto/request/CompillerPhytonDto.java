package com.sts.sinorita.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class CompillerPhytonDto implements Serializable {
    private String phytonScript;
}
