import java.util.*;

public class Main {

    static Scanner scanner = new Scanner(System.in);

    // Stock names and prices
    static String[] stockNames = {
            "TCS",
            "INFY",
            "RELIANCE",
            "HDFC",
            "ITC"
    };

    static double[] stockPrices = {
            3800.00,
            1650.00,
            2900.00,
            1750.00,
            520.00
    };

    // Portfolio
    static int[] sharesOwned = new int[stockNames.length];

    // Starting money
    static double balance = 100000.00;

    // Transaction history
    static ArrayList<String> transactions = new ArrayList<>();

    public static void main(String[] args) {

        boolean running = true;

        System.out.println("========================================");
        System.out.println("       STOCK TRADING PLATFORM");
        System.out.println("========================================");
        System.out.println("Starting Balance: Rs" + balance);

        while (running) {

            System.out.println("\n--------------- MENU ----------------");
            System.out.println("1. View Stocks");
            System.out.println("2. Buy Stock");
            System.out.println("3. Sell Stock");
            System.out.println("4. View Portfolio");
            System.out.println("5. Transaction History");
            System.out.println("6. Update Market Prices");
            System.out.println("7. Exit");
            System.out.println("-------------------------------------");

            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();

            switch (choice) {

                case 1:
                    viewStocks();
                    break;

                case 2:
                    buyStock();
                    break;

                case 3:
                    sellStock();
                    break;

                case 4:
                    viewPortfolio();
                    break;

                case 5:
                    viewTransactions();
                    break;

                case 6:
                    updateMarketPrices();
                    break;

                case 7:
                    running = false;
                    System.out.println("\nThank you for using the Stock Trading Platform!");
                    break;

                default:
                    System.out.println("Invalid choice. Please select 1-7.");
            }
        }

        scanner.close();
    }

    // Display available stocks
    static void viewStocks() {

        System.out.println("\n========== AVAILABLE STOCKS ==========");

        for (int i = 0; i < stockNames.length; i++) {
            System.out.printf(
                    "%d. %-10s Rs.%.2f%n",
                    i + 1,
                    stockNames[i],
                    stockPrices[i]
            );
        }
    }

    // Buy stock
    static void buyStock() {

        viewStocks();

        System.out.print("\nEnter stock number: ");
        int stockNumber = scanner.nextInt();

        if (stockNumber < 1 || stockNumber > stockNames.length) {
            System.out.println("Invalid stock number.");
            return;
        }

        int index = stockNumber - 1;

        System.out.print("Enter quantity to buy: ");
        int quantity = scanner.nextInt();

        if (quantity <= 0) {
            System.out.println("Quantity must be greater than 0.");
            return;
        }

        double totalCost = stockPrices[index] * quantity;

        if (totalCost > balance) {
            System.out.println("Insufficient balance.");
            System.out.println("Required: Rs" + totalCost);
            System.out.println("Available: Rs" + balance);
            return;
        }

        balance -= totalCost;
        sharesOwned[index] += quantity;

        String transaction =
                "BOUGHT " + quantity + " shares of "
                        + stockNames[index]
                        + " at Rs" + stockPrices[index];

        transactions.add(transaction);

        System.out.println("\nPurchase successful!");
        System.out.println("Stock: " + stockNames[index]);
        System.out.println("Quantity: " + quantity);
        System.out.println("Total cost: Rs" + totalCost);
        System.out.println("Remaining balance: Rs" + balance);
    }

    // Sell stock
    static void sellStock() {

        viewPortfolio();

        System.out.print("\nEnter stock number to sell: ");
        int stockNumber = scanner.nextInt();

        if (stockNumber < 1 || stockNumber > stockNames.length) {
            System.out.println("Invalid stock number.");
            return;
        }

        int index = stockNumber - 1;

        if (sharesOwned[index] == 0) {
            System.out.println("You do not own this stock.");
            return;
        }

        System.out.print("Enter quantity to sell: ");
        int quantity = scanner.nextInt();

        if (quantity <= 0) {
            System.out.println("Quantity must be greater than 0.");
            return;
        }

        if (quantity > sharesOwned[index]) {
            System.out.println("You don't own enough shares.");
            System.out.println("You own: " + sharesOwned[index]);
            return;
        }

        double saleValue = stockPrices[index] * quantity;

        balance += saleValue;
        sharesOwned[index] -= quantity;

        String transaction =
                "SOLD " + quantity + " shares of "
                        + stockNames[index]
                        + " at Rs" + stockPrices[index];

        transactions.add(transaction);

        System.out.println("\nSale successful!");
        System.out.println("Stock: " + stockNames[index]);
        System.out.println("Quantity: " + quantity);
        System.out.println("Amount received: Rs" + saleValue);
        System.out.println("Current balance: Rs" + balance);
    }

    // Display portfolio
    static void viewPortfolio() {

        System.out.println("\n========== MY PORTFOLIO ==========");

        boolean hasStocks = false;
        double portfolioValue = 0;

        for (int i = 0; i < stockNames.length; i++) {

            if (sharesOwned[i] > 0) {

                hasStocks = true;

                double value =
                        sharesOwned[i] * stockPrices[i];

                portfolioValue += value;

                System.out.printf(
                        "%-10s | Shares: %-5d | Current Value: ₹%.2f%n",
                        stockNames[i],
                        sharesOwned[i],
                        value
                );
            }
        }

        if (!hasStocks) {
            System.out.println("No stocks in portfolio.");
        }

        System.out.println("----------------------------------");
        System.out.printf("Cash Balance: Rs.%.2f%n", balance);
        System.out.printf("Stock Value : Rs.%.2f%n", portfolioValue);
        System.out.printf(
                "Total Value : Rs.%.2f%n",
                balance + portfolioValue
        );
    }

    // Transaction history
    static void viewTransactions() {

        System.out.println("\n======= TRANSACTION HISTORY =======");

        if (transactions.isEmpty()) {
            System.out.println("No transactions yet.");
            return;
        }

        for (int i = 0; i < transactions.size(); i++) {
            System.out.println((i + 1) + ". " + transactions.get(i));
        }
    }

    // Simulate market price changes
    static void updateMarketPrices() {

        Random random = new Random();

        System.out.println("\n======= MARKET UPDATE =======");

        for (int i = 0; i < stockPrices.length; i++) {

            double oldPrice = stockPrices[i];

            // Random change between -5% and +5%
            double change =
                    (random.nextDouble() * 0.10) - 0.05;

            stockPrices[i] =
                    stockPrices[i] * (1 + change);

            System.out.printf(
                    "%s: Rs.%.2f -- Rs.%.2f%n",
                    stockNames[i],
                    oldPrice,
                    stockPrices[i]
            );
        }

        System.out.println("Market prices updated successfully!");
    }
}
