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

    private final String action;

    private int client;
    private int PT;
    private Date date;
    private Time startTime;
    private Time endTime;
    private int focus;

    public BackendRequest(String action) {
        this.action = action;
    }

    public String getAction() {
        return action;
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
