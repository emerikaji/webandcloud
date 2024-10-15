package foo;

import java.io.IOException;
import java.util.*;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.KeyRange;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.PropertyProjection;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.CompositeFilterOperator;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;

import com.google.appengine.repackaged.com.google.datastore.v1.CompositeFilter;
import com.google.appengine.repackaged.com.google.datastore.v1.Projection;
import com.google.appengine.repackaged.com.google.datastore.v1.PropertyFilter;

@WebServlet(name = "FriendQuery", urlPatterns = { "/query" })
public class FriendQuery extends HttpServlet {

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		DatastoreService datastore =
				DatastoreServiceFactory.getDatastoreService();

		// Exercice 1

		try {
			response.getWriter().print("Age de f0 : " + datastore.get(new Entity("Friend", "f0").getKey()).getProperty("age"));
		} catch (EntityNotFoundException e) {
			e.printStackTrace();
		}

		// Exercice 2

		Query q2 =
				new Query("Friend")
						.setFilter(new FilterPredicate("age", FilterOperator.EQUAL, "30"));

		List<Entity> result2 =
				datastore.prepare(q2).asList(FetchOptions.Builder.withLimit(5));

		response.getWriter().print("Utilisateurs ages de 30 ans : "+ result2.toString());

		// Exercice 3

		Query q3 =
				new Query("Friend")
						.setFilter(new FilterPredicate("name", FilterOperator.GREATER_THAN_OR_EQUAL, "f14"))
						.setFilter(new FilterPredicate("name", FilterOperator.LESS_THAN, "f15"));

		List<Entity> result3 =
				datastore.prepare(q3).asList(FetchOptions.Builder.withDefaults());

		response.getWriter().print("Utilisateurs qui commencent par f14 : "+ result3.toString());

		/*
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");

//		Entity e = new Entity("Friend", "f" + i);
//		e.setProperty("firstName", "first" + i);
//		e.setProperty("lastName", "last" + i);
//		e.setProperty("age", r.nextInt(100) + 1);
//      e.setProperty("friends", fset);

		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

		response.getWriter().print("<h1> Friends Queries </h1>");;

		response.getWriter().print("<h2> is f0 exist ? </h2>");

		
		Entity e=new Entity("Friend","f0");
		try {
			Entity e1=datastore.get(e.getKey());
			response.getWriter().print("<li> Get F0:" + e1.getProperty("firstName"));
		} catch (EntityNotFoundException e1) {
			e1.printStackTrace();
		}
		
		
		response.getWriter().print("<h2> all friends with firstname first0 ? </h2>");
		
		Query q = new Query("Friend").setFilter(new FilterPredicate("firstName", FilterOperator.EQUAL, "first0"));

		PreparedQuery pq = datastore.prepare(q);
		List<Entity> result = pq.asList(FetchOptions.Builder.withDefaults());

		response.getWriter().print("<li> result:" + result.size() + "<br>");
		for (Entity entity : result) {
			response.getWriter().print("<li>" + entity.getProperty("firstName") + "," + entity.getProperty("lastName")
					+ "," + entity.getProperty("age"));
		}


		response.getWriter().print("<h2> all friends that have  f94 and f93 as friends and age >67 and age < 96  ? </h2>");
		response.getWriter().print("<h3>need composite index ? </h3>");
		
		q = new Query("Friend")
				.setFilter(CompositeFilterOperator.and(
						new FilterPredicate("friends", FilterOperator.EQUAL, "f94"),
						new FilterPredicate("friends", FilterOperator.EQUAL, "f93"),
						new FilterPredicate("age", FilterOperator.GREATER_THAN_OR_EQUAL, 67),
						new FilterPredicate("age", FilterOperator.LESS_THAN_OR_EQUAL, 96) //and >= ??
						)); //and >= ??
		
		pq = datastore.prepare(q);
		result = pq.asList(FetchOptions.Builder.withDefaults());

		response.getWriter().print("<li> result:" + result.size() + "<br>");
		for (Entity entity : result) {
			response.getWriter().print("<li>" + entity.getProperty("firstName"));
		}


		long t1=System.currentTimeMillis();


		response.getWriter().print("<h2> Q1:just print all friends.... </h2>");		
		q = new Query("Friend");
		pq = datastore.prepare(q);
		result = pq.asList(FetchOptions.Builder.withDefaults());

		response.getWriter().print("<li> result:" + result.size() + "<br>");
		for (Entity entity : result) {
		    response.getWriter().print(entity.getProperty("firstName")+";");
		}
		long t2=System.currentTimeMillis();

		
		response.getWriter().print("<h2> Q2: now just print all friends with only firstName projected.... </h2>");		
		q = new Query("Friend");
		q.addProjection(new PropertyProjection("firstName",String.class));
		pq = datastore.prepare(q);
		result = pq.asList(FetchOptions.Builder.withDefaults());

		response.getWriter().print("<li> result:" + result.size() + "<br>");
		for (Entity entity : result) {
		    response.getWriter().print(entity.getProperty("firstName")+".");
		}
		long t3=System.currentTimeMillis();

		response.getWriter().print("<h2> time(Q1) </h2>");		
		response.getWriter().print("q1:"+(t2-t1));
		
		response.getWriter().print("<h2> time(Q2) </h2>");		
		response.getWriter().print("q2:"+(t3-t2));

		*/
	}
}
