package pannelli;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import gioco.Handler;

public class Sfondo {
	
	private BufferedImage sfondo;
	private Handler h;
	
	private double x;
	private double y;
	private double dx;
	private double dy;
	
	public Sfondo (String s, Handler h){
		try {
			sfondo = ImageIO.read(new File(s));
		}catch (Exception e){
			e.printStackTrace();
		}
		this.h = h;
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
	}
	
	public void disegna (Graphics g) {
		g.drawImage(sfondo, (int) x, (int) y, null);
		if(x < 0)
			g.drawImage(sfondo, (int) x + h.getLarghezza(), (int) y, null);
		if (x > 0)
			g.drawImage(sfondo, (int) x - h.getAltezza(), (int) y, null);
	}
}
