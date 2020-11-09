package fabio.excelToDb.model;

import java.util.List;

import fabio.excelToDb.view.TabelaAbstrata;

public class TabelaReceitas extends TabelaAbstrata<Receitas>{

	public TabelaReceitas(List<Receitas> linhas) {
		super(linhas);
		// TODO Auto-generated constructor stub
		
		colunas = new String[] {
				"Descrição",
				"Fonte",
				"Data de recebimento",
				"Valor"};
	}
	
	@Override
	public Object getValueAt(int linha, int col) {
		Receitas rc = linhas.get(linha);
		switch(col) {
		case 0:
			return rc.getDescricao();
		case 1:
			return rc.getFonte();
		case 2:
			return rc.getDataRecebimento();
		case 3:
			return rc.getValor();
		default:
			return null;
		}
	}

}
