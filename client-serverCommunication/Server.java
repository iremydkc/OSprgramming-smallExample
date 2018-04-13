import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
 
public class Server
{
 
    private static Socket socket;
 
    public static void main(String[] args)
    {
        try
        {
 
            int portnum = 25000;
            ServerSocket serverSocket = new ServerSocket(portnum);
 
            //Server is running always. This is done using this while(true) loop
            while(true)
            {
                //client bağlantısı kurulur
                socket = serverSocket.accept();

                // Client'tan gelen mesaj okunur
                InputStream is = socket.getInputStream();
                InputStreamReader isr = new InputStreamReader(is);
                BufferedReader br = new BufferedReader(isr);
                String a = br.readLine();
                System.out.println("data: "+ a);

                String returnMessage;
		        char[] decrypted;
		        decrypted = new char[100]; 

                 try
                 {      //client'tan gelen mesaj kabul edilir
                        OutputStream os = socket.getOutputStream();
                        OutputStreamWriter osw = new OutputStreamWriter(os);
                        BufferedWriter bw = new BufferedWriter(osw);


                        char b;
                        int deger=23; // sezar şifreleme algoritmasında ileri kaydırılarak bulunacak harf için ileri 
					    //kaydırma değeri 5 olarak sabit şekilde atandı

                        //sezar şifreleme algoritması
                        for(int i=0;i<a.length();i++){

				if((a.charAt(i))>=65 && (a.charAt(i))<=90){ // Girilen harf büyük harf ise ASCII tablosunda 65 ile 90
 										//arasındadır, ona göre kaydırmak için 
										//farklı matematiksel işlem yapar

                            		b=(char) ((int)(a.charAt(i)-65+deger)%26+65);
			                        decrypted[i]=b;
			                }

                            else if( (a.charAt(i)) >=97 && (a.charAt(i)) <=122 ){ //Girilen harf küçük harf ise ASCII tablosunda 97 ile 122
										//arasındadır ve kaydırmak için farklı matematiksel işlem yapar

                         			b=(char) ((int)(a.charAt(i)-97+deger) %26+97);
			                        decrypted[i]=b;
                            } 

                        } 
                        
                        String decryptedStr = new String(decrypted);
                        returnMessage = decryptedStr + "\n";             
                 }
                 catch(NumberFormatException e)
                 {
                    returnMessage = "Please send a string\n";
                 }
             
                 //Sending the response back to the client.
                 OutputStream os = socket.getOutputStream();
                 OutputStreamWriter osw = new OutputStreamWriter(os);
                 BufferedWriter bw = new BufferedWriter(osw);
                 bw.write(returnMessage);
                 System.out.print("decrypted data: "+ returnMessage);
                 bw.flush();
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                socket.close(); //socket kapatıldı, daha fazla veri alışverişi olmaz
            }
            catch(Exception e){}
        }
    }
}
