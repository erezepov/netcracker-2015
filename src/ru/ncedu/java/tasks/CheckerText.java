package ru.ncedu.java.tasks;

import java.util.regex.Pattern;

/**
 * Created by eschy_000 on 10.10.2015.
 */
public class CheckerText {
    public static void main (String[] args) {
        Checker checker = new CheckerImpl();
        Pattern emailPattern = checker.getEMailPattern();
        Pattern plsqlPattern = checker.getPLSQLNamesPattern();
        Pattern urlPattern = checker.getHrefURLPattern();
        System.out.print(Pattern.matches(emailPattern.pattern(),"simba.lion@korol-lion.com"));
    }
}
