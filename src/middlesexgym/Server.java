/* Gym Application Package */
package middlesexgym;

/* Class Requirements & Dependencies */
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
 * This class initializes the Server-Side Java Application and starts
 * ClientRequest to achieve Concurrent Multiple Clients Functionality
 * 
 * @author Lavesh Panjwani (M00692913)
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

/**
 * This class handles Conditonal Routing of Request & redirects accordingly.
 * 
 * @author Lavesh Panjwani (M00692913)
 */
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
            String response = null;

            switch (req.getCommand()) {
            case "LISTALL":
                dbResponse = actions.getBookings();
                break;
            case "LISTID":
                System.out.println(req.getQuery());
                dbResponse = actions.getBookingsByID(req.getQuery());
                break;
            case "LISTPT":
                dbResponse = actions.getBookingsByPT(req.getQuery());
                break;
            case "LISTCLIENT":
                dbResponse = actions.getBookingsByClient(req.getQuery());
                break;
            case "LISTDAY":
                dbResponse = actions.getBookingsByDate(req.getDate());
                break;
            case "ADD":
                response = actions.newBooking(req);
                break;
            case "UPDATE":
                response = actions.updateBooking(req);
                break;
            case "DELETE":
                response = actions.deleteBooking(req);
                break;

            }

            if (dbResponse != null) {
                for (String i : dbResponse) {
                    out.println(i);
                }
            } else {
                out.print(response);
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
