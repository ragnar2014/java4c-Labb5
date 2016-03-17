//Laboration 5- Nätverkskalkylator
//DAvid Nilsson Löfvall
//DALO1300
//CalculatorUI
//Lärare: Mikael Nilsson/Robert Jonsson
/**Denna klass är ett interface och är angiven av labbeskrivningen
 */

import java.net.UnknownHostException;
import java.util.Vector;

public interface CalculatorUI {
/** getExpression hämtar beräkningsuttrycket från användaren.
Den returnerade Vector innehåller
operand1 operator operand2
 * 
*/
public Vector<String> getExpression();
/** showResult presenterar resultatet av beräkningen,
eventuellt ett felmeddelande
*/
public void showResult(String result);


}


