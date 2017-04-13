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

import gioco.Handler;
import grafica.Risorse;

public class FinestraNessunSalvataggio implements KeyListener, ActionListener{
	
	private JFrame f;
	
	private JLabel q;
	private JLabel sfondo;
	private JButton ok = new JButton();
	
	private Handler h;
	
	/**
	 * Costruttore oggetto FinestraNessunSalvataggio.
	 * @param h oggetto Handler utile per la gestione con altre classi.
	 */
	
	public FinestraNessunSalvataggio(Handler h){
		ok.setBorderPainted(false);
		ok.setFocusPainted(false);
		ok.setContentAreaFilled(false);
		ok.setIcon(new ImageIcon(Risorse.voce_ok));
		
		//Question
		q = new JLabel(new ImageIcon(Risorse.voce_no_carica));
		
		//Impostazione della larghezza in base alla lunghezza della voce
		int larghezza = Risorse.voce_no_carica.getWidth()+16;
		
		//creazione della finestra
		f = new JFrame();
		f.setSize(larghezza, 65);
		f.setResizable(false);
		f.setLocationRelativeTo(h.getGioco().getFrame());
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//Nasconde la finestra dalla taskbar del sistema
		f.setType(javax.swing.JFrame.Type.UTILITY);
		
		//Elimina la barra del titolo
	    f.setUndecorated(true);
	    //Per impostare la forma
	    f.setShape(new RoundRectangle2D.Double.Double(0, 0, larghezza, 65, 15, 80));
		//Resizing dinamico dello sfondo
		Image img = Risorse.sfondo_popup.getScaledInstance(larghezza, 65, Image.SCALE_SMOOTH);
		
		//Lo sfondo � inteso come un JLabel su cui viene stampata l'immagine.
		sfondo = new JLabel(new ImageIcon(img));
		f.setContentPane(sfondo);
		//Si crea un nuovo livello su cui inserire gli altri oggetti grafici.
		f.setLayout(new FlowLayout());
		
		//aggiunta dei pannelli e dei pulsanti.
		f.add(q, BorderLayout.NORTH);
		f.add(ok, BorderLayout.SOUTH);
		f.setVisible(true);
		
		//aggiunta degli ascoltatori
		ok.addKeyListener(this);

		ok.addActionListener(this);
		
		this.h = h;
	
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == ok){
			if(!h.getSuono().getMuto()){
				h.getSuono().getClipStatoMenu().start();
			}
			f.dispose();
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent k) {
		if(k.getKeyCode() == KeyEvent.VK_ENTER){
			if(!h.getSuono().getMuto()){
				h.getSuono().getClipStatoMenu().start();
			}
			f.dispose();
		}
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		h.getGestioneInput().keyReleased(h.getGestioneInput().getKeyEvent());
		
	}

}
