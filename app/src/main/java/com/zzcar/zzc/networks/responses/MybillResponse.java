package com.zzcar.zzc.networks.responses;

/**
 * 描述：
 * 作者：黄如辉  时间 2017/5/7.
 */

public class MybillResponse {
    private double balance;
    private double income;
    private double expenses;
    private double unconfirmed;
    private double deposited;
    private double depositeding;
    private double trade_deposit;
    private double receipt_eposit;
    private boolean freeze;
    private double integral;

    public MybillResponse(double balance, double income, double expenses, double unconfirmed,
                          double deposited, double depositeding, double trade_deposit, double receipt_eposit,
                          boolean freeze, double integral) {
        this.balance = balance;
        this.income = income;
        this.expenses = expenses;
        this.unconfirmed = unconfirmed;
        this.deposited = deposited;
        this.depositeding = depositeding;
        this.trade_deposit = trade_deposit;
        this.receipt_eposit = receipt_eposit;
        this.freeze = freeze;
        this.integral = integral;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public double getIncome() {
        return income;
    }

    public void setIncome(double income) {
        this.income = income;
    }

    public double getExpenses() {
        return expenses;
    }

    public void setExpenses(double expenses) {
        this.expenses = expenses;
    }

    public double getUnconfirmed() {
        return unconfirmed;
    }

    public void setUnconfirmed(double unconfirmed) {
        this.unconfirmed = unconfirmed;
    }

    public double getDeposited() {
        return deposited;
    }

    public void setDeposited(double deposited) {
        this.deposited = deposited;
    }

    public double getDepositeding() {
        return depositeding;
    }

    public void setDepositeding(double depositeding) {
        this.depositeding = depositeding;
    }

    public double getTrade_deposit() {
        return trade_deposit;
    }

    public void setTrade_deposit(double trade_deposit) {
        this.trade_deposit = trade_deposit;
    }

    public double getReceipt_eposit() {
        return receipt_eposit;
    }

    public void setReceipt_eposit(double receipt_eposit) {
        this.receipt_eposit = receipt_eposit;
    }

    public boolean isFreeze() {
        return freeze;
    }

    public void setFreeze(boolean freeze) {
        this.freeze = freeze;
    }

    public double getIntegral() {
        return integral;
    }

    public void setIntegral(double integral) {
        this.integral = integral;
    }
}
// "balance": 2.0,余额，含待确认
//         "income": 3.0,累计收入
//         "expenses": 4.0,累计支出
//         "unconfirmed": 5.0,待确认
//         "deposited": 6.0,已提现
//         "depositeding": 7.0,提现中
//         "trade_deposit": 8.0,交易可提现
//         "receipt_eposit": 9.0,收款可提现
//         "freeze": true,账务冻结
//         "integral": 11积分