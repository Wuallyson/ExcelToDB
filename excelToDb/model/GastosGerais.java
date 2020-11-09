package fabio.excelToDb.model;

import java.text.ParseException;
import java.util.Date;

/*Essa classe representa uma linha da tabela gastos gerais*/
public class GastosGerais {

	private Date data;
	private String descricao;
	private String categoria;
	private Double valor;

	public Date getData() {
		return data;
	}
	public void setData(Date data) throws ParseException {
		this.data = data;
	}
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
	public Double getValor() {
		return valor;
	}
	public void setValor(Double valor) {
		this.valor = valor;
	}
	
}
