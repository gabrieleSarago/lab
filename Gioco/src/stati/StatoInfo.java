package stati;

import java.awt.Graphics;

import gfx.Risorse;
import gfx.Suono;
import gioco.Handler;

public class StatoInfo extends Stato{
	
	private Handler h;
	private Suono suono;
	
	public StatoInfo(Handler h, Suono suono) {
		super(h);
		this.h = h;
		this.suono = suono;
	}

	@Override
	public void aggiorna() {
		if(h.getGestioneInput().esc || h.getGestioneInput().enter){
			h.setStato(new StatoMenu(h, suono));
		}
	}

	@Override
	public void disegna(Graphics g) {
		g.drawImage(Risorse.stato_info, 0, 0, null);
	}

}
