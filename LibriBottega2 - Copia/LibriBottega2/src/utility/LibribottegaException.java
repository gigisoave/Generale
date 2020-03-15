package utility;

public class LibribottegaException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public LibribottegaException(Exception inner) {
		this.initCause(inner);
	}
}
