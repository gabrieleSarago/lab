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
import stati.StatoGioco;
import stati.StatoMenu;

public class FinestraSconfitta implements ActionListener,KeyListener {
	
	private JFrame f;
	private JLabel label =new JLabel("Hai Perso! Vuoi iniziare una nuova partita?");
	private JButton si= new JButton("Si");
	private JButton no = new JButton("No");
	private JPanel p = new JPanel();
	private JPanel c = new JPanel();
	private Handler h;
	
	
	public FinestraSconfitta(Handler h){
		
		switch(h.getLingua().getLingua()){
		case "ENGLISH":{
			label.setText("You Lose! Do you want to start a new game?");
			si.setText("Yes");
			no.setText("No");
			break;
		}
		case "DEUTSCH":{
			si.setText("Ja");
			no.setText("Nein");
			break;
		}
		}
		f = new JFrame();
		f.setSize(300, 100);
		f.setResizable(false);
		f.setLocationRelativeTo(h.getGioco().getFrame());
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		p.add(label); c.add(si);
		c.add(no);
		
		f.add(p,BorderLayout.NORTH);
		f.add(c,BorderLayout.SOUTH);
		
		f.setVisible(true);
		si.addActionListener(this);
		si.addKeyListener(this);
		no.addActionListener(this);
		no.addKeyListener(this);
		this.h=h;
	}

	@Override
	public void keyPressed(KeyEvent k) {
		if(k.getKeyCode() == KeyEvent.VK_ENTER && si.isFocusOwner()){
			f.setVisible(false);
			h.getGioco().setStato(new StatoGioco(h));
		}
		if(k.getKeyCode() == KeyEvent.VK_ENTER && no.isFocusOwner()){
			f.setVisible(false);
			h.getGioco().setStato(new StatoMenu(h));
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
	public void keyTyped(KeyEvent arg0) {	}

	@Override
	public void actionPerformed(ActionEvent evt) {
		if(evt.getSource()==si){
			f.setVisible(false);
			h.getGioco().setStato(new StatoGioco(h));
		}
		if(evt.getSource()==no){
			f.setVisible(false);
			h.getGioco().setStato(new StatoMenu(h));
		}
	}
	
	

}
