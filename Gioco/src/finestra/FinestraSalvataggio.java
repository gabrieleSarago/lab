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
import javax.swing.JTextField;

import classifica.Classifica;
import classifica.Nominativo;
import gioco.Handler;
import stati.StatoMenu;

public class FinestraSalvataggio implements KeyListener,ActionListener {
	
	private JFrame f;
	private JLabel q = new JLabel("You win! Do you want to save your score?");
	
	private JButton yes = new JButton("Yes");
	private JButton no = new JButton("No");
	
	private JPanel p = new JPanel();
	private JPanel c = new JPanel();
	
	private Handler h;
	private int tempo;
	
	public FinestraSalvataggio(Handler h,int tempo){
		
		switch(h.getLingua().getLingua()){
		case "ENGLISH": {break;}
		case "ITALIANO":{
			q.setText("Hai vinto! Vuoi salvare il tuo punteggio?");
			yes.setText("Si");
			no.setText("No");
			break;
		}
		case "DEUTSCH":{
			q.setText("Gewonnen! Willst du dein Ergebnis speichern?");
			yes.setText("Ja");
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
		c.add(yes); c.add(no);
		
		f.add(p,BorderLayout.NORTH);
		f.add(c,BorderLayout.SOUTH);
		f.setVisible(true);
		yes.addKeyListener(this);
		no.addKeyListener(this);
		
		yes.addActionListener(this);
		no.addActionListener(this);
		
		this.h=h;
		this.tempo=tempo;
	}

	@Override
	public void keyPressed(KeyEvent k) {
		if(k.getKeyCode() == KeyEvent.VK_ENTER && yes.isFocusOwner()){
			f.setVisible(false);
			new FinestraInserimento(h);
		}
		if(k.getKeyCode() == KeyEvent.VK_ENTER && no.isFocusOwner()){
			f.setVisible(false);
			new FinestraConfermaUscita(h);
		}
		if(k.getKeyCode() == KeyEvent.VK_LEFT || k.getKeyCode() == KeyEvent.VK_RIGHT){
			if(yes.isFocusOwner()){
				no.requestFocus();
			}
			else{
				yes.requestFocus();
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
		if(evt.getSource()==yes){
			f.setVisible(false);
			new FinestraInserimento(h);
		}
		if(evt.getSource()==no){
			f.setVisible(false);
			new FinestraConfermaUscita(h);
		}
	}
	
	private class FinestraInserimento implements KeyListener,ActionListener{
		
		
		private JFrame w;
		private Classifica c = new Classifica();
		
		private JButton ok = new JButton("Ok");
		private JButton cancel = new JButton("Cancel");
		private JTextField name = new JTextField("",15);
		private JPanel x = new JPanel();
		private JPanel y = new JPanel();
		private String player;
		private Handler h;
		
		public FinestraInserimento(Handler h){
			
			try {
				c.carica("res/classifiche/classificaPunteggio.txt");
			} catch (IOException e) {
				e.printStackTrace();
			}
			switch(h.getLingua().getLingua()){
			case "ITALIANO":{
				x.add(new JLabel("Nome",JLabel.RIGHT));
				x.add(name);
				cancel.setText("Annulla");
				break;
			}
			case "DEUTSCH":{
				x.add(new JLabel("Name",JLabel.RIGHT));
				x.add(name);
				cancel.setText("Zurück");
				break;
			}
			case "ENGLISH":{
				x.add(new JLabel("Name",JLabel.RIGHT));
				x.add(name);
				break;
			}
			}
			
			y.add(ok); y.add(cancel);
			
			w= new JFrame();
			w.setSize(300, 100);
			w.setResizable(false);
			w.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			w.setLocationRelativeTo(h.getGioco().getFrame());
			w.add(x,BorderLayout.NORTH);
			w.add(y,BorderLayout.SOUTH);
			
			w.setVisible(true);
			
			
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
				w.setVisible(false);
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
				w.setVisible(false);
				new FinestraConfermaUscita(h);
			}
		}

		@Override
		public void keyPressed(KeyEvent k) {
			if(k.getKeyCode() == KeyEvent.VK_DOWN && name.isFocusOwner()){
				ok.requestFocus();
			}
			if(k.getKeyCode() == KeyEvent.VK_UP && (ok.isFocusOwner() || cancel.isFocusOwner())){
				name.requestFocus();
			}
			if(k.getKeyCode() == KeyEvent.VK_ENTER && ok.isFocusOwner()){
				player = name.getText();
				w.setVisible(false);
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
				w.setVisible(false);
				new FinestraConfermaUscita(h);
			}
			if((k.getKeyCode() == KeyEvent.VK_LEFT || k.getKeyCode() == KeyEvent.VK_RIGHT) && ok.isFocusOwner()){
					cancel.requestFocus();
			}
			if((k.getKeyCode() == KeyEvent.VK_LEFT || k.getKeyCode() == KeyEvent.VK_RIGHT) && cancel.isFocusOwner()){
					ok.requestFocus();
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
		private JFrame f1;
		private JLabel label;
		private JButton confirm = new JButton("Ok");
		private Handler h;
		private JPanel p1 = new JPanel();
		private JPanel c1 = new JPanel();
		
		public FinestraMessaggioOk(Handler h){
			switch(h.getLingua().getLingua()){
			case "ENGLISH":{
				label = new JLabel("Save completed!");
				break;
			}
			case "ITALIANO":{
				label = new JLabel("Salvataggio completato!");
				break;
			}
			case "DEUTSCH":{
				label = new JLabel("Speichern erfolgreich!");
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
	
	private  class FinestraConfermaUscita implements KeyListener,ActionListener{
		private JFrame f;
		private JLabel label;
		private JButton confirm = new JButton("Ok");
		private JButton  cancel = new JButton("Cancel");
		private Handler h;
		private JPanel p = new JPanel();
		private JPanel c = new JPanel();

		
		public FinestraConfermaUscita(Handler h){
			switch(h.getLingua().getLingua()){
			case "ITALIANO":{
				label = new JLabel("Sei sicuro di uscire senza salvare?");
				cancel.setText("Annulla");
				break;
			}
			case "ENGLISH":{
				label = new JLabel("Are you sure to exit and do not to save?");
				break;
			}
			case "DEUTSCH":{
				label = new JLabel("Willst du wirklich zurück ohne zu speichern?");
				cancel.setText("Zurück");
				break;
			}
			}//switch
			
			//finestra
			f = new JFrame();
			f.setSize(300, 100);
			f.setResizable(false);
			f.setLocationRelativeTo(h.getGioco().getFrame());
			f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			
			p.add(label);
			c.add(confirm); c.add(cancel); 
			
			f.add(p,BorderLayout.NORTH);
			f.add(c,BorderLayout.SOUTH);
			
			f.setVisible(true);
			
			//ascoltatori
			confirm.addActionListener(this);
			cancel.addActionListener(this);
			
			confirm.addKeyListener(this);
			cancel.addKeyListener(this);
			
			this.h=h;
		}
		
		
		
		
		@Override
		public void actionPerformed(ActionEvent evt) {
			if(evt.getSource()==confirm){
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
			if(k.getKeyCode() == KeyEvent.VK_ENTER && confirm.isFocusOwner()){
				f.setVisible(false);
				h.getGioco().setStato(new StatoMenu(h));
			}
			if(k.getKeyCode() == KeyEvent.VK_ENTER && cancel.isFocusOwner()){
				f.setVisible(false);
				new FinestraInserimento(h);
			}
			if(k.getKeyCode() == KeyEvent.VK_LEFT || k.getKeyCode() == KeyEvent.VK_RIGHT){
				if(confirm.isFocusOwner()){
					cancel.requestFocus();
				}
				else{
					confirm.requestFocus();
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
		
		private JLabel label = new JLabel();
		private JFrame f;
		private JPanel p = new JPanel();
		private JPanel c = new JPanel();
		private JButton ok = new JButton("Ok");
		private Handler h;
		
		public FinestraReinserisci(Handler h){
			switch(h.getLingua().getLingua()){
			case "ENGLISH":{
				label.setText("There isn't any name. Repete.");
				break;
			}
			case "ITALIANO":{
				label.setText("Nessun nominativo. Reinserire.");
				break;
			}
			case "DEUTSCH":{
				label.setText("Kein Name! Setze ihn wieder ein.");
				break;
			}
			}//switch
			
			f = new JFrame();
			f.setSize(300, 100);
			f.setResizable(false);
			f.setLocationRelativeTo(h.getGioco().getFrame());
			f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			
			p.add(label);
			c.add(ok);
			
			f.add(p,BorderLayout.NORTH);
			f.add(c,BorderLayout.SOUTH);
			
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
