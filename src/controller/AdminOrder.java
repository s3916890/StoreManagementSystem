package controller;

import lib.crud.Read;
import lib.crud.Write;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;

public class AdminOrder {
    public void updateOrderStatus(int position, String filePath, String newStatus) throws IOException {
        ArrayList<String[]> database = Read.readAllLine("ordersHistory.txt");
        System.out.println(database.get(position - 1)[7]);
        System.out.println(newStatus);
        database.get(position - 1)[7] = String.valueOf(newStatus);

        File file = new File(filePath);
        PrintWriter pw = new PrintWriter(file);

        pw.write("");
        pw.close();

        ArrayList<String[]> newDatabase = database;

        for(String[] obj : newDatabase){
            Write.write(filePath, "ORDER_ID,CUSTOMER_ID, PRODUCT_ID,MEMBERSHIP,TOTAL_PAYMENT,TIMESTAMP,ORDER_STATUS,DELIVERY_STATUS", String.join(",", obj));
        }
    }
}
