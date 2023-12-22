package task1.c482;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.InHouse;
import model.Inventory;
import model.OutSourced;

import java.io.IOException;
import java.util.Objects;

/**This is the class that defines the Add Parts Controller.*/
public class AddPartsController {

    /**This is the toggle group for the InHouse and OutSourced radio buttons. */
    @FXML
    public ToggleGroup addPartToggle;

    /**This is the InHouse radio button. */
    @FXML
    public RadioButton AddPartinHouse;

    /**This is the OutSourced radio button. */
    @FXML
    public RadioButton AddPartOutsourced;

    @FXML
    private Label machCustSwapLbl;
    @FXML
    private TextField machCustSwapTxt;

    @FXML
    public TextField AddPartIDtxt;
    @FXML
    public TextField AddPartNameTxt;
    @FXML
    public TextField AddPartInvTxt;
    @FXML
    public TextField AddPartPriceTxt;
    @FXML
    public TextField AddPartMaxTxt;
    @FXML
    public TextField AddPartMinTxt;

    @FXML
    public Button addPartSaveBtn;
    @FXML
    public Button addPartCancelBtn;

    Stage stage;
    Parent scene;

    /** This is the method for the InHouse Radio Button.
     * @param actionEvent What happens when you click the InHouse radio button. */
    public void onAddPartInhouse(ActionEvent actionEvent) {
        machCustSwapLbl.setText("Machine ID:");
        machCustSwapTxt.setText("");
    }

    /** This is the method for the Outsourced Radio Button.
     * @param actionEvent What happens when you click the OutSourced radio button. */
    public void onAddPartOutsourced(ActionEvent actionEvent) {
        machCustSwapLbl.setText("Company Name:");
        machCustSwapTxt.setText("");
    }

    /** This is the method for the Save Button.
     * @param event What happens when the button is clicked.
     * @throws IOException When an error happens. */
    public void AddPartSaveBtn(ActionEvent event) throws IOException{
        int tempid = (int) (Math.random() * 202);
        for (int i = 0; i < Inventory.getAllParts().size(); i++){
            if (tempid <= Inventory.getAllParts().get(i).getId()) tempid =  Inventory.getAllParts().get(i).getId() + 1;
        }
        try {
             String partName = AddPartNameTxt.getText().trim();
             if (partName.isEmpty()){
                 throw new Exception("You must enter a name.");
             }
             int partStock;
             try {
                 partStock = Integer.parseInt(AddPartInvTxt.getText().trim());
             } catch (NumberFormatException e){
                 throw new Exception("Stock value must be an integer with no decimals.");
             }
             double partPrice;
             try { partPrice = Double.parseDouble(AddPartPriceTxt.getText().trim());
             } catch (NumberFormatException e) {
                 throw new Exception("Price must include two decimal digits. Ex: 0.00 ");
             }
             int partMin;
            try { partMin = Integer.parseInt(AddPartMinTxt.getText().trim());
            } catch (NumberFormatException e) {
                throw new Exception("Minimum value must be an integer with no decimals.");
            }
             int partMax;
            try { partMax = Integer.parseInt(AddPartMaxTxt.getText().trim());
            } catch (NumberFormatException e) {
                throw new Exception("Maximum value must be an integer with no decimals.");
            }

          if (partMin > partMax){
              throw new Exception("Minimum value may not exceed maximum value.");
          }

          if (partStock > partMax || partStock < partMin) {
              throw new Exception("Current stock value must be between the minimum and maximum values.");
          }

          if (AddPartinHouse.isSelected()){
              int machID;
              try{
                  machID = Integer.parseInt(machCustSwapTxt.getText().trim());
              } catch (NumberFormatException e){
                  throw new Exception("Machine ID must be an integer.");
              }
              Inventory.addPart(new InHouse(tempid, partName, partPrice, partStock, partMin, partMax, machID));
          } else {
              String companyName = machCustSwapTxt.getText().trim();
              if (companyName.isEmpty()){
                  throw new Exception("Company name cannot be blank.");
              }
              Inventory.addPart(new OutSourced(tempid, partName, partPrice, partStock, partMin, partMax, companyName));
          }
          OnActionMainFormBtn(event);
        } catch (Exception invalid){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid Error");
            alert.setContentText(invalid.getMessage());
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