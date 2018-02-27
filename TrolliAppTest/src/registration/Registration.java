package registration;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.appengine.api.NamespaceManager;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;

/**
 * Servlet implementation class Registration
 */
@WebServlet("/Registration")
public class Registration extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public Registration() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		try {
			//ob.getOAuth(servletRequest,response);
			response.setContentType("application/json");
			PrintWriter out = response.getWriter();
			List<Entity> list = getRecord();
			ObjectMapper objectMapper = new ObjectMapper();  
			
			  objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
			  objectMapper.writeValue(response.getWriter(), list);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			response.getWriter().print("ERROR !\r\n"+e);
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		DatastoreService ds = DatastoreServiceFactory.getDatastoreService();
	 	
	 	String fullName = request.getParameter("fullName");
	 	String simNumber = request.getParameter("simNumber");
	 	String suburb = request.getParameter("suburb");
	 	String state = request.getParameter("state");
	 	int postCode = Integer.parseInt(request.getParameter("postCode"));
	 	String country = request.getParameter("country");
	 	String email = request.getParameter("email");
	 	String userType = request.getParameter("userType");
	 	
	 	ArrayList<Integer> currentLocation = new ArrayList<>();
	 	currentLocation.add(Integer.parseInt(request.getParameter("latitude")));

	 	currentLocation.add(Integer.parseInt(request.getParameter("longitude")));
	 	NamespaceManager.set("trolli");
	 	Entity e = new Entity("Registration");
	 	
	 	e.setProperty("fullName", fullName);
	 	e.setProperty("simNumber", simNumber);
	 	e.setProperty("suburb", suburb);
	 	e.setProperty("state", state);
	 	e.setProperty("postCode", postCode);
	 	e.setProperty("country", country);
	 	e.setProperty("email", email);
	 	e.setProperty("profileImageUrl", null);
	 	e.setProperty("userType", userType);
	 	e.setProperty("currentLocation", currentLocation);
	 	ds.put(e);
	}

	
	private List<Entity> getRecord() {
		  DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		  NamespaceManager.set("trolli");
		  Query q = new Query("Registration");
		  PreparedQuery pq = datastore.prepare(q);
		  return pq.asList(FetchOptions.Builder.withLimit(50));
		}
}
