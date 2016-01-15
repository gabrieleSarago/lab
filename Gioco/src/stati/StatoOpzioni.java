package stati;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import gfx.CaricatoreImmagini;
import gfx.Risorse;
import gioco.Handler;
import lingue.Lingua;
import pannelli.Sfondo;

public class StatoOpzioni extends Stato{
	
	private Sfondo s;
	private Handler h;
	
	private int sceltaCorrente = 0;
	private int linguaCorrente = 0;
	
	private String [] opzioni = {"LINGUA", "MUSICA", "TORNA AL MENU"};
	private String [] opzioniENG = {"LANGUAGE", "MUSIC", "MAIN MENU"};
	private String [] opzioniITA =  {"LINGUA", "MUSICA", "TORNA AL MENU"};
	
	private Lingua lingua;
	private String [] lingue = {"ITALIANO", "ENGLISH", "DEUTSCH"}; 
	
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
	
	public StatoOpzioni(Handler h) {
		super(h);
		this.h = h;
		s = new Sfondo("res/img/sfondi/menu.png",h);
		s.setPosizione(h.getLarghezza(), h.getAltezza());
		//s.setVector(-0.1, 0);
		
		coloreTitolo = new Color(150,0,24);
		fontTitolo = new Font ("Goudy Stout", Font.BOLD,42);
		
		colore = new Color(0,128,128);
		font = new Font ("Arial", Font.BOLD,32);
		
		lingua = h.getGioco().getLingua();
		for(int i = 0; i<lingue.length; i++){
			if(lingua.getLingua() != null && lingua.getLingua().equals(lingue[i])){
				linguaCorrente = i;
				if(i == 0)
					opzioni = opzioniITA;
				if(i == 1)
					opzioni = opzioniENG;
			}
			if(lingua.getLingua() == null)
				opzioni = opzioniENG;
		}
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
		g.drawImage(CaricatoreImmagini.caricaImmagine("res/img/titoli/titolo.png"),300, 100, null);
		
		g.setFont(font);
		for (int i = 0; i < opzioni.length; i++){
			if (i == sceltaCorrente) 
				g.setColor(new Color(47,47,47));
			else
				g.setColor(colore);
			if(i == 0 && i == sceltaCorrente)
				g.drawString(lingue[linguaCorrente], 500, 275 + i * 50);
			else
				g.drawString(opzioni[i], 500, 275 + i * 50);
		}
		
	}
	
	private void seleziona (){
		if (sceltaCorrente == 0){
			switch(linguaCorrente){
			case 0:{
				lingua.setLingua("ITALIANO");
				Risorse.inizializzaITA();
				opzioni = opzioniITA;
				break;
			}
			case 1:{
				lingua.setLingua("ENGLISH"); 
				Risorse.inizializzaENG();
				opzioni = opzioniENG;
				break;
			}
			case 2:{
				lingua.setLingua("DEUTSCH");
				Risorse.inizializzaDEU();
				break;
			}
			}
		}
		
		if (sceltaCorrente == 1){
			//musica
		}
		if (sceltaCorrente == 2)
			h.getGioco().setStato(new StatoMenu(h));
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
		
		if(sceltaCorrente == 0 && h.getGestioneInput().right){
			linguaCorrente++;
			if(linguaCorrente > lingue.length-1)
				linguaCorrente = 0;
			seleziona();
		}
		
		if(sceltaCorrente == 0 && h.getGestioneInput().left){
			linguaCorrente--;
			if(linguaCorrente < 0)
				linguaCorrente = lingue.length-1;
			seleziona();
		}
	}		

}
