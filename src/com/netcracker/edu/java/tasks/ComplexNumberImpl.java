package com.netcracker.edu.java.tasks;

import java.util.Arrays;

public class ComplexNumberImpl implements ComplexNumber {

    double im;
    double re;

    public double getRe() {
        return re;
    }

    public double getIm() {
        return im;
    }

    public boolean isReal() {
        return im == 0;
    }

    public void set(double re, double im) {
        this.re = re;
        this.im = im;
    }

    public void set(String value) throws NumberFormatException {
        int sepIndex;
        if (value.endsWith("i")) {
            sepIndex = Math.max(value.lastIndexOf('+'), value.lastIndexOf('-'));
            if (sepIndex < 1) {
                re = 0;
                im = value.length() - sepIndex < 3 ? Double.parseDouble(value.replace('i','1')) :
                        Double.parseDouble(value.substring(0, value.length() - 1));
            } else {
                re = Double.parseDouble(value.substring(0, sepIndex));
                im = value.length() - sepIndex < 3 ? Double.parseDouble(value.replace('i','1').substring(sepIndex)) :
                        Double.parseDouble(value.substring(sepIndex, value.length() - 1));
            }
        } else {
            re = Double.parseDouble(value);
            im = 0;
        }

    }

    public ComplexNumber copy() {
        ComplexNumber result = new ComplexNumberImpl();
        result.set(re, im);
        return result;
    }

    public ComplexNumber clone() throws CloneNotSupportedException {
        return (ComplexNumber) super.clone();
    }

    public String toString() {
        String result;
        if (im == 0) {
            result = Double.toString(re);
        } else if (re == 0) {
            result = im + "i";
        } else {
            result = im > 0 ? re + "+" + im + "i" : re + (im + "i");
        }
        return result;
    }

    public boolean equals(Object other) {
        return other instanceof ComplexNumber
                && ((((ComplexNumber) other).getIm() == im) && (((ComplexNumber) other).getRe() == re));
    }


    public int compareTo(ComplexNumber other) {
        return Double.compare(Math.pow(re, 2) + Math.pow(im, 2),
                Math.pow(other.getRe(), 2) + Math.pow(other.getIm(), 2));
    }

    public void sort(ComplexNumber[] array) {
        Arrays.sort(array);
    }

    public ComplexNumber negate() {
        re = -re;
        im = -im;
        return this;
    }

    public ComplexNumber add(ComplexNumber arg2) {
        re = re + arg2.getRe();
        im = im + arg2.getIm();
        return this;
    }

    public ComplexNumber multiply(ComplexNumber arg2) {
        double newRe = re * arg2.getRe() - im * arg2.getIm();
        double newIm = im * arg2.getRe() + re * arg2.getIm();
        re = newRe;
        im = newIm;
        return this;
    }
}
