package com.ocs.portal.phytonCompiller;

import com.ocs.portal.dto.response.BaseResponseDto;
import com.ocs.portal.dto.response.CompillerPhytonResponseDto;
import com.ocs.portal.enums.EnumRC;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.io.*;
import java.nio.file.*;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
@Slf4j
public class PhytonCompiller {

        public BaseResponseDto PhytonSriptCompile (String pythonCode) {
            BaseResponseDto baseResponseDto = new BaseResponseDto();
            CompillerPhytonResponseDto compillerPhytonResponseDto = new CompillerPhytonResponseDto();

            try {

                // Format timestamp for filename
                String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                // Directory where the file will be written
//                Path targetDir = Paths.get("/apps");
//                Path targetDir = Paths.get("/Users/sweli/Documents/workspace/ocs-web-api/resources");
                Path targetDir = Paths.get("/Users/ocs-web-api/resources");
                Files.createDirectories(targetDir); // Ensure the directory exists
                // Build the full file path
                Path filePath = targetDir.resolve("test_" + timestamp + ".py");
                // Write content to the file
                Files.writeString(filePath, pythonCode);
                ProcessBuilder builder = new ProcessBuilder("python3",  filePath.toString()); //"-m", "py_compile",

                Process process = builder.start();
                int exitCode = process.waitFor();
                if (exitCode == 0) {
                    compillerPhytonResponseDto.setMessage("succeed in compiling the rule script");
                    System.out.println("✅ Python script is syntactically valid.");
                    baseResponseDto.setData(compillerPhytonResponseDto);
//                    baseResponseDto.setCode(EnumRC.SUCCESS.getRESPONSE_CODE());
                    baseResponseDto.setMessage(EnumRC.SUCCESS.getMessage());
                } else {
                    String err = "";

                    try (BufferedReader errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()))) {
                        String line;
                        while ((line = errorReader.readLine()) != null) {
                            if (err.equalsIgnoreCase("")){
                                err = line;
                            }else {
                                err = err.concat(System.lineSeparator()).concat(line);
                            }
                            System.out.println("❌ Python script has syntax errors:");
                            compillerPhytonResponseDto.setMessage(err);
                            baseResponseDto.setData(compillerPhytonResponseDto);
//                            baseResponseDto.setCode(EnumRC.BAD_REQUEST.getRESPONSE_CODE());
                        }

                    }
                }
                Files.deleteIfExists(filePath);
            } catch (IOException | InterruptedException e) {
                compillerPhytonResponseDto.setMessage(e.getMessage());
                baseResponseDto.setData(compillerPhytonResponseDto);
                baseResponseDto.setMessage(EnumRC.BAD_REQUEST.getMessage());
            }
            return  baseResponseDto;
        }



}
