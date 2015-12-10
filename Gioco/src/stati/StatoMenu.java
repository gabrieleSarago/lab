package stati;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JOptionPane;

import gioco.Handler;
import pannelli.Sfondo;

public class StatoMenu extends Stato{
	
	private Sfondo s;
	private Handler h;
	private Stato sg;
	
	private int sceltaCorrente = 0;
	private String [] opzioni = { "NUOVA PARTITA","CARICA PARTITA","CLASSIFICA","OPZIONI","ESCI"};
	
	private Color coloreTitolo;
	private Font fontTitolo;
	
	private Font font;
	private Color colore;
	
	//per regolarizzare i movmenti
	int fps = 60;
	double tempoDiAggiornamento = 1000000000 / fps;
	double delta = 0;
	long ora;
	long ultimoTempo = System.nanoTime();
	long timer = 0;
	
	public StatoMenu(Handler h) {
		super(h);
		this.h = h;
		s = new Sfondo("res/img/d.jpg",h);
		s.setPosizione(h.getLarghezza(), h.getAltezza());
		s.setVector(-0.1, 0);
		
		coloreTitolo = new Color(150,0,24);
		fontTitolo = new Font ("Goudy Stout", Font.BOLD,42);
		
		colore = new Color(0,128,128);
		font = new Font ("Arial", Font.BOLD,32);
		
		sg = new StatoGioco(h);
	}

	@Override
	public void aggiorna() {
		s.aggiorna();
		//ottimizzazione
		ora = System.nanoTime();
		delta +=(ora - ultimoTempo) / tempoDiAggiornamento;
		timer += ora - ultimoTempo;
		ultimoTempo = ora;
		if(delta >= 5){
			getInput();
			delta -= 5;
		}	
	}

	@Override
	public void disegna(Graphics g) {
		s.disegna(g);
		
		g.setColor(coloreTitolo);
		g.setFont(fontTitolo);
		g.drawString("Labirinto", 400, 70);
		
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
			h.setStato(sg);
		}
		if (sceltaCorrente == 1){
			//carica
		}
		if (sceltaCorrente == 2){
			//classifica
		}
		if (sceltaCorrente == 3){
			//opzioni
		}
		if (sceltaCorrente == 4){
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
			if(sceltaCorrente == -1)
				sceltaCorrente = opzioni.length -1;
			}
		if(h.getGestioneInput().down){
			sceltaCorrente++;
			if(sceltaCorrente == opzioni.length)
				sceltaCorrente = 0;	
		}
		if(h.getGestioneInput().enter)
			seleziona();
	}		
}