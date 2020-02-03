/* Gym Application Package */
package middlesexgym;

/* Class Requirements & Dependencies */
import java.io.Serializable;
import java.sql.Date;
import java.sql.Time;

/**
 * This class contains all information required by the Server to successfully
 * process the request. The class implements Serializable so that it can be
 * received by the Java Object Stream
 * 
 * @author Lavesh Panjwani (M00692913)
 */
public class BackendRequest implements Serializable {

    // Request Command to be Processed by the Server such as LISTALL
    private final String command;
    // Additional Command Requirements for Server such as id
    private int query;

    // Booking Details required by Major Operations such as Add & Update Booking
    private int client; // Client ID
    private int PT; // Personal Trainer ID
    private Date date; // Date in SQL Date Format
    private Time startTime; // Start Time in SQL Time Format
    private Time endTime; // End Time in SQL Time Format
    private int focus; // Focus ID

    /*
     * Constructor with Basic Command Details
     */
    public BackendRequest(String command) {
        this.command = command;
    }

    /*
     * Constructor with Command & Additional Requirements for Server
     */
    public BackendRequest(String command, int queryID) {
        this.command = command;
        this.query = queryID;
    }

    /*
     * Retrieves Request Command
     */
    public String getCommand() {
        return command;
    }

    /*
     * Retrieves Request Additional Requirements
     */
    public int getQuery() {
        return query;
    }

    /*
     * Retrieves Date for Major Operations
     */
    public Date getDate() {
        return date;
    }

    /*
     * Stores Date for Client-Server Communication
     */
    public void saveDate(Date date) {
        this.date = date;
    }

    /*
     * Retrieves Client ID
     */
    public int getClient() {
        return client;
    }

    /*
     * Retrieves Personal Trainer ID
     */
    public int getPT() {
        return PT;
    }

    /*
     * Retrieves Start Time in SQL TIME
     */
    public Time getStartTime() {
        return startTime;
    }

    /*
     * Retrieves End Time in SQL TIME
     */
    public Time getEndTime() {
        return endTime;
    }

    /*
     * Retrieves Focus ID
     */
    public int getFocus() {
        return focus;
    }

    /*
     * Stored Additional Data for Major Operations for Client - Server Communication
     */
    public void setAdditionalData(int client, int PT, Date date, Time startTime, Time endTime, int focus) {
        this.client = client;
        this.PT = PT;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.focus = focus;
    }

}
