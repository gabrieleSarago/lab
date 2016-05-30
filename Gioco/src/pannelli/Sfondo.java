package pannelli;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import gioco.Handler;
/**
 * Crea l'oggetto Sfondo per gli stati.
 */
public class Sfondo {
	

	private BufferedImage sfondo;
	private Handler h;
	
	private double x;
	private double y;
	private double dx;
	private double dy;
	
	/**
	 * Costruisce l'oggetto Sfondo.
	 * @param s il nome del file che contiene lo Sfondo.
	 * @param h oggetto Handler utile nella gestione con altre classi.
	 */
	public Sfondo (String s, Handler h){
		try {
			sfondo = ImageIO.read(new File(s));
		}catch (Exception e){
			e.printStackTrace();
		}
		this.h = h;
	}
	
	public Sfondo(BufferedImage img, Handler h){
		this.h = h;
		sfondo = img;
	}
	
	public void setPosizione(double x,double y){
		this.x = (x) % h.getLarghezza();
		this.y = (y) % h.getAltezza();
	}
	
	public void setVector(double dx, double dy){
		this.dx = dx;
		this.dy = dy;
	}
	
	public void aggiorna () {
		x += dx;
		y += dy;
		if(x <0 && Math.abs(x) >= sfondo.getWidth()+h.getLarghezza())
			x += sfondo.getWidth();
		if(x > 0 && Math.abs(x) >= sfondo.getWidth())
			x -= sfondo.getWidth();
	}
	
	public void disegna (Graphics g) {
		g.drawImage(sfondo, (int) x, (int) y, null);
		if(x <0 && Math.abs(x)>=h.getLarghezza()){
			g.drawImage(sfondo, (int) x + sfondo.getWidth(), (int) y, null);
		}
		if (x > 0)
			g.drawImage(sfondo, (int) x - sfondo.getWidth(), (int) y, null);
	}
	
	public BufferedImage getSfondo(){
		return sfondo;
	}
	
	public double getY(){
		return y;
	}
	
	public double getX(){
		return x;
	}
	
}
