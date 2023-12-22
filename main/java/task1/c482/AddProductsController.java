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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.*;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

/**This is the class that defines the Add Products Controller.*/
public class AddProductsController implements Initializable {

    @FXML
    public TextField addProdIDTxt;
    @FXML
    public TextField addProdNameTxt;
    @FXML
    public TextField addProdInvTxt;
    @FXML
    public TextField addProdPriceTxt;
    @FXML
    public TextField addProdMaxTxt;
    @FXML
    public TextField addProdMinTxt;
    @FXML
    public TextField addProdSearchTxt;

    @FXML
    public TableView<Part> partTable;
    @FXML
    public TableColumn addProdPartIDTblCol1;
    @FXML
    public TableColumn addProdPartNameTblCol1;
    @FXML
    public TableColumn addProdInvTblCol1;
    @FXML
    public TableColumn addProdPriceTblCol1;
    @FXML
    public TableView<Part> associatedPartTable;
    @FXML
    public TableColumn addProdPartIDTblCol2;
    @FXML
    public TableColumn addProdPartNameTblCol2;
    @FXML
    public TableColumn addProdInvTblCol2;
    @FXML
    public TableColumn addProdPriceTblCol2;

    @FXML
    public Button addProdAddBtn;
    @FXML
    public Button addProdRemoveBtn;
    @FXML
    public Button addProdSaveBtn;
    @FXML
    public Button addProdCancelBtn;


    Stage stage;
    Parent scene;

    ObservableList<Part> associatedParts = FXCollections.observableArrayList();

    /**This is the method to initialize the tableviews by populating with values from the cell value factory.
     * @param url The location of the resource.
     * @param resourceBundle The bundle of Resources. */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        partTable.setItems(Inventory.getAllParts());
        addProdPartIDTblCol1.setCellValueFactory(new PropertyValueFactory<>("id"));
        addProdPartNameTblCol1.setCellValueFactory(new PropertyValueFactory<>("name"));
        addProdInvTblCol1.setCellValueFactory(new PropertyValueFactory<>("stock"));
        addProdPriceTblCol1.setCellValueFactory(new PropertyValueFactory<>("price"));

        associatedPartTable.setItems(associatedParts);
        addProdPartIDTblCol2.setCellValueFactory(new PropertyValueFactory<>("id"));
        addProdPartNameTblCol2.setCellValueFactory(new PropertyValueFactory<>("name"));
        addProdInvTblCol2.setCellValueFactory(new PropertyValueFactory<>("stock"));
        addProdPriceTblCol2.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

    /** This is the method for the Save Button.
     * @param event What happens when the button is clicked.
     * @throws IOException When an error happens. */
    public void OnActionAddProdSave(ActionEvent event) throws IOException{
        int tempId = (int) (Math.random() * 2002);
        for (int i=0; i < Inventory.getAllProducts().size(); i++){
            if (tempId <= Inventory.getAllProducts().get(i).getId()) tempId = Inventory.getAllProducts().get(i).getId() + 1;
        }
        try {
            String prodName = addProdNameTxt.getText().trim();
            if (prodName.isEmpty()){
                throw new Exception("You must enter a name.");
            }
            int prodStock;
            try {
                prodStock = Integer.parseInt(addProdInvTxt.getText().trim());
            } catch (NumberFormatException e){
                throw new Exception("Stock value must be an integer with no decimals.");
            }
            double prodPrice;
            try { prodPrice = Double.parseDouble(addProdPriceTxt.getText().trim());
            } catch (NumberFormatException e) {
                throw new Exception("Price must include two decimal digits. Ex: 0.00 ");
            }
            int prodMin;
            try { prodMin = Integer.parseInt(addProdMinTxt.getText().trim());
            } catch (NumberFormatException e) {
                throw new Exception("Minimum value must be an integer with no decimals.");
            }
            int prodMax;
            try { prodMax = Integer.parseInt(addProdMaxTxt.getText().trim());
            } catch (NumberFormatException e) {
                throw new Exception("Maximum value must be an integer with no decimals.");
            }

            if (prodMin > prodMax){
                throw new Exception("Minimum value may not exceed maximum value.");
            }

            if (prodStock > prodMax || prodStock < prodMin){
                throw new Exception("Current stock value must be between the minimum and maximum values.");
            }

            Product product = new Product(tempId, prodName, prodPrice, prodStock, prodMin, prodMax);
            for (Part part: associatedParts){
                if (part != associatedParts) product.addAssociatedPart(part);
            }
            Inventory.getAllProducts().add(product);

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
    public void OnActionAddProdAdd(ActionEvent event) {
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

    /** This is the method for the Remove Part Button.
     * Updated to include a confirmation when associated parts are removed. 12-14-23
     * @param event What happens when you press the Remove button. */
    public void OnActionAddProdRemove(ActionEvent event) {
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
                associatedParts.remove(partSelected);
                associatedPartTable.setItems(associatedParts);
            }
        }
    }

    /** This is the method for the Search Field.
     * @param event What happens when the search field is used. */
    public void OnActionAddProdSearchTxt(ActionEvent event){
        String searchTxt = addProdSearchTxt.getText();
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
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("mainForm-view.fxml")));
        stage.setScene(new Scene(scene));
        stage.show();
    }

}