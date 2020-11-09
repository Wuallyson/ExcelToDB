package fabio.excelToDb;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;

import javax.swing.SwingUtilities;

import fabio.excelToDb.view.EscolherArquivo;


public class App 
{
    public static void main( String[] args ) throws IOException, ParseException, SQLException
    {
    	SwingUtilities.invokeLater(new Runnable() {
			//@Override
			public void run() {
				new EscolherArquivo().setVisible(true);
			}
		});
    }
}
