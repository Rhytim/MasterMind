package v3_graph1;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author Marquinhos
 */
public class Graph_play extends JFrame {

    private JLabel intents;
    private JPanel panel;
    private JButton btn_go;
    private Partida p;
    private JTextField text;
    private JTable tabla;
    private int cont;

    public Graph_play(Partida p) {
        this.p = p;
        setSize(650, 685);
        setLocationRelativeTo(null);
        setUndecorated(true);
        setResizable(false);
        getContentPane().setBackground(Color.LIGHT_GRAY);

        crearPanel();

        if (!p.checkEstado()) {
            btn_go.setEnabled(false);
            text.setEnabled(false);
            intents.setVisible(false);
        }
    }

    private void crearPanel() {
        panel = new JPanel();
        panel.setLayout(null);
        panel.setBorder(BorderFactory.createBevelBorder(1, Color.GRAY, Color.GRAY));

        title();
        menu();
        numIntents();
        textfield();
        info();

        intents.setText(Integer.toString(tabla.getRowCount() + 1));

        botons();

        this.getContentPane().add(panel);
    }

    private void title() {
        JLabel titul = new JLabel(new ImageIcon("logo.png"));
        titul.setSize(625, 200);
        titul.setAlignmentX(50);
        panel.add(titul);
    }

    private void menu() {
        JMenuBar mb = new JMenuBar();
        JMenu m = new JMenu("Help");
        JMenuItem mi = new JMenuItem("Instructions");

        setJMenuBar(mb);

        //LISTENER
        ActionListener listen_menu = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(
                        null,
                        "Welcome to MasterMind,"
                        + "\n\nTo play, click on the button NEW GAME "
                        + "next you're gonna introduce the name of the game,"
                        + "\nthen you select the dificulty of the game:"
                        + "\n\n\t- Easy: You play with a hint table"
                        + "\n\t- Hard: You play without a hint table"
                        + "\n\nThis hint table is gonna tell you 3 things:"
                        + "\n\n\t- 0: Indicates that the number in this posicion doesnt exist"
                        + "\n\t- 1: Indicates that the number in this posicion is correct"
                        + "\n\t- 2: Indicates that the number in this posicion is correct but wrong posicion"
                        + "\n\nNow, after this if you want to play, you're gonna have to click on the PLAY button."
                        + "\nAnd at the moment is gonna appear a window with a text field where you are gonna have to:"
                        + "\n\n\t- Insert a 5 digit number"
                        + "\n\t- No letter, just numbers"
                        + "\n\nAnd then you click on the GO button to try if your guess was right or not."
                        + "\n\n\nPS: You have 10 attemps to guess the mysterious number.",
                        "Instructions", JOptionPane.INFORMATION_MESSAGE);
            }
        };

        mi.addActionListener(listen_menu);
        m.add(mi);
        mb.add(m);
    }

    private void numIntents() {
        JLabel tlab = new JLabel("Tº: ");
        intents = new JLabel();

        //Text
        tlab.setFont(new Font("arial", Font.CENTER_BASELINE, 20));
        tlab.setBounds(67, 200, 50, 85);

        //INTENTS
        intents.setFont(new Font("Corbel", Font.BOLD, 55));
        intents.setForeground(Color.orange);
        intents.setBounds(100, 197, 60, 85);

        panel.add(tlab);
        panel.add(intents);
    }

    private void textfield() {
        text = new JTextField();

        text.setBounds(162, 200, 270, 85);
        text.setFocusable(true);
        text.setFont(new Font("arial", Font.PLAIN, 25));
        text.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));
        text.setHorizontalAlignment(JTextField.CENTER);
        text.setEnabled(true);

        panel.add(text);
    }

    private void info() {
        String query;
        String nom = p.getNom();
        //MODELO
        DefaultTableModel modelo = new DefaultTableModel();
        modelo.addColumn("ID");
        modelo.addColumn("USER NUMBER");
        if (p.getDificultat().equalsIgnoreCase("easy")) {
            modelo.addColumn("HINT");
            query = "select tirada.id,nombreIntroduit,taulaAjuda,contadorEncertats,contadorMalPosicionats from tirada join partida on partida.id = tirada.idPartida where partida.nomPartida = '" + nom + "'";
        } else {
            query = "select tirada.id,nombreIntroduit,contadorEncertats,contadorMalPosicionats from tirada join partida on partida.id = tirada.idPartida where partida.nomPartida = '" + nom + "'";
        }
        modelo.addColumn("COUNT OK");
        modelo.addColumn("COUNT WRONG");

        //TABLA
        tabla = new JTable(modelo);
        tabla.setBounds(82, 320, 560, 184);
        tabla.setEnabled(false);
        tabla.setAutoCreateRowSorter(false);
        tabla.setFont(new Font("Arial Nova Light", Font.CENTER_BASELINE, 14));

        //sql 
        BaseDades bd = new BaseDades();
        tabla = bd.selectDatosJTable(query, tabla);

        //ordenant per nombre de tirada
        TableRowSorter<TableModel> sorter = new TableRowSorter<>(tabla.getModel());
        tabla.setRowSorter(sorter);
        //List<RowSorter.SortKey> sortKeys = new ArrayList<>();
        //sortKeys.add(new RowSorter.SortKey(0, SortOrder.ASCENDING));
        //sorter.setSortKeys(sortKeys);
        sorter.sort();
        sorter.setSortable(0, false);
        sorter.setSortable(1, false);
        sorter.setSortable(2, false);
        sorter.setSortable(3, false);
        try {
            sorter.setSortable(4, false);
        } catch (Exception e) {
        }

        //donant disseny a la taula
        JTableHeader thead = tabla.getTableHeader();
        //thead.setBackground(new Color(245,185,100));
        thead.setBackground(new Color(230, 167, 96));
        thead.setForeground(Color.black);
        thead.setFont(new Font("arial", Font.BOLD, 14));
        thead.setEnabled(false);

        panel.add(tabla);

        //SCROLL
        JScrollPane scroll = new JScrollPane(tabla, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scroll.setBounds(50, 320, 560, 184);
        panel.add(scroll);
    }

    private boolean comprovarSiNumUserTotNumber() {
        try {
            int i = Integer.parseInt(text.getText());
        } catch (NumberFormatException ex) {
            text.setText("");
            return false;
        }
        return true;
    }

    private void botons() {
        BufferedImage image = null;
        try {
            image = ImageIO.read(new File("menu4.png"));
        } catch (IOException e) {
            System.err.println("Error, " + e);
        }
        ImageIcon icon = new ImageIcon(fitimage(image, 200, 70));
        JButton btn_exit = new JButton(icon);
        btn_go = new JButton("GO");

        //GO
        btn_go.setBounds(459, 200, 150, 85);
        btn_go.setFont(new Font("arial", Font.BOLD, 20));
        btn_go.setBorder(BorderFactory.createLineBorder(Color.orange));
        btn_go.setFocusable(false);
        btn_go.setEnabled(true);
        btn_go.setToolTipText("Roll");

        //EXIT
        btn_exit.setBounds(220, 540, 200, 70);
        btn_exit.setFont(new Font("arial", Font.BOLD, 20));
        btn_exit.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
        btn_exit.setFocusable(false);
        btn_exit.setBorderPainted(false);
        btn_exit.setContentAreaFilled(false);
        btn_exit.setFocusPainted(false);
        btn_exit.setOpaque(false);
        btn_exit.setToolTipText("Go back to menu");

        //LISTENERS
        ActionListener listen_go = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (text.getText().length() == 5) { //valida si textfield conte 5 lletres especificament, ni mes ni menys
                    BaseDades bd = new BaseDades();
                    if (comprovarSiNumUserTotNumber()) {
                        if (cont != 10) {
                            if (!intents.getText().equalsIgnoreCase("10")) {
                                intents.setText(Integer.toString(tabla.getRowCount() + 1));
                            }
                        }
                        Tirada t = p.crearTirada(text.getText());
                        bd.insertarDatosTirada(t, p, intents.getText());
                        info();

                        if (p.siGuanyat(t.getCont_Encertats())) {// ha endivinat el numero?
                            btn_go.setEnabled(false);
                            text.setEnabled(false);
                            JOptionPane.showMessageDialog(null, "WIN", "", JOptionPane.INFORMATION_MESSAGE);
                        } else if (!p.siTiradesRestants()) { // hi ha tirades restants?
                            btn_go.setEnabled(false);
                            text.setEnabled(false);
                            JOptionPane.showMessageDialog(null, "GAME OVER", "", JOptionPane.ERROR_MESSAGE);
                        }

                        bd.actualizarDatosPartida(p);
                    }
                }
            }
        };
        btn_go.addActionListener(listen_go);

        ActionListener listen_exit = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                System.gc();
                Graph_menu mp = new Graph_menu();
                mp.setVisible(true);
            }
        };
        btn_exit.addActionListener(listen_exit);

        panel.add(btn_go);
        panel.add(btn_exit);
    }

    //metode que adapta les dimension d'una imatge a la d'un component grafic
    private Image fitimage(Image img, int w, int h) {
        BufferedImage resizedimage = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = resizedimage.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2.drawImage(img, 0, 0, w, h, null);
        g2.dispose();
        return resizedimage;
    }

}
