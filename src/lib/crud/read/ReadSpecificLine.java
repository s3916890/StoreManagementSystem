package lib.crud.read;

import java.io.BufferedReader;
import java.io.FileReader;

public class ReadSpecificLine {
    public static String[] getSpecificLine(String fetch, int column, String filePath, String delimiter) {
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
