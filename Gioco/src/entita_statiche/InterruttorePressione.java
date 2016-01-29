package entita_statiche;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import entita_statiche.funzione.Funzione;
import gfx.Risorse;
import gioco.Handler;
import tiles.Tile;

public class InterruttorePressione extends Interruttore{

	public InterruttorePressione(Handler h, float x, float y, Funzione... funzione) {
		super(h, x, y, Tile.TILE_LARGHEZZA, Tile.TILE_ALTEZZA, funzione);
	}

	@Override
	public void aggiorna() {
	}

	@Override
	public void disegna(Graphics g) {
		g.drawImage(getAnimazioneCorrente(), (int) (x - h.getCameraGioco().getxOffset()), 
				(int) (y - h.getCameraGioco().getyOffset()), larghezza, altezza, null);
		
	}
	
	private BufferedImage getAnimazioneCorrente(){
		if(attivo)
			return Risorse.interruttore_acceso;
		else
			return Risorse.interruttore_spento;
		
	}

}
