import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class VendingMachine {
    private int balance;
    private final Scanner scanner = new Scanner(System.in);
    private List<String> selectedProducts;

    public VendingMachine() {
        balance = 0;
    }

    public String selectProduct() {
        System.out.print("Veuillez sélectionner une option : ");

        int choice = scanner.nextInt();
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
        String selectedProduct = selectProduct();
        while (selectedProduct == null) {
            selectedProduct = selectProduct();
        }
        selectedProducts.add(selectedProduct);
        System.out.println(selectedProduct + " a été ajouté(e) au panier");
    }
    public void displayPaymentOption() {
        System.out.print("Veuillez entrer les pièces");
        int amount = scanner.nextInt();
        List<Integer> acceptedCoins = Arrays.asList(5, 10, 20, 50);
        while (!acceptedCoins.contains(amount)) {
            System.out.println("Les pièces de " + amount + " ne sont pas acceptées");
            System.out.print("Veuillez entrer les pièces");
            amount = scanner.nextInt();

        }
        System.out.println("La pièce de " + amount + " a été reçue");
        balance += amount;
    }

    public void updateBalance(String productName){
        switch (productName) {
            case "Coke" -> balance -= 25;
            case "Sprite" -> balance -= 35;
            case "Dr.Pepper" -> balance -= 45;
        }
    }


    public void displayMenu() {
        System.out.println("Produits disponibles :");
        System.out.println("1. Coke - 25 cents");
        System.out.println("2. Sprite - 35 cents");
        System.out.println("3. Dr.Pepper - 45 cents");
    }

    public static void main(String[] args) {
        VendingMachine machine1 = new VendingMachine();
        machine1.displayMenu();
        machine1.displaySelectedProduct();
        machine1.displayPaymentOption();
    }
}