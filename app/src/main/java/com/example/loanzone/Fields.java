package com.example.loanzone;

public class Fields {
    private double loan_amount;
    private double down_payment;
    private double annual_percentage;
    private int duration;
    private int post_pone_start;
    private int post_pone_end;
    private boolean annuity;
    private boolean linear;

    public Fields(double loan_amount, double down_payment, double annual_percentage, int duration, int post_pone_start, int post_pone_end, boolean annuity, boolean linear) {
        this.loan_amount = loan_amount;
        this.down_payment = down_payment;
        this.annual_percentage = annual_percentage;
        this.duration = duration;
        this.post_pone_start = post_pone_start;
        this.post_pone_end = post_pone_end;
        this.annuity = annuity;
        this.linear = linear;
    }
    public double getLoan_amount() {
        return loan_amount;
    }

    public void setLoan_amount(double loan_amount) {
        this.loan_amount = loan_amount;
    }

    public double getDown_payment() {
        return down_payment;
    }

    public void setDown_payment(double down_payment) {
        this.down_payment = down_payment;
    }

    public double getAnnual_percentage() {
        return annual_percentage;
    }

    public void setAnnual_percentage(double annual_percentage) {
        this.annual_percentage = annual_percentage;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getPost_pone_start() {
        return post_pone_start;
    }

    public void setPost_pone_start(int post_pone_start) {
        this.post_pone_start = post_pone_start;
    }

    public int getPost_pone_end() {
        return post_pone_end;
    }

    public void setPost_pone_end(int post_pone_end) {
        this.post_pone_end = post_pone_end;
    }

    public boolean isLinear() {
        return linear;
    }

    public void setLinear(boolean linear) {
        this.linear = linear;
    }

    public boolean isAnnuity() {
        return annuity;
    }

    public void setAnnuity(boolean annuity) {
        this.annuity = annuity;
    }
}
