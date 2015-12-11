package stati;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import gioco.Handler;
import pannelli.Sfondo;

public class StatoMenu extends Stato{

	private Sfondo s;
	
	private int sceltaCorrente = 0;
	private String[] opzioni = {"NUOVA PARTITA","CARICA PARTITA","OPZIONI","CLASSIFICA","ESCI"};
	
	private Color coloreTitolo;
	private Font fontTitolo;
	private Font font;
	
	int fps = 60;
	double tempoDiAggiornamento = 1000000000 / fps;
	double delta = 0;
	long ora;
	long ultimoTempo = System.nanoTime();
	long timer = 0;
	
	public StatoMenu(Handler h) {
		super(h);
		s = new Sfondo("res/img/d.jpg", h);
		
		//traslazione sfondo
		s.setVector(-0.1, 0);
		
		coloreTitolo = Color.BLACK;
		fontTitolo = new Font("Arial", Font.BOLD, 40);
		font = new Font("Arial", Font.BOLD, 30);
	}

	@Override
	public void aggiorna() {
		s.aggiorna();
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
		//disegna sfondo
		s.disegna(g);
		
		//disegna titolo
		g.setColor(coloreTitolo);
		g.setFont(fontTitolo);
		g.drawString("LABIRINTO", 420, 100);
		
		//disegna menu
		g.setFont(font);
		for(int i = 0; i<opzioni.length; i++){
			if(i == sceltaCorrente){
				g.setColor(Color.ORANGE);
			}
			else{
				g.setColor(Color.WHITE);
			}
			g.drawString(opzioni[i], 400, 170 + i*30);
		}
	}
	
	private void seleziona(){
		if(sceltaCorrente == 0){
			h.getGioco().setStato(new StatoGioco(h));
		}
		if(sceltaCorrente == 1){
			//carica partita
		}
		if(sceltaCorrente == 2){
			//classifica
		}
		if(sceltaCorrente == 3){
			//opzioni
		}
		if(sceltaCorrente == 4){
			System.exit(0);
		}
	}
	
	private void getInput(){
		if(h.getGestioneInput().up){
			sceltaCorrente--;
			if(sceltaCorrente<0){
				sceltaCorrente = opzioni.length-1;
			}
		}
		if(h.getGestioneInput().down){
			sceltaCorrente++;
			if(sceltaCorrente > opzioni.length-1)
				sceltaCorrente = 0;
		}
		if(h.getGestioneInput().enter)
			seleziona();
	}
}
