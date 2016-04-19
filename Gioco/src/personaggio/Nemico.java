package personaggio;

import entita.Entita;
import gfx.Risorse;
import gioco.Handler;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import tiles.Tile;


public class Nemico extends Personaggio{

	int raggioVisione;
	private Rectangle visione;
	private boolean vivo;
	private Sink sink;
	
	public Nemico(Handler h, float x, float y)
	{
		super(h, x, y, Personaggio.DEFAULT_LARGHEZZA_PERSONAGGIO/2, Personaggio.DEFAULT_ALTEZZA_PERSONAGGIO/2);
		bounds = new Rectangle(0, 0, Tile.TILE_LARGHEZZA/2, Tile.TILE_ALTEZZA/2);
		raggioVisione = 5*Tile.TILE_LARGHEZZA;
		vivo = true;
		visione = new Rectangle(-raggioVisione, -raggioVisione, raggioVisione*2, raggioVisione*2);
		velocita = 2.0f;
	}
	
		
	@Override
	public void aggiorna() {
		if(!sinkAvvistato()) return;
		elaborazionePercorso();
		muovi();
	}

	
	@Override
	public void disegna(Graphics g) {
		/*g.setColor(Color.RED);
		int tempX = (int)(x + visione.x - h.getCameraGioco().getxOffset());
		int tempY =(int)(y + visione.y - h.getCameraGioco().getyOffset());
		g.drawLine(tempX, tempY, visione.width, tempY);
		g.drawLine(tempX, tempY+visione.height, visione.width, tempY+visione.height);
		g.drawLine(tempX, tempY, tempX, tempY+visione.height);
		g.drawLine(tempX+visione.width, tempY, tempX+visione.width, tempY+visione.height);
		/*g.fillRect((int)(x + visione.x - h.getCameraGioco().getxOffset()),
				(int)(y + visione.y - h.getCameraGioco().getyOffset()),
				visione.width, visione.height);*/
		g.drawImage(Risorse.nemico, (int) (x - h.getCameraGioco().getxOffset()), 
				(int) (y - h.getCameraGioco().getyOffset()), larghezza, altezza, null);
		
	}
	
	private void elaborazionePercorso() {
		dx = 0;
		dy = 0;
		
		if(sink.getY() < this.getY())
			dy = -velocita;
		if(sink.getY() > this.getY())
			dy = velocita;
		if(sink.getX() < this.getX())
			dx = -velocita;
		if(sink.getX() > this.getX())
			dx = velocita;
		
		//per stabilizzare i movimenti
		if(Math.abs(sink.getY()-this.getY()) < 1.1)
			dy = 0;
		if(Math.abs(sink.getX()-this.getX()) < 1.1)
			dx = 0;
		
	}
	
	public Rectangle getVisionBounds(){
		return new Rectangle((int) (x + visione.x), (int) (y + visione.y), visione.width, visione.height);
	}
	// metodo generale probabile uso in seguito
	private Entita controllaCollisioniVisione(){
		for (Entita e : h.getLivello().getArray_entita()){
			if(!e.equals(this))
			{
				if(getVisionBounds().intersects(e.getCollisionBounds(0f, 0f)))
					return e;
			}
		}
		return null;
	}
	
	private boolean sinkAvvistato(){	
		if(sink == null) sink = h.getLivello().getSink();
		if(getVisionBounds().intersects(sink.getCollisionBounds(0f, 0f)))
		return true;
			return false;
	}

}


