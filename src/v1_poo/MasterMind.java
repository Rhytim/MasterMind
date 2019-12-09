package v1_poo;

import java.util.Random;
import java.util.Scanner;

/**
 *
 * @author marc lopez
 */
public class MasterMind {

    private byte vides = 3;
    private byte[] t_IA;

    //Comprova si ha adivinat el numero secret
    private void siGuanyat(byte cont) {
        if (cont != 5) {
            vides--;
            if (vides == 0) {
                System.out.println(" - GAME OVER - ");
            }
        } else {
            System.out.println(" -  WIN  - ");
        }
    }

    //AUXILIAR que escriu el numero que insertara
    private String userEscriuNum(String aux) {
        Scanner sc = new Scanner(System.in);
        do {
            System.out.println("Inserta un numero de 5 digits: ");
            aux = sc.nextLine();
        } while (!userNumOk(aux));
        return aux;
    }

    //AUXILIAR que comprova el format sigui correcte que user ha insertat
    private boolean userNumOk(String cad) {
        if (cad.length() == 5) {
            if (cad.matches("[0-9]+")) {
                //System.out.println("CORRECTE!");
                return true;
            }
        }
        System.out.println("Error. Torna a intentar. :)\n");
        return false;
    }

    //Crear una taula a partir del nombre que user inserti
    private byte[] crearTaulaUser() {
        byte[] t_User = new byte[5];
        String aux = "";

        aux = userEscriuNum(aux);

        for (byte i = 0; i < 5; i++) {
            t_User[i] = (byte) ((byte) aux.charAt(i) - 48);
        }

        return t_User;
    }

    //Crea la taula amb 5 nombres aleatoris a adivinar
    private void crearTaulaIA() {
        t_IA = new byte[5];
        Random rand = new Random();

        for (byte i = 0; i < 5; i++) {
            t_IA[i] = (byte) rand.nextInt(10);
        }
    }

    //metode per començar a jugar
    public void jugar() {
        byte t_User[];
        byte cont;

        crearTaulaIA(); //add data to IA table up to guess.
        do {
            System.out.println("Vides: "+vides);
            t_User = crearTaulaUser(); //add data to User table to guess IA's one.  

            Tirada t1 = new Tirada(t_User, t_IA);

            System.out.println(t1.toString()); //visualitza les dades de tirada
            cont = t1.getCont_Encertats();
            siGuanyat(cont); //comprova si has encertat el numero a adivinar o no
        } while (vides != 0 && cont != 5);
    }

    public static void main(String[] args) {
        MasterMind mm = new MasterMind();
        mm.jugar();
    }
}
