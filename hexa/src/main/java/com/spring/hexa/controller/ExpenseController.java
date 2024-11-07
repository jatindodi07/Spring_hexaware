package com.spring.hexa.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.spring.hexa.exception.InvalidCredentialsException;
import com.spring.hexa.model.Expense;
import com.spring.hexa.model.User;
import com.spring.hexa.service.ExpenseService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class ExpenseController {
@Autowired
private ExpenseService expenseService;

@GetMapping("/")
public String showLogin()
{
	
	return "Login";
}
@GetMapping("/login-form")

      
		public String verifyLogin(HttpServletRequest req ,HttpSession session ) {
	          String username = req.getParameter("username");
	          String password = req.getParameter("password");
	          
	         try {
				User user =  expenseService.authenticate(username,password);
				session.setAttribute("username", user.getUsername());
				//fetch all expenses
			   List <Expense> list =expenseService.fetchExpenses((String)session.getAttribute("username")); 
			   req.setAttribute("expenses", list);
				
				return "dashboard";
			} catch (InvalidCredentialsException e) {
			     req.setAttribute("mssg", e.getMessage());
			     return "Login";
			}
	         
}
@GetMapping("/user-dashboard")
public String goToUserDashboard(HttpServletRequest req,HttpSession session) {
	
	  List <Expense> list =expenseService.fetchExpenses((String)session.getAttribute("username")); 
	
	  req.setAttribute("expenses", list);
		return "dashboard";
}
@GetMapping("/expense/deleted")
public String expenseDelete(HttpServletRequest req,HttpSession session) {
	String cid = req.getParameter("cid");
	expenseService.softDelete(cid);
	 List <Expense> list =expenseService.fetchExpenses((String)session.getAttribute("username")); 

		  req.setAttribute("expenses", list);
	return "redirect:/user-dashboard";
}
	
@GetMapping("/expense/added")

public String addExpense(HttpServletRequest req,HttpSession session) {
	Expense expense = new Expense();
	expense.setAmount(Double.parseDouble(req.getParameter("amount")));
	expense.setDescription(req.getParameter("description"));
	String category = req.getParameter("category");
	String username = (String)session.getAttribute("username");
	expenseService.addExpense(username,expense,category);
	return "redirect:/user-dashboard";
}
@GetMapping("/add/info")
public String takeUserExpenseInfo(HttpServletRequest req,HttpSession session) {
	
	return "form";
	
}
};
