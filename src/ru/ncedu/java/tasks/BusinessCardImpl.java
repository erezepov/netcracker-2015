package ru.ncedu.java.tasks;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Created by eschy_000 on 28.09.2015.
 */
public class BusinessCardImpl implements BusinessCard {

    String firstName;
    String lastName;
    String department;
    Calendar birthDate;
    String gender;
    int salary;
    String phoneNumber;

    public BusinessCard getBusinessCard(Scanner scanner) {
        scanner.useDelimiter(";");
        firstName = scanner.next();
        lastName = scanner.next();
        department = scanner.next();
        try {
            (birthDate = Calendar.getInstance()).setTime(new SimpleDateFormat("dd-MM-yyyy").parse(scanner.next()));
        } catch (ParseException e) {
            scanner.close();
            throw new InputMismatchException("Cannot parse birth date");
        }
        gender = scanner.next();
        switch (gender) {
            case "F":
                gender = "Female";
                break;
            case "M":
                gender = "Male";
                break;
            default:
                scanner.close();
                throw new InputMismatchException("Strange gender");
        }
        salary = scanner.nextInt();
        if (salary < 100 || salary > 100000) {
            scanner.close();
            throw new InputMismatchException("Wrong salary");
        }
        phoneNumber = scanner.next();
        try {
            Long.parseLong(phoneNumber);
        } catch (NumberFormatException e) {
            throw new InputMismatchException("Bad number");
        }
        if (phoneNumber.length() != 10) {
            scanner.close();
            throw new InputMismatchException("Bad phone number");
        }
        scanner.close();
        return this;
    }

    public String getEmployee() {
        return firstName + " " + lastName;
    }

    public String getDepartment() {
        return department;
    }

    public int getSalary() {
        return salary;
    }

    public int getAge() {
        int age = -1;
        Calendar calendar = Calendar.getInstance();
        while (calendar.compareTo(birthDate) > 0) {
            calendar.add(Calendar.YEAR, -1);
            ++age;
        }
        return age;
    }

    public String getGender() {
        return gender;
    }

    public String getPhoneNumber() {
        return "+7 " + phoneNumber.substring(0,3) + "-" + phoneNumber.substring(3,6) + "-"
                + phoneNumber.substring(6,8) + "-" + phoneNumber.substring(8,10);
    }
}
