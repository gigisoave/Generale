/** <a href="http://www.cpupk.com/decompiler">Eclipse Class Decompiler</a> plugin, Copyright (c) 2017 Chen Chao. **/
package controller;

import java.io.IOException;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONException;
import org.json.JSONObject;

import utility.CaseEditrici;
import utility.DBFactory;
import utility.ILibriDB;
import utility.JsonReader;
import utility.Libro;

@WebServlet({"/GetBookController"})
public class GetBookController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			String isbn = request.getParameter("ISBN");
			ILibriDB ml = DBFactory.GetDB();
			//JSONObject jsonLibro = GetFromUrl("http://localhost:8080/LibriBottega_RestfulService/rest/service/findbyisbn/" + isbn);
			Libro l = ml.FindByIsbn(isbn);
			//Libro l = null;
			//9788861425194
			//if (jsonLibro != null)
				//l = new Libro();
			if (l == null) {
				JSONObject json = GetFromUrl("https://www.googleapis.com/books/v1/volumes?q=isbn:" + isbn);
				if (json == null) {
					json = GetFromUrl("https://www.googleapis.com/books/v1/volumes?q=ean:" + isbn);
				}
				l = new Libro();
				l.set_isbn(isbn);
				if (json != null) {
					JSONObject volumeInfo = json.getJSONArray("items").getJSONObject(0).getJSONObject("volumeInfo");
					l.set_titolo(volumeInfo.getString("title"));
					try {
						l.set_autore(volumeInfo.getJSONArray("authors"));
					} catch (Exception e) {
						l.set_autore("AA.VV");
					}
					try {
						String casa = CaseEditrici.GetCasaEditrice(isbn);
						if (casa.isEmpty())
							l.set_casaEditrice(volumeInfo.getString("publisher"));
						else
							l.set_casaEditrice(volumeInfo.getString(casa));
					} catch (Exception localException1) {
					}
				}
			}
			ServletContext sc = getServletContext();
			Common.OpenDaInserire(request, response, sc, l, Boolean.valueOf(true));
		} catch (Exception ex) {
			ServletOutputStream sos = response.getOutputStream();
			sos.write(ex.getMessage().getBytes(), 0, ex.getMessage().length());
			sos.flush();
		}
	}
	
	private JSONObject GetFromUrl(String url) throws IOException {
		JSONObject json = new JSONObject();
		try {
			json = JsonReader.readJsonFromUrl(url);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		boolean existIsbn = false;
		try {
			existIsbn = ((Integer) json.get("totalItems")).intValue() == 1;
		} catch (JSONException e) {
			e.printStackTrace();
		}
		if (!existIsbn)
			json = null;
		return json;
	}
}