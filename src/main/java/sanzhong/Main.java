package sanzhong;
import java.awt.*;
    import com.google.gson.Gson;
    import com.google.gson.GsonBuilder;

    import javax.swing.*;
    import java.awt.event.ActionEvent;
    import java.awt.event.ActionListener;
    import java.io.*;
    import java.util.HashMap;
    import java.util.Map;
    import java.util.prefs.Preferences;

    public class Main extends javax.swing.JFrame {


        // Variables declaration - do not modify
        private javax.swing.JButton btnSelStuFile;
        private javax.swing.JButton btnSelTeaFile;
        private javax.swing.JButton btnStuTrans;
        private javax.swing.JButton btnTeaTrans;
        private javax.swing.JPanel fileTrans;
        private javax.swing.JSeparator jSeparator1;
        private javax.swing.JTextField txtStuInFile;
        private javax.swing.JTextField txtStuOutFile;
        private javax.swing.JTextField txtTeaInFile;
        private javax.swing.JTextField txtTeaOutFile;
        // End of variables declaration
        private static final long serialVersionUID = 1L;

        private String userpath = "";
        private String stufilename = "";
        private String teafilename = "";
        private Gson gson;
        private String json = "";
        private Map<String, Student> stumap = new HashMap<String, Student>();
        private Map<String, Student> teamap = new HashMap<String, Student>();

        public Main() {
            initComponents();
            this.setLocationRelativeTo(null); //
            this.setTitle("schooldatatool");

            //lastpath
            Preferences pre = Preferences.systemRoot().node("/schooldatatool");
            userpath = pre.get("userpath", System.getProperty("user.dir"));
            // pre.put("userpath", userpath);

        }

        private void btnStuTransActionPerformed(java.awt.event.ActionEvent evt) {
            // TODO add your handling code here:
            gson = new GsonBuilder().create();
            stufilename = txtStuInFile.getText();
            if (stufilename.length() <= 0)
                return;
            // System.out.println(stufilename);
            stumap = readTxtFile(stufilename);
            json = gson.toJson(stumap);
            // System.out.println(json);

            if (txtStuOutFile.getText().indexOf(".json") >= 0)
                jsonTofile(txtStuOutFile.getText(), json);
        }

        private void btnTeaTransActionPerformed(java.awt.event.ActionEvent evt) {
            gson = new GsonBuilder().create();
            teafilename = txtTeaInFile.getText();
            if (teafilename.length() <= 0)
                return;
            // System.out.println(teafilename);
            teamap = readTxtFile(teafilename);
            json = gson.toJson(teamap);
            // System.out.println(json);

            if (txtTeaOutFile.getText().indexOf(".json") >= 0)
                jsonTofile(txtTeaOutFile.getText(), json);

        }

        private void btnSelStuFileActionPerformed(java.awt.event.ActionEvent evt) {
            // TODO add your handling code here:
            JFileChooser fileChooser = new JFileChooser(userpath);
            int result;
            result = fileChooser.showOpenDialog(this);

            if (result == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                stufilename = file.getName();
                userpath = file.getPath();
                userpath = userpath.substring(0, userpath.lastIndexOf(File.separator));

                Preferences pre = Preferences.systemRoot().node("/schooldatatool");
                pre.put("userpath", userpath);

                txtStuInFile.setText(file.getPath());
                txtStuOutFile.setText(
                        userpath + File.separator + stufilename.substring(0, stufilename.lastIndexOf(".") + 1) + "json");
                // System.out.println(userpath);
            }

        }

        private void btnSelTeaFileActionPerformed(ActionEvent e) {

            JFileChooser fileChooser = new JFileChooser(userpath);
            int result;
            result = fileChooser.showOpenDialog(this);
            if (result == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                teafilename = file.getName();
                userpath = file.getPath();
                userpath = userpath.substring(0, userpath.lastIndexOf(File.separator));

                Preferences pre = Preferences.systemRoot().node("/schooldatatool");
                pre.put("userpath", userpath);

                txtTeaInFile.setText(file.getPath());
                txtTeaOutFile.setText(
                        userpath + File.separator + stufilename.substring(0, stufilename.lastIndexOf(".") + 1) + "json");
                // System.out.println(userpath);
            }
        }

        private void txtStuOutFileActionPerformed(java.awt.event.ActionEvent evt) {
            // TODO add your handling code here:
        }

        public static void main(String[] args) {
            java.awt.EventQueue.invokeLater(new Runnable() {
                public void run() {
                    new Main().setVisible(true);
                }
            });

        }

        public static void jsonTofile(String filename, String json)

        {
            File file = new File(filename);
            OutputStreamWriter write;
            try {
                write = new OutputStreamWriter(new FileOutputStream(file), "utf-8");
                BufferedWriter writer = new BufferedWriter(write);
                writer.write(json);
                writer.close();

            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }

        public static Map<String, Student> readTxtFile(String filePath) {

            Map<String, Student> map = new HashMap<String, Student>();

            try {
                // String encoding="UTF-16";
                File file = new File(filePath);
                if (file.isFile() && file.exists()) {
                    BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filePath), "utf-8"));
                    String lineTxt = null;
                    int i = 0;
                    while ((lineTxt = br.readLine()) != null) {
                        i = i + 1;
                        Student stu1 = new Student(lineTxt);
                        map.put(String.format("%06d", i), stu1);
                    }

                    System.out.println(i);
                    br.close();
                } else {
                    // System.out.println("");
                }
            } catch (Exception e) {
                // System.out.println("");
                e.printStackTrace();
            }

            return map;

        }

        private void initComponents() {

            fileTrans = new javax.swing.JPanel();
            btnSelStuFile = new javax.swing.JButton();
            txtStuInFile = new javax.swing.JTextField();
            txtStuOutFile = new javax.swing.JTextField();
            btnStuTrans = new javax.swing.JButton();
            jSeparator1 = new javax.swing.JSeparator();
            txtTeaInFile = new javax.swing.JTextField();
            btnSelTeaFile = new javax.swing.JButton();
            btnSelTeaFile.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    btnSelTeaFileActionPerformed(e);
                }
            });

            txtTeaOutFile = new javax.swing.JTextField();
            btnTeaTrans = new javax.swing.JButton();

            setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

            btnSelStuFile.setText("Select StuFile");
            btnSelStuFile.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    btnSelStuFileActionPerformed(evt);
                }
            });

            txtStuOutFile.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    txtStuOutFileActionPerformed(evt);
                }
            });

            btnStuTrans.setText("Trans");

            btnStuTrans.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    btnStuTransActionPerformed(evt);
                }
            });

            btnSelTeaFile.setText("Select TeaFile");
            btnTeaTrans.setText("Trans");
            btnTeaTrans.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    btnTeaTransActionPerformed(evt);
                }
            });

            javax.swing.GroupLayout fileTransLayout = new javax.swing.GroupLayout(fileTrans);
            fileTrans.setLayout(fileTransLayout);
            fileTransLayout
                    .setHorizontalGroup(fileTransLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGap(0, 434, Short.MAX_VALUE).addGroup(fileTransLayout
                                    .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(fileTransLayout
                                            .createSequentialGroup().addContainerGap().addGroup(
                                                    fileTransLayout
                                                            .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                            .addComponent(jSeparator1,
                                                                    javax.swing.GroupLayout.Alignment.TRAILING)
                                                            .addGroup(fileTransLayout.createSequentialGroup().addGap(21, 21,
                                                                    21).addGroup(
                                                                    fileTransLayout
                                                                            .createParallelGroup(
                                                                                    javax.swing.GroupLayout.Alignment.LEADING)
                                                                            .addComponent(txtStuOutFile)
                                                                            .addGroup(fileTransLayout
                                                                                    .createSequentialGroup()
                                                                                    .addComponent(txtStuInFile)
                                                                                    .addGap(18, 18, 18)
                                                                                    .addComponent(btnSelStuFile))
                                                                            .addGroup(
                                                                                    fileTransLayout
                                                                                            .createSequentialGroup()
                                                                                            .addComponent(
                                                                                                    btnStuTrans,
                                                                                                    javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                    75,
                                                                                                    javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                            .addGap(0,
                                                                                                    0,
                                                                                                    Short.MAX_VALUE))))
                                                            .addGroup(fileTransLayout.createSequentialGroup()
                                                                    .addGap(23, 23, 23)
                                                                    .addGroup(fileTransLayout
                                                                            .createParallelGroup(
                                                                                    javax.swing.GroupLayout.Alignment.LEADING)
                                                                            .addGroup(fileTransLayout
                                                                                    .createParallelGroup(
                                                                                            javax.swing.GroupLayout.Alignment.LEADING,
                                                                                            false)
                                                                                    .addComponent(txtTeaOutFile)
                                                                                    .addComponent(txtTeaInFile,
                                                                                            javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                            268,
                                                                                            javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                            .addComponent(btnTeaTrans,
                                                                                    javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                    72,
                                                                                    javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                    .addGap(18, 18, Short.MAX_VALUE)
                                                                    .addComponent(btnSelTeaFile)))
                                            .addContainerGap())));
            fileTransLayout
                    .setVerticalGroup(fileTransLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGap(0, 290, Short.MAX_VALUE).addGroup(fileTransLayout
                                    .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(fileTransLayout.createSequentialGroup().addContainerGap()
                                            .addGroup(fileTransLayout
                                                    .createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                    .addComponent(btnSelStuFile)
                                                    .addComponent(txtStuInFile, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                            javax.swing.GroupLayout.DEFAULT_SIZE,
                                                            javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGap(18, 18, 18)
                                            .addComponent(txtStuOutFile, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                    javax.swing.GroupLayout.DEFAULT_SIZE,
                                                    javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGap(18, 18, 18)
                                            .addComponent(btnStuTrans, javax.swing.GroupLayout.PREFERRED_SIZE, 31,
                                                    javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGap(26, 26, 26)
                                            .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                    javax.swing.GroupLayout.DEFAULT_SIZE,
                                                    javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGap(27, 27, 27)
                                            .addGroup(fileTransLayout
                                                    .createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                    .addComponent(txtTeaInFile, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                            javax.swing.GroupLayout.DEFAULT_SIZE,
                                                            javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(btnSelTeaFile))
                                            .addGap(18, 18, 18)
                                            .addComponent(txtTeaOutFile, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                    javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                            .addComponent(btnTeaTrans, javax.swing.GroupLayout.PREFERRED_SIZE, 32,
                                                    javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))));

            javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
            getContentPane().setLayout(layout);
            layout.setHorizontalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(
                    fileTrans, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
                    javax.swing.GroupLayout.PREFERRED_SIZE));
            layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(
                    fileTrans, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
                    javax.swing.GroupLayout.PREFERRED_SIZE));

            pack();
        }// </editor-fold>


    public class SchoolDataManager extends JFrame {
        public SchoolDataManager() {
            initComponents();
        }

        private void initComponents() {
            // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents

            //======== this ========
            Container contentPane = getContentPane();

            GroupLayout contentPaneLayout = new GroupLayout(contentPane);
            contentPane.setLayout(contentPaneLayout);
            contentPaneLayout.setHorizontalGroup(
                contentPaneLayout.createParallelGroup()
                    .addGap(0, 524, Short.MAX_VALUE)
            );
            contentPaneLayout.setVerticalGroup(
                contentPaneLayout.createParallelGroup()
                    .addGap(0, 306, Short.MAX_VALUE)
            );
            pack();
            setLocationRelativeTo(getOwner());
            // JFormDesigner - End of component initialization  //GEN-END:initComponents
        }

        // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
        // JFormDesigner - End of variables declaration  //GEN-END:variables
    }
    }


