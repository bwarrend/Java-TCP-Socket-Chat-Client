import java.io.*;
import java.net.*;

//This class holds the main method and sets up the gui for the chat client.
public class ChatClientGui extends javax.swing.JFrame {
    String ipAddress;
    int port;
    ChatClient client;
    
    String userName;
    Socket socket;
    PrintWriter writer;
    
    /**
     * Constructor: initComponents method is called which initialized all
     * components of the GUI as we created them in the design view.  Because
     * this constructor is one of the first things called, we can set our IP,
     * port, and user name to null or other such invalid values
     */
    public ChatClientGui() {
        initComponents();        
        ipAddress = null;
        port = -1;
        userName = null;
        
        /**
         * The main text area will display awaiting connection until a 
         * connection has been established.  The label at the bottom will give
         * the user directions as to how to connect to a server.
         */
        jTextArea1.setText("Awaiting connection...");
        jLabel1.setText("Enter IP address of server:");
        this.getRootPane().setDefaultButton(jButton1);
        
    }

    //This is all auto generated code by the design feature
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jButton1 = new javax.swing.JButton();
        jTextField1 = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Chat Client");

        jPanel1.setBackground(new java.awt.Color(51, 51, 51));

        jTextArea1.setEditable(false);
        jTextArea1.setBackground(new java.awt.Color(51, 51, 51));
        jTextArea1.setColumns(20);
        jTextArea1.setForeground(new java.awt.Color(255, 255, 255));
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        jButton1.setText("Send");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jTextField1.setBackground(new java.awt.Color(51, 51, 51));
        jTextField1.setForeground(new java.awt.Color(255, 255, 255));
        jTextField1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField1KeyReleased(evt);
            }
        });

        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("XXXXXXXX");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 775, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, 111, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 288, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 497, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton1)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * This method sets up a listener for the "Send" button of the GUI.  It 
     * works in stages.  Something different will happen when the send button is
     * pressed until we are fully connected to a server and sending messages.
     * 1. Get an IP Address
     * 2. Get a Port
     * 3. Get a username
     * 4. Start sending messages 
     */
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        //1. Get an IP address and then prompt for a port using the bottom label
        if(ipAddress == null){
            try{
                ipAddress = jTextField1.getText();
                jLabel1.setText("Enter port of server:");
                jTextField1.setText("");
            }catch(Exception e){
                jLabel1.setText("Invalid IP Address - Enter IP address of server: ");
                jTextField1.setText("");
                ipAddress = null;
            }
        }
        /**
         * 2. Get a Port and try to make a connection. If connected, prompt for 
         * a username.  If a valid integer is not entered for a port, throw an
         * exception.  If a connected cannot be made, throw an exception.
         */
        else if(port == -1){
            try{
                //Grab port from text field and create an instance of ChatClient
                port = Integer.parseInt(jTextField1.getText());
                client = new ChatClient(ipAddress, port, jTextArea1);                
                String status = client.execute();
                /**
                 * Tell the user if the connection was established, if not we
                 * can start the process over.
                 */
                if(status.startsWith("Connected")){
                    jTextField1.setText("");
                    jLabel1.setText("Connected... Enter a user name:");
                    socket = client.getServerSocket();
                    jTextField1.setText("");
                }else{
                    ipAddress = null;
                    port = -1;
                    jLabel1.setText(status + "\nEnter IP address of server:");
                }
            }catch(Exception e){
                jTextArea1.setText("Invalid Port.\nEnter port of server...");
                jTextField1.setText("");
                port = -1; 
            }            
        }
        /**
         * 3. Get username after a connection has been established. Update the
         * label to show the username and server ip:port
         */
        else if(userName == null){
            userName = jTextField1.getText();
            jTextField1.setText("");            
            jLabel1.setText("Connected as \"" + userName + "\" at " 
                    + ipAddress + ": " + port);
            try {
                //Create printwriter for sending messages to server and send
                //the username which the server is expecting first
                OutputStream output = socket.getOutputStream();
                writer = new PrintWriter(output, true);
                writer.println(userName);
            } catch (IOException ex) {
                jTextArea1.setText(jTextArea1.getText()
                        + "Cannot get output stream: " + ex.getMessage());
                ex.printStackTrace();
            }
        }
        /**
         * 4. Start sending messages.  Now every time the "Send" button is 
         * pressed, the contents of the text field are sent to the server and 
         * are also displayed to the text area.  Once 'bye' has been sent, 
         * we exit the client.  
         */
        else{
            String text;
            text = jTextField1.getText();
            writer.println(text);
            if(text.equalsIgnoreCase("bye")) System.exit(0);            
            jTextArea1.setText(jTextArea1.getText() + "\n"+userName+": "+text);
            jTextField1.setText("");
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jTextField1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField1KeyReleased
        
    }//GEN-LAST:event_jTextField1KeyReleased

    /**
     * Main: create an instance of ChatClientGui and set it to visible
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
            java.util.logging.Logger.getLogger(ChatClientGui.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ChatClientGui.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ChatClientGui.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ChatClientGui.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ChatClientGui().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables
}