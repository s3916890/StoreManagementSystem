package lib.crud;

import java.io.*;
import java.util.ArrayList;

public class Write {
    private static int id = 1;

    public static void write(String filePath, String delimiter, String attributes, String obj) throws IOException {
        File file = new File(filePath);
        FileWriter csvFile = new FileWriter(file, true);
        int lines = 0;

        BufferedReader reader = new BufferedReader(new FileReader(filePath));

        if(reader.readLine() == null){
            csvFile.append(attributes);
            csvFile.append("\n");
            lines++;
        }

        while (reader.readLine() != null){
            if(lines == 1){
                lines++;
                id = lines;
            }
            else{
                id++;
            }
            lines++;
        }
        reader.close();

        try {
            /*
             * @param userName: to check the duplicate userName because userName is only unique */
            ArrayList<String> db = new ArrayList<>();

            db.add(obj);

            for (String s : db) {
                csvFile.append(String.valueOf(s));
                csvFile.append("\n");
            }
            csvFile.close();
        }catch (Exception e){
            e.getStackTrace();
        }
    }
}
