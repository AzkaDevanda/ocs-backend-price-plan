package com.ocs.portal.svc.role.utils;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Component
public class ListUtils {

    public static List subtract(List list1, List list2) {
        ArrayList result = new ArrayList(list1);
        Iterator iterator = list2.iterator();
        while (iterator.hasNext())
            result.remove(iterator.next());
        return result;
    }
}
