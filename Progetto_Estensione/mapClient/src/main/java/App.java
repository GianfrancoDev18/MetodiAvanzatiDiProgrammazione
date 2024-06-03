import gui.ConnectionToServer;
import gui.MenuUI;

/**
 * Classe che istanzia il client grafico che cerchera' di collegarsi al server
 */
public class App {
    public static void main(String[] args) {
        if(args.length > 0) {
            // In caso di avvio con argomenti verra' istanziata direttamente la connesione dal menu
            MenuUI menuUI = MenuUI.getInstance(args[0], Integer.parseInt(args[1]));
            menuUI.setVisible(true);

        } else {
            // Crea un form che chiedera' all'utente di inserire l'ip e la porta in cui il server e' collocato
            ConnectionToServer connectionToServer = new ConnectionToServer();
            connectionToServer.setVisible(true);
        }
    }
}
