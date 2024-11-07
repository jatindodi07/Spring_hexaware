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
@GetMapping("/student-dashboard")
public String goToStudentDashboard(HttpServletRequest req,HttpSession session) {
	//InsertExpense
	Expense expense = new Expense();
	expense.setAmount(Double.parseDouble(req.getParameter("amount")));
	expense.setDescription(req.getParameter("description"));
	String category = req.getParameter("category");
	String username = (String)session.getAttribute("username");
	expenseService.addExpense(username,expense,category);
	System.out.println("Hello");
	//fetch all courses
	  List <Expense> list =expenseService.fetchExpenses((String)session.getAttribute("username")); 
	//List listCourses courseService.fetchAllCourses();
	  req.setAttribute("expenses", list);
		return "dashboard";
}

@GetMapping("/delete-course")
public String deleteCourse(HttpServletRequest req) {
	String cid = req.getParameter("cid");
	expenseService.softDelete(cid);
	return "redirect:/student-dashboard";
}

@GetMapping("/add/info")
public String addExpense(HttpServletRequest req,HttpSession session) {
	
	return "form";
	
}
};
