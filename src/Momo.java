import java.util.*;
import java.util.stream.Collectors;

public class Momo {
    public static Scanner sc = new Scanner(System.in);
    public static void main(String[] args) {
        double winRate = 0.1;
        double budget = 50; // machine budget
        String option;

        /* this variable is to check whether user want to stay current day or next day
         will have three value: "c" for currentDay, "n" for next day, "q" for quit using this program */
        String currentDay = "c";

        // check whether user wants to choose next day or continue on the current day.
        while (currentDay.equalsIgnoreCase("n") || currentDay.equalsIgnoreCase("c")){
            System.out.println(" momo 3: " + budget);
            List<Product> prods = new ArrayList<>();

            // we only check the remaining budget if the user choose to continue the current day.
            if (currentDay.equalsIgnoreCase("n")){
                if (budget > 0){
                    winRate = winRate + 0.5 * 0.1;
                }
                budget = 50;
            }
            System.out.println("A. 10.000\tB. 20.000\tC.50.000\tD.100.000\tE.200.000");
            System.out.print("Choose money: ");
            option = sc.nextLine();
            double money = selectMoney(option);
            if (money == 0) {
                System.out.println("Goodbye!");
                return;
            }
            chooseProduct(prods, money);

            List<String> prodNames =  prods.stream().map(Product::getName).collect(Collectors.toList());

            String result = checkAwardCondition(prodNames);
            budget = playRandom(winRate, budget, prods, result);
            System.out.print("Next day? (n for next day, q for quit, c for continue current day): ");
            option = sc.nextLine();
            currentDay = option.toLowerCase();
        }

    }

    private static void chooseProduct(List<Product> prods, double money) {
        String option;
        do {
            System.out.println("A: Coke\tB: Pepsi\tC: Soda");
            System.out.print("Choose product: ");
            option = sc.nextLine();
            Product product = selectProduct(option);
            if (product == null) {
                break;
            }
            if (money - product.getCost() < 0){
                System.out.println("Sorry your budget is not enough to continue. Goodbye!");
                break;
            }
            prods.add(product);
            money = money - product.getCost();
            System.out.print("Money remaining: ");
            System.out.println(money);
            System.out.println();

            prods.forEach(p -> {
                System.out.println(p.getName());
            });

            System.out.println();
            System.out.println("Do you want to continue? (q to quit, any key to continue)");
            option = sc.nextLine();

        } while (!option.equalsIgnoreCase("q"));

        System.out.println("Your change: " + money);
    }

    private static double playRandom(double winRate, double budget, List<Product> prods, String result) {
        if (result != null){
            Random rand = new Random();

            double randNum = rand.nextDouble();
            if (randNum <= winRate){
                Product product = findProductByName(result, prods);
                if (product != null){
                    budget -= product.getCost();
                    prods.add(product);
                    System.out.println("Congrats! You've won a " + product.getName());
                } else {
                    System.out.println("Sorry. The machine is out of order. ");
                }
            }
        }
        return budget;
    }


    private static Product findProductByName (String prodName, List<Product> products){
        for (Product product: products){
            if (product.getName().equals(prodName)){
                return product;
            }
        }
        return null;
    }

    private static String checkAwardCondition (List<String> prodNames){
        String [] names = prodNames.toArray(new String[0]);
        int count = 1;

        for (int i = 0; i < names.length - 1; i++){
            if (names[i + 1].equals(names[i])){
                count++;
            } else {
                count = 1;
            }
            if (count == 3){
                return names[i];
            }
        }
        return null;
    }

    private static Product selectProduct (String option){
        option = option.toUpperCase();
        Product product = null;
        switch (option){
            case "A":
                product = new Product("Coke", 10);
                break;
            case "B":
                product = new Product("Pepsi", 10);
                break;
            case "C":
                product = new Product("Soda", 20);
                break;
            default:
                System.out.println("Not valid. Goodbye!");
                break;
        }
        return product;
    }

    private static double selectMoney (String option){
        option = option.toUpperCase();
        switch (option){
            case "A":
                return 10;
            case "B":
               return 20;
            case "C":
                return 50;
            case "D":
                return 100;
            case "E":
                return 200;
            default:
                System.out.println("Not valid.");
                return 0;
        }
    }
}
