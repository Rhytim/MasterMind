package v3_graph1;

/**
 *
 * @author marc lopez
 */
public class Joc {

    public static void main(String[] args) {
        //inici de la base de dades
        BaseDades bd = new BaseDades();
        bd.prePlay();

        //Obrim finestra grafica
        Graph_menu m1 = new Graph_menu();
        m1.setVisible(true);
    }
}
