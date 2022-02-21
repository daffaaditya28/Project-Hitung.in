/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */

/**
 *
 * @author Windows 10
 */
public class Setting extends javax.swing.JFrame {

    /**
     * Creates new form Setting
     */
    public Setting( String username) {
        initComponents();
        setNama(username);
        this.setResizable(false);
    }
    public void setNama(String user){
    username1.setText(user);
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton1 = new javax.swing.JButton();
        btn_profile = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jButton1.setContentAreaFilled(false);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 290, 160, 30));

        username1.setFont(new java.awt.Font("Segoe UI", 1, 30)); // NOI18N
        username1.setForeground(new java.awt.Color(255, 255, 255));
        username1.setText("jLabel2");
        getContentPane().add(username1, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 190, -1, -1));

        btn_profile.setContentAreaFilled(false);
        btn_profile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_profileActionPerformed(evt);
            }
        });
        getContentPane().add(btn_profile, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 350, 150, 40));

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img12/Setting.png"))); // NOI18N
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 840, -1));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        this.setVisible(false);
        new MainMenu(username1.getText()).setVisible(true);
        
    }//GEN-LAST:event_jButton1ActionPerformed

    private void btn_profileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_profileActionPerformed
        // TODO add your handling code here:
        this.setVisible(false);
        new SettingProfile(username1.getText()).setVisible(true);
    }//GEN-LAST:event_btn_profileActionPerformed

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
            java.util.logging.Logger.getLogger(Setting.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Setting.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Setting.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Setting.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Setting(username1.getText()).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_profile;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel2;
    public static final javax.swing.JLabel username1 = new javax.swing.JLabel();
    // End of variables declaration//GEN-END:variables
}