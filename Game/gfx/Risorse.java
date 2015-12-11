package gfx;

import java.awt.image.BufferedImage;

public class Risorse {
	
	private static final int larghezza = 32, altezza = 32;
	
	public static BufferedImage strada, muro, sink_sopra_fermo;
	public static BufferedImage[] sink_sotto, sink_sopra, sink_sinistra, sink_destra;
	public static BufferedImage[] sink_sotto_fermo, sink_sinistra_fermo, sink_destra_fermo;
	
	
	public static void inizializza(){
		Sprite s = new Sprite(CaricatoreImmagini.caricaImmagine("res/img/Risorse.png"));
		
		sink_sotto = new BufferedImage[10];
		sink_sopra = new BufferedImage[10];
		sink_sinistra = new BufferedImage[10];
		sink_destra = new BufferedImage[10];
		sink_sotto_fermo = new BufferedImage[3];
		sink_sinistra_fermo = new BufferedImage[3];
		sink_destra_fermo = new BufferedImage[3];
		
		for(int i = 0; i < 10; i++){
			sink_sotto[i] = s.prendiSprite(i * larghezza, 4 * altezza, larghezza, altezza);
		}
		for(int i = 0; i < 10; i++){
			sink_sopra[i] = s.prendiSprite(i * larghezza, 6 * altezza, larghezza, altezza);
		}
		for(int i = 0; i < 10; i++){
			int x=0;
			if (i == 1)
				x=1;
			sink_sinistra[i] = s.prendiSprite(i * larghezza + x, 5 * altezza + 1, larghezza - x, altezza - 1);
		}
		for(int i = 0; i < 10; i++){
			int x=0;
			if (i == 9)
				x=1;
			sink_destra[i] = s.prendiSprite(i * larghezza + x, 7 * altezza, larghezza - x, altezza);
		}
		for(int i = 0; i < 3; i++){
			sink_sotto_fermo[i] = s.prendiSprite(i * larghezza, 0, larghezza, altezza);
		}
		for(int i = 0; i < 3; i++){
			sink_sinistra_fermo[i] = s.prendiSprite(i * larghezza, altezza + 1, larghezza, altezza - 1);
		}
		for(int i = 0; i < 3; i++){
			int x=0;
			if (i == 1)
				x=2;
			sink_destra_fermo[i] = s.prendiSprite(i * larghezza - x, 3 * altezza + 1, larghezza, altezza);
		}
		
		sink_sopra_fermo = s.prendiSprite(0, 2 * altezza, larghezza, altezza);
		
		strada = s.prendiSprite(3 * larghezza, 0, larghezza, altezza);
		muro = s.prendiSprite(4 * larghezza, 0, larghezza, altezza);
		
	}

}
