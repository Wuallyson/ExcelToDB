package fabio.excelToDb.model;

import java.util.Date;

/*Essa classe representa uma linha da tabela receitas*/
public class Receitas {

	private String descricao;
	private String fonte;
	private Date dataRecebimento;
	private double valor;
	
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public String getFonte() {
		return fonte;
	}
	public void setFonte(String fonte) {
		this.fonte = fonte;
	}
	public Date getDataRecebimento() {
		return dataRecebimento;
	}
	public void setDataRecebimento(Date dataRecebimento) {
		this.dataRecebimento = dataRecebimento;
	}
	public double getValor() {
		return valor;
	}
	public void setValor(double valor) {
		this.valor = valor;
	}
	
}

