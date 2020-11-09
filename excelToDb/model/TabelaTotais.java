package fabio.excelToDb.model;

import java.util.List;

import fabio.excelToDb.view.TabelaAbstrata;

public class TabelaTotais extends TabelaAbstrata<Totais>{

	public TabelaTotais(List<Totais> linhas) {
		super(linhas);
		// TODO Auto-generated constructor stub
		
		colunas = new String[] {
				"Valor Disponível",
				"Despesas",
				"Receitas",
				"A pagar"};
	}
	
	@Override
	public Object getValueAt(int linha, int col) {
		Totais tt = linhas.get(linha);
		switch(col) {
		case 0:
			return tt.getValorDisponivel();
		case 1:
			return tt.getDespesas();
		case 2:
			return tt.getReceitas();
		case 3:
			return tt.getaPagar();
		default:
			return null;
		}
	}

}
