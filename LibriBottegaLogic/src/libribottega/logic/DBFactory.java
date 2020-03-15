package libribottega.logic;

public class DBFactory {

	public static ILibriDB GetDB() {
		return new MongoLibri();
		//return new MySqlLibri();
	}
}
