package com.example.easierhomefinance;

public class Data {
    int income;
    int expense;
    int save;
    int total;
    String date;

    public Data(int income,int expense, int save, String date){
        this.income = income;
        this.expense = expense;
        this.save = save;
        this.total = income + (-1) * expense;
        this.date = date;
    }

}
