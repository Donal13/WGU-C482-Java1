package task1.c482;

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
import model.Inventory;
import model.Part;
import model.Product;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

/**This is the class that defines the Main Form Controller. */
public class MainFormController implements Initializable {
    @FXML
    public TextField MainPartSearchTxt;
    @FXML
    public TextField MainProductSearchTxt;
    @FXML
    public Button AddPartsMainBtn;
    @FXML
    public Button ModifyPartsMainBtn;
    @FXML
    public Button DeletePartsMainBtn;
    @FXML
    public Button AddProductsMainBtn;
    @FXML
    public Button ModifyProductsMainBtn;
    @FXML
    public Button DeleteProductsMainBtn;
    @FXML
    public TableView<Part> mainPartsTable;

    @FXML
    public TableColumn<Part,Integer>partIdMainTblCol;

    @FXML
    public TableColumn<Part,String>partNameMainTblCol;

    @FXML
    public TableColumn<Part,Integer>partInvMainTblCol;

    @FXML
    public TableColumn<Part,Double>partPriceMainTblCol;

    @FXML
    public TableView<Product> mainProductsTable;

    @FXML
    public TableColumn<Product,Integer>productIdMainTblCol;

    @FXML
    public TableColumn<Product,String>productNameMainTblCol;

    @FXML
    public TableColumn<Product,Integer>productInvMainTblCol;

    @FXML
    public TableColumn<Product,Double>productPriceMainTblCol;

    Stage stage;
    Parent scene;

/**This is the method to initialize the main form by populating the tableviews with values from the cell value factory.
 * @param url The location of the resource.
 * @param resourceBundle The bundle of Resources.
 * */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        partIdMainTblCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameMainTblCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInvMainTblCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPriceMainTblCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        mainPartsTable.setItems(Inventory.getAllParts());

        productIdMainTblCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        productNameMainTblCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        productInvMainTblCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        productPriceMainTblCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        mainProductsTable.setItems(Inventory.getAllProducts());


    }

    /** This is the method for the Add Parts Button.
     * @param event What happens when the button is clicked.
     * @throws IOException When an error happens. */
    @FXML
    public void OnActionAddPartBtn(ActionEvent event) throws IOException {
        stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("addPartsForm-view.fxml")));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /** This is the method for the Modify Parts Button.
     *
     * RUNTIME ERROR - NullPointerException "Caused by: java.lang.NullPointerException: Cannot invoke
     * "task1.c482.modifyPartsController.parseData(model.Part)" because "controller"
     * is null at task1.c482/task1.c482.mainFormController.OnActionModifyPartBtn(mainFormController.java:110)"
     * Runtime error solution - This method was the first one to be fully written and had problems with the fxml loader.
     * I corrected the controller not being initialized correctly and added a check to be sure the controller != null.
     *
     * @param event What happens when the button is clicked.
     * @throws IOException When an error happens. */
    @FXML
    public void OnActionModifyPartBtn(ActionEvent event) throws IOException {
        Part partSelected = mainPartsTable.getSelectionModel().getSelectedItem();
        if (partSelected != null) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("modifyPartsForm-view.fxml"));
            Parent root = loader.load();
            ModifyPartsController controller = loader.getController();

            if (controller != null) {
                controller.parseData(partSelected);

                Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.show();
            } else {
                System.out.println("Controller is null.");
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("No item was selected to modify.");
            alert.showAndWait();
        }
    }

    /** This is the method for the Delete Parts Button.
     * @param event What happens when the button is clicked.
     * @throws IOException When an error happens. */
    public void OnActionDeletePartBtn(ActionEvent event) throws IOException{
        Part partToDelete = mainPartsTable.getSelectionModel().getSelectedItem();
        if (partToDelete != null){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("Confirm deletion of " + mainPartsTable.getSelectionModel().getSelectedItem().getName());
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.CANCEL){
                return;
            }
            if (result.get() == ButtonType.OK){
                Inventory.deletePart(partToDelete);
                mainPartsTable.setItems(Inventory.getAllParts());
                for (int i =0; i <Inventory.getAllProducts().size(); i++){
                    if (Inventory.getAllProducts().get(i).getAllAssociatedParts().contains(partToDelete)){
                        Inventory.getAllProducts().get(i).deleteAssociatedPart(partToDelete);
                    }
                }
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("No part was selected.");
            alert.showAndWait();
        }
    }

    /** This is the method for the Add Product Button.
     * @param event What happens when the button is clicked.
     * @throws IOException When an error happens. */
    @FXML
    public void OnActionAddProdBtn(ActionEvent event) throws IOException {
        stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("addProductsForm-view.fxml")));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /** This is the method for the Modify Product Button.
     * @param event What happens when the button is clicked.
     * @throws IOException When an error happens. */
    @FXML
    public void OnActionModProdBtn(ActionEvent event) throws IOException {
        Product productSelected = mainProductsTable.getSelectionModel().getSelectedItem();
        if (productSelected != null) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("modifyProductsForm-view.fxml"));
            Parent root = loader.load();
            ModifyProductsController controller = loader.getController();
            if (controller != null) {
                controller.associatedParts.clear();
                controller.parseProductData(productSelected);

                Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.show();
            } else {

                System.out.println("Controller is Null.");
            }
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("No product was selected.");
                alert.showAndWait();
            }
        }

    /** This is the method for the Delete Product Button.
     * @param event What happens when the button is clicked.
     * @throws IOException When an error happens. */
    public void OnActionDelProdBtn(ActionEvent event) throws IOException{
        Product productDelete = mainProductsTable.getSelectionModel().getSelectedItem();
        if (productDelete != null){
            if (productDelete.getAllAssociatedParts().isEmpty()){
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setContentText("Are you sure you wish to delete " + productDelete.getName() + " ?");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.isPresent() && result.get() == ButtonType.OK){
                    Inventory.deleteProduct(productDelete);
                    mainProductsTable.setItems(Inventory.getAllProducts());
                }
            } else {
                Alert warnAlert = new Alert(Alert.AlertType.WARNING, "Cannot delete. Product is associated with at least one part.");
                warnAlert.showAndWait();
            }
        } else {
            Alert errAlert = new Alert(Alert.AlertType.ERROR);
            errAlert.setContentText("No product is selected.");
            errAlert.showAndWait();
        }
    }

    /** This is the method for the Part Search Field.
     * Contains an error message for the not-found scenario.
     * @param event What happens when the button is clicked. */
    public void OnActionPartSearch(ActionEvent event) {
        String search = MainPartSearchTxt.getText().trim();
        ObservableList<Part> result = Inventory.lookupPart(search);
        try {
            while (result.size() == 0){
                int partId = Integer.parseInt(search);
                result.add(Inventory.lookupPart(partId));
            }
            mainPartsTable.setItems(result);
        } catch (NumberFormatException exception){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("No part was found.");
            alert.showAndWait();
        }
    }

    /** This is the method for the Product Search Field.
     * Contains an error message for the not-found scenario.
     * @param event What happens when the button is clicked. */
    public void OnActionProdSearch(ActionEvent event) {
        String search = MainProductSearchTxt.getText().trim();
        ObservableList<Product> result = Inventory.lookupProduct(search);
        try {
            while (result.size() == 0){
                int prodId = Integer.parseInt(search);
                result.add(Inventory.lookupProduct(prodId));
            }
            mainProductsTable.setItems(result);
        } catch (NumberFormatException exception){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("No product was found.");
            alert.showAndWait();
        }
    }

    /** This is the method for the Exit Button. */
    @FXML
    public Button ExitMainBtn;
    public void OnExitMainBtn(ActionEvent event){
        System.exit(0);
    }

}