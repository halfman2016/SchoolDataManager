/*
 * Created by JFormDesigner on Thu Jul 21 10:17:57 CST 2016
 */

package lizhi;

import java.beans.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import lizhi.Module.*;
import lizhi.util.MDBTools;
import lizhi.util.StuTableModel;
import lizhi.util.TeaTableModel;
import org.bson.Document;

import java.awt.*;
import java.awt.event.*;
import java.io.*;

import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;
import javax.swing.*;
import javax.swing.GroupLayout;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import static javafx.application.Platform.exit;

/**
 * @author halfman
 */
public class lizhiMain extends JFrame {
    private Map<String,Integer> defaultValues=new HashMap<>();
    private Map<String,Rank> ranks =new HashMap<>();
    private Map<String, GradeClass> classes = new HashMap<>();

    private ArrayList<Subject> arraysubjects;
    private ArrayList<Student> stus;
    private ArrayList<Teacher> teas;

    private ArrayList<GradeClass> gradeClasses;

    private int rankpostion=0;



    MDBTools mdb=new MDBTools();




    public lizhiMain() throws IOException {
        initComponents();
    }



    private void addToMapMouseClicked(MouseEvent e) {
        // TODO add your code here
        defaultValues.put(txtActionName.getText(),Integer.valueOf(txtValue.getText()));

       actionListRefresh();
    }

    private void saveToFileMouseClicked(MouseEvent e)  {
        // TODO add your code here
        Gson gson= new GsonBuilder().create();
        String json=gson.toJson(defaultValues,Map.class);
        System.out.println(json);
        File file =new File(txtFilename.getText());
        FileOutputStream fstream = null;
        try {
            fstream = new FileOutputStream(file);
            BufferedOutputStream stream = new BufferedOutputStream(fstream);
            stream.write(json.getBytes());
            stream.close();
            fstream.close();
        } catch (IOException e1) {
            e1.printStackTrace();
        }


    }

    private void LoadFromFileMouseClicked(MouseEvent e)  {
        // TODO add your code here
        Gson gson= new GsonBuilder().create();

        //String json=gson.toJson(defaultValues);
        File file =new File(txtFilename.getText());
        String str="";
        FileInputStream in= null;
        try {
            in = new FileInputStream(file);
            // size  为字串的长度 ，这里一次性读完
            int size=in.available();
            byte[]buffer=new byte[size];
            in.read(buffer);
            in.close();
            str=new String(buffer,"UTF-8");
        } catch (IOException e1) {
            e1.printStackTrace();
        }

        System.out.println(str);

        java.lang.reflect.Type type=new TypeToken<Map<String,Integer>>(){}.getType();
        defaultValues=gson.fromJson(str,type);

        actionListRefresh();
       // txtMap.setText(mapTostring(defaultValues));


    }

    private void searchNameMouseClicked(MouseEvent e) {
        // TODO add your code here
        Map<String,Integer>result=new HashMap<String, Integer>();
        String keystr=txtSearchStr.getText();
        Iterator it=defaultValues.entrySet().iterator();
        while (it.hasNext())
        {
            Map.Entry<String,Integer>  entry= (Map.Entry<String, Integer>) it.next();

            if(entry.getKey().indexOf(keystr)!=-1){
                result.put(entry.getKey(),entry.getValue());
            }


        }

        txtSearchresult.setText(mapTostring(result));

      }

    private String mapTostring(Map map){
        String str="";
               Iterator iter = map.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry entry = (Map.Entry) iter.next();
            str=str+entry.getKey()+":" + entry.getValue()+"\n";
        }

        return str;
    }

    private void btnSearchByValMouseClicked(MouseEvent e) {
        Map<String,Integer>result=new HashMap<String,Integer>();
        Integer keyval=Integer.valueOf(txtSerchVal.getText());
        Iterator it=defaultValues.entrySet().iterator();
        while (it.hasNext())
        {
            Map.Entry<String,Integer>  entry= (Map.Entry<String, Integer>) it.next();

            if(entry.getValue().intValue()==keyval){
                result.put(entry.getKey(),entry.getValue());
            }
        }

        txtSearchresult.setText(mapTostring(result));
    }

    private void LoadStusMouseClicked(MouseEvent e) {
        // TODO add your code here
        stus=mdb.getStus();

        tabStus.setModel(new StuTableModel(stus));

    }

    private void AddToRanksMouseClicked(MouseEvent e) {
        // TODO add your code here
        Rank rank = new Rank();
        rank.setRankName(txtRankName.getText());
        rank.setDoorScore(Integer.valueOf(txtdoorscore.getText()));
        rank.setHighestscore(Integer.valueOf(txthighestscore.getText()));
        rank.setRanklevel(Integer.valueOf(txtlevel.getText()));

        ranks.put(String.valueOf(rank.getRanklevel()),rank);

        rankListRefresh();
    }

    private void actionListRefresh(){
        DefaultListModel dlist=new DefaultListModel();
        Iterator iter=defaultValues.entrySet().iterator();
        while (iter.hasNext())
        {
            Map.Entry<String,Integer> temp=(Map.Entry<String,Integer>) iter.next();
            dlist.addElement(temp.getKey() + ":" + String.valueOf(temp.getValue()));
        }
                actionList.setModel(dlist);
        actionList.setCellRenderer(new myActionListRender());

    }
    private  void rankListRefresh()
    {
        DefaultListModel<Rank> dlist=new DefaultListModel<>();
        Iterator iter = ranks.entrySet().iterator();
        while (iter.hasNext())
        {
            Map.Entry<String,Rank> temp= (Map.Entry<String, Rank>) iter.next();
           dlist.add(Integer.valueOf(temp.getKey()),temp.getValue());
        }

        ranklist.setModel(dlist);
        ranklist.setCellRenderer(new myRanklistRender());

        ranklist.updateUI();
    }
    private void RanksSaveToFileMouseClicked(MouseEvent e) {
        // TODO add your code here
        mdb.saveRanks(ranks);
        rankListRefresh();
    }

    private void RanksLoadFromFileMouseClicked(MouseEvent e) {
        // TODO add your code here
        ranks=mdb.getRanks();
        rankListRefresh();
    }

    private void ranklistMouseClicked(MouseEvent e) {
        // TODO add your code here
    }

    private void defaultValuesFromDatabaseMouseClicked(MouseEvent e) {
        // TODO add your code here

    }

    private void defaultValueeSaveToDatabaseMouseClicked(MouseEvent e) {
        // TODO add your code here
    if(defaultValues.size()>0) {
        mdb.saveDefaultValuestoDataBase(defaultValues);
    }
    }

    private void addStuMouseClicked(MouseEvent e) {
        // TODO add your code here
        Student student =new Student();
        String str= (String) stuGradeClass.getSelectedItem();
        student.setName(stuName.getText());
        student.setPwd(stuPwd.getText());
        student.setGradeclass(str);
        student.setScore(Integer.valueOf(stuTSc.getText()));
        student.setPhoneNum(stuPhone.getText());
        student.setQQ(stuQq.getText());

        mdb.addStu(student);


    }

    private void PeopleComponentShown(ComponentEvent e) {

        stuGradeClass.addItem("初一（1）班");
        stuGradeClass.addItem("初二（1）班");
        stuGradeClass.addItem("初三（1）班");
        stuGradeClass.addItem("初三（2）班");
        stuGradeClass.addItem("女生（1）班");
        stuGradeClass.addItem("女生（2）班");
    }

    private void stuGradeClassActionPerformed(ActionEvent e) {
        // TODO add your code here
    }

    private void ranklistValueChanged(ListSelectionEvent e) {
        // TODO add your code here
        Rank rank=new Rank();
        rank= (Rank) ranklist.getSelectedValue();
        txtRankName.setText(rank.getRankName());
        txtdoorscore.setText(String.valueOf(rank.getDoorScore()));
        txthighestscore.setText(String.valueOf(rank.getHighestscore()));
        txtlevel.setText(String.valueOf(rank.getRanklevel()));
        rankpostion=ranklist.getSelectedIndex();

    }

    private void ModifyRanksMouseClicked(MouseEvent e) {
        // TODO add your code here

        }

        private void tabStusPropertyChange(PropertyChangeEvent e) {

            int row=tabStus.getSelectedRow();
            Student student;
            if(row>=0)
            {
                int modelindex=tabStus.convertRowIndexToModel(row);
                StuTableModel model= (StuTableModel) tabStus.getModel();
                student=model.getRowStudent(modelindex);
                stuName.setText(student.getName());

            }
        }

        private void LoadTeasActionPerformed(ActionEvent e) {
            // TODO add your code here
            teas = mdb.getTeas();
            tabTeas.setModel(new TeaTableModel(teas));
        }



        private void addTeaMouseClicked(MouseEvent e) {
            Teacher teacher =new Teacher();
            teacher.setName(teaName.getText());
            teacher.setTid(teaTid.getText());
            teacher.setPwd(teaPwd.getText());
            teacher.setPhoneNum(teaPhone.getText());
            mdb.addTea(teacher);

        }

        private void actionListValueChanged(ListSelectionEvent e) {
            // TODO add your code here
            String value= (String) actionList.getSelectedValue();
            int poszion= value.indexOf(":");
            txtActionName.setText(value.substring(0,poszion));
            txtValue.setText(value.substring(poszion+1,value.length()));
        }

        private void btnLoadFromDatabaseActionPerformed(ActionEvent e) {
            // TODO add your code here
            System.out.println("clicked");
            defaultValues=mdb.getDefaultValues();
            actionListRefresh();
        }

        private void btnAddGradeClassActionPerformed(ActionEvent e) {
            // 班级增加
            GradeClass gradeClass=new GradeClass();
            gradeClass.setName(txtGradeClass.getText());
            gradeClass.setCreateTime(new Date());

            ArrayList<Student>stus=mdb.getStus();
            for (Student stu:stus
                 ) {
                stu.setGradeclass(txtGradeClass.getText());
                stu.setGradeclassid(gradeClass.getGcid());
                    }
            gradeClass.setStus(stus);
            mdb.saveGradeClass(gradeClass);


        }

        private void TeamsPropertyChange(PropertyChangeEvent e) {
            // TODO add your code here

            System.out.print("Team is ok");
        }

        private void btnGCFromDatabaseActionPerformed(ActionEvent e) {
            // TODO add your code here
            //取出现有的班级列表和学生列表
            //班级列表，点击时，返回学生的UUID array
            gradeClasses=mdb.getGradeClasses();


            DefaultListModel<GradeClass> dlist=new DefaultListModel<>();
            int i=0;
            for (GradeClass gs:gradeClasses
                 ) {

                dlist.add(i++,gs);
            }

            classlist.setModel(dlist);
            classlist.setCellRenderer(new myGradeClassRender());

            classlist.updateUI();


        }

        private void classlistValueChanged(ListSelectionEvent e) {
            // TODO add your code here
           GradeClass jx= (GradeClass) classlist.getSelectedValue();
            txtGradeClass.setText(jx.getName());
        }

        private void gradeClassSaveToDatabaseActionPerformed(ActionEvent e) {
            // TODO add your code here
            mdb.saveGradeClasses(gradeClasses);

        }

        private void btnLoadAllStusActionPerformed(ActionEvent e) {
            stus=mdb.getStus();



        }



  private ArrayList<DayCommonAction> dayactionlist=new ArrayList<>();

        private void CommonDayActionsComponentShown(ComponentEvent e) {
            // TODO add your code here
            dayactionlist.clear();
            //显示dayaction
            DayCommonAction dayCommonAction=new DayCommonAction("作业按时完成，态度认真，不抄袭","学习标兵",1);
            dayactionlist.add(dayCommonAction);
            dayCommonAction=new DayCommonAction("课堂上按照老师要求，未受到批评或扣分","学习标兵",1);
            dayactionlist.add(dayCommonAction);
            dayCommonAction=new DayCommonAction("起床后在规定时间内完成个人内务态度端正","内务标兵",1);
            dayactionlist.add(dayCommonAction);
            dayCommonAction=new DayCommonAction("床铺整理干净整洁，洗漱用品摆放整齐","内务标兵",1);
            dayactionlist.add(dayCommonAction);
            dayCommonAction=new DayCommonAction("起床后在规定时间内完成卫生责任区卫生态度端正","卫生标兵",1);
            dayactionlist.add(dayCommonAction);
            dayCommonAction=new DayCommonAction("卫生责任区干净整洁","卫生标兵",1);
            dayactionlist.add(dayCommonAction);
            dayCommonAction=new DayCommonAction("说话和气，不骂人，不说粗言秽语","文明标兵",1);
            dayactionlist.add(dayCommonAction);
            dayCommonAction=new DayCommonAction("礼貌待人，尊敬师长，尊重同学","文明标兵",1);
            dayactionlist.add(dayCommonAction);
            dayCommonAction=new DayCommonAction("白天（上午8：00—下午17：00）扣分不超过1分","纪律标兵",1);
            dayactionlist.add(dayCommonAction);
            dayCommonAction=new DayCommonAction("晚上（下午17：00—上午8：00）扣分不超过1分","纪律标兵",1);
            dayactionlist.add(dayCommonAction);

            dayCommonAction=new DayCommonAction("思想汇报300字 丰富贴切","思想进步标兵",10);
            dayactionlist.add(dayCommonAction);
            dayCommonAction=new DayCommonAction("思想汇报300字 内容一般","思想进步标兵",8);
            dayactionlist.add(dayCommonAction);
            dayCommonAction=new DayCommonAction("思想汇报300字 内容空泛","思想进步标兵",5);
            dayactionlist.add(dayCommonAction);
            dayCommonAction=new DayCommonAction("思想汇报 态度不认真","思想进步标兵",-2);
            dayactionlist.add(dayCommonAction);
            dayCommonAction=new DayCommonAction("思想汇报300字 态度很不认真","思想进步标兵",-3);
            dayactionlist.add(dayCommonAction);

            mdb.addDayCommonAction(dayactionlist);

            daylist.setCellRenderer(new myDaylistRender());
            daylist.setListData(dayactionlist.toArray());

        }

            private void btndaystudyActionPerformed(ActionEvent e) {
                // TODO add your code here
                dayactionlist.clear();
                dayactionlist=mdb.getTypedDayActions("学习标兵");

                daylist.setCellRenderer(new myDaylistRender());
                daylist.setListData(dayactionlist.toArray());
            }

            private void btndayneiwuActionPerformed(ActionEvent e) {
                // TODO add your code here
                dayactionlist.clear();
                dayactionlist=mdb.getTypedDayActions("内务标兵");

                daylist.setCellRenderer(new myDaylistRender());
                daylist.setListData(dayactionlist.toArray());

            }

            private void btndayweishengActionPerformed(ActionEvent e) {
                dayactionlist.clear();
                dayactionlist=mdb.getTypedDayActions("卫生标兵");

                daylist.setCellRenderer(new myDaylistRender());
                daylist.setListData(dayactionlist.toArray());


            }

            private void btndaywenmingActionPerformed(ActionEvent e) {
                dayactionlist=mdb.getTypedDayActions("文明标兵");

                daylist.setCellRenderer(new myDaylistRender());
                daylist.setListData(dayactionlist.toArray());
            }

            private void btndayjilvActionPerformed(ActionEvent e) {
                dayactionlist=mdb.getTypedDayActions("纪律标兵");

                daylist.setCellRenderer(new myDaylistRender());
                daylist.setListData(dayactionlist.toArray());
            }

            private void btnweeksixiangActionPerformed(ActionEvent e) {
                dayactionlist=mdb.getTypedDayActions("思想进步标兵");

                daylist.setCellRenderer(new myDaylistRender());
                daylist.setListData(dayactionlist.toArray());
            }

            private void thisWindowClosed(WindowEvent e) {
                // TODO add your code here

                System.exit(0);
            }

            private void btnAddSubjectsActionPerformed(ActionEvent e) {
                // TODO add your code here
                Subject addsubject=new Subject(txtSubjectName.getText());
                addsubject.setSubjectInfo(txtSubjectInfo.getText());
                DateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");
                Date date= null;
                try {
                    date = dateFormat.parse(txtSubStartTime.getText());
                } catch (ParseException e1) {
                    e1.printStackTrace();
                }
                addsubject.setStartTime(date);
                try {
                    date=dateFormat.parse(txtSubEndTime.getText());
                } catch (ParseException e1) {
                    e1.printStackTrace();
                }
                addsubject.setEndTime(date);
                mdb.addSubject(addsubject);

                arraysubjects.clear();

                arraysubjects=mdb.getSubjects();
                listSubjects.setListData(arraysubjects.toArray());
                listSubjects.setCellRenderer(new mysublistRender());

            }




            private void subjectsComponentShown(ComponentEvent e) {
                // TODO add your code here

                arraysubjects=mdb.getSubjects();
                listSubjects.setListData(arraysubjects.toArray());
                listSubjects.setCellRenderer(new mysublistRender());

            }

            private void listSubjectsValueChanged(ListSelectionEvent e) {
                // TODO add your code here
                Subject cursub=(Subject)listSubjects.getSelectedValue();
                txtSubjectName.setText(cursub.getSubjectName());
                txtSubjectInfo.setText(cursub.getSubjectInfo());
                SimpleDateFormat fml=new SimpleDateFormat("yyyy-MM-dd");
                txtSubStartTime.setText(fml.format(cursub.getStartTime()));
                txtSubEndTime.setText(fml.format(cursub.getEndTime()));

            }

            private void btnSubDelActionPerformed(ActionEvent e) {
                // TODO add your code here
                mdb.delSubjects(txtSubjectName.getText());
                arraysubjects=mdb.getSubjects();
                listSubjects.setListData(arraysubjects.toArray());
                listSubjects.setCellRenderer(new mysublistRender());
            }




    class mysublistRender implements ListCellRenderer{

        @Override
        public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            Subject subject=(Subject)value;
            JTextField jx=new JTextField();
            jx.setText(subject.getSubjectName());
            return  jx;

        }
    }


    class  myRanklistRender implements ListCellRenderer{

        @Override
        public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            Rank temp=(Rank)value;
            JTextField jx=new JTextField();
            jx.setText(temp.getRanklevel()+"  "+temp.getRankName()+"  " +temp.getDoorScore() +"~" + temp.getHighestscore());

            return jx;
        }
    }

    class myGradeClassRender implements ListCellRenderer {


        @Override
        public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {

            GradeClass gc = (GradeClass) value;
            JTextField jx = new JTextField();
            jx.setText(gc.getName());
            return jx;
        }
    }



    class myActionListRender implements ListCellRenderer{
        @Override
        public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            JTextField js=new JTextField();
            js.setText((String )value);
            return js;
        }
    }

    class myDaylistRender implements  ListCellRenderer{


        @Override
        public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            JTextField js=new JTextField();
            DayCommonAction da=(DayCommonAction)value;
            js.setText(da.getActionName() + ":" + da.getActionType() + ":" + da.getActionValue());
            return js;
        }
    }




    private void initComponents() throws IOException{
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        panel1 = new JPanel();
        lihzidata = new JTabbedPane();
        DefaultValues = new JPanel();
        label1 = new JLabel();
        txtActionName = new JTextField();
        label2 = new JLabel();
        txtValue = new JTextField();
        addToMap = new JButton();
        txtFilename = new JTextField();
        saveToFile = new JButton();
        LoadFromFile = new JButton();
        txtSearchStr = new JTextField();
        searchName = new JButton();
        scrollPane2 = new JScrollPane();
        txtSearchresult = new JTextArea();
        textField3 = new JTextField();
        txtSerchVal = new JTextField();
        btnSearchByVal = new JButton();
        defaultValueeSaveToDatabase = new JButton();
        scrollPane1 = new JScrollPane();
        actionList = new JList();
        btnLoadFromDatabase = new JButton();
        Ranks = new JPanel();
        scrollPane5 = new JScrollPane();
        ranklist = new JList();
        txtRankName = new JTextField();
        RandName = new JLabel();
        label3 = new JLabel();
        txtdoorscore = new JTextField();
        label4 = new JLabel();
        txthighestscore = new JTextField();
        label5 = new JLabel();
        txtlevel = new JTextField();
        AddToRanks = new JButton();
        RanksLoadFromFile = new JButton();
        RanksSaveToDatabase = new JButton();
        People = new JPanel();
        scrollPane3 = new JScrollPane();
        tabStus = new JTable();
        addTea = new JButton();
        LoadStus = new JButton();
        scrollPane4 = new JScrollPane();
        tabTeas = new JTable();
        LoadTeas = new JButton();
        stuName = new JTextField();
        label6 = new JLabel();
        label7 = new JLabel();
        stuPwd = new JTextField();
        label8 = new JLabel();
        stuPhone = new JTextField();
        teaTid = new JTextField();
        addStu = new JButton();
        label10 = new JLabel();
        label11 = new JLabel();
        teaDuty = new JTextField();
        scrollPane6 = new JScrollPane();
        dutylist = new JList();
        addDuty = new JButton();
        label12 = new JLabel();
        stuGradeClass = new JComboBox();
        label13 = new JLabel();
        stuTSc = new JTextField();
        label14 = new JLabel();
        label15 = new JLabel();
        rankName = new JTextField();
        ranklevel = new JTextField();
        rankAdjust = new JButton();
        teaPhone = new JTextField();
        label16 = new JLabel();
        label17 = new JLabel();
        teaName = new JTextField();
        stuQq = new JTextField();
        label18 = new JLabel();
        label19 = new JLabel();
        teaPwd = new JTextField();
        separator1 = new JSeparator();
        Teams = new JPanel();
        txtGradeClass = new JTextField();
        btnAddGradeClass = new JButton();
        scrollPane7 = new JScrollPane();
        classlist = new JList();
        btnGCFromDatabase = new JButton();
        gradeClassSaveToDatabase = new JButton();
        scrollPane8 = new JScrollPane();
        list1 = new JList();
        btnLoadAllStus = new JButton();
        button5 = new JButton();
        Infos = new JPanel();
        Actions = new JPanel();
        CommonDayActions = new JPanel();
        btndaystudy = new JButton();
        scrollPane9 = new JScrollPane();
        daylist = new JList();
        btndayneiwu = new JButton();
        btndayweisheng = new JButton();
        btndaywenming = new JButton();
        btndayjilv = new JButton();
        btnweeksixiang = new JButton();
        Photos = new JPanel();
        subjects = new JPanel();
        scrollPane10 = new JScrollPane();
        listSubjects = new JList();
        txtSubjectName = new JTextField();
        label9 = new JLabel();
        label20 = new JLabel();
        label21 = new JLabel();
        txtSubStartTime = new JTextField();
        label22 = new JLabel();
        label23 = new JLabel();
        txtSubEndTime = new JTextField();
        scrollPane11 = new JScrollPane();
        txtSubjectInfo = new JTextArea();
        btnAddSubjects = new JButton();
        btnSubDel = new JButton();
        Analysis = new JPanel();

        //======== this ========
        setTitle("\u783a\u5fd7\u5b66\u6821\u540e\u53f0\u6570\u636e\u7cfb\u7edf");
        setIconImage(new ImageIcon("D:\\Users\\Feng\\eclipseworkspace\\SchoolDataManager\\src\\main\\java\\lizhi\\settings_1351px_1202476_easyicon.net.png").getImage());
        setMinimumSize(new Dimension(980, 450));
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                thisWindowClosed(e);
            }
        });
        Container contentPane = getContentPane();

        //======== panel1 ========
        {

            //======== lihzidata ========
            {
                lihzidata.setMinimumSize(new Dimension(980, 450));
                lihzidata.setMaximumSize(new Dimension(980, 450));

                //======== DefaultValues ========
                {
                    DefaultValues.setMinimumSize(new Dimension(980, 450));
                    DefaultValues.setMaximumSize(new Dimension(980, 450));

                    //---- label1 ----
                    label1.setText("actionName");

                    //---- label2 ----
                    label2.setText("defaultValue");

                    //---- addToMap ----
                    addToMap.setText("addToMap");
                    addToMap.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseClicked(MouseEvent e) {
                            addToMapMouseClicked(e);
                        }
                    });

                    //---- txtFilename ----
                    txtFilename.setText("D:\\1boteteam\\product\\GrowUp\\defaultValue.json");

                    //---- saveToFile ----
                    saveToFile.setText("SaveToFile");
                    saveToFile.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseClicked(MouseEvent e) {
                            saveToFileMouseClicked(e);
                        }
                    });

                    //---- LoadFromFile ----
                    LoadFromFile.setText("LoadFromFile");
                    LoadFromFile.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseClicked(MouseEvent e) {
                            LoadFromFileMouseClicked(e);
                        }
                    });

                    //---- searchName ----
                    searchName.setText("SearchByName");
                    searchName.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseClicked(MouseEvent e) {
                            searchNameMouseClicked(e);
                        }
                    });

                    //======== scrollPane2 ========
                    {
                        scrollPane2.setViewportView(txtSearchresult);
                    }

                    //---- textField3 ----
                    textField3.setText("D:\\1boteteam\\product\\GrowUp\\defaultValue.json");

                    //---- btnSearchByVal ----
                    btnSearchByVal.setText("SearchByVal");
                    btnSearchByVal.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseClicked(MouseEvent e) {
                            btnSearchByValMouseClicked(e);
                        }
                    });

                    //---- defaultValueeSaveToDatabase ----
                    defaultValueeSaveToDatabase.setText("SaveToDatabase");
                    defaultValueeSaveToDatabase.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseClicked(MouseEvent e) {
                            defaultValuesFromDatabaseMouseClicked(e);
                            defaultValueeSaveToDatabaseMouseClicked(e);
                            defaultValueeSaveToDatabaseMouseClicked(e);
                            defaultValueeSaveToDatabaseMouseClicked(e);
                        }
                    });

                    //======== scrollPane1 ========
                    {

                        //---- actionList ----
                        actionList.addListSelectionListener(e -> actionListValueChanged(e));
                        scrollPane1.setViewportView(actionList);
                    }

                    //---- btnLoadFromDatabase ----
                    btnLoadFromDatabase.setText("LoadFromDatabase");
                    btnLoadFromDatabase.addActionListener(e -> btnLoadFromDatabaseActionPerformed(e));

                    GroupLayout DefaultValuesLayout = new GroupLayout(DefaultValues);
                    DefaultValues.setLayout(DefaultValuesLayout);
                    DefaultValuesLayout.setHorizontalGroup(
                        DefaultValuesLayout.createParallelGroup()
                            .addGroup(DefaultValuesLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(DefaultValuesLayout.createParallelGroup()
                                    .addGroup(DefaultValuesLayout.createSequentialGroup()
                                        .addComponent(txtSearchStr, GroupLayout.PREFERRED_SIZE, 137, GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(searchName, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtSerchVal, GroupLayout.PREFERRED_SIZE, 59, GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(btnSearchByVal))
                                    .addGroup(DefaultValuesLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                        .addGroup(DefaultValuesLayout.createSequentialGroup()
                                            .addComponent(defaultValueeSaveToDatabase, GroupLayout.PREFERRED_SIZE, 227, GroupLayout.PREFERRED_SIZE)
                                            .addGap(18, 18, 18)
                                            .addComponent(btnLoadFromDatabase, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addComponent(scrollPane2, GroupLayout.PREFERRED_SIZE, 344, GroupLayout.PREFERRED_SIZE)
                                        .addGroup(DefaultValuesLayout.createSequentialGroup()
                                            .addComponent(label1)
                                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(txtActionName, GroupLayout.PREFERRED_SIZE, 139, GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(label2)
                                            .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                            .addComponent(txtValue, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(addToMap))))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(DefaultValuesLayout.createParallelGroup()
                                    .addComponent(scrollPane1, GroupLayout.PREFERRED_SIZE, 407, GroupLayout.PREFERRED_SIZE)
                                    .addGroup(DefaultValuesLayout.createSequentialGroup()
                                        .addComponent(textField3, GroupLayout.PREFERRED_SIZE, 270, GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(LoadFromFile, GroupLayout.PREFERRED_SIZE, 108, GroupLayout.PREFERRED_SIZE))
                                    .addGroup(DefaultValuesLayout.createSequentialGroup()
                                        .addComponent(txtFilename, GroupLayout.PREFERRED_SIZE, 275, GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(saveToFile)))
                                .addGap(483, 483, 483))
                    );
                    DefaultValuesLayout.setVerticalGroup(
                        DefaultValuesLayout.createParallelGroup()
                            .addGroup(GroupLayout.Alignment.TRAILING, DefaultValuesLayout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addGroup(DefaultValuesLayout.createParallelGroup()
                                    .addGroup(DefaultValuesLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(txtActionName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(label2)
                                        .addComponent(label1)
                                        .addComponent(txtValue, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(addToMap))
                                    .addGroup(GroupLayout.Alignment.TRAILING, DefaultValuesLayout.createParallelGroup()
                                        .addComponent(LoadFromFile)
                                        .addComponent(textField3, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
                                .addGap(18, 18, 18)
                                .addGroup(DefaultValuesLayout.createParallelGroup()
                                    .addGroup(DefaultValuesLayout.createSequentialGroup()
                                        .addGroup(DefaultValuesLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                            .addComponent(txtSearchStr, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                            .addComponent(searchName)
                                            .addComponent(txtSerchVal, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                            .addComponent(btnSearchByVal))
                                        .addGap(18, 18, 18)
                                        .addComponent(scrollPane2, GroupLayout.PREFERRED_SIZE, 221, GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addGroup(DefaultValuesLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                            .addComponent(defaultValueeSaveToDatabase)
                                            .addComponent(btnLoadFromDatabase))
                                        .addGap(0, 151, Short.MAX_VALUE))
                                    .addComponent(scrollPane1, GroupLayout.DEFAULT_SIZE, 448, Short.MAX_VALUE))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(DefaultValuesLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                    .addComponent(txtFilename, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                    .addComponent(saveToFile))
                                .addGap(10, 10, 10))
                    );
                }
                lihzidata.addTab("DefaultValues", DefaultValues);

                //======== Ranks ========
                {
                    Ranks.setMinimumSize(new Dimension(980, 450));
                    Ranks.setMaximumSize(new Dimension(980, 450));

                    //======== scrollPane5 ========
                    {

                        //---- ranklist ----
                        ranklist.addListSelectionListener(e -> ranklistValueChanged(e));
                        scrollPane5.setViewportView(ranklist);
                    }

                    //---- RandName ----
                    RandName.setText("\u7ea7\u522b\u540d\u79f0");

                    //---- label3 ----
                    label3.setText("\u95e8\u69db\u5206\u6570");

                    //---- label4 ----
                    label4.setText("\u6700\u9ad8\u5206\u6570");

                    //---- label5 ----
                    label5.setText("\u7ea7\u522b\u6570");

                    //---- AddToRanks ----
                    AddToRanks.setText("AddToRanks");
                    AddToRanks.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseClicked(MouseEvent e) {
                            AddToRanksMouseClicked(e);
                        }
                    });

                    //---- RanksLoadFromFile ----
                    RanksLoadFromFile.setText("LoadFromDataBase");
                    RanksLoadFromFile.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseClicked(MouseEvent e) {
                            RanksLoadFromFileMouseClicked(e);
                        }
                    });

                    //---- RanksSaveToDatabase ----
                    RanksSaveToDatabase.setText("SaveToDataBase");
                    RanksSaveToDatabase.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseClicked(MouseEvent e) {
                            RanksSaveToFileMouseClicked(e);
                        }
                    });

                    GroupLayout RanksLayout = new GroupLayout(Ranks);
                    Ranks.setLayout(RanksLayout);
                    RanksLayout.setHorizontalGroup(
                        RanksLayout.createParallelGroup()
                            .addGroup(RanksLayout.createSequentialGroup()
                                .addGap(34, 34, 34)
                                .addGroup(RanksLayout.createParallelGroup()
                                    .addGroup(RanksLayout.createSequentialGroup()
                                        .addGroup(RanksLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                            .addComponent(RandName, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(label5, GroupLayout.PREFERRED_SIZE, 48, GroupLayout.PREFERRED_SIZE))
                                        .addGap(12, 12, 12)
                                        .addGroup(RanksLayout.createParallelGroup()
                                            .addGroup(RanksLayout.createSequentialGroup()
                                                .addComponent(txtRankName, GroupLayout.PREFERRED_SIZE, 119, GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(label3)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(txtdoorscore, GroupLayout.PREFERRED_SIZE, 103, GroupLayout.PREFERRED_SIZE)
                                                .addGap(24, 24, 24)
                                                .addComponent(label4))
                                            .addComponent(txtlevel, GroupLayout.PREFERRED_SIZE, 68, GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addGroup(RanksLayout.createParallelGroup()
                                            .addGroup(RanksLayout.createSequentialGroup()
                                                .addComponent(txthighestscore, GroupLayout.PREFERRED_SIZE, 106, GroupLayout.PREFERRED_SIZE)
                                                .addGap(14, 14, 14))
                                            .addGroup(GroupLayout.Alignment.TRAILING, RanksLayout.createSequentialGroup()
                                                .addComponent(AddToRanks, GroupLayout.PREFERRED_SIZE, 106, GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED))))
                                    .addGroup(GroupLayout.Alignment.TRAILING, RanksLayout.createSequentialGroup()
                                        .addGroup(RanksLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                            .addComponent(RanksSaveToDatabase, GroupLayout.PREFERRED_SIZE, 144, GroupLayout.PREFERRED_SIZE)
                                            .addComponent(RanksLoadFromFile))
                                        .addGap(18, 18, 18)))
                                .addComponent(scrollPane5, GroupLayout.PREFERRED_SIZE, 218, GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(209, Short.MAX_VALUE))
                    );
                    RanksLayout.setVerticalGroup(
                        RanksLayout.createParallelGroup()
                            .addGroup(RanksLayout.createSequentialGroup()
                                .addGap(32, 32, 32)
                                .addGroup(RanksLayout.createParallelGroup()
                                    .addComponent(scrollPane5, GroupLayout.PREFERRED_SIZE, 299, GroupLayout.PREFERRED_SIZE)
                                    .addGroup(RanksLayout.createSequentialGroup()
                                        .addGroup(RanksLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                            .addComponent(RandName)
                                            .addComponent(label3)
                                            .addComponent(label4)
                                            .addComponent(txtdoorscore, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                            .addComponent(txthighestscore, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                            .addComponent(txtRankName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(RanksLayout.createParallelGroup()
                                            .addGroup(RanksLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                                .addComponent(txtlevel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                .addComponent(label5))
                                            .addComponent(AddToRanks))
                                        .addGap(56, 56, 56)
                                        .addComponent(RanksLoadFromFile)
                                        .addGap(42, 42, 42)
                                        .addComponent(RanksSaveToDatabase)))
                                .addContainerGap(203, Short.MAX_VALUE))
                    );
                }
                lihzidata.addTab("Ranks", Ranks);

                //======== People ========
                {
                    People.setMinimumSize(new Dimension(980, 450));
                    People.setMaximumSize(new Dimension(980, 450));
                    People.addComponentListener(new ComponentAdapter() {
                        @Override
                        public void componentShown(ComponentEvent e) {
                            PeopleComponentShown(e);
                        }
                    });

                    //======== scrollPane3 ========
                    {

                        //---- tabStus ----
                        tabStus.addPropertyChangeListener("selectedRow", e -> tabStusPropertyChange(e));
                        scrollPane3.setViewportView(tabStus);
                    }

                    //---- addTea ----
                    addTea.setText("AddTea");
                    addTea.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseClicked(MouseEvent e) {
                            addTeaMouseClicked(e);
                        }
                    });

                    //---- LoadStus ----
                    LoadStus.setText("LoadStus");
                    LoadStus.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseClicked(MouseEvent e) {
                            LoadStusMouseClicked(e);
                            LoadStusMouseClicked(e);
                        }
                    });

                    //======== scrollPane4 ========
                    {
                        scrollPane4.setViewportView(tabTeas);
                    }

                    //---- LoadTeas ----
                    LoadTeas.setText("LoadTeas");
                    LoadTeas.addActionListener(e -> LoadTeasActionPerformed(e));

                    //---- label6 ----
                    label6.setText("\u59d3\u540d");

                    //---- label7 ----
                    label7.setText("\u5bc6\u7801");

                    //---- label8 ----
                    label8.setText("\u7535\u8bdd");

                    //---- addStu ----
                    addStu.setText("AddStu");
                    addStu.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseClicked(MouseEvent e) {
                            addStuMouseClicked(e);
                        }
                    });

                    //---- label10 ----
                    label10.setText("\u6559\u5e08\u5de5\u53f7");

                    //---- label11 ----
                    label11.setText("\u804c\u52a1");

                    //======== scrollPane6 ========
                    {
                        scrollPane6.setViewportView(dutylist);
                    }

                    //---- addDuty ----
                    addDuty.setText("\u589e\u52a0\u804c\u52a1>>");

                    //---- label12 ----
                    label12.setText("\u73ed\u7ea7");

                    //---- stuGradeClass ----
                    stuGradeClass.addActionListener(e -> stuGradeClassActionPerformed(e));

                    //---- label13 ----
                    label13.setText("\u603b\u79ef\u5206");

                    //---- label14 ----
                    label14.setText("\u5f53\u524d\u7b49\u7ea7\u540d\u79f0");

                    //---- label15 ----
                    label15.setText("\u7b49\u7ea7\u7ea7\u6570");

                    //---- rankAdjust ----
                    rankAdjust.setText("\u7b49\u7ea7\u81ea\u52a8\u6821\u51c6");

                    //---- label16 ----
                    label16.setText("QQ");

                    //---- label17 ----
                    label17.setText("\u6559\u5e08\u59d3\u540d");

                    //---- label18 ----
                    label18.setText("\u6559\u5e08\u5bc6\u7801");

                    //---- label19 ----
                    label19.setText("\u6559\u5e08\u7535\u8bdd");

                    //---- separator1 ----
                    separator1.setOrientation(SwingConstants.VERTICAL);

                    GroupLayout PeopleLayout = new GroupLayout(People);
                    People.setLayout(PeopleLayout);
                    PeopleLayout.setHorizontalGroup(
                        PeopleLayout.createParallelGroup()
                            .addGroup(PeopleLayout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addGroup(PeopleLayout.createParallelGroup()
                                    .addGroup(PeopleLayout.createSequentialGroup()
                                        .addGap(766, 766, 766)
                                        .addComponent(label18))
                                    .addGroup(PeopleLayout.createSequentialGroup()
                                        .addComponent(scrollPane3, GroupLayout.PREFERRED_SIZE, 424, GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(LoadStus)
                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(scrollPane4, GroupLayout.PREFERRED_SIZE, 388, GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(LoadTeas))
                                    .addGroup(PeopleLayout.createSequentialGroup()
                                        .addGroup(PeopleLayout.createParallelGroup()
                                            .addGroup(PeopleLayout.createSequentialGroup()
                                                .addComponent(stuGradeClass, GroupLayout.PREFERRED_SIZE, 106, GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(stuTSc, GroupLayout.PREFERRED_SIZE, 90, GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(PeopleLayout.createParallelGroup(GroupLayout.Alignment.TRAILING, false)
                                                    .addGroup(PeopleLayout.createSequentialGroup()
                                                        .addComponent(label14)
                                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addComponent(label15))
                                                    .addGroup(PeopleLayout.createSequentialGroup()
                                                        .addComponent(rankName, GroupLayout.PREFERRED_SIZE, 103, GroupLayout.PREFERRED_SIZE)
                                                        .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                                        .addComponent(ranklevel, GroupLayout.PREFERRED_SIZE, 47, GroupLayout.PREFERRED_SIZE))
                                                    .addComponent(rankAdjust, GroupLayout.Alignment.LEADING)))
                                            .addGroup(PeopleLayout.createSequentialGroup()
                                                .addGroup(PeopleLayout.createParallelGroup()
                                                    .addGroup(PeopleLayout.createSequentialGroup()
                                                        .addComponent(stuName, GroupLayout.PREFERRED_SIZE, 120, GroupLayout.PREFERRED_SIZE)
                                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                        .addComponent(stuPwd, GroupLayout.PREFERRED_SIZE, 110, GroupLayout.PREFERRED_SIZE)
                                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                        .addGroup(PeopleLayout.createParallelGroup()
                                                            .addComponent(label8)
                                                            .addComponent(stuPhone, GroupLayout.PREFERRED_SIZE, 137, GroupLayout.PREFERRED_SIZE)))
                                                    .addGroup(GroupLayout.Alignment.TRAILING, PeopleLayout.createSequentialGroup()
                                                        .addGroup(PeopleLayout.createParallelGroup()
                                                            .addComponent(label6)
                                                            .addGroup(PeopleLayout.createParallelGroup()
                                                                .addGroup(PeopleLayout.createSequentialGroup()
                                                                    .addGap(130, 130, 130)
                                                                    .addComponent(label7))
                                                                .addGroup(GroupLayout.Alignment.TRAILING, PeopleLayout.createSequentialGroup()
                                                                    .addGap(118, 118, 118)
                                                                    .addComponent(label13)))
                                                            .addComponent(label12, GroupLayout.PREFERRED_SIZE, 72, GroupLayout.PREFERRED_SIZE))
                                                        .addGap(231, 231, 231)))
                                                .addGroup(PeopleLayout.createParallelGroup()
                                                    .addComponent(stuQq, GroupLayout.PREFERRED_SIZE, 111, GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(label16)))
                                            .addComponent(addStu, GroupLayout.Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 114, GroupLayout.PREFERRED_SIZE))
                                        .addGroup(PeopleLayout.createParallelGroup()
                                            .addGroup(PeopleLayout.createSequentialGroup()
                                                .addGap(7, 7, 7)
                                                .addComponent(separator1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                .addGap(9, 9, 9)
                                                .addGroup(PeopleLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                                    .addGroup(PeopleLayout.createSequentialGroup()
                                                        .addGroup(PeopleLayout.createParallelGroup()
                                                            .addComponent(teaName, GroupLayout.PREFERRED_SIZE, 83, GroupLayout.PREFERRED_SIZE)
                                                            .addComponent(label17))
                                                        .addGap(18, 18, 18)
                                                        .addGroup(PeopleLayout.createParallelGroup()
                                                            .addComponent(teaPhone, GroupLayout.PREFERRED_SIZE, 102, GroupLayout.PREFERRED_SIZE)
                                                            .addComponent(label19))
                                                        .addGap(37, 37, 37))
                                                    .addGroup(PeopleLayout.createSequentialGroup()
                                                        .addGroup(PeopleLayout.createParallelGroup()
                                                            .addComponent(teaTid, GroupLayout.PREFERRED_SIZE, 122, GroupLayout.PREFERRED_SIZE)
                                                            .addComponent(label10))
                                                        .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                                        .addGroup(PeopleLayout.createParallelGroup()
                                                            .addComponent(label11)
                                                            .addGroup(GroupLayout.Alignment.TRAILING, PeopleLayout.createSequentialGroup()
                                                                .addGroup(PeopleLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                                                    .addGroup(PeopleLayout.createSequentialGroup()
                                                                        .addGap(0, 0, Short.MAX_VALUE)
                                                                        .addComponent(addDuty, GroupLayout.PREFERRED_SIZE, 88, GroupLayout.PREFERRED_SIZE))
                                                                    .addComponent(teaDuty))
                                                                .addGap(18, 18, 18)))))
                                                .addGroup(PeopleLayout.createParallelGroup()
                                                    .addComponent(teaPwd, GroupLayout.PREFERRED_SIZE, 161, GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(scrollPane6, GroupLayout.PREFERRED_SIZE, 195, GroupLayout.PREFERRED_SIZE))
                                                .addGap(213, 213, 213))
                                            .addGroup(GroupLayout.Alignment.TRAILING, PeopleLayout.createSequentialGroup()
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(addTea, GroupLayout.PREFERRED_SIZE, 109, GroupLayout.PREFERRED_SIZE)
                                                .addGap(166, 166, 166))))))
                    );
                    PeopleLayout.setVerticalGroup(
                        PeopleLayout.createParallelGroup()
                            .addGroup(GroupLayout.Alignment.TRAILING, PeopleLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(PeopleLayout.createParallelGroup()
                                    .addGroup(PeopleLayout.createSequentialGroup()
                                        .addGroup(PeopleLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                            .addGroup(PeopleLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                                .addComponent(label6)
                                                .addComponent(label7)
                                                .addComponent(label8)
                                                .addComponent(label16))
                                            .addGroup(GroupLayout.Alignment.LEADING, PeopleLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                                .addComponent(label17)
                                                .addComponent(label18)
                                                .addComponent(label19)))
                                        .addGap(1, 1, 1)
                                        .addGroup(PeopleLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                            .addComponent(stuName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                            .addComponent(stuPwd, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                            .addComponent(stuPhone, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                            .addComponent(stuQq, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                            .addComponent(teaName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                            .addComponent(teaPhone, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                            .addComponent(teaPwd, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                        .addGap(13, 13, 13)
                                        .addGroup(PeopleLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                            .addGroup(PeopleLayout.createParallelGroup()
                                                .addGroup(PeopleLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                                    .addComponent(label12)
                                                    .addComponent(label13)
                                                    .addComponent(label14)
                                                    .addComponent(label15))
                                                .addComponent(label10, GroupLayout.Alignment.TRAILING))
                                            .addComponent(label11))
                                        .addGroup(PeopleLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                            .addGroup(PeopleLayout.createSequentialGroup()
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(stuGradeClass, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                            .addGroup(PeopleLayout.createSequentialGroup()
                                                .addGap(18, 18, 18)
                                                .addGroup(PeopleLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                                    .addComponent(stuTSc, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(rankName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(ranklevel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(teaTid, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(teaDuty, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                                .addGroup(PeopleLayout.createParallelGroup()
                                                    .addGroup(PeopleLayout.createSequentialGroup()
                                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addComponent(addDuty)
                                                        .addGap(12, 12, 12))
                                                    .addGroup(PeopleLayout.createSequentialGroup()
                                                        .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                                        .addComponent(rankAdjust))))
                                            .addGroup(PeopleLayout.createSequentialGroup()
                                                .addGap(18, 18, 18)
                                                .addComponent(scrollPane6, GroupLayout.PREFERRED_SIZE, 93, GroupLayout.PREFERRED_SIZE))))
                                    .addComponent(separator1, GroupLayout.PREFERRED_SIZE, 130, GroupLayout.PREFERRED_SIZE))
                                .addGroup(PeopleLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                    .addComponent(addStu, GroupLayout.PREFERRED_SIZE, 68, GroupLayout.PREFERRED_SIZE)
                                    .addComponent(addTea, GroupLayout.PREFERRED_SIZE, 68, GroupLayout.PREFERRED_SIZE))
                                .addGap(33, 33, 33)
                                .addGroup(PeopleLayout.createParallelGroup()
                                    .addComponent(scrollPane3, GroupLayout.DEFAULT_SIZE, 245, Short.MAX_VALUE)
                                    .addComponent(scrollPane4, GroupLayout.DEFAULT_SIZE, 245, Short.MAX_VALUE)
                                    .addGroup(PeopleLayout.createSequentialGroup()
                                        .addGroup(PeopleLayout.createParallelGroup()
                                            .addComponent(LoadTeas)
                                            .addComponent(LoadStus))
                                        .addGap(0, 222, Short.MAX_VALUE)))
                                .addContainerGap())
                    );
                }
                lihzidata.addTab("People", People);

                //======== Teams ========
                {
                    Teams.setMinimumSize(new Dimension(980, 450));
                    Teams.setMaximumSize(new Dimension(980, 450));
                    Teams.addPropertyChangeListener(e -> TeamsPropertyChange(e));

                    //---- btnAddGradeClass ----
                    btnAddGradeClass.setText("\u589e\u52a0\u4fee\u6539\u73ed\u7ea7");
                    btnAddGradeClass.addActionListener(e -> btnAddGradeClassActionPerformed(e));

                    //======== scrollPane7 ========
                    {

                        //---- classlist ----
                        classlist.addListSelectionListener(e -> classlistValueChanged(e));
                        scrollPane7.setViewportView(classlist);
                    }

                    //---- btnGCFromDatabase ----
                    btnGCFromDatabase.setText("LoadFromDatabase");
                    btnGCFromDatabase.addActionListener(e -> btnGCFromDatabaseActionPerformed(e));

                    //---- gradeClassSaveToDatabase ----
                    gradeClassSaveToDatabase.setText("SaveToDatabase");
                    gradeClassSaveToDatabase.addActionListener(e -> gradeClassSaveToDatabaseActionPerformed(e));

                    //======== scrollPane8 ========
                    {
                        scrollPane8.setViewportView(list1);
                    }

                    //---- btnLoadAllStus ----
                    btnLoadAllStus.setText("LoadStus");
                    btnLoadAllStus.addActionListener(e -> btnLoadAllStusActionPerformed(e));

                    //---- button5 ----
                    button5.setText("AddtoClass");

                    GroupLayout TeamsLayout = new GroupLayout(Teams);
                    Teams.setLayout(TeamsLayout);
                    TeamsLayout.setHorizontalGroup(
                        TeamsLayout.createParallelGroup()
                            .addGroup(TeamsLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(TeamsLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                    .addComponent(txtGradeClass, GroupLayout.PREFERRED_SIZE, 175, GroupLayout.PREFERRED_SIZE)
                                    .addComponent(scrollPane7, GroupLayout.PREFERRED_SIZE, 175, GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(TeamsLayout.createParallelGroup()
                                    .addComponent(btnAddGradeClass, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(btnGCFromDatabase, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(gradeClassSaveToDatabase, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(258, 258, 258)
                                .addGroup(TeamsLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                    .addComponent(btnLoadAllStus, GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE)
                                    .addComponent(button5, GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE))
                                .addGap(18, 18, 18)
                                .addComponent(scrollPane8, GroupLayout.PREFERRED_SIZE, 274, GroupLayout.PREFERRED_SIZE)
                                .addGap(32, 32, 32))
                    );
                    TeamsLayout.setVerticalGroup(
                        TeamsLayout.createParallelGroup()
                            .addGroup(GroupLayout.Alignment.TRAILING, TeamsLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(TeamsLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                    .addComponent(scrollPane8)
                                    .addGroup(TeamsLayout.createSequentialGroup()
                                        .addGroup(TeamsLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                            .addComponent(txtGradeClass, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                            .addComponent(btnAddGradeClass, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(btnLoadAllStus))
                                        .addGap(18, 18, 18)
                                        .addGroup(TeamsLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                            .addComponent(scrollPane7, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                            .addGroup(TeamsLayout.createSequentialGroup()
                                                .addGroup(TeamsLayout.createParallelGroup()
                                                    .addComponent(btnGCFromDatabase)
                                                    .addComponent(button5))
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(gradeClassSaveToDatabase)))))
                                .addGap(323, 323, 323))
                    );
                }
                lihzidata.addTab("Teams", Teams);

                //======== Infos ========
                {
                    Infos.setMinimumSize(new Dimension(980, 450));
                    Infos.setMaximumSize(new Dimension(980, 450));

                    GroupLayout InfosLayout = new GroupLayout(Infos);
                    Infos.setLayout(InfosLayout);
                    InfosLayout.setHorizontalGroup(
                        InfosLayout.createParallelGroup()
                            .addGap(0, 1007, Short.MAX_VALUE)
                    );
                    InfosLayout.setVerticalGroup(
                        InfosLayout.createParallelGroup()
                            .addGap(0, 534, Short.MAX_VALUE)
                    );
                }
                lihzidata.addTab("Infos", Infos);

                //======== Actions ========
                {
                    Actions.setMinimumSize(new Dimension(980, 450));
                    Actions.setMaximumSize(new Dimension(980, 450));

                    GroupLayout ActionsLayout = new GroupLayout(Actions);
                    Actions.setLayout(ActionsLayout);
                    ActionsLayout.setHorizontalGroup(
                        ActionsLayout.createParallelGroup()
                            .addGap(0, 1007, Short.MAX_VALUE)
                    );
                    ActionsLayout.setVerticalGroup(
                        ActionsLayout.createParallelGroup()
                            .addGap(0, 534, Short.MAX_VALUE)
                    );
                }
                lihzidata.addTab("Actions", Actions);

                //======== CommonDayActions ========
                {
                    CommonDayActions.setMinimumSize(new Dimension(980, 450));
                    CommonDayActions.setMaximumSize(new Dimension(980, 450));
                    CommonDayActions.addComponentListener(new ComponentAdapter() {
                        @Override
                        public void componentShown(ComponentEvent e) {
                            CommonDayActionsComponentShown(e);
                        }
                    });

                    //---- btndaystudy ----
                    btndaystudy.setText("\u5b66\u4e60\u6807\u5175");
                    btndaystudy.addActionListener(e -> {
			btndaystudyActionPerformed(e);
			btndaystudyActionPerformed(e);
		});

                    //======== scrollPane9 ========
                    {
                        scrollPane9.setViewportView(daylist);
                    }

                    //---- btndayneiwu ----
                    btndayneiwu.setText("\u5185\u52a1\u6807\u5175");
                    btndayneiwu.addActionListener(e -> btndayneiwuActionPerformed(e));

                    //---- btndayweisheng ----
                    btndayweisheng.setText("\u536b\u751f\u6807\u5175");
                    btndayweisheng.addActionListener(e -> btndayweishengActionPerformed(e));

                    //---- btndaywenming ----
                    btndaywenming.setText("\u6587\u660e\u6807\u5175");
                    btndaywenming.addActionListener(e -> btndaywenmingActionPerformed(e));

                    //---- btndayjilv ----
                    btndayjilv.setText("\u7eaa\u5f8b\u6807\u5175");
                    btndayjilv.addActionListener(e -> btndayjilvActionPerformed(e));

                    //---- btnweeksixiang ----
                    btnweeksixiang.setText("\u601d\u60f3\u8fdb\u6b65\u6807\u5175");
                    btnweeksixiang.addActionListener(e -> btnweeksixiangActionPerformed(e));

                    GroupLayout CommonDayActionsLayout = new GroupLayout(CommonDayActions);
                    CommonDayActions.setLayout(CommonDayActionsLayout);
                    CommonDayActionsLayout.setHorizontalGroup(
                        CommonDayActionsLayout.createParallelGroup()
                            .addGroup(CommonDayActionsLayout.createSequentialGroup()
                                .addGap(68, 68, 68)
                                .addGroup(CommonDayActionsLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                    .addComponent(btndaystudy, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(btndayneiwu, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(btndayweisheng, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(btndaywenming, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(btndayjilv, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(btnweeksixiang, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(41, 41, 41)
                                .addComponent(scrollPane9, GroupLayout.PREFERRED_SIZE, 568, GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(225, Short.MAX_VALUE))
                    );
                    CommonDayActionsLayout.setVerticalGroup(
                        CommonDayActionsLayout.createParallelGroup()
                            .addGroup(CommonDayActionsLayout.createSequentialGroup()
                                .addGap(38, 38, 38)
                                .addGroup(CommonDayActionsLayout.createParallelGroup()
                                    .addComponent(scrollPane9, GroupLayout.PREFERRED_SIZE, 277, GroupLayout.PREFERRED_SIZE)
                                    .addGroup(CommonDayActionsLayout.createSequentialGroup()
                                        .addComponent(btndaystudy)
                                        .addGap(18, 18, 18)
                                        .addComponent(btndayneiwu)
                                        .addGap(18, 18, 18)
                                        .addComponent(btndayweisheng)
                                        .addGap(18, 18, 18)
                                        .addComponent(btndaywenming)
                                        .addGap(18, 18, 18)
                                        .addComponent(btndayjilv)
                                        .addGap(18, 18, 18)
                                        .addComponent(btnweeksixiang)))
                                .addContainerGap(219, Short.MAX_VALUE))
                    );
                }
                lihzidata.addTab("CommonDayActions", CommonDayActions);

                //======== Photos ========
                {
                    Photos.setMinimumSize(new Dimension(980, 450));
                    Photos.setMaximumSize(new Dimension(980, 450));

                    GroupLayout PhotosLayout = new GroupLayout(Photos);
                    Photos.setLayout(PhotosLayout);
                    PhotosLayout.setHorizontalGroup(
                        PhotosLayout.createParallelGroup()
                            .addGap(0, 1007, Short.MAX_VALUE)
                    );
                    PhotosLayout.setVerticalGroup(
                        PhotosLayout.createParallelGroup()
                            .addGap(0, 534, Short.MAX_VALUE)
                    );
                }
                lihzidata.addTab("Photos", Photos);

                //======== subjects ========
                {
                    subjects.addComponentListener(new ComponentAdapter() {
                        @Override
                        public void componentShown(ComponentEvent e) {
                            subjectsComponentShown(e);
                        }
                    });

                    //======== scrollPane10 ========
                    {

                        //---- listSubjects ----
                        listSubjects.addListSelectionListener(e -> listSubjectsValueChanged(e));
                        scrollPane10.setViewportView(listSubjects);
                    }

                    //---- label9 ----
                    label9.setText("\u540d\u79f0");

                    //---- label20 ----
                    label20.setText("\u4e13\u9898\u7b80\u4ecb");

                    //---- label21 ----
                    label21.setText("\u4e13\u9898\u65f6\u95f4");

                    //---- label22 ----
                    label22.setText("\u5f00\u59cb\u65f6\u95f4");

                    //---- label23 ----
                    label23.setText("\u7ed3\u675f\u65f6\u95f4");

                    //======== scrollPane11 ========
                    {
                        scrollPane11.setViewportView(txtSubjectInfo);
                    }

                    //---- btnAddSubjects ----
                    btnAddSubjects.setText("\u589e\u52a0");
                    btnAddSubjects.addActionListener(e -> btnAddSubjectsActionPerformed(e));

                    //---- btnSubDel ----
                    btnSubDel.setText("\u5220\u9664");
                    btnSubDel.addActionListener(e -> btnSubDelActionPerformed(e));

                    GroupLayout subjectsLayout = new GroupLayout(subjects);
                    subjects.setLayout(subjectsLayout);
                    subjectsLayout.setHorizontalGroup(
                        subjectsLayout.createParallelGroup()
                            .addGroup(subjectsLayout.createSequentialGroup()
                                .addGap(29, 29, 29)
                                .addComponent(scrollPane10, GroupLayout.PREFERRED_SIZE, 334, GroupLayout.PREFERRED_SIZE)
                                .addGap(60, 60, 60)
                                .addGroup(subjectsLayout.createParallelGroup()
                                    .addGroup(subjectsLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                        .addComponent(label9)
                                        .addComponent(label20)
                                        .addComponent(txtSubjectName)
                                        .addComponent(scrollPane11)
                                        .addComponent(label21)
                                        .addComponent(label22)
                                        .addGroup(subjectsLayout.createSequentialGroup()
                                            .addComponent(txtSubStartTime, GroupLayout.PREFERRED_SIZE, 142, GroupLayout.PREFERRED_SIZE)
                                            .addGap(40, 40, 40)
                                            .addGroup(subjectsLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                                .addComponent(txtSubEndTime, GroupLayout.DEFAULT_SIZE, 147, Short.MAX_VALUE)
                                                .addComponent(label23)
                                                .addComponent(btnSubDel, GroupLayout.DEFAULT_SIZE, 147, Short.MAX_VALUE))))
                                    .addComponent(btnAddSubjects, GroupLayout.PREFERRED_SIZE, 142, GroupLayout.PREFERRED_SIZE))
                                .addContainerGap(255, Short.MAX_VALUE))
                    );
                    subjectsLayout.setVerticalGroup(
                        subjectsLayout.createParallelGroup()
                            .addGroup(subjectsLayout.createSequentialGroup()
                                .addGroup(subjectsLayout.createParallelGroup()
                                    .addGroup(subjectsLayout.createSequentialGroup()
                                        .addGap(25, 25, 25)
                                        .addComponent(scrollPane10, GroupLayout.PREFERRED_SIZE, 482, GroupLayout.PREFERRED_SIZE))
                                    .addGroup(subjectsLayout.createSequentialGroup()
                                        .addGap(24, 24, 24)
                                        .addComponent(label9)
                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtSubjectName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(label20)
                                        .addGap(18, 18, 18)
                                        .addComponent(scrollPane11, GroupLayout.PREFERRED_SIZE, 132, GroupLayout.PREFERRED_SIZE)
                                        .addGap(28, 28, 28)
                                        .addComponent(label21)
                                        .addGap(18, 18, 18)
                                        .addGroup(subjectsLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                            .addComponent(label22)
                                            .addComponent(label23))
                                        .addGap(18, 18, 18)
                                        .addGroup(subjectsLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                            .addComponent(txtSubStartTime, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                            .addComponent(txtSubEndTime, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                        .addGap(18, 18, 18)
                                        .addGroup(subjectsLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                            .addComponent(btnAddSubjects)
                                            .addComponent(btnSubDel))))
                                .addContainerGap(27, Short.MAX_VALUE))
                    );
                }
                lihzidata.addTab("\u4e13\u9898", subjects);

                //======== Analysis ========
                {
                    Analysis.setMinimumSize(new Dimension(980, 450));
                    Analysis.setMaximumSize(new Dimension(980, 450));

                    GroupLayout AnalysisLayout = new GroupLayout(Analysis);
                    Analysis.setLayout(AnalysisLayout);
                    AnalysisLayout.setHorizontalGroup(
                        AnalysisLayout.createParallelGroup()
                            .addGap(0, 1007, Short.MAX_VALUE)
                    );
                    AnalysisLayout.setVerticalGroup(
                        AnalysisLayout.createParallelGroup()
                            .addGap(0, 534, Short.MAX_VALUE)
                    );
                }
                lihzidata.addTab("Analysis", Analysis);
            }

            GroupLayout panel1Layout = new GroupLayout(panel1);
            panel1.setLayout(panel1Layout);
            panel1Layout.setHorizontalGroup(
                panel1Layout.createParallelGroup()
                    .addGroup(panel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(lihzidata, GroupLayout.DEFAULT_SIZE, 1012, Short.MAX_VALUE)
                        .addContainerGap())
            );
            panel1Layout.setVerticalGroup(
                panel1Layout.createParallelGroup()
                    .addGroup(panel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(lihzidata, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
            );
        }

        GroupLayout contentPaneLayout = new GroupLayout(contentPane);
        contentPane.setLayout(contentPaneLayout);
        contentPaneLayout.setHorizontalGroup(
            contentPaneLayout.createParallelGroup()
                .addComponent(panel1, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        contentPaneLayout.setVerticalGroup(
            contentPaneLayout.createParallelGroup()
                .addGroup(GroupLayout.Alignment.TRAILING, contentPaneLayout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(panel1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
        );
        setSize(1040, 600);
        setLocationRelativeTo(null);
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private JPanel panel1;
    private JTabbedPane lihzidata;
    private JPanel DefaultValues;
    private JLabel label1;
    private JTextField txtActionName;
    private JLabel label2;
    private JTextField txtValue;
    private JButton addToMap;
    private JTextField txtFilename;
    private JButton saveToFile;
    private JButton LoadFromFile;
    private JTextField txtSearchStr;
    private JButton searchName;
    private JScrollPane scrollPane2;
    private JTextArea txtSearchresult;
    private JTextField textField3;
    private JTextField txtSerchVal;
    private JButton btnSearchByVal;
    private JButton defaultValueeSaveToDatabase;
    private JScrollPane scrollPane1;
    private JList actionList;
    private JButton btnLoadFromDatabase;
    private JPanel Ranks;
    private JScrollPane scrollPane5;
    private JList ranklist;
    private JTextField txtRankName;
    private JLabel RandName;
    private JLabel label3;
    private JTextField txtdoorscore;
    private JLabel label4;
    private JTextField txthighestscore;
    private JLabel label5;
    private JTextField txtlevel;
    private JButton AddToRanks;
    private JButton RanksLoadFromFile;
    private JButton RanksSaveToDatabase;
    private JPanel People;
    private JScrollPane scrollPane3;
    private JTable tabStus;
    private JButton addTea;
    private JButton LoadStus;
    private JScrollPane scrollPane4;
    private JTable tabTeas;
    private JButton LoadTeas;
    private JTextField stuName;
    private JLabel label6;
    private JLabel label7;
    private JTextField stuPwd;
    private JLabel label8;
    private JTextField stuPhone;
    private JTextField teaTid;
    private JButton addStu;
    private JLabel label10;
    private JLabel label11;
    private JTextField teaDuty;
    private JScrollPane scrollPane6;
    private JList dutylist;
    private JButton addDuty;
    private JLabel label12;
    private JComboBox stuGradeClass;
    private JLabel label13;
    private JTextField stuTSc;
    private JLabel label14;
    private JLabel label15;
    private JTextField rankName;
    private JTextField ranklevel;
    private JButton rankAdjust;
    private JTextField teaPhone;
    private JLabel label16;
    private JLabel label17;
    private JTextField teaName;
    private JTextField stuQq;
    private JLabel label18;
    private JLabel label19;
    private JTextField teaPwd;
    private JSeparator separator1;
    private JPanel Teams;
    private JTextField txtGradeClass;
    private JButton btnAddGradeClass;
    private JScrollPane scrollPane7;
    private JList classlist;
    private JButton btnGCFromDatabase;
    private JButton gradeClassSaveToDatabase;
    private JScrollPane scrollPane8;
    private JList list1;
    private JButton btnLoadAllStus;
    private JButton button5;
    private JPanel Infos;
    private JPanel Actions;
    private JPanel CommonDayActions;
    private JButton btndaystudy;
    private JScrollPane scrollPane9;
    private JList daylist;
    private JButton btndayneiwu;
    private JButton btndayweisheng;
    private JButton btndaywenming;
    private JButton btndayjilv;
    private JButton btnweeksixiang;
    private JPanel Photos;
    private JPanel subjects;
    private JScrollPane scrollPane10;
    private JList listSubjects;
    private JTextField txtSubjectName;
    private JLabel label9;
    private JLabel label20;
    private JLabel label21;
    private JTextField txtSubStartTime;
    private JLabel label22;
    private JLabel label23;
    private JTextField txtSubEndTime;
    private JScrollPane scrollPane11;
    private JTextArea txtSubjectInfo;
    private JButton btnAddSubjects;
    private JButton btnSubDel;
    private JPanel Analysis;
    // JFormDesigner - End of variables declaration  //GEN-END:variables


    public static void main(String[] args) {
        // TODO Auto-generated method stub
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new lizhiMain().setVisible(true);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
