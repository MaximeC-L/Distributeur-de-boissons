import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class VendingMachine {
    private int balance = 0;
    private final Scanner scanner = new Scanner(System.in);
    private List<String> selectedProducts = new ArrayList<>();
    private boolean isRunning = true;
    private final List<Integer> acceptedCoins = Arrays.asList(50, 20, 10, 5);

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
            if (balance >= coin) {
                int nCoin = balance / coin;
                balance -= nCoin * coin;
                System.out.println(nCoin + " pièces de " + coin + " sous");
            }

        }
        balance = 0;
    }

    private void cancelOrder() {
        System.out.println("Voulez-vous vraiment annuler la commande? (o/n)");
        String answer = scanner.nextLine();
        if (answer.equals("oui") || answer.equals("o")) {
            giveMoneyBack();
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
        System.out.println(selectedProduct + " a été ajouté(e) au panier");
    }

    public void displayPaymentOption() {
        System.out.print("Veuillez entrer les pièces");
        int amount = readNumber();

        while (!acceptedCoins.contains(amount)) {
            System.out.println("Les pièces de " + amount + " ne sont pas acceptées");
            System.out.print("Veuillez entrer les pièces");
            amount = readNumber();

        }
        System.out.println("La pièce de " + amount + " a été reçue");
        balance += amount;
    }

    //Todo utiliser la fonction updateBalance
    public void updateBalance(String productName) {
        switch (productName) {
            case "Coke" -> balance -= 25;
            case "Sprite" -> balance -= 35;
            case "Dr.Pepper" -> balance -= 45;
        }
    }


    public void displayMenuProducts() {
        System.out.println("Produits disponibles :");
        System.out.println("1. Coke - 25 cents");
        System.out.println("2. Sprite - 35 cents");
        System.out.println("3. Dr.Pepper - 45 cents");
    }

    public void deliverProduct() {
        if (balance == 0) {
            // afficher liste produits dans panier
            selectedProducts.forEach(System.out::println);
            selectedProducts.clear();
        } else if (balance < 0) {
            System.out.println("Veuillez insérer " + Math.abs(balance) + " sous avant de compléter la commande");
        } else {
            selectedProducts.forEach(System.out::println);
            selectedProducts.clear();
            giveMoneyBack();
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