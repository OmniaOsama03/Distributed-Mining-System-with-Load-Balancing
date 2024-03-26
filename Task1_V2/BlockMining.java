package Task1_V2;

import Task1_V2.StringUtil;
import com.google.gson.Gson;

public class BlockMining {
    private int number; // The block number
    private int nonce; // Nonce value used in mining
    private String data; // Data stored in the block
    private int leadingZeros; // Number of leading zeros required in the block's hash

    // Constructor to initialize the Block object with provided values
    public BlockMining(int number, int nonce, String data, int leadingZeros) {
        this.number = number;
        this.nonce = nonce;
        this.data = data;
        this.leadingZeros = leadingZeros;
    }

    // Method to increment the nonce value
    public void incrementNonce() {
        nonce++;
    }

    // Getter method for the block number
    public int getNumber() {
        return number;
    }

    // Getter method for the data stored in the block
    public String getData() {
        return data;
    }

    // Getter method for the nonce value
    public int getNonce() {
        return nonce;
    }

    // Method to calculate the hash of the block using SHA-256 hash function
    public String calculateHash() {
        return StringUtil.applySha256(number + nonce + data);
    }

    // Getter method for the number of leading zeros required in the block's hash
    public int getLeadingZeros() {
        return leadingZeros;
    }

    // Method to convert the Block object to JSON format
    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    // Method to create a Block object from JSON format
    public static BlockMining fromJson(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, BlockMining.class);
    }

}
