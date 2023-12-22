package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

/**This is the class that defines the Inventory model.
 * Parts and products are stored in Observable Lists.*/
public class Inventory {
        private static ObservableList<Part> allParts = FXCollections.observableArrayList();
        private static ObservableList<Product> allProducts = FXCollections.observableArrayList();

    /** This is the method for adding a part to the allParts Observable List.
     * @param part The part to add. */
    public static void addPart(Part part){
        allParts.add(part);
    }

    /** This is the method for adding a product to the allProducts Observable List.
     * @param Product The product to add. */
    public static void addProduct(Product Product){
        allProducts.add(Product);
    }

    /** This is the method that returns all Parts to the allParts Observable List.
     * @return Parts into the ObservableList. */
    public static ObservableList<Part> getAllParts(){
        return allParts;
    }

    /** This is the method that returns all Products to the allProducts Observable List.
     * @return Products into the ObservableList. */
    public static ObservableList<Product> getAllProducts(){
        return allProducts;
    }

    /** This is the method that modifies a Part in the allParts Observable List.
     * @param index Index of the Part to update.
     * @param updatedPart Part to update. */
    public static void updatePart(int index, Part updatedPart){
        allParts.set(index, updatedPart);
    }

    /** This is the method that modifies a Product in the allProduct Observable List.
     * @param index Index of the Product to update.
     * @param updateProduct Product to update. */
    public static void updateProduct(int index, Product updateProduct){
        allProducts.set(index, updateProduct);
    }

    /** This is the method that deletes a Part from the allParts Observable List.
     * This is a boolean that returns either true for successful deletion or false if unsuccessful.
     * @param selectedPart Part to Delete.
     * @return True or False. */
    public static boolean deletePart(Part selectedPart){
        return allParts.remove(selectedPart);
    }

    /** This is the method that deletes a Product from the allProducts Observable List.*
     * This is a boolean that returns either true for successful deletion or false if unsuccessful.
     * @param selectedProduct Product to Delete.
     * @return True or False.  */
    public static boolean deleteProduct(Product selectedProduct){
        return allProducts.remove(selectedProduct);
    }

    /** This is the method that searches for a Part.
     * Contains an Error message for the no-match scenario.
     * @param partId Part ID to be looked up.
     * @return The part searched for or null. */
    public static Part lookupPart(int partId){
        for (Part thePart : Inventory.getAllParts()){
            if (thePart.getId() == partId){
                return thePart;
            }
        }
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText("No matching part was found.");
        alert.showAndWait();
        return null;
    }

    /** This is the method that searches for a Product.
     * Contains an Error message for the no-match scenario.
     * @param productId Product ID to be looked up.
     * @return The Product searched for or null. */
    public static Product lookupProduct(int productId){
        for (Product theProduct : Inventory.getAllProducts()){
            if (theProduct.getId() == productId) {
                return theProduct;
            }
        }
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText("No matching product was found.");
        alert.showAndWait();
        return null;
    }

    /** This is the method that creates a filtered Observable list of found Parts.
     * @param partName The name of the part to be looked up.
     * @return The ObservableList of looked up parts. */
    public static ObservableList<Part> lookupPart(String partName){
        ObservableList<Part> matchingParts = FXCollections.observableArrayList();
        for (Part getPart : Inventory.getAllParts()) {
                if (getPart.getName().toLowerCase().contains(partName.toLowerCase())) {
                    matchingParts.add(getPart);
                }
            }
        return matchingParts;
    }

    /** This is the method that creates a filtered Observable list of found Products.
     * @param productName The name of the product ot be looked up.
     * @return The ObservableList of looked up parts. */
    public static ObservableList<Product> lookupProduct(String productName){
        ObservableList<Product> matchingProducts = FXCollections.observableArrayList();
        for (Product getProd : Inventory.getAllProducts()) {
                if (getProd.getName().toLowerCase().contains(productName.toLowerCase())) {
                    matchingProducts.add(getProd);
                }
            }
        return matchingProducts;
    }

}