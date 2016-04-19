package entita;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import gioco.Handler;

public abstract class Entita implements Externalizable{
	
	protected Handler h;
	protected float x, y;
	protected int larghezza,altezza;
	protected Rectangle bounds;
	//------------
	protected boolean attraversabile;
	
	public Entita(){}// necessario per esternalizzazione

	public Entita(Handler h, float x, float y, int larghezza, int altezza){
		this.h = h;
		this.x = x;
		this.y = y;
		this.larghezza = larghezza;
		this.altezza = altezza;
		
		bounds = new Rectangle(0, 0, larghezza, altezza);
		//---------
		attraversabile = true;
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
	//---- gestisce le collisioni
	public Rectangle getCollisionBounds(float xOffset, float yOffset){
		return new Rectangle((int) (x + bounds.x + xOffset), (int) (y + bounds.y + yOffset), bounds.width, bounds.height);
	}
	// ---- gestisce le collisioni
	public Entita controllaCollisioni(float xOffset, float yOffset){
		for (Entita e : h.getLivello().getArray_entita()){
			if(!e.equals(this))
			{
				if(e.getCollisionBounds(0f, 0f).intersects(getCollisionBounds(xOffset, yOffset)))
					return e;
			}
		}
		return null;
	}
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
	
	//------ gestire le collisioni
	public boolean eAttraversabile() {
		return attraversabile;
	}

	public void setAttraversabile(boolean attraversabile) {
		this.attraversabile = attraversabile;
	}
	
	public Handler getHandler()
	{
		return h;
	}
	
	public void setHandler(Handler h)
	{
		this.h = h;
	}
	
	//per esternalizzazione
	public void writeExternal(ObjectOutput out) throws IOException {
		out.writeFloat(x);
		out.writeFloat(y);
		out.writeInt(larghezza);
		out.writeInt(altezza);
		
	}
	
	public void readExternal(ObjectInput in) throws IOException,
	ClassNotFoundException {
		
		this.x = in.readFloat();
		this.y = in.readFloat();
		this.larghezza = in.readInt();
		this.altezza = in.readInt();
		bounds = new Rectangle(0, 0, larghezza, altezza);
		attraversabile = true;
	}
	
	
	
}
