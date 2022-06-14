package ru.ncedu.java.tasks;

import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CheckerImpl implements Checker {
    public Pattern getPLSQLNamesPattern() {
        return Pattern.compile("^[a-zA-Z][\\w\\$]{0,29}$");
    }

    public Pattern getHrefURLPattern() {
        return Pattern.compile("(?i)^<a[\\s]*href[\\s]*=[\\s]*(([^ \"]*)|(\"[^\"]*\"))[\\s]*>?$");
    }

    public Pattern getEMailPattern() {
        return Pattern.compile("^[a-zA-Z0-9]([\\w\\.\\-]{0,20}[a-zA-Z0-9])?@([a-zA-Z0-9]([a-zA-Z0-9\\-]*[a-zA-Z0-9])" +
                "+\\.)+(ru|com|net|org)$");
    }

    public boolean checkAccordance(String inputString, Pattern pattern) throws IllegalArgumentException {
        if (inputString == null ^ pattern == null) {
            throw new IllegalArgumentException("null parameter");
        }
        return inputString == null && pattern == null
                || Pattern.matches(pattern.pattern(),inputString);
    }

    public List<String> fetchAllTemplates(StringBuffer inputString, Pattern pattern) throws IllegalArgumentException {
        if (inputString == null || pattern == null) {
            throw new IllegalArgumentException("null parameter");
        }
        List<String> allMatches = new LinkedList<>();
        Matcher m = pattern.matcher(inputString);
        while (m.find()) {
            allMatches.add(m.group());
        }
        return allMatches;
    }
}
