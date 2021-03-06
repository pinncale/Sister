/*
 * Copyright (C) 2014 Yoan Pratama Putra
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package client;

import fridge.RMI;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.security.MessageDigest;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Yoan Pratama Putra, Ritos Penyawang
 */
public class Main extends javax.swing.JFrame {

    /**
     * Creates new form Main
     */
    private static RMI rmi = null;
    private static Registry reg = null;
    private static connectThread conThread = null;
    private static int temperature = 0;
    private static int freezerTemp = 0;
    private static int connectFlag = 1; // untuk menandakan percobaan pertama atau bukan
    private static DefaultTableModel tbl;
    private static int fail = 0;
    private static String ip = null;
    
    public Main() {
        initComponents();
        this.setTitle("Client");
        
        tbl = (DefaultTableModel)tblItem.getModel();
    }
    
    public static void refreshList() {
        // ambil list barang dari server
        try {
            int numItem = rmi.getNumItem();
            Object[][] o = new Object[numItem][2];
            
            // bersihkan row
            int row = tbl.getRowCount();
            for(int i = row - 1; i >= 0; i--)
                tbl.removeRow(i);
                
            // tampilkan isi kulkas
            o = rmi.getItemList();
            for(int i = 0; i < numItem; i++)
                tbl.addRow(o[i]);
        } catch(RemoteException ex) {
            System.out.println(ex);
        }
    }
    
    public static boolean ConnectServer() {
        try {            
            // ubah status menjadi connecting
            lblConnectStatus.setText("Connecting...");
            dlgLogin.setTitle("Connecting...");
            
            // membuat koneksi ke kulkas
            reg = LocateRegistry.getRegistry(ip, 1099);
            rmi = (RMI) reg.lookup("server");

            if(connectFlag == 1) {
                // ambil suhu kulkas saat ini
                temperature = rmi.getTemperature();
                freezerTemp = rmi.getFreezerTemp();
                txtTemperature.setValue(temperature);
                txtFreezer.setValue(freezerTemp);
                connectFlag++;
            }
            return true;
        } catch(Exception ex) {
            // unset reg dan rmi
            reg = null;
            rmi = null;
            
            // kembalikan nilai connectFlag ke 1
            connectFlag = 1;
            
            // tambah penghitung fail
            fail++;
            conThread.setFail(fail);
            
            System.out.println(ex);
        } finally {
            // ubah status menjadi connected
            if(rmi != null) {
                // ubah status menjadi connected
                lblConnectStatus.setText("Connected");
                dlgLogin.setTitle("Connected");
                
                // ubah fail kembali menjadi 0 untuk melakukan reconnect
                fail = 0;
                
                // kirim nilai fail yang baru
                conThread.setFail(fail);
            } else {
                if(fail > 2) {
                    // ubah status menjadi failed to connect
                    lblConnectStatus.setText("Failed to connect");
                    dlgLogin.setTitle("Failed to connect");
                    
                    // tampilkan dialog konfirmasi reconnect
                    int confirm = JOptionPane.showConfirmDialog(tblItem, "Failed to connect to server. Do you wish to continue?", "Connection failed", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
                    
                    // cek konfirmasi apakah "ya" atau "tidak"
                    if(confirm < 1) {
                        // lakukan reconnect ke server kulkas
                        fail = 0;
                        conThread.setFail(fail);
                    }
                }
            }
        }
        return false;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        dlgLogin = new javax.swing.JDialog();
        lblUser = new javax.swing.JLabel();
        lblPass = new javax.swing.JLabel();
        txtUser = new javax.swing.JTextField();
        btnLogin = new javax.swing.JButton();
        txtPass = new javax.swing.JPasswordField();
        lblIP = new javax.swing.JLabel();
        txtIP = new javax.swing.JTextField();
        btnApply = new javax.swing.JButton();
        lblCelcius = new javax.swing.JLabel();
        txtTemperature = new javax.swing.JSpinner();
        btnRefreshTemp = new javax.swing.JButton();
        pnlStatus = new javax.swing.JPanel();
        lblConnectStatus = new javax.swing.JLabel();
        lblTemperature = new javax.swing.JLabel();
        txtFreezer = new javax.swing.JSpinner();
        lblFreezer = new javax.swing.JLabel();
        lblCelcius2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblItem = new javax.swing.JTable();

        dlgLogin.setTitle("Login Form");
        dlgLogin.setLocationByPlatform(true);
        dlgLogin.setMinimumSize(new java.awt.Dimension(231, 177));
        dlgLogin.setResizable(false);
        dlgLogin.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                dlgLoginWindowClosing(evt);
            }
        });

        lblUser.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        lblUser.setText("Username");

        lblPass.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        lblPass.setText("Password");

        txtUser.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N

        btnLogin.setText("Login");
        btnLogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLoginActionPerformed(evt);
            }
        });

        txtPass.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N

        lblIP.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        lblIP.setText("IP Address");

        txtIP.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        txtIP.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtIP.setText("127.0.0.1");

        javax.swing.GroupLayout dlgLoginLayout = new javax.swing.GroupLayout(dlgLogin.getContentPane());
        dlgLogin.getContentPane().setLayout(dlgLoginLayout);
        dlgLoginLayout.setHorizontalGroup(
            dlgLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dlgLoginLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(dlgLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblUser)
                    .addComponent(lblPass)
                    .addComponent(lblIP))
                .addGap(18, 18, 18)
                .addGroup(dlgLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(dlgLoginLayout.createSequentialGroup()
                        .addComponent(btnLogin)
                        .addGap(62, 86, Short.MAX_VALUE))
                    .addGroup(dlgLoginLayout.createSequentialGroup()
                        .addGroup(dlgLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(txtIP, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtPass, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 132, Short.MAX_VALUE)
                            .addComponent(txtUser, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 132, Short.MAX_VALUE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        dlgLoginLayout.setVerticalGroup(
            dlgLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, dlgLoginLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(dlgLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtUser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblUser))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(dlgLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtPass, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblPass))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(dlgLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtIP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblIP))
                .addGap(18, 18, 18)
                .addComponent(btnLogin)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMaximumSize(new java.awt.Dimension(400, 350));
        setMinimumSize(new java.awt.Dimension(400, 350));
        setResizable(false);

        btnApply.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        btnApply.setText("Apply");
        btnApply.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnApplyActionPerformed(evt);
            }
        });

        lblCelcius.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        lblCelcius.setText("°C");

        txtTemperature.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        txtTemperature.setModel(new javax.swing.SpinnerNumberModel(1, 1, 6, 1));
        txtTemperature.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                txtTemperatureStateChanged(evt);
            }
        });

        btnRefreshTemp.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        btnRefreshTemp.setText("Refresh");
        btnRefreshTemp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRefreshTempActionPerformed(evt);
            }
        });

        pnlStatus.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N

        lblConnectStatus.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        lblConnectStatus.setText("Not Connected");

        javax.swing.GroupLayout pnlStatusLayout = new javax.swing.GroupLayout(pnlStatus);
        pnlStatus.setLayout(pnlStatusLayout);
        pnlStatusLayout.setHorizontalGroup(
            pnlStatusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlStatusLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblConnectStatus)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlStatusLayout.setVerticalGroup(
            pnlStatusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblConnectStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        lblTemperature.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        lblTemperature.setText("Fridge :");

        txtFreezer.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        txtFreezer.setModel(new javax.swing.SpinnerNumberModel(-13, -21, -13, 1));
        txtFreezer.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                txtFreezerStateChanged(evt);
            }
        });

        lblFreezer.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        lblFreezer.setText("Freezer :");

        lblCelcius2.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        lblCelcius2.setText("°C");

        tblItem.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        tblItem.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Name", "Quantity"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.Integer.class
            };
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tblItem);
        if (tblItem.getColumnModel().getColumnCount() > 0) {
            tblItem.getColumnModel().getColumn(0).setMinWidth(200);
            tblItem.getColumnModel().getColumn(0).setPreferredWidth(250);
            tblItem.getColumnModel().getColumn(0).setMaxWidth(250);
            tblItem.getColumnModel().getColumn(1).setMinWidth(100);
        }

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlStatus, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 125, Short.MAX_VALUE)
                        .addComponent(lblFreezer)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtFreezer, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(lblCelcius2)
                                .addGap(18, 18, 18)
                                .addComponent(lblTemperature)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtTemperature, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblCelcius))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addComponent(btnRefreshTemp)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnApply)))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblCelcius)
                    .addComponent(txtTemperature, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblTemperature)
                    .addComponent(txtFreezer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblFreezer)
                    .addComponent(lblCelcius2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnApply)
                    .addComponent(btnRefreshTemp))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 251, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlStatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnApplyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnApplyActionPerformed
        // kirim suhu baru ke kulkas
        try {
            temperature = Integer.parseInt(txtTemperature.getValue().toString());
            freezerTemp = Integer.parseInt(txtFreezer.getValue().toString());
            try {
                rmi.setTemperature(temperature, freezerTemp);
            } catch(RemoteException ex) {
                System.out.println(ex);
            }
        } catch(NumberFormatException ex) {
            System.out.println(ex);
        }
    }//GEN-LAST:event_btnApplyActionPerformed

    private void txtTemperatureStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_txtTemperatureStateChanged
        // mengubah suhu kulkas
        temperature = Integer.parseInt(txtTemperature.getValue().toString());
    }//GEN-LAST:event_txtTemperatureStateChanged

    private void btnRefreshTempActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRefreshTempActionPerformed
        // refresh suhu dari kulkas
        try {
            temperature = rmi.getTemperature();
            freezerTemp = rmi.getFreezerTemp();
            txtTemperature.setValue(temperature);
            txtFreezer.setValue(freezerTemp);
        } catch(RemoteException ex) {
            System.out.println(ex);
        }
    }//GEN-LAST:event_btnRefreshTempActionPerformed

    private void txtFreezerStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_txtFreezerStateChanged
        // mengubah suhu freezer
        freezerTemp = Integer.parseInt(txtFreezer.getValue().toString());
    }//GEN-LAST:event_txtFreezerStateChanged
    
    private void btnLoginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLoginActionPerformed
        if(login() == 1){
            this.setEnabled(true);
            this.setVisible(true);
            dlgLogin.setVisible(false);
            dlgLogin.setEnabled(false);
        }
    }//GEN-LAST:event_btnLoginActionPerformed

    private void dlgLoginWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_dlgLoginWindowClosing
        // keluar dari aplikasi
        System.exit(1);
    }//GEN-LAST:event_dlgLoginWindowClosing

    private static int login(){
        try{
            MessageDigest md = MessageDigest.getInstance("SHA1");
            char[] temp = txtPass.getPassword();
            String temp2 = new String(temp);
            temp2 = temp2 + ":smartrefrigerator";
            md.update(temp2.getBytes());
            byte[] output = md.digest();
            String temp3 = bytesToHex(output);
            ip = txtIP.getText();
            
            if(rmi.login(txtUser.getText(), temp3.toLowerCase()))
                return 1;

        } catch(Exception e) {
            System.out.println("Exception: "+e);
        }
        return 0;
    }

    public static String bytesToHex(byte[] b) {
        char hexDigit[] = {'0', '1', '2', '3', '4', '5', '6', '7',
                            '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        StringBuffer buf = new StringBuffer();
        for (int j=0; j<b.length; j++) {
            buf.append(hexDigit[(b[j] >> 4) & 0x0f]);
            buf.append(hexDigit[b[j] & 0x0f]);
        }
        return buf.toString();
    }

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
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
//                new Main().setVisible(true);
                new Main().setEnabled(false);
                dlgLogin.setVisible(true);
                
                // buat thread koneksi
                conThread = new connectThread();
                
                // jalankan thread koneksi
                conThread.start();
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnApply;
    private javax.swing.JButton btnLogin;
    private javax.swing.JButton btnRefreshTemp;
    private static javax.swing.JDialog dlgLogin;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblCelcius;
    private javax.swing.JLabel lblCelcius2;
    private static javax.swing.JLabel lblConnectStatus;
    private javax.swing.JLabel lblFreezer;
    private javax.swing.JLabel lblIP;
    private javax.swing.JLabel lblPass;
    private javax.swing.JLabel lblTemperature;
    private javax.swing.JLabel lblUser;
    private javax.swing.JPanel pnlStatus;
    private static javax.swing.JTable tblItem;
    private static javax.swing.JSpinner txtFreezer;
    private static javax.swing.JTextField txtIP;
    private static javax.swing.JPasswordField txtPass;
    private static javax.swing.JSpinner txtTemperature;
    private static javax.swing.JTextField txtUser;
    // End of variables declaration//GEN-END:variables
}
