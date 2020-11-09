package fabio.excelToDb.model.dao;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

import fabio.excelToDb.model.DespesasFixas;
import fabio.excelToDb.model.DespesasVariaveis;
import fabio.excelToDb.model.EstruturaDB;
import fabio.excelToDb.model.GastosCategoria;
import fabio.excelToDb.model.GastosGerais;
import fabio.excelToDb.model.Planilha;
import fabio.excelToDb.model.Receitas;
import fabio.excelToDb.model.Totais;

//Essa classe é responsavel por receber um list de String, tratar e armazenar em um arquivo exeterno
public class GerarScript {
	private String path;
	String sqlInstruction;
	
	//Essa classe contém definições dos dados tais como estão no banco de dados
	//os nomes da tabela e seus atrubutos
	EstruturaDB estdb = new EstruturaDB();
	
	//O construtor recebe uma string com caminho, nome e tipo de arquivo que deve ser criado
	public GerarScript(String path) {
		this.path = path;
	}
	
	//Método cria ou sobrecreve arquivo com o script SQL de inserção ou desfazimento das operações efetuadas
	public void exportarScript(List<String> list) {
		try {
			FileWriter fw = new FileWriter(path);
			BufferedWriter bw = new BufferedWriter(fw);
			for(String reg : list) {
				//é realizado aqui um tratamento para retirar trecho de informação inserida pelo método toString de PreparedStatement
				if(reg.contains("com.mysql.cj.jdbc.ClientPreparedStatement: "))
					reg = reg.replace("com.mysql.cj.jdbc.ClientPreparedStatement: ", "");
				bw.write(reg + "\n");
			}
			bw.close();
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}
	
	public List<String> criarScript(Planilha plan, String mesAno) {
		List<GastosGerais> tabelaGastosGerais = plan.retornaGastosGerais();
		List<Receitas> tabelaReceitas = plan.retornaReceitas();
		List<DespesasFixas> tabelaDespesasFixas = plan.retornaDespesasFixas();
		List<DespesasVariaveis> tabelaDespesasVariaveis = plan.retornaDespesasVariaveis();
		List<Totais> tabelaTotais = plan.retornaTotais();
		List<GastosCategoria> tabelaGastosCategoria = plan.retornaGastosCategoria();
		
		//Lista de Strings que conterá os scripts SQL
		List<String> scriptSQL = new ArrayList<String>();
		
		scriptSQL.add("START TRANSACTION;");
		scriptSQL.add("INSERT INTO mes_ano (mes_ano, data_referencia) VALUES ('" + mesAno + "', curdate());");
		
		scriptSQL.add("\n\n/*GASTOS GERAIS***********************************************************************************/");
		for(GastosGerais linhaGastosGerais : tabelaGastosGerais) {
			java.util.Date dt = linhaGastosGerais.getData();
			java.sql.Date d = new java.sql.Date (dt.getTime());
			scriptSQL.add("INSERT INTO " + estdb.getNomeTabela(0) + " " + estdb.getAtributosTabela(0) + " VALUES ('" + 
							d + "', " +
							"'" + linhaGastosGerais.getDescricao() + "', " +
							"(SELECT id FROM categorias WHERE categoria='" + linhaGastosGerais.getCategoria() + "'), " +
							linhaGastosGerais.getValor() + ", " +
							"(SELECT id_mes_ano FROM mes_ano WHERE mes_ano='" + mesAno + "'));");
		}
		
		scriptSQL.add("\n\n/*RECEITAS****************************************************************************************/");
		for(Receitas linhaReceitas : tabelaReceitas) {
			
			sqlInstruction = "INSERT INTO " + estdb.getNomeTabela(1) + " " + estdb.getAtributosTabela(1) + " VALUES (" + 
					"'" + linhaReceitas.getDescricao() + "', " +
					"'" + linhaReceitas.getFonte() + "', ";
			
			if(linhaReceitas.getDataRecebimento() != null) {
				java.util.Date dt = linhaReceitas.getDataRecebimento();
				java.sql.Date d = new java.sql.Date (dt.getTime());
				sqlInstruction = sqlInstruction + "'" + d + "'";
			} else {
				sqlInstruction = sqlInstruction + "null";
			}
			
			sqlInstruction = sqlInstruction + ", " +
					linhaReceitas.getValor() + ", " +
					"(SELECT id_mes_ano FROM mes_ano WHERE mes_ano='" + mesAno + "'));";
			
			scriptSQL.add(sqlInstruction);
		}
		
		scriptSQL.add("\n\n/*DESPESAS FIXAS**********************************************************************************/");
		for(DespesasFixas linhaDespesasFixas : tabelaDespesasFixas) {
			
			sqlInstruction = "INSERT INTO " + estdb.getNomeTabela(2) + " " + estdb.getAtributosTabela(2) + " VALUES ('" +
					linhaDespesasFixas.getDescricao() + "', " +
					"(SELECT id FROM categorias WHERE categoria='" + linhaDespesasFixas.getCategoria() + "'), " +
					linhaDespesasFixas.getValor() + ", '" +
					linhaDespesasFixas.getStatus() + "', ";
			
			if(linhaDespesasFixas.getData() != null) {
				java.util.Date dt = linhaDespesasFixas.getData();
				java.sql.Date d = new java.sql.Date (dt.getTime());
				sqlInstruction = sqlInstruction + "'" + d + "'";
			} else {
				sqlInstruction = sqlInstruction + "";
			}
			
			sqlInstruction = sqlInstruction + ", '" + 
					linhaDespesasFixas.getObservacao() + "', " +
					"(SELECT id_mes_ano FROM mes_ano WHERE mes_ano='" + mesAno + "'));";
			
			scriptSQL.add(sqlInstruction);

		}
		
		scriptSQL.add("\n\n/*DESPESAS VARIAVEIS******************************************************************************/");
		for(DespesasVariaveis linhaDespesasVariaveis : tabelaDespesasVariaveis) {
			
			sqlInstruction = "INSERT INTO " + estdb.getNomeTabela(3) + " " + estdb.getAtributosTabela(3) + " VALUES ('" +
								linhaDespesasVariaveis.getDescricao() + "', " +
								"(SELECT id FROM categorias WHERE categoria='" + linhaDespesasVariaveis.getCategoria() + "'), ";
			
			if(linhaDespesasVariaveis.getValorPlanejado() != 0)
				sqlInstruction = sqlInstruction + linhaDespesasVariaveis.getValorPlanejado() + ", ";
			else
				sqlInstruction = sqlInstruction + "null, ";
			
			if(linhaDespesasVariaveis.getValorPago() != 0)
				sqlInstruction = sqlInstruction + linhaDespesasVariaveis.getValorPago() + ", '";
			else
				sqlInstruction = sqlInstruction + "null, '";
			
			sqlInstruction = sqlInstruction + linhaDespesasVariaveis.getStatus() + "', ";
			
			if(linhaDespesasVariaveis.getData() != null) {
				java.util.Date dt = linhaDespesasVariaveis.getData();
				java.sql.Date d = new java.sql.Date (dt.getTime());
				sqlInstruction = sqlInstruction + "'" + d + "'";
			} else {
				sqlInstruction = sqlInstruction + "null";
			}
			
			sqlInstruction = sqlInstruction + ", '" +
					linhaDespesasVariaveis.getObservacao() + "', " +
					"(SELECT id_mes_ano FROM mes_ano WHERE mes_ano='" + mesAno + "'));";
			
			scriptSQL.add(sqlInstruction);
		}
		
		scriptSQL.add("\n\n/*TOTAIS******************************************************************************************/");
		for(Totais linhaTotais : tabelaTotais) {
			
			scriptSQL.add("INSERT INTO " + estdb.getNomeTabela(4) + " " + estdb.getAtributosTabela(4) + " VALUES (" +
						linhaTotais.getValorDisponivel() + ", " +
						linhaTotais.getDespesas() + ", " +
						linhaTotais.getReceitas() + ", " +
						linhaTotais.getaPagar() + ", " +
						"(SELECT id_mes_ano FROM mes_ano WHERE mes_ano='" + mesAno + "'));");
		}
		
		scriptSQL.add("\n\n/*GASTOS POR CATEGORIA****************************************************************************/");
		for(GastosCategoria linhaGastosCategoria : tabelaGastosCategoria) {
			
			scriptSQL.add("INSERT INTO " + estdb.getNomeTabela(5) + " " + estdb.getAtributosTabela(5) + " VALUES (" +
					"(SELECT id FROM categorias WHERE categoria='" + linhaGastosCategoria.getCategoria() + "'), " +
					linhaGastosCategoria.getTotalGasto() + ", " +
					"(SELECT id_mes_ano FROM mes_ano WHERE mes_ano='" + mesAno + "'));");
		}
		
		scriptSQL.add("\n\nCOMMIT;");
		
		scriptSQL.add("\n\n/*DESFAZER SCRIPT DE INSERÇÃO*********************************************************************/");
		scriptSQL.add("START TRANSACTION;");
		scriptSQL.add("DELETE FROM gastos_gerais WHERE id_mes_ano = " + 
						"(SELECT id_mes_ano FROM mes_ano WHERE mes_ano='" + mesAno + "');");
		scriptSQL.add("DELETE FROM receitas WHERE id_mes_ano = " +
						"(SELECT id_mes_ano FROM mes_ano WHERE mes_ano='" + mesAno + "');");
		scriptSQL.add("DELETE FROM despesas_fixas WHERE id_mes_ano = " +
						"(SELECT id_mes_ano FROM mes_ano WHERE mes_ano='" + mesAno + "');");
		scriptSQL.add("DELETE FROM despesas_variaveis WHERE id_mes_ano = " +
						"(SELECT id_mes_ano FROM mes_ano WHERE mes_ano='" + mesAno + "');");
		scriptSQL.add("DELETE FROM totais WHERE id_mes_ano = " +
						"(SELECT id_mes_ano FROM mes_ano WHERE mes_ano='" + mesAno + "');");
		scriptSQL.add("DELETE FROM gastos_categoria WHERE id_mes_ano = " +
						"(SELECT id_mes_ano FROM mes_ano WHERE mes_ano='" + mesAno + "');");
		scriptSQL.add("DELETE FROM mes_ano WHERE mes_ano = '" + mesAno + "';");
		
		scriptSQL.add("COMMIT;");
		
		return scriptSQL;
	}
}
