package com.example.loanzone;


import java.util.ArrayList;
import java.util.List;

public class MonthListGenerator {
    private static Fields givenFields = null;
    public static void setFields(Fields other) {
        givenFields = other;
    }
    public static List<Month> generateList() {
        if (givenFields.isAnnuity()) return getAnnuityList();
        else if (givenFields.isLinear()) return getAnnuityList();
        return null;
    }
    private static List<Month> getLinearList() {
        if (givenFields.isLinear()) {
            int counter = 1;
            double remainingMortgage = givenFields.getLoan_amount() - givenFields.getDown_payment();
            double monthlyReduction = remainingMortgage / givenFields.getDuration();
            double monthlyInterestRate = givenFields.getAnnual_percentage() / 12 / 100;
            double monthlyPayment;
            double totalToPay = 0;
            List<Month> LinearList = new ArrayList<>();
            for (int month = 1; month <= givenFields.getDuration(); month++) {
                monthlyPayment = monthlyReduction + monthlyInterestRate * remainingMortgage;
                if (month >= givenFields.getPost_pone_start() && month < givenFields.getPost_pone_end() && givenFields.getPost_pone_end() < givenFields.getDuration()) {
                    monthlyPayment = 0;
                    counter++;
                }
                else if (givenFields.getPost_pone_end() == month && month > 1 && givenFields.getPost_pone_end() < givenFields.getDuration()) {
                    remainingMortgage += remainingMortgage * counter * monthlyInterestRate;
                    monthlyReduction = remainingMortgage / givenFields.getDuration();
                    monthlyPayment = monthlyReduction + remainingMortgage * monthlyInterestRate;
                }
                else {
                    remainingMortgage -= monthlyReduction;
                }
                if (remainingMortgage < 0) remainingMortgage = 0;
                totalToPay += monthlyPayment;
            }
            double percentPay = totalToPay;
            monthlyReduction = (givenFields.getLoan_amount() - givenFields.getDown_payment()) / givenFields.getDuration();
            remainingMortgage = givenFields.getLoan_amount() - givenFields.getDown_payment();
            counter = 1;
            double percent;
            for (int month = 1; month <= givenFields.getDuration(); month++) {
                monthlyPayment = monthlyReduction + monthlyInterestRate * remainingMortgage;
                if (month >= givenFields.getPost_pone_start() && month < givenFields.getPost_pone_end() && givenFields.getPost_pone_end() < givenFields.getDuration()) {
                    monthlyPayment = 0;
                    counter++;
                    percent = remainingMortgage * monthlyInterestRate;
                }
                else if (givenFields.getPost_pone_end() == month && month > 1 && givenFields.getPost_pone_end() < givenFields.getDuration()) {
                    remainingMortgage += remainingMortgage * counter * monthlyInterestRate;
                    monthlyReduction = remainingMortgage / givenFields.getDuration();
                    monthlyPayment = monthlyReduction + remainingMortgage * monthlyInterestRate;
                    percent = remainingMortgage * monthlyInterestRate;
                }
                else {
                    percent = remainingMortgage * monthlyInterestRate;
                    remainingMortgage -= monthlyReduction;
                }
                if (remainingMortgage < 0) remainingMortgage = 0;
                totalToPay -= monthlyPayment;
                LinearList.add(new Month(month,monthlyPayment, percent, Math.abs(totalToPay)));
            }
            return LinearList;
        }
        else return new ArrayList<>();
    }
    public static List<Month> getAnnuityList() {
        double monthlyInterestRate = givenFields.getAnnual_percentage() / 12 / 100;
        int totalMonths = givenFields.getDuration();
        double monthlyPayment = ((givenFields.getLoan_amount() - givenFields.getDown_payment()) * monthlyInterestRate) / (1 - Math.pow((1+monthlyInterestRate), -totalMonths));
        if (Double.isNaN(monthlyPayment)) {
            monthlyPayment = (givenFields.getLoan_amount() - givenFields.getDown_payment()) / totalMonths;
        }
        List<Month> AnnuitList = new ArrayList<>();
        int counter = 1;
        double remainingBalance = monthlyPayment * totalMonths;
        double principal = (givenFields.getLoan_amount() - givenFields.getDown_payment());
        double percent;
        for (int month = 1; month <= totalMonths; month++) {
            remainingBalance -= monthlyPayment;
            if (month >= givenFields.getPost_pone_start() && month < givenFields.getPost_pone_end() && givenFields.getPost_pone_end() < totalMonths) {
                monthlyPayment = 0;
                counter++;
            }
            if (givenFields.getPost_pone_end() == month && month > 1 && givenFields.getPost_pone_end() < totalMonths) {
                remainingBalance += remainingBalance * counter * monthlyInterestRate;
                monthlyPayment = remainingBalance / (totalMonths - month);
                principal += principal * monthlyInterestRate;
            }
            if (principal <= 0) principal = 0;
            percent = principal * monthlyInterestRate;
            principal -= percent;
            AnnuitList.add(new Month(month,monthlyPayment, percent, Math.abs(remainingBalance)));
        }
        return AnnuitList;
    }

    public static Fields getGivenFields() {
        return givenFields;
    }
}
