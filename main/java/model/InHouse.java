package model;
/** This is the class that defines the InHouse model.*/
public class InHouse extends Part {

    int machineId;

    /** This method is the InHouse constructor.
     * @param id The part ID.
     * @param name The part name.
     * @param price The part price.
     * @param stock Current stock level of the part.
     * @param min Minimum allowed stock level.
     * @param max Maximum allowed stock level.
     * @param machineId The machine ID for the new part. */
    public InHouse(int id, String name, double price, int stock, int min, int max, int machineId) {
        super(id, name, price, stock, min, max);
        this.machineId = machineId;
    }

    /** This is the method for getting the machine ID. */
    public int getMachineId(){
        return machineId;
    }

    /** This is the method for setting the machine ID. */
    public void setMachineId(int machineId){
        this.machineId = machineId;
    }

}