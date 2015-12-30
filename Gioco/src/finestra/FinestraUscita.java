package finestra;

import java.awt.BorderLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import gioco.Handler;

public class FinestraUscita implements KeyListener{
	private JFrame f;
	
	private JLabel q = new JLabel("Sei sicuro di voler uscire?");
	
	private JButton si = new JButton("Si");
	private JButton no = new JButton("No");
	
	private JPanel p = new JPanel();
	private JPanel c = new JPanel();
	
	private Handler h;
	
	public FinestraUscita(Handler h){
		f = new JFrame();
		f.setSize(200, 100);
		f.setLocation(500, 500);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		p.add(q);
		c.add(si); c.add(no);
		
		f.add(p, BorderLayout.NORTH);
		f.add(c, BorderLayout.SOUTH);
		
		f.setVisible(true);
		
		si.addKeyListener(this);
		no.addKeyListener(this);
		
		this.h = h;
	}
	
	@Override
	public void keyPressed(KeyEvent k) {
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
	public void keyTyped(KeyEvent e) {
		
	}

}
