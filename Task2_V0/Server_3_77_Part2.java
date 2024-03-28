package Task2_V0;


import com.google.gson.GsonBuilder;
import java.io.IOException;
import java.lang.Runnable;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
//Maryam Mohammed Ali 1079679
//Omnia Osama Ahmed 1084505
//Nourhan Ahmed Elmehalawy 1078096
class Server_3_77_Part2 implements Runnable {
    private static HashMap<String, InetAddress> connectedClients = new HashMap<>();
    private int leadingZeros;
    private long startNonce;
    private long endNonce;

    //Constructor
    public Server_3_77_Part2(int leadingZeros, long startNonce, long endNonce) {
        this.leadingZeros = leadingZeros;
        this.startNonce = startNonce;
        this.endNonce = endNonce;
    }
<<<<<<< HEAD:TryTask2/Server_3_77_Part2.java

    @Override
    public void run() {
=======
>>>>>>> 86d64c464af381e26039a77d0c4042c7437b8a8f:Task2_V0/Server_3_77_Part2.java

    @Override
    public void run() {
        DatagramSocket aSocket = null;
        try {
            aSocket = new DatagramSocket(20000);
            byte[] buffer = new byte[1000];
            System.out.println("Servers are searching for the nonce... ");
<<<<<<< HEAD:TryTask2/Server_3_77_Part2.java

            while (true) {

=======
            while (true) {
>>>>>>> 86d64c464af381e26039a77d0c4042c7437b8a8f:Task2_V0/Server_3_77_Part2.java
                DatagramPacket request = new DatagramPacket(buffer, buffer.length);
                aSocket.receive(request);
                String clientID = request.getAddress().toString();
// Checking if the client is a new connection and adding it to the connectedClients HashMap
                if (!connectedClients.containsKey(clientID)) {
                    connectedClients.put(clientID, request.getAddress());
                    System.out.println("connected client: " + clientID);
                }

                BlockMining_3_77 block = (BlockMining_3_77) BlockMining_3_77.getObject(new String(request.getData(), 0, request.getLength()));
<<<<<<< HEAD:TryTask2/Server_3_77_Part2.java

                System.out.println("Mining from nonce " + startNonce + " to nonce " + endNonce + " with " + leadingZeros + " leading zeros");

                // Generate hash for each nonce in the range [startNonce, endNonce]
                for (long nonce = startNonce; nonce <= endNonce; nonce++) {
                    block.generateHash(leadingZeros, startNonce, endNonce) ;// Calculate hash for the current nonce
                }

=======
                System.out.println("Mining from nonce " + startNonce + " to nonce " + endNonce + " with " + leadingZeros + " leading zeros");
// Generate hash for each nonce in the range [startNonce, endNonce]
                for (long nonce = startNonce; nonce <= endNonce; nonce++) {
                    block.generateHash(leadingZeros, startNonce, endNonce);// Calculate hash for the current nonce
                }
>>>>>>> 86d64c464af381e26039a77d0c4042c7437b8a8f:Task2_V0/Server_3_77_Part2.java

                String blockInfo = "\nBlock Number: " + block.getBlockNumber() +
                        "\nData: " + block.getData() +
                        "\nNonce: " + block.getNonce() +
                        "\nHash with " + block.getLeadingZeros() + " leading zeros: " + block.getHash() +
                        "\n\n EXECUTION TIME: " + block.getExecutionTime() + " ms";

<<<<<<< HEAD:TryTask2/Server_3_77_Part2.java

=======
>>>>>>> 86d64c464af381e26039a77d0c4042c7437b8a8f:Task2_V0/Server_3_77_Part2.java
                DatagramPacket reply = new DatagramPacket(blockInfo.getBytes(), blockInfo.length(), request.getAddress(), request.getPort());
                aSocket.send(reply);
            }
        } catch (SocketException var8) {
            System.out.println("Error Socket: " + var8.getMessage());
        } catch (IOException var9) {
            System.out.println("Error IO: " + var9.getMessage());
        } finally {
            if (aSocket != null) {
                aSocket.close();
            }
        }
    }
<<<<<<< HEAD:TryTask2/Server_3_77_Part2.java

}

=======
}
>>>>>>> 86d64c464af381e26039a77d0c4042c7437b8a8f:Task2_V0/Server_3_77_Part2.java
