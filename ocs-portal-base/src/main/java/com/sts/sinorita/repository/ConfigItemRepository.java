package com.sts.sinorita.repository;

import com.sts.sinorita.entity.ConfigItem;
import com.sts.sinorita.projection.configitem.ConfigItemParamProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ConfigItemRepository extends JpaRepository<ConfigItem, Integer> {

  @Query(value = """
      SELECT
        B.PARAM_VALUE AS paramValue,
        B.DEFAULT_VALUE AS defaultValue,
        B.DATA_TYPE AS dataType
        FROM
          CONFIG_ITEM A
        JOIN
           CONFIG_ITEM_PARAM B ON B.CONFIG_ITEM_ID = A.CONFIG_ITEM_ID
           WHERE
            A.BUSINESS_MODULE_CODE = :moduleCode
            AND A.CONFIG_ITEM_CODE = :configItemCode
            AND B.PARAM_CODE = :paramCode AND ROWNUM = 1
      """, nativeQuery = true)
  Optional<ConfigItemParamProjection> findConfigItem(@Param("moduleCode") String moduleCode,
      @Param("configItemCode") String configItemCode, @Param("paramCode") String paramCode);

}
