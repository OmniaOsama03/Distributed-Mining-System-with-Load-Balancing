package Task1;

import com.google.gson.GsonBuilder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

//Maryam Mohammed Ali 1079679
//Omnia Osama Ahmed 1084505
//Nourhan Ahmed Elmehalawy 1078096

public class Block_3_77
{
    private int blockNumber;
    private long nonce;
    private String data;
    private String hash;
    private int leadingZeros;
    private long executionTime; // Variable for execution time.

    public Block_3_77(int blockNumber, String data, int leadingZeros) {
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


    // Generating SHA-256 hash of the string.
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

    // Mine the block to find a hash with a specified number of leading zeros.
    public void mineBlock(int leadingZeros) {
        // Start the timer
        long startTime = System.nanoTime();

        String prefix = "0".repeat(leadingZeros);

        while(true)
        {
            nonce++; // Increment nonce value
            String dataWithNonce = blockNumber + data + nonce;
            hash = calculateHash(dataWithNonce);

            if(hash.startsWith(prefix) && hash.charAt(leadingZeros) != '0')
                break;
        }

        // Stop the timer
        long endTime = System.nanoTime();

        // Calculate time in milliseconds.
        executionTime = (endTime - startTime) / 1000000;
    }

    public static String getJson(Object o) {
        return new GsonBuilder().setPrettyPrinting().create().toJson(o);
    }
    public static Object getObject(String JsgString) {
        return new GsonBuilder().setPrettyPrinting().create().fromJson(JsgString, Block_3_77.class);
    }
}

