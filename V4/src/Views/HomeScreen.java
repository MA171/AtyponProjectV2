package Views;

import System.General.CheckUtils;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import System.MapReduceSystem;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class HomeScreen implements Screen {

    private Label maperFunLbl,reducerFunLbl,numOfMaperLbl,numOfReducerLbl,status,inputLbl,outputLbl;
    private TextField numOfMappers,numOfReducers,inputFile,outputFile;
    private TextArea reducerFunction,mapperFunction;
    private Button startBtn,selectInput,selectOutput;
    private Scene scene;

    public HomeScreen(){

        maperFunLbl = new Label("Mapper function");
        reducerFunLbl = new Label("Reducer function");
        startBtn = new Button("Start");


        mapperFunction = new TextArea("    private static void run(){\n" +
                "        Pattern pattern = Pattern.compile(\"[a-zA-Z]+\");\n" +
                "        Matcher matcher;\n" +
                "        while(!dataQ.isEmpty()){\n" +
                "            String line = dataQ.poll();\n" +
                "            matcher = pattern.matcher(line);\n" +
                "            while(matcher.find()){\n" +
                "                String word = matcher.group();\n" +
                "                add(word);\n" +
                "            }\n" +
                "        }\n" +
                "    }\n");


        reducerFunction = new TextArea("    public static void run(){\n" +
                "        while(!dataQ.isEmpty()){\n" +
                "            KeyVal<String,Integer> line = dataQ.poll();\n" +
                "            add(line);\n" +
                "        }\n" +
                "    }\n");


        numOfMaperLbl = new Label("Number of mappers");
        numOfReducerLbl = new Label("Number of mappers");
        numOfMappers = new TextField("2");
        numOfReducers = new TextField("2");
        selectInput = new Button("Input");
        selectOutput = new Button("Output");
        inputFile = new TextField();
        outputFile = new TextField();
        inputLbl = new Label("Input");
        outputLbl = new Label("Output");

        selectInput.setOnAction(event -> selectInput());
        selectOutput.setOnAction(event -> selectOutput());
        inputLbl.setMinWidth(50);
        outputLbl.setMinWidth(50);

        VBox MapperAll = new VBox(maperFunLbl,mapperFunction);
        VBox ReducerAll = new VBox(reducerFunLbl,reducerFunction);
        HBox functions = new HBox(MapperAll,ReducerAll);
        HBox input = new HBox(inputLbl,inputFile,selectInput);
        HBox output = new HBox(outputLbl,outputFile,selectOutput);
        HBox inputOutput = new HBox(input,output);

        mapperFunction.setPrefWidth(1000);
        reducerFunction.setPrefWidth(1000);
        mapperFunction.setPrefHeight(1000);
        reducerFunction.setPrefHeight(1000);

        numOfReducers.setPrefWidth(1000);
        numOfMappers.setPrefWidth(1000);
        functions.setSpacing(10);

        input.setSpacing(10);
        output.setSpacing(10);
        inputOutput.setSpacing(20);
        inputOutput.setPadding(new Insets(0,0,20,0));
        inputFile.setPrefWidth(1000);
        outputFile.setPrefWidth(1000);


        VBox numMaperAll = new VBox(numOfMaperLbl,numOfMappers);
        VBox numReducerAll = new VBox(numOfReducerLbl,numOfReducers);

        numMaperAll.setPadding(new Insets(10));
        numReducerAll.setPadding(new Insets(10));

        HBox nums = new HBox(numMaperAll,numReducerAll);
        startBtn.setMaxWidth(200);
        startBtn.setMinHeight(50);
        startBtn.setPadding(new Insets(15));
        startBtn.setBorder(Border.EMPTY);

        status = new Label("IDEL");
        status.setPadding(new Insets(10));
        status.setTextFill(Color.GREEN);
        status.setFont(Font.font(18));
        status.setTextAlignment(TextAlignment.RIGHT);
        inputFile.setDisable(true);
        outputFile.setDisable(true);

        VBox root = new VBox(inputOutput,functions, nums,startBtn,status);

        root.setPadding(new Insets(10));

        scene = new Scene(root,1200,700);

        startBtn.setOnAction(event -> startBtnFunction());

    }

    public Scene getScreen(){
        return scene;
    }

    private void startBtnFunction(){
        status.setText("Working");
        status.setTextFill(Color.BLUE);
        if(
                numOfReducers.getText() == null || !CheckUtils.isNumric(numOfReducers.getText()) ||
                numOfMappers.getText() == null || !CheckUtils.isNumric(numOfMappers.getText())
        ){
            status.setText("Error , Enter Correct number of reducers and mappers");
            status.setTextFill(Color.RED);
        }
        else if(inputFile.getText().isEmpty()|| outputFile.getText().isEmpty()){
            status.setText("Error , Select Input file and output directory");
            status.setTextFill(Color.RED);
        }
        else{
            int reducers = Integer.parseInt(numOfReducers.getText());
            int mappers = Integer.parseInt(numOfMappers.getText());
            startBtn.setDisable(true);
            MapReduceSystem system = new MapReduceSystem(mappers,reducers);

            system.setInput(inputFile.getText());
            system.setOutput(outputFile.getText());
            system.setMapper(mapperFunction.getText());
            system.setReducer(reducerFunction.getText());

            system.start();
            status.setText("Operation Done Successfully");
            startBtn.setDisable(false);
        }

    }

    private void selectInput(){
        FileChooser input = new FileChooser();

        FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter(
                "TXT files (*.txt)", "*.txt");
        input.getExtensionFilters().add(filter);
        File selectedFile = input.showOpenDialog(null);
        if(selectedFile != null){
            inputFile.setText(selectedFile.getPath());
        }


    }

    private void selectOutput(){
        DirectoryChooser input = new DirectoryChooser();

        File selectedFile = input.showDialog(null);
        if(selectedFile != null){
            outputFile.setText(selectedFile.getPath());
        }

    }

}
