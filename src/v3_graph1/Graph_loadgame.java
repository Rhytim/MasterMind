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
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 *
 * @author Marquinhos
 */
public class Graph_loadgame extends JFrame {

    private JPanel panel;
    private JLabel l_id;
    private JLabel l_nom;
    private JLabel l_dificultat;
    private JLabel l_estado;
    private DefaultListModel modelo;
    private JButton btn_delete;
    private JButton btn_play;
    private JList lista;

    private String nomPartida;
    private int indexSeleccionat;

    public Graph_loadgame() {
        setSize(750, 750);
        setLocationRelativeTo(null);
        setUndecorated(true);
        setResizable(false);
        getContentPane().setBackground(Color.LIGHT_GRAY);

        crearPanel();
    }

    private void crearPanel() {
        panel = new JPanel();
        panel.setLayout(null);
        panel.setBorder(BorderFactory.createBevelBorder(1, Color.GRAY, Color.GRAY));

        menu();
        titul();
        jlist();
        labels();
        botones();

        this.getContentPane().add(panel);
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

    private void titul() {
        JLabel title = new JLabel("SAVED GAMES");

        title.setBounds(170, 50, 700, 75);
        title.setFont(new Font("arial", Font.BOLD, 60));

        panel.add(title);
    }

    private void jlist() {
        BaseDades bd = new BaseDades();
        modelo = new DefaultListModel();
        lista = new JList(modelo);

        //DATABASE
        modelo = bd.fillJList(modelo);

        //LISTA
        lista.setBounds(70, 150, 200, 400);
        lista.setFont(new Font("Arial Nova Light", Font.BOLD, 20));

        DefaultListCellRenderer renderer = (DefaultListCellRenderer) lista.getCellRenderer();
        renderer.setHorizontalAlignment(SwingConstants.CENTER);

        //LISTENER
        ListSelectionListener lsl_list = new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    btn_delete.setEnabled(true);
                    btn_play.setEnabled(true);
                    indexSeleccionat = lista.getSelectedIndex();
                    nomPartida = (String) lista.getSelectedValue();
                    String[] data = bd.getDatosPartidaJList(nomPartida);

                    int id = 0;
                    try {
                        id = Integer.parseInt(data[0]);
                    } catch (NumberFormatException ex) {
                    }

                    String dificulty = data[1];
                    String estado = data[2];

                    info(id, dificulty, estado);
                }
            }
        };
        lista.addListSelectionListener(lsl_list);
        panel.add(lista);

        //SCROLL
        JScrollPane scroll = new JScrollPane(lista, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scroll.setBounds(70, 150, 200, 400);
        panel.add(scroll);
    }

    //metode que rellena les dades al costat del jlist
    private void info(int id, String dificultat, String estado) {
        //ID
        l_id.setText("ID: " + id);
        //l_id.setBounds(350, 150, 50, 50);
        //l_id.setFont(new Font("arial", Font.BOLD, 35));

        //NOM
        l_nom.setText("NAME: " + nomPartida);
        //l_nom.setBounds(430, 200, 100, 50);
        //l_nom.setFont(new Font("arial", Font.BOLD, 35));

        //TYPE
        l_dificultat.setText("TYPE: " + dificultat);
        //l_dificultat.setBounds(430, 260, 100, 50);
        //l_dificultat.setFont(new Font("arial", Font.BOLD, 35));

        //ESTADO
        l_estado.setText("STATE: " + estado);
        //l_estado.setBounds(430, 320, 135, 50);
        //l_estado.setFont(new Font("arial", Font.BOLD, 35));
        l_id.paintImmediately(l_id.getVisibleRect());
        panel.repaint();
        setVisible(true);
    }

    private void infoAfterDeleted() {
        //ID
        l_id.setText("ID: ");

        //NOM
        l_nom.setText("NAME: ");

        //TYPE
        l_dificultat.setText("TYPE: ");

        //ESTADO
        l_estado.setText("STATE: ");

        l_id.paintImmediately(l_id.getVisibleRect());
        panel.repaint();
        setVisible(true);
    }

    private void labels() {
        //LABELS
        l_id = new JLabel("ID:");
        l_nom = new JLabel("NAME:");
        l_dificultat = new JLabel("TYPE:");
        l_estado = new JLabel("STATE:");

        //ID
        l_id.setBounds(290, 150, 200, 50);
        l_id.setFont(new Font("arial", Font.ROMAN_BASELINE, 30));

        //NAME
        l_nom.setBounds(325, 200, 400, 50);
        l_nom.setFont(new Font("arial", Font.ROMAN_BASELINE, 30));

        //DIFICULTY
        l_dificultat.setBounds(325, 260, 200, 50);
        l_dificultat.setFont(new Font("arial", Font.ROMAN_BASELINE, 30));

        //ESTADO
        l_estado.setBounds(325, 320, 200, 50);
        l_estado.setFont(new Font("arial", Font.ROMAN_BASELINE, 30));

        panel.add(l_id);
        panel.add(l_nom);
        panel.add(l_dificultat);
        panel.add(l_estado);
    }

    private void botones() {
        btn_delete = new JButton("DELETE");
        btn_play = new JButton("PLAY");
        BufferedImage image = null;
        try {
            image = ImageIO.read(new File("menu4.png"));
        } catch (IOException e) {
            System.err.println("Error, " + e);
        }
        ImageIcon icon = new ImageIcon(fitimage(image, 200, 70));
        JButton btn_exit = new JButton(icon);

        //DELETE
        btn_delete.setBounds(550, 390, 125, 100);
        btn_delete.setFont(new Font("arial", Font.BOLD, 20));
        btn_delete.setBorder(BorderFactory.createLineBorder(Color.RED));
        btn_delete.setFocusable(false);
        btn_delete.setEnabled(false);
        btn_delete.setToolTipText("Delete game");

        //PLAY
        btn_play.setBounds(350, 390, 180, 100);
        btn_play.setFont(new Font("arial", Font.BOLD, 20));
        btn_play.setBorder(BorderFactory.createLineBorder(Color.ORANGE));
        btn_play.setFocusable(false);
        btn_play.setEnabled(false);
        btn_play.setToolTipText("Start game");

        //EXIT
        btn_exit.setBounds(480, 605, 200, 70);
        btn_exit.setFont(new Font("arial", Font.BOLD, 20));
        btn_exit.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
        btn_exit.setFocusable(false);
        btn_exit.setEnabled(true);
        btn_exit.setBorderPainted(false);
        btn_exit.setContentAreaFilled(false);
        btn_exit.setFocusPainted(false);
        btn_exit.setOpaque(false);
        btn_exit.setToolTipText("Go back to menu");

        //LISTENERS
        ActionListener listen_play = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                BaseDades bd = new BaseDades();
                Partida p = bd.getPartida(nomPartida);
                Graph_play gp = new Graph_play(p);
                gp.setVisible(true);
                setVisible(false);
                System.gc();
            }
        };
        btn_play.addActionListener(listen_play);

        ActionListener listen_del = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                BaseDades bd = new BaseDades();
                bd.deletePartida(nomPartida);
                modelo.remove(indexSeleccionat);
                infoAfterDeleted();
                lista.setFocusable(false);
            }
        };
        btn_delete.addActionListener(listen_del);

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

        panel.add(btn_play);
        panel.add(btn_delete);
        panel.add(btn_exit);
    }

    //metode que reescala la imatge
    private Image fitimage(Image img, int w, int h) {
        BufferedImage resizedimage = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = resizedimage.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2.drawImage(img, 0, 0, w, h, null);
        g2.dispose();
        return resizedimage;
    }

}
