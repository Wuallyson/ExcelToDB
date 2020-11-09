package fabio.excelToDb.model;

import java.util.List;

import fabio.excelToDb.view.TabelaAbstrata;

public class TabelaDespesasVariaveis extends TabelaAbstrata<DespesasVariaveis>{

	public TabelaDespesasVariaveis(List<DespesasVariaveis> linhas) {
		super(linhas);
		// TODO Auto-generated constructor stub
		
		colunas = new String[] {
				"Descrição",
				"Categoria",
				"Valor Planejado",
				"Valor Pago",
				"Status",
				"Data",
				"Observações"};
	}
	
	@Override
	public Object getValueAt(int linha, int col) {
		DespesasVariaveis dv = linhas.get(linha);
		switch(col) {
		case 0:
			return dv.getDescricao();
		case 1:
			return dv.getCategoria();
		case 2:
			return dv.getValorPlanejado();
		case 3:
			return dv.getValorPago();
		case 4:
			return dv.getStatus();
		case 5:
			return dv.getData();
		case 6:
			return dv.getObservacao();
		default:
			return null;
		}
	}

}
