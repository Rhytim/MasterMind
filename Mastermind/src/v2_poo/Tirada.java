package v2_poo;

/**
 *
 * @author marc lopez
 */
public class Tirada {

    //ATTRIBUTS
    private byte cont_Encertats;
    private byte cont_MalPosicionats;
    private byte[] t_NumIntroduit;
    private byte[] t012 = {0, 0, 0, 0, 0}; // 0 1 2

    //CONSTRUCTORS
    public Tirada(byte[] t_NumIntroduit, byte[] t_IA) {
        this.t_NumIntroduit = t_NumIntroduit;
        cercarBe(t_IA);
        cercarMal(t_IA);
    }

    //METODES
    //Cerca quins numeros ha encertat l'user
    private void cercarBe(byte[] t_IA) {
        for (byte i = 0; i < 5; i++) {
            if (t_NumIntroduit[i] == t_IA[i]) {
                t012[i] = 1;
                cont_Encertats++;
            }
        }
    }

    /*Cerca quins numeros (encara que no estiguin en la pos correcta)
    existeixen de la que hi ha que adivinar l'user */
    private void cercarMal(byte[] t_IA) {
        for (byte i = 0; i < 5; i++) {
            boolean fin = false;
            if (t012[i] != 1) {
                for (byte j = 0; j < 5 && !fin; j++) {
                    if (t012[j] == 0) {
                        if (t_NumIntroduit[i] == t_IA[j]) {
                            t012[j] = 2;
                            cont_MalPosicionats++;
                            fin = true;
                        }
                    }
                }
            }
        }
    }

    //Mostra els resultats   
    @Override
    public String toString() {

        String cadenaNumIntroduit = "";
        String cadena012 = "";

        for (byte b : t_NumIntroduit) {
            cadenaNumIntroduit += b;
        }

        for (byte b : t012) {
            cadena012 += b;
        }

        return "Numero insertat: " + cadenaNumIntroduit + "\t"
                + "HINT: " + cadena012 + "\t"
                + "Numeros encertats: " + cont_Encertats + "\t"
                + "Numeros mal colocats: " + cont_MalPosicionats + "\n";
    }

    public byte getCont_Encertats() {
        return cont_Encertats;
    }
}
