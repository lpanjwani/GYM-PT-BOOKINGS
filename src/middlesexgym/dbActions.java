/* Gym Application Package */
package middlesexgym;

/* Class Requirements & Dependencies */
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * This class deals with performance SQL commands required for the GYM
 * Application.
 * 
 * @author Lavesh Panjwani (M00692913)
 */
public class dbActions {

    private final dbController db = new dbController();
    private final ReentrantLock lock = new ReentrantLock();
    private final Condition bookingCondition = lock.newCondition();

    private ArrayList<String> bookingsDecode(ResultSet dbRes) throws SQLException {
        ArrayList<String> bookings = new ArrayList<>();

        while (dbRes.next()) {
            String current = ("ID: " + dbRes.getInt("id"));
            current += " Client: " + dbRes.getString("client");
            current += " Trainer: " + dbRes.getString("trainer");
            current += " Date: " + dbRes.getDate("date");
            current += " Start Time: " + dbRes.getTime("startTime");
            current += " End Time: " + dbRes.getTime("endTime");
            current += " Focus: " + dbRes.getString("focus");
            bookings.add(current);
        }

        return bookings;

    }

    private int resultCount(ResultSet dbRes) {
        try {
            dbRes.next();
            return dbRes.getInt(1);
        } catch (Exception ex) {
            return 0;
        }
    }

    private String checkClientsPTExists(int clientID, int ptID) {
        int clientCount = resultCount(
                db.runQuery("SELECT COUNT(*) AS COUNT FROM GYM.client WHERE id=" + clientID + ";"));
        if (clientCount < 1)
            return "Error - No Client with ID " + clientID + " Exists!";

        int ptCount = resultCount(db.runQuery("SELECT COUNT(*) AS COUNT FROM GYM.staff WHERE id=" + ptID + ";"));
        if (ptCount < 1)
            return "Error - No Trainer with ID " + ptID + " Exists!";

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

    public ArrayList getBookings() throws SQLException {
        ResultSet dbRes = db.runQuery("SELECT bookings.id, CONCAT(client.name, '"
                + " (ID ', client.id, ')') AS client, " + "CONCAT(staff.name, ' (ID ', staff.id, ')') AS trainer, "
                + "bookings.date, bookings.startTime, bookings.endTime, "
                + "CONCAT(focus.name, ' (ID ', focus.id, ')') AS focus FROM GYM.bookings "
                + "INNER JOIN client ON client.id = bookings.client "
                + "INNER JOIN staff ON staff.id = bookings.trainer "
                + "INNER JOIN focus ON focus.id = bookings.focus ORDER BY bookings.id DESC;");

        ArrayList<String> bookings = bookingsDecode(dbRes);

        return bookings;
    }

    public ArrayList getBookingsByID(int id) throws SQLException {
        ResultSet dbRes = db.runQuery("SELECT bookings.id, CONCAT(client.name,' (ID ', client.id, ')') AS client, "
                + "CONCAT(staff.name, ' (ID ', staff.id, ')') AS trainer, "
                + "bookings.date, bookings.startTime, bookings.endTime, "
                + "CONCAT(focus.name, ' (ID ', focus.id, ')') AS focus " + "FROM GYM.bookings INNER JOIN client "
                + "ON client.id = bookings.client INNER JOIN staff "
                + "ON staff.id = bookings.trainer INNER JOIN focus ON "
                + "focus.id = bookings.focus WHERE bookings.id = " + id + " ORDER BY bookings.id DESC;");

        ArrayList<String> bookings = bookingsDecode(dbRes);

        return bookings;
    }

    public ArrayList getBookingsByClient(int id) throws SQLException {
        ResultSet dbRes = db.runQuery("SELECT bookings.id, CONCAT(client.name, "
                + "' (ID ', client.id, ')') AS client, " + "CONCAT(staff.name, ' (ID ', staff.id, ')') AS trainer, "
                + "bookings.date, bookings.startTime, bookings.endTime, "
                + "CONCAT(focus.name, ' (ID ', focus.id, ')') AS focus " + "FROM GYM.bookings INNER JOIN client "
                + "ON client.id = bookings.client INNER JOIN staff "
                + "ON staff.id = bookings.trainer INNER JOIN focus ON " + "focus.id = bookings.focus WHERE client.id = "
                + id + " ORDER BY bookings.id DESC;");

        ArrayList<String> bookings = bookingsDecode(dbRes);

        return bookings;
    }

    public ArrayList getBookingsByPT(int id) throws SQLException {

        ResultSet dbRes = db.runQuery("SELECT bookings.id, CONCAT(client.name, "
                + "' (ID ', client.id, ')') AS client, " + "CONCAT(staff.name, ' (ID ', staff.id, ')') AS trainer, "
                + "bookings.date, bookings.startTime, bookings.endTime, "
                + "CONCAT(focus.name, ' (ID ', focus.id, ')') AS focus " + "FROM GYM.bookings INNER JOIN client "
                + "ON client.id = bookings.client INNER JOIN staff "
                + "ON staff.id = bookings.trainer INNER JOIN focus ON " + "focus.id = bookings.focus WHERE staff.id = "
                + id + " ORDER BY bookings.id DESC; ;");

        ArrayList<String> bookings = bookingsDecode(dbRes);

        return bookings;
    }

    public ArrayList getBookingsByDate(Date date) throws SQLException {
        ResultSet dbRes = db.runQuery("SELECT bookings.id, CONCAT(client.name, "
                + "' (ID ', client.id, ')') AS client, CONCAT(staff.name, "
                + "' (ID ', staff.id, ')') AS trainer, bookings.date, "
                + "bookings.startTime, bookings.endTime, CONCAT(focus.name, "
                + "' (ID ', focus.id, ')') AS focus FROM GYM.bookings "
                + "INNER JOIN client ON client.id = bookings.client "
                + "INNER JOIN staff ON staff.id = bookings.trainer " + "INNER JOIN focus ON focus.id = bookings.focus "
                + "WHERE DATE(bookings.date) = " + date + " ORDER BY bookings.id DESC;");

        ArrayList<String> bookings = bookingsDecode(dbRes);

        return bookings;
    }

    public String newBooking(BackendRequest req) {
        try {
            lock.lock();

            String error = checkClientsPTExists(req.getClient(), req.getPT());
            if (error != null)
                return error;

            int result = db.runUpdate("INSERT INTO GYM.bookings (`client`,"
                    + "`trainer`,`date`,`startTime`,`endTime`,`focus`) " + "SELECT '" + req.getClient() + "', '"
                    + req.getPT() + "'," + " '" + req.getDate() + "', '" + req.getStartTime() + "'," + " '"
                    + req.getEndTime() + "', '" + req.getFocus() + "' " + "FROM DUAL "
                    + "WHERE NOT EXISTS( SELECT id FROM GYM.bookings " + "WHERE date = '" + req.getDate() + "'"
                    + "AND endTime > '" + req.getStartTime() + "' " + "AND startTime < '" + req.getEndTime() + "' );");

            if (result == 0) {
                return "Error - Conflicting Booking Exists";
            } else if (result == 1) {
                return "Success - Booking Successfully Created";
            }

            return "Error - Booking Creation Error";
        } finally {
            bookingCondition.signalAll();
            lock.unlock();
        }
    }

    public String updateBooking(BackendRequest req) throws RuntimeException {
        try {
            lock.lock();

            bookingConditionAwait(req.getQuery());

            String error = checkClientsPTExists(req.getClient(), req.getPT());
            if (error != null)
                return error;

            int result = db.runUpdate("UPDATE GYM.bookings SET client=" + req.getClient() + ", trainer=" + req.getPT()
                    + ", date='" + req.getDate() + "', startTime='" + req.getStartTime() + "', endTime='"
                    + req.getEndTime() + "', focus=" + req.getFocus() + " WHERE id=" + req.getQuery() + ";");

            if (result == 1)
                return "Success - Booking Successfully Updated";

            return "Error - Booking Updating Error";
        } catch (RuntimeException ex) {
            return "Error - Booking Deleting Error";
        } finally {
            lock.unlock();
        }
    }

    public String deleteBooking(BackendRequest req) {
        try {
            lock.lock();

            bookingConditionAwait(req.getQuery());

            int result = db.runUpdate("DELETE FROM GYM.bookings WHERE id=" + req.getQuery() + ";");
            if (result == 1)
                return "Success - Booking Successfully Updated";

            return "Error - Booking Deleting Error";
        } catch (RuntimeException ex) {
            return "Error - Booking Deleting Error";
        } finally {
            lock.unlock();
        }
    }

}
