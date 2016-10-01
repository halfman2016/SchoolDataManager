/*
 * Created by JFormDesigner on Thu Jul 14 19:27:25 CST 2016
 */

package HAPPYREAD;

import HAPPYREAD.Module.Book;
import HAPPYREAD.Module.Tip;
import HAPPYREAD.util.MongoBookDB;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;


/**
 * @author unknown
 */
public class DmA extends JFrame {
    private List<Book> books=new ArrayList<>();
    private MongoBookDB mdb;
    private String userpath;
    public DmA() {
        mdb=new MongoBookDB();
        initComponents();
    }

    private void addbookActionPerformed(ActionEvent e) {
        // TODO add your code here
    }

    private void addbookMouseClicked(MouseEvent e) {
        // TODO add your code here

        Book book=new Book(txtTitle.getText(), txtAuthor.getText());
        book.setBrief(txtBrief.getText());
        book.setImageSrc(txtImage.getText());

        mdb.addBook(book);

        txtTitle.setText("");
        txtBrief.setText("");
        txtImage.setText("");
        txtAuthor.setText("");
        txtTitle.requestFocus();
    }

    private void panlBooksComponentShown(ComponentEvent e) {
        // TODO add your code here

//        Book book=null;
//        FindIterable<Document> findIterable=collection.find();
//        MongoCursor<Document> mongoCursor=findIterable.iterator();
//        Gson gson= new GsonBuilder().create();
//        books.clear();
//        while(mongoCursor.hasNext())
//        {
//            String json=mongoCursor.next().toJson();
//            book=gson.fromJson(json,Book.class);
//            books.add(book);
//        }

        txtTitle.requestFocus();

    }

    private void palHappyReadComponentShown(ComponentEvent e) {


    }


    private void addTipsMouseClicked(MouseEvent e) {
        // TODO add your code here



        Tip tip=new Tip(txtTipTitle.getText());
        tip.setTipBody(txtTipBody.getText());
        mdb.addTip(tip);


    }

    private void btnStartMDTMouseClicked(MouseEvent e) {
        // TODO add your code here
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new DmA().setVisible(true);
            }
        });
    }

    private void SelTxtFileMouseClicked(MouseEvent e) {
        // TODO add your code here
        JFileChooser fileChooser=new JFileChooser("d:/");
        fileChooser.setFileFilter(new FileFilter() {
            @Override
            public boolean accept(File f) {
                if (f.isDirectory()) return true;
             return    f.getName().endsWith(".txt");
             //   return false;
            }

            @Override
            public String getDescription() {
                return ".txt";
            }
        });
        int result;
        result=fileChooser.showOpenDialog(this);

        if(result==JFileChooser.APPROVE_OPTION)
        {
            userpath=fileChooser.getSelectedFile().getPath();
            txtfile.setText(userpath);
            txtFileDown.setText(userpath.substring(0,userpath.length()-4));
        }
    }

    private void addTipsActionPerformed(ActionEvent e) {
        // TODO add your code here
    }

    private void UpLoadMouseClicked(MouseEvent e) {
        // TODO add your code here
        if (txtBookUploadtitle.getText().length()!=0 && txtfile.getText().length()!=0) {
//            System.out.println(txtBookUploadtitle.getText()+txtfile.getText());
            mdb.addBookforDownLoad(txtBookUploadtitle.getText(), txtfile.getText());

            System.out.println("dk");
        }
    }

    private void btnDownTxtFileMouseClicked(MouseEvent e) {
        // TODO add your code here
        byte[] txt=mdb.GetBookTxt(txtBookUploadtitle.getText());
       mdb.putFileFromBytes(txt,txtFileDown.getText());
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        panel1 = new JPanel();
        palHappyRead = new JTabbedPane();
        panel5 = new JPanel();
        panel6 = new JPanel();
        panel3 = new JPanel();
        btnStartMDT = new JButton();
        tabbedPane2 = new JTabbedPane();
        TxtDownFile = new JPanel();
        txtTitle = new JTextField();
        txtAuthor = new JTextField();
        txtImage = new JTextField();
        scrollPane1 = new JScrollPane();
        txtBrief = new JEditorPane();
        addbook = new JButton();
        label1 = new JLabel();
        label2 = new JLabel();
        label3 = new JLabel();
        label4 = new JLabel();
        separator1 = new JSeparator();
        txtBookUploadtitle = new JTextField();
        label7 = new JLabel();
        txtfile = new JTextField();
        SelTxtFile = new JButton();
        label8 = new JLabel();
        UpLoad = new JButton();
        btnDownTxtFile = new JButton();
        txtFileDown = new JTextField();
        panel2 = new JPanel();
        txtTipTitle = new JTextField();
        scrollPane2 = new JScrollPane();
        txtTipBody = new JTextArea();
        addTips = new JButton();
        label5 = new JLabel();
        label6 = new JLabel();

        //======== this ========
        Container contentPane = getContentPane();
        contentPane.setLayout(null);

        //======== panel1 ========
        {
            panel1.setLayout(null);

            //======== palHappyRead ========
            {
                palHappyRead.addComponentListener(new ComponentAdapter() {
                    @Override
                    public void componentShown(ComponentEvent e) {
                        palHappyReadComponentShown(e);
                    }
                });
                palHappyRead.addTab("Yper", panel5);
                palHappyRead.addTab("Lizhi", panel6);

                //======== panel3 ========
                {

                    //---- btnStartMDT ----
                    btnStartMDT.setText("Start MDT");
                    btnStartMDT.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseClicked(MouseEvent e) {
                            btnStartMDTMouseClicked(e);
                        }
                    });

                    GroupLayout panel3Layout = new GroupLayout(panel3);
                    panel3.setLayout(panel3Layout);
                    panel3Layout.setHorizontalGroup(
                        panel3Layout.createParallelGroup()
                            .addGroup(GroupLayout.Alignment.TRAILING, panel3Layout.createSequentialGroup()
                                .addContainerGap(594, Short.MAX_VALUE)
                                .addComponent(btnStartMDT, GroupLayout.PREFERRED_SIZE, 111, GroupLayout.PREFERRED_SIZE)
                                .addGap(43, 43, 43))
                    );
                    panel3Layout.setVerticalGroup(
                        panel3Layout.createParallelGroup()
                            .addGroup(panel3Layout.createSequentialGroup()
                                .addGap(14, 14, 14)
                                .addComponent(btnStartMDT)
                                .addContainerGap(441, Short.MAX_VALUE))
                    );
                }
                palHappyRead.addTab("Sanzhong", panel3);

                //======== tabbedPane2 ========
                {

                    //======== TxtDownFile ========
                    {
                        TxtDownFile.addComponentListener(new ComponentAdapter() {
                            @Override
                            public void componentShown(ComponentEvent e) {
                                panlBooksComponentShown(e);
                            }
                        });

                        //======== scrollPane1 ========
                        {
                            scrollPane1.setViewportView(txtBrief);
                        }

                        //---- addbook ----
                        addbook.setText("Add Book");
                        addbook.addActionListener(e -> addbookActionPerformed(e));
                        addbook.addMouseListener(new MouseAdapter() {
                            @Override
                            public void mouseClicked(MouseEvent e) {
                                addbookMouseClicked(e);
                            }
                        });

                        //---- label1 ----
                        label1.setText("Book Title");

                        //---- label2 ----
                        label2.setText("Author");

                        //---- label3 ----
                        label3.setText("ImageSrc");

                        //---- label4 ----
                        label4.setText("Brief");

                        //---- label7 ----
                        label7.setText("BookTitle\uff08UpLoad\uff09");

                        //---- SelTxtFile ----
                        SelTxtFile.setText("SelectTxt File");
                        SelTxtFile.addMouseListener(new MouseAdapter() {
                            @Override
                            public void mouseClicked(MouseEvent e) {
                                SelTxtFileMouseClicked(e);
                            }
                        });

                        //---- label8 ----
                        label8.setText("TxtFile ");

                        //---- UpLoad ----
                        UpLoad.setText("UpLoad");
                        UpLoad.addMouseListener(new MouseAdapter() {
                            @Override
                            public void mouseClicked(MouseEvent e) {
                                UpLoadMouseClicked(e);
                            }
                        });

                        //---- btnDownTxtFile ----
                        btnDownTxtFile.setText("DownLoadTxtFile");
                        btnDownTxtFile.addMouseListener(new MouseAdapter() {
                            @Override
                            public void mouseClicked(MouseEvent e) {
                                btnDownTxtFileMouseClicked(e);
                            }
                        });

                        GroupLayout TxtDownFileLayout = new GroupLayout(TxtDownFile);
                        TxtDownFile.setLayout(TxtDownFileLayout);
                        TxtDownFileLayout.setHorizontalGroup(
                            TxtDownFileLayout.createParallelGroup()
                                .addGroup(GroupLayout.Alignment.TRAILING, TxtDownFileLayout.createSequentialGroup()
                                    .addContainerGap()
                                    .addComponent(separator1))
                                .addGroup(TxtDownFileLayout.createSequentialGroup()
                                    .addGap(22, 22, 22)
                                    .addGroup(TxtDownFileLayout.createParallelGroup()
                                        .addComponent(scrollPane1)
                                        .addGroup(TxtDownFileLayout.createSequentialGroup()
                                            .addGroup(TxtDownFileLayout.createParallelGroup()
                                                .addComponent(txtTitle, GroupLayout.PREFERRED_SIZE, 157, GroupLayout.PREFERRED_SIZE)
                                                .addComponent(label1)
                                                .addComponent(label4))
                                            .addGap(24, 24, 24)
                                            .addGroup(TxtDownFileLayout.createParallelGroup()
                                                .addComponent(txtAuthor, GroupLayout.PREFERRED_SIZE, 173, GroupLayout.PREFERRED_SIZE)
                                                .addComponent(label2))
                                            .addGap(18, 18, 18)
                                            .addGroup(TxtDownFileLayout.createParallelGroup()
                                                .addComponent(label3)
                                                .addComponent(txtImage, GroupLayout.PREFERRED_SIZE, 332, GroupLayout.PREFERRED_SIZE))
                                            .addGap(0, 0, Short.MAX_VALUE))))
                                .addGroup(TxtDownFileLayout.createSequentialGroup()
                                    .addGroup(TxtDownFileLayout.createParallelGroup()
                                        .addGroup(TxtDownFileLayout.createSequentialGroup()
                                            .addGap(23, 23, 23)
                                            .addGroup(TxtDownFileLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                                .addComponent(txtBookUploadtitle, GroupLayout.DEFAULT_SIZE, 149, Short.MAX_VALUE)
                                                .addComponent(label7)
                                                .addComponent(btnDownTxtFile, GroupLayout.DEFAULT_SIZE, 149, Short.MAX_VALUE))
                                            .addGap(18, 18, 18)
                                            .addGroup(TxtDownFileLayout.createParallelGroup()
                                                .addGroup(TxtDownFileLayout.createSequentialGroup()
                                                    .addGroup(TxtDownFileLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                                        .addComponent(txtfile, GroupLayout.DEFAULT_SIZE, 383, Short.MAX_VALUE)
                                                        .addComponent(txtFileDown, GroupLayout.DEFAULT_SIZE, 383, Short.MAX_VALUE))
                                                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                    .addGroup(TxtDownFileLayout.createParallelGroup()
                                                        .addComponent(SelTxtFile, GroupLayout.PREFERRED_SIZE, 141, GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(UpLoad, GroupLayout.PREFERRED_SIZE, 133, GroupLayout.PREFERRED_SIZE)))
                                                .addGroup(TxtDownFileLayout.createSequentialGroup()
                                                    .addComponent(label8)
                                                    .addGap(0, 0, Short.MAX_VALUE))))
                                        .addGroup(GroupLayout.Alignment.TRAILING, TxtDownFileLayout.createSequentialGroup()
                                            .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(addbook, GroupLayout.PREFERRED_SIZE, 141, GroupLayout.PREFERRED_SIZE)))
                                    .addContainerGap())
                        );
                        TxtDownFileLayout.setVerticalGroup(
                            TxtDownFileLayout.createParallelGroup()
                                .addGroup(TxtDownFileLayout.createSequentialGroup()
                                    .addContainerGap()
                                    .addGroup(TxtDownFileLayout.createParallelGroup()
                                        .addComponent(label1)
                                        .addComponent(label2)
                                        .addComponent(label3))
                                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                    .addGroup(TxtDownFileLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(txtTitle, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(txtAuthor, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(txtImage, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(label4)
                                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(scrollPane1, GroupLayout.PREFERRED_SIZE, 111, GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(addbook)
                                    .addGap(18, 18, 18)
                                    .addComponent(separator1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                    .addGroup(TxtDownFileLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(label7)
                                        .addComponent(label8))
                                    .addGap(4, 4, 4)
                                    .addGroup(TxtDownFileLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(txtBookUploadtitle, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(txtfile, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(SelTxtFile))
                                    .addGap(18, 18, 18)
                                    .addGroup(TxtDownFileLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(UpLoad)
                                        .addComponent(btnDownTxtFile)
                                        .addComponent(txtFileDown, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                    .addContainerGap(97, Short.MAX_VALUE))
                        );
                    }
                    tabbedPane2.addTab("Books", TxtDownFile);

                    //======== panel2 ========
                    {

                        //======== scrollPane2 ========
                        {
                            scrollPane2.setViewportView(txtTipBody);
                        }

                        //---- addTips ----
                        addTips.setText("Add Tips");
                        addTips.addActionListener(e -> addTipsActionPerformed(e));
                        addTips.addMouseListener(new MouseAdapter() {
                            @Override
                            public void mouseClicked(MouseEvent e) {
                                addTipsMouseClicked(e);
                            }
                        });

                        //---- label5 ----
                        label5.setText("TipTitle");

                        //---- label6 ----
                        label6.setText("TipBody");

                        GroupLayout panel2Layout = new GroupLayout(panel2);
                        panel2.setLayout(panel2Layout);
                        panel2Layout.setHorizontalGroup(
                            panel2Layout.createParallelGroup()
                                .addGroup(panel2Layout.createSequentialGroup()
                                    .addGap(18, 18, 18)
                                    .addGroup(panel2Layout.createParallelGroup()
                                        .addComponent(scrollPane2, GroupLayout.DEFAULT_SIZE, 722, Short.MAX_VALUE)
                                        .addGroup(panel2Layout.createSequentialGroup()
                                            .addGroup(panel2Layout.createParallelGroup()
                                                .addComponent(label6)
                                                .addComponent(label5)
                                                .addComponent(txtTipTitle, GroupLayout.PREFERRED_SIZE, 495, GroupLayout.PREFERRED_SIZE))
                                            .addGap(0, 227, Short.MAX_VALUE)))
                                    .addContainerGap())
                                .addGroup(GroupLayout.Alignment.TRAILING, panel2Layout.createSequentialGroup()
                                    .addContainerGap(588, Short.MAX_VALUE)
                                    .addComponent(addTips, GroupLayout.PREFERRED_SIZE, 139, GroupLayout.PREFERRED_SIZE)
                                    .addGap(19, 19, 19))
                        );
                        panel2Layout.setVerticalGroup(
                            panel2Layout.createParallelGroup()
                                .addGroup(panel2Layout.createSequentialGroup()
                                    .addContainerGap()
                                    .addComponent(label5)
                                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(txtTipTitle, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                    .addGap(8, 8, 8)
                                    .addComponent(label6)
                                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(scrollPane2, GroupLayout.PREFERRED_SIZE, 330, GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(addTips)
                                    .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        );
                    }
                    tabbedPane2.addTab("Tips", panel2);
                }
                palHappyRead.addTab("HappyRead", tabbedPane2);
            }
            panel1.add(palHappyRead);
            palHappyRead.setBounds(0, 0, 750, 515);

            { // compute preferred size
                Dimension preferredSize = new Dimension();
                for(int i = 0; i < panel1.getComponentCount(); i++) {
                    Rectangle bounds = panel1.getComponent(i).getBounds();
                    preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
                    preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
                }
                Insets insets = panel1.getInsets();
                preferredSize.width += insets.right;
                preferredSize.height += insets.bottom;
                panel1.setMinimumSize(preferredSize);
                panel1.setPreferredSize(preferredSize);
            }
        }
        contentPane.add(panel1);
        panel1.setBounds(new Rectangle(new Point(0, 0), panel1.getPreferredSize()));

        { // compute preferred size
            Dimension preferredSize = new Dimension();
            for(int i = 0; i < contentPane.getComponentCount(); i++) {
                Rectangle bounds = contentPane.getComponent(i).getBounds();
                preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
                preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
            }
            Insets insets = contentPane.getInsets();
            preferredSize.width += insets.right;
            preferredSize.height += insets.bottom;
            contentPane.setMinimumSize(preferredSize);
            contentPane.setPreferredSize(preferredSize);
        }
        pack();
        setLocationRelativeTo(null);
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private JPanel panel1;
    private JTabbedPane palHappyRead;
    private JPanel panel5;
    private JPanel panel6;
    private JPanel panel3;
    private JButton btnStartMDT;
    private JTabbedPane tabbedPane2;
    private JPanel TxtDownFile;
    private JTextField txtTitle;
    private JTextField txtAuthor;
    private JTextField txtImage;
    private JScrollPane scrollPane1;
    private JEditorPane txtBrief;
    private JButton addbook;
    private JLabel label1;
    private JLabel label2;
    private JLabel label3;
    private JLabel label4;
    private JSeparator separator1;
    private JTextField txtBookUploadtitle;
    private JLabel label7;
    private JTextField txtfile;
    private JButton SelTxtFile;
    private JLabel label8;
    private JButton UpLoad;
    private JButton btnDownTxtFile;
    private JTextField txtFileDown;
    private JPanel panel2;
    private JTextField txtTipTitle;
    private JScrollPane scrollPane2;
    private JTextArea txtTipBody;
    private JButton addTips;
    private JLabel label5;
    private JLabel label6;
    // JFormDesigner - End of variables declaration  //GEN-END:variables

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new DmA().setVisible(true);
            }
        });
    }
}
