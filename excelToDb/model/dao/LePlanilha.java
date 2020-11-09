package fabio.excelToDb.model.dao;

import fabio.excelToDb.model.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


/*Esta classe é resposável por ler as informações da planilha e armazenar o que foi lido em objetos*/
public class LePlanilha {
	
	//Caminho até a planilha
	private static String fileName;
	
	/*Ao percorrer a planilha pode acontecer de células sem conteúdo serem lidas e armazenadas como null, para ler 
	 * apenas células com conteúdo em uma tabela as variáveis abaixo verificam se a primeira coluna de uma tabela 
	 * possui conteúdo, caso não haja a variável é setada como false e as colunas posteriores são ignoradas*/
	private boolean flag1, flag2, flag3, flag4, flag5, flag6;
	
	public LePlanilha (String filePath) {
		LePlanilha.fileName = filePath;
	}
	
	//método lê um arquivo .xlsx e retorna um objeto Planilha com todo conteúdo lido
	public Planilha retornaPlanilha() throws IOException, ParseException {
		
		Planilha plan = new Planilha();
		
		try {
			//Localiza e adiciona o arquivo
			FileInputStream arquivo = new FileInputStream(new File(LePlanilha.fileName));
			
			//Verfica se o arquivo é de fato um excel
			XSSFWorkbook workbook = new XSSFWorkbook(arquivo);
			
			//Dentro do arquivo diz que a planilha que vamos usar é a primeira
			XSSFSheet mesAtual = workbook.getSheetAt(0);
			
			Iterator<Row> iteradorLinhas = mesAtual.iterator();
			
			//A primeira linha é de cabeçalhos, pulamos ela
			iteradorLinhas.next();
			
			//percorre cada linha, enquanto há uma próxima
			while(iteradorLinhas.hasNext()) {
				GastosGerais linhaGastosGerais = new GastosGerais();
				Receitas linhaReceitas = new Receitas();
				DespesasFixas linhaDespesasFixas = new DespesasFixas();
				DespesasVariaveis linhaDespesasVariaveis = new DespesasVariaveis();
				Totais linhaTotais = new Totais();
				GastosCategoria linhaGastosCategoria = new GastosCategoria();
				//Vai para a próxima linha
				Row linha = iteradorLinhas.next();

				//Cria uma lita de células para a linha
				Iterator<Cell> iteradorCelulas = linha.cellIterator();
				
				//Enquanto houver uma próxima célula na linha lê seus conteúdos
				while(iteradorCelulas.hasNext()) {
					Cell celula = iteradorCelulas.next();
					
					//Para as colunas onde há linhas das tabelas
					switch (celula.getColumnIndex()) {
					//Cada case está relacionado a uma coluna da planilha
					case 1:// (B) Gastos Gerais: data
						if(celula.getDateCellValue() != null) {
							linhaGastosGerais.setData( celula.getDateCellValue() );
							flag1 = true;
						}
						else {
							flag1 = false;
						}
						break;
					case 2:// (C) Gastos Gerais: Descrição
						if(flag1)
							linhaGastosGerais.setDescricao( celula.getStringCellValue() );
						break;
					case 3:// (D) Gastos Gerais: Categoria
						if(flag1)
							linhaGastosGerais.setCategoria( celula.getStringCellValue() );
						break;
					case 4:// (E) Gastos Gerais: Valor
						if(flag1) {
							linhaGastosGerais.setValor( celula.getNumericCellValue() );
							plan.adicionaLinha( linhaGastosGerais );}
						break;
					case 7:// (H) Receitas: Descrição
						if(celula.getStringCellValue() != "") {
							linhaReceitas.setDescricao( celula.getStringCellValue() );
							flag2 = true;
						}
						else {
							flag2 = false;
						}
						break;
					case 8:// (I) Receitas: Fonte
						if(flag2)
							linhaReceitas.setFonte( celula.getStringCellValue() );
						break;
					case 9:// (J) Receitas: Data de recebimento
						if(flag2) {
							try {
								linhaReceitas.setDataRecebimento( celula.getDateCellValue() );
							} catch(NullPointerException e) {}
						}
						break;
					case 10:// (K) Receitas: Valor
						if(flag2) {
							linhaReceitas.setValor( celula.getNumericCellValue() );
							plan.adicionaLinha( linhaReceitas );
						}
						break;
					case 13:// (N) Despesas fixas: Descrição
						if(celula.getStringCellValue() != "") {
							linhaDespesasFixas.setDescricao( celula.getStringCellValue() );
							flag3 = true;
						}
						else {
							flag3 = false;
						}
						break;
					case 14:// (O) Despesas fixas: Categoria
						if(flag3)
							linhaDespesasFixas.setCategoria( celula.getStringCellValue() );
						break;
					case 15:// (P) Despesas fixas: Valor
						if(flag3)
							linhaDespesasFixas.setValor( celula.getNumericCellValue() );
						break;
					case 16:// (Q) Despesas fixas: Status
						if(flag3)
							linhaDespesasFixas.setStatus( celula.getStringCellValue() );
						break;
					case 17:// (R) Despesas fixas: Data
						if(flag3) {
							try {
								linhaDespesasFixas.setData( celula.getDateCellValue() );
							} catch(NullPointerException e) {}
						}
						break;
					case 18:// (S) Despesas fixas: Observação
						if(flag3) {
							linhaDespesasFixas.setObservacao( celula.getStringCellValue() );
							plan.adicionaLinha( linhaDespesasFixas );
						}
							//System.out.println(celula.getStringCellValue());	
						break;
					case 21:// (V) Despesas variáveis: Descrição
						if(celula.getStringCellValue() != "") {
							linhaDespesasVariaveis.setDescricao( celula.getStringCellValue() );
							flag4 = true;
						}
						else {
							flag4 = false;
						}
						break;
					case 22:// (W) Despesas variáveis: Categoria
						if(flag4)
							linhaDespesasVariaveis.setCategoria( celula.getStringCellValue() );
						break;
					case 23:// (X) Despesas variáveis: Valor Planejado
						if(flag4)
							linhaDespesasVariaveis.setValorPlanejado( celula.getNumericCellValue() );
						break;
					case 24:// (Y) Despesas variáveis: Valor Pago
						if(flag4)
							linhaDespesasVariaveis.setValorPago( celula.getNumericCellValue() );
						break;
					case 25:// (Z) Despesas variáveis: Status
						if(flag4)
							linhaDespesasVariaveis.setStatus( celula.getStringCellValue() );
						break;
					case 26:// (AA) Despesas variáveis: Data
						if(flag4) {
							try {
								linhaDespesasVariaveis.setData( celula.getDateCellValue() );
							} catch ( NullPointerException e ) {}
						}
						break;
					case 27:// (AB) Despesas variáveis: Observações
						if(flag4) {
							linhaDespesasVariaveis.setObservacao( celula.getStringCellValue() );
							plan.adicionaLinha( linhaDespesasVariaveis );
						}
						break;
					case 30:// (AE) Totais: Valor Disponível
						if(celula.getNumericCellValue() != 0) {
							linhaTotais.setValorDisponivel( celula.getNumericCellValue() );
							flag5 = true;
						}
						else {
							flag5 = false;
						}
						break;
					case 31:// (AF) Totais: Despesas
						if(flag5)
							linhaTotais.setDespesas( celula.getNumericCellValue() );
						break;
					case 32:// (AG) Totais: Receitas
						if(flag5)
							linhaTotais.setReceitas( celula.getNumericCellValue() );
						break;
					case 33:// (AH) Totais: A pagar
						if(flag5) {
							linhaTotais.setaPagar( celula.getNumericCellValue() );
							plan.adicionaLinha( linhaTotais );
						}
						break;
					case 35:// (AJ) Gastos por categoria: Categoria
						if(celula.getStringCellValue() != "") {
							linhaGastosCategoria.setCategoria( celula.getStringCellValue() );
							flag6 = true;
						}
						else {
							flag6 = false;
						}
						break;
					case 36:// (AK) Gastos por categoria: Total Gasto
						if(flag6) {
							linhaGastosCategoria.setTotalGasto( celula.getNumericCellValue() );
							plan.adicionaLinha( linhaGastosCategoria );
						}
						break;
					}//switch
				}//while das celulas
			}//while das linhas
			
			arquivo.close();
			workbook.close();
		} catch (FileNotFoundException e) {
			System.out.println("Arquivo Excel não encontrado!");
			e.printStackTrace();
		}
		return plan;
	}//método retornaPlanilha

}//classe
