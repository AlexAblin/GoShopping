package fr.enac.goshopping.objects;

import java.util.Date;

/**
 * Created by alexandre on 11/11/2016.
 */

public class Reminder {
    private String list_id;
    private Date date;

    public Reminder(String list_id, Date date) {
        this.list_id = list_id;
        this.date=date;
    }

    public String getList_id() {
        return list_id;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getDate() {
        return date;
    }

    public void setList_id(String list_id) {
        this.list_id = list_id;
    }

}
