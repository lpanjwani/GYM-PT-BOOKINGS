/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package middlesexgym;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Time;

/**
 *
 * @author LaveshPanjwani
 */
public class BackendRequest implements Serializable {

    private final String command;
    private int query;

    private int client;
    private int PT;
    private Date date;
    private Time startTime;
    private Time endTime;
    private int focus;

    public BackendRequest(String command) {
        this.command = command;
    }

    public BackendRequest(String command, int queryID) {
        this.command = command;
        this.query = queryID;
    }

    public String getCommand() {
        return command;
    }

    public int getQuery() {
        return query;
    }

    public Date getDate() {
        return date;
    }

    public void saveDate(Date date) {
        this.date = date;
    }

    public int getClient() {
        return client;
    }

    public int getPT() {
        return PT;
    }

    public Time getStartTime() {
        return startTime;
    }

    public Time getEndTime() {
        return endTime;
    }

    public int getFocus() {
        return focus;
    }

    public void setAdditionalData(int client, int PT, Date date, Time startTime, Time endTime, int focus) {
        this.client = client;
        this.PT = PT;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.focus = focus;
    }

}
