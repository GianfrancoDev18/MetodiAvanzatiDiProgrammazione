package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Classe utilizzata per modellare un Server multithreaded per la comunicazione con piu' Client.
 * Il server si espone di default sulla porta 8080, e genera una nuova istanza di ServerOneClient
 * ogni volta che viene contatto da un Client.
 *
 * @author Leonardo Colucci, Gianfranco De Vincenzo
 */
public class MultiServer {
    /**
     * Porta di default dove viene locato il server.
     */
    private int PORT = 8080;

    /**
     * Costruttore di MultiServer
     * L'istanziazione di MultiServer equivale all'esecuzione
     * del server sulla macchina, poiche' richiama il metodo {@code run}
     * @param port Porta in cui si vogliono offrire i servizi del server
     */
    public MultiServer(int port) {
        this.PORT = port;
        run();
    }

    /**
     * Metodo principale di esecuzione del server multithread.
     *
     * Il server rimane in attesa della comunicazione da parte di un Client. Nel momento in cui viene
     * contattato da un Client, viene istanziato un nuovo ServerOneClient (che effettuera' la comunicazione
     * vera e propria con il Client).
     */
    private void run() {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(PORT);
            while(true) {
                Socket socket = null;
                try {
                    socket = serverSocket.accept();
                    new ServerOneClient(socket);
                } catch (IOException e) {
                    System.out.println("[Errore]: " + e.getMessage());
                    assert socket != null;
                    socket.close();
                    break;
                }
            }
        } catch (IOException e) {
            System.out.println("[Errore]: " + e.getMessage());
        } finally {
            try {
                assert serverSocket != null;
                serverSocket.close();
            } catch (IOException e) {
                System.err.println("[Errore]: " + e.getMessage());
            }
        }
    }
}
