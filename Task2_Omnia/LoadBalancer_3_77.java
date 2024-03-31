package Task2_Omnia;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.HashMap;

public class LoadBalancer_3_77
{
    private static HashMap<String, InetAddress> connectedClients = new HashMap<>();
    static int portIncrement = 0;

    public static void main(String[] args) {

        int serverCount = Integer.parseInt(args[0]); // Number of servers
        long minNonce = Long.parseLong(args[1]); // Minimum nonce
        long maxNonce = Long.parseLong(args[2]); // Maximum nonce

        ArrayList<Server_Part2> serverThreads = new ArrayList<>();
        DatagramSocket aSocket = null;
        try {
            aSocket = new DatagramSocket(20000);
            byte[] buffer = new byte[1000];
            System.out.println("Load balancer is ready and accepting clients' requests ... ");

            while(true) {
                DatagramPacket request = new DatagramPacket(buffer, buffer.length);
                aSocket.receive(request);

                String clientID = request.getSocketAddress().toString();

                // Checking if the client is a new connection and adding it to the connectedClients HashMap
                if (!connectedClients.containsKey(clientID)) {
                    connectedClients.put(clientID, request.getAddress());
                    System.out.println("connected client: " + clientID);
                    portIncrement++;
                }

               //Creating the threads and assigning each an equal range of nonce.
                long nonceRange = (maxNonce - minNonce + 1) / serverCount;

                long firstValue = 0;
                long lastValue = nonceRange;
                for(int x = 1; x <= serverCount; x++)
                {
                     System.out.println("Server " + x + ": First - " + firstValue + " & Last - " + lastValue);

                     //Extracting the block:
                     Block_3_77 block = (Block_3_77) Block_3_77.getObject(new String(request.getData(), 0, request.getLength()));

                     //Creating and adding the thread to the arraylist.
                     Server_Part2 thread = new Server_Part2(firstValue, lastValue, block, 20000 + x , request.getAddress(), request.getPort());
                     serverThreads.add(thread);

                    // Update nonce range for the next server
                     firstValue = lastValue + 1;
                     lastValue = firstValue + nonceRange - 1;
                }

                //Running all the threads.
                for(Server_Part2 thread: serverThreads)
                {
                    if(!thread.isAlive())
                    thread.start();
                    else
                    {
                        for(Server_Part2 updateThread: serverThreads)
                            updateThread.setServerPort(updateThread.getServerPort() + portIncrement);
                    }

                }

            }

        } catch (SocketException var8) {
            System.out.println("Error Socket: " + var8.getMessage());
        } catch (IOException var9) {
            System.out.println("Error IO: " + var9.getMessage());
        }  finally {
            if (aSocket != null) {
                aSocket.close();
            }

        }
    }
}
