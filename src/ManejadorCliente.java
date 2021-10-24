import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.*;
//import java.util.Map;


public class ManejadorCliente {
    public static void manejarCliente(Socket clientEntrada) throws IOException {
        System.out.println("TENEMOS CLIENTE NUEVO: " + clientEntrada.toString());
        BufferedReader br = new BufferedReader(new InputStreamReader(clientEntrada.getInputStream()));                       
        StringBuilder requestBuilder = new StringBuilder();
        String line;
        int contador=0;
        try{
            while ((line = br.readLine())!=null) {
                requestBuilder.append(line + "\r\n");   
                System.out.println("SE SOLICITA ESTO" + contador + ": \n");
                contador++;
            }
        }catch(Exception e){
            System.out.println(e);
        }
        

        
        //sacamos el metodo y la url para abrir
        String request = requestBuilder.toString();
        System.out.println("SE SOLICITA ESTO  Seg: \n");
        String direccion = urlDeSolicitud(request);
        String metodosolicitud = metodoDeSolicitud(request);
        
        System.out.println(request);              
        
        
        if(direccion!=null && metodosolicitud!=null){            
            URL url = new URL(direccion);
            HttpURLConnection conexion= (HttpURLConnection) url.openConnection();            
            conexion.setRequestMethod(metodosolicitud);
            System.out.println("SE RESPONDIENDO ESTO: \n");
            System.out.println(conexion.getResponseCode() + " " + conexion.getResponseMessage());
            BufferedReader bufferEntrada = new BufferedReader(new InputStreamReader(conexion.getInputStream()));                                   
            OutputStream clientOutput = clientEntrada.getOutputStream();
            String i;   
            while ((i = bufferEntrada.readLine()) != null)    
            {   try{
                    System.out.println(i);
                    clientOutput.write(i.getBytes());       
                }catch(Exception e){
                    System.out.println(e);
                }
                
            }             
        }
        
        
        
    }


    public static String metodoDeSolicitud(String request){
        int metodoini = request.indexOf("GET");
        int metofin = request.indexOf(" h");

        if(metodoini != -1 && metofin != -1){
            String metodo = request.substring(metodoini,metofin);
            return metodo;
        }
        return null;
    }


    public static String urlDeSolicitud(String request){
        int inicioURl =  request.indexOf("http:");        
        int finalURL = request.indexOf(" H");

        if (inicioURl != -1 && finalURL != -1){
            String direccion = request.substring(inicioURl, finalURL);
            return direccion;
        }
        return null;
        
    }

}
