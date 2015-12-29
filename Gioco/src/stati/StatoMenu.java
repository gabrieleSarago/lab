package stati;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JOptionPane;

import gfx.CaricatoreImmagini;
import gioco.Handler;
import pannelli.Sfondo;

public class StatoMenu extends Stato{
	
	private Sfondo s;
	private Handler h;
	
	private int sceltaCorrente = 0;
	private String [] opzioni = { "NUOVA PARTITA","CARICA PARTITA","CLASSIFICA","ESCI"};
	
	private Color coloreTitolo;
	private Font fontTitolo;
	
	private Font font;
	private Color colore;
	
	//per regolarizzare i movimenti
	int fps = 60;
	double tempoDiAggiornamento = 1000000000 / fps;
	double delta = 0;
	long ora;
	long ultimoTempo = System.nanoTime();
	long timer = 0;
	
	public StatoMenu(Handler h) {
		super(h);
		this.h = h;
		s = new Sfondo("res/img/menu.jpg",h);
		s.setPosizione(h.getLarghezza(), h.getAltezza());
		//s.setVector(-0.1, 0);
		
		coloreTitolo = new Color(150,0,24);
		fontTitolo = new Font ("Goudy Stout", Font.BOLD,42);
		
		colore = new Color(0,128,128);
		font = new Font ("Arial", Font.BOLD,32);
		
	}

	@Override
	public void aggiorna() {
		s.aggiorna();
		//ottimizzazione
		ora = System.nanoTime();
		delta +=(ora - ultimoTempo) / tempoDiAggiornamento;
		timer += ora - ultimoTempo;
		ultimoTempo = ora;
		if(delta >= 6){
			getInput();
			delta -= 6;
		}	
	}

	@Override
	public void disegna(Graphics g) {
		s.disegna(g);
		
		g.setColor(coloreTitolo);
		g.setFont(fontTitolo);
		g.drawImage(CaricatoreImmagini.caricaImmagine("res/img/titolo.png"),300, 100, null);
		
		g.setFont(font);
		for (int i = 0; i < opzioni.length; i++){
			if (i == sceltaCorrente) 
				g.setColor(new Color(47,47,47));
			else
				g.setColor(colore);
			g.drawString(opzioni[i], 500, 275 + i * 50);
		}
	}
	
	private void seleziona (){
		if (sceltaCorrente == 0){
			h.getGioco().setStato(new StatoGioco(h));
		}
		if (sceltaCorrente == 1){
			h.getGioco().setStato(new StatoGioco(h,"res/livelli/livelloS.txt"));
		}
		if (sceltaCorrente == 2){
			//classifica
		}
		if (sceltaCorrente == 3){
			if(consensoUscita())
				System.exit(0);
		}
	}
	
	private boolean consensoUscita(){
		int i=-1;
		i=JOptionPane.showConfirmDialog(null,"Sei sicuro di voler uscire?");
		if(i==JOptionPane.YES_OPTION)
			return true;
		if(i==JOptionPane.NO_OPTION)
			return false;
		return false;
	}
	
	private void getInput() {
		if(h.getGestioneInput().up){
			sceltaCorrente--;
			h.getGestioneInput().keyReleased(h.getGestioneInput().getKeyEvent());
			if(sceltaCorrente == -1)
				sceltaCorrente = opzioni.length -1;
			}
		if(h.getGestioneInput().down){
			sceltaCorrente++;
			h.getGestioneInput().keyReleased(h.getGestioneInput().getKeyEvent());
			if(sceltaCorrente == opzioni.length)
				sceltaCorrente = 0;	
		}
		if(h.getGestioneInput().enter){
			seleziona();
			h.getGestioneInput().keyReleased(h.getGestioneInput().getKeyEvent());
		}
	}		
}