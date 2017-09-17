/** <a href="http://www.cpupk.com/decompiler">Eclipse Class Decompiler</a> plugin, Copyright (c) 2017 Chen Chao. **/
package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.json.JSONException;

import utility.DBFactory;
import utility.ILibriDB;
import utility.LibribottegaException;
import utility.Libro;

@WebServlet({"/ListaLibriController"})
public class ListaLibriController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String isbn = request.getParameter("isbn");
		ILibriDB ml = DBFactory.GetDB();
		try {
			Libro l = ml.FindByIsbn(isbn);
			HttpSession session = request.getSession();
			session.setAttribute("libro", l);
			session.setAttribute("generi", Common.Generi());
		} catch (JSONException e) {
			e.printStackTrace();
		} catch (LibribottegaException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}