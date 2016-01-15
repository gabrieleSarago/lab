package finestra;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import gioco.Handler;

public class FinestraUscita implements KeyListener, ActionListener{
	private JFrame f;
	
	private JLabel q  = new JLabel("Are you sure to exit?");
	
	private JButton si = new JButton("Si");
	private JButton no = new JButton("No");
	
	private JPanel p = new JPanel();
	private JPanel c = new JPanel();
	
	private Handler h;
	
	public FinestraUscita(Handler h){
		
		switch(h.getLingua().getLingua()){
		case "ENGLISH" : q.setText("Are you sure to exit?"); break;
		case "ITALIANO" : q.setText("Sei sicuro di voler uscire?"); break;
		}
		
		//creazione della finestra
		f = new JFrame();
		f.setSize(200, 100);
		f.setResizable(false);
		f.setLocationRelativeTo(h.getGioco().getFrame());
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//aggiunta dei pannelli e dei pulsanti
		p.add(q);
		c.add(si); c.add(no);
		
		f.add(p, BorderLayout.NORTH);
		f.add(c, BorderLayout.SOUTH);
		
		f.setVisible(true);
		
		//aggiunta degli ascoltatori
		si.addKeyListener(this);
		no.addKeyListener(this);
		
		si.addActionListener(this);
		no.addActionListener(this);
		
		this.h = h;
	}
	
	@Override
	public void keyPressed(KeyEvent k) {
		//Gestione del focus dei pulsanti
		if(k.getKeyCode() == KeyEvent.VK_ENTER && si.isFocusOwner())
			System.exit(0);
		if(k.getKeyCode() == KeyEvent.VK_ENTER && no.isFocusOwner())
			f.dispose();
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
	public void keyReleased(KeyEvent e) {
		//gestione dell'input del gioco nel momento in cui compare la finestra.
		//Con questo comando si evita il bug di aggiornamento continuo dell'input.
		h.getGestioneInput().keyReleased(h.getGestioneInput().getKeyEvent());
	}

	@Override
	public void keyTyped(KeyEvent e) {}

	@Override
	public void actionPerformed(ActionEvent evt) {
		if(evt.getSource() == si)
			System.exit(0);
		if(evt.getSource() == no)
			f.dispose();
	}

}
