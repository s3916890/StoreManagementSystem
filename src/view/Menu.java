package view;

import controller.Account;
import controller.Customer;
import controller.Order;
import controller.Product;

import lib.CheckVisitStatus;
import lib.DateAndTime;
import lib.OptionInput;
import lib.algorithm.sort.ArrayListSorting;
import lib.crud.Read;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class Menu {
    private boolean cookies = false;
    public void view() throws IOException {
        System.out.println("\n================================================= WELCOME TO APPLE STORE MANAGEMENT SYSTEM !!! =================================================");
        System.out.println("""
                    1. Register
                    2. Login
                    3. List all products.
                    4. Search the available product.
                    5. Sort Products
                    6. Exit""");

        Menu menu = new Menu();
        String option = OptionInput.input();
        Account user = new Account();
        Product product = new Product();
        ArrayList<Long> prices = product.getAllPrice();


        switch (option) {
            case "1" -> {
                System.out.println("\n================================================= User Registration Form ==================================================");
                user.register(user.userNameRegisterInput(), user.passwordRegisterInput(), user.fullNameInput(), user.phoneNumberInput());
                System.out.println("Successfully Registering");
                menu.view();
            }
            case "2" -> {
                System.out.println("\n================================================= Member Login Form =================================================");
                this.cookies = false;

                String userName = user.userNameLoginInput();
                String password = user.passwordLoginInput();

                while(!user.verifyLogin(userName, password, "users.txt", ",")){
                    System.out.println("Wrong password, try again bro");
                    userName = user.userNameLoginInput();
                    password = user.passwordLoginInput();

                    if(user.verifyLogin(userName, password, "users.txt", ",")){
                        break;
                    }
                }

                this.cookies = true;

                String[] data = Read.getSpecificLine(user.getUserName(), 1, "users.txt", ",");
                System.out.println("\nUsername: " + data[1] + ", \sFull name: " + data[3] + ", \sPhone-number: " + data[5]);
                System.out.println("\nCookies (Login Status): "+ this.cookies);

                this.viewHomepage(userName);
            }
            case "3" -> {
                System.out.println("\n================================================= Available Products =================================================");
                Product products = new Product();
                products.view();
                CheckVisitStatus.checkVisitOrExit();
            }
            case "4" -> {
                System.out.println("\n================================================= Category Searching !!! =================================================");
                System.out.println("""
                    1. Laptop
                    2. Iphone
                    3. Watch
                    4. Airpods
                    5. Ipad
                    6. Desktop
                    7. Move back to the main menu
                    8. Exit""");
                Product availableProduct = new Product();

                availableProduct.viewUserSearchingResult();
                CheckVisitStatus.checkVisitOrExit();
            }
            case "5" -> {
                System.out.println("\n================================================= Sorting Option =================================================");
                System.out.println("""
                        1. Ascending
                        2. Descending
                        3. Move back to menu
                        4. Exit""");

                option = OptionInput.input();
                int priceASCLength, priceDSCLength;
                switch (option) {
                    case "1" -> {
                        ArrayList<Long> priceASC = ArrayListSorting.sortAscending(prices);
                        priceASCLength = priceASC.size();
                        for (int i = 0; i < priceASCLength; i++) {
                            String[] sortProductsASC = Read.getSpecificLine(Long.toString(priceASC.get(i)), 4, "products.txt", ",");
                            System.out.println("\nName: " + sortProductsASC[2] +
                                    ", Category: " + sortProductsASC[1] +
                                    ", Color: " + sortProductsASC[3] +
                                    ", Price: " + sortProductsASC[4] + " VND");
                        }
                        menu.view();
                    }
                    case "2" -> {
                        ArrayList<Long> priceDSC = ArrayListSorting.sortDescending(prices);
                        priceDSCLength = priceDSC.size();

                        for (int i = 0; i < priceDSCLength; i++) {
                            String[] sortProductsDSC = Read.getSpecificLine(Long.toString(priceDSC.get(i)), 4, "products.txt", ",");
                            System.out.println("\nName: " + sortProductsDSC[2] +
                                    ", Category: " + sortProductsDSC[1] +
                                    ", Color: " + sortProductsDSC[3] +
                                    ", Price: " + sortProductsDSC[4] + " VND");
                        }
                        menu.view();
                    }
                    case "3" -> {
                        menu.view();
                    }
                    case "4" -> {
                        System.exit(1);
                    }
                }
            }
            case "6" -> {
                System.out.println("Thank you so much for using our system. See you soon !!!!");
                System.exit(1);
            }
            default -> {
                System.out.println("There is no match option, please enter again");
                menu.view();
            }
        }
    }

    public void viewHomepage(String userName) throws IOException {
        System.out.println("\n================================================= Homepage  =================================================");

        System.out.println("""
                1. Create new order
                2. Search order
                3. List all products.
                4. Search the available product.
                5. Sort Product
                6. Log Out
                7. Exit""");

        Order order = new Order();
        String option = OptionInput.input();
        String[] obj = Read.getSpecificUserInfo(userName, 1, "users.txt", ",");
        String[] productInfo = Read.getSpecificLine("Macbook Pro 13 inch Retina 2015", 2, "products.txt", ",");
        Customer member = new Customer(Integer.parseInt(obj[0]), obj[1], obj[5]);
        Product product = new Product(Integer.parseInt(productInfo[0]), productInfo[1], productInfo[2], productInfo[3], Long.parseLong(productInfo[4]));;
        Menu menu = new Menu();

        switch (option) {
            case "1" -> {
                System.out.println("\n================================================= Available Category !!! =================================================");
                System.out.println("""
                    1. Laptop
                    2. Iphone
                    3. Watch
                    4. Airpods
                    5. Ipad
                    6. Desktop
                    7. Move back to the main menu
                    8. Exit""");
                option = OptionInput.input();
                ArrayList<String[]> productOptions = Read.readAllLine( "products.txt");
                switch (option){
                    case "1" -> {
                        System.out.println("\n================================================= Available Products !!! =================================================");
                        System.out.println("""
                                1. Category: laptop, Name: Macbook Pro 13 inch Retina 2015, Color: silver, Price: 17900000
                                2. Category: laptop, Name: Macbook Pro 16 inch Late 2013, Color: space gray, Price: 19900000
                                3. Category: laptop, Name: Macbook Pro 13 inch 2016, Color: gold, Price: 19900000
                                4. Category: laptop, Name: Macbook Air 13 inch Retina 2020, Color: rose gold, Price: 21900000
                                """);
                        option = OptionInput.input();

                        switch (option){
                            case "1" -> {
                                order.createNewOrder(member, new Product(Integer.parseInt(productOptions.get(0)[0]), productOptions.get(0)[1], productOptions.get(0)[2], productOptions.get(0)[3], Long.parseLong(productOptions.get(0)[4])));
                                System.out.println("\nPayment Successfully");
                                System.out.println("\nOrder Detailed Information");
                                System.out.println("\nName: " + productOptions.get(0)[2]);
                                System.out.println("Category: " + productOptions.get(0)[1]);
                                System.out.println("Price: " + productOptions.get(0)[4] + " VND");
                                System.out.println("Order time: " + new DateAndTime().getDateAndTime());
                                this.viewHomepage(userName);
                            }
                            case "2" -> {
                                order.createNewOrder(member, new Product(Integer.parseInt(productOptions.get(1)[0]), productOptions.get(1)[1], productOptions.get(1)[2], productOptions.get(1)[3], Long.parseLong(productOptions.get(1)[4])));
                                System.out.println("\nPayment Successfully");
                                System.out.println("\nOrder Detailed Information");
                                System.out.println("\nName: " + productOptions.get(1)[2]);
                                System.out.println("Category: " + productOptions.get(1)[1]);
                                System.out.println("Price: " + productOptions.get(1)[4] + " VND");
                                System.out.println("Order time: " + new DateAndTime().getDateAndTime());
                                this.viewHomepage(userName);
                            }
                            case "3" -> {
                                order.createNewOrder(member, new Product(Integer.parseInt(productOptions.get(2)[0]), productOptions.get(2)[1], productOptions.get(2)[2], productOptions.get(2)[3], Long.parseLong(productOptions.get(2)[4])));
                                System.out.println("\nPayment Successfully");
                                System.out.println("\nOrder Detailed Information");
                                System.out.println("\nName: " + productOptions.get(2)[2]);
                                System.out.println("Category: " + productOptions.get(2)[1]);
                                System.out.println("Price: " + productOptions.get(2)[4] + " VND");
                                System.out.println("Order time: " + new DateAndTime().getDateAndTime());
                                this.viewHomepage(userName);
                            }
                            case "4" -> {
                                order.createNewOrder(member, new Product(Integer.parseInt(productOptions.get(3)[0]), productOptions.get(3)[1], productOptions.get(3)[2], productOptions.get(3)[3], Long.parseLong(productOptions.get(3)[4])));
                                System.out.println("\nPayment Successfully");
                                System.out.println("\nOrder Detailed Information");
                                System.out.println("\nName: " + productOptions.get(3)[2]);
                                System.out.println("Category: " + productOptions.get(3)[1]);
                                System.out.println("Price: " + productOptions.get(3)[4] + " VND");
                                System.out.println("Order time: " + new DateAndTime().getDateAndTime());
                                this.viewHomepage(userName);
                            }
                        }
                    }
                    case "2" -> {
                        System.out.println("\n================================================= Available Products !!! =================================================");
                        System.out.println("""
                                1. Category: iPhone, Name: iPhone 13 mini 128 GBs, Color: pink, Price: 21900000
                                2. Category: iPhone, Name: iPhone 12 Pro Max 256 GBs, Color: sky blue, Price: 38999000
                                3. Category: iPhone, Name: iphone SE 64 GBs, Color: black, Price: 18999000
                                """);
                        option = OptionInput.input();

                        switch (option){
                            case "1" -> {
                                order.createNewOrder(member, new Product(Integer.parseInt(productOptions.get(4)[0]), productOptions.get(4)[1], productOptions.get(4)[2], productOptions.get(4)[3], Long.parseLong(productOptions.get(4)[4])));
                                System.out.println("\nPayment Successfully");
                                System.out.println("\nOrder Detailed Information");
                                System.out.println("\nName: " + productOptions.get(4)[2]);
                                System.out.println("Category: " + productOptions.get(4)[1]);
                                System.out.println("Price: " + productOptions.get(4)[4] + " VND");
                                System.out.println("Order time: " + new DateAndTime().getDateAndTime());
                                this.viewHomepage(userName);
                            }
                            case "2" -> {
                                order.createNewOrder(member, new Product(Integer.parseInt(productOptions.get(5)[0]), productOptions.get(5)[1], productOptions.get(5)[2], productOptions.get(5)[3], Long.parseLong(productOptions.get(5)[4])));
                                System.out.println("\nPayment Successfully");
                                System.out.println("\nOrder Detailed Information");
                                System.out.println("\nName: " + productOptions.get(5)[2]);
                                System.out.println("Category: " + productOptions.get(5)[1]);
                                System.out.println("Price: " + productOptions.get(5)[4] + " VND");
                                System.out.println("Order time: " + new DateAndTime().getDateAndTime());
                                this.viewHomepage(userName);
                            }
                            case "3" -> {
                                order.createNewOrder(member, new Product(Integer.parseInt(productOptions.get(6)[0]), productOptions.get(6)[1], productOptions.get(6)[2], productOptions.get(6)[3], Long.parseLong(productOptions.get(6)[4])));
                                System.out.println("\nPayment Successfully");
                                System.out.println("\nOrder Detailed Information");
                                System.out.println("\nName: " + productOptions.get(6)[2]);
                                System.out.println("Category: " + productOptions.get(6)[1]);
                                System.out.println("Price: " + productOptions.get(6)[4] + " VND");
                                System.out.println("Order time: " + new DateAndTime().getDateAndTime());
                                this.viewHomepage(userName);
                            }
                        }
                    }
                    case "3" -> {
                        System.out.println("\n================================================= Available Products !!! =================================================");
                        System.out.println("""
                                1. Category: watch, Name: Apple watch series 7, Color: midnight, Price: 8900000
                                2. Category: watch, Name: Apple watch series 3, Color: space gray, Price: 5900000
                                """);
                        option = OptionInput.input();

                        switch (option){
                            case "1" -> {
                                order.createNewOrder(member, new Product(Integer.parseInt(productOptions.get(7)[0]), productOptions.get(7)[1], productOptions.get(7)[2], productOptions.get(7)[3], Long.parseLong(productOptions.get(7)[4])));
                                System.out.println("\nPayment Successfully");
                                System.out.println("\nOrder Detailed Information");
                                System.out.println("\nName: " + productOptions.get(7)[2]);
                                System.out.println("Category: " + productOptions.get(7)[1]);
                                System.out.println("Price: " + productOptions.get(7)[4] + " VND");
                                System.out.println("Order time: " + new DateAndTime().getDateAndTime());
                                this.viewHomepage(userName);
                            }
                            case "2" -> {
                                order.createNewOrder(member, new Product(Integer.parseInt(productOptions.get(8)[0]), productOptions.get(8)[1], productOptions.get(8)[2], productOptions.get(8)[3], Long.parseLong(productOptions.get(8)[4])));
                                System.out.println("\nPayment Successfully");
                                System.out.println("\nOrder Detailed Information");
                                System.out.println("\nName: " + productOptions.get(8)[2]);
                                System.out.println("Category: " + productOptions.get(8)[1]);
                                System.out.println("Price: " + productOptions.get(8)[4] + " VND");
                                System.out.println("Order time: " + new DateAndTime().getDateAndTime());
                                this.viewHomepage(userName);
                            }
                        }
                    }
                    case "4" -> {
                        System.out.println("\n================================================= Available Products !!! =================================================");
                        System.out.println("""
                                1. Category: airpods, Name: airpods 2nd Generation, Color: white, Price: 3999000
                                2. Category: airpods, Name: airpods max, Color: green, Price: 5999000
                                3. Category: airpods, Name: airpods pro, Color: white, Price: 4900000
                                """);
                        option = OptionInput.input();
                        switch (option){
                            case "1" -> {
                                order.createNewOrder(member, new Product(Integer.parseInt(productOptions.get(9)[0]), productOptions.get(9)[1], productOptions.get(9)[2], productOptions.get(9)[3], Long.parseLong(productOptions.get(9)[4])));
                                System.out.println("\nPayment Successfully");
                                System.out.println("\nOrder Detailed Information");
                                System.out.println("\nName: " + productOptions.get(9)[2]);
                                System.out.println("Category: " + productOptions.get(9)[1]);
                                System.out.println("Price: " + productOptions.get(9)[4] + " VND");
                                System.out.println("Order time: " + new DateAndTime().getDateAndTime());
                                this.viewHomepage(userName);
                            }
                            case "2" -> {
                                order.createNewOrder(member, new Product(Integer.parseInt(productOptions.get(10)[0]), productOptions.get(10)[1], productOptions.get(10)[2], productOptions.get(10)[3], Long.parseLong(productOptions.get(10)[4])));
                                System.out.println("\nPayment Successfully");
                                System.out.println("\nOrder Detailed Information");
                                System.out.println("\nName: " + productOptions.get(10)[2]);
                                System.out.println("Category: " + productOptions.get(10)[1]);
                                System.out.println("Price: " + productOptions.get(10)[4] + " VND");
                                System.out.println("Order time: " + new DateAndTime().getDateAndTime());
                                this.viewHomepage(userName);
                            }
                            case "3" -> {
                                order.createNewOrder(member, new Product(Integer.parseInt(productOptions.get(11)[0]), productOptions.get(11)[1], productOptions.get(11)[2], productOptions.get(11)[3], Long.parseLong(productOptions.get(11)[4])));
                                System.out.println("\nPayment Successfully");
                                System.out.println("\nOrder Detailed Information");
                                System.out.println("\nName: " + productOptions.get(11)[2]);
                                System.out.println("Category: " + productOptions.get(11)[1]);
                                System.out.println("Price: " + productOptions.get(11)[4] + " VND");
                                System.out.println("Order time: " + new DateAndTime().getDateAndTime());
                                this.viewHomepage(userName);
                            }
                        }
                    }
                    case "5" -> {
                        System.out.println("\n================================================= Available Products !!! =================================================");
                        System.out.println("""
                                1. Category: iPad, Name: iPad Air 4 10 inch 2020 WiFi 256 GB, Color: pink, Price: 15999000
                                2. Category: iPad, Name: iPad Pro 2021 12 inch Liquid Retina XDR display M1 chip, Color: silver, Price: 41999000
                                3. Category: iPad, Name: iPad mini 6 WiFi 64 GB 2020, Color: starlight, Price: 21890000
                                """);
                        option = OptionInput.input();
                        switch (option){
                            case "1" -> {
                                order.createNewOrder(member, new Product(Integer.parseInt(productOptions.get(12)[0]), productOptions.get(12)[1], productOptions.get(12)[2], productOptions.get(12)[3], Long.parseLong(productOptions.get(12)[4])));
                                System.out.println("\nPayment Successfully");
                                System.out.println("\nOrder Detailed Information");
                                System.out.println("\nName: " + productOptions.get(12)[2]);
                                System.out.println("Category: " + productOptions.get(12)[1]);
                                System.out.println("Price: " + productOptions.get(12)[4] + " VND");
                                System.out.println("Order time: " + new DateAndTime().getDateAndTime());
                                this.viewHomepage(userName);
                            }
                            case "2" -> {
                                order.createNewOrder(member, new Product(Integer.parseInt(productOptions.get(13)[0]), productOptions.get(13)[1], productOptions.get(13)[2], productOptions.get(13)[3], Long.parseLong(productOptions.get(13)[4])));
                                System.out.println("\nPayment Successfully");
                                System.out.println("\nOrder Detailed Information");
                                System.out.println("\nName: " + productOptions.get(13)[2]);
                                System.out.println("Category: " + productOptions.get(13)[1]);
                                System.out.println("Price: " + productOptions.get(13)[4] + " VND");
                                System.out.println("Order time: " + new DateAndTime().getDateAndTime());
                                this.viewHomepage(userName);
                            }
                            case "3" -> {
                                order.createNewOrder(member, new Product(Integer.parseInt(productOptions.get(14)[0]), productOptions.get(14)[1], productOptions.get(14)[2], productOptions.get(14)[3], Long.parseLong(productOptions.get(14)[4])));
                                System.out.println("\nPayment Successfully");
                                System.out.println("\nOrder Detailed Information");
                                System.out.println("\nName: " + productOptions.get(14)[2]);
                                System.out.println("Category: " + productOptions.get(14)[1]);
                                System.out.println("Price: " + productOptions.get(14)[4] + " VND");
                                System.out.println("Order time: " + new DateAndTime().getDateAndTime());
                                this.viewHomepage(userName);
                            }
                        }
                    }
                    case "6" -> {
                        System.out.println("\n================================================= Available Products !!! =================================================");
                        System.out.println("""
                                1. Category: desktop, Name: Apple iMac 27 inch 2020 Retina Core i7 512GB SSD, Color: silver, Price: 56999000
                                2. Category: desktop, Name: Apple iMac 24 inch 2021 Retina M1 8 Core CPU 7 Core GPU 8GB 256GB SSD, Color: blue, Price: 56999000
                                """);

                        option = OptionInput.input();

                        switch (option){
                            case "1" -> {
                                order.createNewOrder(member, new Product(Integer.parseInt(productOptions.get(15)[0]), productOptions.get(15)[1], productOptions.get(15)[2], productOptions.get(15)[3], Long.parseLong(productOptions.get(15)[4])));
                                System.out.println("\nPayment Successfully");
                                System.out.println("\nOrder Detailed Information");
                                System.out.println("\nName: " + productOptions.get(15)[2]);
                                System.out.println("Category: " + productOptions.get(15)[1]);
                                System.out.println("Price: " + productOptions.get(15)[4] + " VND");
                                System.out.println("Order time: " + new DateAndTime().getDateAndTime());
                                this.viewHomepage(userName);
                            }
                            case "2" -> {
                                order.createNewOrder(member, new Product(Integer.parseInt(productOptions.get(15)[0]), productOptions.get(16)[1], productOptions.get(16)[2], productOptions.get(16)[3], Long.parseLong(productOptions.get(16)[4])));
                                System.out.println("\nPayment Successfully");
                                System.out.println("\nOrder Detailed Information");
                                System.out.println("\nName: " + productOptions.get(16)[2]);
                                System.out.println("Category: " + productOptions.get(16)[1]);
                                System.out.println("Price: " + productOptions.get(16)[4] + " VND");
                                System.out.println("Order time: " + new DateAndTime().getDateAndTime());
                                this.viewHomepage(userName);
                            }
                        }

                    }
                }
            }
            case "2" -> {
                order.searchOrder();
                this.viewHomepage(userName);
            }
            case "3" -> {
                product.view();
                this.viewHomepage(userName);
            }
            case "4" -> {
                System.out.println("\n================================================= Category Searching !!! =================================================");
                System.out.println("""
                        1. Laptop
                        2. Iphone
                        3. Watch
                        4. Airpods
                        5. Ipad
                        6. Desktop
                        7. Move back to the homepage
                        8. Exit""");
                Product availableProducts = new Product();
                availableProducts.viewMemberSearchingResult(userName);
                this.viewHomepage(userName);
            }
            case "5" -> {
                System.out.println("\n================================================= Sorting Option =================================================");
                System.out.println("""
                        1. Ascending
                        2. Descending
                        3. Log out
                        4. Exit""");

                option = OptionInput.input();
                ArrayList<Long> prices = product.getAllPrice();
                switch (option) {
                    case "1" -> {
                        ArrayList<Long> priceASC = ArrayListSorting.sortAscending(prices);

                        for (int i = 0; i < priceASC.size(); i++) {
                            String[] sortProductsASC = Read.getSpecificLine(Long.toString(priceASC.get(i)), 4, "products.txt", ",");
                            System.out.println("\nName: " + sortProductsASC[2] +
                                    ", Category: " + sortProductsASC[1] +
                                    ", Color: " + sortProductsASC[3] +
                                    ", Price: " + sortProductsASC[4] + " VND");
                        }
                        this.viewHomepage(userName);
                    }
                    case "2" -> {
                        ArrayList<Long> priceDSC = ArrayListSorting.sortDescending(prices);

                        for(int i = 0; i < priceDSC.size(); i++) {
                            String[] sortProductsDSC = Read.getSpecificLine(Long.toString(priceDSC.get(i)), 4, "products.txt", ",");
                            System.out.println("\nName: " + sortProductsDSC[2] +
                                    ", Category: " + sortProductsDSC[1] +
                                    ", Color: " + sortProductsDSC[3] +
                                    ", Price: " + sortProductsDSC[4] + " VND");
                        }
                        this.viewHomepage(userName);
                    }
                    case "3" -> {
                        menu.view();
                    }
                    case "4" -> {
                        System.exit(1);
                    }
                }
            }
            case "6" -> {
                this.cookies = false;
                // Reset the order DB
                File file = new File("orders.txt");
                PrintWriter writer = new PrintWriter(file.getName());
                writer.write("");
                writer.flush();
                writer.close();
                menu.view();
            }
            case "7" -> {
                System.exit(1);
            }
        }
    }
}
