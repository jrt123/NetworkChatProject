//Name: Joseph Taylor
//Date: 3/24/2019
//HW5Q6

import java.io.*;
import java.net.*;
import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;

public class simpleChatRoom implements Runnable {
	boolean isThere = false;
	HashSet<String> ips = new HashSet<String>();
    public static int Port = 1777;
	private static final File DATA_FILE = new File("C:\\Users\\JRT12\\Desktop\\Joseph_Taylor_HW5\\Joseph_TaylorHW5Q6\\Joseph_TaylorHW5Q6\\src\\chatSaved.txt");


    /*     Create the GUI Chat Room and listen to message
     *     on local area network. 
     *     Please do not modify. 
     */
    // Variables declaration - do not modify                     
    static JTextPane content = new JTextPane();
    
    static StyledDocument doc = content.getStyledDocument();
    // End of variables declaration   
    
    public static void chatRoomGUI() {
    	
        //Create and set up the window 
        JFrame frame = new JFrame("Chat Room");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        try {
			doc.insertString(0, "Start of Text\n", null);
			//doc.insertString(doc.getLength(),"\nEnd of text", null);
		} catch (BadLocationException e) {

			e.printStackTrace();
		}
        
        /*content.setText("Welcome");
        content.setLineWrap(true);
        //
        content.insertString("Start chatroom.......\n");*/
        JScrollPane scroll = new JScrollPane(content);
        scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        frame.getContentPane().add(scroll,BorderLayout.CENTER);
        frame.setPreferredSize(new Dimension(400, 600));
        frame.setAlwaysOnTop(true);
        frame.pack();
        frame.setVisible(true);
        
    }

    @Override
    public void run() {
    		clientGUI client = new clientGUI();
    		client.setTitle("Client Messaging");
			client.setSize(450,250);
			client.setResizable(false);
			client.setLocationRelativeTo(null);
			client.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			client.setVisible(true);	
        //Listen to UDP packets.
			FileWriter fw;
			try {
				fw = new FileWriter( DATA_FILE,true ); //true here is used to append for next time some one is on. We could probably add a date between too later.
				
				 try {
			            DatagramSocket server = new DatagramSocket(Port);
			            
			            while (true) {
			            	
			                // receive a packet 
			                byte[] buf = new byte[256];
			                DatagramPacket packet = new DatagramPacket(buf, buf.length);
			                server.receive(packet);
			                
			                ips.add(packet.getAddress().toString());//used to get connected user IP address. 

			                //System.out.println(ips);
			                // display response
			                String received = new String(packet.getData(), 0, packet.getLength());  
			                String message = received.split(":")[1];
			                //System.out.println(message);
			                if(message.equals(" has left...")) {
			                	
			                try {
			                	 //System.out.println("it worked");
			                	 ImageIcon red = new ImageIcon("C:\\Users\\JRT12\\Desktop\\Joseph_Taylor_HW5\\Joseph_TaylorHW5Q6\\Joseph_TaylorHW5Q6\\src\\redlight.png");
			                	 Style style = doc.addStyle("StyleName", null);
			                	 StyleConstants.setIcon(style, red);
			                	
								doc.insertString(doc.getLength(),"Ignored",style);
								doc.insertString(doc.getLength(), received + "\n", null);
								
								fw.write(received);
								fw.close();
							} catch (BadLocationException e) {
								// TODO Auto-generated catch block
								System.out.print("Writing or Receiving failed!");
							}
			                }else {
			                	 try {
			                		 //System.out.println("it worked");
			                    	 ImageIcon red = new ImageIcon("C:\\Users\\JRT12\\Desktop\\Joseph_Taylor_HW5\\Joseph_TaylorHW5Q6\\Joseph_TaylorHW5Q6\\src\\greenlight.png");
			                    	 Style style = doc.addStyle("StyleName", null);
			                    	 StyleConstants.setIcon(style, red);
			     					fw.write(received+"\n");
			     					
			    					doc.insertString(doc.getLength(),"Ignored",style);
			    					doc.insertString(doc.getLength(), received + "\n", null);
			     				} catch (BadLocationException e) {
			     					// TODO Auto-generated catch block
			     					System.out.print("Writing or receiving failed!");
			     				}
			                }
			                
			              
			            }
			        } catch (IOException e) {
			            System.err.print(e.getMessage());
			        }
				
				
				
				
				
				
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			
       
    }

    public static void main(String[] args) throws IOException {
        {
        	
            //creating and showing this application's GUI.
            javax.swing.SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    chatRoomGUI();
                  
                }
            });

            //Start a thread to listen to the packet
            (new Thread(new simpleChatRoom())).start();
            
         
            /* Please start your client-side codes here. 
             * Please send all your packets to port 1777
             */
             
            
          
           
            
            
            
            
            /*
            * End of your code
            */

        }
    }
    
    
    class clientGUI extends JFrame{
    	
        String getInput = "" ;
    	JPanel master = new JPanel(new FlowLayout(FlowLayout.LEFT));
    		JPanel holdAll = new JPanel(new BorderLayout());
    		JPanel holdName = new JPanel();
    		JPanel holdInput = new JPanel();
    		JTextField Name = new JTextField();
    		JTextField input = new JTextField();
    		JLabel nameLBL = new JLabel("Name: ");
    		JLabel inputLBL = new JLabel("Input: ");
    		String getName = "";
    		JButton changeNam = new JButton("Change Name");
    		
    		JButton logoff = new JButton("Log Off");
    		String combine = "";
    		boolean logoffNow = false;
    		String tempName = "";
    	clientGUI(){
    		
    		
    		  /*Scanner in = new Scanner(System.in);
              String name = "";
              String combine = "";
              System.out.println("Client logged in.");
              System.out.println("Please enter your name: ");
              name = in.nextLine();
              System.out.println("Start type below: ");
              */
    		Name.setPreferredSize( new Dimension( 200, 24 ) );
    		input.setPreferredSize( new Dimension( 200, 24 ) );
    		holdName.add(nameLBL);
    		holdName.add(Name);
    		holdInput.add(inputLBL);
    		holdInput.add(input);
    		holdAll.add(holdName,BorderLayout.NORTH);
    		holdAll.add(holdInput,BorderLayout.CENTER);
            JPanel holdLog = new JPanel();
    		holdLog.add(logoff);
    		holdAll.add(holdLog,BorderLayout.SOUTH);

              try {

             getInput = "Hello...";
             
            	  DatagramSocket client = new DatagramSocket();
            
              logoff.addActionListener(e->{
            	  try {
            		  
            		  String leave = getName + ": has left...";
            		  
            		  byte[] bf = leave.getBytes();
                      InetAddress addr = InetAddress.getByName("255.255.255.255");
                      DatagramPacket packet2 = new DatagramPacket(bf,bf.length,addr,Port);
          
                      client.send(packet2);
                      client.close();
                      
            	  }catch(Exception e3) {
            		    System.out.print("Failed");
            	  }
           	  });
             
              
              changeNam.addActionListener(e->{
            	  Name.setEditable(true);
            	  
              });
              
              Name.addFocusListener(new FocusListener() {

                  @Override
                  public void focusGained(FocusEvent e) {
                      Name.selectAll();
                  }

                  @Override
                  public void focusLost(FocusEvent e) {
                    getName = Name.getText();
                    tempName = Name.getText();
                    Name.setEditable(false);
                  }
              });
              input.addActionListener(e->{
            	  
            	if(getName.contentEquals("")) {
            		
            		JOptionPane.showMessageDialog(this, "Name is empty!");
            	}else {
            		getInput = input.getText();
              		 //System.out.print(getInput);
              		 try {
               		  
                  	    
                  	    combine = getName + ": "+ getInput;
                  	    byte[] bf = combine.getBytes();
                          InetAddress addr = InetAddress.getByName("255.255.255.255");
                          DatagramPacket packet2 = new DatagramPacket(bf,bf.length,addr,Port);
              
                          client.send(packet2);
              		}catch(Exception e2) {
               			System.out.print("Something went wrong!");
              		}
               		 input.setText("");
               		 
            	}
          		 
            		 
           	 });
	    

              }catch(Exception e) {
            	  System.out.print("Fail");
              }
            JPanel btnPanel = new JPanel(new GridLayout(2,1));
            JPanel holdNam = new JPanel();

            holdNam.add(changeNam);
            btnPanel.add(holdNam);

            master.add(holdAll);
            master.add(btnPanel);
            add(master);
          

    	}
    	
    }
    
    
    
}