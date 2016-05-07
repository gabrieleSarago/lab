package stati;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import gfx.Risorse;
import gfx.Suono;
import gioco.Handler;
import lingue.Lingua;
import pannelli.Sfondo;

public class StatoOpzioni extends Stato{
	
	private Sfondo s;
	private Handler h;
	
	private int sceltaCorrente = 0;
	private int linguaCorrente = 0;
	
	private String [] opzioni = {"LINGUA", "MUSICA", "TORNA AL MENU"};
	
	private Lingua lingua;
	private String [] lingue = {"ITALIANO", "ENGLISH", "DEUTSCH"};
	
	private Suono suono;
	
	//per regolarizzare i movimenti
	int fps = 55;
	double tempoDiAggiornamento = 1000000000 / fps;
	double delta = 0;
	long ora;
	long ultimoTempo = System.nanoTime();
	long timer = 0;
	
	public StatoOpzioni(Handler h, Suono suono) {
		super(h);
		this.h = h;
		s = new Sfondo("res/img/sfondi/menu.png",h);
		s.setPosizione(h.getLarghezza(), h.getAltezza());
		this.suono = suono;
		
		lingua = h.getLingua();
		for(int i = 0; i<lingue.length; i++){
			if(lingua.getLingua() != null && lingua.getLingua().equals(lingue[i]))
				linguaCorrente = i;
		}
	}

	@Override
	public void aggiorna() {
		
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
				voce = Risorse.voci_opzioni[i];
			else
				voce = Risorse.voci_opzioni_off[i];
			switch(i){
			case 0:{
				if(i == sceltaCorrente){
					switch(linguaCorrente){
					case 0: g.drawImage(Risorse.lingue[linguaCorrente], 485, 290 + i * 50, null); break;
					case 1: g.drawImage(Risorse.lingue[linguaCorrente], 495, 290 + i * 50, null); break;
					case 2: g.drawImage(Risorse.lingue[linguaCorrente], 480, 290 + i * 50, null); break;
					}
				}
				else{
					switch(linguaCorrente){
					case 0: g.drawImage(voce, 505, 290 + i * 50, null); break;
					case 1: g.drawImage(voce, 470, 290 + i * 50, null); break;
					case 2: g.drawImage(voce, 465, 290 + i * 50, null); break;
					}
				}
				break;
			}
			case 1:{
				switch(linguaCorrente){
				case 0: g.drawImage(voce, 505, 290 + i * 50, null); break;
				case 1: g.drawImage(voce, 530, 290 + i * 50, null); break;
				case 2: g.drawImage(voce, 525, 290 + i * 50, null); break;
				}break;
			}	
			case 2:{
				switch(linguaCorrente){
				case 0: g.drawImage(voce, 415, 290 + i * 50, null); break;
				case 1: g.drawImage(voce, 475, 290 + i * 50, null); break;
				case 2: g.drawImage(voce, 485, 290 + i * 50, null); break;
				}break;
			}
			}
		}	
		
	}
	
	private void seleziona (){
		if (sceltaCorrente == 0){
			switch(linguaCorrente){
			case 0:{
				lingua.setLingua("ITALIANO");
				Risorse.inizializzaITA();
				break;
			}
			case 1:{
				lingua.setLingua("ENGLISH"); 
				Risorse.inizializzaENG();
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
		if (sceltaCorrente == 2){
			suono.getClipStatoMenu().close();
			h.getGioco().setStato(new StatoMenu(h));
		}
		
		System.out.println("attivo");
		
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
			seleziona();
			h.getGestioneInput().keyReleased(h.getGestioneInput().getKeyEvent());
			suono.riproduci(Suono.suoni.CONFERMA);
		}
		
		if(sceltaCorrente == 0 && h.getGestioneInput().right){
			linguaCorrente++;
			suono.riproduci(Suono.suoni.SCORRI);
			if(linguaCorrente > lingue.length-1)
				linguaCorrente = 0;
			seleziona();
		}
		
		if(sceltaCorrente == 0 && h.getGestioneInput().left){
			linguaCorrente--;
			suono.riproduci(Suono.suoni.SCORRI);
			if(linguaCorrente < 0)
				linguaCorrente = lingue.length-1;
			seleziona();
		}
		if(h.getGestioneInput().esc){
			suono.riproduci(Suono.suoni.CONFERMA);
			h.getGioco().setStato(new StatoMenu(h));
		}
	}		
		

}
