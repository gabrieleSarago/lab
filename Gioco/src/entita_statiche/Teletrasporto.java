package entita_statiche;

import gfx.Risorse;
import gioco.Handler;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import tiles.Tile;

public class Teletrasporto extends EntitaStatica{

	private float destinazioneX, destinazioneY;
	private boolean attivo;
	
	public Teletrasporto(){}
	public Teletrasporto(Handler h, float x, float y, float destinazioneX, float destinazioneY) {
		super(h, x, y, Tile.TILE_LARGHEZZA, Tile.TILE_LARGHEZZA);
		attivo = true;
		this.destinazioneX = destinazioneX;
		this.destinazioneY = destinazioneY;
	}
	
	
	public Teletrasporto(Handler h, float x, float y, float destinazioneX, float destinazioneY, boolean attivo) {
		super(h, x, y, Tile.TILE_LARGHEZZA, Tile.TILE_LARGHEZZA);
		this.attivo = attivo;
		this.attraversabile = attivo;
		this.destinazioneX = destinazioneX;
		this.destinazioneY = destinazioneY;
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
			return Risorse.teletrasporto_attivo;
		else
			return Risorse.teletrasporto_inattivo;
		
	}

	public float getDestinazioneX() {
		return destinazioneX;
	}

	public void setDestinazioneX(float destinazioneX) {
		this.destinazioneX = destinazioneX;
	}

	public float getDestinazioneY() {
		return destinazioneY;
	}

	public void setDestinazioneY(float destinazioneY) {
		this.destinazioneY = destinazioneY;
	}
	
	public boolean eAttivo() {
		return attivo;
	}

	public void setAttivo(boolean attivo) {
		this.attivo = attivo;
	}
	
	public void cambiaAttivo() {
		this.attivo = !attivo;
	}
	
	@Override
	public void writeExternal(ObjectOutput out) throws IOException {
		super.writeExternal(out);
		out.writeBoolean(attivo);
		out.writeFloat(destinazioneX);
		out.writeFloat(destinazioneY);
		
	}
	@Override
	public void readExternal(ObjectInput in) throws IOException,
	ClassNotFoundException {
		super.readExternal(in);	
		attivo= in.readBoolean();
		destinazioneX = in.readFloat();
		destinazioneY = in.readFloat();
	}
	
}
