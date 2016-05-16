package stati;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
// import java.util.ArrayList; ora c � gestioneEntita

import classifica.Classifica;

import gfx.Suono;
// import entita.Entita; ora c � gestioneEntita
//import entita_statiche.Caramella;
//import entita_statiche.EntitaStatica;
//import entita_statiche.Trofeo;
import gioco.Handler;
import livelli.Livello;
import personaggio.Sink;

public class StatoGioco extends Stato {
	
	//----private Sink s;
	private Livello l;
	// ---private int x;
	//----private int y;
	private int tempo;
	//private ArrayList<EntitaStatica> entitaStatiche;	
	private boolean vittoria = false;
	private Classifica classifica = new Classifica();
	private Graphics g;
	private boolean riproduzione = true;
	private Suono suono;
	
	public StatoGioco(Handler h){
		super(h);
		l = new Livello(h, "res/livelli/livello1.txt");
		h.setLivello(l);
		//----x = l.sinkX;
		//----y = l.sinkY;
		tempo = l.getTempo();
		suono = h.getSuono();
		// ---- entitaStatiche = l.entitaStatiche;
		//----s = new Sink(h, x, y, tempo);
	}
	
	public StatoGioco(Handler h, String file){
		super(h);
		l = new Livello(h, file);
		h.setLivello(l);
		suono = h.getSuono();
		//----x = l.sinkX;
		//----y = l.sinkY;
		tempo = l.getTempo();
		//----entitaStatiche = l.entitaStatiche;
		//----s = new Sink(h, x, y, tempo);
	}

	@Override
	public void aggiorna() {
		
		tempo = l.getTempo();
		
		if(riproduzione){
			suono.riproduci(Suono.suoni.GIOCO);
			riproduzione = false;
		}
		//se si prende una caramella e il tempo � > 95 supera i 100 secondi.
		if(tempo > 100)
			tempo = 100; 
		if(!vittoria){
		if(!(h.getGioco().getPausa())){
			getInput();
			//controlla();
			l.aggiorna();
			if (vittoria){
				h.getSuono().getClipGioco().close();
				h.getGioco().setStato(new StatoVittoria(h, this.getUltimoScreen(), this.tempo));
			}
			if (tempo<=0){
				h.getSuono().getClipGioco().close();
				h.getGioco().setStato(new StatoSconfitta(h, this.getUltimoScreen()));
			}
		}
		}
			/* ---- eliminato se ne occupa gestioneEntita in livello
			for(int i = 0; i < entitaStatiche.size(); i++){
				EntitaStatica c = entitaStatiche.get(i);
				c.aggiorna();
			}
			s.aggiorna();
			if (s.getTempo()<0){
				h.setStato(new StatoMenu(h));
			}*/
	
		
			/*h.getGioco().setPausa(true);
			String input;
			int i = JOptionPane.showConfirmDialog(null, "Vuoi salvare il tuo punteggio?");
			if(i==JOptionPane.NO_OPTION) {
				h.getGioco().setStato(new StatoMenu(h));			}
			else if (i==JOptionPane.CANCEL_OPTION)
				JOptionPane.showMessageDialog(null, "Devi rispondere SI o NO.");
			else{
				try{
					input = JOptionPane.showInputDialog("Inserisci il tuo nominativo: ");
					classifica.add(new Nominativo(String.valueOf(tempo).toString(),input));
					classifica.salva("classificaPunteggio.txt");
					JOptionPane.showMessageDialog(null, "Punteggio salvato!");
					h.getGioco().setStato(new StatoMenu(h));
					h.getStato().aggiorna();
				}catch(Exception e){
					input = null;
				}
			}*/
		
	}

	@Override
	public void disegna(Graphics g) {
		l.disegna(g);
		
		//Disegna la barra del tempo.
		g.setColor(Color.black);
		g.setFont(new Font ("Arial", Font.BOLD,15));
		g.drawString(tempo+"", 180, 52);
		g.fillRect(68, 38, 104 , 14);
		g.setColor(Color.red);
		g.fillRect(70, 40, 100 , 10);
		g.setColor(Color.green);
		if(tempo>100)
			g.fillRect(70, 40, 100, 10);
		else
			g.fillRect(70, 40, tempo, 10);
		
		
	}
	
	private void getInput(){
		if(h.getGestioneInput().esc){
			h.getGestioneInput().keyReleased(h.getGestioneInput().getKeyEvent());
						
			h.getGioco().setPausa(true);
			h.getGioco().setStato(new StatoPausa(h, this));
			
			//Se si entra in pausa la musica del gioco si ferma e le risorse dei clip vengono liberate.
			//Tale operazione viene eseguita in seguito alla chiamata di StatoPausa per evitare il
			//ritardo nel momento in cui si entra in pausa.
			if(!suono.getMuto()){
				suono.getClipGioco().stop();
				suono.ferma();
			}
		}
	}
	
	public Sink getSink(){
		//----return s;
		return l.getSink();
		
	}
	
	
	//Converte l'oggetto Graphics in un BufferedImage,
	//in modo da poter essere stampato su schermo.
	public BufferedImage getUltimoScreen(){
		BufferedImage img = new BufferedImage(1200, 700, BufferedImage.TYPE_INT_RGB);
		g = img.createGraphics();
		disegna(g);
		g.dispose();
		return img;
	}
	/*---- probabilmente in gestione
	public void controlla(){
		for (int i = 0; i < entitaStatiche.size(); i++){
			Entita e = entitaStatiche.get(i);
			if ((e instanceof Caramella) && s.getX()<e.getX() && s.getX()>(e.getX()-(e.getLarghezza()/2)) &&
					s.getY()<e.getY() && s.getY()>(e.getY()-(e.getAltezza()/2))){
				s.setTempo(s.getTempo()+5);
				entitaStatiche.remove(e);
				}
			if ((e instanceof Trofeo) && s.getX()<e.getX() && s.getX()>(e.getX()-(e.getLarghezza()/2)) &&
					s.getY()<e.getY() && s.getY()>(e.getY()-(e.getAltezza()/2))){
				vittoria =true;
			}
	    }
	
    }*/
	// aggiunto
	public void setVittoria(boolean vittoria)
	{
		this.vittoria = vittoria;
	}
	
	public Suono getSuono(){
		return suono;
	}
	
}
