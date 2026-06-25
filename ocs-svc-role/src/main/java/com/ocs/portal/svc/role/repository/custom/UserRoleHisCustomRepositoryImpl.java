package com.ocs.portal.svc.role.repository.custom;

import com.ocs.portal.svc.role.dto.request.roles.UserRoleDto;
import com.ocs.portal.svc.role.dto.request.roles.UserRoleExtDto;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Repository("userRoleHisCustomRepository")
public class UserRoleHisCustomRepositoryImpl implements UserRoleHisCustomRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public List<UserRoleExtDto> selectUserRoles(List<UserRoleDto> list) {
        if (list == null || list.isEmpty()) return List.of();

        StringBuilder sql = new StringBuilder("""
            SELECT
                USER_ID, ROLE_ID, SP_ID, ID,
                USER_ROLE_TIMES, STAFF_ROLE_TIMES,
                CREATED_DATE, UPDATE_DATE
            FROM POT.BFM_USER_ROLE
            WHERE
        """);

        List<Object> params = new ArrayList<>();

        for (int i = 0; i < list.size(); i++) {
            sql.append("(ROLE_ID = ? AND USER_ID = ? AND SP_ID = ?)");
            if (i < list.size() - 1) {
                sql.append(" OR ");
            }
            UserRoleDto item = list.get(i);
            params.add(item.getRoleId());
            params.add(item.getUserId());
            params.add(item.getSpId());
        }

        Query query = entityManager.createNativeQuery(sql.toString());

        for (int i = 0; i < params.size(); i++) {
            query.setParameter(i + 1, params.get(i));
        }

        @SuppressWarnings("unchecked")
        List<Object[]> results = query.getResultList();

        List<UserRoleExtDto> dtoList = new ArrayList<>();
        for (Object[] row : results) {
            UserRoleExtDto dto = new UserRoleExtDto();
            dto.setUserId(((Number) row[0]).longValue());
            dto.setRoleId(((Number) row[1]).longValue());
            dto.setSpId(((Number) row[2]).longValue());
//            dto.setId(((Number) row[3]).longValue());
            dto.setUserRoleTimes(row[4] != null ? ((Number) row[4]).longValue() : null);
            dto.setStaffRoleTimes(row[5] != null ? ((Number) row[5]).longValue() : null);
            dto.setCreatedDate(
                    row[6] != null
                            ? ((Timestamp) row[6]).toLocalDateTime().toLocalDate()
                            : null
            );

            dto.setUpdateDate( row[7] != null
                    ? ((Timestamp) row[7]).toLocalDateTime().toLocalDate()
                    : null);
            dtoList.add(dto);
        }
        return dtoList;
    }
}
