package tableadvanced;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;

/**
 * Demo of table that has a panel containing a combo box that changes the values in formatted text field.</br>
 * Note that a JTable has the following three concepts that you need to understand:<br/>
 * 1. Value: The underlying object that holds the values.<br/>
 * 2. Renderer: The component that draws the cells when they are not edited.<br/>
 * 3. Editor: The component the draws the cell that is currently edited.<br/>
 *
 * @author skorkmaz
 */
public class Driver {

    private static final int MAX_ROW_COUNT = 15;    

    public static void main(String[] args) {
        final JFrame jf = new JFrame("Advanced Table Demo - Şamil Korkmaz, Nov 2017");
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.setLayout(null);

        final MyTableModel tableModel = new MyTableModel();
        MyTable table = new MyTable(tableModel, DataPanel.PANEL_HEIGTH, MAX_ROW_COUNT);
        EditorRenderer editorRenderer = new EditorRenderer(table);
        table.setDefaultRenderer(Object.class, editorRenderer);
        table.setDefaultEditor(Object.class, editorRenderer);
        tableModel.addRow(new TableValue(0, 10)); //0: input val is 1X

        JScrollPane jsp = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        jsp.setBounds(10, 10, DataPanel.PANEL_WIDTH, 10*DataPanel.PANEL_HEIGTH);
        jf.add(jsp);

        JButton jb = new JButton("Add Row");
        jb.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (tableModel.getRowCount() > MAX_ROW_COUNT - 1) {
                    JOptionPane.showMessageDialog(jf, "Cannot add more than " + MAX_ROW_COUNT + " rows!"
                            + "\nTo change max row nb, open Driver.java and change MAX_ROW_COUNT.", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    tableModel.addRow(new TableValue(1, 15.0)); //1: input val is 2X
                }
            }
        });
        jf.add(jb);
        int buttonWidth = 100;
        jb.setBounds((DataPanel.PANEL_WIDTH - buttonWidth) / 2, jsp.getHeight() + 10, 100, 25);

        jf.setPreferredSize(new Dimension(500, 350));
        jf.setLocation(150, 150);
        jf.pack();
        jf.setVisible(true);
    }

}
