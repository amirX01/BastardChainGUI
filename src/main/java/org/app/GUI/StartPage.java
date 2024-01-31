package org.app.GUI;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.app.CLI.*;

import java.net.URL;
import java.security.PublicKey;

public class StartPage extends Application {
    public static Stage stage;

    public static void main(String[] args){launch(args);}

    public static void addBlock(Block newBlock) {
        newBlock.mineBlock(BastardChain.difficulty);
        BastardChain.blockchain.add(newBlock);
    }
    public void start(Stage stage) throws Exception {
        Crypt.initialize();
        BastardChain.walletA = new Wallet();
        BastardChain.walletB = new Wallet();
        Wallet coinbase = new Wallet();
        BastardChain.genesisTransaction = new Transaction(coinbase.publicKey, BastardChain.walletA.publicKey, 100f, null);
        BastardChain.genesisTransaction.generateSignature(coinbase.privateKey);	 //manually sign the genesis transaction
        BastardChain.genesisTransaction.transactionId = "0"; //manually set the transaction id
        BastardChain.genesisTransaction.outputs.add(new TransactionOutput(BastardChain.genesisTransaction.reciepient, BastardChain.genesisTransaction.value, BastardChain.genesisTransaction.transactionId)); //manually add the Transactions Output
        BastardChain.UTXOs.put(BastardChain.genesisTransaction.outputs.getFirst().id, BastardChain.genesisTransaction.outputs.getFirst()); //its important to store our first transaction in the UTXOs list.

        System.out.println("Creating and Mining Genesis block... ");
        Block genesis = new Block("0");
        genesis.addTransaction(BastardChain.genesisTransaction);
        addBlock(genesis);

        //testing
        Block block1 = new Block(genesis.hash);
        System.out.println("\nWalletA's balance is: " + BastardChain.walletA.getBalance());
        System.out.println("\nWalletA is Attempting to send funds (40) to WalletB...");
        block1.addTransaction(BastardChain.walletA.sendFunds(BastardChain.walletB.publicKey, 40f));
        addBlock(block1);
        System.out.println("\nWalletA's balance is: " + BastardChain.walletA.getBalance());
        System.out.println("WalletB's balance is: " + BastardChain.walletB.getBalance());
        //Graphic
        StartPage.stage = stage;
        new SignupPage().start(StartPage.stage);
    }
    public static void chargeNewAccounts(PublicKey to) {
        Crypt.initialize();
        Block block1 = new Block(BastardChain.blockchain.getLast().hash);
        System.out.println("\nWalletA's balance is: " + BastardChain.walletA.getBalance());
        System.out.println("\nWalletA is Attempting to send funds (40) to NewWallet...");
        block1.addTransaction(BastardChain.walletA.sendFunds(to, 40f));
        addBlock(block1);
    }
}
