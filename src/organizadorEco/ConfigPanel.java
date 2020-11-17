package organizadorEco;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.*;

class ConfigPanel extends JPanel implements ItemListener {
    final int WIDTH = 320;
    final int HEIGHT = 300;
    final static File config = new File("config.txt");
    static String fuenteStr;
    static String tituloStr;
    static String tema;
    JPanel custom;
    JLabel cambiarFuente;
    JLabel cambiarTema;
    JLabel cambiarTitulo;
    JTextField fuenteActual;
    JTextField tituloActual;
    JComboBox<String> temasCombo;
    String[] temas = {"Naturaleza", "Acuario", "Oscuro"};

    ConfigPanel() {
        this.setBackground(GUI.colorPrincipal);
        this.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 20));
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));

        custom = new JPanel();
        custom.setBackground(GUI.colorSecundario);
        custom.setPreferredSize(new Dimension(WIDTH - 20, HEIGHT));
        custom.setLayout(new FlowLayout(FlowLayout.CENTER, 150, 10));

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
        fuenteActual.setBackground(GUI.colorSecundario);
        fuenteActual.addActionListener(e -> {
            fuenteStr = fuenteActual.getText();
            fuenteActual.setBackground(GUI.colorTerciario);
        });

        tituloActual = new JTextField(GUI.tituloStr);
        tituloActual.setFont(new Font(GUI.fuente, Font.PLAIN, 14));
        tituloActual.setBackground(GUI.colorSecundario);
        tituloActual.addActionListener(e -> {
            tituloStr = tituloActual.getText();
            tituloActual.setBackground(GUI.colorTerciario);
        });

        temasCombo = new JComboBox<>(temas);
        temasCombo.setFont(new Font(GUI.fuente, Font.PLAIN, 14));
        temasCombo.setBackground(GUI.colorSecundario);
        temasCombo.addItemListener(this);

        custom.add(cambiarFuente);
        custom.add(fuenteActual);
        custom.add(cambiarTitulo);
        custom.add(tituloActual);
        custom.add(cambiarTema);
        custom.add(temasCombo);
        this.add(custom);
    }

    public static void aplicarConfiguracion() {
        try {
            if (config.createNewFile()) {
                fuenteStr = GUI.fuente;
                tituloStr = GUI.tituloStr;
                tema = "Naturaleza";
            }
            else {
                FileReader lector = new FileReader(config);
                int data = lector.read();
                // No haced esto en casa chicos
                fuenteStr = "";
                while (data != '\n' && data != '\r' && data != -1) {
                    fuenteStr += (char) data;
                    data = lector.read();
                }
                data = lector.read();
                GUI.fuente = fuenteStr;

                tituloStr = "";
                while (data != '\n' && data != '\r' && data != -1) {
                    tituloStr += (char) data;
                    data = lector.read();
                }
                GUI.tituloStr = tituloStr;
                tema = "Naturaleza";
                lector.close();
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
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
            tema = (String)temasCombo.getSelectedItem();
        }
    }
}
