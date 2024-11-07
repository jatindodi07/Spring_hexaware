<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import ="java.util.List" %>
<%@ page import ="com.spring.hexa.model.Expense" %>
 <html>
	<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
	<body >
		<%
		List<Expense> expenses = (List<Expense>) request.getAttribute("expenses"); 
		%>
	       <div class="container-fluid" style="margin: 0%; padding: 0px;">
	           <div class="row">
	               <div class="col-lg-12">
					<%@ include file="navbar.jsp" %>	
	               </div>
	           </div>
	                    
	                   <div class="row">
	                        
	                       <div class="col-lg-12">
	                           <table class="table">
	                               <thead>
	                                 <tr>
	                                   <th scope="col">Expense_id</th>
	                                   <th scope="col">Expense Amount</th>
	                                   <th scope="col">Date</th>
									   <th scope="col">Description</th>
	                                   <th scope="col">Category_name</th>
									   <th scope="col">Actions</th>
	                                 </tr>
	                               </thead>
	                               <tbody>
									<% for( Expense c:expenses){
										%>
										<tr>
										  <th scope="row"> <%=c.getExpense_id() %></th>
										  <td><%=c.getAmount() %></td>
										  <td><%=c.getDate() %></td>
										  <td><%=c.getDescription() %></td>
										  <td><%=c.getExpenseCategoryobj().getExpenseCategory() %></td>
										 <td><a href="<%=request.getContextPath() %>/delete-course?cid=<%=c.getExpense_id() %>">Delete</a></td>
										</tr>										
									<%	
									} 
									%>

	                                 
	                               </tbody>
	                             </table>
	                       </div>
	       </div>
		   <div>
		   <a href="<%=request.getContextPath() %>/add/info?username=<%=session.getAttribute("username") %>" <button class="btn btn-primary btn-lg"   type="button">Add an Expense</button></a>
	  </div center>
	    </body>
	   
</html>	