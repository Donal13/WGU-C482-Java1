package model;

/** This is the class that defines the OutSourced model.*/
public class OutSourced extends Part {
    private String companyName;

    /** This method is the OutSourced constructor.
     * @param id The part ID.
     * @param name The part name.
     * @param price The part price.
     * @param stock Current stock level of the part.
     * @param min Minimum allowed stock level.
     * @param max Maximum allowed stock level.
     * @param companyName The Company Name for the new part. */
    public OutSourced(int id, String name, double price, int stock, int min, int max, String companyName) {
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
    }

    /** This is the method for getting the companyName. */
    public String getCompanyName() {
        return companyName;
    }

    /** This is the method for setting the companyName. */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

}