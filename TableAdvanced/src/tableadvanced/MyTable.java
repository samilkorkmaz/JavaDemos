package tableadvanced;

import javax.swing.JTable;
import javax.swing.table.TableModel;

/**
 * Table without header.
 *
 * @author skorkmaz
 */
public class MyTable extends JTable {

    private DataPanel[] editors;
    private final int maxRows;
    private int editedRow = -1;

    public void setEditors(DataPanel[] editors) {
        this.editors = editors;
    }
    
    public int getEditedRow() {
        return editedRow;
    }

    public void setEditedRow(int editedRow) {
        this.editedRow = editedRow;
    }
    
    public DataPanel getEditedDataPanel() {
        return editors[editedRow];
    }

    public int getMaxRows() {
        return maxRows;
    }

    public MyTable(TableModel dm, int rowHeight, int maxRows) {
        super(dm);
        setRowHeight(rowHeight);
        setTableHeader(null);
        this.maxRows = maxRows;
    }

}
