/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package middlesexgym;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *S
 * @author LaveshPanjwani
 */
public class Server {

    public static final int port = 5555;

    public static void main(String[] args) {
        try {
            ServerSocket ss = new ServerSocket(port);

            System.out.println("Ready for Connections");

            while (true) {
                Socket socket = ss.accept();

                Thread t = new Thread(new ClientRequest(socket));
                t.start();
            }
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}

class ClientRequest implements Runnable {

    private final Socket socket;
    private final dbActions actions;

    ClientRequest(Socket socket) {
        this.socket = socket;
        this.actions = new dbActions();
    }

    @Override
    public void run() {
        try {
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BackendRequest req = (BackendRequest) in.readObject();

            ArrayList<String> dbResponse = null;

            switch (req.getAction()) {
                case "GET":
                    dbResponse = actions.getBookings();
                    break;
            }

            for (String i : dbResponse) {
                out.println(i);
            }

            in.close();
            out.close();
            socket.close();
        } catch (IOException ex) {
            Logger.getLogger(ClientRequest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ClientRequest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(ClientRequest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(ClientRequest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
