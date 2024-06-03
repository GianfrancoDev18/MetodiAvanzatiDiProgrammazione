package clienthandler;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.rmi.ServerException;


/**
 * Classe che modella le operazioni che il client puo' effettuare
 * e comunica con il server
 *
 * @author Leonardo Colucci, Gianfranco De Vincenzo
 */
public class ClientOptions {
    /**
     * Socket utilizzato per la comunicazione con un client.
     */
    private Socket socket = null;
    /**
     * Stream di output su cui verranno inviate le comunicazioni al client.
     */
    private ObjectOutputStream out;
    /**
     * Stream di input su cui verrano ricevute le comunicazioni del client.
     */
    private ObjectInputStream in ;


    /**
     * Costruttore che inizializza i membri e stabilisce una connessione
     * col server
     * @param ip ip del server
     * @param port porta del server
     * @throws IOException lancia l'eccezione in caso di fallimento di collegamento al server
     */
    public ClientOptions(String ip, int port) throws IOException{
        InetAddress addr = InetAddress.getByName(ip); //ip
        System.out.println("addr = " + addr);
        socket = new Socket(addr, port); //Port
        System.out.println(socket);

        out = new ObjectOutputStream(socket.getOutputStream());
        in = new ObjectInputStream(socket.getInputStream()); // stream con richieste del client
    }

    /**
     * Metodo che chiede all'utente il path del file che verra' cercato dal server
     * per leggere i centroidi all'interno di esso
     * @param tabName nome tabella serve per il path del file
     * @param k numero di cluster serve per il path del file
     * @return centroidi letti da file
     * @throws SocketException
     * @throws ServerException
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public String learningFromFile(String tabName, int k) throws SocketException, ServerException,IOException,ClassNotFoundException{
        out.writeObject(3);

        out.writeObject(tabName);
        out.writeObject(k);
        String result = (String)in.readObject();
        if(result.equals("OK"))
            return (String)in.readObject();
        else {
            throw new ServerException(result);
        }
    }

    /**
     * Metodo che prende in input dal client la tabella che verra' cercata nel db dal server
     * @param tabName nome tabella da trovare nel db
     * @throws SocketException
     * @throws ServerException
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public void storeTableFromDb(String tabName) throws SocketException,ServerException,IOException,ClassNotFoundException{
        out.writeObject(0);
        out.writeObject(tabName);
        String result = (String)in.readObject();
        if(!result.equals("OK"))
            throw new ServerException(result);
    }

    /**
     * Metodo che chiede al server di effettuare l'algoritmo del kmeans con un numero di cluster
     * uguale a {@code k}.
     * @param k numero di cluster
     * @return il clustering output ottenuto tramite l'algoritmo del kmeans
     * @throws SocketException
     * @throws ServerException
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public String learningFromDbTable(int k) throws SocketException,ServerException,IOException,ClassNotFoundException{
        out.writeObject(1);
        out.writeObject(k);
        String result = (String) in.readObject();
        if(result.equals("OK")){
            return "Clustering output: " + in.readObject();
        }
        else throw new ServerException(result);
    }

    /**
     * Memorizza i centroidi del cluster generato in un file
     * @throws SocketException
     * @throws ServerException
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public void storeClusterInFile() throws SocketException,ServerException,IOException,ClassNotFoundException{
        out.writeObject(2);
        String result = (String)in.readObject();
        if(!result.equals("OK")) {
            throw new ServerException(result);
        }
    }

    /**
     * Chiude il socket e gli stream
     * @throws IOException
     */
    public void closeAll() throws IOException {
        in.close();
        out.close();
        socket.close();
    }
}



