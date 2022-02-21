import java.sql.Connection;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */

/**
 *
 * @author Windows 10
 */
public class penjTes extends javax.swing.JFrame {
    int total;
    /**
     * Creates new form penjTes
     */
    public penjTes() {
        initComponents();
        autoNumber();
        loadTableBrg();
        jTable1.setModel(model);
        
    }
    public void Bayar(){
        int totalKabeh = 0;
        int kembalian = 0;
        int bayar = 0;
        
        bayar = Integer.parseInt(txt_uang.getText());
        for (int i = 0; i<model.getRowCount(); i++){
            totalKabeh += Integer.parseInt(String.valueOf(jTable1.getValueAt(i, 4)));
          }
        
        if(totalKabeh<=bayar){
            kembalian = bayar - totalKabeh;
            txt_kembali.setText(String.valueOf(kembalian));
        } else{
            JOptionPane.showMessageDialog(this, "DUEK KURANG SU , ORA ONO KASBON ");
        }
    }
    
    public void InsertPenjualan(){
        int totalKabeh = 0;
        int totalBarang = 0;
        
        for (int i = 0; i<model.getRowCount(); i++){
            totalBarang += Integer.parseInt(String.valueOf(jTable1.getValueAt(i, 3)));
            totalKabeh += Integer.parseInt(String.valueOf(jTable1.getValueAt(i, 4)));
          }
            txt_tampil.setText(String.valueOf(totalKabeh));
            String sql1="INSERT INTO penjualan VALUES ('"+txt_idTrans.getText()+"','"+totalKabeh+"','"+totalBarang+"', now());";
            System.out.println(sql1);
            try{
                java.sql.Connection conn = (Connection) kon12.configDB();
                java.sql.PreparedStatement pst = conn.prepareStatement(sql1);
                pst.execute();
            
            }catch(Exception e){
                JOptionPane.showMessageDialog(null, e.getMessage());
            }
    
    }
    
    public void InsertDetail(){
         for (int i = 0; i<model.getRowCount(); i++){
         String hrgBrg = String.valueOf(jTable1.getValueAt(i, 2));
         String jmlahBrg = String.valueOf(jTable1.getValueAt(i, 3));
         String totalHrg = String.valueOf(jTable1.getValueAt(i, 4));
         String idBrg = String.valueOf(jTable1.getValueAt(i, 1));
         //System.out.println("harga barang : "+hrgBrg+" jumlah yang dibeli: "+jmlahBrg+" Total: "+ totalHrg);
         String sql = "INSERT INTO detailpenjualan VALUES('"+txt_idTrans.getText()+"','"+idBrg+"','"+jmlahBrg+"','"+hrgBrg+"','"+totalHrg+"')";
         System.out.println(sql);
         try{
             java.sql.Connection conn = (Connection) kon12.configDB();
             java.sql.PreparedStatement pst1 = conn.prepareStatement(sql);
             pst1.execute();
         
         }catch(Exception e){
             JOptionPane.showMessageDialog(null, e.getMessage());
         
         }
         
         }
    }
    
    public void tambahData(){
         int harga = 0;
         int brg = 0;
         
         String sql23 ="SELECT * FROM barang WHERE idBarang ='"+txt_idBrg.getText()+"';";
         int totalStock;
         harga = Integer.parseInt(txt_hrg.getText());
         brg = Integer.parseInt(txt_jml.getText());
         total = harga * brg;
         try{
            java.sql.Connection conn = (Connection) kon12.configDB();
            java.sql.Statement stm = conn.createStatement();
            java.sql.ResultSet res = stm.executeQuery(sql23);
            if(res.next()){
                totalStock = Integer.parseInt(res.getString(4));
                if(brg<totalStock){
                    model.addRow(new Object[]{
                    
                        txt_idTrans.getText(),txt_idBrg.getText(),txt_hrg.getText(),txt_jml.getText(),total
        
                    });
                        jTable1.setModel(model);
                    
                } else{
                    
                    JOptionPane.showMessageDialog(this, "BARANG ORA CUKUP SU");
                    JOptionPane.showMessageDialog(this, "INDONESIA DARURAT MEMBACA, WES METU AE TEKAN APK NGENTOD");
                    System.exit(0);
                }
                
            }
         
         }catch(Exception e){
         
        }
         
    }
    
    public void hapusColumn(){
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        
        if(jTable1.getSelectedRowCount()==1){
            model.removeRow(jTable1.getSelectedRow());
            
        } else if(jTable1.getSelectedRowCount()==0){
            JOptionPane.showMessageDialog(this, "Pilih salah satu column");
            
        } else if(jTable1.getSelectedRow()>2){
            JOptionPane.showMessageDialog(this, "Pilih salah satu column tidak boleh double");
        }
    }
    
    public void resetTextField(){
        txt_tampil.setText("");
        model.setRowCount(0);
        txt_idBrg.setText("");
        txt_hrg.setText("");
        txt_jml.setText("");
    
    }
    
    public void loadTableBrg(){
        DefaultTableModel model = new DefaultTableModel();
         model.addColumn("id Barang");
         model.addColumn("Nama Barang");
         model.addColumn("Harga Barang");
         model.addColumn("Stok Barang");
         
        String sql23 ="SELECT * FROM barang;";
        try{
            java.sql.Connection conn = (Connection) kon12.configDB();
            java.sql.Statement stm = conn.createStatement();
            java.sql.ResultSet res = stm.executeQuery(sql23);
            while(res.next()){
            model.addRow(new Object[]{res.getString(1),res.getString(2),res.getString(3),res.getString(4)});
            }
        jTable2.setModel(model);
        
        }catch(Exception e){
        
        }
    }
    
    public void cariData(String key){
        DefaultTableModel model = new DefaultTableModel();
         model.addColumn("id Barang");
         model.addColumn("Nama Barang");
         model.addColumn("Harga Barang");
         model.addColumn("Stok Barang");
         
        String sql23 ="SELECT * FROM barang WHERE namaBarang LIKE '%"+key+"%';";
        
        try{
            java.sql.Connection conn = (Connection) kon12.configDB();
            java.sql.Statement stm = conn.createStatement();
            java.sql.ResultSet res = stm.executeQuery(sql23);
            while(res.next()){
            model.addRow(new Object[]{res.getString(1),res.getString(2),res.getString(3),res.getString(4)});
            }
        jTable2.setModel(model);
        
        }catch(Exception e){
        
        }
    }
    
    public void autoNumber(){
    
        String autoNumber = "SELECT * FROM penjualan ORDER BY idTrans DESC";
    
    try{
        java.sql.Connection conn = (Connection) kon12.configDB();
        java.sql.Statement pst = conn.createStatement();
        java.sql.ResultSet res = pst.executeQuery(autoNumber);
        
        if(res.next()){
            String id = res.getString(1).substring(2);
            String TR = ""+(Integer.parseInt(id)+1);
            String nol = "";
            
            if(TR.length()==1){
            nol = "000";
            }
            else if(TR.length()==2){
            nol = "00";
            }
            else if(TR.length()==3){
            nol = "0";
            }
            else if(TR.length()==4){
            nol = "";
            }
            
            txt_idTrans.setText("TR"+nol+TR);
        } else{
            
            txt_idTrans.setText("TR0001");
        }
    }catch(Exception e){
    
    }
    }
    
    public void EditContentTable(){
    jTable1.getModel();
    
    if(jTable1.getSelectedRow()==-1){
        if(jTable1.getRowCount()==0){
            JOptionPane.showMessageDialog(null, "Tidak ada Data Dalam Table");
        } else {
            JOptionPane.showMessageDialog(null, "pilih data yang akan diubah");
        }
    }else{
        model.setValueAt(txt_jml.getText(), jTable1.getSelectedRow(), 3);
    }
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        txt_hrg = new javax.swing.JTextField();
        txt_jml = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        btn_tambahData = new javax.swing.JButton();
        btn_hps = new javax.swing.JButton();
        txt_tampil = new javax.swing.JTextField();
        Btn_Hitung = new javax.swing.JButton();
        btn_reset = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txt_idBrg = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        txt_cariData = new javax.swing.JTextField();
        txt_idTrans = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txt_uang = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        txt_kembali = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        btn_bayar = new javax.swing.JButton();
        txt_namaBarang = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        Btn_editData = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        txt_hrg.setEditable(false);
        getContentPane().add(txt_hrg, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 160, 270, -1));
        getContentPane().add(txt_jml, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 190, 270, -1));

        jTable1.setBackground(new java.awt.Color(255, 255, 255));
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 330, 550, 168));

        btn_tambahData.setText("TAMBAH");
        btn_tambahData.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_tambahDataActionPerformed(evt);
            }
        });
        getContentPane().add(btn_tambahData, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 240, -1, -1));

        btn_hps.setText("HAPUS");
        btn_hps.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_hpsActionPerformed(evt);
            }
        });
        getContentPane().add(btn_hps, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 240, 80, -1));

        txt_tampil.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        txt_tampil.setEnabled(false);
        getContentPane().add(txt_tampil, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 550, 224, 40));

        Btn_Hitung.setText("HITUNG");
        Btn_Hitung.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Btn_HitungActionPerformed(evt);
            }
        });
        getContentPane().add(Btn_Hitung, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 520, -1, -1));

        btn_reset.setText("RESET");
        btn_reset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_resetActionPerformed(evt);
            }
        });
        getContentPane().add(btn_reset, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 560, -1, -1));

        jLabel1.setText("Jumlah Barang");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 190, 90, 20));

        jLabel2.setText("Harga");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 160, 40, 20));

        txt_idBrg.setEditable(false);
        getContentPane().add(txt_idBrg, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 130, 270, -1));

        jTable2.setBackground(new java.awt.Color(255, 255, 255));
        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jTable2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable2MouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(jTable2);

        getContentPane().add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 120, 290, 140));

        txt_cariData.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_cariDataKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_cariDataKeyTyped(evt);
            }
        });
        getContentPane().add(txt_cariData, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 80, 190, 30));

        txt_idTrans.setEditable(false);
        txt_idTrans.setBackground(new java.awt.Color(255, 255, 255));
        getContentPane().add(txt_idTrans, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 100, 90, -1));

        jLabel3.setText("ID Barang");
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 130, -1, -1));

        jLabel4.setText("Id Trans");
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 100, -1, -1));

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel5.setText("TABLE TRANSAKSI");
        getContentPane().add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 300, 140, 20));

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jLabel6.setText("KASIR NGEBUG");
        getContentPane().add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 10, -1, -1));

        jLabel7.setText("UANG PEMBAYARAN");
        getContentPane().add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 520, 130, 20));

        jLabel8.setText("INPUT BARANG");
        getContentPane().add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 70, 90, 20));
        getContentPane().add(txt_uang, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 540, 150, 30));

        jLabel9.setText("CARI BARANG");
        getContentPane().add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 80, 90, 30));
        getContentPane().add(txt_kembali, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 540, 150, 30));

        jLabel10.setText("KEMBALIAN");
        getContentPane().add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 520, 130, 20));

        btn_bayar.setText("BAYAR");
        btn_bayar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_bayarActionPerformed(evt);
            }
        });
        getContentPane().add(btn_bayar, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 580, 150, 30));
        getContentPane().add(txt_namaBarang, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 270, 190, 30));

        jLabel11.setText("NAMA BARANG");
        getContentPane().add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 270, 90, 30));

        jLabel12.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel12.setText("TOTAL BAYAR :");
        getContentPane().add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 520, -1, -1));

        Btn_editData.setText("EDIT");
        Btn_editData.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Btn_editDataActionPerformed(evt);
            }
        });
        getContentPane().add(Btn_editData, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 240, -1, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents
   
    private void btn_tambahDataActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_tambahDataActionPerformed
        // TODO add your handling code here:
        tambahData();
         
    }//GEN-LAST:event_btn_tambahDataActionPerformed

    private void btn_hpsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_hpsActionPerformed
        // TODO add your handling code here:
        hapusColumn();
        
    }//GEN-LAST:event_btn_hpsActionPerformed

    private void Btn_HitungActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Btn_HitungActionPerformed
        // TODO add your handling code here:
        InsertPenjualan();
        InsertDetail();
        JOptionPane.showMessageDialog(null, "DATA BERHASIL TERSIMPAN");
        loadTableBrg();
        
        
    }//GEN-LAST:event_Btn_HitungActionPerformed

    private void btn_resetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_resetActionPerformed
        // TODO add your handling code here:
        resetTextField();
        autoNumber();
    }//GEN-LAST:event_btn_resetActionPerformed

    private void txt_cariDataKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_cariDataKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_cariDataKeyTyped

    private void txt_cariDataKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_cariDataKeyReleased
        // TODO add your handling code here:
        String key=txt_cariData.getText(); 
        if(key!=""){
            cariData(key);
        }else{
            loadTableBrg();
        }
    }//GEN-LAST:event_txt_cariDataKeyReleased

    private void jTable2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable2MouseClicked
        // TODO add your handling code here:
        int barisan = jTable2.rowAtPoint(evt.getPoint());
        
        if(jTable2.getValueAt(barisan, 0)==null){
            txt_idBrg.setText("");
            
        }else {
            txt_idBrg.setText(jTable2.getValueAt(barisan, 0).toString());
        }
        if(jTable2.getValueAt(barisan, 1)==null){
            txt_namaBarang.setText("");
            
        }else {
            txt_namaBarang.setText(jTable2.getValueAt(barisan, 1).toString());
        }
        
        if(jTable2.getValueAt(barisan, 2)==null){
            txt_hrg.setText("");
        }else {
            txt_hrg.setText(jTable2.getValueAt(barisan, 2).toString());
        }
         if(jTable2.getValueAt(barisan, 3)==null){
            txt_jml.setText("");
        }else {
            txt_jml.setText(jTable2.getValueAt(barisan, 3).toString());
        }
    }//GEN-LAST:event_jTable2MouseClicked

    private void btn_bayarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_bayarActionPerformed
        // TODO add your handling code here:
        Bayar();
    }//GEN-LAST:event_btn_bayarActionPerformed

    private void Btn_editDataActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Btn_editDataActionPerformed
        // TODO add your handling code here:
        EditContentTable();
    }//GEN-LAST:event_Btn_editDataActionPerformed

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
            java.util.logging.Logger.getLogger(penjTes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(penjTes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(penjTes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(penjTes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new penjTes().setVisible(true);
            }
        });
    }
    DefaultTableModel model2 = new DefaultTableModel();
    int baris = 0;
    static Object kolom[]= {"id Transaksi","idBarang","Harga","Jumlah Barang","Grand Total"};
    DefaultTableModel model = new DefaultTableModel(kolom,baris);
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Btn_Hitung;
    private javax.swing.JButton Btn_editData;
    private javax.swing.JButton btn_bayar;
    private javax.swing.JButton btn_hps;
    private javax.swing.JButton btn_reset;
    private javax.swing.JButton btn_tambahData;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTextField txt_cariData;
    private javax.swing.JTextField txt_hrg;
    private javax.swing.JTextField txt_idBrg;
    private javax.swing.JTextField txt_idTrans;
    private javax.swing.JTextField txt_jml;
    private javax.swing.JTextField txt_kembali;
    private javax.swing.JTextField txt_namaBarang;
    private javax.swing.JTextField txt_tampil;
    private javax.swing.JTextField txt_uang;
    // End of variables declaration//GEN-END:variables
}
