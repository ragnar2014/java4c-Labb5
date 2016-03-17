//Laboration 5- Nätverkskalkylator
//DAvid Nilsson Löfvall
//DALO1300
//Client
//Lärare: Mikael Nilsson/Robert Jonsson
/**Denna klass är en Klientklass till CalculatorUI och GraphicUI och pratar med CalcServer. Den tar command line argument
 * och skapar antingen en konsolbaserad kalkylator eller en swingbaserd kalkylator. 
 */
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Vector;

import javax.swing.SwingUtilities;

public class Client {
	private static int portNr;
	private static String connectAdr;
	private static Boolean run= true;
	private static String oldResult="";
	private static Socket s=null;
	private static Vector<String> complete= new Vector<String>(3);
	private static ObjectOutputStream out= null;
	private static ObjectInputStream in= null;
	private static CalculatorUI ui= null;



	public static void main(String[] args) throws UnknownHostException, IOException, ClassNotFoundException  {
		// TODO Auto-generated method stub
		
		
		
		if(args.length<2)
		{
			System.out.println("You need adress, portnr and what calculator(GUI or Console");
		}
		else{
			connectAdr= args[0];
		
			try{
				portNr= Integer.parseInt(args[1]);
			}
			catch(NumberFormatException e)
			{
				System.out.println("Not an integer");
			}
			if(args.length>2)
			{
				String CalcType= args[2];
				
				if(CalcType.equals("GUI")||CalcType.equals("gui"))
				{
					//ui= new GraphicalUI();
					try {
						SwingUtilities.invokeAndWait(new Runnable() {
						    public void run() {
						    
								ui= new GraphicalUI();

						    }
						});
					} catch (InvocationTargetException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
						
					                                                                 
					
				}
				
				else
				{
					ui = new ConsoleUI();
				}
			}
			
			
		
		}
		
		
		
do{		
	try{
	
		complete= ui.getExpression();
	}
	catch(ArrayIndexOutOfBoundsException e)
	{
		continue;
	}
	
	
	
	if(complete.size()==3)
	{
		if(complete.get(0).equals("exit") || complete.get(0).equals("EXIT"))
		{
			run= false;
			System.out.println("Avslutar");
			
			
			break;
		}
		
		
		/*if(complete.get(0).equals("CLEAR") || complete.get(0).equals("clear"))
		{
			continue;
			
			
			
			//break;
		}
		
		/*if(complete.get(0).equals("DIVIDE BY ZERO"))
		{
			//System.out.println(complete.get(1));
			
			continue;
		}*/
		
		/*if(complete.get(0).equals("NR ERROR"))
		{
			continue;
		}*/
		
		/*Ansluter till servern*/
		try{
		
				s= new Socket(connectAdr, portNr);
			}
			catch (UnknownHostException addressFel)
			{
				// Kunde inte skapa InetAddress
				addressFel.printStackTrace();
			}
			catch (SocketException socketFel)
			{
				// Kunde inte skapa DatagramSocket
				socketFel.printStackTrace();
			}
			catch (IOException ioFel)
			{
				// Kunde inte skicka paketet
				ioFel.printStackTrace();
			}
			
			
			out= new ObjectOutputStream(s.getOutputStream());
			
			
			out.writeObject(complete);
			out.flush();
			
	
			System.out.print("Skickar meddelandet... ");
			System.out.println("klart");
			
			in= new ObjectInputStream(s.getInputStream());
			 
			String results=(String)in.readObject();
			//Sparar för vidare beräkning
			oldResult= results;
			
			ui.showResult(results);
			
			
		
		
		
	}
	
		
}

	while(run);

	s.close();
	out.close();

	}
	
}
