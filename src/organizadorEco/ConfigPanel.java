package organizadorEco;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

class ConfigPanel extends JPanel implements ItemListener {
    final int WIDTH = 320;
    final int HEIGHT = 50;
    JPanel custom;
    JLabel cambiarFuente;
    JLabel cambiarTema;
    JTextField fuenteActual;
    String[] temas = {"Naturaleza, Acuario, Oscuro"};
    JComboBox<String> temasCombo;

    ConfigPanel() {
        this.setBackground(GUI.colorPrincipal);
        this.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 20));
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));

        custom = new JPanel();
        custom.setBackground(GUI.colorSecundario);
        custom.setPreferredSize(new Dimension(WIDTH - 20, HEIGHT));
        custom.setLayout(new FlowLayout(FlowLayout.CENTER, 80, 10));

        cambiarFuente = new JLabel("Fuente");
        cambiarFuente.setFont(new Font(GUI.fuente, Font.PLAIN, 14));

        cambiarTema = new JLabel("Tema");
        cambiarTema.setFont(new Font(GUI.fuente, Font.PLAIN, 14));

        fuenteActual = new JTextField(GUI.fuente);
        fuenteActual.setFont(new Font(GUI.fuente, Font.PLAIN, 14));
        fuenteActual.setForeground(Color.gray);
        fuenteActual.setBackground(GUI.colorSecundario);
        fuenteActual.addActionListener(e -> {
            // Ni la más mínima idea de que hacer aquí lol
            // Creo que se puede hacer leyendo un archivo de configuraciones o algo así
            // Pero la verdad si está muy pro para mí xd
        });

        temasCombo = new JComboBox<>(temas);
        temasCombo.setFont(new Font(GUI.fuente, Font.PLAIN, 14));
        temasCombo.setBackground(GUI.colorSecundario);
        temasCombo.addItemListener(this);

        custom.add(cambiarFuente);
        custom.add(fuenteActual);
        custom.add(cambiarTema);
        custom.add(temasCombo);

        this.add(custom);
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        if (e.getSource() == temasCombo) {
            switch ((String) temasCombo.getSelectedItem()) {
                case "Naturaleza":
                    // Do things
                    break;
                case "Acuario":
                    // Do more things
                    break;
                case "Oscuro":
                    // So many things to do
                    break;
                default:
                    // Do nothing
                    break;
            }
        }
    }
}
