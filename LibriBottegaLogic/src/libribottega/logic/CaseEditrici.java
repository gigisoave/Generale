package libribottega.logic;

public final class CaseEditrici {
	public static final int TOPI_PITTORI_CODE = 9852;
	public static final String TOPI_PITTORI_NAME = "TopiPittori";
	public static final int LAPIS_CODE = 7874;
	public static final String LAPIS_NAME = "Lapis";
	public static final int ORECCHIO_ACERBO_CODE = 9906;
	public static final String ORECCHIO_ACERBO_NAME = "Orecchio Acerbo";
	public static final int BABALIBRI_CODE = 8362;
	public static final String BABALIBRI_NAME = "Babalibri";
	public static final int CARTHUSIA_CODE = 9544;
	public static final String CARTHUSIA_NAME = "Carthusia";
	public static final int SINNOS_CODE = 7609;
	public static final String SINNOS_NAME = "Sinnos";
	
	public static String GetCasaEditrice(String isbn) {
		String ret = "";
		int code = Integer.parseInt(isbn.substring(5, 8));
		switch (code) {
		case TOPI_PITTORI_CODE:
			ret = TOPI_PITTORI_NAME;
			break;
		case LAPIS_CODE:
			ret = LAPIS_NAME;
			break;
		case ORECCHIO_ACERBO_CODE:
			ret = ORECCHIO_ACERBO_NAME;
			break;
		case BABALIBRI_CODE:
			ret = BABALIBRI_NAME;
			break;
		case CARTHUSIA_CODE:
			ret = CARTHUSIA_NAME;
			break;
		case SINNOS_CODE:
			ret = SINNOS_NAME;
			break;
		}
		return ret;
	}
}
