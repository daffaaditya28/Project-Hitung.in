
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
public class Kasir extends javax.swing.JFrame {
    int total;
    
    /**
     * Creates new form Kasir
     */
    public Kasir(String username) {
        initComponents();
        setNama(username);
        autoNumber();
        loadTableBrg();
        
        jTable1.setModel(model);
        this.setResizable(false);
    }
    public void todKabeh(){
        int totalKabeh =0;
        for (int i = 0; i<model.getRowCount(); i++){
            totalKabeh += Integer.parseInt(String.valueOf(jTable1.getValueAt(i, 5)));
        }
            txt_tampil.setText(String.valueOf("Rp. "+totalKabeh+".00"));
    }
    
    public void Bayar(){
        int totalKabeh = 0;
        int kembalian = 0;
        int bayar = 0;
        
        bayar = Integer.parseInt(txt_uang.getText());
        for (int i = 0; i<model.getRowCount(); i++){
            totalKabeh += Integer.parseInt(String.valueOf(jTable1.getValueAt(i, 5)));
          }
        
        if(totalKabeh<=bayar){
            kembalian = bayar - totalKabeh;
            txt_kembali.setText(String.valueOf("Rp. "+kembalian+".00"));
        } else{
            JOptionPane.showMessageDialog(this, "DUEK KURANG SU , ORA ONO KASBON ");
        }
    }
    
    public void InsertPenjualan(){
        int totalKabeh = 0;
        int totalBarang = 0;
        
        for (int i = 0; i<model.getRowCount(); i++){
            totalBarang += Integer.parseInt(String.valueOf(jTable1.getValueAt(i, 4)));
            totalKabeh += Integer.parseInt(String.valueOf(jTable1.getValueAt(i, 5)));
          }
            //txt_tampil.setText(String.valueOf("Rp. "+totalKabeh+".00"));
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
         String hrgBrg = String.valueOf(jTable1.getValueAt(i, 3));
         String jmlahBrg = String.valueOf(jTable1.getValueAt(i, 4));
         String totalHrg = String.valueOf(jTable1.getValueAt(i, 5));
         String idBrg = String.valueOf(jTable1.getValueAt(i, 1));
         //System.out.println("harga barang : "+hrgBrg+" jumlah yang dibeli: "+jmlahBrg+" Total: "+ totalHrg);
         String sql = "INSERT INTO detailpenjualan VALUES ('"+txt_idTrans.getText()+"','"+idBrg+"','"+jmlahBrg+"','"+hrgBrg+"','"+totalHrg+"')";
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
         int totalKabeh = 0;
         int jmlahBrg = 0;
         int jmlahBrg2 = 0;
         int totalStock;
         
         String sql23 ="SELECT * FROM barang WHERE idBarang ='"+txt_idBrg.getText()+"';";
         
         harga = Integer.parseInt(txt_hrg.getText());
         brg = Integer.parseInt(txt_jml.getText());
         total = harga * brg;
         
         for (int i = 0; i<model.getRowCount(); i++){
             
             if(txt_idBrg.getText().equals(String.valueOf(jTable1.getValueAt(i, 1)))){
                 
                jmlahBrg += Integer.parseInt(String.valueOf(jTable1.getValueAt(i, 4)));
             }
             
         }
          jmlahBrg2 = jmlahBrg + brg;
          System.out.println(jmlahBrg2);
         try{
            java.sql.Connection conn = (Connection) kon12.configDB();
            java.sql.Statement stm = conn.createStatement();
            java.sql.ResultSet res = stm.executeQuery(sql23);
            if(res.next()){
                totalStock = Integer.parseInt(res.getString(4));
                
                if(totalStock==0){
                    JOptionPane.showMessageDialog(this, "BARANG SUDAH HABIS BOSKUHHH , CEPAT ISI");
                    confirm();
                    
                }else if(txt_jml.getText().toString().equals("0")){
                    JOptionPane.showMessageDialog(this, "INPUT YANG BENER YA HEHEHE ASU");
                
                }else if(jmlahBrg2 >totalStock){
                   JOptionPane.showMessageDialog(this, "Barang Kurang");
                
                
                }else if(brg<=totalStock ){
                    
                    model.addRow(new Object[]{
                    
                        txt_idTrans.getText(),txt_idBrg.getText(),txt_namaBarang.getText(),txt_hrg.getText(),txt_jml.getText(),total
        
                    });
                        jTable1.setModel(model);
                        todKabeh();
                
                } else{
                    
                    JOptionPane.showMessageDialog(this, "BARANG ORA CUKUP SU");
                    JOptionPane.showMessageDialog(this, "INDONESIA DARURAT MEMBACA, NGENTOD");
                      for(int x = 0; x<5; x++){
                          JOptionPane.showMessageDialog(this, "ASU");
                      }
                    
                }
                
            }
         
         }catch(Exception e){
         
        }
         
    }
    
    public void hapusColumn(){
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        
        if(jTable1.getSelectedRowCount()==1){
            model.removeRow(jTable1.getSelectedRow());
            todKabeh();
            
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
        txt_namaBarang.setText("");
        txt_uang.setText("");
        txt_kembali.setText("");
    
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
    int harga = 0;
    int brg = 0;
         
         
         harga = Integer.parseInt(txt_hrg.getText());
         brg = Integer.parseInt(txt_jml.getText());
         total = harga * brg;
         
    if(jTable1.getSelectedRow()==-1){
        if(jTable1.getRowCount()==0){
            JOptionPane.showMessageDialog(null, "Tidak ada Data Dalam Table");
        } else {
            JOptionPane.showMessageDialog(null, "pilih data yang akan diubah");
        }
    }else{
        
        model.setValueAt(brg, jTable1.getSelectedRow(), 4);
        model.setValueAt(total, jTable1.getSelectedRow(), 5);
        todKabeh();
    }
    }
    
    public void confirm(){
        int jawab = JOptionPane.showConfirmDialog(this, "Apakah anda  ingin Menambah Stock? dan menuju menu Barang?");
        
        switch(jawab){
            case JOptionPane.YES_OPTION:
                this.setVisible(false);
                new MainMenu(username1.getText()).setVisible(true);
                break;
            case JOptionPane.NO_OPTION:
                JOptionPane.showMessageDialog(this, "Silakan Kembali beraktifitas");
                break;
            case JOptionPane.CANCEL_OPTION:
                 JOptionPane.showMessageDialog(this, "BATAL OKE");
                 break;   
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

        jFormattedTextField1 = new javax.swing.JFormattedTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        txt_cariData = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txt_namaBarang = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txt_jml = new javax.swing.JTextField();
        txt_idTrans = new javax.swing.JTextField();
        txt_idBrg = new javax.swing.JTextField();
        txt_hrg = new javax.swing.JTextField();
        Btn_editData = new javax.swing.JButton();
        btn_bayar = new javax.swing.JButton();
        btn_reset = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel5 = new javax.swing.JLabel();
        txt_tampil = new javax.swing.JTextField();
        btn_hps = new javax.swing.JButton();
        btn_tambahData = new javax.swing.JButton();
        txt_kembali = new javax.swing.JTextField();
        txt_uang = new javax.swing.JTextField();
        Btn_Hitung = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        btn_out = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

        jFormattedTextField1.setText("jFormattedTextField1");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jTable2.setBackground(new java.awt.Color(255, 255, 255));
        jTable2.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
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
        jScrollPane1.setViewportView(jTable2);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 120, 370, 160));

        txt_cariData.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_cariDataKeyReleased(evt);
            }
        });
        getContentPane().add(txt_cariData, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 82, 290, 30));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("NAMA BARANG");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 290, -1, 40));

        txt_namaBarang.setEditable(false);
        txt_namaBarang.setFont(new java.awt.Font("Segoe UI", 3, 14)); // NOI18N
        getContentPane().add(txt_namaBarang, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 290, 280, 40));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 0, 0));
        jLabel3.setText("KEMBALIAN:");
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 580, -1, 20));
        getContentPane().add(txt_jml, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 260, 260, 30));

        txt_idTrans.setEditable(false);
        getContentPane().add(txt_idTrans, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 140, 120, 30));

        txt_idBrg.setEditable(false);
        getContentPane().add(txt_idBrg, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 180, 260, 30));

        txt_hrg.setEditable(false);
        txt_hrg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_hrgActionPerformed(evt);
            }
        });
        getContentPane().add(txt_hrg, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 220, 260, 30));

        Btn_editData.setBackground(new java.awt.Color(255, 153, 153));
        Btn_editData.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        Btn_editData.setForeground(new java.awt.Color(255, 255, 255));
        Btn_editData.setText("EDIT");
        Btn_editData.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Btn_editDataActionPerformed(evt);
            }
        });
        getContentPane().add(Btn_editData, new org.netbeans.lib.awtextra.AbsoluteConstraints(710, 310, 100, 30));

        btn_bayar.setBackground(new java.awt.Color(0, 255, 0));
        btn_bayar.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btn_bayar.setForeground(new java.awt.Color(255, 255, 255));
        btn_bayar.setText("BAYAR");
        btn_bayar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_bayarActionPerformed(evt);
            }
        });
        getContentPane().add(btn_bayar, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 600, 90, 30));

        btn_reset.setBackground(new java.awt.Color(51, 51, 255));
        btn_reset.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btn_reset.setForeground(new java.awt.Color(255, 255, 255));
        btn_reset.setText("RESET");
        btn_reset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_resetActionPerformed(evt);
            }
        });
        getContentPane().add(btn_reset, new org.netbeans.lib.awtextra.AbsoluteConstraints(710, 510, 120, 30));

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 0, 0));
        jLabel4.setText("TOTAL BAYAR :");
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 410, -1, -1));

        jTable1.setBackground(new java.awt.Color(255, 255, 255));
        jTable1.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
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
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(jTable1);

        getContentPane().add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 400, 550, 170));

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 0, 0));
        jLabel5.setText("TABLE TRANSAKSI");
        getContentPane().add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        txt_tampil.setBackground(new java.awt.Color(204, 204, 204));
        txt_tampil.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        txt_tampil.setCaretColor(new java.awt.Color(51, 51, 255));
        txt_tampil.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txt_tampil.setEnabled(false);
        txt_tampil.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_tampilActionPerformed(evt);
            }
        });
        getContentPane().add(txt_tampil, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 440, 260, 60));

        btn_hps.setBackground(new java.awt.Color(255, 51, 51));
        btn_hps.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btn_hps.setForeground(new java.awt.Color(255, 255, 255));
        btn_hps.setText("HAPUS");
        btn_hps.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_hpsActionPerformed(evt);
            }
        });
        getContentPane().add(btn_hps, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 310, 100, 30));

        btn_tambahData.setBackground(new java.awt.Color(255, 153, 153));
        btn_tambahData.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btn_tambahData.setForeground(new java.awt.Color(255, 255, 255));
        btn_tambahData.setText("TAMBAH");
        btn_tambahData.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_tambahDataActionPerformed(evt);
            }
        });
        getContentPane().add(btn_tambahData, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 310, 100, 30));

        txt_kembali.setEditable(false);
        txt_kembali.setFont(new java.awt.Font("Segoe UI", 3, 14)); // NOI18N
        getContentPane().add(txt_kembali, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 600, 180, 30));

        txt_uang.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        getContentPane().add(txt_uang, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 600, 180, 30));

        Btn_Hitung.setBackground(new java.awt.Color(255, 51, 51));
        Btn_Hitung.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        Btn_Hitung.setForeground(new java.awt.Color(255, 255, 255));
        Btn_Hitung.setText("SIMPAN");
        Btn_Hitung.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Btn_HitungActionPerformed(evt);
            }
        });
        getContentPane().add(Btn_Hitung, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 510, 120, 30));

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("STOCK");
        getContentPane().add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 260, 80, 30));

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(0, 0, 0));
        jLabel7.setText("UANG BAYAR :");
        getContentPane().add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 580, -1, 20));

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("CARI BARANG");
        getContentPane().add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 80, -1, 30));

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("ID TRANS");
        getContentPane().add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 140, 60, 30));

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("ID BARANG");
        getContentPane().add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 180, 80, 30));

        jLabel11.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText("HARGA");
        getContentPane().add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 220, 80, 30));

        btn_out.setBackground(new java.awt.Color(255, 0, 153));
        btn_out.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btn_out.setForeground(new java.awt.Color(255, 255, 255));
        btn_out.setText("OUT");
        btn_out.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_outActionPerformed(evt);
            }
        });
        getContentPane().add(btn_out, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 610, 80, 30));

        username1.setFont(new java.awt.Font("Lucida Sans", 1, 14)); // NOI18N
        username1.setForeground(new java.awt.Color(255, 255, 255));
        username1.setText("jLabel12");
        getContentPane().add(username1, new org.netbeans.lib.awtextra.AbsoluteConstraints(770, 50, -1, -1));

        username2.setFont(new java.awt.Font("Lucida Sans", 1, 14)); // NOI18N
        username2.setForeground(new java.awt.Color(255, 255, 255));
        username2.setText("ADMIN :");
        getContentPane().add(username2, new org.netbeans.lib.awtextra.AbsoluteConstraints(710, 50, -1, -1));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img12/Kasir.png"))); // NOI18N
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, -10, -1, 660));

        setSize(new java.awt.Dimension(855, 679));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void txt_hrgActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_hrgActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_hrgActionPerformed

    private void txt_cariDataKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_cariDataKeyReleased
        // TODO add your handling code here:
        String key=txt_cariData.getText(); 
        if(key!=""){
            cariData(key);
        }else{
            loadTableBrg();
        }
    }//GEN-LAST:event_txt_cariDataKeyReleased

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
        jTable1.getModel();
        if(model.getRowCount()==0){
            JOptionPane.showMessageDialog(this, "tes");
        } else {
            InsertPenjualan();
            InsertDetail();
            JOptionPane.showMessageDialog(null, "DATA BERHASIL TERSIMPAN");
            loadTableBrg();
        }
        
    }//GEN-LAST:event_Btn_HitungActionPerformed

    private void btn_resetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_resetActionPerformed
        // TODO add your handling code here:
        resetTextField();
        autoNumber();
    }//GEN-LAST:event_btn_resetActionPerformed

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

    private void txt_tampilActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_tampilActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_tampilActionPerformed
    public void setNama(String user){
    username1.setText(user);
    }
    private void btn_outActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_outActionPerformed
        // TODO add your handling code here:
        this.setVisible(false);
        new MainMenu(username1.getText()).setVisible(true);
    }//GEN-LAST:event_btn_outActionPerformed

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        // TODO add your handling code here:
        int barisan = jTable1.rowAtPoint(evt.getPoint());
        
        if(jTable1.getValueAt(barisan, 0)==null){
            txt_idTrans.setText("");
            
        }else {
            txt_idTrans.setText(jTable1.getValueAt(barisan, 0).toString());
        }
        if(jTable1.getValueAt(barisan, 1)==null){
            txt_idBrg.setText("");
            
        }else {
            txt_idBrg.setText(jTable1.getValueAt(barisan, 1).toString());
        }
        
        if(jTable1.getValueAt(barisan, 2)==null){
            txt_namaBarang.setText("");
        }else {
            txt_namaBarang.setText(jTable1.getValueAt(barisan, 2).toString());
        }
         if(jTable1.getValueAt(barisan, 3)==null){
            txt_hrg.setText("");
        }else {
            txt_hrg.setText(jTable1.getValueAt(barisan, 3).toString());
        }
         if(jTable1.getValueAt(barisan, 4)==null){
            txt_jml.setText("");
        }else {
            txt_jml.setText(jTable1.getValueAt(barisan, 4).toString());
        }
        
    }//GEN-LAST:event_jTable1MouseClicked

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
            java.util.logging.Logger.getLogger(Kasir.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Kasir.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Kasir.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Kasir.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Kasir(username1.getText()).setVisible(true);
            }
        });
    }
    DefaultTableModel model2 = new DefaultTableModel();
    int baris = 0;
    static Object kolom[]= {"id Transaksi","ID Barang","Barang","Harga","Jumlah Barang","Grand Total"};
    DefaultTableModel model = new DefaultTableModel(kolom,baris);
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Btn_Hitung;
    private javax.swing.JButton Btn_editData;
    private javax.swing.JButton btn_bayar;
    private javax.swing.JButton btn_hps;
    private javax.swing.JButton btn_out;
    private javax.swing.JButton btn_reset;
    private javax.swing.JButton btn_tambahData;
    private javax.swing.JFormattedTextField jFormattedTextField1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
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
    public static final javax.swing.JLabel username1 = new javax.swing.JLabel();
    public static final javax.swing.JLabel username2 = new javax.swing.JLabel();
    // End of variables declaration//GEN-END:variables
}
