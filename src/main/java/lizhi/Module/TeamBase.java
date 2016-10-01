package lizhi.Module;

import java.util.*;

/**
 * Created by Feng on 2016/7/27.
 */
public class TeamBase extends Object {

    private String Name;
    private ArrayList<Student> stus;
    private Map<String,Teacher> teaOnDuty;
    private UUID _id=UUID.randomUUID();
    private Date createTime;
    private Date endTime;
    private String Stats;
    private boolean isActive;  //是否有效

    public TeamBase() {

        isActive=true;  //默认有效，从创建时候
        createTime=new Date();  //默认现在创建
        endTime=new Date();
        Calendar cal=Calendar.getInstance();
        cal.setTime(endTime);
        cal.add(Calendar.YEAR,1);
        endTime=cal.getTime();

    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public ArrayList<Student> getStus() {
        return stus;
    }

    public void setStus(ArrayList<Student> stus) {
        this.stus = stus;
    }

    public Map<String, Teacher> getTeaOnDuty() {
        return teaOnDuty;
    }

    public void setTeaOnDuty(Map<String, Teacher> teaOnDuty) {
        this.teaOnDuty = teaOnDuty;
    }

    public UUID getGcid() {
        return _id;
    }

    public void setGcid(UUID gcid) {
        this._id = gcid;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getStats() {
        return Stats;
    }

    public void setStats(String stats) {
        Stats = stats;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
