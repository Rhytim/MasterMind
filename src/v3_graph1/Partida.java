package v3_graph1;

import java.util.ArrayList;
import java.util.Random;

/**
 * @author marc lopez
 */
public class Partida {

    //ATRIBUTS
    private final int MAX_INTENTS = 10;
    private String estado; //si la partida esta acabada o no
    private String nom;
    private String dificultat;
    private byte[] t_IA;
    private ArrayList<Tirada> llista = new ArrayList<>();

    //CONSTRUCTORS
    public Partida() {
    }

    public Partida(String nomPartida, String dificultat) {
        crearTaulaIA();
        this.nom = nomPartida;
        this.dificultat = dificultat;
        this.estado = "Open";
    }

    public Partida(String nomPartida, byte[] t_IA, String dificultat, String estado) {
        this.nom = nomPartida;
        this.t_IA = t_IA;
        this.dificultat = dificultat;
        this.estado = estado;
    }

    //METODES
    //afegeix una tirada dins la llista partida
    public Tirada crearTirada(String numUser) {
        byte[] arrayaux = numUser.getBytes();
        Tirada t = new Tirada(arrayaux, t_IA);
        llista.add(t);
        return t;
    }

    //Crea la taula amb 5 nombres aleatoris a adivinar
    private void crearTaulaIA() {
        t_IA = new byte[5];
        Random rand = new Random();

        for (byte i = 0; i < 5; i++) {
            t_IA[i] = (byte) (rand.nextInt(10) + 48);
        }
        //System.out.println(new String(t_IA));
    }

    //Comprova si ha adivinat el numero secret
    public boolean siGuanyat(byte contadorBe) {
        if (contadorBe == 5) {
            estado = "Win";
            return true;
        }
        return false;
    }

    //comprova si es pot seguir realitzant tirades
    public boolean siTiradesRestants() {
        BaseDades bd = new BaseDades();
        if (MAX_INTENTS - bd.getNumTirades(nom) == 0) {
            estado = "Lost";
            return false;
        }

        return true;
    }

    //metode que comprova si la partida esta acabada o no
    public boolean checkEstado() {
        return estado.equalsIgnoreCase("open");
    }

    public byte[] getT_IA() {
        return t_IA;
    }

    public String getEstado() {
        return estado;
    }

    public String getNom() {
        return nom;
    }

    public String getDificultat() {
        return dificultat;
    }

}
