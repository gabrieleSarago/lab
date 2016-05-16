package stati;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import gfx.Risorse;
import gfx.Suono;
import gfx.Suono.suoni;
import gioco.Handler;
import lingue.Lingua;
import pannelli.Sfondo;

public class StatoOpzioni extends Stato{
	
	private Sfondo s;
	private Handler h;
	
	private int sceltaCorrente = 0;
	private int linguaCorrente = 0;
	private int musicaCorrente = 0;
	
	private String [] opzioni = {"LINGUA", "MUSICA", "TORNA AL MENU"};
	
	private Lingua lingua;
	private String [] lingue = {"ITALIANO", "ENGLISH", "DEUTSCH"};
	private String [] musica = {"ON", "OFF"};
	
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
		if(suono.getClipStatoMenu() == null){
			suono.riproduci(suoni.MENU);
		}
		lingua = h.getLingua();
		for(int i = 0; i<lingue.length; i++){
			if(lingua.getLingua() != null && lingua.getLingua().equals(lingue[i]))
				linguaCorrente = i;
		}
		//TODO impostare musica on/off da file
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
			if(sceltaCorrente == 0 && i == 0)
				//Per impostare le voci centrate
				g.drawImage(Risorse.lingue[linguaCorrente], h.getLarghezza()/2 - Risorse.lingue[linguaCorrente].getWidth()/2, 290 + i * 50, null);
			else if(sceltaCorrente == 1 && i == 1){
				g.drawImage(Risorse.voci_musica[musicaCorrente], h.getLarghezza()/2 - Risorse.voci_musica[musicaCorrente].getWidth()/2, 290 + i * 50, null);
			}
			else
				g.drawImage(voce, h.getLarghezza()/2 - voce.getWidth()/2, 290 + i * 50, null);
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
			if(musicaCorrente == 0){
				suono.setMuto(false);
			}
			else{
				suono.setMuto(true);
			}
		}
		if (sceltaCorrente == 2){
			h.getGioco().setStato(new StatoMenu(h, suono));
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
			h.getGestioneInput().keyReleased(h.getGestioneInput().getKeyEvent());
			seleziona();
			suono.riproduci(Suono.suoni.CONFERMA);
		}
		
		if(sceltaCorrente == 0 && h.getGestioneInput().right){
			h.getGestioneInput().keyReleased(h.getGestioneInput().getKeyEvent());
			linguaCorrente++;
			suono.riproduci(Suono.suoni.SCORRI);
			if(linguaCorrente > lingue.length-1)
				linguaCorrente = 0;
			seleziona();
		}
		
		if(sceltaCorrente == 0 && h.getGestioneInput().left){
			h.getGestioneInput().keyReleased(h.getGestioneInput().getKeyEvent());
			linguaCorrente--;
			suono.riproduci(Suono.suoni.SCORRI);
			if(linguaCorrente < 0)
				linguaCorrente = lingue.length-1;
			seleziona();
		}
		
		if(sceltaCorrente == 1 && h.getGestioneInput().right){
			h.getGestioneInput().keyReleased(h.getGestioneInput().getKeyEvent());
			suono.riproduci(Suono.suoni.SCORRI);
			musicaCorrente++;
			if(musicaCorrente > musica.length-1)
				musicaCorrente = 0;
			seleziona();
		}
		
		if(sceltaCorrente == 1 && h.getGestioneInput().left){
			h.getGestioneInput().keyReleased(h.getGestioneInput().getKeyEvent());
			suono.riproduci(Suono.suoni.SCORRI);
			musicaCorrente--;
			if(musicaCorrente < 0)
				musicaCorrente = musica.length-1;
			seleziona();
		}
		
		if(h.getGestioneInput().esc){
			h.getGestioneInput().keyReleased(h.getGestioneInput().getKeyEvent());
			suono.riproduci(Suono.suoni.CONFERMA);
			h.getGioco().setStato(new StatoMenu(h, suono));
		}
	}		
		

}
