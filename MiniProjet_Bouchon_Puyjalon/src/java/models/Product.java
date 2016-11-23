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
public class Product {
    // TODO : ajouter les autres propriétés
	private String description;
        private int productId;
        private String manufacturer;
        private String productType;
        private float productCost;
        private int quantityInStock;

	public Product(int productId) {
                this.productId = productId;
	}

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @return the productId
     */
    public int getProductId() {
        return productId;
    }

    /**
     * @return the manufacturerId
     */
    public String getManufacturer() {
        return manufacturer;
    }

    /**
     * @return the productType
     */
    public String getProductType() {
        return productType;
    }

    /**
     * @return the productCost
     */
    public float getProductCost() {
        return productCost;
    }

    /**
     * @return the quantityInStock
     */
    public int getQuantityInStock() {
        return quantityInStock;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @param productId the productId to set
     */
    public void setProductId(int productId) {
        this.productId = productId;
    }

    /**
     * @param manufacturer the manufacturerId to set
     */
    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    /**
     * @param productType the productType to set
     */
    public void setProductType(String productType) {
        this.productType = productType;
    }

    /**
     * @param productCost the productCost to set
     */
    public void setProductCost(float productCost) {
        this.productCost = productCost;
    }

    /**
     * @param quantityInStock the quantityInStock to set
     */
    public void setQuantityInStock(int quantityInStock) {
        this.quantityInStock = quantityInStock;
    }
    
}
