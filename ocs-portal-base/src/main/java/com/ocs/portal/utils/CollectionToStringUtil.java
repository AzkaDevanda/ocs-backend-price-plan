package com.ocs.portal.utils;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public final class CollectionToStringUtil {
    public static String getStringByCollection(Collection<String> targetIdCollection) {
        return getStringByCollection(targetIdCollection, ",");
    }

    public static String getStringByCollection(Collection<String> targetIdCollection, String splitString) {
        StringBuffer targetIdSbf = new StringBuffer();
        boolean init = true;
        if (targetIdCollection != null && !targetIdCollection.isEmpty()) {
            Iterator<String> offerIdIt = targetIdCollection.iterator();
            while (offerIdIt.hasNext()) {
                if (init) {
                    init = false;
                } else {
                    targetIdSbf.append(splitString);
                }
                targetIdSbf.append(offerIdIt.next());
            }
        }
        return targetIdSbf.toString();
    }

    public static String toString(List<?> a) {
        if (a == null)
            return "null";
        if (a.isEmpty())
            return "[]";
        StringBuilder buf = new StringBuilder();
        for (int i = 0; i < a.size(); i++) {
            if (i == 0) {
                buf.append('[');
            } else {
                buf.append(", ");
            }
            buf.append(String.valueOf(a.get(i)));
        }
        buf.append("]");
        return buf.toString();
    }
}
