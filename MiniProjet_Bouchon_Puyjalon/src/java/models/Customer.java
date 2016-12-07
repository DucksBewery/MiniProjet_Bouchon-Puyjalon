package models;

/**
 * Correspond à un enregistrement de la table Customer
 */
public class Customer {

	private int customerId;
	private String name;
	private String addressLine1;

        /**
         * Constructeur
         * 
         * @param customerId
         * @param name
         * @param addressLine1 
         */
	public Customer(int customerId, String name, String addressLine1) {
		this.customerId = customerId;
		this.name = name;
		this.addressLine1 = addressLine1;
	}
        
    // -----------------------------------------------------------------------//
    // --------------------------- GETTERS -----------------------------------//
    // -----------------------------------------------------------------------//

	/**
	 * Get the value of customerId
	 *
	 * @return the value of customerId
	 */
	public int getCustomerId() {
		return customerId;
	}

	/**
	 * Get the value of name
	 *
	 * @return the value of name
	 */
	public String getName() {
		return name;
	}
        
	/**
	 * Get the value of addressLine1
	 *
	 * @return the value of addressLine1
	 */
	public String getAddressLine1() {
		return addressLine1;
	}
        
    // -----------------------------------------------------------------------//
    // --------------------------- SETTERS -----------------------------------//
    // -----------------------------------------------------------------------//

        /**
         * Set the value of customerId
         * 
         * @param id 
         */
        public void setCustomerId(int id){
            this.customerId = id;
        }
        
        /**
         * Set the value of name
         * 
         * @param name 
         */
        public void setName(String name){
            this.name = name;
        }
        
        /**
         * Set the value of addressLine1
         * 
         * @param addressLine1 
         */
        public void setAddressLine1(String addressLine1){
           this.addressLine1 = addressLine1;
        }
}
