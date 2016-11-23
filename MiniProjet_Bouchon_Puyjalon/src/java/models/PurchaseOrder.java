/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

/**
 *
 * @author マルゴ
 */
public class PurchaseOrder {
    // TODO : ajouter les autres propriétés
	private int idProduit;
	private int idCustomer;
        private int quantite;

	public PurchaseOrder(int idProduit, int idCustomer, int quantite) {
		this.idProduit = idProduit;
		this.idCustomer = idCustomer;
                this.quantite = quantite;
	}
    
}
