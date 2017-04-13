package stati;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;

import finestra.FinestraNessunSalvataggio;
import finestra.FinestraUscita;
import gioco.Handler;
import grafica.Risorse;
import grafica.Sfondo;
import suoni.Suono;
/**
 * Crea l'oggetto StatoMenu.
 */
public class StatoMenu extends Stato{
	
	private Sfondo s;
	private Handler h;
	
	private int sceltaCorrente = 0;
	private enum opzioni{NUOVA_PARTITA,CARICA_PARTITA,CLASSIFICA,OPZIONI, INFO, ESCI};
	
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
	
	/**
	 * Costruisce l'oggetto StatoMenu.
	 * @param h oggetto Handler utile per la gestione con le altre classi.
	 */
	public StatoMenu(Handler h) {
		super(h);
		this.h = h;
		s = new Sfondo("res/img/sfondi/menu.png", h);
		s.setPosizione(h.getLarghezza(), h.getAltezza());
		
		suono = h.getSuono();		
	}
	
	/**
	 * Costruisce l'oggetto StatoMenu.
	 * @param h oggetto Handler utile per la gestione con le altre classi.
	 * @param suono la musica di sottofondo nel menu'.
	 */
	public StatoMenu(Handler h, Suono suono) {
		super(h);
		this.h = h;
		s = new Sfondo("res/img/sfondi/menu.png",h);
		s.setPosizione(h.getLarghezza(), h.getAltezza());
		riproduzione = false;
		this.suono = suono;
		if(!suono.getClipStatoMenu().isRunning()){
			riproduzione = true;
		}
	}

	/**
	 * Aggiorna lo StatoMenu.
	 */
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
		for (int i = 0; i < opzioni.values().length/*opzioni.length*/; i++){
			if (i == sceltaCorrente)
				voce = Risorse.voci_menu[i];
			else
				voce = Risorse.voci_menu_off[i];
			//Per impostare le voci centrate
			g.drawImage(voce, h.getLarghezza()/2 - voce.getWidth()/2, 290 + i * 50, null);
		}
	}
	
	/**
	 * Aziona le scelte a dispozione dell'utente.
	 */
	private void seleziona (){
		suono.riproduci(Suono.suoni.CONFERMA);
		
		if (sceltaCorrente == opzioni.NUOVA_PARTITA.ordinal()){// nuova partita
			suono.getClipStatoMenu().close();
			h.getGioco().setStato(new StatoGioco(h));
		}
		if (sceltaCorrente == opzioni.CARICA_PARTITA.ordinal()){//qua carichi il livello
			String pathLivelloEntita = Risorse.PATH_ESTERNO_LIVELLI + Risorse.SEPARATORE + "livello1.salvataggio";
			File f = new File(pathLivelloEntita);
			if(!f.exists()){
				suono.getClipStatoMenu().stop();
				new FinestraNessunSalvataggio(h);
			}
			else{
				suono.getClipStatoMenu().close();
				h.getGioco().setStato(new StatoGioco(h, "livello1_S"));
			}
			
		}
		if (sceltaCorrente == opzioni.CLASSIFICA.ordinal()){
			h.getGioco().setStato(new StatoClassifica(h, suono));

		}
		if(sceltaCorrente == opzioni.OPZIONI.ordinal()){
			h.getGioco().setStato(new StatoOpzioni(h, suono));
		}
		
		if(sceltaCorrente == opzioni.INFO.ordinal()){
			suono.getClipStatoMenu().stop();
			h.getGioco().setStato(new StatoInfo(h));
		}
		
		if (sceltaCorrente == opzioni.ESCI.ordinal()){
			suono.getClipStatoMenu().stop();
			new FinestraUscita(h);
		}
		
		suono.ferma();
	}
	
	/**
	 * Gestisce gli ascoltatori da tastiera in StatoMenu.
	 */
	private void getInput() {
		
		if(h.getGestioneInput().up){
			sceltaCorrente--;
			h.getGestioneInput().keyReleased(h.getGestioneInput().getKeyEvent());
			if(sceltaCorrente == -1)
				sceltaCorrente = opzioni.values().length -1;
			suono.riproduci(Suono.suoni.SCORRI);
			}
		if(h.getGestioneInput().down){
			sceltaCorrente++;
			h.getGestioneInput().keyReleased(h.getGestioneInput().getKeyEvent());
			if(sceltaCorrente == opzioni.values().length)
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