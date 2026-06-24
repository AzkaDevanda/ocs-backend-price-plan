package com.sts.sinorita.repository;

import com.sts.sinorita.dto.request.TimeSpanNameDto;
import com.sts.sinorita.entity.TimeSpan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TimeSpanRepository extends JpaRepository<TimeSpan,Integer> {
    @Query("SELECT new com.sts.sinorita.dto.request.TimeSpanNameDto(ts.timeSpanId, ts.timeSpanName) FROM TimeSpan ts")
    List<TimeSpanNameDto> getTimeSpanName();
}
