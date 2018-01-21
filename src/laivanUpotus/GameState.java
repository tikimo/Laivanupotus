package laivanUpotus;

import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.io.RandomAccessFile;

public class GameState {
	private static final Path bulletSave = Paths.get("bulletSave.txt");
	private static final Path boardSave = Paths.get("boardSave.txt");
	

	/**
	 * Tama luokka tallentaa, tarkistaa ja lataa pelin.
	 * Sovellus voi tallentaa yhden pelin.
	 * 
	 * Osumat ja raketit tallennetaan samaan tiedostoon,
	 * rivi 1: Osumat
	 * rivi 2: Raketit
	 * 
	 * @author Tijam Moradi
	 * @author Niklas Kiuru
	 * @version 1.0
	 * @param osumat
	 * @param raketit
	 * @param lauta
	 * 
	 */
	
	public static void saveState(int osumat, int raketit, String[][] lauta) {
		/**
		 * Tassa metodissa kirjoitamme laudan ja statsit eri tiedostoihin.
		 */
		
		List<String> bulletLine = Arrays.asList(Integer.toString(osumat), Integer.toString(raketit));
		List<String> boardLine = Arrays.asList(toString(lauta));
		
		try {
			Files.write(bulletSave, bulletLine, Charset.forName("UTF-8"));
		} catch (IOException ioe) {
			ioe.printStackTrace();
		    System.out.println("[SAVE] TIEDOSTOON bulletSave.txt KIRJOITTAMINEN EPÄONNISTUI!");
		}
		
		try {
			Files.write(boardSave, boardLine, Charset.forName("UTF-8"));
		} catch (IOException ioe) {
			ioe.printStackTrace();
		    System.out.println("[SAVE] TIEDOSTOON boardSave.txt KIRJOITTAMINEN EPÄONNISTUI!");
		}
			
	}
	
	public static boolean checkSave() {
		/**
		 * Tassa metodissa tarkistetaan tallennus.
		 */
		int laskin = 0;
		try {
			@SuppressWarnings("resource")
			BufferedReader brBullets = new BufferedReader(new FileReader("bulletSave.txt"));
			if (brBullets.readLine() != null) {
				// Osumat
				laskin++;
				if (brBullets.readLine() != null) {
					// Raketit
					laskin ++;
				}
			}
			
			@SuppressWarnings("resource")
			BufferedReader brBoard = new BufferedReader(new FileReader("boardSave.txt"));
			laskin++;
			// Laudan eheys katsotaan siten, etta onko kaikki
			// indeksit alustettu vai ei. 
			if (brBoard.ready() == false) {	// Tarkistetaan, jos lauta on tyhja tai syotevirta ei ole valmis.
				throw new Exception();
			}
			for (int i = 0; i < 8*8-1; i++) {
				int sisalto = brBoard.read(); 
				if (sisalto == -1 || (sisalto != Character.valueOf('*') && sisalto != Character.valueOf('!') 
						&& sisalto != Character.valueOf('H') && sisalto != Character.valueOf('L'))) { 
					laskin--;
					deleteBoardSave();
				}
			}
			
		} catch (Exception e) {
			System.out.println("[CHECK] EI TALLENNUSTA!");
			return false;
		}
		
		if (laskin == 3) {
			return true;
		} else {
			return false;
		}
	}
	
	public static int loadOsumat() {
		/**
		 * Tassa metodissa ladataan ja palautetaan osumat 
		 */
		int osumat = 0;
		try {
			osumat = Integer.parseInt(Files.readAllLines(bulletSave).get(0));
		} catch (NumberFormatException | IOException e) {
			System.out.println("[LOAD] KORRUPTOITUNUT RAKETIT JA OSUMAT TIEDOSTO!");
			deleteBulletSave();
			e.printStackTrace();
		} 
		return osumat;
	}
	
	public static int loadRaketit() {
		/**
		 * Tassa metodissa ladataan ja palautetaan raketit
		 */
		int raketit = 10;
		try {
			raketit = Integer.parseInt(Files.readAllLines(bulletSave).get(1));
		} catch (NumberFormatException | IOException e) {
			System.out.println("[LOAD] KORRUPTOITUNUT RAKETIT JA OSUMAT TIEDOSTO!");
			deleteBulletSave();
			e.printStackTrace();
		}
		return raketit;
	}
	
	public static void loadBoard(String[][] lauta) {
		/**
		 * Tassa metodissa injektoidaan tallennettu lauta suoraan peliin.
		 */
		try {
			@SuppressWarnings("resource")
			BufferedReader brBoard = new BufferedReader(new FileReader("boardSave.txt"));
			
			for (int i = 0; i < 8; i++) {
				for (int j = 0; j < 8; j++) {
					char ch = (char) brBoard.read();
					lauta[i][j] = Character.toString(ch);
				}
			}
		} catch (Exception e){ 
			System.out.println("[LOAD] LAUTA KORRUPTOITUNUT!");
			deleteBoardSave();
		}
	}
	
	public static String toString(String[][] taulu) {
		/**
		 * Tassa metodissa simplifioidaan lauta Stringiksi,
		 * 	jotta tallennus ja lataus olisi sujuvampaa.
		 */
		String lauta = "";
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				lauta += taulu[i][j];
			}
		}
		return lauta;
	}
	
	public static void deleteBulletSave() {
		/**
		 * Tama on toteutettu RandomAccessFilella, joka ei oikeastaan poista tallennusta,
		 * 	vaan nollaa sen tiedoston. Nain siksi, etta poistaminen voi aiheuttaa File:lla
		 * 	tahattoman virheen FileSystemException, vaikkei tiedosto olisi minkaan
		 * 	muun kaytossa.
		 */
		try {
			System.out.println("[FIX] POISTETAAN KORRUPTOITUNUT OSUMAT JA RAKETIT...");
			RandomAccessFile raf = new RandomAccessFile("bulletSave.txt", "rw"); 
			raf.setLength(0);
			System.out.println("[SUCCESS] KORRUPTOITUNEET TIEDOSTOT POISTETTU!");
			raf.close();
			mainGame.main(null);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void deleteBoardSave() { 
		/**
		 * @see GameState.deleteBulletSave
		 */
		try {
			System.out.println("[FIX] POISTETAAN KORRUPTOITUNUT LAUTA...");
			RandomAccessFile raf = new RandomAccessFile("boardSave.txt","rw");
			raf.setLength(0);
			System.out.println("[SUCCESS] KORRUPTOITUNUT LAUTA POISTETTU!");
			raf.close();
			mainGame.main(null);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
