/** <a href="http://www.cpupk.com/decompiler">Eclipse Class Decompiler</a> plugin, Copyright (c) 2017 Chen Chao. **/
package controller;

import java.io.IOException;
import java.io.OutputStream;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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
import libribottega.logic.Libro;
import utility.ListOperationEnum;
import utility.ViewTypeEnum;

@WebServlet({"/ExportController"})
public class ExportController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ListOperationEnum type = ListOperationEnum.valueOf(request.getParameter("hiddenButtonName"));
		switch(type) {
		case Export:
			try {
				Esporta(response, request);
			} catch (LibribottegaException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			break;
		case Rendi:
			try {
				Rendi(response);
			} catch (JSONException | LibribottegaException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		}
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	private void Rendi(HttpServletResponse response) throws LibribottegaException {
		ILibriDB ml = DBFactory.GetDB();
		ArrayList<Libro> disponibili = ml.getAll(ViewTypeEnum.InShop);
		for (Libro l: disponibili) {
			l.set_resi(l.get_resi() + l.GetDisponibili());
			ml.insertOrUpdate(l);
		}
	}
	
	private void Esporta(HttpServletResponse response, HttpServletRequest request) throws IOException, LibribottegaException {

		String fileName = "export.txt";
		String fileType = "text/plain";
		response.setContentType(fileType);

		response.setHeader("Content-disposition", "attachment; filename=" + fileName);

		OutputStream out = response.getOutputStream();
		ILibriDB ml = DBFactory.GetDB();
		ViewTypeEnum type = ViewTypeEnum.All;
		HttpSession sess = request.getSession();
		if (sess.getAttribute("list_type") != null)
			type = (ViewTypeEnum)sess.getAttribute("list_type");
		ArrayList<Libro> libri = ml.getAll(type);
		String text = "";
		for (Libro l : libri) {
			switch (type) {
			case All:
			case InShop:
				if (text.isEmpty())
					text = "ISBN|TITOLO|RESI|PREZZO" + System.getProperty("line.separator");
				if (l.GetDisponibili() > 0) {
					text = text + l.get_isbn() + "|" + l.get_titolo() + "|" + l.GetDisponibili() + "|" + l.get_prezzo()
							+ System.getProperty("line.separator");	
				}
			break;
			case Sold:
				if (text.isEmpty())
					text = "ISBN|TITOLO|VENDUTI|PREZZO|DataVendita" + System.getProperty("line.separator");
				text = text + l.get_isbn() + "|" + l.get_titolo() + "|" + l.get_venduti() + "|" + l.get_prezzo()
				+ "|" + l.get_LastDateVendite().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
				+ System.getProperty("line.separator");	
				break;
			default:
				break;					
			}
		}
		byte[] bytes = text.getBytes();
		out.write(bytes);
		out.flush();
	}
}