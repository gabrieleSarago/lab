package entita_statiche;

import gfx.Risorse;
import gioco.Handler;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import tiles.Tile;

public class Sbarra extends EntitaStatica{

	public Sbarra(){} // per esternalizzazione
	public Sbarra(Handler h, float x, float y, boolean aperta) {
		super(h, x, y, Tile.TILE_LARGHEZZA, Tile.TILE_ALTEZZA);
		this.attraversabile = aperta;
	}

	@Override
	public void aggiorna() {
		// TODO 
		
	}

	@Override
	public void disegna(Graphics g) {
		g.drawImage(getAnimazioneCorrente(), (int) (x - h.getCameraGioco().getxOffset()), 
				(int) (y - h.getCameraGioco().getyOffset()), larghezza, altezza, null);
		
	}
	
	private BufferedImage getAnimazioneCorrente(){
		if(eAttraversabile())
			return Risorse.sbarra_aperta;
		else
			return Risorse.sbarra_chiusa;
		
	}
	public void apri()
	{
		setAttraversabile(true);
	}
	public void chiudi()
	{
		setAttraversabile(false);
	}
	
	public void cambia()
	{
		setAttraversabile(!attraversabile);
	}
	
	@Override
	public void writeExternal(ObjectOutput out) throws IOException {
		super.writeExternal(out);
		out.writeBoolean(attraversabile);
	}
	@Override
	public void readExternal(ObjectInput in) throws IOException,
	ClassNotFoundException {
		super.readExternal(in);	
		attraversabile= in.readBoolean();
	}
	
}
