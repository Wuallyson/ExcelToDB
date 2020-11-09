package fabio.excelToDb.view;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.text.ParseException;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.filechooser.FileNameExtensionFilter;

import fabio.excelToDb.model.Planilha;
import fabio.excelToDb.model.TabelaDespesasFixas;
import fabio.excelToDb.model.TabelaDespesasVariaveis;
import fabio.excelToDb.model.TabelaGastosCategoria;
import fabio.excelToDb.model.TabelaGastosGerais;
import fabio.excelToDb.model.TabelaReceitas;
import fabio.excelToDb.model.TabelaTotais;
import fabio.excelToDb.model.dao.LePlanilha;

public class VisualizarTabelas extends JFrame{
	
	private String caminho;
	private JLabel lbTitulo;
	private JLabel lbGastosGerais;
	private JLabel lbReceitas;
	private JLabel lbDespesasFixas;
	private JLabel lbDespesasVariaveis;
	private JLabel lbTotais;
	private JLabel lbGastosCategoria;
	private Container cp;
	private JButton btOpcoes;
	private Planilha financas;
	
	
	public VisualizarTabelas(String caminho) throws IOException, ParseException {
		
		lbTitulo = new JLabel("Visualizar Tabelas");
		lbGastosGerais = new JLabel("Gastos Gerais");
		lbReceitas = new JLabel("Receitas");
		lbDespesasFixas = new JLabel("Despesas Fixas");
		lbDespesasVariaveis = new JLabel("Despesas Variáveis");
		lbTotais = new JLabel("Totais");
		lbGastosCategoria = new JLabel("Gastos por Categoria");
		btOpcoes = new JButton("Configurações do Banco de dados");
		
		btOpcoes.setToolTipText("Inserir em um banco de dados e/ou gerar script");
		
		setTitle("Excel To Database");
		setSize(950,730);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		lbTitulo.setFont(new Font("Segoe UI", Font.BOLD, 19));
		lbGastosGerais.setFont(new Font("Segoe UI", Font.BOLD, 12));
		lbReceitas.setFont(new Font("Segoe UI", Font.BOLD, 12));
		lbDespesasFixas.setFont(new Font("Segoe UI", Font.BOLD, 12));
		lbDespesasVariaveis.setFont(new Font("Segoe UI", Font.BOLD, 12));
		lbTotais.setFont(new Font("Segoe UI", Font.BOLD, 12));
		lbGastosCategoria.setFont(new Font("Segoe UI", Font.BOLD, 12));
		
		cp = getContentPane();
		cp.setLayout(null);
		cp.setBackground(new Color(180, 205, 205));
		
		this.caminho = caminho;
		
		LePlanilha rdx = new LePlanilha(caminho);
		financas = new Planilha();
		financas = rdx.retornaPlanilha();
		
		JPanel painelGastosGerais = new JPanel();
		TabelaGastosGerais tgg = new TabelaGastosGerais( financas.retornaGastosGerais() );
		JTable jtGastosGerais = new JTable(tgg);
		jtGastosGerais.setPreferredScrollableViewportSize(new Dimension(700,65));
		painelGastosGerais.add(new JScrollPane(jtGastosGerais));
		
		JPanel painelReceitas = new JPanel();
		TabelaReceitas tr = new TabelaReceitas( financas.retornaReceitas() );
		JTable jtReceitas = new JTable( tr );
		jtReceitas.setPreferredScrollableViewportSize(new Dimension(601,65));
		painelReceitas.add( new JScrollPane(jtReceitas) );
		
		JPanel painelDespesasFixas = new JPanel();
		TabelaDespesasFixas tdf = new TabelaDespesasFixas( financas.retornaDespesasFixas() );
		JTable jtDespesasFixas = new JTable(tdf);
		jtDespesasFixas.setPreferredScrollableViewportSize(new Dimension(810,65));
		painelDespesasFixas.add(new JScrollPane(jtDespesasFixas));
		
		JPanel painelDespesasVariaveis = new JPanel();
		TabelaDespesasVariaveis tdv = new TabelaDespesasVariaveis( financas.retornaDespesasVariaveis() );
		JTable jtDespesasVariaveis = new JTable(tdv);
		jtDespesasVariaveis.setPreferredScrollableViewportSize( new Dimension(900, 65) );
		painelDespesasVariaveis.add( new JScrollPane(jtDespesasVariaveis) );
		
		JPanel painelTotais = new JPanel();
		TabelaTotais tt = new TabelaTotais( financas.retornaTotais() );
		JTable jtTotais = new JTable(tt);
		jtTotais.setPreferredScrollableViewportSize( new Dimension(500, 30) );
		painelTotais.add( new JScrollPane(jtTotais) );
		
		JPanel painelGastosCategoria = new JPanel();
		TabelaGastosCategoria tgc = new TabelaGastosCategoria( financas.retornaGastosCategoria() );
		JTable jtGastosCategoria = new JTable( tgc );
		jtGastosCategoria.setPreferredScrollableViewportSize( new Dimension(180, 65) );
		painelGastosCategoria.add( new JScrollPane( jtGastosCategoria ) );
		
		painelReceitas.setBackground( new Color(180, 205, 205) );
		painelGastosGerais.setBackground( new Color(180, 205, 205) );
		painelDespesasFixas.setBackground( new Color(180, 205, 205) );
		painelDespesasVariaveis.setBackground( new Color(180, 205, 205) );
		painelGastosCategoria.setBackground( new Color(180, 205, 205) );
		painelTotais.setBackground( new Color(180, 205, 205) );
		
		lbTitulo.setBounds(355, 10, 300, 25);
		lbGastosGerais.setBounds(10, 40, 300, 25);
		lbReceitas.setBounds(10, 160, 300, 25);
		lbDespesasFixas.setBounds(10, 280, 300, 25);
		lbDespesasVariaveis.setBounds(10, 400, 300, 25);
		lbTotais.setBounds(10, 520, 300, 25);
		lbGastosCategoria.setBounds(565, 520, 300, 25);
		
		painelGastosGerais.setBounds(10, 59, 718, 100);
		painelReceitas.setBounds(10, 180, 619, 100);
		painelDespesasFixas.setBounds(10, 300, 828, 100);
		painelDespesasVariaveis.setBounds(10, 420, 918, 100);
		painelTotais.setBounds(10, 540, 503, 42);
		painelGastosCategoria.setBounds(565, 540, 198, 100);
		
		btOpcoes.setBounds(340, 640, 230, 25);
		
		cp.add( painelGastosGerais );
		cp.add( painelReceitas );
		cp.add( painelDespesasFixas );
		cp.add( painelDespesasVariaveis );
		cp.add( painelTotais );
		cp.add( painelGastosCategoria );
		
		cp.add(lbTitulo);
		cp.add(lbGastosGerais);
		cp.add(lbReceitas);
		cp.add(lbDespesasFixas);
		cp.add(lbDespesasVariaveis);
		cp.add(lbTotais);
		cp.add(lbGastosCategoria);
		
		cp.add(btOpcoes);
		
		btOpcoes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) { btOpcoes(); }
		});
	}//fim do construtor
	
	private void btOpcoes() {
		ConfigurarBanco config = new ConfigurarBanco( financas );
		config.setVisible(true);
		this.dispose();
	}
}
