package Part_1;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.HashMap;

//Maryam Mohammed Ali 1079679
//Omnia Osama Ahmed 1084505
//Nourhan Ahmed Elmehalawy 1078096

public class Server_Part1 {
    // HashMap to store connected clients with their IP addresses and ports
    private static HashMap<InetAddress, Integer> connectedClients = new HashMap<>();

    public static void main(String[] args) {

        DatagramSocket aSocket = null;
        try {
            aSocket = new DatagramSocket(20000);
            byte[] buffer = new byte[1000];
            System.out.println("Server is ready and accepting clients' requests ... ");

            while(true) {

                DatagramPacket request = new DatagramPacket(buffer, buffer.length);
                aSocket.receive(request);

                InetAddress clientID = request.getAddress();

                // Checking if the client is a new connection and adding it to the connectedClients HashMap
                if (!connectedClients.containsKey(clientID)) {
                    connectedClients.put(clientID, request.getPort());
                    System.out.println("connected client: " + clientID);
                }

                //Deserializing the received block into a Block_3_77 object.
                Block_3_77 block = (Block_3_77) Block_3_77.getObject(new String(request.getData(), 0, request.getLength()));

                //Mining the received block.
                block.mineBlock(block.getLeadingZeros());

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

}
