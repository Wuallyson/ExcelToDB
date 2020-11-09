package fabio.excelToDb.model;

import java.util.List;

import fabio.excelToDb.view.TabelaAbstrata;

public class TabelaGastosGerais extends TabelaAbstrata<GastosGerais>{
	
	public TabelaGastosGerais(List<GastosGerais> linhas) {
		super(linhas);
		
		colunas = new String[] {
				"Data",
				"Descrição",
				"Categoria",
				"Valor"};
	}//Fim do construtor
	
	@Override
	public Object getValueAt(int linha, int col) {
		GastosGerais gg = linhas.get(linha);
		switch(col) {
		case 0:
			return gg.getData();
		case 1:
			return gg.getDescricao();
		case 2:
			return gg.getCategoria();
		case 3:
			return gg.getValor();
		default:
			return null;
		}
	}
}
