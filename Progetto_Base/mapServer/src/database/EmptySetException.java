package database;


/**
 * Classe che estende la classe {@link Exception},
 * e serve per modellare la restituzione di un resultset vuoto.
 */
public class EmptySetException extends Exception {
    /**
     * Costruttore che chiama il costruttore padre.
     * @param msg messaggio d'errore
     */
    public EmptySetException(String msg) {
        super(msg);
    }
}
