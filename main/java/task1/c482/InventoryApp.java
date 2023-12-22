package task1.c482;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.*;

import java.io.IOException;

    /** This class creates the main application that starts the whole program and loads the FXML.
     *
     * FUTURE ENHANCEMENT - There doesn't seem to be any kind of database or way to save new parts or products
     * so that they will still exist when the program has been closed and then re-opened. I imagine that is just
     * because of the complexity required for this level of instruction, but it would be pretty essential for a
     * program like this to actually be useful.
     * */
    public class InventoryApp extends Application {

        /** This is the start method - This is the entry point for the program.
         * This is where the first fxml file is loaded.
         * the start method is a method from the base parent Application.
         *
         * @param stage Is an FXML file to be loaded.
         * @throws IOException When no fxml file is located.
         * */
        @Override
        public void start(Stage stage) throws IOException {
            Inventory inv = new Inventory();
            addTestData(inv);

            FXMLLoader fxmlLoader = new FXMLLoader(InventoryApp.class.getResource("mainForm-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 900, 400);
            stage.setTitle("Parts and Inventory");
            stage.setScene(scene);
            stage.show();
        }

        /**This is the test data used for analyzing the program's functionality.
         * First, five parts are added to the InHouse fields.
         * Next, five parts are added to the OutSourced fields.
         * Then, There are five products added which each have two associated parts.
         * */
        void addTestData(Inventory inv) {
            Inventory.addPart(new InHouse(1, "Quick Part 1", 1.99, 1, 1, 50, 1001));
            Inventory.addPart(new InHouse(2, "Brown Part 2", 2.99, 2, 1, 50, 1002));
            Inventory.addPart(new InHouse(3, "Fox Part 3", 3.99, 3, 1, 50, 1003));
            Inventory.addPart(new InHouse(4, "Jumped Part 4", 4.99, 4, 1, 50, 1004));
            Inventory.addPart(new InHouse(5, "Over Part 5", 5.99, 5, 1, 50, 1005));
            Inventory.addPart(new OutSourced(6, "Five Part 6", 6.99, 6, 1, 50, "Big Co."));
            Inventory.addPart(new OutSourced(7, "Boxing Part 7", 7.99, 7, 1, 50, "Small Co."));
            Inventory.addPart(new OutSourced(8, "Wizards Part 8", 8.99, 8, 1, 50, "Public Co."));
            Inventory.addPart(new OutSourced(9, "Jump Part 9", 9.99, 9, 1, 50, "Private Co."));
            Inventory.addPart(new OutSourced(10, "Quickly Part 10", 10.99, 10, 1, 50, "Strange Co."));
            Product prod1 = new Product(100, "Basic Product 1", 9.99, 5, 1, 50);
            prod1.addAssociatedPart(new InHouse(1, "Part 1", 1.99, 1, 1, 50, 1001));
            prod1.addAssociatedPart(new OutSourced(6, "Part 6", 6.99, 6, 1, 50, "Big Co."));
            Inventory.addProduct(prod1);
            Product prod2 = new Product(200, "Improved Product 2", 19.99, 10, 1, 50);
            prod2.addAssociatedPart(new InHouse(2, "Part 2", 2.99, 2, 1, 50, 1002));
            prod2.addAssociatedPart(new OutSourced(7, "Part 7", 7.99, 7, 1, 50, "Small Co."));
            Inventory.addProduct(prod2);
            Product prod3 = new Product(300, "Super Product 3", 29.99, 15, 1, 50);
            prod3.addAssociatedPart(new InHouse(3, "Part 3", 3.99, 3, 1, 50, 1003));
            prod3.addAssociatedPart(new OutSourced(8, "Part 8", 8.99, 8, 1, 50, "Public Co."));
            Inventory.addProduct(prod3);
            Product prod4 = new Product(400, "Wild Product 4", 39.99, 20, 1, 50);
            prod4.addAssociatedPart(new InHouse(4, "Part 4", 4.99, 4, 1, 50, 1004));
            prod4.addAssociatedPart(new OutSourced(9, "Part 9", 9.99, 9, 1, 50, "Private Co."));
            Inventory.addProduct(prod4);
            Product prod5 = new Product(500, "Insane Product 5", 49.99, 25, 1, 50);
            prod5.addAssociatedPart(new InHouse(5, "Part 5", 5.99, 5, 1, 50, 1005));
            prod5.addAssociatedPart(new OutSourced(10, "Part 10", 10.99, 10, 1, 50, "Strange Co."));
            Inventory.addProduct(prod5);
        }
        

        /** This is the main method. It actually launches the program.
         * @param args The args.
         * */
        public static void main(String[] args){
            launch(args);
        }

    }