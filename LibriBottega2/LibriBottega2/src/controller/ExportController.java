/** <a href="http://www.cpupk.com/decompiler">Eclipse Class Decompiler</a> plugin, Copyright (c) 2017 Chen Chao. **/
package controller;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONException;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;

import utility.Libro;
import utility.ListOperationEnum;
import utility.MongoLibri;
import utility.ViewTypeEnum;

@WebServlet({"/ExportController"})
public class ExportController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ListOperationEnum type = ListOperationEnum.valueOf(request.getParameter("hiddenButtonName"));
		switch(type) {
		case Export:
			Esporta(response, request);
			break;
		case Rendi:
			try {
				Rendi(response);
			} catch (JSONException e) {
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

	private void Rendi(HttpServletResponse response) throws JsonGenerationException, JsonMappingException, JSONException, IOException {
		MongoLibri ml = new MongoLibri();
		ArrayList<Libro> disponibili = ml.getAll(ViewTypeEnum.InShop);
		for (Libro l: disponibili) {
			l.set_resi(l.GetDisponibili());
			ml.insertOrUpdate(l);
		}
	}
	
	private void Esporta(HttpServletResponse response, HttpServletRequest request) throws IOException {

		String fileName = "export.txt";
		String fileType = "text/plain";
		response.setContentType(fileType);

		response.setHeader("Content-disposition", "attachment; filename=" + fileName);

		OutputStream out = response.getOutputStream();
		MongoLibri ml = new MongoLibri();
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
					text = "ISBN|TITOLO|VENDUTI|PREZZO" + System.getProperty("line.separator");
				text = text + l.get_isbn() + "|" + l.get_titolo() + "|" + l.get_venduti() + "|" + l.get_prezzo()
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