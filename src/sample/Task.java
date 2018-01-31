package sample;

import java.util.Date;

public class Task {
    private int taskid;
    private int userid;
    private String label;
    private String descripion;
    private Date created;

    public Task(int taskid, int userid, String label, String description, Date created) {
        this.taskid = taskid;
        this.userid = userid;
        this.label = label;
        this.descripion = description;
        this.created = created;
    }

    public int getTaskid() {
        return taskid;
    }

    public void setTaskid(int taskid) {
        this.taskid = taskid;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getDescripion() {
        return descripion;
    }

    public void setDescripion(String descripion) {
        this.descripion = descripion;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }
}
