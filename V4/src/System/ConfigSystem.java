package System;

import System.General.CheckUtils;
import System.IO.IOReport;
import System.IO.IOWrite;

public class ConfigSystem {

    private String input,output;
    private int numOfMappers,numOfReducers;
    private long startTime,startMemory,endTime,endMemory;
    private IOReport report;


    public ConfigSystem(String input,String output,int numOfMaps,int numOfReduce){
        this.input = input;
        this.output = output;
        this.numOfMappers = numOfMaps;
        this.numOfReducers = numOfReduce;
    }

    public void startSystem(){
       IOWrite.setOutput(output);
        report = new IOReport("Logs");
        report.addMessage("Start Time : " + CheckUtils.getTimeNow());
        report.addMessage("Number of Mappers : " + numOfMappers);
        report.addMessage("Number of Reducers : " + numOfReducers);
        startTime = System.currentTimeMillis();
        startMemory = Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory();
    }

    public void endSystem(){
        long finishTime = System.currentTimeMillis();
        long finishMemory = Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory();

        double takenTime = (finishTime-startTime+0.0)/1000;
        double takenMemory = ((int)((finishMemory-startMemory)/(1024*1024)*3))/3;

        report.addMessage("Finish Time : " + CheckUtils.getTimeNow());
        report.addMessage("Taken time is : " + takenTime + "s");
        report.addMessage("Taken Memory is : " + takenMemory + "MB");
        report.flush();

    }

}
