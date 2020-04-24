import java.net.*;
import java.io.*;
import javax.swing.*;
 
/**
 * ChatClient is a class that stores all of the information of a chat client
 * and controls the Read Thread for receiving messages.
 */
public class ChatClient {
    private String hostname;
    private int port;
    private String userName;
    private JTextArea jTextArea1;
    private Socket socket;
 
    /**
     * Constructor: Takes an IP called hostname, port, and a reference to the 
     * chat GUI's JTextArea.  We take this text area in order to send a 
     * reference to the read thread.  This way,  the read thread can update the
     * text area when it receives messages from the server.
     */
    public ChatClient(String hostname, int port, JTextArea jTextArea1) {
        this.hostname = hostname;
        this.port = port;
        this.jTextArea1 = jTextArea1;
    }
    
    /**
     * Execute method: gets called in ChatClientGui whenever the chatclient 
     * object is created.  Attempts to create a socket with the hostname and 
     * port provided.  Then creates a readthread.  Returns a string message
     * based on the status so that the GUI text area can update the user.
     */
    public String execute() {
        try {
            socket = new Socket(hostname, port);            
            new ReadThread(socket, this, jTextArea1).start();
            return ("Connected to the chat server");
        } catch (UnknownHostException ex) {
            return ("Server not found: " + ex.getMessage());
        } catch (IOException ex) {
            return ("I/O Error: " + ex.getMessage());
        } catch(Exception ex){
            return ("Something went wrong: " + ex.getMessage());
        }
    }
    
    //Set the user Name
    void setUserName(String userName) {
        this.userName = userName;
    }
    
    //Return the user name
    String getUserName() {
        return this.userName;
    }
    
    //Return the socket, this is used by ChatClientGui in order to write to the
    
    Socket getServerSocket(){
        return socket;
    }
}
