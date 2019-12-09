package v2_poo;

import java.util.Random;

/**
 * @author marc lopez
 */
public class Partida {

    //ATRIBUTS
    private byte vides = 10;
    private byte[] t_IA;
    
    //CONSTRUCTORS
    public Partida(){
        crearTaulaIA(); //add data to IA table up to guess.
    }
    
    //METODES
    //Crea la taula amb 5 nombres aleatoris a adivinar
    private void crearTaulaIA() {
        t_IA = new byte[5];
        Random rand = new Random();

        for (byte i = 0; i < 5; i++) {
            t_IA[i] = (byte) rand.nextInt(10);
        }
    }
    
    //Comprova si ha adivinat el numero secret
    public void siGuanyat(byte contadorBe) {
        if (contadorBe != 5) {
            vides--;
            if (vides == 0) {
                System.out.println(" - GAME OVER - ");
            }
        } else {
            System.out.println(" -  WIN  - ");
        }
    }

    public byte[] getT_IA() {
        return t_IA;
    }

    public byte getVides() {
        return vides;
    }

}
