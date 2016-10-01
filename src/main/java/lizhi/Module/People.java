package lizhi.Module;

import com.mongodb.DBObject;

import java.util.UUID;

/**
 * Created by Feng on 2016/6/16.
 */
public class People extends Object {
    private String name;
    private String pwd;
    private String phoneNum;
    private String email;

    private UUID _id=UUID.randomUUID();

    public People() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
