package organizadorEco;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class TaskPanel extends JPanel implements ActionListener {
    JButton addOne;
    JPanel escritura;
    JTextField campo;

    TaskPanel() {
        this.setBackground(GUI.colorPrincipal);
        this.setOpaque(false);
        this.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));
        this.setPreferredSize(new Dimension(350, 480));
        addOne = new JButton();
        addOne.setIcon(new ImageIcon("imagenes/plus.png"));
        addOne.setBackground(null);
        addOne.setBorder(null);
        addOne.addActionListener(this);
        actualizarPaneles();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        this.remove(addOne);
        escritura = new JPanel();
        escritura.setPreferredSize(new Dimension(300, 40));
        escritura.setBackground(GUI.colorTerciario);
        campo = new JTextField();
        campo.setPreferredSize(new Dimension(290, 30));
        campo.setFont(new Font(GUI.fuente, Font.PLAIN, 14));
        campo.addActionListener(f -> {
            String texto = campo.getText();
            Organizador.agregarPendiente(texto);
            actualizarPaneles();
            GUI.calendar.actualizarPaneles();
        });
        escritura.add(campo);
        this.add(escritura);
        revalidate();
        repaint();
    }

    public void actualizarPaneles() {
        this.removeAll();
        for (Pendiente pend : Organizador.pendientes) {
            String texto = pend.getDescripcion();
            int a = pend.getYear();
            int m = pend.getMonth();
            int d = pend.getDay();
            PendientePanel panel = new PendientePanel(texto, a, m, d);
            this.add(panel);
        }
        this.add(addOne);
        revalidate();
        repaint();
    }
}