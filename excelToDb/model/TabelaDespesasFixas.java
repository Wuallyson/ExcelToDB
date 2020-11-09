package fabio.excelToDb.model;

import java.util.List;

import fabio.excelToDb.view.TabelaAbstrata;

public class TabelaDespesasFixas extends TabelaAbstrata<DespesasFixas>{

	public TabelaDespesasFixas(List<DespesasFixas> linhas) {
		super(linhas);
		// TODO Auto-generated constructor stub
		
		colunas = new String[] {
				"Descrição",
				"Categoria",
				"Valor",
				"Status",
				"Data",
				"Observação"};
	}
	
	@Override
	public Object getValueAt(int linha, int col) {
		DespesasFixas df = linhas.get(linha);
		switch(col) {
		case 0:
			return df.getDescricao();
		case 1:
			return df.getCategoria();
		case 2:
			return df.getValor();
		case 3:
			return df.getStatus();
		case 4:
			return df.getData();
		case 5:
			return df.getObservacao();
		default:
			return null;
		}
	}

}
