package com.demoCRUDApp;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/students")
public class StudentCRUDApp extends HttpServlet {
	
	private String esc(String value) {
	    if (value == null) return "";

	    return value.replace("&", "&amp;")
	                .replace("<", "&lt;")
	                .replace(">", "&gt;")
	                .replace("\"", "&quot;")
	                .replace("'", "&#39;");
	}
	
	   @Override
	    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException  {
		   
		       res.setContentType("text/html; charset=UTF-8");
		       
		       try(Connection con = DBConnectionApp.getConnection();
		    		   PrintWriter out = res.getWriter()){
		    	   
		    	   System.out.println("Database connected: " + !con.isClosed());
		    	   
		    	   out.println("<!DOCTYPE html><html><body bgColor='pink'>");
		    	   out.println("<h1>Student Management System</h1>");

		    	   
		    	     // Create
		    	    out.println("""
		    	    		      <h3>Add Student </h3>
		    	    		      <form method='post' action='students'>
		    	    		        <input type='hidden' name='action' value='create'>
		    	    		        Name: <input type='text' name='name' required>
		    	    		        Email: <input type='text' name='email' required>
	    		        		    Course: <input type='text' name='course' required> 
	    		        		    <button type='submit'>Add</button>	    	    		             
		    	    		      </form>
		    	    		      <hr>
		    	    		      <h3> Student List </h3>
		    	    		      <table border='1' cellpadding='8'>
		    	    		         <tr>
		    	    		        <th>ID</th>
		    	    		        <th>Name</th>
		    	    		        <th>Email</th>
		    	    		        <th>Course</th>
		    	    		        <th>Update</th>
		    	    		        <th>Delete</th>
		    	    		         </tr>    		      	    	    		      
		    	    		""");
		    	    
		    	    // Read:
		    	    String sql =
		    	    	    "SELECT id, name, email, course FROM student_detailsdb.studenttable";

		    	    	try (PreparedStatement ps = con.prepareStatement(sql);
		    	    	     ResultSet rs = ps.executeQuery()) {

		    	    	    while (rs.next()) {
		    	    	        int id = rs.getInt("id");
		    	    	        String name = rs.getString("name");
		    	    	        String email = rs.getString("email");
		    	    	        String course = rs.getString("course");

		    	    		
		    	    		out.println("<tr><td>" + id + "</td>");
		    	    		out.println("<td>" + name + "</td>");
		    	    		out.println("<td>" + email + "</td>");
		    	    		out.println("<td>" + course + "</td>");
		    	    		
		    	    		
                          // Update: 
		    	    		out.println("<td><form method='post' action='students'>");
		    	    		out.println("<input type='hidden' name='action' value='update'>");
		    	    		out.println("<input type='hidden' name='id' value='"+ id +"'>");
		    	    		out.println("<input type='text' name='name' value='"+ esc(name) + "' required>");
		    	    		out.println("<input type='email' name='email' value='" + esc(email) + "' required>");
		    	    		out.println("<input type='text' name='course' value='"+ esc(course) + "' required>");	
		    	    		out.println("<button type='submit'>Update</button>");
		    	    		out.println("</form></td>");
		    	    		
		    	    	// Delete: 
		    	    		out.println("<td><form method='post' action='students'>");
		    	    		out.println("<input type='hidden' name='action' value='delete'>");
		    	    		out.println("<input type='hidden' name='id' value='"+ id +"'>");
		    	    		out.println("<button type='submit'>Delete</button>");
		    	    		out.println("</form></td></tr>");
		    	    	}
		    	    }
		    	    
		    	    out.println("</table></body></html>");
		    	   
		       } catch (SQLException e) {

		    	    e.printStackTrace(); // Shows the actual error in Eclipse Console

		    	    res.sendError(
		    	        HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
		    	        "Database Error: " + e.getMessage()
		    	    );
		    	}
		       
	   }
}
