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

public class FinestraNessunPunteggio implements KeyListener,ActionListener {
	private JFrame f1;
	private JLabel label;
	private JButton confirm = new JButton("Ok");
	private Handler h;
	private JPanel p1 = new JPanel();
	private JPanel c1 = new JPanel();
	
	public FinestraNessunPunteggio(Handler h){
		switch(h.getLingua().getLingua()){
		case "ENGLISH":{
			label = new JLabel("There aren't any scores!");
			break;
		}
		case "ITALIANO":{
			label = new JLabel("Nessun punteggio!");
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
			f1.setVisible(false);
		}
	}
	
	@Override
	public void keyPressed(KeyEvent k){
		if(k.getKeyCode()== KeyEvent.VK_ENTER){
			f1.setVisible(false);
		}
		
	}
	@Override
	public void keyReleased(KeyEvent k) {
		h.getGestioneInput().keyReleased(h.getGestioneInput().getKeyEvent());
	}
	@Override
	public void keyTyped(KeyEvent arg0) {}

}
