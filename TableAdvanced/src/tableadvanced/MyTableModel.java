package tableadvanced;

import javax.swing.table.DefaultTableModel;

/**
 *
 * @author skorkmaz
 */
public class MyTableModel extends DefaultTableModel {
    
    @Override
    public int getColumnCount() {
        return 1;
    }
    
    public void addRow(TableValue tableVal) {
        super.addRow(new Object[]{tableVal});
    }
    
}
