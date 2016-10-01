package lizhi.util;

import lizhi.Module.Student;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;

/**
 * Created by Feng on 2016/7/23.
 */
public class StuTableModel  extends AbstractTableModel{
    ArrayList<Student> students=new ArrayList<>();
    public StuTableModel(ArrayList<Student> students) {
        this.students=students;
    }

    @Override

    public int getRowCount() {
        return students.size();
    }

    @Override
    public int getColumnCount() {
        return 4;
    }

    public Student getRowStudent(int rowIndex){

        return students.get(rowIndex);

    }
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Student student;
        String result;

        student=students.get(rowIndex);

        switch (columnIndex)
        {
            case 0:
                result=student.getGradeclass();
                break;
            case 1:
                result=student.getName();
                break;
            case 2:
                result=student.getPwd();
                break;

            case 3:


                result=student.getRankname();
                break;
            case 4:
                result=String.valueOf(student.getScore());
                break;
            default:
                result="";
        }

        return result;
    }

    @Override
    public String getColumnName(int column) {
        switch (column)
        {
            case 0:
                return "班级";
            case 1:
                return "姓名";
            case 2:
                return "密码";
            case 3:
                return "级别";
            case 4:
                return "积分";
        }
        return super.getColumnName(column);
    }

}
