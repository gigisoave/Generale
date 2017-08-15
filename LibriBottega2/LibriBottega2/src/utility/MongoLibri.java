/** <a href="http://www.cpupk.com/decompiler">Eclipse Class Decompiler</a> plugin, Copyright (c) 2017 Chen Chao. **/
package utility;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.util.JSON;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Pattern;

import org.bson.Document;
import org.json.JSONException;
import org.json.JSONObject;

public class MongoLibri {
	MongoClient _client;
	MongoDatabase _db;
	MongoCollection<Document> _libriCollection;

	public MongoLibri() {
		MongoClientURI uri = new MongoClientURI("mongodb://gigi:gigi@ds113670.mlab.com:13670/bottegalegnago");
		this._client = new MongoClient(uri);
		this._db = this._client.getDatabase(uri.getDatabase());
		this._libriCollection = this._db.getCollection("libri2");
	}

	public void close() {
		this._client.close();
	}

	public void insert(Libro libro) throws JsonGenerationException, JsonMappingException, IOException {
		ObjectMapper map = new ObjectMapper();
		String jsonLibro = map.writeValueAsString(libro);
		Document dl = new Document("libro", JSON.parse(jsonLibro));
		this._libriCollection.insertOne(dl);
	}

	public void insertOrUpdate(Libro libro)
			throws JsonGenerationException, JsonMappingException, IOException, JSONException {
		Libro l = FindByIsbn(libro.get_isbn());
		ObjectMapper map = new ObjectMapper();
		String jsonLibro = map.writeValueAsString(libro);
		Document dl = new Document("libro", JSON.parse(jsonLibro));
		if (l == null) {
			this._libriCollection.insertOne(dl);
		} else {
			BasicDBObject whereQuery = new BasicDBObject();
			whereQuery.put("libro._isbn", libro.get_isbn());
			this._libriCollection.replaceOne(whereQuery, dl);
		}
	}

	public ArrayList<Libro> getAll(ViewTypeEnum type) {
		ArrayList<Libro> libri = new ArrayList<Libro>();
		FindIterable<Document> found = null;
		switch (type) {
			case All :
				found = getAll();
				LibriByCursor(libri, found);
				break;
			case InShop :
				libri = getInShop2();
				break;
			case Sold :
				found = getSold();
				LibriByCursor(libri, found);
		}

		return libri;
	}

	private void LibriByCursor(ArrayList<Libro> libri, FindIterable<Document> found) {
		for (Document document : found)
			try {
				Libro l = new Libro(new JSONObject(document.toJson()));
				libri.add(l);
			} catch (Exception e) {
				e.printStackTrace();
			}
	}

	private FindIterable<Document> findSort(String clauseString, String sortClauseString) {
		BasicDBObject sortClause = BasicDBObject.parse(sortClauseString);
		if (clauseString != "") {
			BasicDBObject clause = BasicDBObject.parse(clauseString);
			return this._libriCollection.find(clause).sort(sortClause);
		}

		return this._libriCollection.find().sort(sortClause);
	}

	private FindIterable<Document> findSort(String clause) {
		return findSort(clause, "{\"libro._genere\": 1, \"libro._titolo\":1}");
	}

	private FindIterable<Document> getSold() {
		String clause = "{ \"libro._venduti\": {\"$gt\": 0} }";

		return findSort(clause);
	}

	@SuppressWarnings("unused")
	private FindIterable<Document> getInShop() {
		String query = "{\"$or\":[{\"$and\":[{\"libro._venduti\": {\"$exists\": true} },{\"libro._resi\": {\"$exists\": true} },{$where: \"this._prezzo > 0\"}]},{\"$and\":[{\"libro._venduti\": {\"$exists\": true} },{\"libro._resi\": {\"$exists\": false} },{\"$where\":\"this._quantita>this._venduti\"}]},{\"$and\":[{\"libro._venduti\": {\"$exists\": false} },{\"libro._resi\": {\"$exists\": true} },{\"$where\": \"this._quantita > this._resi\"}]},{\"$and\":[{\"libro._venduti\": {\"$exists\": false} },{\"libro._resi\": {\"$exists\": false} }]}]}";

		return findSort(query);
	}

	private ArrayList<Libro> getInShop2() {
		FindIterable<Document> cursor = getAll();
		ArrayList<Libro> ret = new ArrayList<Libro>();
		for (Document document : cursor) {
			try {
				Libro l = new Libro(new JSONObject(document.toJson()));
				if (l.get_quantita() > l.get_resi() + l.get_venduti())
					ret.add(l);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return ret;
	}

	public Libro FindByIsbn(String isbn) throws JSONException {
		Libro ret = null;
		BasicDBObject clauseIsbn = new BasicDBObject();
		clauseIsbn.put("libro._isbn", isbn);

		FindIterable<Document> cursor = this._libriCollection.find(clauseIsbn);
		if (cursor != null) {
			Document d = (Document) cursor.first();
			if (d != null) {
				ret = new Libro(new JSONObject(d.toJson()));
			}
		}
		return ret;
	}

	public ArrayList<Libro> FindByProp(String prop) throws JSONException {
		ArrayList<Libro> ret = new ArrayList<Libro>();
		BasicDBObject clauseIsbn = new BasicDBObject();
		clauseIsbn.put("libro._isbn", prop);
		BasicDBObject clauseTitolo = new BasicDBObject();
		clauseTitolo.put("libro._titolo", Pattern.compile(Pattern.quote(prop)));
		BasicDBList or = new BasicDBList();
		or.add(clauseIsbn);
		or.add(clauseTitolo);
		BasicDBObject sortClause = BasicDBObject.parse("{\"libro._genere\": 1, \"libro._titolo\":1}");
		BasicDBObject query = new BasicDBObject("$or", or);

		FindIterable<Document> cursor = this._libriCollection.find(query).sort(sortClause);
		LibriByCursor(ret, cursor);
		return ret;
	}

	private FindIterable<Document> getAll() {
		return findSort("");
	}
}