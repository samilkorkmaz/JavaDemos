
package apachecommonsdemo;

import org.apache.commons.lang3.ArrayUtils;

/**
 *
 * @author skorkmaz
 */
public class ArrayUtilsDemo extends javax.swing.JFrame {
    
    private static final double DOUBLE_ARRAY[] = {10.1, 30.3, 40.4};
    private static final double VAL_TO_FIND = 30.33;
    private static final double TOLERANCE = 0.1;

    /**
     * Creates new form ArrayUtilsDemo
     */
    public ArrayUtilsDemo() {
        initComponents();        
        jlDoubleArray.setText("double array = " + ArrayUtils.toString(DOUBLE_ARRAY));
        jlValueToLookFor.setText(String.format("value: %1.2f", VAL_TO_FIND));
        jlTolerance.setText(String.format("tolerance = %1.1f", TOLERANCE));
        jlContains.setText("");
    }

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The content of this method is always regenerated by the
     * Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jlDoubleArray = new javax.swing.JLabel();
        jlValueToLookFor = new javax.swing.JLabel();
        jlTolerance = new javax.swing.JLabel();
        jbContains = new javax.swing.JButton();
        jlContains = new javax.swing.JLabel();

        setTitle("ArrayUtils Demo");

        jlDoubleArray.setText("jLabel1");

        jlValueToLookFor.setText("jLabel1");

        jlTolerance.setText("jLabel1");

        jbContains.setText("--> Contains -->");
        jbContains.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbContainsActionPerformed(evt);
            }
        });

        jlContains.setText("jLabel1");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jlTolerance)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jlDoubleArray)
                            .addComponent(jlValueToLookFor))
                        .addGap(27, 27, 27)
                        .addComponent(jbContains)
                        .addGap(18, 18, 18)
                        .addComponent(jlContains)))
                .addContainerGap(164, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jlDoubleArray)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jlValueToLookFor))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jbContains)
                        .addComponent(jlContains)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jlTolerance)
                .addContainerGap(227, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jbContainsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbContainsActionPerformed
        jlContains.setText(String.valueOf(ArrayUtils.contains(DOUBLE_ARRAY, VAL_TO_FIND, TOLERANCE)));
    }//GEN-LAST:event_jbContainsActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ArrayUtilsDemo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ArrayUtilsDemo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ArrayUtilsDemo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ArrayUtilsDemo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ArrayUtilsDemo().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jbContains;
    private javax.swing.JLabel jlContains;
    private javax.swing.JLabel jlDoubleArray;
    private javax.swing.JLabel jlTolerance;
    private javax.swing.JLabel jlValueToLookFor;
    // End of variables declaration//GEN-END:variables
}
