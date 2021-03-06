package adminPackage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class DeactivateServlet
 */

public class DeactivateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	String deactiQuery,searchQuery,fname,lname,dept;
	int id_user,i;
    Connection currentCon;
    ResultSet rs;
    Statement stmt;
    
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeactivateServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		String[] checkboxes=request.getParameterValues("check");
		int lengt=checkboxes.length;
		System.out.println(lengt);
		System.out.println(checkboxes[0]);
		
		try {
			currentCon=loginPackage.ConnectionManager.getConnection();
			stmt=currentCon.createStatement();
		
			for(i=0;i<lengt;i++)
			{
			deactiQuery="update user set active=0 where iduser =" + checkboxes[i];
		
			
				try{
				stmt.executeUpdate(deactiQuery);
				}
				catch(SQLException ex)
				{
				System.out.println(ex);
				}
				request.getRequestDispatcher("PendingServletdummy").forward(request, response);
			}
			
			}
		catch(Throwable e)
		{
			System.out.println("error");
			e.printStackTrace();
		}
		

		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("In deactivate servlet post");
		
		List<String> UserNameList=new ArrayList<String>();
		List<String> UserLastnameList=new ArrayList<String>();
		List<String> UserDeptList=new ArrayList<String>();
		List<String> UserIdList=new ArrayList<String>();
		
		try {
			currentCon=loginPackage.ConnectionManager.getConnection();
		stmt=currentCon.createStatement();
	
		searchQuery="select iduser,first_name,last_name,dept from user where emailid != 'admin'  AND active=1";
		
		rs = stmt.executeQuery(searchQuery);

		while(rs.next())
		{
			//System.out.println(rs.getString("first_name"));
			//System.out.println(rs.getString("last_name"));
			
			UserNameList.add(rs.getString("first_name"));
			UserLastnameList.add(rs.getString("last_name"));
			UserDeptList.add(rs.getString("dept"));
			UserIdList.add(rs.getString("iduser"));
			
			
		}
		
		}
		catch(Throwable e)
		{
			System.out.println("error");
			
		}
		
		request.setAttribute("firstname",UserNameList);
		request.setAttribute("lastname",UserLastnameList);
		request.setAttribute("department", UserDeptList);
		request.setAttribute("Iduser", UserIdList);
		
	
		request.getRequestDispatcher("WEB-INF/adminpage.jsp").forward(request, response);
		
	}
	

}
