package database;

/**
 * classe che estende la classe {@link Exception} per modellare il
 * fallimento nella connessione al database.
 *
 */
public class DatabaseConnectionException extends Exception{
    /**
     * Costruttore che chiama il costruttore padre
     * @param msg messaggio d'errore
     */
    DatabaseConnectionException(String msg) {
        super(msg);
    }
}
