package gfx;

import java.awt.image.BufferedImage;

public class Risorse {
	
	private static final int larghezza = 32, altezza = 32;
	private static final int l = 29,a = 32;
	
	public static BufferedImage strada, muro, pietra;
	public static BufferedImage [] giocatore_down;
	public static BufferedImage [] giocatore_up;
	public static BufferedImage [] giocatore_right;
	public static BufferedImage [] giocatore_left;

	public static void inizializza(){
		Sprite o = new Sprite(CaricatoreImmagini.caricaImmagine("res/img/Test4.png"));
		Sprite g = new Sprite(CaricatoreImmagini.caricaImmagine("res/img/Sink.png"));
		Sprite s = new Sprite(CaricatoreImmagini.caricaImmagine("res/img/pietra.png"));
	
		giocatore_down = new BufferedImage[10];
		
		giocatore_down[0] = g.prendiSprite(0, a*4, l, a);
		giocatore_down[1] = g.prendiSprite(l, a*4, l, a);
		giocatore_down[2] = g.prendiSprite(l*2, a*4, l, a);
		giocatore_down[3] = g.prendiSprite(l*3, a*4, l, a);
		giocatore_down[4] = g.prendiSprite(l*4, a*4, l, a);
		giocatore_down[5] = g.prendiSprite(l*5, a*4, l, a);
		giocatore_down[6] = g.prendiSprite(l*6, a*4, l, a);
		giocatore_down[7] = g.prendiSprite(l*7, a*4, l, a);
		giocatore_down[8] = g.prendiSprite(l*8, a*4, l, a);
		giocatore_down[9] = g.prendiSprite(l*9, a*4, l, a);
		
		giocatore_up = new BufferedImage[10];
		
		giocatore_up[0] = g.prendiSprite(0, a*6, l, a);
		giocatore_up[1] = g.prendiSprite(l, a*6, l, a);
		giocatore_up[2] = g.prendiSprite(l*2, a*6, l, a);
		giocatore_up[3] = g.prendiSprite(l*3, a*6, l, a);
		giocatore_up[4] = g.prendiSprite(l*4, a*6, l, a);
		giocatore_up[5] = g.prendiSprite(l*5, a*6, l, a);
		giocatore_up[6] = g.prendiSprite(l*6, a*6, l, a);
		giocatore_up[7] = g.prendiSprite(l*7, a*6, l, a);
		giocatore_up[8] = g.prendiSprite(l*8, a*6, l, a);
		giocatore_up[9] = g.prendiSprite(l*9, a*6, l, a);
		
		giocatore_right = new BufferedImage[10];
		
		giocatore_right[0] = g.prendiSprite(0, a*7, l, a);
		giocatore_right[1] = g.prendiSprite(l, a*7, l, a);
		giocatore_right[2] = g.prendiSprite(l*2, a*7, l, a);
		giocatore_right[3] = g.prendiSprite(l*3, a*7, l, a);
		giocatore_right[4] = g.prendiSprite(l*4, a*7, l, a);
		giocatore_right[5] = g.prendiSprite(l*5, a*7, l, a);
		giocatore_right[6] = g.prendiSprite(l*6, a*7, l, a);
		giocatore_right[7] = g.prendiSprite(l*7, a*7, l, a);
		giocatore_right[8] = g.prendiSprite(l*8, a*7, l, a);
		giocatore_right[9] = g.prendiSprite(l*9, a*7, l, a);
		
		giocatore_left = new BufferedImage[10];
		
		giocatore_left[0] = g.prendiSprite(0, a*5, l, a);
		giocatore_left[1] = g.prendiSprite(l, a*5, l, a);
		giocatore_left[2] = g.prendiSprite(l*2, a*5, l, a);
		giocatore_left[3] = g.prendiSprite(l*3, a*5, l, a);
		giocatore_left[4] = g.prendiSprite(l*4, a*5, l, a);
		giocatore_left[5] = g.prendiSprite(l*5, a*5, l, a);
		giocatore_left[6] = g.prendiSprite(l*6, a*5, l, a);
		giocatore_left[7] = g.prendiSprite(l*7, a*5, l, a);
		giocatore_left[8] = g.prendiSprite(l*8, a*5, l, a);
		giocatore_left[9] = g.prendiSprite(l*9, a*5, l, a);
		
		strada = o.prendiSprite(larghezza, 0, larghezza, altezza);
		muro = o.prendiSprite(larghezza*2, 0, larghezza, altezza);
		pietra = s.prendiSprite(0, 0, 90, 90);
	}
}
