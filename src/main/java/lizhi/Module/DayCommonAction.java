package lizhi.Module;

import java.util.Date;

/**
 * Created by Feng on 2016/8/24.
 */
public class DayCommonAction extends ScoreAction  {

    private  Date date;
    public DayCommonAction(String actionName, String actionType, int actionScoreValue) {
        super(actionName, actionType, actionScoreValue);
        date=new Date();


    }




}
