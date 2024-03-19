import javax.print.DocFlavor;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class VendingMachine {
    private int totalCoins = 0;
    private int totalProducts = 0;
    private final Scanner scanner = new Scanner(System.in);
    private final Map<Product, Integer> selectedProducts = new HashMap<>();
    private boolean isRunning = true;
    private final List<Integer> acceptedCoins = Arrays.asList(50, 20, 10, 5);
    private final String logPath = "logMachine.txt";
    private Map<Product, Integer> stock = new HashMap<>();
    private final List<Product> inventory = Arrays.asList(
            new Product("Coke", 25),
            new Product("Sprite", 35),
            new Product("Dr.Pepper", 45)
    );

    VendingMachine() {
        for (Product p : inventory) {
            stock.put(p, 3);
            selectedProducts.put(p, 0);
        }
    }

    VendingMachine(Integer stockCustom) {
        for (Product p : inventory) {
            stock.put(p, stockCustom);
            selectedProducts.put(p, 0);
        }
    }

    VendingMachine(Integer stockCoke, Integer stockSprite, Integer stockDrPepper) {
        stock.put(inventory.get(0), stockCoke);
        stock.put(inventory.get(1), stockSprite);
        stock.put(inventory.get(2), stockDrPepper);
        for (Product p : inventory) {
            selectedProducts.put(p, 0);
        }
    }

    public void start() {
        while (isRunning) {
            int choice = displayMenuActions();
            switch (choice) {
                case 1 -> displaySelectedProduct();
                case 2 -> displayPaymentOption();
                case 3 -> displayCart();
                case 4 -> deliverProduct();
                case 5 -> cancelOrder();
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
        System.out.println("3. Afficher le panier");
        System.out.println("4. Confirmer la commande");
        System.out.println("5. Annuler la commande");

        return readNumber();

    }


    public Product selectProduct() {
        System.out.print("Veuillez sélectionner une option : ");

        int choice = readNumber() - 1;
        if (choice >= 0 && choice < inventory.size()) {
            return inventory.get(choice);
        } else {
            System.out.println("L'option sélectionnée n'existe pas");
            return null;
        }
    }

    private void displayCart() {
        System.out.println("Contenu du panier:");
        for (Map.Entry<Product, Integer> p : selectedProducts.entrySet()) {
            if (p.getValue() != 0) {
                System.out.println(p.getValue() + "x " + p.getKey().getName());
            }
        }
        System.out.println("Total: " + totalProducts + " cents");
        if (totalCoins - totalProducts > 0) {
            System.out.println("Vous avez inséré " + totalCoins + " cents");
        } else {
            System.out.println("Il vous reste " + (totalProducts - totalCoins) + " cents à payer");
        }
    }

    public void displaySelectedProduct() {
        displayMenuProducts();
        Product selectedProduct = selectProduct();
        while (selectedProduct == null) {
            selectedProduct = selectProduct();
        }
        selectedProducts.put(selectedProduct, selectedProducts.get(selectedProduct) + 1);
        updateBalance(selectedProduct.getPrice());
        System.out.println(selectedProduct.getName() + " a été ajouté(e) au panier");
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


    public void updateBalance(Integer price) {
        totalProducts += price;
    }


    public void displayMenuProducts() {
        System.out.println("Produits disponibles :");
        int i = 1;
        for (Product product : inventory) {
            System.out.println(i + ". " + product.getName() + " - " + product.getPrice() + " cents");
            i++;
        }

    }

    //todo verifier le stock
    //todo fix warnings
    //todo maintenance avec lecture
    public void deliverProduct() {
        if (totalCoins < totalProducts) {
            System.out.println("Veuillez insérer " + Math.abs(totalProducts - totalCoins) + " sous avant de compléter la commande");
            return;
        }

        // afficher liste produits dans panier
        for (Map.Entry<Product, Integer> p : selectedProducts.entrySet()) {
            if (p.getValue() != 0) {
                System.out.println(p.getValue() + "x " + p.getKey().getName());
                stock.put(p.getKey(), stock.get(p.getKey()) - p.getValue());
            }
        }

        selectedProducts.clear();
        if (totalCoins == totalProducts) {
            totalCoins = 0;
            totalProducts = 0;

        } else {
            totalCoins -= totalProducts;
            totalProducts = 0;
            giveMoneyBack();
        }
        writeLog("Une commande a été complétée");
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
        VendingMachine machine2 = new VendingMachine(3, 5, 2);
        VendingMachine machine3 = new VendingMachine(10);
        machine1.start();
    }
}