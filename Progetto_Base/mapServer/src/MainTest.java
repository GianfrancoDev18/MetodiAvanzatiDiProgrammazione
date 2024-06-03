import server.MultiServer;

/**
 * Classe utilizzata per l'esecuzione del server. Esso viene avviato
 * sulla porta 8080 della macchina su cui viene eseguito.
 *
 * @author Leonardo Colucci, Gianfranco De Vincenzo
 */
public class MainTest {
    /**
     * Metodo main della classe. Crea un oggetto {@link MultiServer} che gestisce
     * l'esecuzione del server sulla porta 8080 della macchina locale
     * @param args Argomenti a riga di comando, non utilizzati dall'applicazione
     */
    public static void main(String[] args) {
        MultiServer multiServer = new MultiServer(8080);
    }
}
