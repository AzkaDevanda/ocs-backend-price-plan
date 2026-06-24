package com.sts.sinorita.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ADVICE_CHANNEL")
public class AdviceChannel implements Serializable {
    @Id
    @Column(name = "ADVICE_CHANNEL", length = 2, nullable = false)
    private String adviceChannel;

    @Column(name = "ADVICE_CHANNEL_NAME", length = 60, nullable = false)
    private String adviceChannelName;

    @Column(name = "COMMENTS", length = 120)
    private String comments;
}
