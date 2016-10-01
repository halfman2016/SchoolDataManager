package lizhi.util;

import lizhi.Module.Teacher;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;

/**
 * Created by Feng on 2016/7/24.
 */
public class TeaTableModel extends AbstractTableModel {
    ArrayList<Teacher> teas = new ArrayList<>();

    public TeaTableModel(ArrayList<Teacher> teas) {
        this.teas = teas;
    }

    @Override
    public int getRowCount() {
        return teas.size();
    }

    @Override
    public int getColumnCount() {
        return 5;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {

        Teacher tea=teas.get(rowIndex);
        String result = "";



        switch (columnIndex)
        {
            case 0:
                result=tea.getName();
                break;
            case 1:
                result=tea.getPwd();
                break;
            case 2:
                result=tea.getPhoneNum();
                break;
            case 3:
                result=tea.getEmail();
                break;
            case 4:
                result=tea.getTid();
                break;
            default:
                result="";
        }

        return result;
    }
}
