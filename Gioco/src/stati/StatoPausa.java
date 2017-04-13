package stati;

import java.awt.Color;
import java.awt.Graphics;

import gioco.Handler;
import grafica.Animazione;
import grafica.Risorse;
import grafica.Sfondo;
import suoni.Suono;

/**
 * Crea l'oggetto StatoPausa.
 */
public class StatoPausa extends Stato{

	private Sfondo s;
	
	private int sceltaCorrente = 0;
	
	int fps = 60;
	double tempoDiAggiornamento = 1000000000 / fps;
	double delta = 0;
	long ora;
	long ultimoTempo = System.nanoTime();
	long timer = 0;
	
	private StatoGioco precedente;
	private Animazione sinkDestra;
	private Suono suono;
	
	private boolean salvato = false;
	int dx = 2;
	
	/**
	 * Costruisce l'oggetto StatoPausa.
	 * @param h oggetto Handler utile per la gestione con le altre classi.
	 * @param precedente oggetto StatoGioco che identifica lo stato di gioco precedente.
	 */
	public StatoPausa(Handler h, StatoGioco precedente) {
		super(h);
		this.precedente = precedente;
				
		s = new Sfondo("res/img/sfondi/pausa.png", h);
		
		s.setVector(-0.3, 0);
		sinkDestra = new Animazione(100, Risorse.sink_destra);
		suono = h.getSuono();
	}

	/**
	 * Aggiorna lo StatoPausa.
	 */
	@Override
	public void aggiorna() {
		sinkDestra.aggiorna();
		precedente.getSink().aggiorna();
		s.aggiorna();
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
		//disegna sfondo
		s.disegna(g);
		
		//disegna la miniatura del gioco.
		g.setColor(Color.black);
		g.drawRect(848, 48, 304, 204);
		g.drawImage(precedente.getUltimoScreen(), 850, 50, 300, 200, null);
		
		//disegna sink nella pausa
		g.drawImage(sinkDestra.getFrameCorrente(),100, h.getGioco().getAltezza()-121, 
				precedente.getSink().getLarghezza(), precedente.getSink().getAltezza(), null);
		
		//disegna titolo
		g.drawImage(Risorse.titoloPausa, 400, 70, null);
		//disegna menu
		for(int i = 0; i<Risorse.voci_pausa.length; i++){
			if(i == sceltaCorrente)
				if(i == 2 && (h.getGestioneInput().enter || salvato)){
					g.drawImage(Risorse.ok, 400, 170 + i*60, null);
					salvato = true;
				}
				else
					g.drawImage(Risorse.voci_pausa[i], 400, 170 + i*60, null);
			else
				g.drawImage(Risorse.voci_pausa_off[i], 400, 170 + i*60, null);
		}
	}
	
	/**
	 * Aziona le scelte a disposizione dell'utente in StatoPausa.
	 */
	private void seleziona(){
		if(sceltaCorrente == 0){
			h.getGioco().setPausa(false);
			//Riprende il suono da dove era stato interrotto.
			if(!h.getSuono().getMuto())
				h.getSuono().getClipGioco().start();
			
			h.getGioco().setStato(precedente);
		}
		if(sceltaCorrente == 1){
			h.getGioco().setPausa(false);
			h.getGioco().setStato(new StatoGioco(h));
		}
		if(sceltaCorrente == 2){
			//qua salvi il livello
			h.getLivello().salva("livello1.salvataggio");
		}
		if(sceltaCorrente == 3){
			h.getGioco().setPausa(false);
			if(!suono.getMuto())
				h.getSuono().getClipGioco().close();
			h.getGioco().setStato(new StatoMenu(h));
		}
	}
	
	/**
	 * Gestisce gli ascoltatori da tastiera in StatoPausa.
	 */
	private void getInput(){
		
		//Accellera la traslazione dello sfondo alla pressione di freccia destra
		if(h.getGestioneInput().right)
			dx+=2;
		s.setVector(-0.3-dx, 0);
		//se la freccia viene rilasciata la velocit� torna normale. 
		dx = 0;
		
		if(h.getGestioneInput().up){
			h.getGestioneInput().keyReleased(h.getGestioneInput().getKeyEvent());
			suono.riproduci(Suono.suoni.SCORRI);
			sceltaCorrente--;
			if(sceltaCorrente<0){
				sceltaCorrente = Risorse.voci_pausa.length-1;			}
		}
		if(h.getGestioneInput().down){
			h.getGestioneInput().keyReleased(h.getGestioneInput().getKeyEvent());
			suono.riproduci(Suono.suoni.SCORRI);
			sceltaCorrente++;
			if(sceltaCorrente > Risorse.voci_pausa.length-1)
				sceltaCorrente = 0;
		}
		if(h.getGestioneInput().enter){
			h.getGestioneInput().keyReleased(h.getGestioneInput().getKeyEvent());
			suono.riproduci(Suono.suoni.CONFERMA);
			seleziona();
		}
	}

}
