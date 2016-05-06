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
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import classifica.Classifica;
import classifica.Nominativo;
import gfx.Risorse;
import gioco.Handler;
import stati.StatoMenu;

public class FinestraSalvataggio implements KeyListener,ActionListener {
	
	private JFrame f;
	private JLabel q;
	
	private JButton si = new JButton();
	private JButton no = new JButton();
	private JLabel sfondo;

	private Handler h;
	private int tempo;
	
	public FinestraSalvataggio(Handler h,int tempo){
		
		si.setBorderPainted(false);
		si.setFocusPainted(false);
		si.setContentAreaFilled(false);
		si.setIcon(new ImageIcon(Risorse.voce_si));
		
		no.setBorderPainted(false);
		no.setFocusPainted(false);
		no.setContentAreaFilled(false);
		no.setIcon(new ImageIcon(Risorse.voce_no_off));
		
		//Question
		q = new JLabel(new ImageIcon(Risorse.voce_salva));
				
		//Impostazione della larghezza in base alla lunghezza della voce
		int larghezza = Risorse.voce_salva.getWidth()+16;
		
		
		//creazione finestra
		f = new JFrame();
		f.setSize(larghezza, 65);
		f.setResizable(false);
		f.setLocationRelativeTo(h.getGioco().getFrame());
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
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
		
		f.add(q,BorderLayout.NORTH);
		f.add(si,BorderLayout.SOUTH);
		f.add(no,BorderLayout.SOUTH);
		f.setVisible(true);
		
		si.addKeyListener(this);
		no.addKeyListener(this);
		
		si.addActionListener(this);
		no.addActionListener(this);
		
		this.h=h;
		this.tempo=tempo;
	}

	@Override
	public void keyPressed(KeyEvent k) {
		if(k.getKeyCode() == KeyEvent.VK_ENTER && si.isFocusOwner()){
			f.setVisible(false);
			new FinestraInserimento(h);
		}
		if(k.getKeyCode() == KeyEvent.VK_ENTER && no.isFocusOwner()){
			f.setVisible(false);
			new FinestraConfermaUscita(h);
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
		//gestione dell'input del gioco nel momento in cui compare la finestra.
				//Con questo comando si evita il bug di aggiornamento continuo dell'input.
				h.getGestioneInput().keyReleased(h.getGestioneInput().getKeyEvent());
	}

	@Override
	public void keyTyped(KeyEvent arg0) {}

	@Override
	public void actionPerformed(ActionEvent evt) {
		if(evt.getSource()==si){
			f.setVisible(false);
			new FinestraInserimento(h);
		}
		if(evt.getSource()==no){
			f.setVisible(false);
			new FinestraConfermaUscita(h);
		}
	}
	
	private class FinestraInserimento implements KeyListener,ActionListener{
		
		
		private JFrame f;
		private Classifica c = new Classifica();
		
		private JButton ok = new JButton();
		private JButton cancel = new JButton();
		private JTextField name = new JTextField("",15);
		private JLabel nome;
		private String player;
		private Handler h;
		
		public FinestraInserimento(Handler h){
			
			try {
				c.carica("res/classifiche/classificaPunteggio.txt");
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			name.setBackground(new Color(128, 206, 251));
			name.setFont(new Font("Arial", Font.BOLD, 14));
			name.setForeground(Color.black);
			
			ok.setBorderPainted(false);
			ok.setFocusPainted(false);
			ok.setContentAreaFilled(false);
			ok.setIcon(new ImageIcon(Risorse.voce_ok_off));
			
			cancel.setBorderPainted(false);
			cancel.setFocusPainted(false);
			cancel.setContentAreaFilled(false);
			cancel.setIcon(new ImageIcon(Risorse.voce_annulla_off));
			
			//Question
			nome = new JLabel(new ImageIcon(Risorse.voce_nome));
			
			f= new JFrame();
			f.setSize(300, 65);
			f.setResizable(false);
			f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			f.setLocationRelativeTo(h.getGioco().getFrame());
			
			//Elimina la barra del titolo
		    f.setUndecorated(true);
		    //Per impostare la forma
		    f.setShape(new RoundRectangle2D.Double.Double(0, 0, 300, 65, 15, 80));
			//Resizing dinamico dello sfondo
			Image img = Risorse.sfondo_popup.getScaledInstance(300, 65, Image.SCALE_SMOOTH);
			
			//Lo sfondo è inteso come un JLabel su cui viene stampata l'immagine.
			sfondo = new JLabel(new ImageIcon(img));
			f.setContentPane(sfondo);
			//Si crea un nuovo livello su cui inserire gli altri oggetti grafici.
			f.setLayout(new FlowLayout());
			
			//aggiunta dei pannelli e dei pulsanti.
			f.add(nome, BorderLayout.NORTH);
			f.add(name, BorderLayout.NORTH);
			f.add(ok, BorderLayout.SOUTH);
			f.add(cancel, BorderLayout.SOUTH);
			
			f.setVisible(true);
			
			
			//ascoltatori
			ok.addKeyListener(this);
			cancel.addKeyListener(this);
			ok.addActionListener(this);
			cancel.addActionListener(this);
			name.addKeyListener(this);
			
			this.h=h;
		}

		@Override
		public void actionPerformed(ActionEvent evt) {
			player = name.getText();
			player.trim();
			if(evt.getSource()==ok){
				f.setVisible(false);
				if(player==null)
					new FinestraReinserisci(h);
				
				else{
					c.add(new Nominativo(String.valueOf(tempo),player));
					try {
						c.salva("res/classifiche/classificaPunteggio.txt");
					} catch (IOException e) {
						e.printStackTrace();
					}
					new FinestraMessaggioOk(h);
					}
			}
			if(evt.getSource() == cancel){
				f.setVisible(false);
				new FinestraConfermaUscita(h);
			}
		}

		@Override
		public void keyPressed(KeyEvent k) {
			if(k.getKeyCode() == KeyEvent.VK_DOWN && name.isFocusOwner()){
				ok.requestFocus();
				ok.setIcon(new ImageIcon(Risorse.voce_ok));
				cancel.setIcon(new ImageIcon(Risorse.voce_annulla_off));
			}
			if(k.getKeyCode() == KeyEvent.VK_UP && (ok.isFocusOwner() || cancel.isFocusOwner())){
				name.requestFocus();
				ok.setIcon(new ImageIcon(Risorse.voce_ok_off));
				cancel.setIcon(new ImageIcon(Risorse.voce_annulla_off));
			}
			if(k.getKeyCode() == KeyEvent.VK_ENTER && ok.isFocusOwner()){
				player = name.getText();
				f.setVisible(false);
				if(player.length()== 0 || player.contains(" "))
					new FinestraReinserisci(h);
				else{
					c.add(new Nominativo(String.valueOf(tempo),player));
					try {
						c.salva("res/classifiche/classificaPunteggio.txt");
					} catch (IOException e) {
						e.printStackTrace();
					}
					new FinestraMessaggioOk(h);
					}
			}
			if(k.getKeyCode() == KeyEvent.VK_ENTER && cancel.isFocusOwner()){
				f.setVisible(false);
				new FinestraConfermaUscita(h);
			}
			if((k.getKeyCode() == KeyEvent.VK_LEFT || k.getKeyCode() == KeyEvent.VK_RIGHT) && ok.isFocusOwner()){
					cancel.requestFocus();
					ok.setIcon(new ImageIcon(Risorse.voce_ok_off));
					cancel.setIcon(new ImageIcon(Risorse.voce_annulla));
			}
			if((k.getKeyCode() == KeyEvent.VK_LEFT || k.getKeyCode() == KeyEvent.VK_RIGHT) && cancel.isFocusOwner()){
					ok.requestFocus();
					ok.setIcon(new ImageIcon(Risorse.voce_ok));
					cancel.setIcon(new ImageIcon(Risorse.voce_annulla_off));
			}
		}

		@Override
		public void keyReleased(KeyEvent k) {
			h.getGestioneInput().keyReleased(h.getGestioneInput().getKeyEvent());
		}

		@Override
		public void keyTyped(KeyEvent k) {}
	}//FinestraInserimento
	
	private  class FinestraMessaggioOk implements KeyListener,ActionListener{
		private JFrame f;
		private JLabel q;
		private JLabel sfondo;
		private JButton ok = new JButton();
		private Handler h;
		
		public FinestraMessaggioOk(Handler h){
			
			ok.setBorderPainted(false);
			ok.setFocusPainted(false);
			ok.setContentAreaFilled(false);
			ok.setIcon(new ImageIcon(Risorse.voce_ok));
			
			//Impostazione della larghezza in base alla lunghezza della voce
			int larghezza = Risorse.voce_salvataggio.getWidth()+16;
			
			//finestra
			f = new JFrame();
			f.setSize(larghezza, 65);
			f.setResizable(false);
			f.setLocationRelativeTo(h.getGioco().getFrame());
			f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			
			//Question
			q = new JLabel(new ImageIcon(Risorse.voce_salvataggio));
			
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
	
	private  class FinestraConfermaUscita implements KeyListener,ActionListener{
		private JFrame f;
		private JLabel q;
		private JLabel sfondo;
		private JButton ok = new JButton();
		private JButton cancel = new JButton();
		private Handler h;
		
		public FinestraConfermaUscita(Handler h){
			
			ok.setBorderPainted(false);
			ok.setFocusPainted(false);
			ok.setContentAreaFilled(false);
			ok.setIcon(new ImageIcon(Risorse.voce_ok));
			
			cancel.setBorderPainted(false);
			cancel.setFocusPainted(false);
			cancel.setContentAreaFilled(false);
			cancel.setIcon(new ImageIcon(Risorse.voce_annulla_off));
			
			//Impostazione della larghezza in base alla lunghezza della voce
			int larghezza = Risorse.voce_no_salvataggio.getWidth()+16;
			
			//finestra
			f = new JFrame();
			f.setSize(larghezza, 65);
			f.setResizable(false);
			f.setLocationRelativeTo(h.getGioco().getFrame());
			f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			
			//Question
			q = new JLabel(new ImageIcon(Risorse.voce_no_salvataggio));
			
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
			f.add(cancel, BorderLayout.SOUTH);
			
			f.setVisible(true);
			
			//ascoltatori
			ok.addActionListener(this);
			cancel.addActionListener(this);
			
			ok.addKeyListener(this);
			cancel.addKeyListener(this);
			
			this.h=h;
		}
		
		
		
		
		@Override
		public void actionPerformed(ActionEvent evt) {
			if(evt.getSource()==ok){
				f.setVisible(false);
				h.getGioco().setStato(new StatoMenu(h));
			}
			if(evt.getSource()==cancel){
				f.setVisible(false);
				new FinestraInserimento(h);
			}
		}
		@Override
		public void keyPressed(KeyEvent k) {
			if(k.getKeyCode() == KeyEvent.VK_ENTER && ok.isFocusOwner()){
				f.setVisible(false);
				h.getGioco().setStato(new StatoMenu(h));
			}
			if(k.getKeyCode() == KeyEvent.VK_ENTER && cancel.isFocusOwner()){
				f.setVisible(false);
				new FinestraInserimento(h);
			}
			if(k.getKeyCode() == KeyEvent.VK_LEFT || k.getKeyCode() == KeyEvent.VK_RIGHT){
				if(ok.isFocusOwner()){
					cancel.requestFocus();
					ok.setIcon(new ImageIcon(Risorse.voce_ok_off));
					cancel.setIcon(new ImageIcon(Risorse.voce_annulla));
				}
				else{
					ok.requestFocus();
					ok.setIcon(new ImageIcon(Risorse.voce_ok));
					cancel.setIcon(new ImageIcon(Risorse.voce_annulla_off));
				}
			}
			
		}
		@Override
		public void keyReleased(KeyEvent k) {
			h.getGestioneInput().keyReleased(h.getGestioneInput().getKeyEvent());
		}
		@Override
		public void keyTyped(KeyEvent k) {}
	}//FinestraConfermaUscita
	
	private class FinestraReinserisci implements KeyListener,ActionListener{
		
		private JLabel q;
		private JFrame f;
		private JButton ok = new JButton();
		private Handler h;
		
		public FinestraReinserisci(Handler h){
			
			ok.setBorderPainted(false);
			ok.setFocusPainted(false);
			ok.setContentAreaFilled(false);
			ok.setIcon(new ImageIcon(Risorse.voce_ok));
			
			//Impostazione della larghezza in base alla lunghezza della voce
			int larghezza = Risorse.voce_no_nominativo.getWidth()+16;
			
			f = new JFrame();
			f.setSize(larghezza, 65);
			f.setResizable(false);
			f.setLocationRelativeTo(h.getGioco().getFrame());
			f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			
			//Question
			q = new JLabel(new ImageIcon(Risorse.voce_no_nominativo));
			
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
			
			ok.addActionListener(this);
			ok.addKeyListener(this);
			this.h=h;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getSource()==ok){
				f.setVisible(false);
				new FinestraInserimento(h);
			}
		}

		@Override
		public void keyPressed(KeyEvent k) {
			if(k.getKeyCode()== KeyEvent.VK_ENTER && ok.isFocusOwner()){
				f.setVisible(false);
				new FinestraInserimento(h);
			}
		}

		@Override
		public void keyReleased(KeyEvent k) {
			h.getGestioneInput().keyReleased(h.getGestioneInput().getKeyEvent());
		}

		@Override
		public void keyTyped(KeyEvent k) {}
		
	}
	
}
