package livelli;

import java.awt.Graphics;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Comparator;

import entita.Entita;
import entita_statiche.Caramella;
import entita_statiche.InterruttoreInvisibile;
import entita_statiche.InterruttorePressione;
import entita_statiche.Teletrasporto;
import entita_statiche.Trofeo;
import gfx.Suono;
import gioco.Handler;
import personaggio.Nemico;
import personaggio.Personaggio;
import personaggio.Sink;
import stati.StatoGioco;
import tiles.Tile;
import utils.Utils;

/**
 * Crea l'oggetto Livello.
 * Vengono salvati mediante dei numeri id le entità da inserire.
 * Il livello viene caricato da un file di interi.
 */

public class Livello {
	
	private Handler h;
	private int larghezza, altezza;
	private int [][] tiles;
	private ArrayList<Entita> array_entita;
	private Comparator<Entita> ordineDisegno = new Comparator<Entita>(){
		@Override
		public int compare(Entita a, Entita b) {
			if(a.getY()+ a.getAltezza() <= b.getY()+b.getAltezza()) return -1;
			return 1;
		}
		
	};
	private Entita ultimaCollisione_sink;
	
	
	/**
	 * Costruisce l'oggetto livello.
	 * @param h oggetto handler utile per la gestione con le altre classi.
	 * @param path nome del file da cui viene caricato il livello.
	 */
	public Livello (Handler h, String path){
		this.h = h;
		carica(path);
	}
	
	/**
	 * Aggiorna le dinamiche del livello.
	 */
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
		
		int xStart = (int) Math.max(0, h.getCameraGioco().getxOffset() / Tile.TILE_LARGHEZZA);
		int xEnd = (int) Math.min(larghezza, (h.getCameraGioco().getxOffset() + h.getLarghezza())/Tile.TILE_LARGHEZZA +1);
		int yStart = (int) Math.max(0, h.getCameraGioco().getyOffset() / Tile.TILE_ALTEZZA);
		int yEnd = (int) Math.min(altezza, (h.getCameraGioco().getyOffset() + h.getLarghezza())/Tile.TILE_ALTEZZA +1);
		
		for (int y =  yStart; y < yEnd; y++)
			for(int x = xStart; x < xEnd ; x++){
				getTile(x,y).disegna(g, (int) (x * Tile.TILE_LARGHEZZA - h.getCameraGioco().getxOffset()),
						(int) (y * Tile.TILE_ALTEZZA - h.getCameraGioco().getyOffset()));
			}
		
		array_entita.sort(ordineDisegno);
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
	
	
	/**
	 * Carica il ivello da file.
	 * @param path il nome del file.
	 */
	
	@SuppressWarnings("unchecked")
	private void carica(String path){
		
		array_entita = new  ArrayList<Entita>();
		String file = Utils.caricaFileComeString("res/livelli/livello1.txt");
		String[] tokens = file.split("\\s+");
		larghezza = Utils.parseInt(tokens[0]);
		altezza = Utils.parseInt(tokens[1]);
		int c = 3;
		tiles = new int[larghezza][altezza];
		for (int y = 0; y < altezza; y++)
			for (int x = 0; x < larghezza; x++)
				tiles[x][y] = Utils.parseInt(tokens[(x + (y * larghezza)) + c]);
		
		array_entita.clear(); //per sicurezza
		
		try{
			File f = new File(path+"1");
			if(!f.exists()){
				path = "res/livelli/livello1.txt";
			}
			ObjectInputStream in = new ObjectInputStream(new FileInputStream(path+"1"));
			array_entita = (ArrayList<Entita>) in.readObject();
			//handler cambia ogni volta che si avvia il gioco quindi bisogna
			//settare ogni entita con l' handler corrente
			for(int i=0; i < array_entita.size(); i++)
				array_entita.get(i).setHandler(h);
			in.close();
		}catch(Exception e){e.printStackTrace();}
		
	}

	/**
	 * Salva le dinamiche correnti del livello su un file.
	 * Utile quando si vuole caricare una partita.
	 * @param file file su cui salvare il livello.
	 */
	public void salva(String file){
		File f = new File(file);
		f.delete();

		try{
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file+"1"));
			oos.writeObject(array_entita);
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
		return getSink().getTempo();
	}

	public void setTempo(int tempo) {
		if(!(array_entita.get(0) instanceof Sink)) 
			throw new RuntimeException("aspettato Sink");
		((Sink)array_entita.get(0)).setTempo(tempo);
		
	}
	//la prima entia ad essere aggiunta è sink
	public Sink getSink()
	{
		for(int i=0; i < array_entita.size(); i++)
			if(array_entita.get(i) instanceof Sink)
				return ((Sink)array_entita.get(i));
		System.out.println("sink non trovato");
		return null;
	}
	
	public void gestisciCollisioni(Personaggio p)
	{
		if(p instanceof Sink)
			if(p.getUltimaEntita() == null) {
			ultimaCollisione_sink = null;
			return;
		} else	collisioniConSink((Sink) p);	
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
		
		if(s.getUltimaEntita() instanceof Teletrasporto && ((Teletrasporto)s.getUltimaEntita()).eAttivo()){
			s.setX(((Teletrasporto)s.getUltimaEntita()).getDestinazioneX());
			s.setY(((Teletrasporto)s.getUltimaEntita()).getDestinazioneY());
		}
		
		if(s.getUltimaEntita() instanceof Nemico && ((Nemico)s.getUltimaEntita()).eVivo())
			s.setTempo(s.getTempo()-1);
		
		if(s.getUltimaEntita() instanceof InterruttoreInvisibile)
			if(!(ultimaCollisione_sink instanceof InterruttoreInvisibile) || 
						!(ultimaCollisione_sink.equals(s.getUltimaEntita())))
					((InterruttoreInvisibile)s.getUltimaEntita()).funzione(h);
	
		ultimaCollisione_sink = s.getUltimaEntita();
	}
	
	public ArrayList<Entita> getArray_entita(){
		return array_entita;
	}
		
}
