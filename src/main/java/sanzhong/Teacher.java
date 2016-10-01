package sanzhong;

/**
 * Created by Feng on 2016/9/27.
 */


import java.util.HashMap;
import java.util.Map;

    public class Teacher {
        Map<String, Bj> tbj = new HashMap<String, Bj>();
        private String temail;
        private String tid;
        private String tkc;
        private int nn;
        private String tpwd;
        private String tqq=null;
        private String tsj=null;
        private String txb;
        private String txm;



        public Teacher(String tid, String txm,String tkc) {
            super();
            this.tid = tid;
            this.txm = txm;
            this.tkc=tkc;
            this.tpwd="123456";
            Bj bj=new Bj();
            bj.setBjmc("(20)��");
            bj.setNjmc("���꼶");
            tbj.put("001", bj);

        }

        public Teacher(String txm,String tkc) {
            super();
            this.tid = txm;
            this.txm = txm;
            this.tpwd="123456";
            this.tkc=tkc;
            Bj bj=new Bj();
            bj.setBjmc("(20)��");
            bj.setNjmc("���꼶");
            tbj.put("001", bj);
        }

        public Map<String, Bj> getTbj() {
            return tbj;
        }
        public void setTbj(Map<String, Bj> tbj) {
            this.tbj = tbj;
        }
        public String getTemail() {
            return temail;
        }
        public void setTemail(String temail) {
            this.temail = temail;
        }
        public String getTid() {
            return tid;
        }
        public void setTid(String tid) {
            this.tid = tid;
        }
        public String getTkc() {
            return tkc;
        }
        public void setTkc(String tkc) {
            this.tkc = tkc;
        }
        public int getNn() {
            return nn;
        }
        public void setNn(int nn) {
            this.nn = nn;
        }
        public String getTpwd() {
            return tpwd;
        }
        public void setTpwd(String tpwd) {
            this.tpwd = tpwd;
        }
        public String getTqq() {
            return tqq;
        }
        public void setTqq(String tqq) {
            this.tqq = tqq;
        }
        public String getTsj() {
            return tsj;
        }
        public void setTsj(String tsj) {
            this.tsj = tsj;
        }
        public String getTxb() {
            return txb;
        }
        public void setTxb(String txb) {
            this.txb = txb;
        }
        public String getTxm() {
            return txm;
        }
        public void setTxm(String txm) {
            this.txm = txm;
        }


    }


