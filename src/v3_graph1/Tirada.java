package v3_graph1;

/**
 *
 * @author marc lopez
 */
public class Tirada {

    //ATTRIBUTS
    private byte cont_Encertats;
    private byte cont_MalPosicionats;
    private byte[] t_NumIntroduit;
    private byte[] t012 = {48,48,48,48,48}; // 0 1 2

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
                t012[i] = 49;
                cont_Encertats++;
            }
        }
    }

    /*Cerca quins numeros (encara que no estiguin en la pos correcta)
    existeixen de la que hi ha que adivinar l'user */
    private void cercarMal(byte[] t_IA) {
        for (byte i = 0; i < 5; i++) {
            boolean fin = false;
            if (t012[i] != 49) {
                for (byte j = 0; j < 5 && !fin; j++) {
                    if (t012[j] == 48) {
                        if (t_NumIntroduit[j] == t_IA[i]) {
                            t012[j] = 50;
                            cont_MalPosicionats++;
                            fin = true;
                        }
                    }
                }
            }
        }
    }

    public byte getCont_MalPosicionats() {
        return cont_MalPosicionats;
    }

    public byte[] getT_NumIntroduit() {
        return t_NumIntroduit;
    }

    public byte[] getT012() {
        return t012;
    }

    public byte getCont_Encertats() {
        return cont_Encertats;
    }
}
