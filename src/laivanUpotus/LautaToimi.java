package laivanUpotus;

public class LautaToimi {
	/**
	 * @author Tijam Moradi
	 * @param lauta
	 * 
	 */
	
	public static void mallinnaLauta(String[][] lauta){
		for (int r = 0; r<lauta.length; r++) {	// r = rivi
			for (int s = 0; s<lauta[0].length; s++) {	// s = sarake
				lauta[r][s] = "*";
			}
		}
	}
	
	public static void pelaajaNakyma(String[][] lauta) {
		/**
		 * Tämä metodi printtaa pelaajanakyman, jossa 
		 * ei nay tietokoneen laivat
		 * 
		 */
		
		System.out.println("\n\n");
		for (int r = 0; r<lauta.length; r++) {
			for (int s = 0; s<lauta[0].length; s++) {
				if (lauta[r][s].equals("L")){
					System.out.print(" "+"*");
				} else {
					System.out.print(" "+lauta[r][s]);
				}
			}
			System.out.println();
		}
		System.out.println("\n\n");
	}
	
	public static void tietokoneNakyma(String[][] lauta) {
		/**
		 *  Tama metodi printtaa myos tietokoneen laivan 
		 */
		
		System.out.println("\n\n");
		for (int r = 0; r<lauta.length; r++) {
			for (int s = 0; s<lauta[0].length; s++) {
				System.out.print(" "+lauta[r][s]);
			}
			System.out.println();
		}
		System.out.println("\n\n");
	}

}
