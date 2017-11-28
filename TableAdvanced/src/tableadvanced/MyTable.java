package tableadvanced;

import javax.swing.JTable;
import javax.swing.table.TableModel;

/**
 * Table without header.
 *
 * @author skorkmaz
 */
public class MyTable extends JTable {

    private final int maxRows;

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
