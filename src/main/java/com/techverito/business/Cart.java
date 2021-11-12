package com.techverito.business;

import com.techverito.dao.Money;
import com.techverito.dao.Wallet;
import com.techverito.exception.ProductNotFoundInCartException;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static com.techverito.util.Currency.INR;

public class Cart {

  public static final Comparator<CartItem> CHEAPEST = Comparator.comparing(CartItem::price);
  public static final Comparator<CartItem> COSTLIER =
      Comparator.comparing(CartItem::price).reversed();


  private final List<CartItem> cartItems = new ArrayList<>();

  public void addItem(CartItem cartItem) {
    this.cartItems.add(cartItem);
  }

  public int totalPrice() {
    return cartItems.stream().mapToInt(CartItem::price).sum();
  }

  public void delete(CartItem cartItem) {
    if (this.cartItems.contains(cartItem)) {
      this.cartItems.remove(cartItem);
    } else {
      throw new ProductNotFoundInCartException();
    }
  }

  public List<CartItem> sort(Comparator<CartItem> comparator) {
    this.cartItems.sort(comparator);
    return this.cartItems;
  }

  public void checkout(PaymentOption paymentOption) {
    Money checkoutMoney = new Money(totalPrice(), INR);
    if(paymentOption.debit(checkoutMoney)) cartItems.clear();
  }

  public int size() {
    return cartItems.size();
  }
}
