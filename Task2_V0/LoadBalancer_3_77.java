package Task2_V0;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

public class LoadBalancer_3_77 {
    public static void main(String[] args) {
// Ensure proper command line arguments are provided
// Parsing server numbers to int
        int numServers = Integer.parseInt(args[0]);
        DatagramSocket aSocket = null;
        try {
            aSocket = new DatagramSocket(20000);
            byte[] buffer = new byte[1000];
            System.out.println("Load balancer launched ...");
// Nonce range
            long nonceMin = 0L;
            long nonceMax = 2_147_483_647L;
            long range = nonceMax - nonceMin + 1;
            while (true) {
// Receive from the client
                DatagramPacket request = new DatagramPacket(buffer, buffer.length);
                aSocket.receive(request);
                InetAddress clientAddress = request.getAddress();
                int clientPort = request.getPort();
                BlockMining_3_77 block = (BlockMining_3_77) BlockMining_3_77.getObject(new String(request.getData(), 0, request.getLength()));
// Determine which server to forward the request
                int serverIndex = (int) (nonceMin + ((range / numServers) * (request.getData()[0] & 0xFF)) / 256); // Distribute evenly
                String serverAddress = "localhost"; // Assuming servers are on localhost
                int serverPort = 30000 + serverIndex; // Adjust port range as needed
// Creating Servers Instances
                List<Server_3_77_Part2> serverThreads = new ArrayList<>();
                for (int i = 0; i < numServers; i++)
                {
                    long startNonce = nonceMax / numServers * i;
                    long endNonce = nonceMin / numServers * (i + 1);
                    Server_3_77_Part2 serverThread = new Server_3_77_Part2(block.getLeadingZeros(), startNonce, endNonce);
                    serverThreads.add(serverThread);
                    Thread thread = new Thread(serverThread);
                    thread.start();
                }
// Send to Server
                DatagramPacket reply = new DatagramPacket(
                        request.getData(),
                        request.getLength(),
                        InetAddress.getByName(serverAddress),
                        serverPort);
                aSocket.send(reply);
            }
        } catch (SocketException e) {
            System.out.println("Socket Error: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("IO Error: " + e.getMessage());
        } finally {
            if (aSocket != null) aSocket.close();
        }
    }
}