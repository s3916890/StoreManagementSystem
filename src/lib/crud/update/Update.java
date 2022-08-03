package lib.crud.update;

import lib.crud.read.Read;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Update {

    private static Scanner x;

    public void updatePrice(String filePath, int productID, String data, String newData) throws IOException{
        Read line = new Read();
        ArrayList<String[]> Product = line.read(filePath);
        FileWriter csvFile = new FileWriter(filePath, true);

        for (int i = 0; i < Product.size(); i++) {
            String readID = Product.get(i)[0];
            String convertID = Integer.toString(productID);
            if (readID.equals(convertID)) {
                System.out.println("ID: " + Product.get(i)[0] + ", Category: " + Product.get(i)[1] + ", Name: " + Product.get(i)[2] + ", oldPrice: " + Product.get(i)[3]);
                System.out.println("ID: " + Product.get(i)[0] + ", Category: " + Product.get(i)[1] + ", Name: " + Product.get(i)[2] + ", newPrice: " + newData);
                break;
            }
        }
    }

    public void updateRecords(String filePath, int column, String data, String newData){

    }
}
