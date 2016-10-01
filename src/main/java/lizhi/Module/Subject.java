package lizhi.Module;

import java.util.Date;

/**
 * Created by Feng on 2016/9/17.
 */
public class Subject {
    private String subjectName ;
    private String SubjectInfo;
    private Date startTime;
    private Date endTime;

    public Subject(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getSubjectInfo() {
        return SubjectInfo;
    }

    public void setSubjectInfo(String subjectInfo) {
        SubjectInfo = subjectInfo;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }
}
