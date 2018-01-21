package laivanUpotus;

public class GameLogic {
	/**
	 * @author Tijam Moradi, Niklas Kiuru
	 * @param osumat
	 * @param raketit
	 * @param lauta
	 */
	public static void LoppuTulos(int osumat, int raketit, String[][] lauta) {
		if (osumat < 4) {
			System.out.println("Et upottanut tietokoneen laivaa.\n Laiva oli tassa:");
			LautaToimi.tietokoneNakyma(lauta);
			
		} 
		if (raketit < 1) {
			System.out.println("Ammuit jo kaikki raketit");
		} else {
			if (osumat >= 4) {
				LautaToimi.pelaajaNakyma(lauta);
				System.out.println("Olet voittanut pelin!");
			}
		}
		System.out.println("Kiitos pelaamisesta. 5/5");
	}
}
