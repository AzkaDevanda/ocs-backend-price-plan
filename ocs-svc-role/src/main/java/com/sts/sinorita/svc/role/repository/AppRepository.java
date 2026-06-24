package com.sts.sinorita.svc.role.repository;

import com.sts.sinorita.entity.BfmApp;
import com.sts.sinorita.svc.role.projection.AppDtoProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppRepository extends JpaRepository<BfmApp, Long> {

    @Query(value = """
                SELECT 
                    APP_ID        AS appId,
                    APP_NAME      AS appName,
                    APP_CODE      AS appCode,
                    APP_URL       AS appUrl,
                    ICON_PATH     AS iconPath,
                    COMMENTS      AS comments,
                    SP_ID         AS spId,
                    STATE         AS state,
                    STATE_DATE    AS stateDate
                FROM 
                    BFM_APP
                WHERE 
                    STATE = 'A'
                ORDER BY 
                    APP_ID
            """, nativeQuery = true)
    List<AppDtoProjection> selectAppList();

}
