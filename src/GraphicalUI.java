//Laboration 5- Nätverkskalkylator
//DAvid Nilsson Löfvall
//DALO1300
//Lärare: Mikael Nilsson/Robert Jonsson

/**GraphicalUI är en swing variant på en kalkylator som skickar en vektor med
 * tal och operator till en server som i sin tur skapar CalcObjekt som utför beräkningen
 *Implementerar interfacet CalculatorUI**                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                    */
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Vector;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

/**
 * 
 */

/**
 * @author David
 *
 */
public class GraphicalUI extends JFrame implements CalculatorUI  {
	
	//Lägger in namn på knapparna i 2D array
	public static final String [][] calcbuttons= {
			{"<-","CE", "C", "+"},
			{"7", "8","9","-"},
			{"4", "5", "6","/"},
			{"1", "2", "3","*"},
			{"0", " ", " ", "="}	
			
	};
	public static final Font calcFont = new Font(Font.MONOSPACED, Font.BOLD, 20);
	
	private JPanel GraphicalUI;
	private JPanel mainFrame;

	private JTextField result;
	private int operand1, operand2, totalSum,counter;
	private String op, previousTask, secondOperand;
	private Boolean operatorInPlace= false;
	private static Vector <String> complete= new Vector<String>(3);
	
	

	public GraphicalUI(){
	super("Kalkylator");
	
	
	ActionListener numbers= new ActionListener() {
		@Override
		
		public void actionPerformed(ActionEvent e) {
			
			String input= e.getActionCommand();
			String outResult=null;
			
			//Kontrollerar typ av input
			if(input.contains("+")|| input.contains("-")|| input.contains("/")||input.contains("*"))
			{
				//Sparar nuvarande läge för t ex CE funktionen
				previousTask= result.getText();

					if(operatorInPlace)
					{
						
						result.setText("Too many operators");
					}
					else
					{
						//Lägger till talet innan operator
						if(complete.isEmpty())
						{
							complete.add(result.getText());
						}
						try{
						operand1= Integer.parseInt(result.getText());
						op= input;
						//Lägger till operand
						complete.add(op);
						operatorInPlace= true;
						input= result.getText()+op;
						result.setText(input);
						}
						catch(NumberFormatException error)
						{
							result.setText("No integer or operator input");
							//result.setText("");
							operand1=0;
							operand2=0;
							operatorInPlace=false;
							totalSum=0;
							counter=0;
							op="";
							complete.clear();
						}
						
						
						if(counter==0)
						{
							counter++;
						}
						
					}
			}
			
			else if(input.contains("CE"))
			{
			
				result.setText(previousTask);
				
				
				if(counter==1 && complete.isEmpty())
				{
					
					operatorInPlace= false;
					
				}
				
				
				
				else if(counter==1 && operatorInPlace)
				{
					operatorInPlace=false;
					
					complete.remove(1);
				}
				
				else
				{
					operatorInPlace=false;
					counter--;
					complete.remove(1);
				}
				
				
			}
			
			else if(input.contains("C"))
			{
				result.setText("");
				operand1=0;
				operand2=0;
				operatorInPlace=false;
				totalSum=0;
				counter=0;
				op="";
				complete.clear();
			}
			//Här skickar vi Vectorn till servern
			else if(input.contains("="))
			{
			 try{
				 //Kontroll för division med noll
				if(operatorInPlace&& counter==2)
				{
					if(op=="/" && operand2==0)
					{
						throw new IllegalArgumentException();
					}
					secondOperand= Integer.toString(operand2);
					complete.add(secondOperand);
					
					//totalSum= calculate (operand1,op, operand2);
					
					
					//outResult=Integer.toString(totalSum);
					//JOptionPane.showMessageDialog(mainFrame, totalSum);
					//result.setText(outResult);
					
					
					
					
					operatorInPlace=false;
					counter--;
					operand1= totalSum;
					operand2=0;
					op="";
					
					
				}
			 }
			 catch(Exception a)
			 {
				 
				 result.setText("Can't divide by zero");
				/* complete.clear();
				 counter=0;
				 op="";
				 operand1=0;
				 operand2=0;
				 operatorInPlace=false;*/
			 }
			}
		
			else if(counter==1)
			{
				
				operand2= Integer.parseInt(input);
				result.setText(result.getText()+input);
				counter++;
				
			}
			
			else 
			{
				result.setText(result.getText()+input);
			
			}
			
			
		
	}
		
	};
	
	
	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	
	GraphicalUI= new JPanel(new GridLayout(calcbuttons.length, calcbuttons[0].length ));
	
	for(int i=0; i<calcbuttons.length; i++)
	{
		for (int j = 0; j <calcbuttons[i].length; j++) {
            JButton btn = new JButton(calcbuttons[i][j]);
            GraphicalUI.setFont(calcFont);
            
            GraphicalUI.add(btn);

            btn.addActionListener(numbers);
         }
		
	}
	result= new JTextField(15);
	result.setHorizontalAlignment(SwingConstants.RIGHT);
	result.setFont(calcFont);
	result.setEditable(false);
	
	
	mainFrame= new JPanel(new BorderLayout());
	mainFrame.add(result,BorderLayout.PAGE_START);
	mainFrame.add(GraphicalUI);

	super.add(mainFrame);
	
	pack();
	setLocation(500, 500);
	setSize(300, 300);
	setVisible(true);
	
	
	
	}
	
	public int calculate(int op1, String oper, int op2){
		char newOper;
		newOper=oper.charAt(0);
		int finalResult = 0;
		switch(newOper)
		{
			case '+': finalResult=op1+op2;
			break;
			case '-': finalResult=op1-op2;
			break;
			case '*': finalResult= op1*op2;
			break;
			case '/': finalResult=op1/op2;
			break;
		}

		return finalResult;
	}

	@Override
	public Vector<String> getExpression() {
		// TODO Auto-generated method stub
		return complete;
	}

	@Override
	public void showResult(String results) {
		//operand1= Integer.parseInt(results);
		complete.clear();
		//complete.add(results);
		//counter++;
		result.setText(results);
		// TODO Auto-generated method stub
		
	}
	

	

}