package v3_graph1;

import com.mysql.jdbc.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;
import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Marquinhos
 */
public class BaseDades {

    private static final String URL = "jdbc:mysql://localhost:3306/";
    private static final String user = "root";
    private static final String pass = "Dequa16.";
    private PreparedStatement ps;
    private Statement st;
    private ResultSet rs;

    public BaseDades() {
    }

    public Connection getConnection() {
        Connection conexion = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conexion = (Connection) DriverManager.getConnection(URL, user, pass);
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Error, " + e);
        }

        return conexion;
    }

    public String[] getDatosPartidaJList(String cad) {
        String[] array = new String[3];

        Connection conexion = getConnection();

        try {
            ps = conexion.prepareStatement("use mastermind");
            ps.executeUpdate();
            ps = conexion.prepareStatement("select id, dificultat, estado from partida where nomPartida = '" + cad + "'");
            rs = ps.executeQuery();

            if (rs.next()) {
                array[0] = Integer.toString(rs.getInt(1));
                array[1] = rs.getString(2);
                array[2] = rs.getString(3);
            }

            conexion.close();
        } catch (SQLException e) {
            System.err.println("Error, " + e);
        }

        return array;
    }

    //metode que ompleix la jlist de graph_loadgame
    public DefaultListModel fillJList(DefaultListModel modelo) {
        Connection conexion = getConnection();

        try {
            ps = conexion.prepareStatement("use mastermind");
            ps.executeUpdate();
            ps = conexion.prepareStatement("select nomPartida from partida");
            rs = ps.executeQuery();

            while (rs.next()) {
                modelo.addElement(rs.getString(1));
            }
            conexion.close();

        } catch (SQLException e) {
        }

        return modelo;
    }

    public Partida getPartida(String cad) {
        Connection conexion = getConnection();
        Partida p = null;
        try {
            ps = conexion.prepareStatement("use mastermind");
            ps.executeUpdate();
            ps = conexion.prepareStatement("select numRandom,dificultat,estado from partida where nomPartida = '" + cad + "'");
            rs = ps.executeQuery();

            if (rs.next()) {
                p = new Partida(cad, rs.getString(1).getBytes(), rs.getString(2), rs.getString(3));
            }

            conexion.close();
        } catch (SQLException e) {
            System.err.println("Error, " + e);
        }

        return p;
    }

    public void deletePartida(String cad) {
        Connection conexion = getConnection();

        try {
            ps = conexion.prepareStatement("use mastermind");
            ps.execute();
            ps = conexion.prepareStatement("delete tirada.* from tirada join partida on partida.id = tirada.idPartida where partida.nomPartida = '" + cad + "'");
            ps.executeUpdate();
            ps = conexion.prepareStatement("delete from partida where nomPartida = '" + cad + "'");
            ps.executeUpdate();

            conexion.close();
        } catch (SQLException e) {
            System.err.println("Error, " + e);
        }
    }

    public int getNumTirades(String nom) {
        Connection conexion = getConnection();
        int numTirades = 0;
        try {
            ps = conexion.prepareStatement("use mastermind");
            ps.executeUpdate();
            ps = conexion.prepareStatement("select count(tirada.id) from tirada join partida on partida.id = tirada.idPartida where partida.nomPartida = '" + nom + "';");
            rs = ps.executeQuery();
            if (rs.next()) {
                numTirades = rs.getInt(1);
            }
        } catch (SQLException e) {
            System.err.println("Error, " + e);
        }
        return numTirades;
    }

    //agafa les dades de tirada
    public JTable selectDatosJTable(String query, JTable table) {
        Connection conexion = getConnection();

        try {
            ps = conexion.prepareStatement("use mastermind");
            ps.executeUpdate();
            ps = conexion.prepareStatement(query);
            rs = ps.executeQuery();
            updateTableModelData((DefaultTableModel) table.getModel(), rs, table);

            DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
            centerRenderer.setHorizontalAlignment(JLabel.CENTER);
            table.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
            for (int i = 0; i < table.getColumnCount(); i++) {
                table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
            }

            conexion.close();
        } catch (SQLException e) {
            System.err.println("Error, " + e);
        }
        
        return table;
    }

    //metode auxiliar de selectDatosJTable
    private static void updateTableModelData(DefaultTableModel tModel, ResultSet rs, JTable t) {
        tModel.setRowCount(0);
        try {
            ResultSetMetaData metaData = rs.getMetaData();
        } catch (SQLException ex) {
            System.err.println("Error, " + ex);
        }

        try {
            while (rs.next()) {
                Vector newRow = new Vector();
                for (int i = 1; i <= t.getColumnCount(); i++) {
                    newRow.addElement(rs.getObject(i));
                }
                tModel.addRow(newRow);
            }
        } catch (SQLException ex) {
            System.err.println("Error, " + ex);
        }
    }

    //metode que inserta una nova partida dins la base de dades
    public void insertarDatosPartida(Partida p) {
        Connection conexion = getConnection();

        String nombrePartida = p.getNom();
        String numRandom = new String(p.getT_IA());
        String dificultat = p.getDificultat();
        String estado = p.getEstado();

        try {
            ps = conexion.prepareStatement("use mastermind");
            ps.executeUpdate();
            ps = conexion.prepareStatement("insert into partida values (null,'" + nombrePartida + "','" + numRandom + "','" + dificultat + "','" + estado + "')");
            ps.executeUpdate();
            conexion.close();
        } catch (SQLException e) {
            System.err.println("Error, " + e);
        }
    }

    //metode que actualitza una partida cada vegada que es realitza una tirada de la mateixa
    public void actualizarDatosPartida(Partida p) {
        Connection conexion = getConnection();

        try {
            ps = conexion.prepareStatement("use mastermind");
            ps.executeUpdate();
            ps = conexion.prepareStatement("update partida set estado='" + p.getEstado() + "' where nomPartida = '" + p.getNom() + "'");
            ps.executeUpdate();
            conexion.close();
        } catch (SQLException e) {
            System.err.println("Error, " + e);
        }
    }

    //metode que insereix una nova tirada dins base de dades 
    public void insertarDatosTirada(Tirada t, Partida p, String idIntentTirada) {
        Connection conexion = getConnection();

        int idTirada = Integer.parseInt(idIntentTirada);
        int idPartida = cercarIdPartida(p);
        int contEncertats = t.getCont_Encertats();
        int contMalPos = t.getCont_MalPosicionats();

        String numUser = new String(t.getT_NumIntroduit());

        String hintNum = new String(t.getT012());
        /*
        for (byte b : t.getT012()) {
            hintNum += b;
        }
         */

        try {
            ps = conexion.prepareStatement("use mastermind");
            ps.executeUpdate();
            ps = conexion.prepareStatement("insert into tirada values(" + idTirada + "," + idPartida + "," + contEncertats + "," + contMalPos + ",'" + numUser + "','" + hintNum + "')");
            ps.executeUpdate();
            conexion.close();
        } catch (SQLException e) {
            System.err.println("Error, " + e);
        }
    }

    //a partir del nom de partida, retorna la seva id
    private int cercarIdPartida(Partida p) {
        String nom = p.getNom();
        int id = 0;

        Connection conexion = getConnection();

        try {
            ps = conexion.prepareStatement("use mastermind");
            ps.executeUpdate();
            ps = conexion.prepareStatement("select id from partida where nomPartida = '" + nom + "'");
            rs = ps.executeQuery();
            if (rs.next()) {
                id = rs.getInt(1);
            }
            conexion.close();
        } catch (SQLException e) {
            System.err.println("Error, " + e);
        }

        return id;
    }

    //metode que comprova si existeix el nom de la nova partida en la base dedades
    public boolean comprovaNomPartida(String text) {
        Connection conexion = getConnection();

        try {
            ps = conexion.prepareStatement("use mastermind");
            ps.execute();
            ps = conexion.prepareStatement("select * from partida where nomPartida = '" + text + "'");
            rs = ps.executeQuery();
            if (rs.next()) {
                return true;
            }
            conexion.close();
        } catch (SQLException e) {
            System.err.println("Error, " + e);
        }

        return false;
    }

    //metode que carga la base de dades sencera desde zero
    public void prePlay() {
        Connection conexion = getConnection();

        try {
            st = conexion.createStatement();
            st.executeUpdate("create database if not exists mastermind");
            st.executeUpdate("create user if not exists 'adminMM'@'localhost' identified by 'Dequa16.'");
            st.executeUpdate("grant all privileges on mastermind.* to 'adminMM'@'localhost' identified by 'Dequa16.'");

            st.executeUpdate("use mastermind");

            ps = conexion.prepareStatement("create table if not exists partida("
                    + "id int primary key auto_increment,"
                    + "nomPartida varchar(50) not null,"
                    + "numRandom varchar(5) not null,"
                    + "dificultat varchar(4) not null,"
                    + "estado varchar(4) not null"
                    + ")");
            ps.executeUpdate();

            ps = conexion.prepareStatement("create table if not exists tirada("
                    + "id int not null,"
                    + "idPartida int not null,"
                    + "contadorEncertats int not null,"
                    + "contadorMalPosicionats int not null,"
                    + "nombreIntroduit varchar(5) not null,"
                    + "taulaAjuda varchar(5) not null,"
                    + "constraint fk_tirada_partida foreign key (idPartida) references partida(id) on delete cascade on update cascade"
                    + ")");
            ps.executeUpdate();

            conexion.close();
            st.close();
        } catch (SQLException e) {
            System.err.println("Error, " + e);
        }
    }

}
