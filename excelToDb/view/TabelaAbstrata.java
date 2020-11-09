package fabio.excelToDb.view;

import java.util.List;

import javax.swing.table.AbstractTableModel;

public class TabelaAbstrata<E> extends AbstractTableModel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected List<E> linhas;
	protected String[] colunas;
	
	public TabelaAbstrata(List<E> linhas) {
		this.linhas = linhas;
	}
	
	//@Override
	public int getRowCount() {
		return linhas.size();
	}
	
	public E getValueAtRow(int linha) {
		return linhas.get(linha);
	}
	
	public void setValueAtRow(int linha, E object) {
		linhas.set(linha, object);
	}
	
	//@Override
	public int getColumnCount() {
		return colunas.length;
	}
	
	public String getColumnName(int coluna) {
		if(coluna < getColumnCount())
			return colunas[coluna];
		return super.getColumnName(coluna);
	}

	public Object getValueAt(int rowIndex, int columnIndex) {
		// TODO Auto-generated method stub
		return null;
	}
}
