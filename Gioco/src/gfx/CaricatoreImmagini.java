package gfx;

import java.awt.image.BufferedImage;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * Oggetto utile per il caricamento di immagini.
 */

public class CaricatoreImmagini {
	
	/**
	 * Carica l'immagine da file.
	 * @param path il percorso (path) del file.
	 * @return l'immagine.
	 */
	public static BufferedImage caricaImmagine(String path){
		
		try {
			return ImageIO.read(new File(path));
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("errore");
			System.exit(0);
		}
		return null;
	}
	
}
