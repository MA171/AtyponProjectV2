package System.Components;

import System.General.Commands;
import System.General.ProtocolMsg;
import System.Sockets.SocketServer;
import System.Sockets.SocketServerFactory;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.Buffer;

public class Splitter {

    private SocketServer server;
    private int numOfProcess;
    private BufferedReader in;

    public Splitter(SocketServer server, int numOfProcess, String reader) {
        if(reader == null || server == null || numOfProcess < 1)throw new IllegalArgumentException("Error in reader");
        this.server = server;
        this.numOfProcess = numOfProcess;
        try{
            this.in = new BufferedReader(new FileReader(reader));
        }
        catch (FileNotFoundException ex){
            System.out.println("Exception in Splitter ");
            ex.printStackTrace();
        }
    }

    public void start(){
        try{
            int i = 0;
            String line = null;
            if(SocketServerFactory.listenToMapStart() == 1){
                while((line = in.readLine()) != null){
                    if(line == "\n" || line == "" || line == "\t")continue;
                    server.sendObj(i,new DataMsg(line));
                    i++;
                    i %= numOfProcess;
                }
                server.announcement(new ProtocolMsg(Commands.getEndReading()));
            }
        }catch (IOException ex){
            System.out.println("Error in Splitter function : " + ex.fillInStackTrace());
        }
        catch (Exception e){
            System.out.println("General ex in Splitter : ");
            e.printStackTrace();
        }

    }

}
