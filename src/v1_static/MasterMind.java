package v1_static;

import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

/**
 * Joc Mastermind tot a nivell d'statis
 *
 * @author marc lopez
 */
public class MasterMind {

    static final byte ELE_TAULA = 5;

    //metode que fa net el terminal
    public static void clearScreen() {
        System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
    }

    //metode que comprova si ha adivinat el numero
    public static byte siGuanyat(byte cont, byte vides) {
        if (cont != ELE_TAULA) {
            vides--;
            if (vides == 0) {
                System.out.println("GAME OVER");
            }
        } else {
            System.out.println(" -  WIN  - ");
        }
        return vides;
    }

    //metode que mostra els resultats
    public static void visualitzaResultat(byte[] t_IA, byte vides, String[] t_Secret, byte[] t) {
        System.out.println("\n\n" + "Numero Secret" + Arrays.toString(t_IA) + "\n"); //HINT IA numero secret
        System.out.println("Vides restants: " + vides);
        System.out.println("Numeros encertats: " + Arrays.toString(t_Secret));
        System.out.println("HINT: " + Arrays.toString(t));
    }

    //metode que retorna el nombre de nombres adivinats
    public static byte contaAdivinats(byte[] t) {
        byte cont = 0;

        for (byte b : t) {
            if (b == 1) {
                cont++;
            }
        }

        return cont;
    }

    //metode que cerca quins numeros (encara que no estiguin en la pos correcta)
    //existeixen de la que hi ha que adivinar l'user
    public static byte[] cercarMal(byte[] t_User, byte[] t_IA, byte[] t) {
        for (byte i = 0; i < ELE_TAULA; i++) {
            for (byte j = 0; j < ELE_TAULA; j++) {
                if (t[j] == 0) {
                    if (t_User[i] == t_IA[j]) {
                        t[j] = 2;
                    }
                }
            }
        }
        return t;
    }

    //metode que cerca quins numeros ha encertat l'user
    public static byte[] cercarBe(byte[] t_User, byte[] t_IA, byte[] t) {
        for (byte i = 0; i < ELE_TAULA; i++) {
            if (t_User[i] == t_IA[i]) {
                t[i] = 1;
            } else {
                t[i] = 0;
            }
        }
        return t;
    }

    //metode que pinta els numeros encertats
    public static String[] pintarTaulaSecreta(byte[] t_User, byte[] t, String[] t_Secret) {
        for (byte i = 0; i < ELE_TAULA; i++) {
            if (t[i] == 1) {
                t_Secret[i] = Byte.toString(t_User[i]);
            } else {
                t_Secret[i] = "_";
            }
        }
        return t_Secret;
    }

    //metode per crear la taula amb 5 nombres aleatoris a adivinar
    public static byte[] crearTaulaIA() {
        byte[] taula = new byte[ELE_TAULA];
        Random rand = new Random();

        for (byte i = 0; i < ELE_TAULA; i++) {
            taula[i] = (byte) rand.nextInt(10);
        }
        return taula;
    }

    //metode per crear una taula a partir del nombre que user inserti
    public static byte[] crearTaulaUser() {
        byte[] taula = new byte[ELE_TAULA];
        String aux = "";

        aux = userEscriuNum(aux);

        for (byte i = 0; i < ELE_TAULA; i++) {
            taula[i] = (byte) ((byte) aux.charAt(i) - 48);
        }

        return taula;
    }

    //metode que comprova el format sigui correcte que user ha insertat
    public static boolean userNumOk(String cad) {
        if (cad.length() == ELE_TAULA) {
            if (cad.matches("[0-9]+")) {
                //System.out.println("CORRECTE!");
                return true;
            }
        }
        System.out.println("Error. Torna a intentar. :)\n");
        return false;
    }

    //metode que escriu el numero que insertara
    public static String userEscriuNum(String aux) {
        Scanner sc = new Scanner(System.in);
        do {
            System.out.println("Inserta un numero de 5 digits: ");
            aux = sc.nextLine();
        } while (!userNumOk(aux));
        return aux;
    }

    //metode per començar a jugar
    public static void jugar() {
        //declaracio de variables
        String t_Secret[] = {"_", "_", "_", "_", "_"};
        byte t[] = {0, 0, 0, 0, 0};
        byte t_IA[];
        byte t_User[];
        byte cont;
        byte vides = 3;

        t_IA = crearTaulaIA(); //add data to IA table up to guess.

        do {
            visualitzaResultat(t_IA, vides, t_Secret, t);
            //
            t_User = crearTaulaUser(); //add data to User table to guess IA's one.  
            t = cercarBe(t_User, t_IA, t); //cerca les posicions que estan be
            t_Secret = pintarTaulaSecreta(t_User, t, t_Secret); //coloca els numeros que son igual al adivinar
            t = cercarMal(t_User, t_IA, t); //cerca si el numero existeix pero esta mal colocat
            cont = contaAdivinats(t); //calcula el nombre de numeros encertats
            vides = siGuanyat(cont, vides); //comprova si has encertat el numero a adivinar o no
        } while (cont != ELE_TAULA & vides != 0);
    }

    //main
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String aux;
        do {
            clearScreen();
            jugar();
            System.out.println("Vols tornar a jugar? (Si/No)");
            aux = sc.nextLine();
        } while (aux.matches("Si") | aux.matches("si") | aux.matches("SI"));
        System.out.println("\n\nEXIT - GOODBYE PLAYER");
    }

}
