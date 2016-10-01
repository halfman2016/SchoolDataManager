package HAPPYREAD.Module;

/**
 * Created by Feng on 2016/7/14.
 */
public class Tip {
    private String tipTitle;
    private String tipBody;

    public Tip(String tiptitle) {
        this.tipTitle = tiptitle;
    }

    public String getTipTitle() {
        return tipTitle;
    }

    public void setTipTitle(String tiptitle) {
        this.tipTitle = tiptitle;
    }

    public String getTipBody() {
        return tipBody;
    }

    public void setTipBody(String tipBody) {
        this.tipBody = tipBody;
    }
}
