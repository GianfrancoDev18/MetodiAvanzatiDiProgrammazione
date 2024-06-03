import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.rmi.ServerException;

import keyboardinput.Keyboard;

/**
 * Classe utilizzata per definire un client CLI che comunica con il server'
 *
 * @author Leonardo Colucci, Gianfranco De Vincenzo
 */
public class MainTest {

    /**
     * stream di output
     */
    private ObjectOutputStream out;

    /**
     * stream di input
     */
    private ObjectInputStream in;


    /**
     * Costruttore che stabilisce una comunicazione con il server
     * @param ip indirizzo ip del server
     * @param port porta in cui il server e' in ascolto
     * @throws IOException
     */
    public MainTest(String ip, int port) throws IOException{
        InetAddress addr = InetAddress.getByName(ip); //ip
        Socket socket = new Socket(addr, port); //Port
        System.out.println(socket);

        out = new ObjectOutputStream(socket.getOutputStream());
        in = new ObjectInputStream(socket.getInputStream()); // stream con richieste del client
    }

    /**
     * Menu del client, che mostra all'utente le varie operazioni effettuabili
     * @return intero che indica la scelta dell'utente
     */
    private int menu(){
        int answer;
        System.out.println("Scegli una opzione");
        do{
            System.out.println("(1) Carica Cluster da File");
            System.out.println("(2) Carica Dati");
            System.out.print("Risposta: ");
            answer=Keyboard.readInt();
        }
        while(answer<=0 || answer>2);
        return answer;

    }

    /**
     * Metodo che chiede all'utente il path del file che verra' cercato dal server
     * per leggere i centroidi all'interno di esso
     * @return centroidi trovati nel file
     * @throws SocketException
     * @throws ServerException
     * @throws IOException
     * @throws ClassNotFoundException
     */
    private String learningFromFile() throws SocketException, ServerException,IOException,ClassNotFoundException{
        out.writeObject(3);

        System.out.print("Nome tabella: ");
        String tabName=Keyboard.readString();
        out.writeObject(tabName);
        System.out.print("Numero iterate: ");
        int k=Keyboard.readInt();
        out.writeObject(k);
        String result = (String)in.readObject();
        if(result.equals("OK"))
            return (String)in.readObject();
        else throw new ServerException(result);

    }

    /**
     * Metodo che prende in input dal client la tabella che verra' cercata nel db dal server
     * @throws SocketException
     * @throws ServerException
     * @throws IOException
     * @throws ClassNotFoundException
     */
    private void storeTableFromDb() throws SocketException,ServerException,IOException,ClassNotFoundException{
        out.writeObject(0);
        System.out.print("Nome tabella: ");
        String tabName = Keyboard.readString();
        out.writeObject(tabName);
        String result = (String)in.readObject();
        if(!result.equals("OK"))
            throw new ServerException(result);

    }

    /**
     * Metodo che chiede al server di effettuare l'algoritmo del kmeans con un numero di cluster
     * uguale a {@code k}.
     * @return
     * @throws SocketException
     * @throws ServerException
     * @throws IOException
     * @throws ClassNotFoundException
     */
    private String learningFromDbTable() throws SocketException,ServerException,IOException,ClassNotFoundException{
        out.writeObject(1);
        System.out.print("Numero di cluster: ");
        int k = Keyboard.readInt();
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
    private void storeClusterInFile() throws SocketException,ServerException,IOException,ClassNotFoundException{
        out.writeObject(2);
        String result = (String)in.readObject();
        if(!result.equals("OK")) {
            throw new ServerException(result);
        }

    }

    /**
     * Main del client in cui verranno chiamati i metodi per comunicare con il server
     * @param args argomenti contenenti l'indirizzo ip e la porta per comincare al server
     */
    public static void main(String[] args) {
        String ip = args[0];
        int port = Integer.parseInt(args[1]);
        MainTest main = null;

        try{
            main = new MainTest(ip, port);
        } catch (IOException e) {
            System.out.println(e);
            return;
        }

        do{
            int menuAnswer = main.menu();
            switch(menuAnswer) {
                case 1:
                    try {
                        String kmeans=main.learningFromFile();
                        System.out.println(kmeans);
                    } catch (SocketException e) {
                        System.err.println(e);
                        return;
                    } catch (FileNotFoundException e) {
                        System.err.println(e);
                        return ;
                    } catch (IOException e) {
                        System.err.println(e);
                        return;
                    } catch (ClassNotFoundException e) {
                        System.err.println(e);
                        return;
                    }
                    break;
                case 2: // learning from db
                    while(true){
                        try{
                            main.storeTableFromDb();
                            break; //esce fuori dal while
                        } catch (SocketException e) {
                            System.err.println(e);
                            return;
                        } catch (FileNotFoundException e) {
                            System.err.println(e);
                            return;
                        } catch (IOException e) {
                            System.err.println(e);
                            return;
                        } catch (ClassNotFoundException e) {
                            System.err.println(e);
                            return;
                        }
                    } //end while [viene fuori dal while con un db (in alternativa il programma termina)

                    char answer = 'y'; //itera per learning al variare di k
                    do{
                        try {
                            String clusterSet = main.learningFromDbTable();
                            System.out.println(clusterSet);
                            main.storeClusterInFile();
                        } catch (SocketException e) {
                            System.err.println(e);
                            return;
                        } catch (FileNotFoundException e) {
                            System.err.println(e);
                            return;
                        } catch (ClassNotFoundException e) {
                            System.err.println(e);
                            return;
                        } catch (IOException e) {
                            System.err.println(e);
                            return;
                        }
                        System.out.print("Vuoi ripetere l'esecuzione? (y/n): ");
                        answer=Keyboard.readChar();
                    } while(answer == 'y');
                    break; //fine case 2
                default: {
                    System.out.println("Opzione non valida!");
                }
            }

            System.out.print("Vuoi scegliere una nuova operazione da menu? (y/n): ");
            if(Keyboard.readChar()!='y') {
                break;
            }
        } while(true);


    }
}



