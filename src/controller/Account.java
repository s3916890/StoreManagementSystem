package controller;

import lib.counting.Counting;
import lib.crud.read.ReadSpecificColumn;
import lib.hashing.Hashing;
import lib.time.DateAndTime;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Account {
    public enum MembershipCategories{
        Silver,
        Gold,
        Platinum;
    }
    private String userName;
    private String password;
    private String fullName;
    private String phoneNumber;

    public Account(String userName, String password, String fullName, String phoneNumber) {
        this.userName = userName;
        this.password = password;
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
    }

    private static int id = 0;

    public Account() {

    }

    /** This method is to view the register form after receiving the result from model*/
    public void register(String userName, String password, String fullName, String phoneNumber) throws IOException {
        Account account = new Account(userName, password, fullName, phoneNumber);
        System.out.println(account.getUserName());
        System.out.println(account.getPassword());
        System.out.println(account.getFullName());
        System.out.println(account.getPhoneNumber());

//        System.out.println("===================================================================== User Registration Form =====================================================================");
//        app.model.register.Model user = new app.model.register.Model();
//        user.register();
        BufferedReader reader = new BufferedReader(new FileReader("users.txt"));
        int lines = 0;
        while (reader.readLine() != null){
            if(lines == 0){
                Counting.increment(id);
                lines++;
            }
            else{
                Counting.increment(id);
            }
            lines++;
        }
        reader.close();


        this.write();

//        this.write();
    }

    public void write(){
        try {
            /*
             * @param userName: to check the duplicate userName because userName is only unique */
            ArrayList<String> readUserNames = new ArrayList<>();
            ArrayList<String> db = new ArrayList<>();
            FileWriter csvFile = new FileWriter("users.txt", true);

            db.add(this.toString());

            String[] data = ReadSpecificColumn.readSpecificColumn(1, "users.txt", ",");

            for(int i = 0; i < data.length; i++){
                readUserNames.add(data[i]);
            }
            for(int i = 0; i < db.size(); i++){
                // Check the duplicated of userName
                if(!userName.contains(this.getUserName())){
                    csvFile.append(String.valueOf(db.get(i)));
                    csvFile.append("\n");
                }
            }
            csvFile.close();
        }catch (Exception e){
            e.getStackTrace();
        }
    }

    public String userNameInput(){
        ArrayList<String> userNameDB = this.getAllUserName();
        Scanner sc = new Scanner(System.in);
        System.out.print("Username: ");
        String userName = sc.nextLine();

        while(!this.validateUserName(userName) || userNameDB.contains(userName)){
            System.out.println("This username is already exist or invalid username, try again !!!!");
            System.out.print("Username: ");
            userName = sc.nextLine();
        }
        return userName;
    }

    public String passwordInput(){
        Scanner sc = new Scanner(System.in);
        System.out.print("Password: ");
        String password = sc.nextLine();
        String hashingPassword = Hashing.hashing(password);
        System.out.println(hashingPassword);

        while(!this.validatePassword(password)){
            System.out.println("Invalid password, try again !!!!");
            System.out.print("Password: ");
            password = sc.nextLine();
        }
        return hashingPassword;
    }

    public String fullNameInput(){
        Scanner sc = new Scanner(System.in);
        System.out.print("Full name: ");
        String fullName = sc.nextLine();

        while(!this.validateFullName(fullName)){
            System.out.println("Invalid full name, try again !!!!");
            System.out.print("Full name: ");
            fullName = sc.nextLine();
        }
        return fullName;
    }

    public String phoneNumberInput(){
        Scanner sc = new Scanner(System.in);
        System.out.print("Phone-number: ");
        String phoneNumber = sc.nextLine();

        while(!this.validatePhoneNumber(phoneNumber)){
            System.out.println("Invalid phone-number, try again !!!!");
            System.out.print("Phone-number: ");
            phoneNumber = sc.nextLine();
        }
        return phoneNumber;
    }

    public static ArrayList<String> getAllUserName(){
        String[] readUserName = ReadSpecificColumn.readSpecificColumn(1, "users.txt", ",");
        ArrayList<String> checkUserName = new ArrayList<>();

        for(int i = 0; i < readUserName.length; i++){
            checkUserName.add(readUserName[i]);
        }

        return checkUserName;
    }

    public static ArrayList<String> getAllFullName(){
        String[] readFullName = ReadSpecificColumn.readSpecificColumn(3, "users.txt", ",");
        ArrayList<String> checkFullName = new ArrayList<>();

        for(int i = 0; i < readFullName.length; i++){
            checkFullName.add(readFullName[i]);
        }

        return checkFullName;
    }

    public static ArrayList<String> getAllPhoneNumber(){
        String[] readPhoneNumber = ReadSpecificColumn.readSpecificColumn(4, "users.txt", ",");
        ArrayList<String> checkPhoneNumber = new ArrayList<>();

        for(int i = 0; i < readPhoneNumber.length; i++){
            checkPhoneNumber.add(readPhoneNumber[i]);
        }

        return checkPhoneNumber;
    }

    public boolean validateUserName(String username) {
        String CONFIG_RULE =
                "^[a-zA-Z0-9]([._-](?![._-])|[a-zA-Z0-9]){3,18}[a-zA-Z0-9]$";
        Pattern pattern = Pattern.compile(CONFIG_RULE);
        Matcher matcher = pattern.matcher(username);
        return matcher.matches();
    }

    public boolean validatePassword(String password) {
        String CONFIG_RULE =
                "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,20}$";
        Pattern pattern = Pattern.compile(CONFIG_RULE);
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }

    public boolean validateFullName(String fullName) {
        String CONFIG_RULE =
                "^[A-Z][a-zA-Z]{3,}(?: [A-Z][a-zA-Z]*){0,2}$";
        Pattern pattern = Pattern.compile(CONFIG_RULE);
        Matcher matcher = pattern.matcher(fullName);
        return matcher.matches();
    }

    public boolean validatePhoneNumber(final String phoneNumber) {
        String CONFIG_RULE =
                "\\d{10}|(?:\\d{3}-){2}\\d{4}|\\(\\d{3}\\)\\d{3}-?\\d{4}";
        Pattern pattern = Pattern.compile(CONFIG_RULE);
        Matcher matcher = pattern.matcher(phoneNumber);
        return matcher.matches();
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return this.password;
    }

    public String getFullName() {
        return fullName;
    }

    public String getPhoneNumber() {return phoneNumber;}

    @Override
    public String toString() {
        return  ++id + "," +
                this.getUserName() + "," +
                this.getPassword() + "," +
                this.getFullName() + "," +
                this.getPhoneNumber() + "," +
                new DateAndTime().getDateAndTime();
    }
}
