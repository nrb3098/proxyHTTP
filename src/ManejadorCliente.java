import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;


public class ManejadorCliente {
    public static void manejarCliente(Socket client) throws IOException {
        System.out.println("TENEMOS CLIENTE NUEVO: " + client.toString());
        BufferedReader br = new BufferedReader(new InputStreamReader(client.getInputStream()));
        //URL url = new URL("http://www.columbia.edu/~fdc/sample.html");
        //HttpURLConnection conexion = (HttpURLConnection) url.openConnection();
        //conexion.setRequestMethod("GET");

        StringBuilder requestBuilder = new StringBuilder();
        String line;
        while (!(line = br.readLine()).isBlank()) {
            requestBuilder.append(line + "\r\n");
        }

        String request = requestBuilder.toString();
        System.out.println("SE SOLICITA ESTO: \n");
        System.out.println(request);
         
        //System.out.println("SE RESPONDIENDO ESTO: \n");
        //System.out.println(conexion.getResponseCode());
    }
    public void enviarRequest(String request){

    }

}
