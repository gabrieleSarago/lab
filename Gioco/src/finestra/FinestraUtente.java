package finestra;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.RoundRectangle2D;
import java.io.File;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import gioco.Handler;
import grafica.Risorse;
import stati.StatoMenu;
import suoni.Suono;

@SuppressWarnings("serial")
public class FinestraUtente extends JFrame implements KeyListener, ActionListener{
	private Handler h;
	private Suono suono;
	
	private JButton ok = new JButton();
	private JTextField name = new JTextField("",15);
	private JLabel nome;
	
	public FinestraUtente(Handler h, Suono suono) {
		
		this.h= h;
		this.suono= suono;
		

		name.setBackground(new Color(128, 206, 251));
		name.setFont(new Font("Arial", Font.BOLD, 14));
		name.setForeground(Color.black);
		
		ok.setBorderPainted(false);
		ok.setFocusPainted(false);
		ok.setContentAreaFilled(false);
		ok.setIcon(new ImageIcon(Risorse.voce_ok_off));
		
		//Question
		nome = new JLabel(new ImageIcon(Risorse.voce_nome));
		
		setSize(300, 65);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(h.getGioco().getFrame());
		
		//Nasconde la finestra dalla taskbar del sistema
		setType(javax.swing.JFrame.Type.UTILITY);
		
		//Elimina la barra del titolo
	    setUndecorated(true);
	    //Per impostare la forma
	    setShape(new RoundRectangle2D.Double.Double(0, 0, 300, 65, 15, 80));
		//Resizing dinamico dello sfondo
		Image img = Risorse.sfondo_popup.getScaledInstance(300, 65, Image.SCALE_SMOOTH);
		
		//Lo sfondo � inteso come un JLabel su cui viene stampata l'immagine.
		JLabel sfondo = new JLabel(new ImageIcon(img));
		setContentPane(sfondo);
		//Si crea un nuovo livello su cui inserire gli altri oggetti grafici.
		setLayout(new FlowLayout());
		
		//aggiunta dei pannelli e dei pulsanti.
		add(nome, BorderLayout.NORTH);
		add(name, BorderLayout.NORTH);
		add(ok, BorderLayout.SOUTH);		
		
		//ascoltatori
		ok.addKeyListener(this);
		ok.addActionListener(this);
		name.addKeyListener(this);
		
		this.h=h;

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Risorse.utenteCorrente = name.getText();
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
		h.getGioco().setStato(new StatoMenu(h, suono));
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(name.getText().length()>20){
			name.setText(name.getText().substring(0, 20));
		}
		
		if(e.getKeyCode() == KeyEvent.VK_UP && (ok.isFocusOwner())){
			name.requestFocus();
			ok.setIcon(new ImageIcon(Risorse.voce_ok_off));
		}
		
		if(e.getKeyCode() == KeyEvent.VK_DOWN && name.isFocusOwner()){
			ok.requestFocus();
			ok.setIcon(new ImageIcon(Risorse.voce_ok));
		}
		if(e.getKeyCode() == KeyEvent.VK_ENTER && ok.isFocusOwner()){
			Risorse.utenteCorrente = name.getText();
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
			h.getGioco().setStato(new StatoMenu(h, suono));
		}
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	
}
