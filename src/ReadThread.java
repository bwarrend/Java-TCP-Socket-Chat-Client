import java.io.*;
import java.net.*;
import javax.swing.*;
 
/**
 * Creates a thread for reading messages from the server without stopping the 
 * application all together.  
 */
public class ReadThread extends Thread {
    private BufferedReader reader;
    private Socket socket;
    private ChatClient client;
    private JTextArea jTextArea1;
    private Logger log;
    
    /**
     * Constructor: Take a socket, the current ChatClient, and try to create a
     * buffered reader for reading server messages
     */
    public ReadThread(Socket socket, ChatClient client, JTextArea jTextArea1, Logger log) {
        this.socket = socket;
        this.client = client;
        this.jTextArea1 = jTextArea1;
        this.log = log;
        try {
            InputStream input = socket.getInputStream();
            reader = new BufferedReader(new InputStreamReader(input));
        } catch (IOException ex) {
            jTextArea1.setText(jTextArea1.getText() 
                    + "\nError getting input stream: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
 
    /**
     * As a Thread, run will be called whenever start() is called on the Thread
     */
    public void run() {
        while (true) {
            try {
                String response = reader.readLine();
                jTextArea1.setText(jTextArea1.getText() + "\n" + response);
                log.log(response);
                
            } catch (IOException ex) {
                jTextArea1.setText(jTextArea1.getText() 
                        + "\nError reading from server: " + ex.getMessage());
                ex.printStackTrace();
                break;
            }
        }
    }
}