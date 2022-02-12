/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package ims;

import java.sql.SQLException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;

/**
 *
 * @author User
 */
public class TransactionScreen extends javax.swing.JFrame {

    public TransactionScreen() {
        initComponents();
        loadDataToComboBox();

    }

    public void loadDataToComboBox() {
        // get products from the database
        Helper.initialize();
        Vector<String> productNames = new Vector<String>();
        String query = "SELECT product_name FROM Product ORDER BY product_name";
        try {
            PreparedStatement pstmt = Helper.conn.prepareStatement(query);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                productNames.add(rs.getString("product_name"));
            }
        } catch (SQLException e) {
            Helper.logger("loadDataToComboBox", e);
        } finally {
            Helper.closeSQLConnection();
        }

        DefaultComboBoxModel model = new DefaultComboBoxModel(productNames);
        productComboBox.setModel(model);
    }

    public int getProductQuantity(String productName) {
        int result = 0;
        Helper.initialize();
        // COLLATE NOCASE makes the product_name value case-insensitive
        String query = "SELECT quantity FROM Product WHERE product_name = ? COLLATE NOCASE";
        try {
            PreparedStatement pstmt = Helper.conn.prepareStatement(query);
            pstmt.setString(1, productName);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                result = rs.getInt("quantity");
            } else {
                JOptionPane.showMessageDialog(null, "Product may not exist");
            }
        } catch (SQLException e) {
            Helper.logger("getProductQuantity", e);
        } finally {
            Helper.closeSQLConnection();
        }
        return result;
    }

    public void updateProductQuantity(String productName, int newProductQuantity) {
        String query = "UPDATE Product SET quantity = ? Where product_name = ? COLLATE NOCASE";

        Helper.initialize();
        try {
            PreparedStatement pstmt = Helper.conn.prepareStatement(query);
            pstmt.setInt(1, newProductQuantity);
            pstmt.setString(2, productName);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            Helper.logger("updateProductQuantity", e);
        } finally {
            Helper.closeSQLConnection();
        }

    }

    public void saveTransaction() {
        int invoiceID = 0;
        int costPrice = 0;
        int quantity = 0;
        int newProductQuantity = 0;
        double sellingPrice = 0.0;

        String productName = productComboBox.getSelectedItem().toString();
        try {
            invoiceID = Integer.parseInt(invoiceNumberTxt.getText());
        } catch (NumberFormatException e) {
            invoiceNumberTxt.setText("");
            Helper.logger("saveTransaction ", e);
        }

        try {
            costPrice = Integer.parseInt(costPriceTxt.getText());
        } catch (NumberFormatException e) {
            costPriceTxt.setText("");
            Helper.logger("saveTransaction ", e);
        }

        try {
            sellingPrice = Double.parseDouble(sellingPriceTxt.getText());
        } catch (NumberFormatException e) {
            sellingPriceTxt.setText("");
            Helper.logger("saveTransaction ", e);
        }

        if (getProductQuantity(productName) == 0) {
            JOptionPane.showMessageDialog(null, "Product out of stock", "Inventory Restock", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (getProductQuantity(productName) < 3) {
            JOptionPane.showMessageDialog(null, "Product inventory getting low. Please restock", "Inventory Restock", JOptionPane.WARNING_MESSAGE);
        }

        try {
            quantity = Integer.parseInt(quantityTxt.getText());
        } catch (NumberFormatException e) {
            quantityTxt.setText("");
            Helper.logger("saveTransaction ", e);
        }

        if (quantity > getProductQuantity(productName)) {
            JOptionPane.showMessageDialog(null, "Quantity greater than available stock. Cannot save transaction", "Transaction Save Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        //date format: YYMMDD
        String date = String.valueOf(yearCombo.getSelectedItem()) + String.valueOf(monthCombo.getSelectedItem()) + String.valueOf(dayCombo.getSelectedItem());

        // insert all the data into database
        String query = "INSERT INTO ProductTransaction (date, invoice_number, product_name, cost_price, selling_price, quantity) VALUES (?, ?, ?, ?, ?, ?)";
        Helper.initialize();
        try {

            PreparedStatement pstmt = Helper.conn.prepareStatement(query);

            pstmt.setString(1, date);
            pstmt.setInt(2, invoiceID);
            pstmt.setString(3, productName);
            pstmt.setInt(4, costPrice);
            pstmt.setDouble(5, sellingPrice);
            pstmt.setInt(6, quantity);

            pstmt.executeUpdate();
            System.out.println("Transaction successfully inserted");
            JOptionPane.showMessageDialog(null, "Transaction has been saved", "Transactions", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException e) {
            Helper.logger("saveTransaction", e);
        } finally {
            Helper.closeSQLConnection();
        }

        // decrease the product quanity in the Product Table after adding Transaction Record
        int previousProductQuantity = getProductQuantity(productName);

        newProductQuantity = previousProductQuantity - quantity;
        updateProductQuantity(productName, newProductQuantity);

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        invoiceNumberTxt = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        costPriceTxt = new javax.swing.JTextField();
        sellingPriceTxt = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        dayCombo = new javax.swing.JComboBox<>();
        jLabel8 = new javax.swing.JLabel();
        yearCombo = new javax.swing.JComboBox<>();
        jLabel9 = new javax.swing.JLabel();
        monthCombo = new javax.swing.JComboBox<>();
        saveTransactionBtn = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        quantityTxt = new javax.swing.JTextField();
        updateDeleteTransactionBtn = new javax.swing.JButton();
        productComboBox = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Transaction Screen");

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jLabel1.setText("Transaction Screen");

        jLabel2.setFont(new java.awt.Font("Segoe UI Light", 0, 14)); // NOI18N
        jLabel2.setText("Product Name");

        jLabel3.setFont(new java.awt.Font("Segoe UI Light", 0, 14)); // NOI18N
        jLabel3.setText("Invoice ID");

        invoiceNumberTxt.setFont(new java.awt.Font("Corbel", 0, 12)); // NOI18N
        invoiceNumberTxt.setToolTipText("Enter the invoice number");

        jLabel4.setFont(new java.awt.Font("Segoe UI Light", 0, 14)); // NOI18N
        jLabel4.setText("Cost Price");

        jLabel5.setFont(new java.awt.Font("Segoe UI Light", 0, 14)); // NOI18N
        jLabel5.setText("Selling Price");

        costPriceTxt.setFont(new java.awt.Font("Corbel", 0, 12)); // NOI18N

        sellingPriceTxt.setFont(new java.awt.Font("Corbel", 0, 12)); // NOI18N

        jLabel6.setFont(new java.awt.Font("Segoe UI Light", 1, 18)); // NOI18N
        jLabel6.setText("Date");

        jLabel7.setFont(new java.awt.Font("Segoe UI Light", 0, 14)); // NOI18N
        jLabel7.setText("Month");

        String[] months = new String[31];
        for(int i = 0; i < 31; i++){
            months[i] = String.valueOf((i + 1));
        }
        dayCombo.setModel(new javax.swing.DefaultComboBoxModel<>(months));

        jLabel8.setFont(new java.awt.Font("Segoe UI Light", 0, 14)); // NOI18N
        jLabel8.setText("Day");

        String[] years = new String[50];
        int start = 2000;
        for(int i = 0; i < 50; i++){
            years[i] = String.valueOf(start);
            start++;
        }
        yearCombo.setModel(new javax.swing.DefaultComboBoxModel<>(years));

        jLabel9.setFont(new java.awt.Font("Segoe UI Light", 0, 14)); // NOI18N
        jLabel9.setText("Year");

        monthCombo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "01", "02", "03", "04",
            "05", "06", "07", "08",
            "09", "10", "11", "12", }));

saveTransactionBtn.setBackground(new java.awt.Color(24, 128, 7));
saveTransactionBtn.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
saveTransactionBtn.setText("Save Transaction");
saveTransactionBtn.addActionListener(new java.awt.event.ActionListener() {
    public void actionPerformed(java.awt.event.ActionEvent evt) {
        saveTransactionBtnActionPerformed(evt);
    }
    });

    jLabel10.setFont(new java.awt.Font("Segoe UI Light", 0, 14)); // NOI18N
    jLabel10.setText("Quantity");

    quantityTxt.setFont(new java.awt.Font("Corbel", 0, 12)); // NOI18N

    updateDeleteTransactionBtn.setBackground(new java.awt.Color(24, 128, 7));
    updateDeleteTransactionBtn.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
    updateDeleteTransactionBtn.setText("Update/Delete");
    updateDeleteTransactionBtn.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            updateDeleteTransactionBtnActionPerformed(evt);
        }
    });

    productComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
    productComboBox.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            productComboBoxActionPerformed(evt);
        }
    });

    javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
    getContentPane().setLayout(layout);
    layout.setHorizontalGroup(
        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(layout.createSequentialGroup()
            .addComponent(saveTransactionBtn)
            .addGap(0, 433, Short.MAX_VALUE))
        .addGroup(layout.createSequentialGroup()
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(16, 16, 16)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                            .addGap(10, 10, 10)
                            .addComponent(jLabel7)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(monthCombo, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(12, 12, 12)
                            .addComponent(jLabel8)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(updateDeleteTransactionBtn)
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(dayCombo, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(18, 18, 18)
                                    .addComponent(jLabel9)
                                    .addGap(18, 18, 18)
                                    .addComponent(yearCombo, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addComponent(jLabel6)
                        .addGroup(layout.createSequentialGroup()
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel2)
                                .addComponent(jLabel4)
                                .addComponent(jLabel5)
                                .addComponent(jLabel10))
                            .addGap(28, 28, 28)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(quantityTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(sellingPriceTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(costPriceTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(invoiceNumberTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(productComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGroup(layout.createSequentialGroup()
                    .addGap(116, 116, 116)
                    .addComponent(jLabel1)))
            .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jLabel3)
                .addContainerGap(496, Short.MAX_VALUE)))
    );
    layout.setVerticalGroup(
        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(layout.createSequentialGroup()
            .addComponent(jLabel1)
            .addGap(28, 28, 28)
            .addComponent(invoiceNumberTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGap(18, 18, 18)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jLabel2)
                .addComponent(productComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jLabel4)
                .addComponent(costPriceTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGap(18, 18, 18)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jLabel5)
                .addComponent(sellingPriceTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jLabel10)
                .addComponent(quantityTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGap(22, 22, 22)
            .addComponent(jLabel6)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jLabel7)
                .addComponent(dayCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(jLabel8)
                .addComponent(jLabel9)
                .addComponent(monthCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(yearCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGap(47, 47, 47)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(saveTransactionBtn)
                .addComponent(updateDeleteTransactionBtn))
            .addGap(0, 50, Short.MAX_VALUE))
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(76, 76, 76)
                .addComponent(jLabel3)
                .addContainerGap(364, Short.MAX_VALUE)))
    );

    pack();
    }// </editor-fold>//GEN-END:initComponents

    private void saveTransactionBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveTransactionBtnActionPerformed
        // TODO add your handling code here:
        this.saveTransaction();
    }//GEN-LAST:event_saveTransactionBtnActionPerformed

    private void updateDeleteTransactionBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateDeleteTransactionBtnActionPerformed
        // TODO add your handling code here:
        new UpdateDeleteTransaction(this, rootPaneCheckingEnabled).setVisible(true);
    }//GEN-LAST:event_updateDeleteTransactionBtnActionPerformed

    private void productComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_productComboBoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_productComboBoxActionPerformed

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
            java.util.logging.Logger.getLogger(TransactionScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TransactionScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TransactionScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TransactionScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TransactionScreen().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField costPriceTxt;
    private javax.swing.JComboBox<String> dayCombo;
    private javax.swing.JTextField invoiceNumberTxt;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JComboBox<String> monthCombo;
    private javax.swing.JComboBox<String> productComboBox;
    private javax.swing.JTextField quantityTxt;
    private javax.swing.JButton saveTransactionBtn;
    private javax.swing.JTextField sellingPriceTxt;
    private javax.swing.JButton updateDeleteTransactionBtn;
    private javax.swing.JComboBox<String> yearCombo;
    // End of variables declaration//GEN-END:variables

    private static class NUmberFormatException {

        public NUmberFormatException() {
        }
    }
}
