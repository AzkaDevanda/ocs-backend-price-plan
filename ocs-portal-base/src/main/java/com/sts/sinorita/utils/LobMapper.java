package com.sts.sinorita.utils;

import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.SQLException;
import java.util.stream.Collectors;

@Component
public class LobMapper {
    public String map(Clob clob) {
        if (clob == null) return null;
        try {
            return clob.getSubString(1, (int) clob.length());
        } catch (SQLException e) {
            throw new RuntimeException("Error converting Clob to String", e);
        }
    }

    public String map(Blob blob) {
        if (blob == null) return null;
        try (InputStream is = blob.getBinaryStream();
             BufferedReader reader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {
            return reader.lines().collect(Collectors.joining("\n"));
        } catch (Exception e) {
            throw new RuntimeException("Error converting Blob to String", e);
        }
    }
}
