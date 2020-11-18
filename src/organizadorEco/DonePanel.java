package organizadorEco;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.concurrent.Flow;

public class DonePanel extends JPanel {
    JLabel puntaje;

    DonePanel() {
        this.setBackground(new Color(0x27AE6A));
        this.setOpaque(false);
        this.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));
        puntaje = new JLabel(Integer.toString(Organizador.realizados.size()));
        puntaje.setFont(new Font(GUI.fuente, Font.BOLD, 20));
        puntaje.setForeground(Color.white);
        actualizarPaneles();
    }

    private void actualizarPaneles() {
        this.removeAll();
        for (Pendiente pend : Organizador.realizados) {
            PendienteDone hecho = new PendienteDone(pend.getDescripcion(), pend.getFechaStr());
            this.add(hecho);
            this.add(new JLabel(new ImageIcon("imagenes/medal.png")));
        }
        puntaje.setText(Integer.toString(Organizador.realizados.size()));
        this.add(puntaje);
        revalidate();
        repaint();
    }

    public void agregar(String desc) {
        Organizador.marcarCompletado(desc);
        actualizarPaneles();
    }

    private class PendienteDone extends JPanel implements MouseListener {
        final int WIDTH = 250;
        final int HEIGHT = 40;
        final int SIZE = 15;
        boolean desplegado;
        JLabel label;
        JLabel date;
        JTextArea area;
        JButton restaurar;
        FlowLayout flowLayout;

        PendienteDone(String descripcion, String fecha) {
            flowLayout = new FlowLayout(FlowLayout.CENTER, 40, 0);
            this.setLayout(flowLayout);
            this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
            this.setBackground(new Color(0xAFF478));

            label = new JLabel(descripcion, SwingConstants.CENTER);
            label.setFont(new Font(GUI.fuente, Font.PLAIN, SIZE));
            label.setVerticalAlignment(JLabel.CENTER);
            label.setHorizontalAlignment(JLabel.CENTER);
            label.setVerticalTextPosition(JLabel.CENTER);
            label.setVerticalAlignment(JLabel.CENTER);
            label.setPreferredSize(new Dimension(WIDTH - 5, HEIGHT));

            area = new JTextArea();
            area.setFont(new Font(GUI.fuente, Font.PLAIN, SIZE));
            area.setText(descripcion);
            area.setWrapStyleWord(true);
            area.setLineWrap(true);
            area.setEditable(false);
            area.setBackground(GUI.colorTerciario);
            area.setPreferredSize(new Dimension(WIDTH - 20, 95));
            // area.setBorder(BorderFactory.createLineBorder(Color.black));

            date = new JLabel(fecha);
            date.setFont(new Font(GUI.fuente, Font.PLAIN, SIZE));
            date.setOpaque(true);
            date.setBackground(GUI.colorCuaternario);
            date.setPreferredSize(new Dimension(90, 30));
            date.setHorizontalAlignment(JLabel.CENTER);
            date.setVerticalAlignment(JLabel.CENTER);

            restaurar = new JButton(new ImageIcon("imagenes/refresh.png"));
            restaurar.setBackground(null);
            restaurar.setBorder(null);
            restaurar.addActionListener(e -> {
                Organizador.marcarNoCompletado(descripcion);
                actualizarPaneles();
                GUI.task.actualizarPaneles();
            });

            desplegado = false;
            this.addMouseListener(this);
            this.add(label);
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            if (desplegado) {
                flowLayout.setVgap(0);
                this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
                this.removeAll();
                this.add(label);
            }
            else {
                flowLayout.setVgap(10);
                this.setPreferredSize(new Dimension(WIDTH, HEIGHT + 118));
                this.remove(label);
                this.add(area);
                this.add(date);
                this.add(restaurar);
            }
            desplegado = !desplegado;
            revalidate();
            repaint();
        }

        @Override
        public void mousePressed(MouseEvent e) { }

        @Override
        public void mouseReleased(MouseEvent e) { }

        @Override
        public void mouseEntered(MouseEvent e) { }

        @Override
        public void mouseExited(MouseEvent e) { }
    }
}
