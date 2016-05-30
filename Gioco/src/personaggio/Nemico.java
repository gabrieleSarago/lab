package personaggio;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import entita.Entita;
import gfx.Risorse;
import gioco.Handler;
import tiles.Tile;

/**
 * Crea l'oggetto Nemico che è un entità del gioco.
 */
public class Nemico extends Personaggio{

	int raggioVisione;
	private Rectangle visione;
	private boolean vivo;
	private Sink sink;
	
	/**
	 * Costruttore di default utile per l'esternalizzazione.
	 */
	public Nemico() {}
	/**
	 * Costruisce l'oggetto Nemico.
	 * @param h oggetto Handler utile per la gestione con le altre classi.
	 * @param x la ccordinata x del nemico.
	 * @param y la coordinata y del nemico.
	 */
	public Nemico(Handler h, float x, float y)
	{
		super(h, x, y, Personaggio.DEFAULT_LARGHEZZA_PERSONAGGIO/2, Personaggio.DEFAULT_ALTEZZA_PERSONAGGIO/2);
		bounds = new Rectangle(0, 0, Tile.TILE_LARGHEZZA/2, Tile.TILE_ALTEZZA/2);
		raggioVisione = 5*Tile.TILE_LARGHEZZA;
		vivo = true;
		visione = new Rectangle(-raggioVisione, -raggioVisione, raggioVisione*2, raggioVisione*2);
		velocita = 2.0f;
	}
	
	public Nemico(Handler h, float x, float y, boolean vivo)
	{
		super(h, x, y, Personaggio.DEFAULT_LARGHEZZA_PERSONAGGIO/2, Personaggio.DEFAULT_ALTEZZA_PERSONAGGIO/2);
		bounds = new Rectangle(0, 0, Tile.TILE_LARGHEZZA/2, Tile.TILE_ALTEZZA/2);
		raggioVisione = 5*Tile.TILE_LARGHEZZA;
		this.vivo = vivo;
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
		if(!vivo) return;
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
	/**
	 * Costruisce il percoso da compiere.
	 */
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
	/**
	 * Utile per far "vedere" il nemico.
	 * @return
	 */
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
	/**
	 * Metodo che valuta la visione del giocatore da parte del nemico.
	 * @return boolean identificativo.
	 */
	private boolean sinkAvvistato(){	
		if(sink == null) sink = h.getLivello().getSink();
		if(getVisionBounds().intersects(sink.getCollisionBounds(0f, 0f)))
		return true;
			return false;
	}
	/**
	 * Carica i nemici nel livello da file.
	 */
	public void readExternal(ObjectInput in) throws IOException,
	ClassNotFoundException {
		super.readExternal(in);
		vivo = in.readBoolean();
		raggioVisione = in.readInt();	
		velocita = in.readFloat();
		bounds = (Rectangle) in.readObject();
		visione = (Rectangle) in.readObject();
		
	}
	
	/**
	 * Salva le posizioni dei nemici su file.
	 */
	@Override
	public void writeExternal(ObjectOutput out) throws IOException {
		super.writeExternal(out);
		out.writeBoolean(vivo);
		out.writeInt(raggioVisione);
		out.writeFloat(velocita);
		out.writeObject(bounds);
		out.writeObject(visione);
	}
	/**
	 * Muove il nemico.
	 */
	@Override
	public void muovi(){
		Entita temp; // entita solo temporanea
		// se temp è null significa che non c è nessuna collisione
		temp = controllaCollisioni(dx, 0f);
		if(temp == null)
			muoviX();
		else
		{
			ultimaEntita = temp;
			if(temp.eAttraversabile() && !(temp instanceof Nemico))
				muoviX();
		}
		temp = controllaCollisioni(0f, dy);
		if(temp == null)
			muoviY();
		else
		{
			ultimaEntita = temp;
			if(temp.eAttraversabile() && !(temp instanceof Nemico))
				muoviY();
		}
	}
		 
	public boolean eVivo(){
		return vivo;
	}
	
	public void setVivo(boolean vivo){
		this.vivo = vivo;
	}

}


