package view;

import lib.OptionInput;
import lib.crud.CreateTable;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Authentication {
    public void view() throws IOException, InterruptedException {
        Menu menu = new Menu();
        AdminMenu adminMenu = new AdminMenu();
        System.out.println("\n====================================================== Administrator or Customer option? ======================================================");
        System.out.println("1. Customer");
        System.out.println("2. Admin");
        System.out.println("3. Exit");
        String option = OptionInput.input();

        switch (option) {
            case "1" -> menu.view();
            case "2" -> adminMenu.view();
            case "3" -> {
                System.out.println("Thank you so much for using our system. See you soon !!!!");
                System.out.println("""
                        \nCOSC2081 GROUP ASSIGNMENT\s
                        STORE ORDER MANAGEMENT SYSTEM\s
                        Instructor: Mr. Minh Vu\s
                        Group: Group Name\s                           
                        """);
                CreateTable.setShowVerticalLines(true);
                CreateTable.setHeaders("STUDENT_ID", "NAME");
                CreateTable.addRow("s3916890", "Nguyen Phuc Loi");
                CreateTable.addRow("s3938101", "Duong Tran My Linh");
                CreateTable.addRow("s3836606", "Dang Hoang Anh Khoa");
                CreateTable.addRow("s3927120", "Vu Quoc Gia Quan");

                CreateTable.render();

                CreateTable.setHeaders(new String[0]);
                CreateTable.setRows(new ArrayList<String[]>());

                System.exit(1);
            }
            default -> {
                System.out.println("Sorry, there is no match option. Please enter again");
                this.view();
            }

        }
    }
}
