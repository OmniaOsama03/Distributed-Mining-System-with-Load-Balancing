package Task2_V0;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class LoadBalancer_3_77 {
    public static void main(String args[]) {
        // parsing server numbers to int
        int numServers = Integer.parseInt(args[0]);

        DatagramSocket aSocket = null;
        try {
            aSocket = new DatagramSocket(20000);
            byte[] buffer = new byte[1000];
            System.out.println("Load balancer launched ...");

            // Counter to keep track of which server to forward the request
            int serverIndex = 0;

            while (true) {
                // Receive from the client
                DatagramPacket request = new DatagramPacket(buffer, buffer.length);
                aSocket.receive(request);

                // Determine which server to forward the request
                String serverAddress = ""; //  server addresses
                int serverPort = 30000 + (serverIndex % numServers); // Adjust port range as needed

                // Send to Server
                DatagramPacket reply = new DatagramPacket(
                        request.getData(),
                        request.getLength(),
                        InetAddress.getByName(serverAddress),
                        serverPort);
                aSocket.send(reply);

                // Increment server index for next request
                serverIndex++;
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
