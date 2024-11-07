package com.spring.hexa.repository;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.spring.hexa.exception.InvalidCredentialsException;
import com.spring.hexa.model.Expense;
import com.spring.hexa.model.ExpenseCategory;
import com.spring.hexa.model.User;

@Repository
public class ExpenseRepository {
	@Autowired
	private JdbcTemplate jdbc;

	public User authenticate(String username, String password) throws InvalidCredentialsException {
		
		String sql = "Select * from users where username=? and password = ?";
	
		PreparedStatementCreator psc = new PreparedStatementCreator() {

			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				PreparedStatement pstmt = con.prepareStatement(sql);
				pstmt.setString(1, username);
				pstmt.setString(2, password);
				return pstmt;
				
			}
			
		};
	
		RowMapper<User>  rm = new RowMapper<User>(){

			@Override
			public User mapRow(ResultSet rs, int rowNum) throws SQLException {
				User user = new User();
				user.setId(rs.getInt("user_id"));
				user.setUsername(username);
				user.setPassword(password);
				return user;
			}
			
		};
		List<User> list = jdbc.query(psc, rm);
		 if(list.isEmpty()) {
			 throw new InvalidCredentialsException("Invalid Credentials");
		 }
		 else
		 return list.get(0);
	}

	public List<Expense> fetchExpense(String username) {
		String sql = "select e.* ,ec.category_name from users u join expenses e on u.user_id = e.user_id join expensecategories ec on e.category_id= ec.category_id where u.username =? and e.is_active=true";
		PreparedStatementCreator psc = new PreparedStatementCreator(){

			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				PreparedStatement pstmt = con.prepareStatement(sql);
				pstmt.setString(1, username);
				return pstmt;
				
			}
			
		};
		RowMapper<Expense>  rm = new RowMapper<Expense>(){

			@Override
			public Expense mapRow(ResultSet rs, int rowNum) throws SQLException {
				Expense expense = new Expense();
				expense.setExpense_id(rs.getInt("Expense_id"));
				expense.setAmount(rs.getDouble("amount"));
				expense.setDate(rs.getDate("date"));
				expense.setDescription(rs.getString("description"));
				ExpenseCategory expenseCategory = new ExpenseCategory();
				expenseCategory.setExpenseCategory(rs.getString("category_name"));
				expense.setExpenseCategory(expenseCategory);
				return expense;
				
				
			}
			
		};	
		List<Expense> expenses = jdbc.query(psc, rm);
		return expenses;
		
	}

	public void softDelete(String expenseId) {
		int id = Integer.parseInt(expenseId);
		String sql ="update expenses set is_active = false where expense_id =?";
		PreparedStatementCreator psc = new PreparedStatementCreator(){

			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				PreparedStatement pstmt = con.prepareStatement(sql);
				pstmt.setInt(1, id);
				return pstmt;
				
			}
			
		};
		jdbc.update(psc);
	}

	public int getUserId(String username) {
		String sql = "select user_id from users where username=?";
		PreparedStatementCreator psc = new PreparedStatementCreator() {

			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				PreparedStatement pstmt = con.prepareStatement(sql);
				pstmt.setString(1, username);
				return pstmt;	
			}
			
		};
		
		RowMapper<User>  rm = new RowMapper<User>(){

			@Override
			public User mapRow(ResultSet rs, int rowNum) throws SQLException {
				User user = new User();
				user.setId(rs.getInt("user_id"));
				
				return user;				
				
			}
			
		};	
		List<User> user = jdbc.query(psc, rm);
		
		User obj = user.get(0);
		return obj.getId();
	
	}

	public int getCategoryId(String category) {
		String sql = " select category_id from expensecategories where category_name =?";
		PreparedStatementCreator psc = new PreparedStatementCreator() {

			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				PreparedStatement pstmt = con.prepareStatement(sql);
				pstmt.setString(1, category);
				return pstmt;	
			}
			
		};
		
		RowMapper<ExpenseCategory>  rm = new RowMapper<ExpenseCategory>(){

			@Override
			public ExpenseCategory mapRow(ResultSet rs, int rowNum) throws SQLException {
				ExpenseCategory ec = new ExpenseCategory();
				ec.setId(rs.getInt("category_id"));
				
				return ec;				
				
			}
			
		};	
		List<ExpenseCategory> ec = jdbc.query(psc, rm);
		
		ExpenseCategory obj = ec.get(0);
		return obj.getId();
		
	}

	public void addExpense(int user_id, int category_id, Expense expense) {
		String sql = "INSERT INTO expenses (user_id, category_id, amount, date, description, is_active)"
				+ "VALUES (?, ?, ?, ?, ?, ?)";
		PreparedStatementCreator psc = new PreparedStatementCreator() {

			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				PreparedStatement pstmt = con.prepareStatement(sql);
				pstmt.setInt(1, user_id);
				pstmt.setInt(2, category_id);
				pstmt.setDouble(3, expense.getAmount());
			    pstmt.setDate(4, Date.valueOf(LocalDate.now()));
				pstmt.setString(5, expense.getDescription());
				pstmt.setBoolean(6, true);
				return pstmt;	
			}
			
		};
		
		jdbc.update(psc);
	}

}
