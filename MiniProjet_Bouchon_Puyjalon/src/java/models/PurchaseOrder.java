package models;


public class PurchaseOrder {

    private int purchaseId;
    private int customer;
    private String product;
    private int productId;
    private int quantity;
    private float cost;
    private String salesDate;
    private String freightCompany;

    public PurchaseOrder(int customer) {
        this.customer = customer;
    }

    //------------- GETTERS ---------------//    
    /**
     * @return the customer
     */
    public int getCustomer() {
        return customer;
    }

    /**
     * @return the product
     */
    public String getProduct() {
        return product;
    }
    
    /**
     * @return the productId
     */
    public int getProductId() {
        return productId;
    }

    /**
     * @return the quantity
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * @return the cost
     */
    public float getCost() {
        return cost;
    }

    /**
     * @return the freightCompany
     */
    public String getFreightCompany() {
        return freightCompany;
    }

    /**
     * @return the salesDate
     */
    public String getSalesDate() {
        return salesDate;
    }

    /**
     * @return the purchaseId
     */
    public int getPurchaseId() {
        return purchaseId;
    }

    //------------- SETTERS ---------------//
    /**
     * @param customer the customer to set
     */
    public void setCustomer(int customer) {
        this.customer = customer;
    }

    /**
     * @param product the product to set
     */
    public void setProduct(String product) {
        this.product = product;
    }
    
    /**
     * @param productId the product to set
     */
    public void setProductId(int productId) {
        this.productId = productId;
    }

    /**
     * @param quantity the quantity to set
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /**
     * @param cost the cost to set
     */
    public void setCost(float cost) {
        this.cost = cost;
    }

    /**
     * @param freightCompany the freightCompany to set
     */
    public void setFreightCompany(String freightCompany) {
        this.freightCompany = freightCompany;
    }

    /**
     * @param salesDate the salesDate to set
     */
    public void setSalesDate(String salesDate) {
        this.salesDate = salesDate;
    }

    /**
     * @param purchaseId the salesDate to set
     */
    public void setPurchaseId(int purchaseId) {
        this.purchaseId = purchaseId;
    }
}
