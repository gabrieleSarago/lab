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
import java.util.Comparator;

import entita.Entita;
//import entita.array_entita;
import entita_statiche.Caramella;
import entita_statiche.InterruttoreInvisibile;
import entita_statiche.InterruttorePressione;
import entita_statiche.Sbarra;
import entita_statiche.Teletrasporto;
//import entita_statiche.EntitaStatica; //eliminato per il gestore entita
import entita_statiche.Trofeo;
import entita_statiche.funzione.Funzionalita;
import entita_statiche.funzione.Funzione;
import gfx.Suono;
import gioco.Handler;
import personaggio.Nemico;
import personaggio.Personaggio;
import personaggio.Sink;
import stati.StatoGioco;
import tiles.Tile;
import utils.Utils;


// TO-DO: permettere il salvataggio dell' entità in un file di testo
public class Livello {
	
	private Handler h;
	private int larghezza, altezza;
	//public int sinkX, sinkY;
	private int [][] tiles;
	//public int tempo;
	private ArrayList<Entita> array_entita;
	private Comparator<Entita> ordineDisegno = new Comparator<Entita>(){
		@Override
		public int compare(Entita a, Entita b) {
			if(a.getY()+ a.getAltezza() <= b.getY()+b.getAltezza()) return -1;
			return 1;
		}
		
	};
	private Entita ultimaCollisione_sink;
	//private Sink sink;
	
	//----gestore entita 
	//private array_entita array_entita;
	
	
	
	public Livello (Handler h, String path){
		this.h = h;
		carica(path);
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
	
	private void carica(String path){
		
		array_entita = new  ArrayList<Entita>();
		
		String file = Utils.caricaFileComeString(path);
		String[] tokens = file.split("\\s+");
		larghezza = Utils.parseInt(tokens[0]);
		altezza = Utils.parseInt(tokens[1]);
		int c = 3;
		tiles = new int[larghezza][altezza];
		for (int y = 0; y < altezza; y++)
			for (int x = 0; x < larghezza; x++)
				tiles[x][y] = Utils.parseInt(tokens[(x + (y * larghezza)) + c]);
		
		// TODO: il percorso non deve esssere fisso
		array_entita.clear(); //per sicurezza
		/*try{
			supportoCarica(path);
		}catch(Exception e){e.printStackTrace();}*/
		array_entita.add(new Sink(h, 75, 75, 200));
		//array_entita.add(new Nemico(h, 9*Tile.TILE_LARGHEZZA, 2*Tile.TILE_ALTEZZA));
		
	}
	private void supportoCarica(String path) throws FileNotFoundException, IOException 
	{
			ObjectInputStream in;		
			in = new ObjectInputStream(new FileInputStream(path+"1"));
			Entita en = null;
			for(;;){
			try {	
				en = (Entita) in.readObject();
				en.setHandler(h);
				array_entita.add(en);
				}catch (ClassNotFoundException e1){
				e1.printStackTrace();
				in.close();
				throw new IOException();
				}catch(ClassCastException e2){
				e2.printStackTrace();
				in.close();
				throw new IOException();
				}catch(EOFException e3){break;}
			}//fine for	
			in.close();
	}
	public void salva(String file){
		File f = new File(file);
		f.delete();
		PrintWriter pw;
		try {
			pw = new PrintWriter(new FileWriter(file));
			pw.write(tiles.length + " " + tiles[0].length+"\n");
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
		// utile se si vuole aggiungere/modificare/eliminare entita
		/*array_entita.clear();
		array_entita.add(new Sink(h, 75, 75, 100));
		caricaEntita();*/
		try{
			//"res/livelli/livello1.txt1"
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file+"1"));
			for(Entita c: array_entita){
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
		//return tempo;
		//da per scontato che Sink è il primo elemento dell' array
		return getSink().getTempo();
	}

	public void setTempo(int tempo) {
		//this.tempo = tempo;
		if(!(array_entita.get(0) instanceof Sink)) throw new RuntimeException("aspettato Sink");
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

	//utile per aggiungere/modificare/eliminare entita
	//eliminare a prodotto finito
	private void caricaEntita(){
		// primo quadrante
		array_entita.add(new Sbarra(h, Tile.TILE_LARGHEZZA*6, Tile.TILE_ALTEZZA*4, true)); //false
		array_entita.add(new Sbarra(h, Tile.TILE_LARGHEZZA*13, Tile.TILE_ALTEZZA*3, true));
		array_entita.add(new Sbarra(h, Tile.TILE_LARGHEZZA*15, Tile.TILE_ALTEZZA*3, true));
		array_entita.add(new Sbarra(h, Tile.TILE_LARGHEZZA*2, Tile.TILE_ALTEZZA*4, true));//false
		array_entita.add(new InterruttorePressione(h, Tile.TILE_LARGHEZZA*10, Tile.TILE_ALTEZZA*2, 
				new Funzione(array_entita, 1, Funzionalita.CAMBIA_SBARRA),
				new Funzione(array_entita, 3, Funzionalita.CAMBIA_SBARRA)
				));
		array_entita.add(new InterruttorePressione(h, Tile.TILE_LARGHEZZA*2, Tile.TILE_ALTEZZA*6, 
				new Funzione(array_entita, 2, Funzionalita.CAMBIA_SBARRA),
				new Funzione(array_entita, 4, Funzionalita.CAMBIA_SBARRA)
		));
		
		// secondo quadrante
		array_entita.add(new Sbarra(h, Tile.TILE_LARGHEZZA*18, Tile.TILE_ALTEZZA*18, false)); //7 false
		array_entita.add(new Sbarra(h, Tile.TILE_LARGHEZZA*20, Tile.TILE_ALTEZZA*18, false));// false
		array_entita.add(new Sbarra(h, Tile.TILE_LARGHEZZA*22, Tile.TILE_ALTEZZA*18, false));// false
		array_entita.add(new Sbarra(h, Tile.TILE_LARGHEZZA*24, Tile.TILE_ALTEZZA*18, false));// false
		array_entita.add(new Teletrasporto(h, Tile.TILE_LARGHEZZA*26, Tile.TILE_ALTEZZA*2,
				Tile.TILE_LARGHEZZA*26, Tile.TILE_ALTEZZA*24, false));
		array_entita.add(new InterruttorePressione(h, Tile.TILE_LARGHEZZA*21, Tile.TILE_ALTEZZA*4, 
				new Funzione(array_entita, 10, Funzionalita.APRI_SBARRA),
				new Funzione(array_entita, 11, Funzionalita.ATTIVA_TELETRASPORTO)
				));
		array_entita.add(new InterruttorePressione(h, Tile.TILE_LARGHEZZA*24, Tile.TILE_ALTEZZA*10, 
				new Funzione(array_entita, 9, Funzionalita.CAMBIA_SBARRA)
		));
		
		array_entita.add(new Nemico(h, 15*Tile.TILE_LARGHEZZA, 22*Tile.TILE_ALTEZZA, false)); //14
		array_entita.add(new Nemico(h, 15*Tile.TILE_LARGHEZZA, 24*Tile.TILE_ALTEZZA, false));
		array_entita.add(new Nemico(h, 15*Tile.TILE_LARGHEZZA, 27*Tile.TILE_ALTEZZA, false));//16
		array_entita.add(new InterruttoreInvisibile(h, Tile.TILE_LARGHEZZA*21, Tile.TILE_ALTEZZA*21,
				Tile.TILE_LARGHEZZA*2, Tile.TILE_ALTEZZA*8,
				new Funzione(array_entita, 14, Funzionalita.ATTIVA_NEMICO),
				new Funzione(array_entita, 15, Funzionalita.ATTIVA_NEMICO),
				new Funzione(array_entita, 16, Funzionalita.ATTIVA_NEMICO)
				));
		array_entita.add(new Teletrasporto(h, Tile.TILE_LARGHEZZA*12, Tile.TILE_ALTEZZA*25, //18
				Tile.TILE_LARGHEZZA*11, Tile.TILE_ALTEZZA*15, false));
		array_entita.add(new InterruttorePressione(h, Tile.TILE_LARGHEZZA*13, Tile.TILE_ALTEZZA*23, 
				new Funzione(array_entita, 18, Funzionalita.ATTIVA_TELETRASPORTO)
		));
		
		array_entita.add(new Sbarra(h, Tile.TILE_LARGHEZZA*26, Tile.TILE_ALTEZZA*15, true)); // false
		array_entita.add(new InterruttorePressione(h, Tile.TILE_LARGHEZZA*17, Tile.TILE_ALTEZZA*15, 
				new Funzione(array_entita, 20, Funzionalita.APRI_SBARRA),
				new Funzione(array_entita, 7, Funzionalita.APRI_SBARRA),
				new Funzione(array_entita, 8, Funzionalita.APRI_SBARRA)
		));
		
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
