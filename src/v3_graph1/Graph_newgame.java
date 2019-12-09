package v3_graph1;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

/**
 *
 * @author Marquinhos
 */
public class Graph_newgame extends JFrame {

    JPanel panel;
    JTextField text;
    JRadioButton rbtn_easy;

    public Graph_newgame() {
        setSize(400, 250);
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

        name();
        radioBotons();
        botons();

        this.getContentPane().add(panel);
    }

    private void name() {
        JLabel name = new JLabel("Name: ");
        text = new JTextField();

        //etiqueta
        name.setBounds(15, 30, 100, 50);
        name.setFont(new Font("arial", Font.BOLD, 25));

        //texto
        text.setBounds(110, 30, 272, 50);
        text.setFocusable(true);
        text.setFont(new Font("arial", Font.PLAIN, 25));
        //text.setBorder(BorderFactory.createCompoundBorder(text.getBorder(),BorderFactory.createEmptyBorder(0, 5, 0, 5)));
        text.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));

        panel.add(name);
        panel.add(text);
    }

    private void radioBotons() {
        rbtn_easy = new JRadioButton("Easy Mode", true);
        JRadioButton rbtn_hard = new JRadioButton("Hard Mode", false);
        ButtonGroup gruporbtn = new ButtonGroup();

        //EASY
        rbtn_easy.setBounds(39, 100, 170, 50);
        rbtn_easy.setFont(new Font("arial", Font.BOLD, 25));

        //HARD
        rbtn_hard.setBounds(205, 100, 170, 50);
        rbtn_hard.setFont(new Font("arial", Font.BOLD, 25));

        //GROUP
        gruporbtn.add(rbtn_easy);
        gruporbtn.add(rbtn_hard);

        panel.add(rbtn_easy);
        panel.add(rbtn_hard);
    }

    private void botons() {
        JButton btn_play = new JButton("PLAY");
        JButton btn_cancel = new JButton("CANCEL");

        //CREATE
        btn_play.setEnabled(true);
        btn_play.setBounds(60, 170, 130, 50);
        btn_play.setFont(new Font("arial", Font.BOLD, 20));
        btn_play.setBorder(BorderFactory.createLineBorder(Color.orange));
        btn_play.setFocusable(false);
        btn_play.setToolTipText("Create game and play");

        //CANCEL
        btn_cancel.setEnabled(true);
        btn_cancel.setBounds(205, 170, 130, 50);
        btn_cancel.setFont(new Font("arial", Font.BOLD, 20));
        btn_cancel.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
        btn_cancel.setFocusable(false);
        btn_cancel.setToolTipText("Go back to menu");

        //LISTENERS
        ActionListener listen_create = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nomUser = text.getText();
                String dificulty;
                if (rbtn_easy.isSelected()) {
                    dificulty = "Easy";
                } else {
                    dificulty = "Hard";
                }

                if (!text.getText().equals("")) {
                    BaseDades bd = new BaseDades();
                    if (!bd.comprovaNomPartida(text.getText())) {
                        Partida p = new Partida(nomUser, dificulty);
                        
                        bd.insertarDatosPartida(p);

                        Graph_play gp = new Graph_play(p);
                        setVisible(false);
                        gp.setVisible(true);
                        System.gc();
                    }
                }
            }
        };
        btn_play.addActionListener(listen_create);

        ActionListener listen_cancel = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                Graph_menu mp = new Graph_menu();
                mp.setVisible(true);
            }
        };
        btn_cancel.addActionListener(listen_cancel);

        panel.add(btn_play);
        panel.add(btn_cancel);
    }

}
