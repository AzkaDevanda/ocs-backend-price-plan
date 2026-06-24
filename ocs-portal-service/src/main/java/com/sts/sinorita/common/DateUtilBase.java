package com.sts.sinorita.common;

import org.springframework.stereotype.Service;
import java.util.Date;

@Service
public class DateUtilBase {

    public static int compare(Date beginDate, Date endDate) {
        int ret = 1;
        long beginTime = beginDate.getTime();
        long endTime = endDate.getTime();
        if (beginTime > endTime)
            ret = 2;
        if (beginTime == endTime)
            ret = 1;
        if (beginTime < endTime)
            ret = 0;
        return ret;
    }

}
