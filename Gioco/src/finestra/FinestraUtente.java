package finestra;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import gioco.Gioco;
import gioco.Handler;
import grafica.Risorse;
import stati.Stato;
import stati.StatoMenu;
import suoni.Suono;

@SuppressWarnings("serial")
public class FinestraUtente extends JFrame{
	private JPanel contentPane;
	private JTextField textField;

	private Handler h;
	private Suono suono;

	public FinestraUtente(Handler h) {
		//Label and TextField/Password start
		this.h = h;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		textField = new JTextField();
		textField.setBounds(172, 65, 154, 22);
		contentPane.add(textField);
		textField.setColumns(10);


		JLabel lblNomeUtente = new JLabel("Nome Utente:");
		lblNomeUtente.setBounds(12, 65, 127, 22);
		contentPane.add(lblNomeUtente);

		//end

		//Link to others frames start


		JButton btnAvanti = new JButton("Avanti");
		btnAvanti.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				Risorse.utenteCorrente = textField.getText();
				h.getGioco().getFrame().setVisible(true);
				Stato.setStato(new StatoMenu(h, suono));
			}
		});
		btnAvanti.setBounds(282, 186, 97, 25);
		contentPane.add(btnAvanti);		

	}
	
	public FinestraUtente(Handler h, Suono suono) {
		//Label and TextField/Password start
		
		this.h= h;
		this.suono= suono;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		textField = new JTextField();
		textField.setBounds(172, 65, 154, 22);
		contentPane.add(textField);
		textField.setColumns(10);


		JLabel lblNomeUtente = new JLabel("Nome Utente:");
		lblNomeUtente.setBounds(12, 65, 127, 22);
		contentPane.add(lblNomeUtente);

		//end

		//Link to others frames start


		JButton btnAvanti = new JButton("Avanti");
		btnAvanti.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				Risorse.utenteCorrente = textField.getText();
				Risorse.DIR_UTENTE = Risorse.DIR_UTENTI + Risorse.SEPARATORE + Risorse.utenteCorrente;
				Risorse.STATISTICA = Risorse.DIR_UTENTE + Risorse.SEPARATORE + "statistica.txt";
				
				
				File f = new File(Risorse.DIR_UTENTE);
				if (! f.exists())f.mkdir();
					
					
				f = new File(Risorse.STATISTICA);
				if (! f.exists())
					try {
						f.createNewFile();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				h.caricaStatistiche();
				h.getGioco().getFrame().setVisible(true);
				dispose();
				h.setStato(new StatoMenu(h, suono));
			}
		});
		btnAvanti.setBounds(282, 186, 97, 25);
		contentPane.add(btnAvanti);		

	}
	
}
