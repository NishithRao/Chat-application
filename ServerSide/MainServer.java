package ServerSide;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

public class MainServer extends javax.swing.JFrame {

    private ObjectOutputStream output;
    private ObjectInputStream input;
    private Socket connection;
    private ServerSocket server;
    private int totalClients = 100; //Max clients
    private int port = 6789;
  
    public MainServer() {
        
        initComponents();
        this.setTitle("Server");
        this.setVisible(true);
        status.setVisible(true);
    }
    
    public void startRunning()
    {
        try
        {
            server=new ServerSocket(port, totalClients); //Creating new server socket with the given port number
            while(true)
            {
                try
                {
                    status.setText(" Waiting for Someone to Connect...");
                    connection=server.accept(); //accepting the client requests 
                    status.setText(" Now Connected to "+connection.getInetAddress().getHostName());


                    output = new ObjectOutputStream(connection.getOutputStream());
                    output.flush();
                    input = new ObjectInputStream(connection.getInputStream());

                    whileChatting();

                }catch(EOFException eofException)
                {
                	JOptionPane.showMessageDialog(null,"Server Might Be Down!","Warning",JOptionPane.WARNING_MESSAGE);
                }
            }
        }
        catch(IOException ioException)
        {       
        	ioException.printStackTrace();
        }
    }
    
   private void whileChatting() throws IOException
   {
        String message="";    
        jTextField1.setEditable(true);
        do{
                try
                {
                        message = (String) input.readObject();
                        chatArea.append("\n"+message);
                }catch(ClassNotFoundException classNotFoundException)
                {
                       	
                }
        }while(!message.equals("Client - END"));
   }

    @SuppressWarnings("unchecked")
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        chatArea = new javax.swing.JTextArea();
        jTextField1 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        status = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setLayout(null);

        chatArea.setColumns(20);
        chatArea.setRows(5);
        jScrollPane1.setViewportView(chatArea);

        jPanel1.add(jScrollPane1);
        jScrollPane1.setBounds(30, 110, 360, 270);

        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });
        jPanel1.add(jTextField1);
        jTextField1.setBounds(30, 50, 270, 30);

        jButton1.setText("Send");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton1);
        jButton1.setBounds(310, 50, 80, 30);

        status.setText("...");
        jPanel1.add(status);
        status.setBounds(30, 80, 300, 40);

        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Write your text here");
        jPanel1.add(jLabel2);
        jLabel2.setBounds(30, 30, 150, 20);

        
        jLabel1.setIcon(new ImageIcon(getClass().getResource("/ServerSide/bg.jpg"))); // NOI18N
        jPanel1.add(jLabel1);
        jLabel1.setBounds(0, 0, 420, 405);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 417, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 403, Short.MAX_VALUE)
        );

        setSize(new java.awt.Dimension(417, 425));
        setLocationRelativeTo(null);
    }

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {
        
    		sendMessage(jTextField1.getText());
    jTextField1.setText("");
    }

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {
        
        sendMessage(jTextField1.getText());
	jTextField1.setText("");
    }

    private void sendMessage(String message)
    {
        try
        {
            output.writeObject("Server - " + message);
            output.flush();
            chatArea.append("\nServer - "+message);
        }
        catch(IOException ioException)
        {
            chatArea.append("\n Unable to Send Message");
        }
        catch(NullPointerException e)
        {
        	JOptionPane.showMessageDialog(null,"No clients connected!","Warning",JOptionPane.WARNING_MESSAGE);
        }
       
    }

   
    private javax.swing.JTextArea chatArea;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JLabel status;
}
