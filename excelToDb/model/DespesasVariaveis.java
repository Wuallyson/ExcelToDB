package fabio.excelToDb.model;

import java.util.Date;

/*Essa classe representa uma linha da tabela despesas variaveis*/
public class DespesasVariaveis {

	private String descricao;
	private String categoria;
	private double valorPlanejado;
	private double valorPago;
	private String status;
	private Date data;
	private String observacao;
	
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public String getCategoria() {
		return categoria;
	}
	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}
	public Double getValorPlanejado() {
		return valorPlanejado;
	}
	public void setValorPlanejado(double valorPlanejado) {
		this.valorPlanejado = valorPlanejado;
	}
	public Double getValorPago() {
		return valorPago;
	}
	public void setValorPago(double valorPago) {
		this.valorPago = valorPago;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Date getData() {
		return data;
	}
	public void setData(Date data) {
		this.data = data;
	}
	public String getObservacao() {
		return observacao;
	}
	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}
	
}
