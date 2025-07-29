package com.deliveroo.cronParser.utils;

import java.util.List;
import java.util.Map;

public class AnnotationMapper {

    public static final Map<String, String[]> MACRO_MAP = Map.of(
            "@yearly",   new String[]{"0", "0", "1", "1", "*"},
            "@annually", new String[]{"0", "0", "1", "1", "*"},
            "@monthly",  new String[]{"0", "0", "1", "*", "*"},
            "@weekly",   new String[]{"0", "0", "*", "*", "0"},
            "@daily",    new String[]{"0", "0", "*", "*", "*"},
            "@midnight", new String[]{"0", "0", "*", "*", "*"},
            "@hourly",   new String[]{"0", "*", "*", "*", "*"}
    );


}
