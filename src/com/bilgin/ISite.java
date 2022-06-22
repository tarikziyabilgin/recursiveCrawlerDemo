package com.bilgin;

import java.util.Arrays;
import java.util.List;


public interface ISite {

    List<String> startsWithPrefixList();
    List<String> containsWithMiddleOfList();
    List<String> endsWithList();

    String baseUrl();

    boolean customValidate(String url);

    String getName();
}
