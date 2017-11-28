package tableadvanced;

import java.awt.Component;
import java.util.EventObject;
import javax.swing.AbstractCellEditor;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

/**
 * Editor and renderer for table.
 *
 * @author skorkmaz
 */
public class EditorRenderer extends AbstractCellEditor implements TableCellRenderer, TableCellEditor {

    public DataPanel[] renderer;
    public DataPanel[] editor;
    int lastEditedRow = -1;

    public EditorRenderer(MyTable mt) {
        renderer = new DataPanel[mt.getMaxRows()];
        editor = new DataPanel[mt.getMaxRows()];
        for (int i = 0; i < mt.getMaxRows(); i++) {
            renderer[i] = new DataPanel(mt);
            editor[i] = new DataPanel(mt);
        }
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        TableValue tv = (TableValue) value;
        System.out.println("row = " + row + ", renderer table val: " + tv);
        renderer[row].setTableValue(tv, row);
        return renderer[row];
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        TableValue tv = (TableValue) value;
        System.out.println("row = " + row + ", editor table val: " + tv);
        lastEditedRow = row;
        editor[row].setTableValue(tv, row); //sets values of current cell with new value. If an invalid operation occurs, the invalid value will be the same as the last edited valid value.
        return editor[row];
    }

    @Override
    public Object getCellEditorValue() {
        return editor[lastEditedRow].getTableValue(); //returns value of last edited cell
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
