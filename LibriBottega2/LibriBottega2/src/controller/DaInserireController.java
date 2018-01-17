/** <a href="http://www.cpupk.com/decompiler">Eclipse Class Decompiler</a> plugin, Copyright (c) 2017 Chen Chao. **/
package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.picketbox.util.StringUtil;

import com.sun.org.apache.xpath.internal.operations.Bool;
import com.sun.xml.internal.ws.util.StringUtils;

import utility.Genere;
import utility.Libro;
import utility.MongoLibri;
import utility.ViewTypeEnum;

@WebServlet({"/DaInserireController"})
public class DaInserireController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException
	{
		String isView = request.getParameter("view");
		String all = request.getParameter("all");
		Boolean isAll = !(StringUtil.isNullOrEmpty(all) || 
				!Boolean.parseBoolean(all));
		if (StringUtil.isNullOrEmpty(isView) || 
				!Boolean.parseBoolean(isView))
		{
			Aggiorna(request,response);
		}
		else
		{
			Load(request, response, isAll);			
		}

	}
	
	protected void Load(HttpServletRequest request, 
			HttpServletResponse response, Boolean isAll)
			throws ServletException, IOException
	{
		String isbn = request.getParameter("ISBN");
		if (!StringUtil.isNullOrEmpty(isbn))
		{
			MongoLibri m = new MongoLibri();
			Libro l = m.FindByIsbn(isbn);
			//HttpSession session = request.getSession();
			//ServletContext sc = getServletContext();
			//session.setAttribute("libro", l);
			//Common.OpenDaInserire(request, response, sc, l, false);
			PrintWriter out = response.getWriter();
			if (!isAll)
				out.print(l.get_isbn());
			else
				out.print(l.GetJsonAll());
			out.flush();
		}
	}

	protected void Aggiorna(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException
	{
		MongoLibri m = null;
		try {
			Libro l = new Libro(request.getParameter("titolo"), request.getParameter("autori"));
			l.set_isbn(request.getParameter("ISBN"));
			l.set_casaEditrice(request.getParameter("casaeditrice"));
			l.set_genere(Genere.valueOf(request.getParameter("genere")));
			l.set_prezzo(Double.parseDouble(request.getParameter("prezzo")));
			l.set_quantità(Integer.parseInt(request.getParameter("quantita")));
			l.set_venduti(Integer.parseInt(request.getParameter("venduti")));

			m = new MongoLibri();
			for (int i = 0; i < l.get_quantita(); ++i) {
				m.insertOrUpdate(l);
			}
			ArrayList<Libro> libri = m.getAll(ViewTypeEnum.All);
			m.close();
			ServletContext sc = getServletContext();
			HttpSession session = request.getSession();
			Boolean batchInsert = Boolean.valueOf(false);
			if (session.getAttribute("batchinsert") != null)
				batchInsert = (Boolean)session.getAttribute("batchinsert");
			if (batchInsert.booleanValue()) {
				Common.OpenInsert(request, response, sc);
			}
			else {
				Common.OpenListaLibri(libri, request, response, sc);
			}
		}
		catch (Exception ex) {
			ServletOutputStream sos = response.getOutputStream();
			sos.write(ex.getMessage().getBytes(), 0, ex.getMessage().length());
			sos.flush();
		}
		finally {
			if (m != null)
				m.close(); 
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}