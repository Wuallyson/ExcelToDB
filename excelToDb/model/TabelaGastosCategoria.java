package fabio.excelToDb.model;

import java.util.List;

import fabio.excelToDb.view.TabelaAbstrata;

public class TabelaGastosCategoria extends TabelaAbstrata<GastosCategoria>{

	public TabelaGastosCategoria(List<GastosCategoria> linhas) {
		super(linhas);
		// TODO Auto-generated constructor stub
		
		colunas = new String[] {
				"Categoria",
				"Total Gasto"};
	}
	
	@Override
	public Object getValueAt(int linha, int col) {
		GastosCategoria gg = linhas.get(linha);
		switch(col) {
		case 0:
			return gg.getCategoria();
		case 1:
			return gg.getTotalGasto();
		default:
			return null;
		}
	}

}
