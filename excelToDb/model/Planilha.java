package fabio.excelToDb.model;

import java.util.*;
import java.util.List;

/*Esta classe representa todas a planilha, todas as tabelas são atributos dela*/
public class Planilha {
	
	//Nessa lista ficarão salvas todas as linhas da tabela Gastos Gerais
	List<GastosGerais> tabelaGastosGerais = new ArrayList<GastosGerais>();
	//Nessa lista ficarão salvas todas as linhas da tabela Receitas
	List<Receitas> tabelaReceitas = new ArrayList<Receitas>();
	//Nessa lista ficarão salvas todas as linhas da tabela Despesas Fixas
	List<DespesasFixas> tabelaDespesasFixas = new ArrayList<DespesasFixas>();
	//Nessa lista ficarão salvas todas as linhas da tabela Despesas Variaveis
	List<DespesasVariaveis> tabelaDespesasVariaveis = new ArrayList<DespesasVariaveis>();
	//Nessa lista ficarão salvas todas as linhas da tabela Totais
	List<Totais> tabelaTotais = new ArrayList<Totais>();
	//Nessa lista ficarão salvas todas as linhas da tabela gastos por categoria
	List<GastosCategoria> tabelaGastosCategoria = new ArrayList<GastosCategoria>();
	
	//Chamei de linha porque tecnicamente essa é uma linha de tabela que é adicionada aqui
	//Todos os métodos são sobrescritos, mudando apenas o tipo de dado
	public void adicionaLinha(GastosGerais linha) {
		tabelaGastosGerais.add(linha);
	}
	
	public void adicionaLinha(Receitas linha) {
		tabelaReceitas.add(linha);
	}
	
	public void adicionaLinha(DespesasFixas linha) {
		tabelaDespesasFixas.add(linha);
	}
	
	public void adicionaLinha(DespesasVariaveis linha) {
		tabelaDespesasVariaveis.add(linha);
	}
	
	public void adicionaLinha(Totais linha) {
		tabelaTotais.add(linha);
	}
	
	public void adicionaLinha(GastosCategoria linha) {
		tabelaGastosCategoria.add(linha);
	}
	
	public List<GastosGerais> retornaGastosGerais(){
		return tabelaGastosGerais;
	}
	
	public List<Receitas> retornaReceitas(){
		return tabelaReceitas;
	}
	
	public List<DespesasFixas> retornaDespesasFixas(){
		return tabelaDespesasFixas;
	}
	
	public List<DespesasVariaveis> retornaDespesasVariaveis(){
		return tabelaDespesasVariaveis;
	}
	
	public List<Totais> retornaTotais(){
		return tabelaTotais;
	}
	
	public List<GastosCategoria> retornaGastosCategoria(){
		return tabelaGastosCategoria;
	}
	
	public Integer getIdCategoria(String categoria, List<String[]> lista) {
		String aux[];
		int id=0;
		for(String[] reg : lista) {
			aux = reg;
			if(aux[0].equals(categoria))
				id = Integer.parseInt(aux[1]);
		}
		return id;
	}
	
	public List<String> sincronizaCategorias(List<String> catBD) {
		List<String> novasCategorias = new ArrayList<String>();
		if( tabelaGastosCategoria.size() != catBD.size() ) {
			List<String> catPlan = new ArrayList<String>();
			for(GastosCategoria reg : tabelaGastosCategoria) {
				catPlan.add( reg.getCategoria() );
			}
			for(String cat : catPlan) {
				if(!catBD.contains(cat)) {
					novasCategorias.add(cat);
				}
			}
		}
		return novasCategorias;
	}

	@Override
	public String toString() {
		return "Planilha [tabelaGastosGerais=" + tabelaGastosGerais + ", tabelaReceitas=" + tabelaReceitas
				+ ", tabelaDespesasFixas=" + tabelaDespesasFixas + ", tabelaDespesasVariaveis="
				+ tabelaDespesasVariaveis + ", tabelaTotais=" + tabelaTotais + ", tabelaGastosCategoria="
				+ tabelaGastosCategoria + ", idCategorias=" + "]";
	}
}

