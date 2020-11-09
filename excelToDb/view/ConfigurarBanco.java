package fabio.excelToDb.view;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import fabio.excelToDb.model.Planilha;
import fabio.excelToDb.model.dao.GerarScript;
import fabio.excelToDb.model.dao.PlanilhaGateway;

public class ConfigurarBanco extends JFrame{
	
	private JLabel lbTitulo, lbMesAno, lbBanco, lbUsuario, lbSenha;
	private static final String valRB[] = {"Gerar script SQL e inserir dados no banco",
										   "Apenas gerar script SQL"};
	private JRadioButton rbOperacao[];
	private ButtonGroup bgOperacao;
	private JTextField tfMesAno, tfBanco, tfUsuario;
	private JPasswordField jpSenha;
	private JButton btFinalizar;
	private Container cp;
	
	private Planilha plan;
	
	public ConfigurarBanco(Planilha plan) {
		this.plan = plan;
		
		lbTitulo = new JLabel("Configurações do Banco de dados");
		lbMesAno = new JLabel("Inserir mês de referência (Ex.: Setembro-2020):");
		lbBanco = new JLabel("Nome do banco de dados:");
		lbUsuario = new JLabel("Usuário:");
		lbSenha = new JLabel("Senha:");
		
		tfMesAno = new JTextField();
		tfBanco = new JTextField();
		tfUsuario = new JTextField();
		jpSenha = new JPasswordField();
		
		rbOperacao = new JRadioButton[2];
		bgOperacao = new ButtonGroup();
		
		btFinalizar = new JButton("Finalizar");
		
		setTitle("Excel To Database");
		setSize(500, 380);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		lbTitulo.setFont(new Font("Segoe UI", Font.BOLD, 19));
		
		for(int aux=0; aux < rbOperacao.length; aux++) {
			rbOperacao[aux] = new JRadioButton(valRB[aux]);
			rbOperacao[aux].setBackground(new Color(180, 205, 205));
			bgOperacao.add(rbOperacao[aux]);
		}
		rbOperacao[1].setSelected(true);
		btFinalizar.setToolTipText("Realizar a operação configurada");
		cp = getContentPane();
		cp.setLayout(null);
		cp.setBackground( new Color(180, 205, 205) );
		
		lbTitulo.setBounds(90, 10, 380, 25);
		lbMesAno.setBounds(10, 40, 300, 25);
		tfMesAno.setBounds(295, 40, 175, 25);
		rbOperacao[0].setBounds(90, 85, 300, 25);
		rbOperacao[1].setBounds(90, 110, 300, 25);
		lbBanco.setBounds(10, 155, 150, 25);
		tfBanco.setBounds(170, 155, 300, 25);
		lbUsuario.setBounds(10, 185, 60, 25);
		tfUsuario.setBounds(70, 185, 400, 25);
		lbSenha.setBounds(10, 215, 60, 25);
		jpSenha.setBounds(60, 215, 410, 25);
		btFinalizar.setBounds(180, 270, 120, 30);
		
		cp.add(lbTitulo);
		cp.add(lbMesAno);
		cp.add(tfMesAno);
		cp.add(rbOperacao[0]);
		cp.add(rbOperacao[1]);
		cp.add(lbBanco);
		cp.add(tfBanco);
		cp.add(lbUsuario);
		cp.add(tfUsuario);
		cp.add(lbSenha);
		cp.add(jpSenha);
		cp.add(btFinalizar);
		
		lbBanco.setEnabled(false);
		tfBanco.setEnabled(false);
		lbUsuario.setEnabled(false);
		tfUsuario.setEnabled(false);
		lbSenha.setEnabled(false);
		jpSenha.setEnabled(false);
		
		btFinalizar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) { try {
				processar();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} }
		});
		
		rbOperacao[0].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) { rbScriptEBanco();}
		});
		
		rbOperacao[1].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) { rbScript();}
		});
		
	}//fim do construtor
	
	public void processar() throws SQLException {
		boolean gerarApenasScript=true;
		
		//Condições de validação do campo mês de referência
		if(tfMesAno.getText().isEmpty() || 
				!tfMesAno.getText().contains("-") || 
				!(tfMesAno.getText().contains("Janeiro") ||
				tfMesAno.getText().contains("Fevereiro") ||
				tfMesAno.getText().contains("Março") ||
				tfMesAno.getText().contains("Abril") ||
				tfMesAno.getText().contains("Maio") ||
				tfMesAno.getText().contains("Junho") ||
				tfMesAno.getText().contains("Julho") ||
				tfMesAno.getText().contains("Agosto") ||
				tfMesAno.getText().contains("Setembro") ||
				tfMesAno.getText().contains("Outubro") ||
				tfMesAno.getText().contains("Novembro") ||
				tfMesAno.getText().contains("Dezembro"))
				) {
			JOptionPane.showMessageDialog(this, "Verifique os dados inseridos no campo mês de referência");
			return;
		}
		
		if(tfBanco.getText().isEmpty()) {
			JOptionPane.showMessageDialog(this, "Verifique os dados inseridos no campo nome do banco");
			return;
		}
		
		if(tfUsuario.getText().isEmpty()) {
			JOptionPane.showMessageDialog(this, "Verifique os dados inseridos no campo nome usuario");
			return;
		}
		
		if(jpSenha.getText().isEmpty()) {
			JOptionPane.showMessageDialog(this, "Verifique os dados inseridos no campo senha");
			return;
		}
		
		for (JRadioButton rb : rbOperacao) {
			if(rb.isSelected()) {
				if(rb.getText().equals(valRB[1])) {
					gerarApenasScript = true;
				}
				else {
					gerarApenasScript = false;
				}
			}
		}
		
		GerarScript script = new GerarScript("src/main/scriptSQL.txt");
		
		if(gerarApenasScript) {
			script.exportarScript(script.criarScript(plan, tfMesAno.getText()));
			JOptionPane.showMessageDialog(this, "Script gerado com sucesso!\nSalvo em src/main/scriptSQL.txt");
		} else {
			PlanilhaGateway pg = new PlanilhaGateway(tfMesAno.getText(), plan, tfBanco.getText(), tfUsuario.getText(), new String(jpSenha.getPassword()));
			pg.insertOnDb();
			script.exportarScript(pg.getScript());
			JOptionPane.showMessageDialog(this, "Dados inseridos com sucesso no banco de dados!\nScript salvo em src/main/scriptSQL.txt");
		}
	
	}
	
	public void rbScriptEBanco() {
		lbBanco.setEnabled(true);
		tfBanco.setEnabled(true);
		lbUsuario.setEnabled(true);
		tfUsuario.setEnabled(true);
		lbSenha.setEnabled(true);
		jpSenha.setEnabled(true);
	}
	
	public void rbScript() {
		lbBanco.setEnabled(false);
		tfBanco.setEnabled(false);
		lbUsuario.setEnabled(false);
		tfUsuario.setEnabled(false);
		lbSenha.setEnabled(false);
		jpSenha.setEnabled(false);
	}
	
}//fim da classe
