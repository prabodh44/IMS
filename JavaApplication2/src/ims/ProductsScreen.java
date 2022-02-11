/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package ims;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author User
 */
public class ProductsScreen extends javax.swing.JFrame {

    public ProductsScreen() {
        initComponents();
        this.setResizable(false);
    }
    
    private void loadData(){
        PreparedStatement pst;
        ResultSet rs;
        
        
        try {
            //Class.forName("org.sqlite.JDBC");
            // con=java.sql.DriverManager.getConnection("jdbc:sqlite:maindb.db");
            Helper.initialize();
            String query="select * from product";
            pst=Helper.conn.prepareStatement(query);
            rs=pst.executeQuery();
            DefaultTableModel tblModel=(DefaultTableModel)productsTable.getModel();
            tblModel.setRowCount(0);
            int sno = 1;
            while(rs.next())
            {
                String productName = rs.getString("product_name");
                String price = rs.getString("price");
                String qty = rs.getString("quantity");
                
                String tbData[]={sno+"",productName,price,qty};
                tblModel.addRow(tbData);
                        sno++;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,e);
        }finally{
            Helper.closeSQLConnection();
        }
        
    }

    
    public void insertProduct(Product product) {

        try {
            Helper.initialize();
            String query = "INSERT INTO product(product_name, price, quantity) VALUES (?, ?, ?)";
            PreparedStatement pstmt = Helper.conn.prepareStatement(query);
            System.out.println("pstmt " + pstmt);
            pstmt.setString(1, product.getProductName().trim());
            pstmt.setDouble(2, product.getPrice());
            pstmt.setInt(3, product.getQuantity());

            pstmt.executeUpdate();
            pstmt.clearParameters();

        } catch (SQLException e) {
            Helper.logger("insertProduct", e);
        } finally {
            Helper.closeSQLConnection();
        }
    }

    public int getNumberOfRows() {
        int count = 0;
        String query = "SELECT count(*) AS count from Product";

        try {
            Helper.initialize();
            try ( Statement statement = Helper.conn.createStatement()) {
                ResultSet rs = statement.executeQuery(query);
                

                while (rs.next()) {
                    count = rs.getInt("count");
                }
                rs.close();
            }
        } catch (SQLException e) {
            Helper.logger("getNumberOfRows", e);
        } finally {
            Helper.closeSQLConnection();
        }
        return count;
    }

    public ResultSet selectAllProducts() {
        ResultSet rs = null;
        
        String query = "SELECT * from Product";

        try {
            Helper.initialize();
            try ( Statement statement = Helper.conn.createStatement()) {
                rs = statement.executeQuery(query);
            }
            System.out.println("selectAllProducts: " + rs);
            rs.close();
            return rs;

        } catch (SQLException e) {
            Helper.logger("selectAllProducts", e);
        } finally {
            Helper.closeSQLConnection();
        }

        return rs;
    }

    public void showDataToTable() {
        try {
            // TODO add your handling code here:
            ResultSet rs = this.selectAllProducts();
            ResultSetMetaData rsMetaData = rs.getMetaData();

            //int NUMBER_OF_COLUMNS = rsMetaData.getColumnCount();
             int NUMBER_OF_COLUMNS = 4;
              
              //System.out.println("showDataToTable conn " + conn);
            int NUMBER_OF_ROWS = this.getNumberOfRows();
            //int NUMBER_OF_ROWS  = 2;

            String[] columns = new String[NUMBER_OF_COLUMNS];
            Object[][] data = new Object[NUMBER_OF_ROWS][NUMBER_OF_COLUMNS];

            for (int i = 0; i < NUMBER_OF_COLUMNS; i++) {
//                columns[i] = rsMetaData.getColumnName(i + 1);
            }

            for (int i = 0; i < NUMBER_OF_ROWS; i++) {
                rs.next();
                for (int j = 0; j < NUMBER_OF_COLUMNS; j++) {
                    data[i][j] = rs.getObject(j + 1);

                }

            }

            productsTable.setModel(new javax.swing.table.DefaultTableModel(
                    data,
                    columns
            ));

            jScrollPane1.setViewportView(productsTable);

        } catch (SQLException e) {
            Helper.logger("showDataToTable", e);
        } finally {
            Helper.closeSQLConnection();
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

        jLabel1 = new javax.swing.JLabel();
        productNameLbl = new javax.swing.JLabel();
        productNameTxt = new javax.swing.JTextField();
        priceLbl = new javax.swing.JLabel();
        priceTxt = new javax.swing.JTextField();
        quantityLbl = new javax.swing.JLabel();
        quantityTxt = new javax.swing.JTextField();
        addBtn = new javax.swing.JButton();
        clearAllBtn = new javax.swing.JButton();
        showAllBtn = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        productsTable = new javax.swing.JTable();
        addTransaction = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setForeground(new java.awt.Color(255, 0, 0));
        setUndecorated(true);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 48)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Products Page");

        productNameLbl.setFont(new java.awt.Font("Segoe UI Light", 0, 18)); // NOI18N
        productNameLbl.setText("Product Name");

        productNameTxt.setFont(new java.awt.Font("Segoe UI Semibold", 0, 24)); // NOI18N
        productNameTxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                productNameTxtActionPerformed(evt);
            }
        });

        priceLbl.setFont(new java.awt.Font("Segoe UI Light", 0, 18)); // NOI18N
        priceLbl.setText("Price");

        priceTxt.setFont(new java.awt.Font("Corbel", 0, 24)); // NOI18N
        priceTxt.setText("0.0");
        priceTxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                priceTxtActionPerformed(evt);
            }
        });

        quantityLbl.setFont(new java.awt.Font("Segoe UI Light", 0, 18)); // NOI18N
        quantityLbl.setText("Quantity");

        quantityTxt.setFont(new java.awt.Font("Corbel", 0, 24)); // NOI18N
        quantityTxt.setText("0");
        quantityTxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                quantityTxtActionPerformed(evt);
            }
        });

        addBtn.setBackground(new java.awt.Color(29, 94, 36));
        addBtn.setFont(new java.awt.Font("Segoe UI Semibold", 1, 12)); // NOI18N
        addBtn.setForeground(new java.awt.Color(255, 255, 255));
        addBtn.setText("Add Product");
        addBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addBtnActionPerformed(evt);
            }
        });

        clearAllBtn.setBackground(new java.awt.Color(189, 0, 22));
        clearAllBtn.setFont(new java.awt.Font("Segoe UI Semibold", 1, 12)); // NOI18N
        clearAllBtn.setForeground(new java.awt.Color(255, 255, 255));
        clearAllBtn.setText("Clear");
        clearAllBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearAllBtnActionPerformed(evt);
            }
        });

        showAllBtn.setBackground(new java.awt.Color(29, 94, 36));
        showAllBtn.setFont(new java.awt.Font("Segoe UI Semibold", 1, 12)); // NOI18N
        showAllBtn.setForeground(new java.awt.Color(255, 255, 255));
        showAllBtn.setText("Show All");
        showAllBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                showAllBtnActionPerformed(evt);
            }
        });

        productsTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "SNo.", "Product Name", "Price", "Quantity"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, true, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(productsTable);

        addTransaction.setBackground(new java.awt.Color(51, 51, 255));
        addTransaction.setText("Add Transaction");
        addTransaction.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addTransactionActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(layout.createSequentialGroup()
                            .addGap(21, 21, 21)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(priceLbl)
                                .addComponent(priceTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGap(43, 43, 43)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(quantityTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(quantityLbl)))
                        .addGroup(layout.createSequentialGroup()
                            .addGap(22, 22, 22)
                            .addComponent(addBtn)
                            .addGap(30, 30, 30)
                            .addComponent(showAllBtn)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(clearAllBtn)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(68, 68, 68)
                        .addComponent(addTransaction, javax.swing.GroupLayout.PREFERRED_SIZE, 214, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 127, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(57, 57, 57))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(22, 22, 22)
                    .addComponent(productNameLbl)
                    .addContainerGap(828, Short.MAX_VALUE)))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(22, 22, 22)
                    .addComponent(productNameTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(638, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(125, 125, 125)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(priceLbl)
                            .addComponent(quantityLbl))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(priceTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(quantityTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(21, 21, 21)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(addBtn)
                            .addComponent(clearAllBtn)
                            .addComponent(showAllBtn))
                        .addGap(33, 33, 33)
                        .addComponent(addTransaction, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 2, Short.MAX_VALUE))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(92, 92, 92)
                    .addComponent(productNameLbl)
                    .addContainerGap(383, Short.MAX_VALUE)))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(124, 124, 124)
                    .addComponent(productNameTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(334, Short.MAX_VALUE)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void quantityTxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_quantityTxtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_quantityTxtActionPerformed

    private void productNameTxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_productNameTxtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_productNameTxtActionPerformed

    private void priceTxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_priceTxtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_priceTxtActionPerformed

    private void addBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addBtnActionPerformed
        // TODO add your hpraandling code here:
        String pname = productNameTxt.getText();
        double price = 0.0;
        int qty = 0;
        
        try {
            price = Double.parseDouble(priceTxt.getText());
        }catch(NumberFormatException e){
            Helper.logger("addBtnActionPerformed", e);
            priceTxt.setText("");
        }
        
        try {
            qty = Integer.parseInt(quantityTxt.getText());
        }catch(NumberFormatException e){
            Helper.logger("addBtnActionPerformed", e);
            quantityTxt.setText("");
        }
        System.out.println("price " + priceTxt.getText());
        if(productNameTxt.getText().equals("") || priceTxt.getText().equals("") || quantityTxt.getText().equals("")){
            System.out.println("Some of the fields are empty. Insert query cannot run");
        }else{
            Product product = new Product(pname, price, qty);
            this.insertProduct(product);
        }


    }//GEN-LAST:event_addBtnActionPerformed

    private void clearAllBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearAllBtnActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_clearAllBtnActionPerformed

    private void showAllBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_showAllBtnActionPerformed
       // this.showDataToTable();
       loadData();

    }//GEN-LAST:event_showAllBtnActionPerformed

    private void addTransactionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addTransactionActionPerformed
        // TODO add your handling code here:
        new TransactionScreen().setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_addTransactionActionPerformed

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
            java.util.logging.Logger.getLogger(ProductsScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ProductsScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ProductsScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ProductsScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                // new ProductsScreen().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addBtn;
    private javax.swing.JButton addTransaction;
    private javax.swing.JButton clearAllBtn;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel priceLbl;
    private javax.swing.JTextField priceTxt;
    private javax.swing.JLabel productNameLbl;
    private javax.swing.JTextField productNameTxt;
    private javax.swing.JTable productsTable;
    private javax.swing.JLabel quantityLbl;
    private javax.swing.JTextField quantityTxt;
    private javax.swing.JButton showAllBtn;
    // End of variables declaration//GEN-END:variables
}
