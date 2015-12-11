package tiles;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Tile {
	
	//STATIC STUFF QUA
	
	public static Tile[] tiles = new Tile [256];
	public static Tile strada = new TileStrada(0);
	public static Tile muro = new TileMuro(1);
	
	//CLASS
	
	public static final int TILE_LARGHEZZA = 64;
	public static final int TILE_ALTEZZA = 64;
	
	protected BufferedImage img;
	protected final int id;
	
	public Tile(BufferedImage img, int id){
		this.img = img;
		this.id = id;
		
		tiles[id] = this;
	}

	
	public void aggiorna(){
		
	}
	
	public void disegna (Graphics g, int x, int y){
		g.drawImage(img, x, y, TILE_LARGHEZZA, TILE_ALTEZZA, null);
	}
	
	public boolean eSolido(){
		return false;
	}
	
	public int getId() {
		return id;
	}	
}
