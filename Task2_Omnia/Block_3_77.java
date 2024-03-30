package Task2_Omnia;

import com.google.gson.GsonBuilder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Block_3_77 {
    private int blockNumber;
    private long nonce;
    private String data;
    private String hash;
    private int leadingZeros;
    private long executionTime; // Variable for execution time
    private boolean nonceFound = false;

    public Block_3_77(){}
    public Block_3_77(int blockNumber, String data, int leadingZeros) {
        this.blockNumber = blockNumber;
        this.data = data;
        this.nonce = 0; // Initial nonce value
        this.hash = "";
        this.leadingZeros = leadingZeros;
    }

    //Getters-
    public int getBlockNumber() {
        return blockNumber;
    }
    public int getLeadingZeros(){return leadingZeros;}
    public long getNonce() {
        return nonce;
    }
    public String getData() {
        return data;
    }
    public String getHash() {
        return hash;
    }
    public long getExecutionTime() {return executionTime;}
    public boolean getNonceFound(){return nonceFound;}


    // Generate SHA-256 hash of a string
    private String calculateHash(String input) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(input.getBytes());

            // Convert byte array to hexadecimal string
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Mine the block to find a hash with a specified number of leading zeros
    public void mineBlock(int leadingZeros, long startNonce, long endNonce) {
        nonceFound = false; //resetting the value of the variable (more than 1 thread might work on the same block)

        long startTime = System.nanoTime(); // Start the timer
        String prefix = "0".repeat(leadingZeros);

        for (nonce = startNonce; nonce <= endNonce; nonce++) {
            String dataWithNonce = blockNumber + data + nonce;

            hash = calculateHash(dataWithNonce);
            if (hash.startsWith(prefix) && hash.charAt(leadingZeros) != '0') {
                nonceFound = true;
                break; // Stop iterating if valid hash is found
            }
        }
        // Stop the timer
        long endTime = System.nanoTime();
        executionTime = (endTime - startTime) / 1000000;
    }

    public static Object getObject(String JsgString) {
        return new GsonBuilder().setPrettyPrinting().create().fromJson(JsgString, Block_3_77.class);
    }

    public static String getJson(Object o) {
        return new GsonBuilder().setPrettyPrinting().create().toJson(o);
    }
}

