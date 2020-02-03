/* Gym Application Package */
package middlesexgym;

/* Class Requirements & Dependencies */
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class initializes the Server-Side Java Application and starts
 * ClientRequest to achieve Concurrent Multiple Clients Functionality
 *
 * @author Lavesh Panjwani (M00692913)
 */
public class Server {

    // Defining Server Port to listen on
    public static final int port = 5555;

    public static void main(String[] args) {
        try {
            // Start Server
            ServerSocket ss = new ServerSocket(port);

            // Inform Admin that the Server is Accepting Connection
            System.out.println("Ready for Connections");

            /*
             * Keep Repeating Process of Accepting Clients & Starting Threads for Multiple
             * Clients Concurrent Access
             */
            while (true) {
                // Accept Request from Client to start connection
                Socket socket = ss.accept();

                // initialize new Thread with ClientRequest Class
                Thread t = new Thread(new ClientRequest(socket));
                // Start Thread
                t.start();
            }
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}

/**
 * This class handles Conditional Routing of Request & redirects accordingly.
 *
 * @author Lavesh Panjwani (M00692913)
 */
class ClientRequest implements Runnable {

    // Stores Socket
    private final Socket socket;
    // Input Stream receives Objects
    private ObjectInputStream in;
    // Output Stream sends Strings
    private PrintWriter out;
    // Booking Actions Class contains all Possible Actions
    private final BookingActions actions;

    // Constructor
    ClientRequest(Socket socket) {
        this.socket = socket;
        this.actions = new BookingActions();
    }

    @Override
    public void run() {
        try {
            // Create Input Stream
            in = new ObjectInputStream(socket.getInputStream());
            // Create Output Stream
            out = new PrintWriter(socket.getOutputStream(), true);

            // Receive Request & Other Additional for Processing
            Request req = (Request) in.readObject();
            // Initialize String for Output
            String response = "";

            // Conditional Routing based on Command Requested
            switch (req.getCommand()) {
            // List All Bookings
            case "LISTALL":
                response = actions.getAllBookings();
                break;
            // List Bookings by ID
            case "LISTID":
                response = actions.getBookingsByID(req.getQuery());
                break;
            // List Bookings for Personal Trainer
            case "LISTPT":
                response = actions.getBookingsByPT(req.getQuery());
                break;
            // List Booking for Client
            case "LISTCLIENT":
                response = actions.getBookingsByClient(req.getQuery());
                break;
            // List Booking for Specific Day
            case "LISTDAY":
                response = actions.getBookingsByDate(req.getDate());
                break;
            // Create New Booking
            case "ADD":
                response = actions.newBooking(req);
                break;
            // Update Existing Bookings
            case "UPDATE":
                response = actions.updateBooking(req);
                break;
            // Delete Existing Bookings
            case "DELETE":
                response = actions.deleteBooking(req);
                break;
            }

            // Send String Based Response to Server
            out.println(response);

            // Close Input Stream to free up resources
            in.close();
            // Close Output Stream to free up resources
            out.close();
            // Close Socket to free up resources
            socket.close();
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(ClientRequest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ClientRequest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(ClientRequest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
