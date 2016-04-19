package livelli;

import java.awt.Graphics;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList; //eliminato per il gestore entita




import personaggio.Personaggio;
import personaggio.Sink;
import entita.Entita;
//import entita.array_entita;
import entita_statiche.Caramella;
import entita_statiche.InterruttorePressione;
import entita_statiche.Sbarra;
import entita_statiche.Teletrasporto;
//import entita_statiche.EntitaStatica; //eliminato per il gestore entita
import entita_statiche.Trofeo;
import entita_statiche.funzione.Funzionalita;
import entita_statiche.funzione.Funzione;
import gfx.Suono;
import gioco.Handler;
import stati.StatoGioco;
import tiles.Tile;
import utils.Utils;


// TO-DO: permettere il salvataggio dell' entità in un file di testo
public class Livello {
	
	private Handler h;
	private int larghezza, altezza;
	public int sinkX, sinkY;
	private int [][] tiles;
	public int tempo;
	private ArrayList<Entita> array_entita;
	private Entita ultimaCollisione_sink;
	//private Sink sink;
	
	//----gestore entita 
	//private array_entita array_entita;
	
	
	
	public Livello (Handler h, String path){
		this.h = h;
		array_entita = new  ArrayList<Entita>();
		array_entita.add(new Sink(h, 75,75,100));
		//---- gestore
		//array_entita = new array_entita(h, new Sink(h,  sinkX, sinkY, tempo));
		//temporaneo
		
		caricaEntita();
		
		carica(path);
		
		array_entita.get(0).setX(sinkX);
		array_entita.get(0).setY(sinkY);
		((Sink)array_entita.get(0)).setTempo(tempo);
		
	}
	
	public void aggiorna(){	
		for(int i=0; i < array_entita.size(); i++)
		{
			Entita e = array_entita.get(i);
			e.aggiorna();
			if(e instanceof Personaggio )
			{
				gestisciCollisioni((Personaggio)e);
			}
		}
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
		//array_entita.disegna(g);
		for(Entita e : array_entita)
		{
			e.disegna(g);
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
		
		// ---- entitaStatiche = new ArrayList<>(); eliminato
		int c = 5;
		while(Utils.parseInt(tokens[c])!= 0000){
			//----# entitaStatiche.add(new Caramella(h, Utils.parseInt(tokens[c]),Utils.parseInt(tokens[c+1])));
			array_entita.add(new Caramella(h, Utils.parseInt(tokens[c]),Utils.parseInt(tokens[c+1])));
			
			c += 2;
		}
		c++;
		array_entita.add(new Trofeo(h, 1750, 689));
		
		tiles = new int[larghezza][altezza];
		for (int y = 0; y < altezza; y++)
			for (int x = 0; x < larghezza; x++)
				tiles[x][y] = Utils.parseInt(tokens[(x + (y * larghezza)) + c]);
		
		// TO-DO: il percorso non deve esssere fisso
		try{
			supportoCarica(path);
		}catch(Exception e){e.printStackTrace();}
		
	}
	private void supportoCarica(String path) throws FileNotFoundException, IOException 
	{
			ObjectInputStream in;		
			in = new ObjectInputStream(new FileInputStream(path+"1"));
			Entita en = null;
			for(;;){
			try{	
			en = (Entita) in.readObject();
			System.out.println("carico");
			if(en == null) System.out.println("nullo");
			if(en instanceof InterruttorePressione) System.out.println("interruttore");
			if(en instanceof Sink) System.out.println("Sink");
			if(en instanceof Sbarra) System.out.println("Sbarra");
			en.setHandler(h);
			array_entita.add(en);
			}catch (ClassNotFoundException e1){
				e1.printStackTrace();
				throw new IOException();
			}catch(ClassCastException e2){
				e2.printStackTrace();
				throw new IOException();
			}catch(EOFException e3){break;}
			}//fine for
			in.close();
			
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
			for(Entita c: array_entita){
				
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
		
		try{
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file+"1"));
			for(Entita c: array_entita){
				if(c == null) System.out.println("nullo salvataggio");
				if(c instanceof InterruttorePressione) System.out.println("interruttore salvataggio");
				if(c instanceof Sink) System.out.println("Sink salvataggio");
				if(c instanceof Sbarra) System.out.println("Ssbarra salvataggio");
				oos.writeObject(c);
			}
			oos.close();
			}catch(IOException e){e.printStackTrace();}
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
	//la prima entia ad essere aggiunta è sink
	public Sink getSink()
	{
		return ((Sink)array_entita.get(0));
	}

	//----
	private void caricaEntita()
	{	
		array_entita.add(new Sbarra(h, 256, 448, false)); // indice 0
		array_entita.add(new Sbarra(h, 600, 128, true)); // indice 1
		array_entita.add(new InterruttorePressione(h, 1000, 150, 
				new Funzione(array_entita, 1, Funzionalita.CAMBIA_SBARRA),
				new Funzione(array_entita, 1, Funzionalita.CAMBIA_SBARRA),
				new Funzione(array_entita, 2, Funzionalita.CAMBIA_SBARRA)
				));
		
		((InterruttorePressione)array_entita.get(3)).getFunzione()[0].setFunzione(
			new Funzione(array_entita, 3, Funzionalita.DISATTIVA_INTERRUTTORE));
		/*array_entita.add(new Sbarra(h, 256, 448, false)); // indice 0
		array_entita.add(new Sbarra(h, 600, 128, true)); // indice 1
		array_entita.add(new InterruttorePressione(h, 1000, 150, 
				new Funzione(array_entita, 1, Funzionalita.CAMBIA_SBARRA),
				new Funzione(array_entita, 1, Funzionalita.CAMBIA_SBARRA),
				new Funzione(array_entita, 2, Funzionalita.CAMBIA_SBARRA)
				));
		
		((InterruttorePressione)array_entita.get(3)).getFunzione()[0].setFunzione(
			new Funzione(array_entita, 3, Funzionalita.DISATTIVA_INTERRUTTORE));
		
		array_entita.add(new Teletrasporto(h, 1220, 500, 50,70));
		array_entita.add(new Sbarra(h, 704, 448+Tile.TILE_ALTEZZA, false)); //5
		
		array_entita.add(new Sbarra(h, 320, 1023, false));
		array_entita.add(new Sbarra(h, 768, 1540, true));
		array_entita.add(new Sbarra(h, Tile.TILE_LARGHEZZA*25, Tile.TILE_ALTEZZA*10, false));
		array_entita.add(new Sbarra(h, Tile.TILE_LARGHEZZA*20, Tile.TILE_ALTEZZA*28, false));
		array_entita.add(new Sbarra(h, Tile.TILE_LARGHEZZA*23, Tile.TILE_ALTEZZA*10, true));//10
		
		array_entita.add(new InterruttorePressione(h, Tile.TILE_LARGHEZZA*2, Tile.TILE_ALTEZZA*14, 
				new Funzione(array_entita, 9, Funzionalita.CAMBIA_SBARRA),
				new Funzione(array_entita, 7, Funzionalita.CAMBIA_SBARRA)
				));
		array_entita.add(new InterruttorePressione(h, Tile.TILE_LARGHEZZA*3, Tile.TILE_ALTEZZA*22,
				new Funzione(array_entita, 8, Funzionalita.CHIUDI_SBARRA)
				));
		array_entita.add(new InterruttorePressione(h, Tile.TILE_LARGHEZZA*13, Tile.TILE_ALTEZZA*26, 
				new Funzione(array_entita, 6, Funzionalita.APRI_SBARRA)
				));
		array_entita.add(new InterruttorePressione(h, Tile.TILE_LARGHEZZA*24, Tile.TILE_ALTEZZA*26, 
				new Funzione(array_entita, 8, Funzionalita.CAMBIA_SBARRA),
				new Funzione(array_entita, 10, Funzionalita.CAMBIA_SBARRA)
				));
		array_entita.add(new Sbarra(h, Tile.TILE_LARGHEZZA*24, Tile.TILE_ALTEZZA*13, false));
		array_entita.add(new Caramella(h, Tile.TILE_LARGHEZZA*25, Tile.TILE_ALTEZZA*25));
		array_entita.add(new Caramella(h, Tile.TILE_LARGHEZZA*24, Tile.TILE_ALTEZZA*10));*/
		}
	
	public void gestisciCollisioni(Personaggio p)
	{
		if(p.getUltimaEntita() == null) {
			ultimaCollisione_sink = null;
			return;
		}
		if(p instanceof Sink){
			collisioniConSink((Sink) p);
		}
		p.setUltimaEntita(null);
		
	}
	
	private void collisioniConSink(Sink s)
	{
		if(s.getUltimaEntita() instanceof Caramella){
			s.setTempo(s.getTempo()+5);
			array_entita.remove(s.getUltimaEntita());
			try {
				h.getSuono().riproduci(Suono.suoni.CARAMELLA);
			} catch (Exception e) {
				e.printStackTrace();}
		}
		if(s.getUltimaEntita() instanceof Trofeo) {
			((StatoGioco)h.getGioco().getStato()).setVittoria(true);
			
		}
		
		if(s.getUltimaEntita() instanceof InterruttorePressione)
			if(!(ultimaCollisione_sink instanceof InterruttorePressione) || 
					!(ultimaCollisione_sink.equals(s.getUltimaEntita()))) 
				((InterruttorePressione)s.getUltimaEntita()).funzione(h);
		
		if(s.getUltimaEntita() instanceof Teletrasporto){
				s.setX(((Teletrasporto)s.getUltimaEntita()).getDestinazioneX());
				s.setY(((Teletrasporto)s.getUltimaEntita()).getDestinazioneY());
		}
		
		ultimaCollisione_sink = s.getUltimaEntita();
	}
	
	public ArrayList<Entita> getArray_entita(){
		return array_entita;
	}
	
	
	
}
