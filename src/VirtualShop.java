import java.util.*;

public class VirtualShop {
    private final List<User> users;
    private final List<Product> products;

    public VirtualShop() {

        users = new LinkedList<>();
        products = new LinkedList<>();
        products.add(new Product(150,"Cheese", 3));
        products.add(new Product(250,"Popcorn", 5));
        products.add(new Product(500,"Honey", 1));
        products.add(new Product(1500,"Shampoo", 10));
        Product p = new Product(130000,"Snack", 36);
        p.setInStock(false);
        products.add(p);
        products.add(new Product(666,"Red Rose", 4));
        run();
    }

    private void run() {
        Options choice = null;
        Scanner intScanner = new Scanner(System.in);
        do {
            System.out.println("Choose an option: ");
            System.out.println("-1- Register");
            System.out.println("-2- Log in");
            System.out.println("-3- Exit");
            try {
                choice = Options.valueOf(intScanner.nextInt());

                if (choice != null) {
                    switch (choice) {
                        case OP_1 -> createUser();
                        case OP_2 -> signIn();
                        case OP_3 -> System.out.println("Exit");
                    }
                }
                else {
                    System.out.println("\n" + "Invalid option (only 1-3 allowed)\n");
                }

            } catch (InputMismatchException e) {
                System.out.println("Enter numbers\n");
                intScanner.nextLine();
            }
        } while (choice != Options.OP_3);
    }

    private void createUser() {
        String userName, password, firstName, lastName;
        Scanner lnScanner = new Scanner(System.in);
        Scanner intScanner = new Scanner(System.in);
        Options userRank = null;
        userName = getCorrectUsername();
        password = getCorrectPassword();
        firstName = getCorrectName(true);
        lastName = getCorrectName(false);


        System.out.println("Are you a worker? y/n");
        String userAnswer = lnScanner.nextLine();

        if (choseYesOption(userAnswer)) {
            Worker workerUser = new Worker(firstName, lastName, userName, password);
            do {
                System.out.println("What is your rank?");
                System.out.println("1.Worker \n2.Manager \n3.Staff \n");

                try {
                    userRank = Options.valueOf(intScanner.nextInt());

                    if (userRank != null) {
                        switch (userRank) {
                            case OP_1 -> workerUser.setRank(Options.OP_1);
                            case OP_2 -> workerUser.setRank(Options.OP_2);
                            case OP_3 -> workerUser.setRank(Options.OP_3);
                        }
                    }
                    else {
                        System.out.println("Invalid option (only 1-3 allowed)\n");
                    }

                } catch (InputMismatchException e) {
                    System.out.println("Enter only numbers\n");
                    intScanner.nextLine();
                }
            } while (!(userRank == Options.OP_3 || userRank == Options.OP_2 || userRank == Options.OP_1));

            users.add(workerUser);

        }
        else {
            Customer customerUser = new Customer(firstName, lastName, userName, password);
            System.out.println("Are you club member? Y/N");
            userAnswer = lnScanner.nextLine();
            customerUser.setClubMember(choseYesOption(userAnswer));
            users.add(customerUser);
        }

    }
    private String getCorrectUsername() {
        boolean exists;
        String userName;
        Scanner lnScanner = new Scanner(System.in);
        do {
            System.out.println("Enter username: ");
            userName = lnScanner.nextLine();
            exists = false;
            for (User user: users) {
                if (user != null) {
                    if (user.getUserName().equals(userName)) {
                        exists = true;
                        break;
                    }
                }
            }
            if (exists) {
                System.out.println("This username already exists, please chose another username.");
            }
        } while (exists);

        return userName;
    }

    private String getCorrectPassword() {
        String password;
        int currentPasswordLen, allowedPasswordLen = 5;
        boolean allowed;
        Scanner lnScanner = new Scanner(System.in);
        do {
            System.out.println("Enter password (min 6 chars): ");
            password = lnScanner.nextLine();
            currentPasswordLen = password.length();

            allowed = currentPasswordLen > allowedPasswordLen;
            if (!allowed) {
                System.out.println("Password too short enter at least 6 numbers");
            }

        } while (!allowed);

        return password;
    }

    private String getCorrectName(boolean isFirstName) {
        Scanner lnScanner = new Scanner(System.in);
        String name;
        boolean correct;
        do {
            correct=true;

            if (isFirstName) {
                System.out.println("Enter the first name: ");
            }
            else {
                System.out.println("Enter the last name: ");
            }

            name = lnScanner.nextLine();

            for (int i = 0; i < name.length(); i++) {
                char nameCharacter = name.charAt(i);
                if (nameCharacter >= 48 && nameCharacter <= 57) {
                    System.out.println("Name cannot contain numbers!");
                    correct = false;
                    break;
                }
            }
        } while (!correct);

        return name;

    }

    private void signIn() {
        Scanner lnScanner = new Scanner(System.in);
        String userAns, userName, password;
        boolean isWorker;
        int succeeded;


        System.out.println("Are you  worker? Y/N");
        userAns = lnScanner.nextLine();
        isWorker = choseYesOption(userAns);

        System.out.println("Enter username: ");
        userName = lnScanner.nextLine();
        System.out.println("Enter password: ");
        password = lnScanner.nextLine();

        succeeded=findUser(isWorker, userName, password);
        if (succeeded < 0) {
            System.out.println("Wrong username or password..");
        }
        else {
            openUserMenu(isWorker, succeeded);
        }
    }

    private void openUserMenu(boolean isWorker, int userIndex) {
        User currentUser = users.get(userIndex);
        if (isWorker) {
            String rankMessage = "";
            Options workerRank = ((Worker) currentUser).getRank();

            switch (workerRank) {
                case OP_1 -> rankMessage = " (Worker)!";
                case OP_2 -> rankMessage = " (Manager)!";
                case OP_3 -> rankMessage = " (Staff)!";
            }

            System.out.println("Hello " + currentUser + rankMessage);
            WorkerMenuOptions workerChoice = null;

            do{
                try {
                    Scanner intScanner = new Scanner(System.in);
                    System.out.println("\nChoose an option: ");
                    System.out.println("""
                            1 -- Print list of all customers\s
                            2 -- Print list of all club members\s
                            3 -- Print list of all buyers (bought once at least)\s
                            4 -- Print the highest customer payment\s
                            5 -- Add product \s
                            6 -- Change product status\s
                            7 -- Buy products\s
                            8 -- Exit\s
                            """);
                    workerChoice = WorkerMenuOptions.valueOf(intScanner.nextInt());
                    if (workerChoice != null) {
                        switch (workerChoice) {
                            case PRINT_ALL_CUSTOMERS -> printCustomOrClubMemberOrBuyers(Options.OP_1);
                            case PRINT_ALL_CLUB_MEMBERS -> printCustomOrClubMemberOrBuyers(Options.OP_2);
                            case PRINT_ALL_BUYERS -> printCustomOrClubMemberOrBuyers(Options.OP_3);
                            case PRINT_MAX_BUYER -> printMaxBuyer();
                            case ADD_PRODUCT -> addToProductsList();
                            case CHANGE_PRODUCT_STATUS -> changeProductStatus();
                            case BUY_PRODUCT -> buyProduct(currentUser, true);

                        }

                    } else {
                        System.out.println("Invalid option try again");
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Only choose numbers");
                }

            } while (workerChoice != WorkerMenuOptions.EXIT);

        }
        else {
            System.out.print("Hello ");
            System.out.println(((Customer) currentUser).isClubMember() ? (currentUser + " (VIP)!") : (currentUser + "!"));
            buyProduct(currentUser, false);
        }

    }

    private int findUser(boolean isWorker, String userName, String password) {
        String className;
        if(isWorker) {
            className = "Worker";

        }
        else {
            className = "Customer";
        }
        int index = -1;

        for (int i = 0; i < users.size(); i++) {
            User currentUser = users.get(i);
            if (currentUser != null) {
                if (className.equals(currentUser.getClass().getTypeName())) {
                    if (userName.equals(currentUser.getUserName()) && password.equals(currentUser.getPassword())) {
                        index = i;
                        break;
                    }
                }
            }
        }
        return index;
    }

    private boolean choseYesOption(String input) {
        return input.equals("Y") || input.equals("y") || input.equals("yes");
    }

    private void buyProduct(User currentUser, boolean isWorker) {

        if (isWorker) {
            setWorkerDiscountPrice(((Worker) currentUser).getRank());
            }
        else {
            setCustomerDiscountPrice();
        }

        int userChoice;
        Scanner intScanner = new Scanner(System.in);
        System.out.println("Enter the number of products you want to buy (-1 to exit): ");
        do {
            userChoice = -1;
            try {
                int i=0, j=1;
                Product currentProduct;
                Map<Integer, Integer> inStockProdIndexes = new HashMap<>();
                while (i < products.size()) {
                        currentProduct = products.get(i);
                        inStockProdIndexes.put(j, i);
                        if (currentProduct != null && currentProduct.isInStock()) {
                            System.out.println(j + ". " + currentProduct);
                            j++;
                        }
                        i++;
                    }

                userChoice = intScanner.nextInt();
                if (userChoice < j && userChoice >= 1) {
                     currentUser.addToBasket(products.get(inStockProdIndexes.get(userChoice)));
                     if (isWorker) {
                         currentUser.printCart(Options.OP_1);
                     } else {

                         if (((Customer) currentUser).isClubMember()) {
                             currentUser.printCart(Options.OP_2);
                         }
                         else {
                             currentUser.printCart(Options.OP_3);
                         }
                     }
                }


            } catch (InputMismatchException e) {
                System.out.println("Error");
            }

        } while ((userChoice != -1));

        double spended = currentUser.getBasket().getTotalBasketPrice();
        System.out.println("Total cart price: " + spended +"$\n");
        if (spended > 0) {
            currentUser.setBought(true);
            currentUser.setSpent(spended+currentUser.getSpent());
            int purchaseCounter = currentUser.getBoughtCounter();
            purchaseCounter++;
            currentUser.setBoughtCounter(purchaseCounter);
           // System.out.println("Total spended " + currentUser.getSpended());
        }
        currentUser.getBasket().getInCart().clear();
    }

    private void setWorkerDiscountPrice(Options rank) {
        float discount = switch (rank) {
            case OP_1 -> 10;
            case OP_2 -> 20;
            case OP_3 -> 30;
        };

        for (Product product : products) {
            if (product != null) {
                double productPrice = product.getPrice();
                product.setDiscountPrice(Math.ceil(productPrice - (discount/100)*productPrice));
            }
        }
    }

    private void setCustomerDiscountPrice() {
        for (Product product : products) {
            if (product != null) {
                double productPrice = product.getPrice();
                product.setDiscountPrice(Math.ceil(productPrice - (product.getDiscount()/100)*productPrice));
            }
        }
    }

    private void printCustomOrClubMemberOrBuyers(Options options){
        for (User currentCustomer : users) {
            if (currentCustomer != null) {
                boolean isCustomer = currentCustomer.getClass().getTypeName().equals("Customer");
                if (isCustomer) {
                    Customer customerRef = (Customer) currentCustomer;
                    switch (options) {
                        case OP_1:
                            printCustomerData(customerRef);
                            break;
                        case OP_2:
                            if (customerRef.isClubMember()) {
                                printCustomerData(customerRef);
                            }
                            break;
                        case OP_3:
                            if (customerRef.isBought()) {
                                printCustomerData(customerRef);
                            }
                            break;
                    }
                }
            }
        }
    }

    void addToProductsList(){
        Scanner scanner = new Scanner(System.in);
        String aboutProduct;
        double price, discount;
        boolean inProductList = false;

        System.out.println("Enter product description: ");
        aboutProduct = scanner.nextLine();
        try {
            System.out.println("Enter product price: ");
            price = scanner.nextDouble();
            System.out.println("Enter product discount: ");
            discount = scanner.nextDouble();

            if (discount < 100 && discount >=0 && price > 0) {

                Product newProduct = new Product(price, aboutProduct, discount);

                for (Product currentProduct : products) {
                    if (currentProduct.equals(newProduct)) {
                        inProductList = true;
                        break;
                    }
                }

                if (!inProductList) {
                    products.add(newProduct);
                }
            }
            else {
                System.out.println("Illegal price or discount percentage");
            }

        } catch (InputMismatchException e) {
            System.out.println("Input error");
            scanner.nextLine();
        }

    }

    void changeProductStatus(){
        Scanner intScanner = new Scanner(System.in);
        Scanner lnScanner = new Scanner(System.in);
        String inStockDescription;
        int productIndex = -1;
        do {
            int i=0;
            try {
                while (i < products.size()) {
                    System.out.println(i + ". " + products.get(i));
                    i++;
                }
                System.out.println("Enter product index");
                productIndex = intScanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Input error!!");
            }
        } while (!(productIndex >= 0 && productIndex < products.size()));

        System.out.println("Is product in stock? Y/N");
        inStockDescription = lnScanner.nextLine();
        Product product = products.get(productIndex);
        product.setInStock(choseYesOption(inStockDescription));

        System.out.println("Product now" + (product.isInStock() ? " " : " not ") + "in stock.");
    }

    private void printMaxBuyer() {
        users.sort(Collections.reverseOrder());
        for (User customer : users) {
            if (customer != null) {
                if (customer.getClass().getTypeName().equals("Customer")) {
                    printCustomerData((Customer) customer);
                    break;
                }
            }
        }
    }

    private void printCustomerData(Customer customer) {
        System.out.println("User name: " + customer.getUserName() + ", name: " +
                           customer + ", total spent: " + customer.getSpent() +
                           ", total purchases: " + customer.getBoughtCounter() +
                           ", is club member: " + customer.isClubMember());

    }
}
