/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package store;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;

/**
 *
 * @author NNAMDI
 */
public class add_item extends javax.swing.JFrame {

    /**
     * Creates new form add_item
     */
   
    static Connection conn;
    static ResultSet rs;
    DateTimeFormatter dtf;
    String date_value;
    String item_name;
    String location;
    String current_stock;
    String add_to_stock;
    String new_stock_value;
    
// To add items to date_time_added table    
 public void item_added(String item_name,String date,String location,String number_added){
      
        try {
            //create a mysql connection
            create_mysql_connection();


            // Sql query
            String query = "insert into date_time_added ( item_name,date,location,number_added) values ?,?,?,? ";

            // Use prepared statement to set the ball roling
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setString(1, item_name);
            preparedStmt.setString(2, date);
            preparedStmt.setString(3, location);
            preparedStmt.setString(4, number_added);
            
             preparedStmt.execute();
       
            
            conn.close();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Sorry, a problem with date insert");
            
            System.err.println("Got an exception");
            System.err.println(e.getMessage());
            System.exit(0);
        }
 } 
 
    //To obtain values item_name enrolled in table store by the enroll module and store it in array item_name
 public static void obtain_item_name() {
        
     jLabel8.setText("N/A");
     jLabel6.setText("N/A");
     
     
        try {
            //create a mysql connection
            create_mysql_connection();


            // Sql query
            String query = "SELECT `item_name` FROM `store` WHERE 1 order by `item_name` ";

            // Use prepared statement to set the ball roling
            PreparedStatement preparedStmt = conn.prepareStatement(query);
           // preparedStmt.setString(1, invoice_number);
            rs = preparedStmt.executeQuery();
       
             
            jComboBox1.removeAllItems();
             while (rs.next()) { 
             jComboBox1.addItem(rs.getString("item_name"));
             }
            
          
            conn.close();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Sorry, a problem with the connection");
            System.exit(0);
            System.err.println("Got an exception");
            System.err.println(e.getMessage());
        }

    }
 
 //To update location and current stock when a new value is selected in item name
 public void update_location_and_current_stock(String item_name){
         try {
            //create a mysql connection
            create_mysql_connection();


            // Sql query
            String query = "SELECT `location`, `stock`  FROM `store` WHERE  `item_name` = ? ";

            // Use prepared statement to set the ball roling
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setString(1, item_name);
            rs = preparedStmt.executeQuery();
       
             
            
             while (rs.next()) { 
             jLabel8.setText(rs.getString("location"));
             jLabel6.setText(rs.getString("stock"));
             
             }
            
          
            conn.close();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Sorry, a problem with the selection process");
            System.exit(0);
            System.err.println("Got an exception");
            System.err.println(e.getMessage());
        }
 
 }
 
 
 //To obtain current date and time
 public void date_value(){
   dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
   LocalDateTime now = LocalDateTime.now();
   date_value = dtf.format(now);
   jLabel5.setText(date_value);
    
    
 }
 
 //To repopulate location and stock
 public void repopulate_location_and_stock(){
     
    jComboBox1.addActionListener(new ActionListener(){ 
       public void actionPerformed(ActionEvent event){
           JComboBox combobox = (JComboBox)event.getSource();
Object selected = combobox.getSelectedItem();

if(selected.toString().equals("Posmo"))
    System.out.println("Wilson");

  update_location_and_current_stock(jComboBox1.getSelectedItem().toString()); 
       }
   });
    
 }
 
//To obtain variables from the application in preperation for storage 
  public void obtain(){
    item_name = jComboBox1.getSelectedItem().toString();
    location = jLabel8.getText();
    current_stock = jLabel6.getText();
    add_to_stock = jTextField3.getText();
    
    try{
    int sum = Integer.parseInt(current_stock) + Integer.parseInt(add_to_stock);
    new_stock_value = String.valueOf(sum);}
    catch(Exception e){JOptionPane.showMessageDialog(null, "Enter an integer value in ' Add to Stock '");
 
    }
    
    }
  
// To update new value of stock in table store
  public void new_value_of_stock(String stock, String item_name){
           try {
            //create a mysql connection
            create_mysql_connection();


            // Sql query
           
            String query ="update store set stock = ? where item_name = ?";
            // Use prepared statement to set the ball roling
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setString(1, stock);
            preparedStmt.setString(2, item_name);
            
             preparedStmt.executeUpdate();
            int input = JOptionPane.showConfirmDialog(null, "Do you wish to add another item?");
            
            if(input == 0){
            //Yes
            //repopulate_location_and_stock();
            update_location_and_current_stock(jComboBox1.getSelectedItem().toString());
               dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
               LocalDateTime now = LocalDateTime.now();
               date_value = dtf.format(now);
               jLabel5.setText(date_value);
               jTextField3.setText(" ");
               
            }
            
            else if(input == 1){
            //No
            System.exit(0);
            }
            
            else if(input == 2){
            //Cancel
            jTextField3.setText(" ");
            }

            conn.close();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Sorry, a problem with the update process");
            System.exit(0);
            System.err.println("Got an exception");
            System.err.println(e.getMessage());
        }
  }
 

    
   // To initialize variables
    public void initialize(){
    obtain_item_name();
    date_value();
    repopulate_location_and_stock();
   
    

  
     
        
    }
    
    //To add information to date_time_added table
    public void date_time_addded(String item_names, String date_added, String location, String number_added){
    try {
            //create a mysql connection
            create_mysql_connection();
            

            // Sql query for removed table insert
            String query_removed_table = "INSERT INTO date_time_added(item_name ,date,location,number_added) "
                    + "values(?,?,?,?) ";
             // Use prepared statement to set the ball roling
            PreparedStatement  preparedStmt = conn.prepareStatement(query_removed_table);
            preparedStmt.setString(1, item_names);
            preparedStmt.setString(2, date_added);
            preparedStmt.setString(3, location);
            preparedStmt.setString(4, number_added);


            preparedStmt.execute();
             

            conn.close();
 
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Sorry, a problem with an inser process in add_item ");

            System.err.println("Got an exception");
            System.err.println(e.getMessage());
            System.exit(0);
        }

        
    }
    
    public add_item() {
        initComponents();
        initialize();
    }

    
    // Creates the connection to the database
    public static void create_mysql_connection() {
        try {
            //Select a jdbc driver which was added to the class path
            Class.forName("com.mysql.jdbc.Driver");

            //select the database - shop_floor
            String myUrl = "jdbc:mysql://localhost/store";

            //Create the connection
            conn = DriverManager.getConnection(myUrl, "root", "");

        } catch (Exception e) {
            System.err.println("Got an exception");
            System.err.println(e.getMessage());
        }

    }// End of create_mysql_connection()
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jTextField3 = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Add Item - Increase stock count");

        jPanel1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel1.setFont(new java.awt.Font("Comic Sans MS", 0, 14)); // NOI18N
        jLabel1.setText("Item Name:");

        jLabel2.setFont(new java.awt.Font("Comic Sans MS", 0, 14)); // NOI18N
        jLabel2.setText("Current Stock:");

        jLabel3.setFont(new java.awt.Font("Comic Sans MS", 0, 14)); // NOI18N
        jLabel3.setText("Location:");

        jButton1.setText("Add");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Comic Sans MS", 0, 14)); // NOI18N
        jLabel4.setText("Date:");

        jLabel5.setFont(new java.awt.Font("Comic Sans MS", 0, 14)); // NOI18N
        jLabel5.setText("Date/Time");

        jLabel6.setFont(new java.awt.Font("Comic Sans MS", 0, 14)); // NOI18N
        jLabel6.setText("10");

        jLabel7.setFont(new java.awt.Font("Comic Sans MS", 0, 14)); // NOI18N
        jLabel7.setText("Add to Stock:");

        jTextField3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField3ActionPerformed(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Comic Sans MS", 0, 14)); // NOI18N
        jLabel8.setText("Shelf A, Left, 10");

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addGap(406, 406, 406))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(25, 25, 25)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel8)
                            .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jButton1)
                        .addContainerGap())
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel7))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel4)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel5)
                                .addGap(28, 28, 28))))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jLabel8))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addGap(1, 1, 1)
                .addComponent(jButton1)
                .addContainerGap(26, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTextField3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField3ActionPerformed

   
    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed

        
            
    }//GEN-LAST:event_jComboBox1ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

obtain();
System.out.println(new_stock_value);
new_value_of_stock(new_stock_value, jComboBox1.getSelectedItem().toString());



date_time_addded(jComboBox1.getSelectedItem().toString(), date_value, jLabel8.getText(), jTextField3.getText());

        // TODO add your handling code here:
    }//GEN-LAST:event_jButton1ActionPerformed

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
            java.util.logging.Logger.getLogger(add_item.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(add_item.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(add_item.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(add_item.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new add_item().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    public static javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private static javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private static javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTextField jTextField3;
    // End of variables declaration//GEN-END:variables
}
