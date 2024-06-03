package database;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * Classe che modella lo schema di una tabella nel
 * database relazionale.
 *
 * @author Leonardo Colucci, Gianfranco De Vincenzzo
 */
public class TableSchema {
	/**
	 * Gestisce l'accesso alla base di dati
	 */
	private DbAccess db;

	/**
	 * Contenitore di {@link Column}
	 */
	private List<Column> tableSchema=new ArrayList<Column>();

	/**
	 * Inner class che rappresenta una colonna all'interno di una tabella relazionale
	 */
	public class Column{
		/**
		 * nome colonna
		 */
		private String name;
		/**
		 * tipo di colonna
		 */
		private String type;

		/**
		 * Costruttore che inizializza gli attributi della
		 * classe
		 * @param name nome colonna
		 * @param type tipo della colonna
		 */
		Column(String name,String type){
			this.name=name;
			this.type=type;
		}

		/**
		 * Restituisce il nome della colonna
		 * @return nome della colonna
		 */
		public String getColumnName(){
			return name;
		}

		/**
		 * Restituisce il tipo di colonna ovvero un numero
		 * @return un booleano se il tipo di colonna e numerico o meno
		 */
		public boolean isNumber(){
			return type.equals("number");
		}

		/**
		 *
		 * @return Stringa che indica il nome e il tipo della colonna
		 */
		public String toString(){
			return name+":"+type;
		}
	}

	/**
	 * Costruttore che avvalora gli attributi della classe, inizializza una connesione con
	 * un db e fa operazioni per transformare gli attributi di SQL in java
	 * @param db gestisce l'accesso alla base di dati
	 * @param tableName nome della tabella
	 * @throws SQLException lancia l'eccezione in caso di una query errata
	 */
	public TableSchema(DbAccess db, String tableName) throws SQLException{
		this.db=db;
		HashMap<String,String> mapSQL_JAVATypes=new HashMap<String, String>();
		//http://java.sun.com/j2se/1.3/docs/guide/jdbc/getstart/mapping.html
		mapSQL_JAVATypes.put("CHAR","string");
		mapSQL_JAVATypes.put("VARCHAR","string");
		mapSQL_JAVATypes.put("LONGVARCHAR","string");
		mapSQL_JAVATypes.put("BIT","string");
		mapSQL_JAVATypes.put("SHORT","number");
		mapSQL_JAVATypes.put("INT","number");
		mapSQL_JAVATypes.put("LONG","number");
		mapSQL_JAVATypes.put("FLOAT","number");
		mapSQL_JAVATypes.put("DOUBLE","number");

		Connection con=db.getConnection();
		DatabaseMetaData meta = con.getMetaData();
		ResultSet res = meta.getColumns(null, null, tableName, null);

		while (res.next()) {
			if(mapSQL_JAVATypes.containsKey(res.getString("TYPE_NAME")))
				tableSchema.add(new Column(
	        				 res.getString("COLUMN_NAME"),
	        				 mapSQL_JAVATypes.get(res.getString("TYPE_NAME")))
	        				 );
	      }
	      res.close();
	    }


	/**
	 * Rescituisce la {@code tableSchema.size()}.
	 * @return la dimensione di tableSchema
	 */
	public int getNumberOfAttributes(){
			return tableSchema.size();
		}


	/**
	 * Restituisce una colonna in posizione index.
	 * @param index indice
	 * @return colonna in posizione index
	 */
	public Column getColumn(int index){
			return tableSchema.get(index);
		}

	}

		     


