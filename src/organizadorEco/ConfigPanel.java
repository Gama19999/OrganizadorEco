package organizadorEco;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.*;

class ConfigPanel extends JPanel implements ItemListener, ActionListener {
    final int WIDTH = 300;
    final int HEIGHT = 250;
    final static File config = new File("config.txt");
    static String fuenteStr;
    static String tituloStr;
    static int tema;
    JPanel custom;
    JPanel otros;
    JLabel cambiarFuente;
    JLabel cambiarTema;
    JLabel cambiarTitulo;
    JTextField fuenteActual;
    JTextField tituloActual;
    JComboBox<String> temasCombo;
    JButton reestablecer;
    String[] temas = {"Naturaleza", "Acuario", "Oscuro"};

    ConfigPanel() {
        this.setBackground(GUI.colorPrincipal);
        this.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 20));
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));

        custom = new JPanel();
        custom.setBackground(GUI.colorTerciario);
        custom.setPreferredSize(new Dimension(WIDTH - 20, HEIGHT));
        custom.setLayout(new FlowLayout(FlowLayout.CENTER, 150, 10));

        otros = new JPanel();
        otros.setBackground(GUI.colorTerciario);
        otros.setPreferredSize(new Dimension(WIDTH - 20, HEIGHT - 202));
        otros.setLayout(new FlowLayout(FlowLayout.CENTER, 150, 10));

        fuenteStr = GUI.fuente;
        tituloStr = GUI.tituloStr;
        tema = GUI.tema;

        cambiarFuente = new JLabel("Fuente");
        cambiarFuente.setFont(new Font(GUI.fuente, Font.PLAIN, 14));
        cambiarFuente.setVerticalAlignment(JLabel.CENTER);
        cambiarFuente.setHorizontalAlignment(JLabel.CENTER);
        cambiarFuente.setHorizontalTextPosition(JLabel.CENTER);
        cambiarFuente.setPreferredSize(new Dimension(WIDTH - 40, 20));

        cambiarTitulo = new JLabel("Titulo");
        cambiarTitulo.setFont(new Font(GUI.fuente, Font.PLAIN, 14));
        cambiarTitulo.setVerticalAlignment(JLabel.CENTER);
        cambiarTitulo.setHorizontalAlignment(JLabel.CENTER);
        cambiarTitulo.setHorizontalTextPosition(JLabel.CENTER);
        cambiarTitulo.setPreferredSize(new Dimension(WIDTH - 40, 20));

        cambiarTema = new JLabel("Tema");
        cambiarTema.setFont(new Font(GUI.fuente, Font.PLAIN, 14));

        fuenteActual = new JTextField(GUI.fuente);
        fuenteActual.setFont(new Font(GUI.fuente, Font.PLAIN, 14));
        fuenteActual.setBackground(Color.white);
        fuenteActual.addActionListener(e -> {
            fuenteStr = fuenteActual.getText();
            fuenteActual.setBackground(GUI.colorTerciario);
        });

        tituloActual = new JTextField(GUI.tituloStr);
        tituloActual.setFont(new Font(GUI.fuente, Font.PLAIN, 14));
        tituloActual.setBackground(Color.white);
        tituloActual.addActionListener(e -> {
            tituloStr = tituloActual.getText();
            tituloActual.setBackground(GUI.colorTerciario);
        });

        temasCombo = new JComboBox<>(temas);
        temasCombo.setFont(new Font(GUI.fuente, Font.PLAIN, 14));
        temasCombo.setBackground(GUI.colorTerciario);
        temasCombo.addItemListener(this);
        temasCombo.setSelectedIndex(tema);

        reestablecer = new JButton("Reestablecer");
        reestablecer.setBackground(GUI.colorTerciario);
        reestablecer.setForeground(Color.black);
        reestablecer.addActionListener(this);
        reestablecer.setFont(new Font(GUI.fuente, Font.BOLD, 14));

        custom.add(cambiarFuente);
        custom.add(fuenteActual);
        custom.add(cambiarTitulo);
        custom.add(tituloActual);
        custom.add(cambiarTema);
        custom.add(temasCombo);
        otros.add(reestablecer);
        this.add(custom);
        this.add(otros);
    }

    public static void guardarConfiguracion() {
        try {
            FileWriter escritor = new FileWriter(config);
            escritor.write(fuenteStr + "\n" + tituloStr + "\n" + tema);
            escritor.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        if (e.getSource() == temasCombo) {
            tema = temasCombo.getSelectedIndex();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == reestablecer) {
            config.delete();
            Organizador.reestablecer();
        }
    }
}
