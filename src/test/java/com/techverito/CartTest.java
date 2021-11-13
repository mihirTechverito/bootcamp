package com.techverito;

import com.techverito.business.CheckoutData;
import com.techverito.business.Money;
import com.techverito.dao.Cart;
import com.techverito.business.CartItem;
import com.techverito.business.PaymentMethod;
import com.techverito.dao.*;
import com.techverito.exception.ProductNotFoundInCartException;
import com.techverito.service.Event;
import com.techverito.service.EventStore;
import com.techverito.stubs.SubscriberStub;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.techverito.util.Currency.INR;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

// @ExtendWith(MockitoExtension.class)
class CartTest {

  @Test
  void oneMockAppleAddedToCartPriceBecomesTen() {
    InventoryProduct mockProduct = mock(InventoryProduct.class);
    when(mockProduct.offerPrice(1)).thenReturn(10);
    CartItem cartItemApple = new CartItem(mockProduct, 1);
    Cart cart = new Cart(null);
    cart.addItem(cartItemApple);
    assertEquals(10, cart.totalPrice());
  }

  @Test
  void oneAppleAddedToCartPriceBecomesTen() {
    InventoryProduct apple = new InventoryProduct(10);
    CartItem cartItemApple = new CartItem(apple, 1);
    Cart cart = new Cart(null);
    cart.addItem(cartItemApple);
    assertEquals(10, cart.totalPrice());
  }

  @Test
  void twoOrangesAddedToCartPriceBecomesForty() {
    InventoryProduct orange = new InventoryProduct(20);
    CartItem cartItemOranges = new CartItem(orange, 2);
    Cart cart = new Cart(null);
    cart.addItem(cartItemOranges);
    assertEquals(40, cart.totalPrice());
  }

  @Test
  void totalPriceIsTwentyWhenTwoOrangesAddedToCartWithBuyOneGetOneOffer() {
    InventoryProduct orange = new InventoryProduct(20);
    BuyAndGetMore buyAndGetMore = new BuyAndGetMore(1, 1);
    orange.activateOffer(buyAndGetMore);
    CartItem cartItemOranges = new CartItem(orange, 2);
    Cart cart = new Cart(null);
    cart.addItem(cartItemOranges);

    assertEquals(20, cart.totalPrice());
  }

  @Test
  void totalPriceIsFortyWhenFiveOrangesAddedToCartWithBuyTwoGetThreeOffer() {
    InventoryProduct orange = new InventoryProduct(20);
    BuyAndGetMore buyAndGetMore = new BuyAndGetMore(2, 3);
    orange.activateOffer(buyAndGetMore);
    CartItem cartItemOranges = new CartItem(orange, 5);
    Cart cart = new Cart(null);
    cart.addItem(cartItemOranges);

    assertEquals(40, cart.totalPrice());
  }

  @Test
  void totalPriceIsHundredWhenElevenOrangesAddedToCartWithBuyTwoGetThreeOffer() {
    InventoryProduct orange = new InventoryProduct(20);
    BuyAndGetMore buyAndGetMore = new BuyAndGetMore(2, 3);
    orange.activateOffer(buyAndGetMore);
    CartItem cartItemOranges = new CartItem(orange, 11);
    Cart cart = new Cart(null);
    cart.addItem(cartItemOranges);

    assertEquals(100, cart.totalPrice());
  }

  //  As a user
  //  I want to delete a product from Cart
  //  So that I can buy only specific products
  @Test
  void givenOneAppleInCartWhenDeletedFromCartTotalPriceShouldBeZero() {
    InventoryProduct apple = new InventoryProduct(10);
    CartItem cartItemApple = new CartItem(apple, 1);
    Cart cart = new Cart(null);
    cart.addItem(cartItemApple);
    cart.delete(cartItemApple);

    assertEquals(0, cart.totalPrice());
  }

  @Test
  void appleAndOrangeInCartWhenOneAppleIsDeletedFromCartPriceShouldBeTwenty() {
    InventoryProduct apple = new InventoryProduct(10);
    InventoryProduct orange = new InventoryProduct(20);
    CartItem cartItemApple = new CartItem(apple, 1);
    CartItem cartItemOrange = new CartItem(orange, 1);

    Cart cart = new Cart(null);
    cart.addItem(cartItemApple);
    cart.addItem(cartItemOrange);
    cart.delete(cartItemApple);

    assertEquals(20, cart.totalPrice());
  }

  @Test
  void deletingAnItemWhichWasNotAddedToCartIsNotAllowed() {
    InventoryProduct apple = new InventoryProduct(10);
    InventoryProduct orange = new InventoryProduct(20);
    CartItem cartItemApple = new CartItem(apple, 1);
    CartItem cartItemOrange = new CartItem(orange, 1);

    Cart cart = new Cart(null);
    cart.addItem(cartItemApple);

    ProductNotFoundInCartException exception =
        assertThrows(ProductNotFoundInCartException.class, () -> cart.delete(cartItemOrange));

    assertEquals("Product not found in cart", exception.getMessage());
  }

  @Test
  void deletingAnItemShouldNotBeAllowedOnAnEmptyCart() {
    InventoryProduct apple = new InventoryProduct(10);
    CartItem cartItemApple = new CartItem(apple, 1);

    Cart cart = new Cart(null);

    ProductNotFoundInCartException exception =
        assertThrows(ProductNotFoundInCartException.class, () -> cart.delete(cartItemApple));

    assertEquals("Product not found in cart", exception.getMessage());
  }

  // As a user
  // I want to sort cart items by their price from low to high
  // So that I could find out cheap cart item
  @Test
  void sortCartItemsByLowToHighCost() {
    InventoryProduct apple = new InventoryProduct(10);
    InventoryProduct orange = new InventoryProduct(20);
    InventoryProduct grapes = new InventoryProduct(5);
    CartItem cartItemApple = new CartItem(apple, 1);
    CartItem cartItemOrange = new CartItem(orange, 1);
    CartItem cartItemGrapes = new CartItem(grapes, 1);
    Cart cart = new Cart(null);

    cart.addItem(cartItemApple);
    cart.addItem(cartItemOrange);
    cart.addItem(cartItemGrapes);

    List<CartItem> cheapestCartItems = cart.sort(Cart.CHEAPEST);

    assertEquals(List.of(cartItemGrapes, cartItemApple, cartItemOrange), cheapestCartItems);
  }

  @Test
  void sortCartItemsByHighToLowCost() {
    InventoryProduct apple = new InventoryProduct(10);
    InventoryProduct orange = new InventoryProduct(20);
    InventoryProduct grapes = new InventoryProduct(5);
    CartItem cartItemApple = new CartItem(apple, 1);
    CartItem cartItemOrange = new CartItem(orange, 1);
    CartItem cartItemGrapes = new CartItem(grapes, 1);
    Cart cart = new Cart(null);

    cart.addItem(cartItemApple);
    cart.addItem(cartItemOrange);
    cart.addItem(cartItemGrapes);

    List<CartItem> cheapestCartItems = cart.sort(Cart.COSTLIER);
    assertEquals(List.of(cartItemOrange, cartItemApple, cartItemGrapes), cheapestCartItems);
  }

  @Test
  void cartWillBeEmptyAfterCheckoutWithWallet() {

    Wallet wallet = new Wallet(INR);
    wallet.credit(new Money(10, INR));
    InventoryProduct apple = new InventoryProduct(10);
    CartItem cartItemApple = new CartItem(apple, 1);
    Cart cart = new Cart(null);
    cart.addItem(cartItemApple);

    cart.checkout(wallet);

    assertEquals(0, cart.size());
  }

  @Test
  void moneyWillDebitedFromWalletWhenCartCheckoutWithWallet() {
    PaymentMethod wallet = mock(PaymentMethod.class);
    InventoryProduct apple = new InventoryProduct(10);
    CartItem cartItemApple = new CartItem(apple, 1);
    Cart cart = new Cart(null);
    cart.addItem(cartItemApple);

    cart.checkout(wallet);

    verify(wallet, times(1)).charge(new Money(10, INR));
  }

  @Test
  void cartWillBeEmptyAfterCheckoutWithCreditCard() { // This is a crime
    PaymentOptionStub creditCardStub = new PaymentOptionStub();
    InventoryProduct apple = new InventoryProduct(10);
    CartItem cartItemApple = new CartItem(apple, 1);
    Cart cart = new Cart(null);
    cart.addItem(cartItemApple);
    cart.checkout(creditCardStub);

    assertEquals(new Money(10, INR), creditCardStub.money());
  }

  class PaymentOptionStub implements PaymentMethod {

    private Money money;

    public Money money() {
      return money;
    }

    @Override
    public boolean charge(Money money) {
      this.money = money;
      return true;
    }
  }

  @Test
  void sendSMSNotificationToUserPostSuccessfulCheckout() {
    // Arrange
    PaymentMethod paymentMethod = mock(PaymentMethod.class);
    when(paymentMethod.charge(any())).thenReturn(true);
    InventoryProduct apple = new InventoryProduct(10);
    CartItem cartItemApple = new CartItem(apple, 1);

    User user = new User("123", "abc", PreferredCommunication.SMS);
    Cart cart = new Cart(user);
    cart.addItem(cartItemApple);
    EventStore eventStore = EventStore.getInstance();
    SubscriberStub userCheckoutSubscriberStub = new SubscriberStub(eventStore, Event.CHECKOUT);

    // Act
    cart.checkout(paymentMethod);
    CheckoutData data = (CheckoutData) userCheckoutSubscriberStub.eventData().data();
    //Assert
    assertEquals(user, data.user());
  }

  @Test
  void sendSMSNotificationToAccountStaffPostSuccessfulCheckout() {
    // Arrange
    PaymentMethod paymentMethod = mock(PaymentMethod.class);
    when(paymentMethod.charge(any())).thenReturn(true);
    InventoryProduct apple = new InventoryProduct(10);
    CartItem cartItemApple = new CartItem(apple, 1);

    User user = new User("123", "abc", PreferredCommunication.SMS);
    Cart cart = new Cart(user);
    cart.addItem(cartItemApple);
    EventStore eventStore = EventStore.getInstance();
    SubscriberStub accountStaffStub = new SubscriberStub(eventStore, Event.CHECKOUT);

    // Act
    cart.checkout(paymentMethod);
    CheckoutData dataAccountStaff = (CheckoutData) accountStaffStub.eventData().data();
    //Assert
    assertEquals(user, dataAccountStaff.user());
    assertEquals(new Money(10,INR), dataAccountStaff.spent());
  }


}
