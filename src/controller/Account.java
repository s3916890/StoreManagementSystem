package controller;

import lib.crud.Read;
import lib.DateAndTime;

import view.AdminMenu;
import view.Menu;

import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Account{

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

    private int id = 1;

    public Account() {

    }

    /** This method is to view the register form after receiving the result from model*/
    public void register(String userName, String password, String fullName, String phoneNumber) throws IOException {
        FileWriter csvFile = new FileWriter("users.txt", true);
        int lines = 0;
        BufferedReader reader = new BufferedReader(new FileReader("users.txt"));

        while (reader.readLine() != null){
            if(lines == 0){
                lines++;
                id = lines;
            }
            else{
                ++id;
            }
            lines++;
        }

        int totalSpending = 0;

        String registerTime = DateAndTime.getDateAndTime();

        StringBuilder data = new StringBuilder("");
        data.append(Integer.toString(id))
                .append(",")
                .append(userName)
                .append(",")
                .append(password)
                .append( ",")
                .append(fullName)
                .append(",")
                .append(totalSpending)
                .append(",")
                .append(phoneNumber)
                .append(",")
                .append(registerTime)
                .append(",")
                .append("MEMBER");
        String obj = data.toString();
        reader.close();

        try {
            /*
             * @param userName: to check the duplicate userName because userName is only unique */
            ArrayList<String> readUserNames = new ArrayList<>();
            ArrayList<String> db = new ArrayList<>();

            db.add(obj);

            String[] userNameData = Read.readSpecificColumn(1, "users.txt", ",");
            int listUserNameSize =  userNameData.length;

            for(int i = 0; i < listUserNameSize; i++){
                readUserNames.add(userNameData[i]);
            }

            int dbLength = db.size();


            for(int i = 0; i < dbLength; i++){
                // Check the duplicated of userName
                if(!readUserNames.contains(userName)){
                    csvFile.append(String.valueOf(db.get(i)));
                    csvFile.append("\n");
                }
            }
            csvFile.close();
        }catch (Exception e){
            e.getStackTrace();
        }

        this.id = 0;
    }

    public boolean verifyLogin(String userName, String password, String filePath, String delimiter){
        String hashing = this.hashing(password);
        String currentLine;
        String data[];
        try{
            FileReader fr = new FileReader(filePath);
            BufferedReader br = new BufferedReader(fr);

            while((currentLine = br.readLine()) != null){
                data = currentLine.split(delimiter);
                if(data[1].equals(userName) && data[2].equals(hashing)){
                    return true;
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    public String userNameRegisterInput() throws IOException {
        Scanner sc = new Scanner(System.in);

        System.out.print("Username: ");
        String userName = sc.nextLine();

        ArrayList<String> userNameDB = this.getAllUserName();
        while(!this.validateUserName(userName) || userNameDB.contains(userName)){
            System.out.println("This username is already exist or invalid username, try again !!!!");
            System.out.print("Username: ");
            userName = sc.nextLine();
        }

        return userName;
    }

    public String userNameLoginInput() throws IOException, InterruptedException {
        Menu menu = new Menu();
        Scanner sc = new Scanner(System.in);
        System.out.print("Username: ");
        String userName = sc.nextLine();

        ArrayList<String> userNameDB = this.getAllUserName();

        while(!this.validateUserName(userName) || !userNameDB.contains(userName)){
            System.out.println("The username does not exist, please sign up !!!!");
            menu.view();
        }

        this.setUserName(userName);

        return userName;
    }

    public String passwordRegisterInput(){
        Scanner sc = new Scanner(System.in);
        System.out.print("Password (Must include 1 number, 1 uppercase, 1 lowercase, 8 to 20 character): ");
        String password = sc.nextLine();

        while(!this.validatePassword(password)) {
            System.out.println("Invalid password, try again !!!!");
            System.out.print("Password: ");
            password = sc.nextLine();
        }
        return this.hashing(password);
    }

    public String passwordLoginInput(){
        Scanner sc = new Scanner(System.in);
        System.out.print("Password (Must include 1 number, 1 uppercase, 1 lowercase, 8 to 20 character): ");
        String password = sc.nextLine();
        this.setPassword(password);
        return password;
    }

    public String hashing(String data){
        try{
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(data.getBytes());
            byte[] resultByteArr = messageDigest.digest();
            StringBuilder sb = new StringBuilder();

            for(byte b: resultByteArr){
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        }catch (NoSuchAlgorithmException e){
            e.printStackTrace();
        }
        return "";
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


    public ArrayList<String> getAllUserName() throws IOException {
        File file = new File("users.txt");
        if(!file.exists()){
            file.createNewFile();
            this.appendAttributesTitleToFile();
        }
        ArrayList<String> checkUserName = new ArrayList<>();
        String[] readUserName = Read.readSpecificColumn(1, file.getName(), ",");

        if(file.length() == 0) {
            return new ArrayList<String>();
        }

        for(int i = 0; i < readUserName.length; i++){
            checkUserName.add(readUserName[i]);
        }

        return checkUserName;
    }

    public void appendAttributesTitleToFile() throws IOException {
        File file = new File("users.txt");
        String attributes = "ID,USERNAME,PASSWORD,FULL_NAME,INITIAL_SPENDING,PHONE_NUMBER,REGISTER_TIME, INITIAL_MEMBERSHIP";
        FileWriter csvFile = new FileWriter(file.getName(), true);
        BufferedReader reader = new BufferedReader(new FileReader(file.getName()));

        if(reader.readLine() == null){
            csvFile.append(attributes);
            csvFile.append("\n");
        }
        reader.close();
        csvFile.close();
    }

    public ArrayList<String> getAllFullName() throws IOException {
        String[] readFullName = Read.readSpecificColumn(3, "users.txt", ",");
        ArrayList<String> checkFullName = new ArrayList<>();

        if(readFullName.length == 0)
            return new ArrayList<String>();

        for(int i = 0; i < readFullName.length; i++){
            checkFullName.add(readFullName[i]);
        }

        return checkFullName;
    }

    public ArrayList<String> getAllPhoneNumber() throws IOException {
        String[] readPhoneNumber = Read.readSpecificColumn(4, "users.txt", ",");
        ArrayList<String> checkPhoneNumber = new ArrayList<>();

        if(readPhoneNumber.length == 0)
            return new ArrayList<String>();

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
                "[a-zA-Z0-9]+( +[a-zA-Z0-9]+)*.{8,20}$";

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

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}

