package com.ocs.portal.utils;

import com.google.gson.GsonBuilder;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.fatboyindustrial.gsonjavatime.Converters.registerLocalDate;

@Component
public class GsonCustom {
    com.google.gson.Gson gson = registerLocalDate(new GsonBuilder()).create();

    public String doJsonString(Object o) {
        return gson.toJson(o);
    }

    public Object fromJsonString(String strJson, Class<?> clazz) {
        return gson.fromJson(strJson, clazz);
    }

    public String objectListToString(List<Object> list) {
        return gson.toJson(list);
    }

    public List<Object> stringToObjectList(String strJson) {
        return gson.fromJson(strJson, List.class);
    }

}
