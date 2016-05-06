
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

public class FinestraUscita implements KeyListener, ActionListener{
	private JFrame f;
	
	private JLabel q;
	private JLabel sfondo;
	private JButton si = new JButton();
	private JButton no = new JButton();
	
	private Handler h;
	
	public FinestraUscita(Handler h){
		
		si.setBorderPainted(false);
		si.setFocusPainted(false);
		si.setContentAreaFilled(false);
		si.setIcon(new ImageIcon(Risorse.voce_si));
		
		no.setBorderPainted(false);
		no.setFocusPainted(false);
		no.setContentAreaFilled(false);
		no.setIcon(new ImageIcon(Risorse.voce_no_off));
		
		//Question
		q = new JLabel(new ImageIcon(Risorse.voce_uscita));
		
		//Impostazione della larghezza in base alla lunghezza della voce
		int larghezza = Risorse.voce_uscita.getWidth()+16;
		
		//creazione della finestra
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
		
		//aggiunta dei pannelli e dei pulsanti.
		f.add(q, BorderLayout.NORTH);
		f.add(si, BorderLayout.SOUTH);
		f.add(no, BorderLayout.SOUTH);
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
