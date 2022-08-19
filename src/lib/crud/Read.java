package lib.crud;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class Read {
    public static ArrayList<String[]> readAllLine(String filePath) throws IOException {
        File file = new File(filePath);
        if(!file.exists()){
            file.createNewFile();
        }
        FileReader fr = new FileReader(filePath);
        BufferedReader br = new BufferedReader(fr);
        String currentLine;
        ArrayList<String[]> products = new ArrayList<String[]>();
        String[] data;


        if(br.readLine() == null){
            return new ArrayList<String[]>();
        }

        while((currentLine = br.readLine()) != null){
            data = currentLine.split(",");
            products.add(data);
        }


        return products;
    }

    public static String[] readSpecificColumn(int col, String filePath, String delimiter) throws IOException {
        File file = new File(filePath);
        if(!file.exists()){
            file.createNewFile();
        }
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

        colData.remove(0);

        return colData.toArray(new String[0]);
    }

    public static String[] getSpecificLine(String fetch, int column, String filePath, String delimiter) throws IOException {
        File file = new File(filePath);
        if(!file.exists()){
            file.createNewFile();
        }
        String currentLine;
        String[] data;
        try {
            FileReader fr = new FileReader(filePath);
            BufferedReader br = new BufferedReader(fr);

            while ((currentLine = br.readLine()) != null) {
                data = currentLine.split(delimiter);
                if(data[column].equals(fetch)){
                    return data;
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return new String[0];
    }

    public static String[] getSpecificUserInfo(String fetch, int column, String filePath, String delimiter) throws IOException {
        File file = new File(filePath);
        if(!file.exists()){
            file.createNewFile();
        }
        String currentLine;
        String[] data;
        try {
            FileReader fr = new FileReader(filePath);
            BufferedReader br = new BufferedReader(fr);

            while ((currentLine = br.readLine()) != null) {
                data = currentLine.split(delimiter);
                if(data[column].equals(fetch)){
                    return data;
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return new String[0];
    }
}
