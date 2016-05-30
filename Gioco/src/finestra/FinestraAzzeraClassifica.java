package finestra;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.RoundRectangle2D;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import classifica.Classifica;
import gfx.CaricatoreImmagini;
import gfx.Risorse;
import gioco.Handler;
import stati.StatoMenu;

/**
 * Crea l'oggetto FinestraClassifica,utile per chiedere al giocatore di 
 * voler azzerare l'attuale classifica presente in StatoClassifica.
 */
public class FinestraAzzeraClassifica implements KeyListener,ActionListener {
	
	private JFrame f;
	private JLabel q;
	private JLabel sfondo;
	private JButton si = new JButton();
	private JButton no = new JButton();
	
	private Handler h;
	private Classifica classifica = new Classifica();
	
	/**
	 * Costruttore di FinestraClassifica.
	 * @param h oggetto Handler utile per la gestione con altre classi.
	 */
	public FinestraAzzeraClassifica(Handler h){
		try {
			classifica.carica("res/classifiche/classificaPunteggio.txt");
		} catch (IOException e) {
			e.printStackTrace();
		}
		si.setBorderPainted(false);
		si.setFocusPainted(false);
		si.setContentAreaFilled(false);
		si.setIcon(new ImageIcon(Risorse.voce_si));
		
		no.setBorderPainted(false);
		no.setFocusPainted(false);
		no.setContentAreaFilled(false);
		no.setIcon(new ImageIcon(Risorse.voce_no_off));
		
		//Question
		q = new JLabel(new ImageIcon(Risorse.voce_azzera));
		
		//Impostazione della larghezza in base alla lunghezza della voce
		int larghezza = Risorse.voce_azzera.getWidth()+16;
		
		//creazione finestra
		f = new JFrame();
		f.setSize(larghezza, 65);
		f.setResizable(false);
		f.setLocationRelativeTo(h.getGioco().getFrame());
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//Nasconde la finestra dalla taskbar del sistema
		f.setType(javax.swing.JFrame.Type.UTILITY);

		//Elimina la barra del titolo
	    f.setUndecorated(true);
	    //Per impostare la forma
	    f.setShape(new RoundRectangle2D.Double.Double(0, 0, larghezza, 65, 15, 80));
		//Resizing dinamico dello sfondo
		Image img = Risorse.sfondo_popup.getScaledInstance(larghezza, 65, Image.SCALE_SMOOTH);
		
		//Lo sfondo è inteso come un JLabel su cui viene stampata l'immagine.
		sfondo = new JLabel(new ImageIcon(img));
		f.setContentPane(sfondo);
		//Si crea un nuovo livello su cui inserire gli altri oggetti grafici.
		f.setLayout(new FlowLayout());
		
		//aggiunta dei pannelli e dei pulsanti.
		f.add(q, BorderLayout.NORTH);
		f.add(si, BorderLayout.SOUTH);
		f.add(no, BorderLayout.SOUTH);
		f.setVisible(true);
		
		si.addKeyListener(this);
		no.addKeyListener(this);
		
		si.addActionListener(this);
		no.addActionListener(this);
		
		this.h=h;
		
		
	}

	@Override
	public void keyPressed(KeyEvent k) {
		if(k.getKeyCode() == KeyEvent.VK_ENTER && si.isFocusOwner()){
			f.setVisible(false);
			classifica.azzeraClassifica();
			try {
				classifica.salva("res/classifiche/classificaPunteggio.txt");
			} catch (IOException e) {
				e.printStackTrace();
			}
			new FinestraMessaggioOk(h);
		}
		if(k.getKeyCode() == KeyEvent.VK_ENTER && no.isFocusOwner()){
			f.setVisible(false);
		}
		if(k.getKeyCode() == KeyEvent.VK_LEFT || k.getKeyCode() == KeyEvent.VK_RIGHT){
			if(si.isFocusOwner()){
				no.requestFocus();
				si.setIcon(new ImageIcon(Risorse.voce_si_off));
				no.setIcon(new ImageIcon(Risorse.voce_no));
			}
			else{
				si.requestFocus();
				si.setIcon(new ImageIcon(Risorse.voce_si));
				no.setIcon(new ImageIcon(Risorse.voce_no_off));
			}
		
		}
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		h.getGestioneInput().keyReleased(h.getGestioneInput().getKeyEvent());
	}

	@Override
	public void keyTyped(KeyEvent arg0) {}

	@Override
	public void actionPerformed(ActionEvent evt) {
		if(evt.getSource()==si){
			f.setVisible(false);
			classifica.azzeraClassifica();
			try {
				classifica.salva("res/classifiche/classificaPunteggio.txt");
			} catch (IOException e) {
				e.printStackTrace();
			}
			new FinestraMessaggioOk(h);
		}
		if(evt.getSource()==no){
			f.setVisible(false);
		}
	}
	private  class FinestraMessaggioOk implements KeyListener,ActionListener{
		private JFrame f;
		private JLabel q, sfondo;
		private JButton ok = new JButton();
		private Handler h;
		
		
		public FinestraMessaggioOk(Handler h){
			
			ok.setBorderPainted(false);
			ok.setFocusPainted(false);
			ok.setContentAreaFilled(false);
			ok.setIcon(new ImageIcon(Risorse.voce_ok));
			
			//Question
			q = new JLabel(new ImageIcon(Risorse.voce_azzera_classifica));
			
			//Impostazione della larghezza in base alla lunghezza della voce
			int larghezza = Risorse.voce_azzera_classifica.getWidth()+16;
			
			//finestra
			f = new JFrame();
			f.setSize(larghezza, 65);
			f.setResizable(false);
			f.setLocationRelativeTo(h.getGioco().getFrame());
			f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			
			//Nasconde la finestra dalla taskbar del sistema
			f.setType(javax.swing.JFrame.Type.UTILITY);
			
			//Elimina la barra del titolo
		    f.setUndecorated(true);
		    //Per impostare la forma
		    f.setShape(new RoundRectangle2D.Double.Double(0, 0, larghezza, 65, 15, 80));
			//Resizing dinamico dello sfondo
			Image img = Risorse.sfondo_popup.getScaledInstance(larghezza, 65, Image.SCALE_SMOOTH);
			
			//Lo sfondo è inteso come un JLabel su cui viene stampata l'immagine.
			sfondo = new JLabel(new ImageIcon(img));
			f.setContentPane(sfondo);
			//Si crea un nuovo livello su cui inserire gli altri oggetti grafici.
			f.setLayout(new FlowLayout());
			
			//aggiunta dei pannelli e dei pulsanti.
			f.add(q, BorderLayout.NORTH);
			f.add(ok, BorderLayout.SOUTH);
			
			f.setVisible(true);
			
			//ascoltatori
			ok.addActionListener(this);
			ok.addKeyListener(this);
			
			this.h=h;
		}
		
		@Override
		public void actionPerformed(ActionEvent evt) {
			if(evt.getSource()==ok){
				h.getGioco().setStato(new StatoMenu(h));
				f.setVisible(false);
			}
		}
		
		@Override
		public void keyPressed(KeyEvent k){
			if(k.getKeyCode()== KeyEvent.VK_ENTER){
				h.getGioco().setStato(new StatoMenu(h));
				f.setVisible(false);
			}
			
		}
		@Override
		public void keyReleased(KeyEvent k) {
			h.getGestioneInput().keyReleased(h.getGestioneInput().getKeyEvent());
		}
		@Override
		public void keyTyped(KeyEvent arg0) {}
	}//FinestraMessaggioOk
	
	
}
