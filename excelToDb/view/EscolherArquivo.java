package fabio.excelToDb.view;

import java.awt.Color;
import java.awt.Container;
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
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

@SuppressWarnings("serial")
public class EscolherArquivo extends JFrame{
	private Container cp;
	private JLabel lbTitulo;
	private JLabel lbInstrucao;
	private JTextField tfCaminho;
	private JButton btProcurar;
	private JButton btProximo;
	
	private boolean tok = false;
	
	public EscolherArquivo() {
		lbTitulo = new JLabel("Escolher Arquivo");
		lbInstrucao = new JLabel("Digite o caminho até o arquivo (.xlsx) ou clique em procurar");
		tfCaminho = new JTextField();
		btProcurar = new JButton("Procurar");
		btProximo = new JButton("Visualizar tabelas");
		setTitle("Excel To Database");
		setSize(500,230);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		lbTitulo.setFont(new Font("Segoe UI", Font.BOLD, 19));
		btProcurar.setToolTipText("Procurar e selecionar arquivo .xlsx");
		
		cp = getContentPane();
		cp.setLayout(null);
		cp.setBackground(new Color(180, 205, 205));
		
		lbTitulo.setBounds(170, 10, 300, 25);
		lbInstrucao.setBounds(30, 40, 400, 25);
		tfCaminho.setBounds(20, 70, 350, 25);
		btProcurar.setBounds(380, 70, 90, 25);
		btProximo.setBounds(180, 125, 140, 25);
		
		cp.add(lbTitulo);
		cp.add(lbInstrucao);
		cp.add(tfCaminho);
		cp.add(btProcurar);
		cp.add(btProximo);
		
		btProcurar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) { btProcurarArquivo(); }
		});
		
		btProximo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) { try {
				btProximaJanela();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} }
		});
	}//fim do construtor
	
	private void btProcurarArquivo() {
		JFileChooser arq = new JFileChooser("C:\\Users\\Fabio Mendes\\eclipse-workspace\\excelToDb\\src\\main");
		FileNameExtensionFilter filtroXLSX =  new FileNameExtensionFilter("Arquivos XLSX", "xlsx");
		arq.addChoosableFileFilter(filtroXLSX);
		arq.setAcceptAllFileFilterUsed(false);
		if( arq.showOpenDialog(this) == JFileChooser.APPROVE_OPTION ) {
			tfCaminho.setText(arq.getSelectedFile().getAbsolutePath());
		}
	}
	
	private void btProximaJanela() throws IOException, ParseException {
		VisualizarTabelas tabs = new VisualizarTabelas( tfCaminho.getText() );
		tabs.setVisible(true);
		this.dispose();
	}
	
	public boolean textoOk () {
		return tok;
	}
	
	public String getPath() {
		return tfCaminho.getText();
	}
}
