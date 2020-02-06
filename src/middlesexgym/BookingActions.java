/* Gym Application Package */
package middlesexgym;

/* Class Requirements & Dependencies */
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * This class deals with performance SQL commands required for the GYM
 * Application.
 * 
 * @author Lavesh Panjwani (M00692913)
 */
public class BookingActions {

    // Contains JDBC Connection to the SQL Database
    private final Database db = new Database();
    // Lock Definition for allowing concurrency
    private final ReentrantLock lock = new ReentrantLock();
    // Booking Conditional Lock
    private final Condition bookingCondition = lock.newCondition();

    /*
     * Retrieve Booking Details from ResultSet to String
     */
    private String extractBookings(ResultSet dbRes) throws SQLException {
        // Initialling Empty String
        String bookings = "";

        // Output All Bookings in String Separated by New Line
        while (dbRes.next()) {
            // Output Booking ID
            bookings += "ID: " + dbRes.getInt("id");
            // Output Client Name
            bookings += " Client: " + dbRes.getString("client");
            // Output Trainer Name
            bookings += " Trainer: " + dbRes.getString("trainer");
            // Output Date
            bookings += " Date: " + dbRes.getDate("date");
            // Output Start Time
            bookings += " Start Time: " + dbRes.getTime("startTime");
            // Output End Time
            bookings += " End Time: " + dbRes.getTime("endTime");
            // Output Focus ID
            bookings += " Focus: " + dbRes.getString("focus") + "\n";
        }

        // Return Bookings String
        return bookings;

    }

    /*
     * Count Results
     */
    private int resultCount(ResultSet dbRes) {
        try {
            dbRes.next();
            return dbRes.getInt(1);
        } catch (Exception ex) {
            return 0;
        }
    }

    /*
     * Check if Clients, PT & Focus Entries are present in Database
     */
    private String checkClientsPTExists(int clientID, int ptID, int focusID) {
        int clientCount = resultCount(
                db.runQuery("SELECT COUNT(*) AS COUNT FROM GYM.client WHERE id=" + clientID + ";"));
        if (clientCount < 1)
            return "Error - No Client with ID " + clientID + " Exists!";

        int ptCount = resultCount(db.runQuery("SELECT COUNT(*) AS COUNT FROM GYM.staff WHERE id=" + ptID + ";"));
        if (ptCount < 1)
            return "Error - No Trainer with ID " + ptID + " Exists!";

        int focusCount = resultCount(db.runQuery("SELECT COUNT(*) AS COUNT FROM GYM.focus WHERE id=" + focusID + ";"));
        if (focusCount < 1)
            return "Error - No Focus with ID " + focusID + " Exists!";

        return null;
    }

    private void bookingConditionAwait(int query) throws RuntimeException {
        if (resultCount(db.runQuery("SELECT COUNT(*) AS COUNT FROM GYM.bookings WHERE id=" + query + ";")) < 1) {
            try {
                bookingCondition.await();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /*
     * Retrieve All Bookings from the Database
     */
    public String getAllBookings() throws SQLException {
        ResultSet dbRes = db.runQuery("SELECT bookings.id, CONCAT(client.name, '"
                + " (ID ', client.id, ')') AS client, " + "CONCAT(staff.name, ' (ID ', staff.id, ')') AS trainer, "
                + "bookings.date, bookings.startTime, bookings.endTime, "
                + "CONCAT(focus.name, ' (ID ', focus.id, ')') AS focus FROM GYM.bookings "
                + "INNER JOIN client ON client.id = bookings.client "
                + "INNER JOIN staff ON staff.id = bookings.trainer "
                + "INNER JOIN focus ON focus.id = bookings.focus ORDER BY bookings.id DESC;");

        String bookings = extractBookings(dbRes);

        return bookings;
    }

    /*
     * Retrieve Bookings by Booking ID
     */
    public String getBookingsByID(int id) throws SQLException {
        ResultSet dbRes = db.runQuery("SELECT bookings.id, CONCAT(client.name,' (ID ', client.id, ')') AS client, "
                + "CONCAT(staff.name, ' (ID ', staff.id, ')') AS trainer, "
                + "bookings.date, bookings.startTime, bookings.endTime, "
                + "CONCAT(focus.name, ' (ID ', focus.id, ')') AS focus " + "FROM GYM.bookings INNER JOIN client "
                + "ON client.id = bookings.client INNER JOIN staff "
                + "ON staff.id = bookings.trainer INNER JOIN focus ON "
                + "focus.id = bookings.focus WHERE bookings.id = " + id + " ORDER BY bookings.id DESC;");

        String bookings = extractBookings(dbRes);

        return bookings;
    }

    /*
     * Retrieve Bookings for Client by ID
     */
    public String getBookingsByClient(int id) throws SQLException {
        ResultSet dbRes = db.runQuery("SELECT bookings.id, CONCAT(client.name, "
                + "' (ID ', client.id, ')') AS client, " + "CONCAT(staff.name, ' (ID ', staff.id, ')') AS trainer, "
                + "bookings.date, bookings.startTime, bookings.endTime, "
                + "CONCAT(focus.name, ' (ID ', focus.id, ')') AS focus " + "FROM GYM.bookings INNER JOIN client "
                + "ON client.id = bookings.client INNER JOIN staff "
                + "ON staff.id = bookings.trainer INNER JOIN focus ON " + "focus.id = bookings.focus WHERE client.id = "
                + id + " ORDER BY bookings.id DESC;");

        String bookings = extractBookings(dbRes);

        return bookings;
    }

    /*
     * Retrieve Bookings for Personal Trainer by ID
     */
    public String getBookingsByPT(int id) throws SQLException {

        ResultSet dbRes = db.runQuery("SELECT bookings.id, CONCAT(client.name, "
                + "' (ID ', client.id, ')') AS client, " + "CONCAT(staff.name, ' (ID ', staff.id, ')') AS trainer, "
                + "bookings.date, bookings.startTime, bookings.endTime, "
                + "CONCAT(focus.name, ' (ID ', focus.id, ')') AS focus " + "FROM GYM.bookings INNER JOIN client "
                + "ON client.id = bookings.client INNER JOIN staff "
                + "ON staff.id = bookings.trainer INNER JOIN focus ON " + "focus.id = bookings.focus WHERE staff.id = "
                + id + " ORDER BY bookings.id DESC; ;");

        String bookings = extractBookings(dbRes);

        return bookings;
    }

    /*
     * Retrieve Bookings by Date
     */
    public String getBookingsByDate(Date date) throws SQLException {
        ResultSet dbRes = db.runQuery("SELECT bookings.id, CONCAT(client.name, "
                + "' (ID ', client.id, ')') AS client, CONCAT(staff.name, "
                + "' (ID ', staff.id, ')') AS trainer, bookings.date, "
                + "bookings.startTime, bookings.endTime, CONCAT(focus.name, "
                + "' (ID ', focus.id, ')') AS focus FROM GYM.bookings "
                + "INNER JOIN client ON client.id = bookings.client "
                + "INNER JOIN staff ON staff.id = bookings.trainer " + "INNER JOIN focus ON focus.id = bookings.focus "
                + "WHERE DATE(bookings.date) = " + date + " ORDER BY bookings.id DESC;");

        String bookings = extractBookings(dbRes);

        return bookings;
    }

    /*
     * Create new Booking in Database
     */
    public String newBooking(Request req) {
        try {
            // Acquire Lock so other threads cannot access the Database (Concurrency)
            lock.lock();

            String error = checkClientsPTExists(req.getClient(), req.getPT(), req.getFocus());
            if (error != null)
                return error;

            int result = db.runUpdate(
                    "INSERT INTO GYM.bookings (`client`," + "`trainer`,`date`,`startTime`,`endTime`,`focus`) "
                            + "SELECT '" + req.getClient() + "', '" + req.getPT() + "'," + " '" + req.getDate() + "', '"
                            + req.getStartTime() + "'," + " '" + req.getEndTime() + "', '" + req.getFocus() + "' "
                            + " WHERE NOT EXISTS( SELECT id FROM GYM.bookings " + "WHERE date = '" + req.getDate() + "'"
                            + "AND endTime > '" + req.getStartTime() + "' " + "AND startTime < '" + req.getEndTime()
                            + " AND PT = " + req.getPT() + "' );");

            if (result == 0) {
                return "Error - Conflicting Booking Exists";
            } else if (result == 1) {
                return "Success - Booking Successfully Created";
            }

            return "Error - Booking Creation Error";
        } finally {
            // bookingCondition.signalAll();
            lock.unlock();
        }
    }

    /*
     * Update Existing Bookings in Database
     */
    public String updateBooking(Request req) throws RuntimeException {
        try {
            lock.lock();

            // bookingConditionAwait(req.getQuery());

            String error = checkClientsPTExists(req.getClient(), req.getPT(), req.getFocus());
            if (error != null)
                return error;

            int result = db.runUpdate("UPDATE GYM.bookings SET client=" + req.getClient() + ", trainer=" + req.getPT()
                    + ", date='" + req.getDate() + "', startTime='" + req.getStartTime() + "', endTime='"
                    + req.getEndTime() + "', focus=" + req.getFocus() + " WHERE id=" + req.getQuery()
                    + " AND NOT EXISTS(SELECT * FROM GYM.bookings WHERE endTime > " + req.getStartTime()
                    + " AND startTime < " + req.getStartTime() + ");");

            if (result == 1)
                return "Success - Booking Successfully Updated";

            return "Error - Booking Updating Error";
        } catch (RuntimeException ex) {
            return "Error - Booking Deleting Error";
        } finally {
            lock.unlock();
        }
    }

    /*
     * Delete Existing Bookings
     */
    public String deleteBooking(Request req) {
        try {
            lock.lock();

            int result = db.runUpdate("DELETE FROM GYM.bookings WHERE id=" + req.getQuery() + ";");
            if (result == 1)
                return "Success - Booking Successfully Deleted";

            return "Error - Booking Deleting Error";
        } catch (RuntimeException ex) {
            return "Error - Booking Deleting Error";
        } finally {
            lock.unlock();
        }
    }

}
