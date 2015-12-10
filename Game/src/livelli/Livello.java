package livelli;

import java.awt.Graphics;

import entita.GestioneEntita;
import gioco.Handler;
import personaggio.Sink;
import statici.Pietra;
import tiles.Tile;
import utils.Utils;

public class Livello {
	
	private Handler h;
	private int larghezza, altezza;
	private int sinkX, sinkY;
	private int [][] tiles;
	
	//Entità
	private GestioneEntita gestioneEntita;
	
	public Livello(Handler h, String path){
		this.h = h;
		gestioneEntita = new GestioneEntita(h, new Sink(h, 70, 100));
		gestioneEntita.addEntita(new Pietra(h,100,300));
		gestioneEntita.addEntita(new Pietra(h,150,300));
		gestioneEntita.addEntita(new Pietra(h,200,300));
		
		carica(path);
		
		gestioneEntita.getSink().setX(sinkX);
		gestioneEntita.getSink().setY(sinkY);

	}
	
	public void aggiorna(){
		gestioneEntita.aggiorna();
	}
	
	public void disegna(Graphics g){
		//ottimizzazione
		int xStart = (int)Math.max(0, h.getCameraGioco().getxOffset() / Tile.LARGHEZZA_TILE);
		int xEnd = (int) Math.min(larghezza, (h.getCameraGioco().getxOffset() + h.getLarghezza()) / Tile.LARGHEZZA_TILE + 1);
		int yStart = (int) Math.max(0, h.getCameraGioco().getyOffset() / Tile.ALTEZZA_TILE);
		int yEnd = (int) Math.min(altezza, (h.getCameraGioco().getyOffset() + h.getAltezza())/ Tile.ALTEZZA_TILE);
		
		for(int y = yStart; y<yEnd; y++){
			for(int x = xStart; x<xEnd; x++){
				getTile(x, y).disegna(g, (int)(x*Tile.LARGHEZZA_TILE - h.getCameraGioco().getxOffset()),
										(int)(y*Tile.ALTEZZA_TILE - h.getCameraGioco().getyOffset()));
			}
		}
		//Entita
		gestioneEntita.disegna(g);
	}
	
	//crea il tile dalla matrice alla posizione (x,y)
	public Tile getTile(int x, int y){
		if(x < 0 || y < 0|| x >= larghezza || y >= altezza)
			return Tile.strada;
		
		Tile t = Tile.tiles[tiles[x][y]];
		if(t == null)
			return Tile.muro;
		return t;
	}
	
	private void carica(String path){
		
		String file = Utils.caricaFileComeStringa(path);
		String[] tokens = file.split("\\s+");
		larghezza = Utils.parseInt(tokens[0]);
		altezza = Utils.parseInt(tokens[1]);
		sinkX = Utils.parseInt(tokens[2]);
		sinkY = Utils.parseInt(tokens[3]);
		
		tiles = new int[larghezza][altezza];
		
		for(int y = 0; y< altezza; y++)
			for(int x = 0; x<larghezza; x++){
				tiles[x][y] = Utils.parseInt(tokens[(x + y*larghezza)+4]);
			}
	}
	
	public GestioneEntita getGestioneEntita() {
		return gestioneEntita;
	}

	public void setGestioneEntita(GestioneEntita gestioneEntita) {
		this.gestioneEntita = gestioneEntita;
	}

	public int getLarghezza(){
		return larghezza;
	}
	
	public int getAltezza(){
		return altezza;
	}
}
