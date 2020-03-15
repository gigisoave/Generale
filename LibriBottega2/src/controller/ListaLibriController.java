/** <a href="http://www.cpupk.com/decompiler">Eclipse Class Decompiler</a> plugin, Copyright (c) 2017 Chen Chao. **/
package controller;

import java.io.IOException;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.json.JSONException;
import org.json.JSONObject;
import org.picketbox.util.StringUtil;

import libribottega.logic.DBFactory;
import libribottega.logic.ILibriDB;
import libribottega.logic.LibribottegaException;
import libribottega.logic.Libro;

@WebServlet({"/ListaLibriController"})
public class ListaLibriController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String jsonLibro = request.getParameter("libro");
		if (!StringUtil.isNullOrEmpty(jsonLibro))
		{
			Libro l = new Libro(new JSONObject( jsonLibro));	
		}
		else
		{
			String isbn = request.getParameter("isbn");
			ILibriDB ml = DBFactory.GetDB();
			Libro l =new Libro();
			try {
				l = ml.FindByIsbn(isbn);
				if (l == null) {
					l = new Libro();
					l.set_titolo("non trovato");
				}
				HttpSession session = request.getSession();
				session.setAttribute("libro", l);				
				session.setAttribute("generi", Common.Generi());
			}
			catch (JSONException e) {
				l.set_titolo(e.getMessage());
				e.printStackTrace();
			} catch (LibribottegaException e) {
				l.set_titolo(e.getMessage());
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			HttpSession session = request.getSession();
			session.setAttribute("libro", l);
			session.setAttribute("generi", Common.Generi());
			response.setContentType("application/json");
			//response.getWriter().write(l.GetJson().toString());
			//response.getWriter().print(l.GetJson());
			ServletContext sc = getServletContext();
			Common.OpenDaInserire(request, response, sc, l, false);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}