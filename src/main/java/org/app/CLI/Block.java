package org.app.CLI;

import java.util.ArrayList;
import java.util.Date;

public class Block {

	public String hash;
	public String previousHash;
	public String merkleRoot;
	//private String data;
	private long timeStamp;
	private int nonce;
	public ArrayList<Transaction> transactions = new ArrayList<Transaction>();
	public Block(String previousHash) {
		//this.data = data;
		this.previousHash = previousHash;
		this.timeStamp = new Date().getTime();
		this.hash = calculateHash();
	}
	public String calculateHash() {
        return Crypt.applySha256(previousHash + Long.toString(timeStamp) + Integer.toString(nonce) + merkleRoot);
	}
	public void mineBlock(int difficulty) {
		merkleRoot = Crypt.getMerkleRoot(transactions);
		String target = new String(new char[difficulty]).replace('\0', '0');
		while(!hash.substring( 0, difficulty).equals(target)) {
			nonce ++;
			hash = calculateHash();
		}
		System.out.println("Block Mined!!! : " + hash);
	}
	public void addTransaction(Transaction transaction) {
		if(transaction == null) return;
		if(!"0".equals(previousHash)) {
			if(!transaction.processTransaction()) {
				System.out.println("Transaction failed to process. Discarded.");
				return;
			}
		}
		transactions.add(transaction);
		System.out.println("Transaction Successfully added to Block");
	}
}
