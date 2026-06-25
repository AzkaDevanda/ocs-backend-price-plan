package com.ocs.portal.svc.role.service.users;

import com.ocs.portal.svc.role.auth.service.UserService;
import com.ocs.portal.svc.role.dto.response.UserDto;
import com.ocs.portal.svc.role.service.roles.RoleUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.List;

@Slf4j
@Service
public class FileHelperUsers {

    @Autowired
    private UserService userService;

    @Autowired
    RoleUserService roleUserService;

    public byte[] exportUsers(Integer userId) throws IOException {
        try(XSSFWorkbook workbook = new XSSFWorkbook()){
            Sheet scheduleSheet = workbook.createSheet("Users");
            List<UserDto> listUser = roleUserService.queryUserListByUserId(userId.longValue());

            boolean isAdmin = listUser.stream()
                        .anyMatch(user -> user.getRoleName() != null &&
                                user.getRoleName().toUpperCase().contains("ADMIN"));

            if (isAdmin) {
                List<UserDto> list = roleUserService.selectRoleByAll();
                createUsers(scheduleSheet, list);
            }else {
                Integer roleID = 0;
                for (UserDto userDto : listUser){
                    roleID = userDto.getRoleId().intValue();
                }
                List<UserDto> listUser2 = roleUserService.selectRoleByRoleID(roleID.longValue());
                createUsers(scheduleSheet, listUser2);
            }
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            workbook.write(out);
            return  out.toByteArray();
        }
    }

    private void createUsers(Sheet sheet, List<UserDto> users) {

        Field[] fields = UserDto.class.getDeclaredFields();

        Row headerRow = sheet.createRow(0);
        int col = 0;
        for (int i = 0; i < fields.length; i++) {
            String fieldName = fields[i].getName();
            if (fieldName.equals("roleId") || fieldName.equals("roleName")) {
                continue;
            }
            headerRow.createCell(col++).setCellValue(toHeader(fieldName));
        }

        int rowNum = 1;

        for (UserDto data : users) {
            Row row = sheet.createRow(rowNum++);

            for (int i = 0; i < fields.length; i++) {
                try {
                    fields[i].setAccessible(true);
                    Object value = fields[i].get(data);

                    row.createCell(i).setCellValue(
                            value != null ? value.toString() : ""
                    );

                } catch (IllegalAccessException e) {
                    row.createCell(i).setCellValue("");
                }
            }
        }

        for (int i = 0; i < fields.length; i++) {
            sheet.autoSizeColumn(i);
        }
    }

    private String toHeader(String fieldName) {
        return fieldName
                .replaceAll("([a-z])([A-Z])", "$1 $2")
                .toUpperCase();
    }
}
