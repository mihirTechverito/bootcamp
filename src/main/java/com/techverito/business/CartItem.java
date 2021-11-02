package com.techverito.business;

import com.techverito.dao.InventoryProduct;

public class CartItem {

  private final InventoryProduct inventoryProduct;

  private final int quantity;

  public CartItem(InventoryProduct inventoryProduct, int quantity) {
    this.inventoryProduct = inventoryProduct;
    this.quantity = quantity;
  }

  public int price() {
    return inventoryProduct.offerPrice(quantity);
  }
}
