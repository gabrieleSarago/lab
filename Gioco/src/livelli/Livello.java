package livelli;

import java.awt.Graphics;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
//import java.util.ArrayList; eliminato per il gestore entita







import personaggio.Sink;
import entita.Entita;
import entita.GestoreEntita;
import entita_statiche.Caramella;
import entita_statiche.InterruttorePressione;
import entita_statiche.Sbarra;
import entita_statiche.Teletrasporto;
//import entita_statiche.EntitaStatica; eliminato per il gestore entita
import entita_statiche.Trofeo;
import entita_statiche.funzione.Funzionalita;
import entita_statiche.funzione.Funzione;
import gioco.Handler;
import tiles.Tile;
import utils.Utils;


// TO-DO: permettere il salvataggio dell' entità in un file di testo
public class Livello {
	
	private Handler h;
	private int larghezza, altezza;
	public int sinkX, sinkY;
	private int [][] tiles;
	public int tempo;
	//----public ArrayList<EntitaStatica> entitaStatiche;
	
	//----gestore entita 
	private GestoreEntita gestoreEntita;
	
	
	
	public Livello (Handler h, String path){
		this.h = h;
		//---- gestore
		gestoreEntita = new GestoreEntita(h, new Sink(h,  sinkX, sinkY, tempo));
		//temporaneo
		caricaEntita();
		
		carica(path);
		
		gestoreEntita.getSink().setX(sinkX);
		gestoreEntita.getSink().setY(sinkY);
		gestoreEntita.getSink().setTempo(tempo);
		
	}
	
	public void aggiorna(){	
		//---- gestore
		gestoreEntita.aggiorna();
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
		//---- gestore
		gestoreEntita.disegna(g);
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
		
		// ---- entitaStatiche = new ArrayList<>(); eliminato
		int c = 5;
		while(Utils.parseInt(tokens[c])!= 0000){
			//----# entitaStatiche.add(new Caramella(h, Utils.parseInt(tokens[c]),Utils.parseInt(tokens[c+1])));
			gestoreEntita.aggiungiEntita(new Caramella(h, Utils.parseInt(tokens[c]),Utils.parseInt(tokens[c+1])));
			
			c += 2;
		}
		c++;
		//---- #entitaStatiche.add(new Trofeo(h, 1750, 689));
		gestoreEntita.aggiungiEntita(new Trofeo(h, 1750, 689));
		
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
			// ----#for(EntitaStatica c : entitaStatiche){
			for(Entita c: gestoreEntita.getEntita()){
				
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
	//---- gestore
	public Sink getSink()
	{
		//return sink;
		return gestoreEntita.getSink();
	}
	//---- gestore
	public GestoreEntita getGestoreEntita()
	{
		return gestoreEntita;
	}
	//----
	private void caricaEntita()
	{
		gestoreEntita.aggiungiEntita(new Sbarra(h, 256, 448, false)); // indice 1
		gestoreEntita.aggiungiEntita(new Sbarra(h, 600, 128, true)); // indice 2
		gestoreEntita.aggiungiEntita(new InterruttorePressione(h, 1000, 150, 
				new Funzione(gestoreEntita, 1, Funzionalita.CAMBIA_SBARRA),
				new Funzione(gestoreEntita, 1, Funzionalita.CAMBIA_SBARRA),
				new Funzione(gestoreEntita, 2, Funzionalita.CAMBIA_SBARRA)
				));
		
		((InterruttorePressione)gestoreEntita.getEntita().get(3)).getFunzione()[0].setFunzione(
			new Funzione(gestoreEntita, 3, Funzionalita.DISATTIVA_INTERRUTTORE));
		
		gestoreEntita.aggiungiEntita(new Teletrasporto(h, 1220, 500, 50,70));
		gestoreEntita.aggiungiEntita(new Sbarra(h, 704, 448+Tile.TILE_ALTEZZA, false)); //5
		
		gestoreEntita.aggiungiEntita(new Sbarra(h, 320, 1023, false));
		gestoreEntita.aggiungiEntita(new Sbarra(h, 768, 1540, true));
		gestoreEntita.aggiungiEntita(new Sbarra(h, Tile.TILE_LARGHEZZA*25, Tile.TILE_ALTEZZA*10, false));
		gestoreEntita.aggiungiEntita(new Sbarra(h, Tile.TILE_LARGHEZZA*20, Tile.TILE_ALTEZZA*28, false));
		gestoreEntita.aggiungiEntita(new Sbarra(h, Tile.TILE_LARGHEZZA*23, Tile.TILE_ALTEZZA*10, true));//10
		
		gestoreEntita.aggiungiEntita(new InterruttorePressione(h, Tile.TILE_LARGHEZZA*2, Tile.TILE_ALTEZZA*14, 
				new Funzione(gestoreEntita, 9, Funzionalita.CAMBIA_SBARRA),
				new Funzione(gestoreEntita, 7, Funzionalita.CAMBIA_SBARRA)
				));
		gestoreEntita.aggiungiEntita(new InterruttorePressione(h, Tile.TILE_LARGHEZZA*3, Tile.TILE_ALTEZZA*22,
				new Funzione(gestoreEntita, 8, Funzionalita.CHIUDI_SBARRA)
				));
		gestoreEntita.aggiungiEntita(new InterruttorePressione(h, Tile.TILE_LARGHEZZA*13, Tile.TILE_ALTEZZA*26, 
				new Funzione(gestoreEntita, 6, Funzionalita.APRI_SBARRA)
				));
		gestoreEntita.aggiungiEntita(new InterruttorePressione(h, Tile.TILE_LARGHEZZA*24, Tile.TILE_ALTEZZA*26, 
				new Funzione(gestoreEntita, 8, Funzionalita.CAMBIA_SBARRA),
				new Funzione(gestoreEntita, 10, Funzionalita.CAMBIA_SBARRA)
				));
		gestoreEntita.aggiungiEntita(new Sbarra(h, Tile.TILE_LARGHEZZA*24, Tile.TILE_ALTEZZA*13, false));
		gestoreEntita.aggiungiEntita(new Caramella(h, Tile.TILE_LARGHEZZA*25, Tile.TILE_ALTEZZA*25));
		gestoreEntita.aggiungiEntita(new Caramella(h, Tile.TILE_LARGHEZZA*24, Tile.TILE_ALTEZZA*10));
		
		
		
		
	}
	
}
