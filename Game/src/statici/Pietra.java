package statici;

import java.awt.Graphics;

import gfx.Risorse;
import gioco.Handler;
import tiles.Tile;

public class Pietra extends EntitaStatica{
	
	public Pietra(Handler h, float x, float y) {
		super(h, x, y, Tile.LARGHEZZA_TILE, Tile.LARGHEZZA_TILE*2);
		
	}

	@Override
	public void aggiorna() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void disegna(Graphics g) {
		g.drawImage(Risorse.pietra,(int)(x -h.getCameraGioco().getxOffset()), (int)(y - h.getCameraGioco().getyOffset()), larghezza, altezza, null);		
	}

}
