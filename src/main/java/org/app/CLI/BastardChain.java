package org.app.CLI;

import java.security.Security;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class BastardChain {

	public static int difficulty = 5;
	public static float minimumTransaction = 1f;
	public static ArrayList<Block> blockchain = new ArrayList<>();
	public static HashMap<String, TransactionOutput> UTXOs = new HashMap<>();
	public static Wallet walletA;
	public static Wallet walletB;
	public static Transaction genesisTransaction;



	public static Boolean isChainValid() {
		Block currentBlock;
		Block previousBlock;

		for(int i=1; i < blockchain.size(); i ++) {
			currentBlock = blockchain.get(i);
			previousBlock = blockchain.get(i - 1);
			if(!currentBlock.hash.equals(currentBlock.calculateHash()) ){
				System.out.println("Current Hashes not equal");
				return false;
			}
			if(!previousBlock.hash.equals(currentBlock.previousHash) ) {
				System.out.println("Previous Hashes not equal");
				return false;
			}
		}
		return true;
	}
	public static void addBlock(Block newBlock) {
		newBlock.mineBlock(difficulty);
		blockchain.add(newBlock);
	}

	public static void main(String[] args) {
		//Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
		Crypt.initialize();
		/*blockchain.add(new Block("Hi im the first block", "0"));
		System.out.println("Trying to Mine block 1... ");
		blockchain.getFirst().mineBlock(difficulty);

		blockchain.add(new Block("Yo im the second block", blockchain.getLast().hash));
		System.out.println("Trying to Mine block 2... ");
		blockchain.get(1).mineBlock(difficulty);

		blockchain.add(new Block("Hey im the third block", blockchain.getLast().hash));
		System.out.println("Trying to Mine block 3... ");
		blockchain.get(2).mineBlock(difficulty);

		System.out.println("\nBlockchain is Valid: " + isChainValid());

		String blockchainJson = new GsonBuilder().setPrettyPrinting().create().toJson(blockchain);		
		System.out.println(blockchainJson);

		walletA = new Wallet();
		walletB = new Wallet();
		//Test public and private keys
		System.out.println("Private and public keys:");
		System.out.println(Crypt.getStringFromKey(walletA.privateKey));
		System.out.println(Crypt.getStringFromKey(walletA.publicKey));
		Transaction transaction = new Transaction(walletA.publicKey, walletB.publicKey, 5, null);
		transaction.generateSignature(walletA.privateKey);
		System.out.println("Is signature verified");
		System.out.println(transaction.verifiySignature());*/
		//Create wallets:
		walletA = new Wallet();
		walletB = new Wallet();
		Wallet coinbase = new Wallet();

		//create genesis transaction, which sends 100 NoobCoin to walletA:
		genesisTransaction = new Transaction(coinbase.publicKey, walletA.publicKey, 100f, null);
		genesisTransaction.generateSignature(coinbase.privateKey);	 //manually sign the genesis transaction
		genesisTransaction.transactionId = "0"; //manually set the transaction id
		genesisTransaction.outputs.add(new TransactionOutput(genesisTransaction.reciepient, genesisTransaction.value, genesisTransaction.transactionId)); //manually add the Transactions Output
		UTXOs.put(genesisTransaction.outputs.get(0).id, genesisTransaction.outputs.getFirst()); //its important to store our first transaction in the UTXOs list.

		System.out.println("Creating and Mining Genesis block... ");
		Block genesis = new Block("0");
		genesis.addTransaction(genesisTransaction);
		addBlock(genesis);

		//testing
		Block block1 = new Block(genesis.hash);
		System.out.println("\nWalletA's balance is: " + walletA.getBalance());
		System.out.println("\nWalletA is Attempting to send funds (40) to WalletB...");
		block1.addTransaction(walletA.sendFunds(walletB.publicKey, 40f));
		addBlock(block1);
		System.out.println("\nWalletA's balance is: " + walletA.getBalance());
		System.out.println("WalletB's balance is: " + walletB.getBalance());

		Block block2 = new Block(block1.hash);
		System.out.println("\nWalletA Attempting to send more funds (1000) than it has...");
		block2.addTransaction(walletA.sendFunds(walletB.publicKey, 1000f));
		addBlock(block2);
		System.out.println("\nWalletA's balance is: " + walletA.getBalance());
		System.out.println("WalletB's balance is: " + walletB.getBalance());

		Block block3 = new Block(block2.hash);
		System.out.println("\nWalletB is Attempting to send funds (20) to WalletA...");
		block3.addTransaction(walletB.sendFunds( walletA.publicKey, 20f));
		System.out.println("\nWalletA's balance is: " + walletA.getBalance());
		System.out.println("WalletB's balance is: " + walletB.getBalance());
		Scanner scanner = new Scanner(System.in);
		String username = scanner.nextLine();
		String password = scanner.nextLine();
		if (!User.checkUsernameExists(username)){
			new User(username, password);
		}
		User amir = new User("amir", "mohammad");
		if (User.checkUsernameExists("amir")) {
			System.out.println("amir exists");
		}
		Wallet wallete_madarkharab = User.getUserByUsername(username).getWallet();
		Block block4 = new Block(block3.hash);
		System.out.println("\nWalleta is Attempting to send funds (20) to Walletmadarkharab...");
		block4.addTransaction(walletA.sendFunds( wallete_madarkharab.publicKey, 20f));
		System.out.println("\nWalletA's balance is: " + walletA.getBalance());
		System.out.println("WalletMkh's balance is: " + wallete_madarkharab.getBalance());
		isChainValid();
	}
}
