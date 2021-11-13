package com.techverito.business;

import com.techverito.dao.Money;
import com.techverito.dao.User;
import com.techverito.exception.ProductNotFoundInCartException;
import com.techverito.service.Event;
import com.techverito.service.EventData;
import com.techverito.service.EventStore;
import com.techverito.service.NotifierFactory;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static com.techverito.util.Currency.INR;

public class Cart {

  public static final Comparator<CartItem> CHEAPEST = Comparator.comparing(CartItem::price);
  public static final Comparator<CartItem> COSTLIER =
      Comparator.comparing(CartItem::price).reversed();

  private final List<CartItem> cartItems = new ArrayList<>();

  private final User user;

  public Cart(User user) {
    this.user = user;
  }

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

  public void checkout(PaymentMethod paymentOption) {
    Money checkoutMoney = new Money(totalPrice(), INR);
    if (paymentOption.charge(checkoutMoney)){
      cartItems.clear();
      EventStore.getInstance().publishEvent(Event.CHECKOUT, new EventData<>(this.user));
    }
  }

  public int size() {
    return cartItems.size();
  }
}
