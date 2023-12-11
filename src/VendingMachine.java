import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class VendingMachine {
    private int totalCoins = 0;
    private int totalProducts = 0;
    private final Scanner scanner = new Scanner(System.in);
    private final List<String> selectedProducts = new ArrayList<>();
    private boolean isRunning = true;
    private final List<Integer> acceptedCoins = Arrays.asList(50, 20, 10, 5);
    private final String logPath = "logMachine.txt";


    public void start() {
        while (isRunning) {
            int choice = displayMenuActions();
            switch (choice) {
                case 1 -> displaySelectedProduct();
                case 2 -> displayPaymentOption();
                case 3 -> deliverProduct();
                case 4 -> cancelOrder();
                default -> System.out.println("L'option sélectionnée n'existe pas");
            }
        }
    }

    private void giveMoneyBack() {
        System.out.println("Rendu :");
        for (int coin : acceptedCoins) {
            if (totalCoins >= coin) {
                int nCoin = totalCoins / coin;
                totalCoins -= nCoin * coin;
                System.out.println(nCoin + " pièces de " + coin + " sous");
            }

        }
        totalCoins = 0;
    }

    private void cancelOrder() {
        System.out.println("Voulez-vous vraiment annuler la commande? (o/n)");
        String answer = scanner.nextLine();
        if (answer.equals("oui") || answer.equals("o")) {
            if (totalCoins != 0) {
                giveMoneyBack();
            }
            selectedProducts.clear();
            isRunning = false;
        }
    }

    public int displayMenuActions() {
        System.out.println("Veuillez sélectionner une option : ");
        System.out.println("1. Choisir un produit");
        System.out.println("2. Ajouter une pièce");
        System.out.println("3. Confirmer la commande");
        System.out.println("4. Annuler la commande");

        return readNumber();

    }


    public String selectProduct() {
        System.out.print("Veuillez sélectionner une option : ");

        int choice = readNumber();
        return switch (choice) {
            case 1 -> "Coke";
            case 2 -> "Sprite";
            case 3 -> "Dr.Pepper";
            default -> {
                System.out.println("L'option sélectionnée n'existe pas");
                yield null;
            }
        };
    }

    public void displaySelectedProduct() {
        displayMenuProducts();
        String selectedProduct = selectProduct();
        while (selectedProduct == null) {
            selectedProduct = selectProduct();
        }
        selectedProducts.add(selectedProduct);
        updateBalance(selectedProduct);
        System.out.println(selectedProduct + " a été ajouté(e) au panier");
    }

    public void displayPaymentOption() {
        System.out.print("Veuillez entrer les pièces : ");
        int amount = readNumber();

        while (!acceptedCoins.contains(amount)) {
            System.out.println("Les pièces de " + amount + " ne sont pas acceptées");
            System.out.print("Veuillez entrer les pièces");
            amount = readNumber();

        }
        System.out.println("La pièce de " + amount + " a été reçue");
        totalCoins += amount;
    }

    //Todo utiliser la fonction updateBalance
    public void updateBalance(String productName) {
        switch (productName) {
            case "Coke" -> totalProducts += 25;
            case "Sprite" -> totalProducts += 35;
            case "Dr.Pepper" -> totalProducts += 45;
        }
    }


    public void displayMenuProducts() {
        System.out.println("Produits disponibles :");
        System.out.println("1. Coke - 25 cents");
        System.out.println("2. Sprite - 35 cents");
        System.out.println("3. Dr.Pepper - 45 cents");
    }

    public void deliverProduct() {
        if (totalCoins == totalProducts) {
            // afficher liste produits dans panier
            selectedProducts.forEach(System.out::println);
            selectedProducts.clear();
            writeLog("Une commande a été complétée");
        } else if (totalCoins < totalProducts) {
            System.out.println("Veuillez insérer " + Math.abs(totalProducts - totalCoins) + " sous avant de compléter la commande");
        } else {
            selectedProducts.forEach(System.out::println);
            selectedProducts.clear();
            giveMoneyBack();
        }
    }

    private void writeLog(String message) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(logPath, true))) {
            LocalDateTime timeStamp = LocalDateTime.now();
            String strTimeStamp = timeStamp.format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));
            writer.write(strTimeStamp + " - " + message + "\n");
        } catch (IOException e) {
            System.out.println("Une erreur est survenue avec la jpurnalisation");
        }
    }

    private int readNumber() {
        int input = scanner.nextInt();
        scanner.nextLine();
        return input;
    }


    public static void main(String[] args) {
        VendingMachine machine1 = new VendingMachine();
        machine1.start();
    }
}