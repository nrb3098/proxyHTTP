import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;

import java.io.OutputStreamWriter;
import java.net.*;


public class ManejadorCliente {
    public static void manejarCliente(Socket clientEntrada) throws IOException {
        System.out.println("TENEMOS CLIENTE NUEVO: " + clientEntrada.toString());
        clientEntrada.setSoTimeout(2000);
        BufferedReader br = new BufferedReader(new InputStreamReader(clientEntrada.getInputStream()));                       
        StringBuilder requestBuilder = new StringBuilder();
        String line;
        try{
            while ((line = br.readLine())!=null) {
                if(line.contains("CONNECT")){
                    clientEntrada.close();
                    break;
                }
                requestBuilder.append(line + "\r\n"); 
                 
            }
        }catch(Exception e){
           System.out.println(e);
        }
               
        String request = requestBuilder.toString();
        System.out.println(request); 

        //sacamos el metodo y la url para abrir
        String direccion = urlDeSolicitud(request);
        String metodosolicitud = metodoDeSolicitud(request);
                     

        if(direccion!=null && metodosolicitud!=null){ 
            URL url = new URL(direccion);
            HttpURLConnection conexion= (HttpURLConnection) url.openConnection();            
            conexion.setRequestMethod(metodosolicitud);
            System.out.println("SE RESPONDIENDO ESTO: \n" );
            String estatus = (conexion.getResponseCode()+ " " + conexion.getResponseMessage());
            BufferedWriter proxyaClientBW = new BufferedWriter(new OutputStreamWriter(clientEntrada.getOutputStream()));
            proxyaClientBW.write(estatus);
            BufferedReader proxyAafueraReader = new BufferedReader(new InputStreamReader(conexion.getInputStream()));
            String linea;
            while((linea= proxyAafueraReader.readLine())!= null){
                proxyaClientBW.write(linea);
            }
            proxyaClientBW.flush();
            proxyAafueraReader.close();
            conexion.disconnect();
        }
        clientEntrada.close();
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
