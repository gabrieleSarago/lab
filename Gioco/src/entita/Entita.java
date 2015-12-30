package entita;

import java.awt.Graphics;
import java.awt.Rectangle;

import gioco.Handler;

public abstract class Entita {
	
	protected Handler h;
	protected float x, y;
	protected int larghezza,altezza;
	protected Rectangle bounds;
	
	public Entita(Handler h, float x, float y, int larghezza, int altezza){
		this.h = h;
		this.x = x;
		this.y = y;
		this.larghezza = larghezza;
		this.altezza = altezza;
		
		bounds = new Rectangle(0, 0, larghezza, altezza);
	}
	
/*	public Rectangle getCollisionBounds(float xOffset, float yOffset){
		return new Rectangle((int) (x + bounds.x + xOffset), (int) (y + bounds.y + yOffset), bounds.width, bounds.height);
	}
	
	public EntitaStatica controllaCollisioni(float xOffset, float yOffset){
		for (EntitaStatica e : h.getLivello().entitaStatiche){
			if(e.getCollisionBounds(0f, 0f).intersects(new Rectangle((int) xOffset, (int) yOffset, (int) xOffset+32, (int) yOffset+32)))
			return e;
		}
		return null;
	}
*/	
	public abstract void aggiorna();
	
	public abstract void disegna(Graphics g);
	
	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public int getLarghezza() {
		return larghezza;
	}

	public void setLarghezza(int larghezza) {
		this.larghezza = larghezza;
	}

	public int getAltezza() {
		return altezza;
	}

	public void setAltezza(int altezza) {
		this.altezza = altezza;
	}
	
}
