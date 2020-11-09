package fabio.excelToDb.model.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import fabio.excelToDb.model.*;

public class PlanilhaGateway {
	private String mesAno; // Formato: Setembro-2020
	private String banco;
	private String usuario;
	private String senha;
	private Integer idMesAno;
	private Planilha plan = new Planilha();
	String SQL_INSERT;
	
	//Lista de Strings que conterá os scripts SQL
	List<String> scriptSQL = new ArrayList<String>();
	
	//Lista de Arrays de Strings: [0] é a categoria e [1] é o ID correspondente
	//Essa lista é lida diretamente do banco
	List<String[]> listaCatIDBanco = new ArrayList<String[]>();
	
	//Array de Strings auxiliar, com as categorias lidas do banco
	List<String> listaCatBanco = new ArrayList<String>();
	
	//Novas categorias detectadas na planilha serão armazenadas nessa lista
	//e inseridas no banco de dados
	List<String> listaNovasCategorias = new ArrayList<String>();
	
	//Essa classe contém definições dos dados tais como estão no banco de dados
	//os nomes da tabela e seus atrubutos
	EstruturaDB estdb = new EstruturaDB();
	
	//Instância de ConnectionFactory, para realizar as conexões ao banco de dados
	//private Connection conn = ConnectionFactory.getConnection();
	private Connection conn;
	//private ConnectionFactory conn;
	private Statement stmt;
	//Statement stmt = conn.createStatement();
	
	/*Construtor da classe: recebe uma planilha e o mês de referência da planilha (Ex.: Setembro-2020)
	 * O construtor inicia uma transação;
	 * Verifica as categorias e seus ID's e se eles correspondem ao do banco, ou se uma nova categoria foi inserida na planilha;
	 * Caso haja uma nova categoria na planilha ela é inserida na banco;*/
	public PlanilhaGateway(String mesAno, Planilha planilha, String banco, String usuario, String senha) throws SQLException {
		this.mesAno = mesAno;
		this.plan = planilha;
		this.banco = banco;
		this.usuario = usuario;
		this.senha = senha;
		
		conn = ConnectionFactory.getConnection(this.banco, this.usuario, this.senha);
		stmt = conn.createStatement();
		
		stmt.executeQuery("START TRANSACTION;");

		scriptSQL.add("START TRANSACTION;");
		
		//Insere o novo mês no banco de dados
		SQL_INSERT = "INSERT INTO mes_ano (mes_ano, data_referencia) VALUES (?, curdate());";
		PreparedStatement pstm = conn.prepareStatement(SQL_INSERT);
		pstm.setString(1, this.mesAno);
		scriptSQL.add(pstm.toString());
		pstm.execute();
		
		//Lê do banco de dados qual o identificador do novo mês
		String SQL_SELECT = "SELECT id_mes_ano FROM mes_ano WHERE mes_ano = ?;";
		pstm = conn.prepareStatement(SQL_SELECT);
		pstm.setString(1, this.mesAno);
		
		ResultSet rs = pstm.executeQuery();
		rs.next();
		idMesAno = rs.getInt(1);
		scriptSQL.add(pstm.toString());
		scriptSQL.add("/*" + idMesAno + "*/");
		
		//Leitura das categorias e respectivos identificadores do banco de dados
		rs = stmt.executeQuery("SELECT * FROM categorias;");
		while(rs.next()){
			String catBD[] = {"",""};//1°: categoria; 2°: ID
			catBD[0] = rs.getString("categoria");
			catBD[1] = Integer.toString(rs.getInt("id"));
			listaCatIDBanco.add(catBD);
		};
		
		//Captura apenas o nome da categoria em uma lista de String
		//Para facilitar a comparação com as categorias da planilha
		for(int var=0; var < listaCatIDBanco.size(); var++) {
			listaCatBanco.add( listaCatIDBanco.get(var)[0] );
		}
		
		//Lê as categorias do banco e compara com as da planilha, retorna a lista
		//de categorias presente na planilha mas não no banco, ou seja, as novas 
		//categorias.
		listaNovasCategorias = plan.sincronizaCategorias( listaCatBanco );
		
		//Se a lista com novas categorias não estiver vazia as novas categorias são inseridas no banco,
		//em seguida são novamente lidas as categorias e identificadores do banco de dados
		if( !listaNovasCategorias.isEmpty() ) {
			for(String novaCategoria :  listaNovasCategorias) {
				SQL_INSERT = "INSERT INTO categorias (categoria, data_inclusao) VALUES (?, curdate())";
				pstm = conn.prepareStatement(SQL_INSERT);
				pstm.setString(1, novaCategoria);
				pstm.execute();
				scriptSQL.add(pstm.toString());
			}
			listaCatIDBanco.clear();
			rs = stmt.executeQuery("SELECT * FROM categorias;");
			while(rs.next()){
				String catBD[] = {"",""};//1° categoria 2° ID
				catBD[0] = rs.getString("categoria");
				catBD[1] = Integer.toString(rs.getInt("id"));
				listaCatIDBanco.add(catBD);
			}
		}
		
		rs.close();
		pstm.close();
	}
	
	//retorna o ID do mês e do ano
	public Integer getIdMesAno() {
		return idMesAno;
	}
	
	/*Esse método pega todos oa dados das tabelas da planilha e os insere no banco de dados*/
	public void insertOnDb() throws SQLException {
		
		List<GastosGerais> tabelaGastosGerais = plan.retornaGastosGerais();
		List<Receitas> tabelaReceitas = plan.retornaReceitas();
		List<DespesasFixas> tabelaDespesasFixas = plan.retornaDespesasFixas();
		List<DespesasVariaveis> tabelaDespesasVariaveis = plan.retornaDespesasVariaveis();
		List<Totais> tabelaTotais = plan.retornaTotais();
		List<GastosCategoria> tabelaGastosCategoria = plan.retornaGastosCategoria();
		
		
		scriptSQL.add("\n\n/*GASTOS GERAIS***********************************************************************************/");
		for(GastosGerais linhaGastosGerais : tabelaGastosGerais) {
			SQL_INSERT = "INSERT INTO " + estdb.getNomeTabela(0) + " " + estdb.getAtributosTabela(0) + " VALUES (?, ?, ?, ?, ?);";
			PreparedStatement pstm = conn.prepareStatement(SQL_INSERT);
			java.util.Date dt = linhaGastosGerais.getData();
			java.sql.Date d = new java.sql.Date (dt.getTime());
			pstm.setDate(1, d );
			pstm.setString(2,linhaGastosGerais.getDescricao());
			//O método getIdCategoria recebe o nome de uma categoria e devolve o ID correspondente
			pstm.setInt(3, (int) plan.getIdCategoria( linhaGastosGerais.getCategoria(), listaCatIDBanco ));
			pstm.setDouble(4, linhaGastosGerais.getValor());
			pstm.setInt(5, idMesAno);
			scriptSQL.add(pstm.toString());
			pstm.execute();
		}
		
		scriptSQL.add("\n\n/*RECEITAS****************************************************************************************/");
		for(Receitas linhaReceitas : tabelaReceitas) {
			SQL_INSERT = "INSERT INTO " + estdb.getNomeTabela(1) + " " + estdb.getAtributosTabela(1) + " VALUES (?, ?, ?, ?, ?);";
			PreparedStatement pstm = conn.prepareStatement(SQL_INSERT);
			pstm.setString(1, linhaReceitas.getDescricao());
			pstm.setString(2, linhaReceitas.getFonte());
			//Aqui é necessário fazer um 'cast' do valor da data
			if(linhaReceitas.getDataRecebimento() != null) {
				java.util.Date dt = linhaReceitas.getDataRecebimento();
				java.sql.Date d = new java.sql.Date (dt.getTime());
				pstm.setDate(3, d);
			} else {
				pstm.setNull(3, 91);
			}
			pstm.setDouble(4, linhaReceitas.getValor());
			pstm.setInt(5, idMesAno);
			scriptSQL.add(pstm.toString());
			pstm.execute();
		}
		
		scriptSQL.add("\n\n/*DESPESAS FIXAS**********************************************************************************/");
		for(DespesasFixas linhaDespesasFixas : tabelaDespesasFixas) {
			SQL_INSERT = "INSERT INTO " + estdb.getNomeTabela(2) + " " + estdb.getAtributosTabela(2) + " VALUES (?, ?, ?, ?, ?, ?, ?);";
			PreparedStatement pstm = conn.prepareStatement(SQL_INSERT);
			pstm.setString(1, linhaDespesasFixas.getDescricao());
			pstm.setInt(2, plan.getIdCategoria( linhaDespesasFixas.getCategoria(), listaCatIDBanco ));
			pstm.setDouble(3, linhaDespesasFixas.getValor());
			pstm.setString(4, linhaDespesasFixas.getStatus());
			if(linhaDespesasFixas.getData() != null) {
				java.util.Date dt = linhaDespesasFixas.getData();
				java.sql.Date d = new java.sql.Date (dt.getTime());
				pstm.setDate(5, d);
			} else {
				pstm.setNull(5, 91);
			}
			pstm.setString(6, linhaDespesasFixas.getObservacao());
			pstm.setInt(7, idMesAno);
			scriptSQL.add(pstm.toString());
			pstm.execute();
		}
		
		scriptSQL.add("\n\n/*DESPESAS VARIAVEIS******************************************************************************/");
		for(DespesasVariaveis linhaDespesasVariaveis : tabelaDespesasVariaveis) {
			SQL_INSERT = "INSERT INTO " + estdb.getNomeTabela(3) + " " + estdb.getAtributosTabela(3) + " VALUES (?, ?, ?, ?, ?, ?, ?, ?);";
			PreparedStatement pstm = conn.prepareStatement(SQL_INSERT);
			pstm.setString(1, linhaDespesasVariaveis.getDescricao());
			pstm.setInt(2, plan.getIdCategoria( linhaDespesasVariaveis.getCategoria(), listaCatIDBanco ));
			
			//Nesse caso deve ser inserido null ao inves de 0.0 se o valor for zero ou se o campo estiver em branco
			if(linhaDespesasVariaveis.getValorPlanejado() != 0)
				pstm.setDouble(3, linhaDespesasVariaveis.getValorPlanejado());
			else
				pstm.setNull(3, 8);
			
			if(linhaDespesasVariaveis.getValorPago() != 0)
				pstm.setDouble(4, linhaDespesasVariaveis.getValorPago());
			else
				pstm.setNull(4, 8);
			
			pstm.setString(5, linhaDespesasVariaveis.getStatus());
			if(linhaDespesasVariaveis.getData() != null) {
				java.util.Date dt = linhaDespesasVariaveis.getData();
				java.sql.Date d = new java.sql.Date (dt.getTime());
				pstm.setDate(6, d);
			} else {
				pstm.setNull(6, 91);
			}
			pstm.setString(7, linhaDespesasVariaveis.getObservacao());
			pstm.setInt(8, idMesAno);
			scriptSQL.add(pstm.toString());
			pstm.execute();
		}
		
		scriptSQL.add("\n\n/*TOTAIS******************************************************************************************/");
		for(Totais linhaTotais : tabelaTotais) {
			SQL_INSERT = "INSERT INTO " + estdb.getNomeTabela(4) + " " + estdb.getAtributosTabela(4) + " VALUES (?, ?, ?, ?, ?);";
			PreparedStatement pstm = conn.prepareStatement(SQL_INSERT);
			pstm.setDouble(1, linhaTotais.getValorDisponivel());
			pstm.setDouble(2, linhaTotais.getDespesas());
			pstm.setDouble(3, linhaTotais.getReceitas());
			pstm.setDouble(4, linhaTotais.getaPagar());
			pstm.setInt(5, idMesAno);
			scriptSQL.add(pstm.toString());
			pstm.execute();
		}
		
		scriptSQL.add("\n\n/*GASTOS POR CATEGORIA****************************************************************************/");
		for(GastosCategoria linhaGastosCategoria : tabelaGastosCategoria) {
			SQL_INSERT = "INSERT INTO " + estdb.getNomeTabela(5) + " " + estdb.getAtributosTabela(5) + " VALUES (?, ?, ?);";
			PreparedStatement pstm = conn.prepareStatement(SQL_INSERT);
			pstm.setInt(1, plan.getIdCategoria( linhaGastosCategoria.getCategoria(), listaCatIDBanco ) );
			pstm.setDouble(2, linhaGastosCategoria.getTotalGasto());
			pstm.setInt(3, idMesAno);
			scriptSQL.add(pstm.toString());
			pstm.execute();
		}
		
		stmt.executeQuery("COMMIT;");
		scriptSQL.add("COMMIT;");
		stmt.close();
	}//método insertOnDb
	
	
	/*Este método acrescenta no scriptSQL.txt um script para desfazer todas as alterações que o programa fez no banco
	 * desde o inicio da execução, depois disso retorna o list que contêm o script*/
	public List<String> getScript() {
		                   
		scriptSQL.add("\n\n/*DESFAZER SCRIPT DE INSERÇÃO*********************************************************************/");
		scriptSQL.add("START TRANSACTION;");
		scriptSQL.add("DELETE FROM gastos_gerais WHERE id_mes_ano = " + idMesAno + ";");
		scriptSQL.add("DELETE FROM receitas WHERE id_mes_ano = " + idMesAno + ";");
		scriptSQL.add("DELETE FROM despesas_fixas WHERE id_mes_ano = " + idMesAno + ";");
		scriptSQL.add("DELETE FROM despesas_variaveis WHERE id_mes_ano = " + idMesAno + ";");
		scriptSQL.add("DELETE FROM totais WHERE id_mes_ano = " + idMesAno + ";");
		scriptSQL.add("DELETE FROM gastos_categoria WHERE id_mes_ano = " + idMesAno + ";");
		scriptSQL.add("DELETE FROM mes_ano WHERE id_mes_ano = " + idMesAno + ";");
		
		//Caso novas categorias tenham sido adicionadas é acrescentado um trecho para desfazer esse acréscimo
		if( !listaNovasCategorias.isEmpty() )
			for(String newReg : listaNovasCategorias) {
				scriptSQL.add("DELETE FROM categorias WHERE categoria = '" + newReg + "';");
			}
		scriptSQL.add("COMMIT;");
		return scriptSQL;
	}
	
}
