package tableadvanced;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.text.NumberFormatter;

/**
 * Data panel.
 *
 * @author skorkmaz
 */
public final class DataPanel extends javax.swing.JPanel {

    public static final int PANEL_WIDTH = 350;
    public static final int PANEL_HEIGTH = 25;
    private final Val2XPanel jpVal2X;

    private final TableValue[] prevTableValues;
    private final MyTable myTable;

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(PANEL_WIDTH, PANEL_HEIGTH);
    }

    public DataPanel(final MyTable myTable) {
        initComponents();
        setBounds(0, 0, 300, 25);
        jftfVal1X.setVisible(false);
        Rectangle rect = new Rectangle(100, 0, 200, PANEL_HEIGTH);
        jpVal2X = new Val2XPanel(rect);
        add(jpVal2X);
        this.myTable = myTable;
        prevTableValues = new TableValue[myTable.getMaxRows()];
        jcbValType.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (myTable.isEditing()) { //A row is selected
                    System.out.println("Called cb action.");
                    int iEditingRow = myTable.getEditingRow();
                    myTable.getCellEditor().stopCellEditing(); //to update values in formatted text fields.
                    TableValue tableVal = update();
                    myTable.setValueAt(tableVal, iEditingRow, 0); //triggers renderer
                }
            }
        });
    }

    private TableValue update() {
        int currentIndex = myTable.getEditedDataPanel().jcbValType.getSelectedIndex();
        int prevIndex = myTable.getEditedDataPanel().prevTableValues[myTable.getEditedRow()].getIndexValType();
        TableValue tableVal = new TableValue(currentIndex);
        if (currentIndex != prevIndex) { //we entered update due to a change in cb index
            if (is2XType(currentIndex)) { //index changed from 1X to 2X, get data in 1X, calculate 2X
                calc2XUsing1X(tableVal);
            } else { //index changed from 2X to 1X, get data in 2X, calculate 1X
                calc1XUsing2X(tableVal);
            }
        } else { //we entered update due to intialization or focus lost of edited row, not cb index change
            if (is2XType(currentIndex)) { //was and is 2X type, get data in 2X, calculate 1X
                calc1XUsing2X(tableVal);
            } else { //was and is 1X, get data in 1X, calculate 2X
                calc2XUsing1X(tableVal);
            }
        }        
        return tableVal;
    }

    private void calc2XUsing1X(TableValue tableVal) {
        double val1X = myTable.getEditedDataPanel().getVal1X();
        double val2X = TableValue.calcVal2X(val1X);
        tableVal.setVal1X(val1X);
        tableVal.setVal2X(val2X);
    }

    private void calc1XUsing2X(TableValue tableVal) {
        double val2X = myTable.getEditedDataPanel().getVal2X();
        double val1X = TableValue.calcVal1X(val2X);
        tableVal.setVal1X(val1X);
        tableVal.setVal2X(val2X);
    }

    private boolean is2XType(int valTypeIndex) {
        return valTypeIndex == 1;
    }

    /**
     * Called by renderer and editor.
     *
     * @param tableVal
     * @param iRow
     */
    public void setTableValue(TableValue tableVal, int iRow) {
        if (prevTableValues[iRow] == null) { //initial creation of renderer or editor
            jcbValType.setSelectedIndex(tableVal.getIndexValType()); //triggers combo box action listener
        } else if (tableVal.getIndexValType() != prevTableValues[iRow].getIndexValType()) { //change in cb index
            jcbValType.setSelectedIndex(tableVal.getIndexValType()); //triggers combo box action listener                
        }
        prevTableValues[iRow] = tableVal;
        //update display:
        jlTableRow.setText(String.valueOf(iRow + 1));
        setVal1X(tableVal.getVal1X());
        setVal2X(tableVal.getVal2X());
        jftfVal1X.setVisible(!is2XType(tableVal.getIndexValType()));
        jpVal2X.setVisible(is2XType(tableVal.getIndexValType()));
    }

    public TableValue getTableValue() {
        return new TableValue(getIndex(), is2XType(getIndex()) ? getVal2X() : getVal1X());
    }

    private int getIndex() {
        return jcbValType.getSelectedIndex();
    }

    private void setVal1X(double val1X) {
        NumberFormatter nf = (NumberFormatter) jftfVal1X.getFormatter();
        jftfVal1X.setText(nf.getFormat().format(val1X));
    }

    private double getVal1X() {
        double val = Double.NaN;
        try {
            NumberFormatter nf = (NumberFormatter) jftfVal1X.getFormatter();
            val = ((Number) nf.stringToValue(jftfVal1X.getText())).doubleValue();
        } catch (ParseException ex) {
            Logger.getLogger(Val2XPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return val;
    }

    private void setVal2X(double val2X) {
        jpVal2X.setVal2X(val2X);
    }

    private double getVal2X() {
        return jpVal2X.getVal2X();
    }

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The content of this method is always regenerated by the Form
     * Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jcbValType = new javax.swing.JComboBox();
        jftfVal1X = new javax.swing.JFormattedTextField();
        jlTableRow = new javax.swing.JLabel();

        setLayout(null);

        jcbValType.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "1 X", "2 X" }));
        add(jcbValType);
        jcbValType.setBounds(40, 2, 50, 20);

        jftfVal1X.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.00"))));
        add(jftfVal1X);
        jftfVal1X.setBounds(100, 2, 220, 20);

        jlTableRow.setText("#");
        add(jlTableRow);
        jlTableRow.setBounds(2, 4, 20, 14);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox jcbValType;
    private javax.swing.JFormattedTextField jftfVal1X;
    private javax.swing.JLabel jlTableRow;
    // End of variables declaration//GEN-END:variables

}
