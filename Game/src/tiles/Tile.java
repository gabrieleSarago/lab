package tiles;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Tile {
	
	//static
	
	public static Tile[] tiles = new Tile[256];
	public static Tile strada = new TileStrada(0);
	public static Tile muro = new TileMuro(1);
	
	//class
	
	public static final int LARGHEZZA_TILE = 64, ALTEZZA_TILE = 64;
	
	protected BufferedImage img;
	protected final int id;
	
	public Tile(BufferedImage img, int id){
		this.img = img;
		this.id = id;
		
		tiles[id] = this;
	}
	
	public void aggiorna(){
		
	}
	
	public void disegna(Graphics g, int x, int y){
		g.drawImage(img, x, y, LARGHEZZA_TILE, ALTEZZA_TILE, null);
		
	}
	
	public boolean eSolido(){
		return false;
	}
	
	public int getId(){return id;}
	
	
}
