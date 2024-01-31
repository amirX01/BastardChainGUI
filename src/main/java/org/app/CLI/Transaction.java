package org.app.CLI;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.ArrayList;

public class Transaction {
	
	public String transactionId;
	public PublicKey sender;
	public PublicKey reciepient;
	public float value;
	public byte[] signature;
	
	public ArrayList<TransactionInput> inputs = new ArrayList<TransactionInput>();
	public ArrayList<TransactionOutput> outputs = new ArrayList<TransactionOutput>();
	
	private static int sequence = 0;

	public Transaction(PublicKey from, PublicKey to, float value,  ArrayList<TransactionInput> inputs) {
		this.sender = from;
		this.reciepient = to;
		this.value = value;
		this.inputs = inputs;
	}
	public boolean processTransaction() {

		if(!verifiySignature()) {
			System.out.println("#Transaction Signature failed to verify");
			return false;
		}

        inputs.forEach(i -> i.UTXO = BastardChain.UTXOs.get(i.transactionOutputId));

		if(getInputsValue() < BastardChain.minimumTransaction) {
			System.out.println("#Transaction Inputs to small: " + getInputsValue());
			return false;
		}

		float leftOver = getInputsValue() - value;
		transactionId = calulateHash();
		outputs.add(new TransactionOutput( this.reciepient, value,transactionId));
		outputs.add(new TransactionOutput( this.sender, leftOver,transactionId));

		for(TransactionOutput o : outputs) {
			BastardChain.UTXOs.put(o.id , o);
		}

		for(TransactionInput i : inputs) {
			if(i.UTXO == null) continue;
			BastardChain.UTXOs.remove(i.UTXO.id);
		}

		return true;
	}

	public float getInputsValue() {
		float total = 0;
		for(TransactionInput i : inputs) {
			if(i.UTXO == null) continue;
			total += i.UTXO.value;
		}
		return total;
	}

	public float getOutputsValue() {
		float total = 0;
		for(TransactionOutput o : outputs) {
			total += o.value;
		}
		return total;
	}
	public void generateSignature(PrivateKey privateKey) {
		String data = Crypt.getStringFromKey(sender) + Crypt.getStringFromKey(reciepient) + Float.toString(value);
		signature = Crypt.applyECDSASig(privateKey, data);
	}
	public boolean verifiySignature() {
		String data = Crypt.getStringFromKey(sender) + Crypt.getStringFromKey(reciepient) + Float.toString(value);
		return Crypt.verifyECDSASig(sender, data, signature);
	}
	private String calulateHash() {
		sequence ++;
		return Crypt.applySha256(Crypt.getStringFromKey(sender) + Crypt.getStringFromKey(reciepient) + Float.toString(value) + sequence);
	}

}
