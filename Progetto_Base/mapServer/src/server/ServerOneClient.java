package server;

import data.Data;
import data.OutOfRangeSampleSize;
import database.DatabaseConnectionException;
import database.EmptySetException;
import database.NoValuesException;
import mining.ClusterSet;
import mining.KMeansMiner;

import java.io.*;
import java.net.Socket;
import java.sql.SQLException;


/**
 * Classe che estende la classe {@link Thread}, e viene utilizzata per comunicare con
 * un singolo client su di un thread separato da quello principale.
 *
 * Questa classe viene istanziata dal metodo {@code run} di {@code MultiServer} quando
 * un client vuole connettersi al server. Il suo compito principale e' quello di comunicare
 * con il Client ed effettuare le operazioni richieste.
 *
 * @see MultiServer
 * @author Leonardo Colucci, Gianfranco De Vincenzo
 */
class ServerOneClient extends Thread {
    /**
     * Socket utilizzato per la comunicazione con un client.
     */
    private Socket socket;
    /**
     * Stream di output su cui verranno inviate le comunicazioni al client.
     */
    private ObjectOutputStream out;
    /**
     * Stream di input su cui verrano ricevute le comunicazioni del client.
     */
    private ObjectInputStream in;
    /**
     * Classe che si occupa di effettuare l'algoritmo del kmeans
     */
    private KMeansMiner kmeans;

    /**
     * Costruttore della classe inizializza e istanzia gli attributi della classe
     * @param s socket di comunicazione con il client che ha effettuato la connesione
     * @throws IOException Lanciata in caso di errore di comunicazione con il client
     */
    ServerOneClient(Socket s) throws IOException {
        socket = s;
        in = new ObjectInputStream(socket.getInputStream());
        out = new ObjectOutputStream(socket.getOutputStream());
        start();
    }

    /**
     * Riscrive il metodo run della superclasse al fine di gestire
     * le richieste del client e fornire i dati richiesti,
     * e il metodo principale di esecuzione di ServerOneClient
     */
    @Override
    public void run() {
        try {
            try {
                Data data = null;
                KMeansMiner kmeans = null;
                String tabName = "";
                int k = 0;
                while (true) {

                    Integer num = (Integer) in.readObject();
                    switch (num) {
                        case 3: {
                            // learningFromFile
                            tabName = (String) in.readObject();
                            k = (Integer) in.readObject();

                            FileInputStream fileIn = new FileInputStream(".\\clustersetsaved\\" + tabName + "_" + k);
                            ObjectInputStream objectIn = new ObjectInputStream(fileIn);
                            ClusterSet C = (ClusterSet) objectIn.readObject();
                            fileIn.close();
                            objectIn.close();
                            out.writeObject("OK");
                            out.writeObject(C.toString());
                            break;
                        }
                        case 0: {

                            // storeTableFromDB
                            tabName = (String) in.readObject();
                            data = new Data("localhost", "mapdb", 3306, "MapUser", "map", tabName);
                            out.writeObject("OK");
                            break;
                        }
                        case 1: {
                            // learningFromDbTable
                            k = (Integer) in.readObject();
                            kmeans = new KMeansMiner(k);
                            int numIter = kmeans.kmeans(data);
                            out.writeObject("OK");
                            out.writeObject("\nNumero di iterazione: " + numIter
                                    + "\n" + kmeans.getC().toString(data));
                            break;
                        }
                        case 2: {
                            // storeClusterInFile
                            assert kmeans != null;
                            kmeans.salva(tabName + "_" + k);
                            out.writeObject("OK");
                            break;
                        }
                        default: break;
                    }
                }
            } catch (OutOfRangeSampleSize | DatabaseConnectionException | NoValuesException | EmptySetException |
                     IOException | ClassNotFoundException e) {
                out.writeObject("\n[Error]: " + e.getMessage());
            } catch (SQLException e) {
                out.writeObject("\n[Error]: tabella inserita inesistente");
            }
        } catch (IOException e) {
            System.err.println("[Error]: " + e.getMessage());
        } finally {
            try {
                socket.close();
                out.close();
                in.close();
            } catch (IOException e) {
                System.err.println("[Error]: " + e.getMessage());
            }
        }


    }
}
