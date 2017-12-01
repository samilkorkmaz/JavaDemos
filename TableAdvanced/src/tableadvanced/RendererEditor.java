package tableadvanced;

import java.awt.Component;
import java.util.EventObject;
import javax.swing.AbstractCellEditor;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

/**
 * Renderers and editors for JTable.
 *
 * @author skorkmaz
 */
public class RendererEditor extends AbstractCellEditor implements TableCellRenderer, TableCellEditor {

    private final DataPanel[] renderers;
    private final DataPanel[] editors;
    private int lastEditedRow = -1;
    private final MyTable myTable;

    public RendererEditor(MyTable myTable) {
        this.myTable = myTable;
        renderers = new DataPanel[myTable.getMaxRows()];
        editors = new DataPanel[myTable.getMaxRows()];
        for (int i = 0; i < myTable.getMaxRows(); i++) {
            renderers[i] = new DataPanel(myTable);
            editors[i] = new DataPanel(myTable);
        }
        myTable.setEditors(editors);
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        TableValue tv = (TableValue) value;
        System.out.println("row = " + row + ", renderer table val: " + tv);
        renderers[row].setTableValue(tv, row);
        return renderers[row];
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        TableValue tv = (TableValue) value;
        System.out.println("row = " + row + ", editor table val: " + tv);
        lastEditedRow = row;
        myTable.setEditedRow(row);
        editors[row].setTableValue(tv, row); //sets values of current cell with new value. If an invalid operation occurs, the invalid value will be the same as the last edited valid value.
        return editors[row];
    }

    @Override
    public Object getCellEditorValue() {
        return editors[lastEditedRow].getTableValue(); //returns value of last edited cell
    }

    @Override
    public boolean isCellEditable(EventObject anEvent) {
        return true;
    }

    @Override
    public boolean shouldSelectCell(EventObject anEvent) {
        return false;
    }
}
