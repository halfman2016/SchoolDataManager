package sanzhong;

/**
 * Created by Feng on 2016/9/27.
 */


    public class Student {

    public Bj bj = new Bj();
    public String email;
    public String mm;
    public int nn;
    public String xb;
    public String xh;
    public String xsxm;

    public Student(String xsxm) {
        super();
        this.mm = "123456";
        this.xsxm = xsxm;
        this.xh = xsxm;
        this.email = "abc@cd.com";
        this.nn = 13;
        this.xb = "��";
        bj.setBjmc("(20)��");
        bj.setNjmc("���꼶");
    }

    public Student(Bj bj, String mm, String xsxm) {
        super();
        this.bj = bj;
        this.mm = mm;
        this.xsxm = xsxm;

    }

}
