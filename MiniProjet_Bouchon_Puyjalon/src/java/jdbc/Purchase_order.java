/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jdbc; 

/**
 *
 * @author vince
 */
public class Purchase_order {
    
    private int idPurchase;
    private int idCustomer;
    private int idProduit;
    private int quantite;
    
    public Purchase_order(){
        this.idPurchase = -1;
        this.idCustomer = -1;
        this.idProduit = -1;
        this.quantite = 0;
    }
    
    /**
	 * Get the value of customerId
	 *
	 * @return the value of customerId
	 */
	public int getIdPurchase() {
		return idPurchase;
	}

	/**
	 * Get the value of name
	 *
	 * @return the value of name
	 */
	public int getIdCustomer() {
		return idCustomer;
	}
        
        /**
	 * Get the value of mail
	 *
	 * @return the value of mail
	 */
	public int getIdProduit() {
		return idProduit;
	}
        
	/**
	 * Get the value of addressLine1
	 *
	 * @return the value of addressLine1
	 */
	public int getQuantite() {
		return quantite;
	}

        
        public void setIdPurchase(int id){
            this.idPurchase = id;
        }
        
        public void setIdCustomer(int id){
            this.idCustomer = id;
        }
        
        public void setIdProduit(int id){
           this.idProduit = id;
        }
        
        public void setQuantite(int qtt){
           this.quantite = qtt;
        }
        
}
