import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class VendingMachine {
    private int balance = 0;
    private final Scanner scanner = new Scanner(System.in);
    private List<String> selectedProducts = new ArrayList<>();
    private boolean isRunning = true;
    public void start() {
        while (isRunning) {
            int choice = displayMenuActions();
            switch (choice) {
                case 1 -> displaySelectedProduct();
                case 2 -> displayPaymentOption();
                case 3 -> deliverProduct();
                case 4 -> cancelOrder();
                default -> {
                    System.out.println("L'option sélectionnée n'existe pas");
                }

            };
        }
    }
    private void cancelOrder(){
        System.out.println("Voulez-vous vraiment annuler la commande? (o/n)");
        String answer = scanner.nextLine();
        if(answer.equals("oui") || answer.equals("o")){
            // Todo implementer remise de l'argent
            System.out.println("Remise de l'argent");
            balance = 0;
            selectedProducts.clear();
            isRunning = false;
        }
    }
    public int displayMenuActions(){
        System.out.println("Veuillez sélectionner une option : ");
        System.out.println("1. Choisir un produit");
        System.out.println("2. Ajouter une pièce");
        System.out.println("3. Confirmer la commande");
        System.out.println("4. Annuler la commande");

        return scanner.nextInt();

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


    public void displayMenuProducts() {
        System.out.println("Produits disponibles :");
        System.out.println("1. Coke - 25 cents");
        System.out.println("2. Sprite - 35 cents");
        System.out.println("3. Dr.Pepper - 45 cents");
    }
    public void deliverProduct(){
        if(balance == 0){
            // afficher liste produits dans panier
            selectedProducts.forEach(System.out::println);

        }
        else{
            System.out.println("Veuilez insérer le bon montant d'argent avant de compléter la commande");
        }
    }

    public static void main(String[] args) {
        VendingMachine machine1 = new VendingMachine();
        machine1.start();
    }
}