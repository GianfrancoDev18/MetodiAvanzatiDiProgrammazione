package database;

/**
 * Classe che estende {@link Exception} per avere un eccezzione
 * controllata, in modo tale da verificare l'assenza di un valore
 * all'interno di un resultSet
 */
public class NoValuesException extends Exception {
    /**
     * Costruttore che chiama il costruttore padre
     * @param msg messaggio d'errore
     */
    public NoValuesException(String msg){
        super(msg);
    }
}
