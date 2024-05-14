package com.example.loanzone;

import java.io.Serializable;

public class Month implements Serializable {
    int index;
    double monthly_payment;
    double monthly_interest;
    double remaining_balance;
    String indexS;
    String monthly_paymentS;
    String monthly_interestS;
    String remaining_balanceS;
    public Month(int index, double monthly_payment, double monthly_interest, double remaining_balance) {
        this.index = index;
        this.monthly_payment = monthly_payment;
        this.monthly_interest = monthly_interest;
        this.remaining_balance = remaining_balance;
        this.indexS = Integer.toString(index);
        this.monthly_paymentS = String.format("%.2f", monthly_payment);
        this.monthly_interestS = String.format("%.2f", monthly_interest);
        this.remaining_balanceS = String.format("%.2f", remaining_balance);
    }
    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public double getMonthly_payment() {
        return monthly_payment;
    }

    public void setMonthly_payment(double monthly_payment) {
        this.monthly_payment = monthly_payment;
    }

    public double getMonthly_interest() {
        return monthly_interest;
    }

    public void setMonthly_interest(double monthly_interest) {
        this.monthly_interest = monthly_interest;
    }

    public double getRemaining_balance() {
        return remaining_balance;
    }

    public void setRemaining_balance(double remaining_balance) {
        this.remaining_balance = remaining_balance;
    }

    public String getIndexS() {
        return indexS;
    }

    public void setIndexS(String indexS) {
        this.indexS = indexS;
    }

    public String getMonthly_paymentS() {
        return monthly_paymentS;
    }

    public void setMonthly_paymentS(String monthly_paymentS) {
        this.monthly_paymentS = monthly_paymentS;
    }

    public String getMonthly_interestS() {
        return monthly_interestS;
    }

    public void setMonthly_interestS(String monthly_interestS) {
        this.monthly_interestS = monthly_interestS;
    }

    public String getRemaining_balanceS() {
        return remaining_balanceS;
    }

    public void setRemaining_balanceS(String remaining_balanceS) {
        this.remaining_balanceS = remaining_balanceS;
    }
}
