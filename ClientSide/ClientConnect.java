package ClientSide;
public class ClientConnect
{

    public static void main(String[] args) 
    {
        Client client=new Client("127.0.0.1"); //Creating the client object with the server IP
        client.startRunning(); //Running the client
    }
}
