import java.util.Scanner;

public class VendingMachine {
    private int balance;

    public VendingMachine() {
        balance = 0;
    }

    public String selectProduct() {
        System.out.print("Veuillez sélectionner une option : ");
        Scanner scanner = new Scanner(System.in);
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
        System.out.println(selectedProduct + " a été ajouté(e) au panier");
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
    }
}