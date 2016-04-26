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
import stati.StatoMenu;

public class FinestraHaiVinto implements KeyListener,ActionListener {
	
	private JFrame f;
	private JLabel label = new JLabel("Hai vinto!");
	private JButton ok = new JButton("Ok");
	private JPanel p = new JPanel();
	private JPanel c = new JPanel();
	private Handler h;
	
	public FinestraHaiVinto(Handler h){
		
		switch(h.getLingua().getLingua()){
		case "ENGLISH":{
			label.setText("You win!");
			break;
		}
		case "DEUTSCH":{
			label.setText("Gewonnen!");
			break;
		}
		}
		
		f = new JFrame();
		f.setSize(300, 100);
		f.setResizable(false);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setLocationRelativeTo(h.getGioco().getFrame());
		
		p.add(label);
		c.add(ok);
		
		f.add(p,BorderLayout.NORTH);
		f.add(c,BorderLayout.SOUTH);
		
		//ascoltatori su ok
		ok.addKeyListener(this);
		ok.addActionListener(this);
		f.setVisible(true);
		
		this.h=h;
	}

	@Override
	public void actionPerformed(ActionEvent evt) {
		if(evt.getSource() == ok){
			f.setVisible(false);
			h.getGioco().setStato(new StatoMenu(h));
		}
	}

	@Override
	public void keyPressed(KeyEvent k) {
		if(k.getKeyCode() == KeyEvent.VK_ENTER && ok.isFocusable()){
			h.getGioco().setStato(new StatoMenu(h));
		}
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		h.getGestioneInput().keyReleased(h.getGestioneInput().getKeyEvent());
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
	}
	
	

}
