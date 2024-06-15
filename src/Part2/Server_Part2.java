package Part2;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;


public class Server_Part2
{
    public static void main(String[] args) {

        int port = Integer.parseInt(args[0]);
        DatagramSocket aSocket = null;
        try {
            aSocket = new DatagramSocket(port);
            byte[] buffer = new byte[1000];
            System.out.println("Server with port " + port + " is ready and accepting clients' requests ... ");

            while(true) {

                DatagramPacket request = new DatagramPacket(buffer, buffer.length);
                aSocket.receive(request);

                String nonceWithBlock = new String(request.getData(), 0, request.getLength());

                //Splitting the data into respective variables.
                String[] data = nonceWithBlock.split("\\|");

                //Extracting the data.
                String myBlock = data[0];
                int startNonce = Integer.parseInt(data[1]);
                int endNonce = Integer.parseInt(data[2]);
                String clientSocket = data[3];

                //Splitting the client's info into respective variables.
                String[] clientInfo = clientSocket.split(":");

                //Extracting the client info.
                String address = clientInfo[0].substring(1, clientInfo[0].length());
                int clientPort = Integer.parseInt(clientInfo[1]);
                InetAddress clientAddress = InetAddress.getByName(address);

                //Deserializing the received block into a Block_3_77 object.
                Block_77_3 block = (Block_77_3) Block_77_3.getObject(myBlock);

                System.out.println("Nonce range - Start:" + startNonce + " & End: " + endNonce);
                System.out.println("Working for client with port: " + clientPort);

                //Using the mineBlock() method to get the nonce.
                block.mineBlock(block.getLeadingZeros(), startNonce, endNonce);

                String blockInfo = "\nBlock Number: " + block.getBlockNumber() +
                        "\nData: " + block.getData() +
                        "\nNonce: " + block.getNonce() +
                        "\nHash with " + block.getLeadingZeros() + " leading zeros: " + block.getHash() +
                        "\n\n EXECUTION TIME: " + block.getExecutionTime() + " ms";

                if (block.getNonceFound()) { //prevents block from sending if nonce not found within range.
                    DatagramPacket reply = new DatagramPacket(blockInfo.getBytes(), blockInfo.length(), clientAddress, clientPort);
                    aSocket.send(reply);
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
