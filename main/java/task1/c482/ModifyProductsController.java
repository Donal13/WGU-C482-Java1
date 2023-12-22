package task1.c482;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Inventory;
import model.Part;
import model.Product;

import java.io.IOException;
import java.util.Objects;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**This is the class that defines the Modify Products Controller. */
public class ModifyProductsController implements Initializable {

    @FXML
    public TextField ModProdIDTxt;
    @FXML
    public TextField ModProdNameTxt;
    @FXML
    public TextField ModProdInvTxt;
    @FXML
    public TextField ModProdPriceTxt;
    @FXML
    public TextField ModProdMaxTxt;
    @FXML
    public TextField ModProdMinTxt;
    @FXML
    public TextField ModProdSearchTxt;

    @FXML
    public TableView<Part> partTable;
    @FXML
    public TableColumn ModProdPartIDTblCol1;
    @FXML
    public TableColumn ModProdNameTblCol1;
    @FXML
    public TableColumn ModProdInvTblCol1;
    @FXML
    public TableColumn ModProdPriceTblCol1;
    @FXML
    public TableView<Part> associatedPartTable;
    @FXML
    public TableColumn ModProdPartIDTblCol2;
    @FXML
    public TableColumn ModProdNameTblCol2;
    @FXML
    public TableColumn ModProdInvTblCol2;
    @FXML
    public TableColumn ModProdPriceTblCol2;

    @FXML
    public Button ModProdAddBtn;
    @FXML
    public Button ModProdRemoveBtn;
    @FXML
    public Button ModProdSaveBtn;
    @FXML
    public Button ModProdCancelBtn;

    private Product productInstance;
    public int ModProdIndex;

    Stage stage;
    Parent scene;

    ObservableList<Part> associatedParts = FXCollections.observableArrayList();

    /**This is the method to initialize the tableviews by populating with values from the cell value factory.
     * @param url The location of the resource.
     * @param resourceBundle The bundle of Resources.*/
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        partTable.setItems(Inventory.getAllParts());
        ModProdPartIDTblCol1.setCellValueFactory(new PropertyValueFactory<>("id"));
        ModProdNameTblCol1.setCellValueFactory(new PropertyValueFactory<>("name"));
        ModProdInvTblCol1.setCellValueFactory(new PropertyValueFactory<>("stock"));
        ModProdPriceTblCol1.setCellValueFactory(new PropertyValueFactory<>("price"));

        associatedPartTable.setItems(associatedParts);
        ModProdPartIDTblCol2.setCellValueFactory(new PropertyValueFactory<>("id"));
        ModProdNameTblCol2.setCellValueFactory(new PropertyValueFactory<>("name"));
        ModProdInvTblCol2.setCellValueFactory(new PropertyValueFactory<>("stock"));
        ModProdPriceTblCol2.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

    /** This is the method that populates the text fields with the correct data.
     * @param productSelected The Product to show. */
    public void parseProductData(Product productSelected){
        productInstance = productSelected;
        associatedParts.addAll(productSelected.getAllAssociatedParts());
        associatedPartTable.setItems(associatedParts);

        ModProdIDTxt.setText(Integer.toString(productSelected.getId()));
        ModProdInvTxt.setText(Integer.toString(productSelected.getStock()));
        ModProdNameTxt.setText(productSelected.getName());
        ModProdPriceTxt.setText(Double.toString(productSelected.getPrice()));
        ModProdMinTxt.setText(Integer.toString(productSelected.getMin()));
        ModProdMaxTxt.setText(Integer.toString(productSelected.getMax()));
        ModProdIndex = Inventory.getAllProducts().indexOf(productSelected);
    }

    /** This is the method for the Save Button.
     * @param event What happens when the button is clicked.
     * @throws IOException When an error happens. */
    public void OnActionModProdSave(ActionEvent event) throws IOException {
        try {
            int prodId;
            try {
                prodId = Integer.parseInt(ModProdIDTxt.getText().trim());
            } catch (NumberFormatException e) {
                throw new Exception("Part ID must be an integer.");
            }
            String prodName = ModProdNameTxt.getText().trim();
            if (prodName.isEmpty()) {
                throw new Exception("Part name cannot be empty.");
            }
            int prodStock;
            try {
                prodStock = Integer.parseInt(ModProdInvTxt.getText().trim());
            } catch (NumberFormatException e) {
                throw new Exception("Stock value must be an integer with no decimals.");
            }
            double prodPrice;
            try {
                prodPrice = Double.parseDouble(ModProdPriceTxt.getText().trim());
            } catch (NumberFormatException e) {
                throw new Exception("Price must include two decimal digits. Ex: 0.00 ");
            }
            int prodMin;
            try {
                prodMin = Integer.parseInt(ModProdMinTxt.getText().trim());
            } catch (NumberFormatException e) {
                throw new Exception("Minimum value must be an integer with no decimals.");
            }
            int prodMax;
            try {
                prodMax = Integer.parseInt(ModProdMaxTxt.getText().trim());
            } catch (NumberFormatException e) {
                throw new Exception("Maximum value must be an integer with no decimals.");
            }

            if (prodMin > prodMax) {
                throw new Exception("Minimum value may not exceed maximum value.");
            }

            if (prodStock > prodMax || prodStock < prodMin) {
                throw new Exception("Current stock must be more than or equal to the minimum and less than or equal to the maximum.");
            }
            Product updatedProduct = new Product(prodId, prodName, prodPrice, prodStock, prodMin, prodMax);
            updatedProduct.getAllAssociatedParts().addAll(associatedParts);
            Inventory.updateProduct(ModProdIndex, updatedProduct);

            OnActionMainFormBtn(event);
        } catch (Exception invalid){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid Error");
            alert.setContentText(invalid.getMessage());
            alert.showAndWait();
        }
    }

    /** This is the method for the Add Part Button.
     * @param event What happens when you press the Add button. */
    public void OnActionModProdAdd(ActionEvent event) {
        Part partSelected = partTable.getSelectionModel().getSelectedItem();
        if (partSelected == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("No part was selected.");
            alert.showAndWait();
        } else if (!associatedParts.contains(partSelected)) {
            associatedParts.add(partSelected);
            associatedPartTable.setItems(associatedParts);
        }
    }

    /** This is the method for the Delete Part Button.
     * Updated to include a confirmation when associated parts are removed. 12-14-23
     * @param event What happens when you press the Remove button. */
    public void OnActionModProdRemove(ActionEvent event) {
        Part partSelected = associatedPartTable.getSelectionModel().getSelectedItem();
        if (partSelected == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("No part was selected.");
            alert.showAndWait();
        } else if (associatedParts.contains(partSelected)) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("Confirm deletion of " + associatedPartTable.getSelectionModel().getSelectedItem().getName());
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.CANCEL) {
                return;
            }
            if (result.get() == ButtonType.OK) {
                Inventory.deletePart(partSelected);
                    productInstance.deleteAssociatedPart(partSelected);
                    associatedParts.remove(partSelected);
                    associatedPartTable.setItems(associatedParts);
            }
        }
    }

    /** This is the method for the Search Field.
     * @param event What happens when the search field is used. */
    public void OnActionModProdSearchTxt(ActionEvent event) {
        String searchTxt = ModProdSearchTxt.getText();
        ObservableList<Part> result = Inventory.lookupPart(searchTxt);
        try {
            while (result.isEmpty()){
                int partId = Integer.parseInt(searchTxt);
                result.add(Inventory.lookupPart(partId));
            }
            partTable.setItems(result);
        } catch (NumberFormatException exception) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("No matching part was found.");
            alert.showAndWait();
        }
    }

    /** This is the method for the Button that returns you to the main screen. */
    @FXML
    void OnActionMainFormBtn(ActionEvent event) throws IOException {
        stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("mainForm-view.fxml")));
        stage.setScene(new Scene(scene));
        stage.show();
    }

}