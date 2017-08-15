/** <a href="http://www.cpupk.com/decompiler">Eclipse Class Decompiler</a> plugin, Copyright (c) 2017 Chen Chao. **/
package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import utility.Genere;
import utility.Libro;

public class Common {
	public static void OpenListaLibri(ArrayList<Libro> libri, HttpServletRequest request, HttpServletResponse response,
			ServletContext sc) throws ServletException, IOException {
		HttpSession session = request.getSession();
		session.setAttribute("libri", libri);

		RequestDispatcher dispatcher = sc.getRequestDispatcher("/ListaLibri.jsp");
		dispatcher.forward(request, response);
	}
///
	public static void OpenInsert(HttpServletRequest request, HttpServletResponse response, ServletContext sc)
			throws ServletException, IOException {
		RequestDispatcher dispatcher = sc.getRequestDispatcher("/Index.jsp");
		dispatcher.forward(request, response);
	}

	public static void OpenDaInserire(HttpServletRequest request, HttpServletResponse response, ServletContext sc,
			Libro l, Boolean batchinsert) throws ServletException, IOException {
		HttpSession session = request.getSession();
		session.setAttribute("libro", l);
		session.setAttribute("batchinsert", batchinsert);
		session.setAttribute("generi", Generi());
		RequestDispatcher dispatcher = sc.getRequestDispatcher("/DaInserire.jsp");
		dispatcher.forward(request, response);
	}

	public static ArrayList<String> Generi() {
		ArrayList<String> ret = new ArrayList<String>();
		String[] array = Arrays.toString(Genere.values()).replaceAll("^.|.$", "").split(", ");
		for (String genere : array) {
			ret.add(genere.replaceAll("\"", ""));
		}
		return ret;
	}
}