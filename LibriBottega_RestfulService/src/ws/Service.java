package ws;

import javax.ws.rs.*;
import javax.ws.rs.core.*;

import utility.DBFactory;
import utility.ILibriDB;
import utility.LibribottegaException;
import utility.Libro;

@Path("service")
public class Service {

	@GET
	@Path("hello")	
	@Produces(MediaType.TEXT_PLAIN)
	public String hello() {
		return "Hello World";
	}
	
	@GET
	@Path("sum/{a}/{b}")
	@Produces(MediaType.TEXT_PLAIN)
	public String sum(@PathParam("a") int a, @PathParam("b") int b) {
		return String.valueOf(a + b);
	}

	@GET
	@Path("findbyisbn/{isbn}")	
	@Produces(MediaType.APPLICATION_JSON)
	public Libro findByIsbn(@PathParam("isbn") String isbn) throws LibribottegaException {
		ILibriDB ml = DBFactory.GetDB();
		return ml.FindByIsbn(isbn);		
	}
}
