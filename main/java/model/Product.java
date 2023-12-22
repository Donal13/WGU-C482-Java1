package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
/**This is the class that defines the Product model. */
public class Product {

    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;

    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();

    /** Constructor for new Products
     * @param id The product ID.
     * @param name The product name.
     * @param price The product price.
     * @param stock Current stock level of the product.
     * @param min Minimum allowed stock level.
     * @param max Maximum allowed stock level. */
    public Product(int id, String name, double price, int stock, int min, int max){
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

    /** This is the method for getting the ID. */
    public int getId() {
        return id;
    }

    /** This is the method for setting the ID. */
    public void setId(int id){
        this.id = id;
    }

    /** This is the method for getting the Name. */
    public String getName() {
        return name;
    }

    /** This is the method for setting the Name.
     * @param name The name being added. */
    public void setName(String name){
        this.name = name;
    }

    /** This is the method for getting the Price. */
    public double getPrice() {
        return price;
    }

    /** This is the method for setting the Price. */
    public void setPrice(double price){
        this.price = price;
    }

    /** This is the method for getting the Stock. */
    public int getStock() {
        return stock;
    }

    /** This is the method for setting the Stock.
     * Even if it isn't used it feels weird not to have it.*/
    public void setStock(int stock) {
        this.stock = stock;
    }

    /** This is the method for getting the Min value. */
    public int getMin() {
        return min;
    }

    /** This is the method for setting the Min value.
     * Even if it isn't used it feels weird not to have it. */
    public void setMin(int min) {
        this.min = min;
    }

    /** This is the method for getting the Max value. */
    public int getMax() {
        return max;
    }

    /** This is the method for setting the Max value.
     * Even if it isn't used it feels weird not to have it.*/
    public void setMax(int max) {
        this.max = max;
    }

    /** This is the method for adding a part to the associatedParts Observable List.
     * @param part The part to add. */
    public void addAssociatedPart(Part part){
        associatedParts.add(part);
    }

    /** This is the method for deleting a part from the associatedParts Observable List.
     * @param part The part to delete. */
    public void deleteAssociatedPart(Part part){
        associatedParts.remove(part);
    }

    /** This is the method for getting the associatedParts from the Observable List.*/
    public ObservableList<Part> getAllAssociatedParts(){
        return associatedParts;
    }

}