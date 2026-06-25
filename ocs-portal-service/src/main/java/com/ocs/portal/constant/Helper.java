package com.ocs.portal.constant;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.SQLException;

public class Helper {

    public String clobToString(Clob clob) {
        if (clob == null) return null;
        try {
            return clob.getSubString(1, (int) clob.length());
        } catch (SQLException e) {
            throw new RuntimeException("Error converting Clob to String", e);
        }
    }

    public String blobToString(Blob blob) {
        if (blob == null) return null;
        try (InputStream inputStream = blob.getBinaryStream()) {
            byte[] bytes = inputStream.readAllBytes();
            return new String(bytes, StandardCharsets.UTF_8);
        } catch (SQLException | IOException e) {
            throw new RuntimeException("Error converting Blob to String", e);
        }
    }

}
