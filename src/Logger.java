import java.io.*;
import java.util.*;
import java.time.*;
import java.time.format.DateTimeFormatter;

public class Logger {
    String fileName;
    BufferedWriter writer;
    DateTimeFormatter dtFormat;
    LocalDateTime currentTime;

    public Logger(String fileName){
        this.fileName = fileName;
        try{
            writer = new BufferedWriter(new FileWriter(fileName, true));
            dtFormat = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        }catch(Exception e){
            System.out.println("Unable to begin logging");
        }
    }
    
    public void log(String toLog){
        
        try{
            writer.newLine();
            currentTime = LocalDateTime.now();
            writer.write("["+dtFormat.format(currentTime)+"]");
            writer.write(toLog);
            writer.flush();
        }catch(Exception e){
            System.out.println("Unable to log last message");
        }
    }
    
    public void close(){
        try{
            writer.close();
        }catch(Exception e){
            System.out.println("Could not close log");
        }
    }
}
