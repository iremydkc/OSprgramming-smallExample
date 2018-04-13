import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.*;
 
 
public class Client
{
 
    private static Socket socket;
 
    public static void main(String args[])
    {
        try
        {
            String host = "localhost";
            int portnum = 25000;
            InetAddress address = InetAddress.getByName(host);
            socket = new Socket(address, portnum);
 
            //Server'a mesaj gönderilir
            OutputStream os = socket.getOutputStream();
            OutputStreamWriter osw = new OutputStreamWriter(os);
            BufferedWriter bw = new BufferedWriter(osw);    


	        Scanner input=new Scanner(System.in);

          	String a=input.nextLine();

 
            String sendMessage = a + "\n";
            bw.write(sendMessage);
            bw.flush();
            System.out.print("Data : "+sendMessage);

            //Server'dan mesaj geri alınır
            InputStream is = socket.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            String sifreliStr = br.readLine();

            System.out.println("decrypted data: "+sifreliStr);
            
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
        }

        finally
        {
            //Soket kapatılır
            try
            {
                socket.close();
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }
    }
}
