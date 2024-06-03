package database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.TreeSet;


/**
 * Classe che modella l’insieme di transazioni collezionate
 * in una tabella. La singola transazione è modellata dalla classe
 * {@link Example}
 *
 * @author Leonardo Colucci, Gianfranco De Vincenzo
 * @see Example
 */
public class TableData {
	/**
	 * classe per la gestione del db
	 */
	DbAccess db;

	/**
	 * Costuttore che inizializza gli attributi della classe
	 * @param db gestisce il db
	 */
	public TableData(DbAccess db) {
		this.db=db;
	}

	/**
	 * Ricava lo schema della tabella con nome table. Esegue una interrogazione
	 * per estrarre le tuple distinte da tale tabella. Per ogni tupla del resultset, si crea un oggetto,
	 * istanza della classe Example, il cui riferimento va incluso nella lista da restituire. In particolare,
	 * per la tupla corrente nel resultset, si estraggono i valori dei singoli campi (usando getFloat() o
	 * getString()), e li si aggiungono all’oggetto istanza della classe Example che si sta costruendo.
	 * @param table nome della tabella
	 * @return Lista di transazioni distinte memorizzate nella tabella
	 * @throws SQLException lancia l'eccezione in caso di un problema di query
	 * @throws EmptySetException lancia l'eccezione in caso di un resultSet vuoto
	 */
	public List<Example> getDistinctTransazioni(String table) throws SQLException, EmptySetException {
		TableSchema tableSchema = new TableSchema(db, table);
		Statement s = db.getConnection().createStatement();
		ResultSet r = s.executeQuery("SELECT DISTINCT * " + "FROM " + table + ";");
		List<Example> distinct = new ArrayList<Example>();

		while (r.next()) {
			Example example = new Example();
			for (int i = 0; i < tableSchema.getNumberOfAttributes(); i++) {
				if (tableSchema.getColumn(i).isNumber()) {
					example.add(r.getDouble(i + 1));
				} else {
					example.add(r.getString(i + 1));
				}
			}
			distinct.add(example);
		}

		s.close();
		r.close();
		if (distinct.isEmpty()) {
			throw new EmptySetException("[Errore]: risultato vuoto");
		}
		return distinct;
	}

	/**
	 * Formula ed esegue una interrogazione SQL per estrarre i valori distinti
	 * ordinati di column e popolare un insieme da restituire (scegliere opportunamente in Set da
	 * utilizzare).
	 * @param table nome tabella
	 * @param column colonna
	 * @return Insieme di valori distinti ordinati in modalità ascendente che l’attributo identificato da
	 * nome column assume nella tabella identificata dal nome table
	 * @throws SQLException lancia l'eccezione in caso di un problema di query
	 */
	public  Set<Object> getDistinctColumnValues(String table, TableSchema.Column column) throws SQLException{
		TableSchema tableSchema = new TableSchema(db, table);
		Statement s = db.getConnection().createStatement();
		ResultSet r = s.executeQuery("SELECT DISTINCT " + column.getColumnName() + " FROM " + table + ";");
		Set<Object> distinct = new TreeSet<>();

		while (r.next()) {
			if (column.isNumber()) {
				distinct.add(r.getDouble(column.getColumnName()));
			} else {
				distinct.add(r.getString(column.getColumnName()));
			}
		}

		r.close();
		return distinct;
	}

	/**
	 * Formula ed esegue una interrogazione SQL per estrarre il valore aggregato
	 * (valore minimo o valore massimo) cercato nella colonna di nome column della tabella di nome
	 * table. Il metodo solleva e propaga una NoValueException se il resultset è vuoto o il valore
	 * calcolato è pari a null
	 * @param table nome tabella
	 * @param column colonna
	 * @param aggregate operatore SQL di aggregazione {@link QUERY_TYPE}
	 * @return Aggregato cercato
	 * @throws SQLException lancia l'eccezione in caso di un problema di query
	 * @throws NoValuesException lancia l'eccezione in caso di assenza di un valore
	 * all'interno di un resultSet
	 */
	public Object getAggregateColumnValue(String table, TableSchema.Column column, QUERY_TYPE aggregate) throws SQLException, NoValuesException{
		Statement S = db.getConnection().createStatement();
		String query = "SELECT " + aggregate + "(" + column.getColumnName() + ")" + " FROM " + table + ";";
		ResultSet r = S.executeQuery(query);
		Object aggVal = null;
		int nTuple = 0;

		while (r.next()) {
			nTuple++;
			aggVal = r.getDouble(aggregate + "(" + column.getColumnName() + ")");
		}
		if (nTuple == 0) {
			throw new NoValuesException("[Errore]: nessun valore aggregato");
		}

		return aggVal;
	}


}
