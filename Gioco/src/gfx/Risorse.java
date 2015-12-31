package gfx;

import java.awt.image.BufferedImage;

public class Risorse {
	
	private static final int size = 38;
	//private static final int lifeh = 15, lifew = 96;
	public static BufferedImage strada, muro, sink_sopra_fermo, caramella, trofeo, ok;
	public static BufferedImage [] voci_pausa, voci_pausa_off;// tempo;
	public static BufferedImage[] sink_sotto, sink_sopra, sink_sinistra, sink_destra;
	public static BufferedImage[] sink_sotto_fermo, sink_sinistra_fermo, sink_destra_fermo;
	
	public static void inizializza(){
		Sprite s = new Sprite(CaricatoreImmagini.caricaImmagine("res/img/sink.png"));
		
		sink_sotto = new BufferedImage[10];
		sink_sopra = new BufferedImage[10];
		sink_sinistra = new BufferedImage[10];
		sink_destra = new BufferedImage[10];
		sink_sotto_fermo = new BufferedImage[3];
		sink_sinistra_fermo = new BufferedImage[3];
		sink_destra_fermo = new BufferedImage[3];
		
		for(int i = 0; i < 10; i++){
			sink_sotto[i] = s.prendiSprite(3+i * 37, 166, size, size);
		}
		for(int i = 0; i < 10; i++){
			sink_sopra[i] = s.prendiSprite(3+i * 37, 246, size, size);
		}
		for(int i = 0; i < 10; i++){
			int x = 0;
			if(i == 9 || i == 8)
				x = 3;
			if(i == 6) x = 4;
			if(i == 1) x = 1;
			sink_sinistra[i] = s.prendiSprite(x+i * 37, 206, size, size);
		}
		for(int i = 0; i < 10; i++){
			int x = 0;
			if(i == 9 || i == 8)
				x = 3;
			if(i == 7) x = 4;
			if(i == 5) x = 5;
			sink_destra[i] = s.prendiSprite(x+i * 37, 287, size, size);
		}
		for(int i = 0; i < 3; i++){
			int x = 0;
			if(i == 2) x -= 1;
			sink_sotto_fermo[i] = s.prendiSprite(x+i * size, 4, size, size);
		}
		for(int i = 0; i < 3; i++){
			int x = 3;
			if(i == 2) x -= 1;
			sink_sinistra_fermo[i] = s.prendiSprite(x+i*size, 46, size, size);
		}
		for(int i = 0; i < 3; i++){
			int x = 0;
			if(i == 1) x -= 2;
			if(i == 2) x -= 1;
			sink_destra_fermo[i] = s.prendiSprite(x+i*size, 128, size-1, size-1);
		}
		
		sink_sopra_fermo = s.prendiSprite(3, 246, size, size);
		
		voci_pausa = new BufferedImage[4];
		voci_pausa_off = new BufferedImage[4];

		Sprite s1 = new Sprite(CaricatoreImmagini.caricaImmagine("res/img/voci_pausa.png"));
		Sprite s2 = new Sprite(CaricatoreImmagini.caricaImmagine("res/img/voci_pausa_off.png"));

		voci_pausa[0] = s1.prendiSprite(0, 0, 250, 40);
		voci_pausa[1] = s1.prendiSprite(0, 63, 215, 40);
		voci_pausa[2] = s1.prendiSprite(0, 128, 177, 40);
		voci_pausa[3] = s1.prendiSprite(0, 190, 404, 40);
		ok = s1.prendiSprite(0, 128, 290, 40);

		voci_pausa_off[0] = s2.prendiSprite(0, 0, 250, 40);
		voci_pausa_off[1] = s2.prendiSprite(0, 63, 215, 40);
		voci_pausa_off[2] = s2.prendiSprite(0, 128, 177, 40);
		voci_pausa_off[3] = s2.prendiSprite(0, 190, 404, 40);
		
		/*
		Sprite t = new Sprite(CaricatoreImmagini.caricaImmagine("res/img/life.png"));
		
		tempo = new BufferedImage[11];
		
		for(int i = 0; i<11; i++)
			tempo[i] =  t.prendiSprite(0, lifeh*i, lifew, lifeh);
		*/
		
		strada = s.prendiSprite(116, 7, 32, 32);
		muro = s.prendiSprite(116+32, 7, 32, 32);
		
		Sprite c = new Sprite(CaricatoreImmagini.caricaImmagine("res/img/caramella.png"));
		caramella = c.prendiSprite(0, 0, 40, 29);
		
		Sprite d = new Sprite(CaricatoreImmagini.caricaImmagine("res/img/Trofeo_d'oro.png"));
		trofeo = d.prendiSprite(0, 0, 40, 40);
		
	}

}
