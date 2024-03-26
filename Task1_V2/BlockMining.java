package Task1_V2;

import com.google.gson.Gson;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class BlockMining
{
    private int blockNumber;
    private long nonce;
    private String data;
    private String hash;
    private int leadingZeros;
    private long executionTime; // Variable for execution time

    public BlockMining(int blockNumber, String data, int leadingZeros) {
        this.blockNumber = blockNumber;
        this.data = data;
        this.nonce = 0; // Initial nonce value
        this.hash = "";
        this.leadingZeros = leadingZeros;

    }

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
    public void generateHash(int leadingZeros) {
        // Start the timer
        long startTime = System.nanoTime();

        String prefix = "0".repeat(leadingZeros);
        do {
            nonce++; // Increment nonce value
            String dataWithNonce = blockNumber + data + nonce;
            hash = calculateHash(dataWithNonce);
        } while (!hash.startsWith(prefix)); // Continue until hash has the required number of leading zeros

        // Stop the timer
        long endTime = System.nanoTime();

        // Calculate elapsed time in milliseconds and assign it to executionTime
        executionTime = (endTime - startTime) / 1000000;
    }
}

