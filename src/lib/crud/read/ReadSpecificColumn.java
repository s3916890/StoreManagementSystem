package lib.crud.read;

//import java.io.BufferedReader;
//import java.io.FileReader;
//import java.io.IOException;
import java.util.ArrayList;
import java.io.*;

public class ReadSpecificColumn {
    public static String[] readSpecificColumn(int col, String filePath, String delimiter){
        String data[];
        String currentLine;
        ArrayList<String> colData = new ArrayList<String>();

        try{
            FileReader fileRead = new FileReader(filePath);
            BufferedReader bufferRead = new BufferedReader(fileRead);

            while((currentLine = bufferRead.readLine()) != null){
                data = currentLine.split(delimiter);
                colData.add(data[col]);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return colData.toArray(new String[0]);
    }
}
