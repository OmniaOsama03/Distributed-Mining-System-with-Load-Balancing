package Task2_Finalised;

import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import java.util.HashMap;

//Omnia Osama Ahmed 1084505
//Maryam Mohammed Ali 1079679
//Nourhan Ahmed Elmehalawy 1078096

public class LoadBalancer_3_77
{
    private static HashMap<String, InetAddress> connectedClients = new HashMap<>();
    public static void main(String[] args){

        int serverCount = Integer.parseInt(args[0]); // Number of servers
        long minNonce = Long.parseLong(args[2]); // Minimum nonce
        long maxNonce = Long.parseLong(args[3]); // Maximum nonce

        /*
        args[0] : Number of servers.
        args[1] : The ip address of the server(s).
        args[2] : The minimum nonce to be tested.
        args[3] : The maximum nonce to be tested.
        args[4] - args [n] : Ports of servers.
         */

        ArrayList<Integer> serverPorts = new ArrayList<>(); //Aids in sending messages to all servers.
        ArrayList<String> nonceIntervals = new ArrayList<>();

        DatagramSocket aSocket = null;
        try {
            InetAddress serverAddress = InetAddress.getByName(args[1]);
            aSocket = new DatagramSocket(30000);

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
                }

                //Adding all server ports to the arraylist.
                for (int i = 4; i < args.length; i++) {
                    serverPorts.add(Integer.parseInt(args[i]));
                }

                //Splitting the nonce in equal intervals
                long nonceRange = (maxNonce - minNonce + 1) / serverCount; //Count of nonce in each interval.

                long firstValue = 0;
                long lastValue = nonceRange;
                for(int x = 1; x <= serverCount; x++)
                {
                    //Extracting the block, but keeping it in String form.
                    String pureBlock = new String(request.getData(), 0, request.getLength());

                    //Adding necessary data to block, and adding it to arraylist.
                    String dataWithBlock = pureBlock + "|" + firstValue + "|" + lastValue + "|" + clientID;
                    nonceIntervals.add(dataWithBlock);

                    // Update nonce range for the next server
                    firstValue = lastValue + 1;
                    lastValue = firstValue + nonceRange - 1;
                }

                //Sending each server the block with their own nonce interval
                for (int x = 0; x < serverCount; x++)
                {
                DatagramPacket toServer = new DatagramPacket(nonceIntervals.get(x).getBytes(), nonceIntervals.get(x).length(), serverAddress, serverPorts.get(x));
                aSocket.send(toServer);
                }

                nonceIntervals.clear(); //Clearing the list to be usable by new client.
            }
        }
        catch (SocketException var8) {
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
