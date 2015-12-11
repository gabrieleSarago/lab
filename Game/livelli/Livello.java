package livelli;

import java.awt.Graphics;

import gioco.Handler;
import tiles.Tile;
import utils.Utils;

public class Livello {
	
	private Handler h;
	private int larghezza, altezza;
	public int sinkX, sinkY;
	private int [][] tiles;
	public int tempo;
	
	public Livello (Handler h, String path){
		this.h = h;
		carica(path);
	}
	
	public void aggiorna(){
		
	}
	
	public void disegna (Graphics g){
		//ottimizzazione
		int xStart = (int) Math.max(0, h.getCameraGioco().getxOffset() / Tile.TILE_LARGHEZZA);
		int xEnd = (int) Math.min(larghezza, (h.getCameraGioco().getxOffset() + h.getLarghezza())/Tile.TILE_LARGHEZZA +1);
		int yStart = (int) Math.max(0, h.getCameraGioco().getyOffset() / Tile.TILE_ALTEZZA);
		int yEnd = (int) Math.min(altezza, (h.getCameraGioco().getyOffset() + h.getLarghezza())/Tile.TILE_ALTEZZA +1);
		
		for (int y =  yStart; y < yEnd; y++)
			for(int x = xStart; x < xEnd ; x++){
				getTile(x,y).disegna(g, (int) (x * Tile.TILE_LARGHEZZA - h.getCameraGioco().getxOffset()),
						(int) (y * Tile.TILE_ALTEZZA - h.getCameraGioco().getyOffset()));
			}
	}
	
	public Tile getTile(int x, int y){
		if(x < 0 || y < 0 || x >= larghezza || y >= altezza)
			return Tile.strada;
		Tile t = Tile.tiles[tiles[x][y]];
		if(t==null)
			return Tile.muro;
		return t;
	}
	
	private void carica(String path){
		String file = Utils.caricaFileComeString(path);
		String[] tokens = file.split("\\s+");
		larghezza = Utils.parseInt(tokens[0]);
		altezza = Utils.parseInt(tokens[1]);
		sinkX = Utils.parseInt(tokens[2]);
		sinkY = Utils.parseInt(tokens[3]);
		tempo = Utils.parseInt(tokens[4]);
		
		tiles = new int[larghezza][altezza];
		for (int y = 0; y < altezza; y++)
			for (int x = 0; x < larghezza; x++)
				tiles[x][y] = Utils.parseInt(tokens[(x + (y * larghezza)) + 5]);
	}
	
	public int getLarghezza(){
		return larghezza;
	}
	
	public int getAltezza(){
		return altezza;
	}
	
}
