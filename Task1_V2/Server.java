package Task1_V2;

import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class Server {
    public Server() {
    }

    public static void main(String[] args) {

        DatagramSocket aSocket = null;

        try {
            aSocket = new DatagramSocket(20000);
            byte[] buffer = new byte[1000];
            System.out.println("Server is ready and accepting clients' requests ... ");

            while(true) {

                DatagramPacket request = new DatagramPacket(buffer, buffer.length);
                aSocket.receive(request);

                BlockMining block = (BlockMining) getObject(new String(request.getData(), 0, request.getLength()));

                block.generateHash(block.getLeadingZeros());

                String blockInfo = "\nBlock Number: " + block.getBlockNumber() +
                        "\nData: " + block.getData() +
                        "\nNonce: " + block.getNonce() +
                        "\nHash with " + block.getLeadingZeros() + " leading zeros: " + block.getHash() +
                        "\n\n EXECUTION TIME: " + block.getExecutionTime() + " ms";

                DatagramPacket reply = new DatagramPacket(blockInfo.getBytes(),blockInfo.length(),request.getAddress(),request.getPort());
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

    public static Object getObject(String JsgString) {
        return new GsonBuilder().setPrettyPrinting().create().fromJson(JsgString, BlockMining.class);
    }
}
