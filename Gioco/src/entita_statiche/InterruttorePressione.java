package entita_statiche;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import entita_statiche.funzione.Funzione;
import gfx.Risorse;
import gioco.Handler;
import tiles.Tile;

public class InterruttorePressione extends Interruttore{

	public InterruttorePressione() {}// necessario per esternalizzazione
	public InterruttorePressione(Handler h, float x, float y, Funzione... funzione) {
		super(h, x, y, Tile.TILE_LARGHEZZA/2, Tile.TILE_ALTEZZA/2, funzione);
		
	}

	@Override
	public void aggiorna() {
	}

	@Override
	public void disegna(Graphics g) {
		g.drawImage(getAnimazioneCorrente(), (int) (x - h.getCameraGioco().getxOffset()), 
				(int) (y - h.getCameraGioco().getyOffset()), larghezza, altezza, null);
		/*g.setColor(Color.RED);
		g.fillRect((int)(x + bounds.x - h.getCameraGioco().getxOffset()),
				(int)(y + bounds.y - h.getCameraGioco().getyOffset()),
				bounds.width, bounds.height);*/
		
	}
	
	private BufferedImage getAnimazioneCorrente(){
		if(attivo){
			if(cambiaPosizione)
				return Risorse.interruttore_acceso_destra; 
			else return Risorse.interruttore_acceso_sinistra;
		}
		else
			return Risorse.interruttore_spento;
		
	}
	
	@Override
	public void writeExternal(ObjectOutput out) throws IOException {
		super.writeExternal(out);
	}
	@Override
	public void readExternal(ObjectInput in) throws IOException,
	ClassNotFoundException {
		super.readExternal(in);
	}
	

}
