package gfx;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

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
			File f = new File(path);
			if(!f.exists()){
				f = new File("/"+path);
			}
			InputStream input = new FileInputStream(f);
			//return ImageIO.read(CaricatoreImmagini.class.getResourceAsStream(path));
			return ImageIO.read(input);
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println(path);
			System.out.println("errore");
			System.exit(0);
		}
		return null;
	}
	
}
