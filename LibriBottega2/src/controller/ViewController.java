/** <a href="http://www.cpupk.com/decompiler">Eclipse Class Decompiler</a> plugin, Copyright (c) 2017 Chen Chao. **/
package controller;

import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import libribottega.logic.DBFactory;
import libribottega.logic.ILibriDB;
import libribottega.logic.Libro;
import libribottega.logic.ViewTypeEnum;

@WebServlet({"/ViewController"})
public class ViewController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			ViewTypeEnum type = ViewTypeEnum.valueOf(request.getParameter("hiddenButtonName"));
			
			  String isbn = request.getParameter("ISBN"); HttpSession session =
			  request.getSession(); session.setAttribute("viewstate_index", isbn);
			  session.setAttribute("list_type", type); ILibriDB m = DBFactory.GetDB();
			  ArrayList<Libro> libri = new ArrayList<Libro>(); if ((isbn != null) &&
			  (!(isbn.isEmpty()))) { libri = m.FindByProp(isbn); } else { libri =
			  m.getAll(type); } ServletContext sc = getServletContext();
			  Common.OpenListaLibri(libri, request, response, sc); m.close();
			 
		} catch (Exception ex) {
			ServletOutputStream sos = response.getOutputStream();
			sos.write(ex.getMessage().getBytes(), 0, ex.getMessage().length());
			sos.flush();
		}
	}
}