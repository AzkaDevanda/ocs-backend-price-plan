package com.ocs.portal.svc.role.auth.service;
import java.util.HashSet;
import java.util.Set;

public class JWTBlacklist {
    private static Set<String> blacklist = new HashSet<>();

    public static void addToBlacklist(String token) {
        blacklist.add(token);
    }

    public static boolean isBlacklisted(String token) {
        return blacklist.contains(token);
    }

    public static void clearBlacklist() {
        blacklist.clear();
    }
}
