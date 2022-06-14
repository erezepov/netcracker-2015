package ru.ncedu.java.tasks;

import java.util.Scanner;

/**
 * Created by eschy_000 on 08.10.2015.
 */
public class BusinessCardTest {
    public static void main (String[] args) {
        BusinessCard businessCard = new BusinessCardImpl().getBusinessCard(new Scanner(System.in));
    }
}
