package tableadvanced;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
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

    TableValue[] prevComps;
    MyTable myTable;

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
        prevComps = new TableValue[myTable.getMaxRows()];
        addCBAction(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (myTable != null && myTable.isEditing()) { //UI creation has finished && a row is selected
                    System.out.println("Called cb action.");
                    int iEditingRow = myTable.getEditingRow();
                    myTable.getCellEditor().stopCellEditing(); //to be able to update values in formatted text fields.
                    TableValue tableVal = update(jcb.getSelectedIndex());
                    myTable.setValueAt(tableVal, iEditingRow, 0);
                }
            }
        });
    }

    private TableValue update(int changedIndex) {
        TableValue tv = new TableValue(changedIndex);
        if (changedIndex == 1) { //changed from 0 to 1, get data in 1X, calculate 2X
            tv.setVal1X(getVal1X());
            setVal2X(tv.getVal2X());
        } else { //changed from 1 to 0, get data in 2X, calculate 1X
            tv.setVal2X(getVal2X());
            setVal1X(tv.getVal1X());
        }
        return tv;
    }

    public void setTableValue(TableValue tableVal, int iRow) {
        if (!tableVal.isEqual(prevComps[iRow])) { //only update UI when value has changed to prevent action listeners from firing unnecessarily
            setTableRow(iRow);
            if (isIndexValTypeChanged(tableVal, prevComps[iRow])) {
                setIndex(tableVal.getIndexValType()); //triggers combo box action listener                
            }
            setVal1X(tableVal.getVal1X()); //update display (editor and renderer)
            setVal2X(tableVal.getVal2X()); //update display (editor and renderer)
            prevComps[iRow] = tableVal;
        }
    }

    public TableValue getTableValue() {
        return new TableValue(getIndex(), getIndex() == 0 ? getVal1X() : getVal2X());
    }

    /**
     * Return true when this is the first time or when IndexValType of current row has changed.
     *
     * @param tableVal
     * @param prevTableVal
     * @return
     */
    private boolean isIndexValTypeChanged(TableValue tableVal, TableValue prevTableVal) {
        return prevTableVal == null || tableVal.getIndexValType() != prevTableVal.getIndexValType();
    }

    void setTableRow(int iRow) {
        jlTableRow.setText(String.valueOf(iRow + 1));
    }

    public void setVal1X(double val1X) {
        NumberFormatter nf = (NumberFormatter) jftfVal1X.getFormatter();
        jftfVal1X.setText(nf.getFormat().format(val1X));
    }

    public void setIndex(int index) {
        jcb.setSelectedIndex(index);
        jftfVal1X.setVisible(index == 0);
        jpVal2X.setVisible(index != 0);
    }

    public int getIndex() {
        return jcb.getSelectedIndex();
    }

    public void addCBAction(ActionListener al) {
        jcb.addActionListener(al);
    }

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The content of this method is always regenerated by the Form
     * Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jcb = new javax.swing.JComboBox();
        jftfVal1X = new javax.swing.JFormattedTextField();
        jlTableRow = new javax.swing.JLabel();

        setLayout(null);

        jcb.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "1 X", "2 X" }));
        add(jcb);
        jcb.setBounds(40, 2, 50, 20);

        jftfVal1X.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.00"))));
        add(jftfVal1X);
        jftfVal1X.setBounds(100, 2, 220, 20);

        jlTableRow.setText("#");
        add(jlTableRow);
        jlTableRow.setBounds(2, 4, 20, 14);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox jcb;
    private javax.swing.JFormattedTextField jftfVal1X;
    private javax.swing.JLabel jlTableRow;
    // End of variables declaration//GEN-END:variables

    boolean isVal1X() {
        return jcb.getSelectedIndex() == 0;
    }

    void setVal2X(double val2X) {
        jpVal2X.setVal2X(val2X);
    }

    double getVal1X() {
        double val = Double.NaN;
        try {            
            NumberFormatter nf = (NumberFormatter) jftfVal1X.getFormatter();
            val = ((Number)nf.stringToValue(jftfVal1X.getText())).doubleValue();
        } catch (ParseException ex) {
            Logger.getLogger(Val2XPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return val;
    }

    double getVal2X() {
        return jpVal2X.getVal2X();
    }

}