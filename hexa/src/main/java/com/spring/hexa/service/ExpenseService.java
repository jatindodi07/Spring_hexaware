package com.spring.hexa.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.hexa.exception.InvalidCredentialsException;
import com.spring.hexa.model.Expense;
import com.spring.hexa.model.ExpenseCategory;
import com.spring.hexa.model.User;
import com.spring.hexa.repository.ExpenseRepository;

@Service
public class ExpenseService {
	@Autowired
  private ExpenseRepository expenseRepository;
	public User authenticate(String username, String password) throws InvalidCredentialsException {
		return expenseRepository.authenticate(username , password);
		
	}
	public List<Expense> fetchExpenses(String username) {
		
		return expenseRepository.fetchExpense(username);
		
	}
	public void softDelete(String cid) {
		expenseRepository.softDelete(cid);
	}
	public void addExpense(String username , Expense expense,String category) {
		int user_id = expenseRepository.getUserId(username);
		System.out.println(user_id);
		int category_id = expenseRepository.getCategoryId(category);
		expenseRepository.addExpense(user_id , category_id , expense);
		
		
	}

}
