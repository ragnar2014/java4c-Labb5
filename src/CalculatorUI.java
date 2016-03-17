//Laboration 5- N�tverkskalkylator
//DAvid Nilsson L�fvall
//DALO1300
//CalculatorUI
//L�rare: Mikael Nilsson/Robert Jonsson
/**Denna klass �r ett interface och �r angiven av labbeskrivningen
 */

import java.net.UnknownHostException;
import java.util.Vector;

public interface CalculatorUI {
/** getExpression h�mtar ber�kningsuttrycket fr�n anv�ndaren.
Den returnerade Vector inneh�ller
operand1 operator operand2
 * 
*/
public Vector<String> getExpression();
/** showResult presenterar resultatet av ber�kningen,
eventuellt ett felmeddelande
*/
public void showResult(String result);


}


