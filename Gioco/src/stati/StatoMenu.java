package stati;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import finestra.FinestraUscita;
import gfx.Risorse;
import gfx.Suono;
import gioco.Handler;
import pannelli.Sfondo;

public class StatoMenu extends Stato{
	
	private Sfondo s;
	private Handler h;
	
	private int sceltaCorrente = 0;
	private String [] opzioni = { "NUOVA PARTITA","CARICA PARTITA","CLASSIFICA","OPZIONI", "INFO", "ESCI"};
	
	//Suono
	private Suono suono;
	private boolean riproduzione = true;
	
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
		
		suono = h.getSuono();		
	}
	
	public StatoMenu(Handler h, Suono suono) {
		super(h);
		this.h = h;
		s = new Sfondo("res/img/sfondi/menu.png",h);
		s.setPosizione(h.getLarghezza(), h.getAltezza());
		riproduzione = false;
		this.suono = suono;
	}

	@Override
	public void aggiorna() {
		if(riproduzione){
			 suono.riproduci(Suono.suoni.MENU);
			 riproduzione = false;
		}
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
		BufferedImage voce;
		for (int i = 0; i < opzioni.length; i++){
			if (i == sceltaCorrente)
				voce = Risorse.voci_menu[i];
			else
				voce = Risorse.voci_menu_off[i];
			switch(h.getLingua().getLingua()){
			case "ITALIANO" : switch(i){
			case 0: g.drawImage(voce, 395, 290 + i * 50, null); break;
			case 1: g.drawImage(voce, 388, 290 + i * 50, null); break;
			case 2: g.drawImage(voce, 445, 290 + i * 50, null); break;
			case 3: g.drawImage(voce, 495, 290 + i * 50, null); break;
			case 4: g.drawImage(voce, 540, 290 + i * 50, null); break;
			case 5: g.drawImage(voce, 540, 290 + i * 50, null); break;
			} break;
		case "ENGLISH" :  switch(i){
			case 0: g.drawImage(voce, 480, 290 + i * 50, null); break;
			case 1: g.drawImage(voce, 465, 290 + i * 50, null); break;
			case 2: g.drawImage(voce, 505, 290 + i * 50, null); break;
			case 3: g.drawImage(voce, 485, 290 + i * 50, null); break;
			case 4: g.drawImage(voce, 550, 290 + i * 50, null); break;
			case 5: g.drawImage(voce, 552, 290 + i * 50, null); break;
			} break;
		case "DEUTSCH" : switch(i){
			case 0: g.drawImage(voce, 440, 290 + i * 50, null); break;
			case 1: g.drawImage(voce, 460, 290 + i * 50, null); break;
			case 2: g.drawImage(voce, 465, 290 + i * 50, null); break;
			case 3: g.drawImage(voce, 480, 290 + i * 50, null); break;
			case 4: g.drawImage(voce, 470, 290 + i * 50, null); break;
			case 5: g.drawImage(voce, 490, 290 + i * 50, null); break;
			} break;
		}
		}
	}
	
	private void seleziona (){
		if (sceltaCorrente == 0){
			suono.getClipStatoMenu().close();
			h.getGioco().setStato(new StatoGioco(h));
			
		}
		if (sceltaCorrente == 1){
			suono.getClipStatoMenu().close();
			h.getGioco().setStato(new StatoGioco(h,"res/livelli/livelloS.txt"));
			
		}
		if (sceltaCorrente == 2){
			h.getGioco().setStato(new StatoClassifica(h, suono));
			
		}
		if(sceltaCorrente == 3){
			h.getGioco().setStato(new StatoOpzioni(h, suono));
		}
		
		if(sceltaCorrente == 4){
			h.getGioco().setStato(new StatoInfo(h, suono));
		}
		
		if (sceltaCorrente == 5){
			suono.getClipStatoMenu().stop();
			new FinestraUscita(h);
			
		}
		
		suono.ferma();
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
			h.getGestioneInput().keyReleased(h.getGestioneInput().getKeyEvent());
			suono.riproduci(Suono.suoni.CONFERMA);
			seleziona();
		}
		
	}
	
}