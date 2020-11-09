package fabio.excelToDb.model;

public class EstruturaDB {
	/*Nessas constantes estão os nomes e atributos das tabelas assim como estão no banco,
	 * em caso de mudança na estrutura do banco as alterações devem ser feitas aqui.*/
	
	public static final String[] tabelas = {"gastos_gerais",
											"receitas",
											"despesas_fixas",
											"despesas_variaveis",
											"totais",
											"gastos_categoria"};
	
	public static final String[] atributos = {"(data_registro, descricao, categoria_id, valor, id_mes_ano)",
											  "(descricao, fonte, data_recebimento, valor, id_mes_ano)",
											  "(descricao, categoria_id, valor, status_registro, data_registro, observacao, id_mes_ano)",
											  "(descricao, categoria_id, valor_planejado, valor_pago, status_registro, data_registro, observacao, id_mes_ano)",
											  "(valor_disponivel, despesas, receitas, a_pagar, id_mes_ano)",
											  "(categoria_id, total_gasto, id_mes_ano)"};
	
	public String getNomeTabela(Integer index) {
		return tabelas[index];
	}
	
	public String getAtributosTabela(Integer index) {
		return atributos[index];
	}
	
}
