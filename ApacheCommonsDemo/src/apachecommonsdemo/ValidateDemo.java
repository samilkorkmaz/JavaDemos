package apachecommonsdemo;

import org.apache.commons.lang3.Validate;

/**
 * Demo of Validate class.
 *
 * @author skorkmaz
 */
public class ValidateDemo extends javax.swing.JFrame {

    public ValidateDemo() {
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jbCheckNullString = new javax.swing.JButton();
        jbCheckEmptyString = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jtaException = new javax.swing.JTextArea();
        jbCheckInvalidValue = new javax.swing.JButton();
        jbCheckInterval = new javax.swing.JButton();

        setTitle("Validate Demo");
        setAlwaysOnTop(true);
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        jbCheckNullString.setText("Check null string");
        jbCheckNullString.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbCheckNullStringActionPerformed(evt);
            }
        });

        jbCheckEmptyString.setText("Check empty string");
        jbCheckEmptyString.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbCheckEmptyStringActionPerformed(evt);
            }
        });

        jtaException.setColumns(20);
        jtaException.setLineWrap(true);
        jtaException.setRows(5);
        jScrollPane1.setViewportView(jtaException);

        jbCheckInvalidValue.setText("Check invalid value");
        jbCheckInvalidValue.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbCheckInvalidValueActionPerformed(evt);
            }
        });

        jbCheckInterval.setText("Check interval");
        jbCheckInterval.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbCheckIntervalActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jbCheckInvalidValue, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jbCheckNullString, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jbCheckEmptyString, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jbCheckInterval, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(0, 122, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jbCheckNullString)
                    .addComponent(jbCheckEmptyString))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jbCheckInvalidValue)
                    .addComponent(jbCheckInterval))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 139, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jbCheckNullStringActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbCheckNullStringActionPerformed
        try {
            String s = null;
            Validate.notBlank(s);
        } catch (Exception e) {
            jtaException.setText(e.toString());
        }
    }//GEN-LAST:event_jbCheckNullStringActionPerformed

    private void jbCheckEmptyStringActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbCheckEmptyStringActionPerformed
        try {
            String s = "";
            Validate.notBlank(s);
        } catch (Exception e) {
            jtaException.setText(e.toString());
        }
    }//GEN-LAST:event_jbCheckEmptyStringActionPerformed

    private void jbCheckInvalidValueActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbCheckInvalidValueActionPerformed
        try {
            double val = 1;
            Validate.isTrue(val < 0, String.format("Value (%1.1f) must be smaller than zero!", val));
        } catch (Exception e) {
            jtaException.setText(e.toString());
        }
    }//GEN-LAST:event_jbCheckInvalidValueActionPerformed

    private void jbCheckIntervalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbCheckIntervalActionPerformed
        try {
            double minVal = 1;
            double maxVal = 5;
            double val = minVal-0.1;
            Validate.inclusiveBetween(minVal, maxVal, val, String.format("Value (%1.1f) must be in interval [%1.1f, %1.1f]!", val, minVal, maxVal));
        } catch (Exception e) {
            jtaException.setText(e.toString());
        }
    }//GEN-LAST:event_jbCheckIntervalActionPerformed

    static int min(int... values) {
        int minVal = values[0];
        for (int i = 1; i < values.length; i++) {
            if (values[i] < minVal) {
                minVal = values[i];
            }
        }
        return minVal;
    }
    
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
            java.util.logging.Logger.getLogger(ValidateDemo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ValidateDemo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ValidateDemo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ValidateDemo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        int minVal = min(2, -1,3);
        System.out.println("minVal = " + minVal);
        minVal = min(new int[]{20,10,30});
        System.out.println("minVal = " + minVal);
        
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ValidateDemo().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton jbCheckEmptyString;
    private javax.swing.JButton jbCheckInterval;
    private javax.swing.JButton jbCheckInvalidValue;
    private javax.swing.JButton jbCheckNullString;
    private javax.swing.JTextArea jtaException;
    // End of variables declaration//GEN-END:variables
}
