package finestra;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import classifica.Classifica;
import gioco.Handler;
import stati.StatoMenu;

public class FinestraAzzeraClassifica implements KeyListener,ActionListener {
	
	private JFrame f;
	private JLabel q = new JLabel("Sicuro di voler azzerare la classifica?");
	private JButton si = new JButton("Si");
	private JButton no = new JButton("No");
	private JPanel p = new JPanel();
	private JPanel c = new JPanel();
	
	private Handler h;
	private Classifica classifica = new Classifica();
	
	public FinestraAzzeraClassifica(Handler h){
		try {
			classifica.carica("res/classifiche/classificaPunteggio.txt");
		} catch (IOException e) {
			e.printStackTrace();
		}
		switch(h.getLingua().getLingua()){
		case "ENGLISH":{
			q.setText("Are you sure to clear highscore?");
			si.setText("Yes");
			break;
		}
		case "DEUTSCH":{
			q.setText("Serve Daniele");
			si.setText("Ja");
			no.setText("Nein");
			break;
		}
		}
		//creazione finestra
		f = new JFrame();
		f.setSize(300, 100);
		f.setResizable(false);
		f.setLocationRelativeTo(h.getGioco().getFrame());
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		p.add(q);
		c.add(si); c.add(no);
		
		f.add(p,BorderLayout.NORTH);
		f.add(c,BorderLayout.SOUTH);
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
			}
			else{
				si.requestFocus();
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
		private JFrame f1;
		private JLabel label;
		private JButton confirm = new JButton("Ok");
		private Handler h;
		private JPanel p1 = new JPanel();
		private JPanel c1 = new JPanel();
		
		public FinestraMessaggioOk(Handler h){
			switch(h.getLingua().getLingua()){
			case "ENGLISH":{
				label = new JLabel("Highscore cleaned!");
				break;
			}
			case "ITALIANO":{
				label = new JLabel("Classifica azzerata!");
				break;
			}
			case "DEUTSCH":{
				label = new JLabel("Daniele!");
				break;
			}
			}//switch
			
			//finestra
			f1 = new JFrame();
			f1.setSize(300, 100);
			f1.setResizable(false);
			f1.setLocationRelativeTo(h.getGioco().getFrame());
			f1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			
			p1.add(label);
			c1.add(confirm);
			
			f1.add(p1,BorderLayout.NORTH);
			f1.add(c1,BorderLayout.SOUTH);
			
			f1.setVisible(true);
			
			//ascoltatori
			confirm.addActionListener(this);
			confirm.addKeyListener(this);
			
			this.h=h;
		}
		
		@Override
		public void actionPerformed(ActionEvent evt) {
			if(evt.getSource()==confirm){
				h.getGioco().setStato(new StatoMenu(h));
				f1.setVisible(false);
			}
		}
		
		@Override
		public void keyPressed(KeyEvent k){
			if(k.getKeyCode()== KeyEvent.VK_ENTER){
				h.getGioco().setStato(new StatoMenu(h));
				f1.setVisible(false);
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
