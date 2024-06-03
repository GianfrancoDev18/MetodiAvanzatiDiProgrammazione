package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Classe che servira' per l'accesso alla base di dati.
 *
 * @author Leonardo Colucci, Gianfranco De Vincenzo
 */
public class DbAccess {
    /**
     * Driver per collegarsi al db.
     */
    private String DRIVER_CLASS_NAME = "com.mysql.cj.jdbc.Driver";
    /**
     * DBMS da utilizzare.
     */
    private final String DBMS = "jdbc:mysql";
    /**
     * Server in cui risiede la base di dati.
     */
    private final String SERVER;
    /**
     * nome della basi di dati.
     */
    private final String DATABASE;
    /**
     * La porta in cui il DBMS MySQL accetta le connessioni.
     */
    private final int PORT;
    /**
     * nome dell'utente per l'accesso alla base di dati.
     */
    private final String USER_ID;
    /**
     * pass dell'utente per l'accesso alla base di dati.
     */
    private final String PASSWORD;
    /**
     * gestisce una connessione.
     */
    Connection conn;

    /**
     * Costruttore che inizializza gli attributi della classe.
     * @param server Server in cui risiede la base di dati
     * @param database nome del db
     * @param port numero della porta
     * @param user_id nome utente del db
     * @param password password dell'utente del db
     */
    public DbAccess(String server, String database, int port, String user_id, String password) {
        SERVER = server;
        DATABASE = database;
        PORT = port;
        USER_ID = user_id;
        PASSWORD = password;
    }

    /**
     * Stabilisce una connessione tramite gli attributi della classe.
     * @throws DatabaseConnectionException eccezione che viene lanciata
     * in caso di fallimento nella connessione al database
     */
    public void initConnection() throws DatabaseConnectionException {
        String connString = DBMS + "://" + SERVER + ":" + PORT + "/" + DATABASE
                + "?user=" + USER_ID + "&password=" + PASSWORD + "&serverTimezone=UTC";
        try {
            conn = DriverManager.getConnection(connString);
        } catch (SQLException e){
            throw new DatabaseConnectionException("Impossibile collegarsi al DB");
        }
    }

    /**
     * Restituisce {@link #conn}.
     * @return la connesione
     */
    public Connection getConnection() {
        return conn;
    }

    /**
     * Chiude la connessione in {@link #conn}.
     */
    public void closeConnection(){
        try {
            conn.close();
        } catch (SQLException e) {
            System.out.println("[Errore]: problema nella chiusura della connessione");
        }
    }


}
