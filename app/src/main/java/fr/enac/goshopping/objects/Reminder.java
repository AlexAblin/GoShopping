package fr.enac.goshopping.objects;

import java.util.Date;

/**
 * Created by alexandre on 11/11/2016.
 */

public class Reminder {
    private String list_name;
    private Date date;

    public Reminder(String list_name, Date date) {
        this.list_name = list_name;
        this.date=date;
    }

    public String getList_name() {
        return list_name;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getDate() {

        return date;
    }

    public void setList_name(String list_name) {
        this.list_name = list_name;
    }

}
