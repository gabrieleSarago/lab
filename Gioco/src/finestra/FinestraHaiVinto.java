package finestra;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.RoundRectangle2D;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import gfx.Risorse;
import gioco.Handler;
import stati.StatoMenu;

public class FinestraHaiVinto implements KeyListener,ActionListener {
	
	private JFrame f;
	private JLabel q;
	private JLabel sfondo;
	private JButton ok = new JButton();
	
	private Handler h;
	
	public FinestraHaiVinto(Handler h){
		
		ok.setBorderPainted(false);
		ok.setFocusPainted(false);
		ok.setContentAreaFilled(false);
		ok.setIcon(new ImageIcon(Risorse.voce_ok));
		
		//Impostazione della larghezza in base alla lunghezza della voce
		int larghezza = Risorse.voce_vittoria.getWidth()+16;
		
		f = new JFrame();
		f.setSize(larghezza, 65);
		f.setResizable(false);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setLocationRelativeTo(h.getGioco().getFrame());
		
		//Question
		q = new JLabel(new ImageIcon(Risorse.voce_vittoria));
		
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
		
		ok.addKeyListener(this);
		ok.addActionListener(this);
		
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
		if(k.getKeyCode() == KeyEvent.VK_ENTER){
			f.setVisible(false);
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
