package com.techverito;

import com.techverito.business.Cart;
import com.techverito.business.CartItem;
import com.techverito.business.PaymentOption;
import com.techverito.dao.BuyAndGetMore;
import com.techverito.dao.InventoryProduct;
import com.techverito.dao.Money;
import com.techverito.dao.Wallet;
import com.techverito.exception.ProductNotFoundInCartException;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.techverito.util.Currency.INR;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

// @ExtendWith(MockitoExtension.class)
class CartTest {

  @Test
  void oneMockAppleAddedToCartPriceBecomesTen() {
    InventoryProduct mockProduct = mock(InventoryProduct.class);
    when(mockProduct.offerPrice(1)).thenReturn(10);
    CartItem cartItemApple = new CartItem(mockProduct, 1);
    Cart cart = new Cart();
    cart.addItem(cartItemApple);
    assertEquals(10, cart.totalPrice());
  }

  @Test
  void oneAppleAddedToCartPriceBecomesTen() {
    InventoryProduct apple = new InventoryProduct(10);
    CartItem cartItemApple = new CartItem(apple, 1);
    Cart cart = new Cart();
    cart.addItem(cartItemApple);
    assertEquals(10, cart.totalPrice());
  }

  @Test
  void twoOrangesAddedToCartPriceBecomesForty() {
    InventoryProduct orange = new InventoryProduct(20);
    CartItem cartItemOranges = new CartItem(orange, 2);
    Cart cart = new Cart();
    cart.addItem(cartItemOranges);
    assertEquals(40, cart.totalPrice());
  }

  @Test
  void totalPriceIsTwentyWhenTwoOrangesAddedToCartWithBuyOneGetOneOffer() {
    InventoryProduct orange = new InventoryProduct(20);
    BuyAndGetMore buyAndGetMore = new BuyAndGetMore(1, 1);
    orange.activateOffer(buyAndGetMore);
    CartItem cartItemOranges = new CartItem(orange, 2);
    Cart cart = new Cart();
    cart.addItem(cartItemOranges);

    assertEquals(20, cart.totalPrice());
  }

  @Test
  void totalPriceIsFortyWhenFiveOrangesAddedToCartWithBuyTwoGetThreeOffer() {
    InventoryProduct orange = new InventoryProduct(20);
    BuyAndGetMore buyAndGetMore = new BuyAndGetMore(2, 3);
    orange.activateOffer(buyAndGetMore);
    CartItem cartItemOranges = new CartItem(orange, 5);
    Cart cart = new Cart();
    cart.addItem(cartItemOranges);

    assertEquals(40, cart.totalPrice());
  }

  @Test
  void totalPriceIsHundredWhenElevenOrangesAddedToCartWithBuyTwoGetThreeOffer() {
    InventoryProduct orange = new InventoryProduct(20);
    BuyAndGetMore buyAndGetMore = new BuyAndGetMore(2, 3);
    orange.activateOffer(buyAndGetMore);
    CartItem cartItemOranges = new CartItem(orange, 11);
    Cart cart = new Cart();
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
    Cart cart = new Cart();
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

    Cart cart = new Cart();
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

    Cart cart = new Cart();
    cart.addItem(cartItemApple);

    ProductNotFoundInCartException exception =
        assertThrows(ProductNotFoundInCartException.class, () -> cart.delete(cartItemOrange));

    assertEquals("Product not found in cart", exception.getMessage());
  }

  @Test
  void deletingAnItemShouldNotBeAllowedOnAnEmptyCart() {
    InventoryProduct apple = new InventoryProduct(10);
    CartItem cartItemApple = new CartItem(apple, 1);

    Cart cart = new Cart();

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
    Cart cart = new Cart();

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
    Cart cart = new Cart();

    cart.addItem(cartItemApple);
    cart.addItem(cartItemOrange);
    cart.addItem(cartItemGrapes);

    List<CartItem> cheapestCartItems = cart.sort(Cart.COSTLIER);
    assertEquals(List.of(cartItemOrange, cartItemApple, cartItemGrapes), cheapestCartItems);
  }

  @Test
  void cartWillBeEmptyAfterCheckoutWithWallet() {

    Wallet wallet = spy(new Wallet(INR));
    wallet.credit(new Money(10, INR));
    InventoryProduct apple = new InventoryProduct(10);
    CartItem cartItemApple = new CartItem(apple, 1);
    Cart cart = new Cart();
    cart.addItem(cartItemApple);

    cart.checkout(wallet);

    assertEquals(0, cart.size());
  }

  @Test
  void moneyWillDebitedFromWalletWhenCartCheckoutWithWallet() {
    Wallet wallet = spy(new Wallet(INR));
    wallet.credit(new Money(10, INR));
    InventoryProduct apple = new InventoryProduct(10);
    CartItem cartItemApple = new CartItem(apple, 1);
    Cart cart = new Cart();
    cart.addItem(cartItemApple);

    cart.checkout(wallet);

    verify(wallet, times(1)).debit(new Money(10, INR));
  }

  @Test
  void cartWillBeEmptyAfterCheckoutWithCreditCard() {
    PaymentStub creditCardStub = spy(new PaymentStub());
    InventoryProduct apple = new InventoryProduct(10);
    CartItem cartItemApple = new CartItem(apple, 1);
    Cart cart = new Cart();
    cart.addItem(cartItemApple);
    cart.checkout(creditCardStub);
    verify(creditCardStub, times(1)).debit(new Money(10, INR));
  }

  class PaymentStub implements PaymentOption {

    @Override
    public boolean debit(Money money) {
      return true;
    }
  }
}
