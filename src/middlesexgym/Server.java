/* Gym Application Package */
package middlesexgym;

/* Class Requirements & Dependencies */
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class initializes the Server-Side Java Application and starts
 * ClientRequest to achieve Concurrent Multiple Clients Functionality
 */
public class Server {

    // Defining Server Port to listen on
    public static final int port = 5555;

    public static void main(String[] args) {
        try {

            if (args.length > 0)
                new CommandLineRequest(args);

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
 * This class handles Conditional Routing of CLI Command & redirects
 * accordingly.
 *
 */
class CommandLineRequest {

    CommandLineRequest(String[] args) {
        try {
            BookingActions actions = new BookingActions();
            Request req = new Request("ADD");

            // Conditional Routing based on Command Requested
            switch (args[0]) {
            // List All Bookings
            case "LISTALL":
                printString(actions.getAllBookings());
                break;
            // List Bookings by ID
            case "LISTID":
                printString(actions.getBookingsByID(Integer.parseInt(args[1])));
                break;
            // List Bookings for Personal Trainer
            case "LISTPT":
                printString(actions.getBookingsByPT(Integer.parseInt(args[1])));
                break;
            // List Booking for Client
            case "LISTCLIENT":
                printString(actions.getBookingsByClient(Integer.parseInt(args[1])));
                break;
            // List Booking for Specific Day
            case "LISTDAY":
                printString(actions.getBookingsByDate(Date.valueOf(args[1])));
                break;
            // Create New Booking
            case "ADD":
                req.setAdditionalData(Integer.parseInt(args[1]), Integer.parseInt(args[2]), Date.valueOf(args[3]),
                        Time.valueOf(args[4]), Time.valueOf(args[5]), Integer.parseInt(args[6]));
                printString(actions.newBooking(req));
                break;
            // Update Existing Bookings
            case "UPDATE":
                req = new Request("UPDATE", Integer.parseInt(args[1]));
                req.setAdditionalData(Integer.parseInt(args[2]), Integer.parseInt(args[3]), Date.valueOf(args[4]),
                        Time.valueOf(args[5]), Time.valueOf(args[6]), Integer.parseInt(args[7]));
                printString(actions.updateBooking(req));
                break;
            // Delete Existing Bookings
            case "DELETE":
                req = new Request("DELETE", Integer.parseInt(args[1]));
                printString(actions.deleteBooking(req));
                break;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void printString(String response) {
        // Send String Based Response to CLI Output
        System.out.println(response);

        // Exit Application
        System.exit(0);
    }
}

/**
 * This class handles Conditional Routing of Request & redirects accordingly.
 *
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

            while (true) {
                // Receive Request & Other Additional for Processing
                Request req = (Request) in.readObject();

                // Conditional Routing based on Command Requested
                switch (req.getCommand()) {
                // List All Bookings
                case "LISTALL":
                    sendString(actions.getAllBookings());
                    break;
                // List Bookings by ID
                case "LISTID":
                    sendString(actions.getBookingsByID(req.getQuery()));
                    break;
                // List Bookings for Personal Trainer
                case "LISTPT":
                    sendString(actions.getBookingsByPT(req.getQuery()));
                    break;
                // List Booking for Client
                case "LISTCLIENT":
                    sendString(actions.getBookingsByClient(req.getQuery()));
                    break;
                // List Booking for Specific Day
                case "LISTDAY":
                    sendString(actions.getBookingsByDate(req.getDate()));
                    break;
                // Create New Booking
                case "ADD":
                    sendString(actions.newBooking(req));
                    break;
                // Update Existing Bookings
                case "UPDATE":
                    sendString(actions.updateBooking(req));
                    break;
                // Delete Existing Bookings
                case "DELETE":
                    sendString(actions.deleteBooking(req));
                    break;
                }

            }

        } catch (Exception ex) {
            ex.printStackTrace();
            try {
                // Close Input Stream to free up resources
                in.close();
                // Close Output Stream to free up resources
                out.close();
                // Close Socket to free up resources
                socket.close();
            } catch (Exception exp) {

            }
        }
    }

    private void sendString(String response) {
        // Send String Based Response to Client
        out.println(response + "\nEND");
    }
}
