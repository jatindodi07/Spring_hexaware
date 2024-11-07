package com.spring.hexa.model;

import java.sql.Date;

public class Expense {
private int expense_id;

private ExpenseCategory expenseCategory;
private double amount;
private String description;
private Date date;


public int getExpense_id() {
	return expense_id;
}
public void setExpense_id(int expense_id) {
	this.expense_id = expense_id;
}

public ExpenseCategory getExpenseCategoryobj() {
	return expenseCategory;
}
public void setExpenseCategory(ExpenseCategory expenseCategory) {
	this.expenseCategory = expenseCategory;
}
public double getAmount() {
	return amount;
}
public void setAmount(double amount) {
	this.amount = amount;
}
public String getDescription() {
	return description;
}
public void setDescription(String description) {
	this.description = description;
}
public Date getDate() {
	return date;
}
public void setDate(Date date) {
	this.date = date;
}

	
}