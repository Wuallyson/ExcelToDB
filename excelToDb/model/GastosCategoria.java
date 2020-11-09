package fabio.excelToDb.model;

//Essa classe é uma abstração de uma linha da tabela Gastos por Categoria, com os respectivos getters e setters
public class GastosCategoria {

	private String categoria;
	private double totalGasto;
	
	public String getCategoria() {
		return categoria;
	}
	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}
	public double getTotalGasto() {
		return totalGasto;
	}
	public void setTotalGasto(double totalGasto) {
		this.totalGasto = totalGasto;
	}
}
