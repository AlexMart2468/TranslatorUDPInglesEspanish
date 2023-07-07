import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.HashMap;
import java.util.Map;

public class UdpDictServer {
    public static final int PORT = 30001;
    public static final int BUFFER_SIZE = 4096;
    private static final Map<String, String> wordMap = new HashMap<>();

    static {
        wordMap.put("dog", "perro");
        wordMap.put("cat", "gato");
        wordMap.put("fish", "pez");
        wordMap.put("bird", "pájaro");
        wordMap.put("pig", "cerdo");
    }

    public static void main(String[] args) {
        try (DatagramSocket socket = new DatagramSocket(PORT)) {
            System.out.println("El servidor inglés-español ha comenzado...");

            while (true) {
                DatagramPacket requestPacket = new DatagramPacket(new byte[BUFFER_SIZE], BUFFER_SIZE);
                socket.receive(requestPacket);

                String request = new String(requestPacket.getData(), 0, requestPacket.getLength()).trim();
                InetAddress clientAddress = requestPacket.getAddress();
                int clientPort = requestPacket.getPort();

                if (request.equalsIgnoreCase("down")) {
                    System.out.println("El cliente solicitó cerrar el servidor.");
                    break;
                }

                String response = wordMap.getOrDefault(request, "Palabra desconocida");
                System.out.println("El cliente ingresó: " + request + ", la traducción es: " + response);

                byte[] responseData = ("La traducción de " + request + " es: " + response).getBytes();
                DatagramPacket responsePacket = new DatagramPacket(responseData, responseData.length, clientAddress, clientPort);
                socket.send(responsePacket);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
