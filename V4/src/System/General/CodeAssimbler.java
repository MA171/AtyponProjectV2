package System.General;

import java.io.*;

public class CodeAssimbler {

    public static void Mapper(String function) {
        read(function,"Mapper");
    }

    public static void Reducer(String function){
        read(function,"Reducer");
    }

    private static void read(String function, String file){
        try{
            BufferedReader reader = new BufferedReader(new FileReader("src/System/"+file+".txt"));
            BufferedWriter writer = new BufferedWriter(new FileWriter("src/"+file+".java"));
            String line;
            while((line = reader.readLine()) != null){
                //System.out.println(line);
                if(line.equals("run();")) writer.write(function+"\n");
                else writer.write(line+"\n");
            }
            writer.flush();
            writer.close();
            reader.close();
        }
        catch (IOException ex){
            System.out.println("Error in Cone Assembler");
        }
    }

}
