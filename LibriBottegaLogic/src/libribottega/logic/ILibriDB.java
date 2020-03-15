package libribottega.logic;

import java.util.ArrayList;

public interface ILibriDB {
	 void insert(Libro libro) throws LibribottegaException;
	 void insertOrUpdate(Libro libro) throws LibribottegaException;
	 ArrayList<Libro> getAll(ViewTypeEnum type) throws LibribottegaException;
	 Libro FindByIsbn(String isbn) throws LibribottegaException;
	 ArrayList<Libro> FindByProp(String prop) throws LibribottegaException;
	 void close();
}
