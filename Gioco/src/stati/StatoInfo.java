package stati;

import java.awt.Color;
import java.awt.Graphics;

import gfx.Risorse;
import gfx.Suono;
import gfx.Suono.suoni;
import gioco.Handler;
import pannelli.Sfondo;
/**
 * Crea l'oggetto StatoInfo.
 */
public class StatoInfo extends Stato{
	
	private Handler h;
	private Suono suono;
	private Sfondo s;
	
	private boolean  riproduzione = true;
	
	/**
	 * Costruisce l'oggetto StatoInfo.
	 * @param h oggetto Handler utile per la gestione con le altre classi.
	 */
	public StatoInfo(Handler h) {
		super(h);
		this.h = h;
		s = new Sfondo(Risorse.stato_info, h);
		s.setPosizione(0, 680);
		s.setVector(0, -0.3);
		
		suono = h.getSuono();
	}

	/**
	 * Aggiorna lo StatoInfo
	 */
	@Override
	public void aggiorna() {
		
		if(riproduzione){
			suono.riproduci(suoni.INFO);
			riproduzione = false;
		}
		
		if(s.getY() >= 0){
			s.aggiorna();
		}
		
		if(h.getGestioneInput().esc || h.getGestioneInput().enter){
			h.getGestioneInput().keyReleased(h.getGestioneInput().getKeyEvent());
			
			h.setStato(new StatoMenu(h));
			
			if(suono.getClipStatoInfo() != null)
				suono.getClipStatoInfo().stop();
		}
	}

	@Override
	public void disegna(Graphics g) {
		g.setColor(Color.black);
		g.fillRect(0, 0, h.getLarghezza(), h.getAltezza());
		s.disegna(g);
	}

}
