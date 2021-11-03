package com.techverito;

import com.techverito.dao.Wallet;
import org.junit.jupiter.api.Test;

import static com.techverito.util.Currency.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class WalletTest {

    //    As a user
    //    I want to add money to my wallet
    //    So that I can keep it ready for purchases
    @Test
    void add1INRToEmptyWalletTotalBalanceBecomes1INR() {
        Wallet wallet = new Wallet(INR);
        wallet.credit(1, INR);

        double walletBalance = wallet.balance();
        assertEquals(1, walletBalance);
    }

    @Test
    void add_2_INRtoWalletHaving_1_INRbalanceBecomes_3_INR() {
        Wallet wallet = new Wallet(INR);
        wallet.credit(1, INR);
        wallet.credit(2, INR);

        double walletBalance = wallet.balance();
        assertEquals(3, walletBalance);
    }

    @Test
    void add_1_USDToEmptyUSDWalletTotalBalanceBecomes_1_USD() {
        Wallet wallet = new Wallet(USD);
        wallet.credit(1, USD);

        double walletBalance = wallet.balance();
        assertEquals(1, walletBalance);
    }

    @Test
    void add_1_USDToEmptyINRWalletTotalBalanceBecomes_70_INR() {
        Wallet wallet = new Wallet(INR);
        wallet.credit(1, USD);

        double walletBalance = wallet.balance();
        assertEquals(70, walletBalance);
    }

    @Test
    void add_2_USDToINRWalletWith10INRTotalBalanceBecomes_150_INR() {
        Wallet wallet = new Wallet(INR);

        wallet.credit(10, INR);
        wallet.credit(2, USD);

        double walletBalance = wallet.balance();
        assertEquals(150, walletBalance);
    }

    @Test
    void add_70_INRToEmptyUSDWalletTotalBalanceBecomes_1_USD() {
        Wallet wallet = new Wallet(USD);

        wallet.credit(70, INR);

        double walletBalance = wallet.balance();
        assertEquals(1, walletBalance);
    }

    @Test
    void add_210_INRToUSDWalletWith_1_USDTotalBalanceBecomes_4_USD() {
        Wallet wallet = new Wallet(USD);

        wallet.credit(1, USD);
        wallet.credit(210, INR);

        double walletBalance = wallet.balance();
        assertEquals(4, walletBalance);
    }

    @Test
    void add_140_INRtoEmptyGBPWalletTotalBalance_1_GBP() {
        Wallet wallet = new Wallet(GBP);
        wallet.credit(140,INR);
        double walletBalance = wallet.balance();
        assertEquals(1,walletBalance);
    }

    @Test
    void add_280_INRtoEmptyGBPWalletTotalBalance_2_GBP() {
        Wallet wallet = new Wallet(GBP);
        wallet.credit(280,INR);
        double walletBalance = wallet.balance();
        assertEquals(2,walletBalance);
    }

    @Test
    void add_140_INRTwiceToEmptyGBPWalletTotalBalanceBecomes_2_GBP() {
        Wallet wallet = new Wallet(GBP);
        wallet.credit(140,INR);
        wallet.credit(140,INR);
        double walletBalance = wallet.balance();
        assertEquals(2,walletBalance);
    }

    @Test
    void add_1_GBPToEmptyINRWalletTotalBalanceBecomes_140_INR() {
        Wallet wallet = new Wallet(INR);
        wallet.credit(1,GBP);
        double walletBalance = wallet.balance();
        assertEquals(140,walletBalance);
    }

    @Test
    void add_1_GBPTwiceToINRWalletTotalBalanceBecomes_280_INR() {
        Wallet wallet = new Wallet(INR);
        wallet.credit(1,GBP);
        wallet.credit(1,GBP);
        double walletBalance = wallet.balance();
        assertEquals(280,walletBalance);
    }
}
