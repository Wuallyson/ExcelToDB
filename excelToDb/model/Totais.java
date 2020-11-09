package fabio.excelToDb.model;

/*Essa classe representa uma linha da tabela Totais*/
public class Totais {

	private double valorDisponivel;
	private double despesas;
	private double receitas;
	private double aPagar;
	
	public double getValorDisponivel() {
		return valorDisponivel;
	}
	public void setValorDisponivel(double valorDisponivel) {
		this.valorDisponivel = valorDisponivel;
	}
	public double getDespesas() {
		return despesas;
	}
	public void setDespesas(double despesas) {
		this.despesas = despesas;
	}
	public double getReceitas() {
		return receitas;
	}
	public void setReceitas(double receitas) {
		this.receitas = receitas;
	}
	public double getaPagar() {
		return aPagar;
	}
	public void setaPagar(double aPagar) {
		this.aPagar = aPagar;
	}
	
}
