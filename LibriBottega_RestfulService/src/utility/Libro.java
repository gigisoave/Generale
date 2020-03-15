/** <a href="http://www.cpupk.com/decompiler">Eclipse Class Decompiler</a> plugin, Copyright (c) 2017 Chen Chao. **/
package utility;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Libro {
	private String _titolo;
	private List<String> _autori;
	private String _casaEditrice;
	private Genere _genere = Genere.nessuno;
	private double _prezzo;
	private int _quantita = 1;
	private String _isbn;
	private int _venduti = 0;
	private int _resi = 0;
	public static final String JSONFLD_LIBRO = "libro";
	public static final String JSONFLD_TITOLO = "_titolo";
	public static final String JSONFLD_CASAEDITRICE = "_casaEditrice";
	public static final String JSONFLD_GENERE = "_genere";
	public static final String JSONFLD_PREZZO = "_prezzo";
	public static final String JSONFLD_QUANTITA = "_quantita";
	public static final String JSONFLD_ISBN = "_isbn";
	public static final String JSONFLD_AUTORISTRING = "_autoriString";
	public static final String JSONFLD_AUTORE = "_autore";
	public static final String JSONFLD_VENDUTI = "_venduti";
	public static final String JSONFLD_RESI = "_resi";
	public static final String JSONFLD_DISPONIBILI = "_disponibili";

	public Libro() {
		this._autori = new ArrayList<String>();
	}

	public Libro(String titolo, String autore) {
		this._titolo = titolo;
		this._autori = Arrays.asList(autore.split("\\s*;\\s*"));
	}

	public Libro(JSONObject json) throws JSONException {
		this._autori = new ArrayList<String>();
		JSONObject volume = json.getJSONObject("libro");
		try {
			set_titolo(volume.getString("_titolo"));
		} catch (Exception localException) {
		}
		try {
			set_autore(volume.getJSONArray("_autore"));
		} catch (Exception localException1) {
		}
		try {
			set_casaEditrice(volume.getString("_casaEditrice"));
		} catch (Exception localException2) {
		}
		try {
			set_quantità(volume.getInt("_quantita"));
		} catch (Exception localException3) {
		}
		try {
			set_venduti(volume.getInt("_venduti"));
		} catch (Exception localException4) {
		}
		try {
			set_resi(volume.getInt("_resi"));
		} catch (Exception localException5) {
		}
		try {
			set_genere(Genere.valueOf(volume.getString("_genere")));
		} catch (Exception localException6) {
		}
		try {
			set_prezzo(volume.getDouble("_prezzo"));
		} catch (Exception localException7) {
		}
		try {
			set_isbn(volume.getString("_isbn"));
		} catch (Exception localException8) {
		}
	}


	public JSONObject GetJsonAll() {
		JSONObject json = GetJson();
		json.put(JSONFLD_DISPONIBILI, this.GetDisponibili());
		return json;		
	}
	public JSONObject GetJsonAutore() {
		JSONObject json = GetJson();
		json.put(JSONFLD_AUTORE, this.get_autore());
		return json;		
	}
	public JSONObject GetJson() {
		JSONObject json = new JSONObject();
		json.put(JSONFLD_TITOLO, this.get_titolo());
		json.put(JSONFLD_AUTORISTRING, this.get_autoriString());
		json.put(JSONFLD_CASAEDITRICE, this.get_casaEditrice());
		json.put(JSONFLD_GENERE, this.get_genere());
		json.put(JSONFLD_ISBN, this.get_isbn());
		json.put(JSONFLD_PREZZO, this.get_prezzo());
		json.put(JSONFLD_QUANTITA, this.get_quantita());
		json.put(JSONFLD_RESI, this.get_resi());
		json.put(JSONFLD_VENDUTI, this.get_venduti());
		return json;
	}
	
	public String get_titolo() {
		return this._titolo;
	}

	public void set_titolo(String titolo) {
		this._titolo = titolo;
	}

	public List<String> get_autore() {
		return this._autori;
	}

	public String get_autoriString() {
		String ret = "";
		for (String a : this._autori)
			ret = ret + a + ";";
		return ret;
	}

	public void set_autore(String autori) {
		this._autori = Arrays.asList(autori.split("\\s*;\\s*"));
	}

	public void set_autore(JSONArray jsonArray) {
		for (int i = 0; i < jsonArray.length(); ++i)
			try {
				this._autori.add(jsonArray.getString(i));
			} catch (JSONException e) {
				e.printStackTrace();
			}
	}

	public String get_casaEditrice() {
		return this._casaEditrice;
	}

	public void set_casaEditrice(String _casaEditrice) {
		this._casaEditrice = _casaEditrice;
	}

	public Genere get_genere() {
		return this._genere;
	}

	public void set_genere(Genere _genere) {
		this._genere = _genere;
	}

	public double get_prezzo() {
		return this._prezzo;
	}

	public void set_prezzo(double _prezzo) {
		this._prezzo = _prezzo;
	}

	public int get_quantita() {
		return this._quantita;
	}

	public void set_quantità(int _quantita) {
		this._quantita = _quantita;
	}

	public int get_venduti() {
		return this._venduti;
	}

	public void set_venduti(int _venduti) {
		this._venduti = _venduti;
	}

	public int get_resi() {
		return this._resi;
	}

	public void set_resi(int _resi) {
		this._resi = _resi;
	}

	public String get_isbn() {
		return this._isbn;
	}

	public void set_isbn(String _isbn) {
		this._isbn = _isbn;
	}

	public int GetDisponibili() {
		return (this._quantita - this._venduti - this._resi);
	}
}