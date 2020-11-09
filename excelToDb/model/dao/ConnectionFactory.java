package fabio.excelToDb.model.dao;

import java.sql.*;
import java.util.Properties;

public class ConnectionFactory {
	  private static String nomeBanco;
	  private static String usuario;
	  private static String senha;
	  
	  public ConnectionFactory() {
		  
	  }
	  
	  public static Connection getConnection(String db, String user, String pswd) {
	    try {
	      nomeBanco = db;
	      usuario = user;
	      senha = pswd;
	      Class.forName("com.mysql.cj.jdbc.Driver"); // Driver de acesso ao MySQL.
	      // String de conexão (Dados: servidor, porta de conexão, banco de dados, usuário, senha, uso de SSL e fuso horário).
	      // O protocolo SSL criptografa o fluxo de dados entre o servidor de banco de dados 
	      // e a aplicação cliente, protegendo-o de ataques externos.
	      // Dependendo da versão do MySQL e de como seu servidor está configurado, também
	      // pode ser preciso determinar um fuso horário específico do servidor MySQL.
	      String url = "jdbc:mysql://localhost/" + nomeBanco + "?useSSL=false&useTimezone=true&serverTimezone=UTC";
	      Properties props = new Properties();
	      props.setProperty("user", usuario );
	      props.setProperty("password", senha );
	      Connection conn = DriverManager.getConnection(url, props);
	      return conn;
	    } catch(Exception e) {
	      System.out.println(e);
	    }
	    return null;
	  }
	
}
