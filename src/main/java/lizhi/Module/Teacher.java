package lizhi.Module;

import java.util.ArrayList;

/**
 * Created by Feng on 2016/7/12.
 */
public class Teacher extends People {
    private String Tid;
    private ArrayList<String> Duty = new ArrayList<>();



    public Teacher() {
    }

    public String getTid() {
        return Tid;
    }

    public void setTid(String tid) {
        Tid = tid;
    }
}
