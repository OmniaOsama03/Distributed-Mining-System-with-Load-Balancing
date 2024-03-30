package Task2_Omnia;


import com.google.gson.GsonBuilder;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;


public class Server_Part2 extends Thread {

    private long startNonce;
    private long endNonce;
    private Block_3_77 block;
    private InetAddress clientAddress;
    private int clientPort;
    DatagramSocket aSocket = null;
    int serverPort;


    Server_Part2(long startNonce, long endNonce, Block_3_77 block, int serverPort, InetAddress clientAddress, int clientPort)
    {
        this.startNonce = startNonce;
        this.endNonce = endNonce;
        this.block = block;
        this.clientAddress = clientAddress;
        this.clientPort = clientPort;
        this.serverPort = serverPort;
    }

    @Override
    public void run() {
        try {
            aSocket = new DatagramSocket(serverPort);
            byte[] buffer = new byte[1000];

            System.out.println("Server instantiation is up and running ... ");

            while (true){
                block.mineBlock(block.getLeadingZeros(), startNonce, endNonce);

            String blockInfo = "\nBlock Number: " + block.getBlockNumber() +
                    "\nData: " + block.getData() +
                    "\nNonce: " + block.getNonce() +
                    "\nHash with " + block.getLeadingZeros() + " leading zeros: " + block.getHash() +
                    "\n\n EXECUTION TIME: " + block.getExecutionTime() + " ms";

            if (block.getNonceFound()) { //prevents block from  if nonce not found within range.
                DatagramPacket reply = new DatagramPacket(blockInfo.getBytes(), blockInfo.length(), clientAddress, clientPort);
                aSocket.send(reply);
                break;
            }
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
}

