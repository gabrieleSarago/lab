package stati;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import finestra.FinestraUscita;
import gfx.CaricatoreImmagini;
import gfx.Risorse;
import gfx.Suono;
import gioco.Handler;
import pannelli.Sfondo;

public class StatoMenu extends Stato{
	
	private Sfondo s;
	private Handler h;
	
	private int sceltaCorrente = 0;
	private String [] opzioni = { "NUOVA PARTITA","CARICA PARTITA","CLASSIFICA","OPZIONI", "INFO", "ESCI"};
	
	private Color coloreTitolo;
	private Font fontTitolo;
	
	private Font font;	
	private Suono suono;
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
		s = new Sfondo("res/img/sfondi/menu.png",h);
		s.setPosizione(h.getLarghezza(), h.getAltezza());
		//s.setVector(-0.1, 0);
		
		coloreTitolo = new Color(150,0,24);
		fontTitolo = new Font ("Goudy Stout", Font.BOLD,42);
		font = new Font ("Arial", Font.BOLD,32);
		suono = h.getSuono();
		
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
		g.drawImage(CaricatoreImmagini.caricaImmagine("res/img/titoli/titolo.png"),230, 50, null);
		BufferedImage voce;
		g.setFont(font);
		for (int i = 0; i < opzioni.length; i++){
			if (i == sceltaCorrente)
				voce = Risorse.voci_menu[i];
			else
				voce = Risorse.voci_menu_off[i];
			switch(h.getLingua().getLingua()){
			case "ITALIANO" : switch(i){
				case 0: g.drawImage(voce, 405, 275 + i * 50, null); break;
				case 1: g.drawImage(voce, 400, 275 + i * 50, null); break;
				case 2: g.drawImage(voce, 450, 275 + i * 50, null); break;
				case 3: g.drawImage(voce, 500, 275 + i * 50, null); break;
				case 4: g.drawImage(voce, 545, 275 + i * 50, null); break;
				case 5: g.drawImage(voce, 545, 275 + i * 50, null); break;
				} break;
			case "ENGLISH" :  switch(i){
				case 0: g.drawImage(voce, 490, 275 + i * 50, null); break;
				case 1: g.drawImage(voce, 475, 275 + i * 50, null); break;
				case 2: g.drawImage(voce, 515, 275 + i * 50, null); break;
				case 3: g.drawImage(voce, 495, 275 + i * 50, null); break;
				case 4: g.drawImage(voce, 560, 275 + i * 50, null); break;
				case 5: g.drawImage(voce, 562, 275 + i * 50, null); break;
				} break;
			case "DEUTSCH" : switch(i){
				case 0: g.drawImage(voce, 450, 275 + i * 50, null); break;
				case 1: g.drawImage(voce, 470, 275 + i * 50, null); break;
				case 2: g.drawImage(voce, 475, 275 + i * 50, null); break;
				case 3: g.drawImage(voce, 490, 275 + i * 50, null); break;
				case 4: g.drawImage(voce, 480, 275 + i * 50, null); break;
				case 5: g.drawImage(voce, 500, 275 + i * 50, null); break;
				} break;
			}
		}
	}
	
	private void seleziona (){
		if (sceltaCorrente == 0){
			//h.getGioco().setPausa(false);
			//h.getGioco().getSuono().riproduci(Suono.suoni.GIOCO);
			h.getGioco().setStato(new StatoGioco(h));
		}
		if (sceltaCorrente == 1){
			//h.getGioco().setPausa(false);
			h.getGioco().setStato(new StatoGioco(h,"res/livelli/livelloS.txt"));
		}
		if (sceltaCorrente == 2){
			//classifica
		}
		if(sceltaCorrente == 3){
			h.getGioco().setStato(new StatoOpzioni(h));
		}
		
		if(sceltaCorrente == 4){
		
		}
		
		if (sceltaCorrente == 5){
			new FinestraUscita(h);
		}
	}
	
	private void getInput() {
		
		if(h.getGestioneInput().up){
			sceltaCorrente--;
			h.getGestioneInput().keyReleased(h.getGestioneInput().getKeyEvent());
			if(sceltaCorrente == -1)
				sceltaCorrente = opzioni.length -1;
			suono.riproduci(Suono.suoni.SCORRI);
			}
		if(h.getGestioneInput().down){
			sceltaCorrente++;
			h.getGestioneInput().keyReleased(h.getGestioneInput().getKeyEvent());
			if(sceltaCorrente == opzioni.length)
				sceltaCorrente = 0;
			suono.riproduci(Suono.suoni.SCORRI);
		}
		if(h.getGestioneInput().enter){
			suono.riproduci(Suono.suoni.CONFERMA);
			seleziona();
			h.getGestioneInput().keyReleased(h.getGestioneInput().getKeyEvent());
		}
		
	}
	
}