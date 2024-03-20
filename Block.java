import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

public class Block {
    private int blockNumber;
    private long nonce;
    private String data;
    private String hash;

    public Block(int blockNumber, String data) {
        this.blockNumber = blockNumber;
        this.data = data;
        this.nonce = 0; // Initial nonce value
        this.hash = "";
    }

    public int getBlockNumber() {
        return blockNumber;
    }

    public long getNonce() {
        return nonce;
    }

    public String getData() {
        return data;
    }

    public String getHash() {
        return hash;
    }

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
    public void generateHashWithLeadingZeros(int leadingZeros) {

        String prefix = "0".repeat(leadingZeros);
        do {
            nonce++; // Increment nonce value
            String dataWithNonce = blockNumber + data + nonce;
            hash = calculateHash(dataWithNonce);
        } while (!hash.startsWith(prefix)); // Continue until hash has the required number of leading zeros
    }

    public static void main(String[] args) {

        int blockNumber = 1;

        Scanner input = new Scanner(System.in);

        System.out.println("Enter your data: ");
        String data = input.nextLine();

        System.out.println("Enter count of leading zeros: ");
        int leadingZeros = input.nextInt();

        Block block = new Block(blockNumber, data);
        block.generateHashWithLeadingZeros(leadingZeros);

        System.out.println("Block Number: " + block.getBlockNumber());
        System.out.println("Data: " + block.getData());
        System.out.println("Nonce: " + block.getNonce());
        System.out.println("Hash with " + leadingZeros + " leading zeros: " + block.getHash());
    }
}
