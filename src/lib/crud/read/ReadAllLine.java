package lib.crud.read;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class ReadAllLine {
    public static ArrayList<String[]> read(String filePath) throws IOException {
        FileReader fr = new FileReader(filePath);
        BufferedReader br = new BufferedReader(fr);
        String currentLine;
        ArrayList<String[]> products = new ArrayList<String[]>();
        String[] data;

        while((currentLine = br.readLine()) != null){
            data = currentLine.split(",");
            products.add(data);
        }

        return products;
    }
}
