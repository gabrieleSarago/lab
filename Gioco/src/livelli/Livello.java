package livelli;

import java.awt.Graphics;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import entita_statiche.Caramella;
import entita_statiche.EntitaStatica;
import entita_statiche.Trofeo;
import gioco.Handler;
import tiles.Tile;
import utils.Utils;

public class Livello {
	
	private Handler h;
	private int larghezza, altezza;
	public int sinkX, sinkY;
	private int [][] tiles;
	public int tempo;
	public ArrayList<EntitaStatica> entitaStatiche;
	
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
		
		entitaStatiche = new ArrayList<>();
		int c = 5;
		while(Utils.parseInt(tokens[c])!= 0000){
			entitaStatiche.add(new Caramella(h, Utils.parseInt(tokens[c]),Utils.parseInt(tokens[c+1])));
			c += 2;
		}
		c++;
		entitaStatiche.add(new Trofeo(h, 1750, 689));
		
		tiles = new int[larghezza][altezza];
		for (int y = 0; y < altezza; y++)
			for (int x = 0; x < larghezza; x++)
				tiles[x][y] = Utils.parseInt(tokens[(x + (y * larghezza)) + c]);
	}
	
	public void salva(String file, int x, int y, int tempo){
		File f = new File(file);
		f.delete();
		PrintWriter pw;
		try {
			pw = new PrintWriter(new FileWriter(file));
			pw.write(tiles.length + " " + tiles[0].length + "\n"+x +" "+y +"\n"+ tempo+"\n");
			pw.write("\n");
			for(EntitaStatica c : entitaStatiche){
				if (c instanceof Caramella){
					x =(int) c.getX();
     				y =(int) c.getY();
				    pw.write(x + " " + y + "\n");
				}
			}
			pw.write("0000" + "\n");
			for(int i = 0; i<tiles.length; i++){
				pw.write("\n");
				for(int j = 0; j<tiles[i].length; j++){
					pw.write(tiles[j][i]+" ");
				}
			}
			pw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public int getLarghezza(){
		return larghezza;
	}
	
	public int getAltezza(){
		return altezza;
	}

	public int getTempo() {
		return tempo;
	}

	public void setTempo(int tempo) {
		this.tempo = tempo;
	}
	
}
