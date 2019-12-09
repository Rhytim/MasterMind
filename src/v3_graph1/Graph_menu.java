package v3_graph1;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

/**
 *
 * @author Marquinhos
 */
public class Graph_menu extends JFrame {

    JPanel panel;

    public Graph_menu() {
        setSize(635, 450); //establecemos el tamaño`
        setUndecorated(true);
        setLocationRelativeTo(null); //window center display
        setResizable(false); //no deja redimensionar
        getContentPane().setBackground(Color.LIGHT_GRAY); //color fondo

        crearPanel();
    }

    private void crearPanel() {
        panel = new JPanel(); //Creacion de un panel
        panel.setLayout(null);
        panel.setBorder(BorderFactory.createBevelBorder(1, Color.GRAY, Color.GRAY));
        
        menu();
        logo();
        botones();

        this.getContentPane().add(panel); //agregamos el panel a la ventana
    }
    
    private void menu(){
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

    private void logo() {
        JLabel t2 = new JLabel(new ImageIcon("logo.png"));

        t2.setSize(625, 200);

        panel.add(t2);
    }

    private void botones() {
        JButton btn_new = new JButton("New Game");
        JButton btn_load = new JButton("Load Game");
        JButton btn_exit = new JButton("EXIT");

        //NEW GAME
        btn_new.setBounds(100, 200, 200, 85);
        btn_new.setFont(new Font("arial", Font.BOLD, 25));
        btn_new.setBorder(BorderFactory.createLineBorder(Color.orange));
        btn_new.setFocusable(false);
        btn_new.setToolTipText("Start a new game");

        //LOAD GAME
        btn_load.setBounds(330, 200, 200, 85);
        btn_load.setFont(new Font("arial", Font.BOLD, 25));
        btn_load.setBorder(BorderFactory.createLineBorder(Color.orange));
        btn_load.setFocusable(false);
        btn_load.setToolTipText("Load a saved game");

        //EXIT 
        btn_exit.setBounds(230, 325, 170, 65);
        btn_exit.setFont(new Font("arial", Font.BOLD, 20));
        btn_exit.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
        btn_exit.setFocusable(false);
        btn_exit.setToolTipText("Exit game");

        //LISTENERS
        ActionListener listen_new = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Graph_newgame np = new Graph_newgame();
                setVisible(false);
                np.setVisible(true);
                System.gc();
            }
        };
        btn_new.addActionListener(listen_new);

        ActionListener listen_load = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Graph_loadgame lg = new Graph_loadgame();
                setVisible(false);
                lg.setVisible(true);
                System.gc();
            }
        };
        btn_load.addActionListener(listen_load);

        ActionListener listen_exit = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.gc();
                System.exit(0);
            }
        };
        btn_exit.addActionListener(listen_exit);

        panel.add(btn_new);
        panel.add(btn_load);
        panel.add(btn_exit);
    }

}
