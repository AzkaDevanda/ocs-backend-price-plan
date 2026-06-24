package com.sts.sinorita.constant;

import java.util.Arrays;
import java.util.List;

public class BalCategoryProvider {
    public static List<BalCategory> getBalCategoryList() {
        return Arrays.asList(
                new BalCategory("M", "monetary"),
                new BalCategory("S" ,"sms"),
                new BalCategory("D", "data"),
                new BalCategory("V", "voice"),
                new BalCategory("B", "bonus"),
                new BalCategory("P", "point")

        );
    }
}
