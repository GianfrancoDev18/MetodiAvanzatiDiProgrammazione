package data;

/**
 * Classe che estende {@link Exception}, eccezione che controlla il numero k di cluster inserito
 * da tastiera è maggiore maggiore rispetto al numero di centroidi generabili
 * dall'insieme di transazioni.
 */
public class OutOfRangeSampleSize extends Exception {
    /**
     * Costruttore che chiama il costruttore padre
     * @param message messaggio d'errore
     */
    public OutOfRangeSampleSize(String message){
        super(message);
    }
}
