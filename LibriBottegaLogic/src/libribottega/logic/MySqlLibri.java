package libribottega.logic;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class MySqlLibri implements ILibriDB {
	
	Connection _mysql;

	public MySqlLibri() {
		try {
			_mysql = DriverManager.getConnection("jdbc:mysql://localhost/libribottegadb", "gigi", "gigi");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Override
	public void insert(Libro libro) throws LibribottegaException {
		
	}

	@Override
	public void insertOrUpdate(Libro libro) throws LibribottegaException {
		// TODO Auto-generated method stub

	}

	@Override
	public ArrayList<Libro> getAll(ViewTypeEnum type) throws LibribottegaException {
		// TODO Auto-generated method stub
		ArrayList<Libro> ret = new ArrayList<>();
		try {
			Statement s = _mysql.createStatement();
			ResultSet rs = s.executeQuery(
					"select l.titolo titolo,\r\n" + 
					"	l.prezzo prezzo,\r\n" + 
					"	l.isbn isbn,\r\n" + 
					"	l.quantita quantita,\r\n" + 
					"	l.genere genere,\r\n" + 
					"	ce.nome casaeditrice\r\n" + 
					"	from libri l, caseeditrici ce\r\n" + 
					"	where l.casaEditrice = ce.idcaseeditrici");
			if (rs.first()) {
				do {
					Libro l = new Libro();
					l.set_isbn(rs.getString("isbn"));
					l.set_titolo(rs.getString("titolo"));
					l.set_casaEditrice(rs.getString("casaeditrice"));
					l.set_genere(Genere.values()[rs.getInt("genere")]);
					l.set_prezzo(rs.getDouble("prezzo"));
					l.set_quantità(rs.getInt("quantita"));
					ret.add(l);
				} while (rs.next());
			}
				
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ret;
	}

	@Override
	public Libro FindByIsbn(String isbn) throws LibribottegaException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Libro> FindByProp(String prop) throws LibribottegaException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void close() {
		// TODO Auto-generated method stub

	}

}
