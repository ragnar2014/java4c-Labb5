//Laboration 5- Nätverkskalkylator
//DAvid Nilsson Löfvall
//DALO1300
//ConsoleUI
//Lärare: Mikael Nilsson/Robert Jonsson
/**Denna klass är en konsolbaserad  kalkylator som implementerar interfacet CalculatorUI
 */
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ConnectException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.util.Vector;

public class ConsoleUI implements CalculatorUI {
	private static int operand1, operand2, totalSum,counter, index;
	private static String op1, op, op2, oldResult;
	private static int quit=-1;
	CharSequence clear= "CLEAR";
	private static Boolean operatorInPlace= false;
	private static Boolean again=true;
	private static Boolean run=true;

	 static char []operatorArr= new char[]{'+', '-', '/', '*'};
	private static Vector<String> complete; 
	public static 
boolean isNumber(String check){
		boolean isNumeric= false;
		
		if(check!= null && !check.equals(""))
		{
			isNumeric=true;
			char chars[]= check.toCharArray();
			
			for(int i=0; i<chars.length; i++)
			{
				isNumeric &=Character.isDigit(chars[i]);
				if(!isNumeric)
				break;
			}
			
		}
		
		return isNumeric;
	}

//Implementerar abstrakt funktion
public Vector<String> getExpression()
	{
		
	do{
		
		complete= new Vector<String>(3);
		
		System.out.println("To clear results type CLEAR anywhere");
		System.out.println("Input: ");
		if(oldResult==null)
		{
			oldResult="";
			
		}
		
		System.out.print(oldResult);
		
		Scanner input= new Scanner(System.in);
		String rawInput= input.nextLine();
		
	//FÖRSÖK TILL AVSLUTA MINiRÄKNARE
		if(rawInput.contains("EXIT")|| rawInput.contains("exit"))
		{
			again=false;
			run=false;
			complete.add(rawInput);
			return complete;
			
			//s.close();
			//break;
		}
		
		if(rawInput.contains(clear)|| rawInput.contains("clear"))
		{
			oldResult="";
			complete.clear();
			continue;
			//complete.add("CLEAR");
			//return complete;

			
		}
		
//Leta genom sträng efter operator		
	for( int i=0; i<4; i++)
	{
		index= rawInput.indexOf(operatorArr[i]);
		if(index!=-1)
		{
			i=4;
			break;
		}
	}
	
	if(index==-1)
	{
		System.out.println("You forgot to input an operator!");

	}
	
	
	else
	{
		if(oldResult=="")
		{
			
		op1= rawInput.substring(0, index);
		
		if(!isNumber(op1))
		{
			System.out.println("First operator is non numeric");
			complete.add("NR ERROR");
		}
		//ReplaceAll rensar strängen från andra tecken än siffror
		String cleanOp1= op1.replaceAll("[^0-9]", "");
		
		//Den första operatorn som anges kommer gälla, oavsett hur många man råkat klicka in
		op=rawInput.substring(index, index+1);
		
		op2= rawInput.substring(index+1, rawInput.length());
		if(!isNumber(op2))
		{
			System.out.println("Second operator is non numeric");
			complete.clear();
			continue;
			//complete.add("NR ERROR");
		}
		//ReplaceAll rensar strängen från andra tecken än siffror utifall att man skulle råkat använda 
		//fler operatorer än en
		String cleanOp2= op2.replaceAll("[^0-9]", "");
		
		for( int i=0; i<4; i++)
		{
			int doublecheck;
			doublecheck= op2.indexOf(operatorArr[i]);
			if(doublecheck>0)
			{
				System.out.println("Too many operators!!");

			}
		}
		if(op.contains("/")&& cleanOp2.contains("0"))
		{
			System.out.println("Can't divide by zero");
			continue;
		}
		
		complete.add(cleanOp1);
		complete.add(op);
		complete.add(cleanOp2);
			if(complete.get(1).equals("/") && complete.get(2).equals("0"))
			{
				System.out.println("Can't divide by zero");
				complete.clear();
				//complete.add("DIVIDE BY ZERO");
				//complete.add("You can't divide by zero");
			
			}
			run=false;

		}
		
		else
		{
			
			complete.add(oldResult);
			op=rawInput.substring(index, index+1);
			op2= rawInput.substring(index+1, rawInput.length());
			//ReplaceAll rensar strängen från andra tecken än siffror utifall att man skulle råkat använda 
			//fler operatorer än en
			String cleanOp2= op2.replaceAll("[^0-9]", "");
			complete.add(op);
			complete.add(cleanOp2);
			
			if(complete.get(1).equals("/") && complete.get(2).equals("0"))
			{
				System.out.println("Can't divide by zero");
				complete.clear();
				//complete.add("DIVIDE BY ZERO");
				//complete.add("You can't divide by zero");
			
			}
			run=false;

		}
		
		
		
		
	}
	
	
	}while(run);
	
		
		
		return complete;
		
	}

	@Override
	public void showResult(String result) {
		oldResult= result;
		System.out.println("Resultat: "+result);
		// TODO Auto-generated method stub
		
	}



	 
	 
	 
	 
	 
	 
	 
}
	
	

	
		


