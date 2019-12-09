package v2_poo;

//import java.util.Arrays;
import java.util.Scanner;

/**
 *
 * @author marc lopez
 */
public class Joc {

    //METODES
    //AUXILIAR que comprova el format sigui correcte que user ha insertat
    private static boolean userNumOk(String cad) {
        if (cad.length() == 5) {
            if (cad.matches("[0-9]+")) {
                return true;
            }
        }
        System.out.println("Error. Torna a intentar. :)\n");
        return false;
    }

    //AUXILIAR que escriu el numero que insertara
    private static String userEscriuNum(String aux) {
        Scanner sc = new Scanner(System.in);
        do {
            System.out.println("Inserta un numero de 5 digits: ");
            aux = sc.nextLine();
        } while (!userNumOk(aux));
        return aux;
    }

    //Crear una taula a partir del nombre que user inserti
    private static byte[] crearTaulaUser() {
        byte[] t_User = new byte[5];
        String aux = "";

        aux = userEscriuNum(aux);

        for (byte i = 0; i < 5; i++) {
            t_User[i] = (byte) ((byte) aux.charAt(i) - 48);
        }

        return t_User;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String siJuga = "";
        System.out.println("Vols jugar una partida?");
        siJuga = sc.nextLine();
        while (siJuga.equalsIgnoreCase("si")) {

            Partida p = new Partida();
            
            byte contadorBe;
            
            do {
                //System.out.println(Arrays.toString(p.getT_IA()));
                byte[] t_NumIntroduit = crearTaulaUser();
                Tirada t = new Tirada(t_NumIntroduit, p.getT_IA());

                System.out.println("Vides: " + p.getVides());
                System.out.println(t.toString());

                contadorBe = t.getCont_Encertats();
                p.siGuanyat(contadorBe);

            } while (p.getVides() != 0 && contadorBe != 5);

            System.out.println("Vols tornar a jugar una partida?");
            siJuga = sc.nextLine();
        }

    }

}
