package com.ocs.portal.svc.role.repository;

import com.ocs.portal.entity.BfmStaff;
import com.ocs.portal.svc.role.dto.request.AttrDefDto;
import com.ocs.portal.svc.role.projection.AttrDefDtoProjection;
import com.ocs.portal.svc.role.projection.BfmAttrDtoProjection;
import com.ocs.portal.svc.role.projection.StafDtoProjection;
import com.ocs.portal.svc.role.projection.StaffAttrProjection;
import com.ocs.portal.svc.role.repository.custom.BfmStaffCustomRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public interface BfmStaffRepository extends JpaRepository<BfmStaff, Long>, BfmStaffCustomRepository {

    @Query(value = """
            SELECT
                STAFF_ID AS staffId,
                STAFF_NAME AS staffName,
                STAFF_CODE AS staffCode,
                EMAIL AS email,
                PHONE AS phone,
                ADDRESS AS address,
                CREATED_DATE AS createdDate,
                STATE AS state,
                STATE_DATE AS stateDate,
                USER_ID AS userId,
                EXT_STR_01 AS extStr01,
                EXT_STR_02 AS extStr02,
                EXT_STR_03 AS extStr03,
                EXT_STR_04 AS extStr04,
                EXT_STR_05 AS extStr05,
                EXT_STR_06 AS extStr06,
                EXT_STR_07 AS extStr07,
                EXT_STR_08 AS extStr08,
                EXT_STR_09 AS extStr09,
                EXT_STR_10 AS extStr10,
                EXT_STR_11 AS extStr11,
                EXT_STR_12 AS extStr12,
                EXT_NUM_01 AS extNum01,
                EXT_NUM_02 AS extNum02,
                EXT_DAT_01 AS extDat01,
                EXT_DAT_02 AS extDat02,
                UPDATE_DATE AS updateDate,
                SP_ID AS spId,
                CREATOR AS creator,
                REMARK AS remark
            FROM BFM_STAFF
            WHERE (:staffId IS NULL OR STAFF_ID = :staffId)
              AND (:userId IS NULL OR USER_ID = :userId)
                    FETCH FIRST 1 ROWS ONLY
            """, nativeQuery = true)
    Optional<StafDtoProjection> selectStaff(
            @Param("staffId") Long staffId,
            @Param("userId") Long userId
    );

    @Query(value = """
            SELECT
                TABLE_NAME AS tableName,
                COLUMN_NAME AS columnName,
                EXT_CODE AS extCode,
                VALUE_ID AS valueId,
                INPUT_TYPE AS inputType,
                MIN_LENGTH AS minLength,
                MAX_LENGTH AS maxLength,
                NULL_ABLE AS nullAble,
                DISPLAY_NAME AS displayName,
                COLUMN_TYPE AS columnType,
                COMMENTS AS comments,
                SP_ID AS spId,
                DISPLAY_ORDER AS displayOrder,
                IS_SYNC_ATTR AS isSyncAttr,
                DISPLAY_ABLE AS displayAble,
                SRC AS src
            FROM BFM_TABLE_EXT
            WHERE (:tableName is null or TABLE_NAME = :tableName)
              AND (:columnName is null or COLUMN_NAME = :columnName)
              AND (:spId IS NULL OR NVL(SP_ID, 0) = :spId)
            FETCH FIRST 1 ROWS ONLY 
            """, nativeQuery = true)
    Optional<AttrDefDtoProjection> qryAttrDefDto(
            @Param("tableName") String tableName,
            @Param("columnName") String columnName,
            @Param("spId") Integer spId
    );


    @Query(value = """
            SELECT
                ATTR_ID AS attrId,
                ATTR_NAME AS attrName,
                ATTR_TYPE AS attrType,
                OBJ_ATTR_ID AS objAttrId,
                ATTR_CODE AS attrCode,
                CONFIG_VISIBLE AS configVisible,
                CSR_VISIBLE AS csrVisible,
                INSTANTIATABLE AS instantiatable,
                SP_ID AS spId
            FROM BFM_ATTR b where (:attrCode is null or  b.ATTR_CODE = :attrCode)
            AND (:spId IS NULL OR COALESCE(b.SP_ID, 0) = :spId) 
            """, nativeQuery = true)
    Optional<BfmAttrDtoProjection> qryBfmAttrByCode(@Param("attrCode") String attrCode,
                                                    @Param("spId") Long spId);

    @Query(value = """
            select
            c.STAFF_ID AS staffId,
            c.ATTR_VALUE AS attrValue,
            c.ATTR_CODE AS attrCode,
            c.ATTR_NAME AS attrName FROM BFM_STAFF_ATTR c
            where (:staffId is NULL or c.STAFF_ID = :staffId)
            AND (:attrId is NULL or c.ATTR_CODE =:attrId)
                        FETCH FIRST 1 ROWS ONLY
            """, nativeQuery = true)
    Optional<StaffAttrProjection> qryBfmStaffAttr(@Param("staffId") Long staffId,
                                                  @Param("attrId") Long attrId);


    @Query("select b from BfmStaff b where b.staffId = :staffId ")
    Optional<BfmStaff> queryStaffDtoByStaffId(@Param("staffId")Long  staffId);


    @Query(value = """
        SELECT TABLE_NAME, COLUMN_NAME, EXT_CODE, VALUE_ID, INPUT_TYPE, MIN_LENGTH, MAX_LENGTH,
               NULL_ABLE, DISPLAY_NAME, COLUMN_TYPE, COMMENTS, SP_ID, DISPLAY_ORDER,
               IS_SYNC_ATTR, DISPLAY_ABLE, SRC
        FROM BFM_TABLE_EXT
        WHERE (:tableName IS NULL OR TABLE_NAME LIKE CONCAT(:tableName, '%'))
          AND (:spId IS NULL OR COALESCE(SP_ID, 0) = :spId)
          AND (:attrCodes IS NULL OR EXT_CODE IN (:attrCodes))
        ORDER BY DISPLAY_ORDER
        """, nativeQuery = true)
    List<AttrDefDto> qryAttrDefList(
            @Param("tableName") String tableName,
            @Param("spId") Long spId,
            @Param("attrCodes") List<String> attrCodes
    );

    @Query(value = """
        SELECT ORG_ID, PARENT_ORG_ID, ORG_NAME, ORG_TYPE, AREA_ID, STATE, ORG_CODE,
               EXT_STR_01, EXT_STR_02, EXT_STR_03, EXT_STR_04, EXT_STR_05,
               EXT_STR_06, EXT_STR_07, EXT_STR_08, EXT_STR_09, EXT_STR_10,
               EXT_STR_11, EXT_STR_12, EXT_STR_13, SP_ID, LEADER,
               EXT_NUM_01, EXT_NUM_02, EXT_DAT_01, EXT_DAT_02
        FROM BFM_ORG
        WHERE STATE = 'A'
          AND (:orgId IS NULL OR :orgId = '' OR ORG_ID = :orgId)
          AND (
              :list IS NULL OR
              CARDINALITY(:list) = 0 OR
              ORG_ID IN (:list)
          )
        """, nativeQuery = true)
    Map<String, Object> selectOrgBasicAttrList(
            @Param("orgId") Long orgId,
            @Param("list") List<String> list
    );

    @Query(value = """
        SELECT STAFF_ID, STATE, EXT_STR_01, EXT_STR_02, EXT_STR_03, EXT_STR_04, EXT_STR_05,
               EXT_STR_06, EXT_STR_07, EXT_STR_08, EXT_STR_09, EXT_STR_10, EXT_STR_11,
               EXT_STR_12, SP_ID, EXT_NUM_01, EXT_NUM_02, EXT_DAT_01, EXT_DAT_02
        FROM BFM_STAFF
        WHERE STATE = 'A'
          AND (:staffId IS NULL OR :staffId = '' OR STAFF_ID = :staffId)
        """, nativeQuery = true)
    Map<String, Object> selectStaffBasicAttrList(@Param("staffId") Long staffId);

    @Modifying
    @Query(value = "DELETE FROM POT.SYSUSER_LOGIN_LIMIT WHERE user_id = :user_id", nativeQuery = true)
    public void removeLimitsByUserId(@Param("user_id") Long userId);

}
