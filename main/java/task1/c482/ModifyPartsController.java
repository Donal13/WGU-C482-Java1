package task1.c482;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

import model.InHouse;
import model.OutSourced;
import model.Inventory;
import model.Part;

/**This is the class that defines the Modify Parts Controller.*/
public class ModifyPartsController {

    /**This is the toggle group for the InHouse and OutSourced radio buttons. */
    @FXML
    public ToggleGroup modPartToggle;

    /**This is the InHouse radio button. */
    @FXML
    public RadioButton ModPartinHouse;

    /**This is the OutSourced radio button. */
    @FXML
    public RadioButton ModPartOutsourced;

    @FXML
    public Label ModMachCustSwapLbl;
    @FXML
    public TextField ModMachCustSwapTxt;

    @FXML
    public TextField ModPartIDTxt;
    @FXML
    public TextField ModPartNameTxt;
    @FXML
    public TextField ModPartInvTxt;
    @FXML
    public TextField ModPartPriceTxt;
    @FXML
    public TextField ModPartMaxTxt;
    @FXML
    public TextField ModPartMinTxt;

    @FXML
    public Button ModPartSaveBtn;
    @FXML
    public Button ModPartCancelBtn;

    public int ModPartIndex;

    Stage stage;
    Parent scene;

    /** This is the method for the InHouse Radio Button.
     * @param actionEvent What happens when you click the InHouse radio button. */
    public void onModPartInhouse(ActionEvent actionEvent) {
        ModMachCustSwapLbl.setText("Machine ID:");
    }

    /** This is the method for the Outsourced Radio Button.
     * @param actionEvent What happens when you click the OutSourced radio button.  */
    public void onModPartOutsourced(ActionEvent actionEvent) {
        ModMachCustSwapLbl.setText("Company Name:");
    }

    /** This is the method for the Save Button.
     *@param event What happens when the button is clicked.
     *@throws IOException When an error happens. */
    public void ModPartSaveBtn(ActionEvent event) throws IOException {
        try{
            int partId;
            try {
                partId = Integer.parseInt(ModPartIDTxt.getText().trim());
            } catch (NumberFormatException e) {
                throw new Exception("Part ID must be an integer.");
            }
            String partName = ModPartNameTxt.getText().trim();
            if (partName.isEmpty()) {
                throw new Exception("Part name cannot be empty.");
            }
            int partStock;
            try {
                partStock = Integer.parseInt(ModPartInvTxt.getText().trim());
            } catch (NumberFormatException e) {
                throw new Exception("Stock value must be an integer with no decimals.");
            }
            double partPrice;
            try {
                partPrice = Double.parseDouble(ModPartPriceTxt.getText().trim());
            } catch (NumberFormatException e) {
                throw new Exception("Price must include two decimal digits. Ex: 0.00 ");
            }
            int partMin;
            try {
                partMin = Integer.parseInt(ModPartMinTxt.getText().trim());
            } catch (NumberFormatException e) {
                throw new Exception("Minimum value must be an integer with no decimals.");
            }
            int partMax;
            try {
                partMax = Integer.parseInt(ModPartMaxTxt.getText().trim());
            } catch (NumberFormatException e) {
                throw new Exception("Maximum value must be an integer with no decimals.");
            }

            if (partMax < partMin) {
                throw new Exception("Minimum value may not exceed maximum value.");
            }

            if (partStock < partMin || partStock > partMax) {
                throw new Exception("Current stock must be more than or equal to the minimum and less than or equal to the maximum.");
            }

            if (ModPartinHouse.isSelected()) {
                int machId;
                try {
                    machId = Integer.parseInt(ModMachCustSwapTxt.getText().trim());
                } catch (NumberFormatException e) {
                    throw new Exception("Machine ID must be an integer.");
                }
                Inventory.updatePart(ModPartIndex, new InHouse(partId, partName, partPrice, partStock, partMin, partMax, machId));
            } else if (ModPartOutsourced.isSelected()) {
                String companyName = ModMachCustSwapTxt.getText().trim();
                if (companyName.isEmpty()) {
                    throw new Exception("Company name cannot be blank.");
                }
                Inventory.updatePart(ModPartIndex, new OutSourced(partId, partName, partPrice, partStock, partMin, partMax, companyName));
            }
            OnActionMainFormBtn(event);
        } catch (Exception invalid){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid Error");
            alert.setContentText(invalid.getMessage());
            alert.showAndWait();
        }
    }

    /** This is the method that populates the text fields with the correct data.
     * Not sure if "parse" was the right word to use here, but I guess it doesn't really matter.
     * @param part The part to be shown. */
    public void parseData(Part part){
        if (part instanceof OutSourced){
            ModPartOutsourced.setSelected(true);
            ModMachCustSwapLbl.setText("Company Name:");
            ModMachCustSwapTxt.setText(((OutSourced) part).getCompanyName());
        } else if (part instanceof InHouse) {
            ModPartinHouse.setSelected(true);
            ModMachCustSwapTxt.setText(Integer.toString(((InHouse) part).getMachineId()));
        }
        ModPartIDTxt.setText(Integer.toString(part.getId()));
        ModPartInvTxt.setText(Integer.toString(part.getStock()));
        ModPartNameTxt.setText(part.getName());
        ModPartPriceTxt.setText(Double.toString(part.getPrice()));
        ModPartMinTxt.setText(Integer.toString(part.getMin()));
        ModPartMaxTxt.setText(Integer.toString(part.getMax()));
        ModPartIndex = Inventory.getAllParts().indexOf(part);
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