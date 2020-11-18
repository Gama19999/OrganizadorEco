package organizadorEco;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class DeletedPanel extends JPanel {
    DeletedPanel() {
        this.setBackground(GUI.colorPrincipal);
        this.setOpaque(false);
        this.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));
        for (int i = 0; i < Organizador.eliminados.size(); i++) {
            String desc = Organizador.eliminados.get(i).getDescripcion();
            String fecha = Organizador.eliminados.get(i).getFechaStr();
            this.add(new PendienteDeleted(desc, fecha));
        }
    }

    public void borrar(String desc) {
        Organizador.eliminarPendiente(desc);
        actualizarPaneles();
    }

    private void actualizarPaneles() {
        this.removeAll();
        for (Pendiente pend : Organizador.eliminados) {
            PendienteDeleted deleted = new PendienteDeleted(
                    pend.getDescripcion(),
                    pend.getFechaStr());
            this.add(deleted);
        }
        revalidate();
        repaint();
    }

    private class PendienteDeleted extends JPanel implements MouseListener {
        final int WIDTH = 300;
        final int HEIGHT = 40;
        final int SIZE = 15;
        boolean desplegado;
        JLabel label;
        JLabel date;
        JTextArea area;
        JButton recuperar;
        JButton descartar;
        FlowLayout flowLayout;

        PendienteDeleted(String descripcion, String fecha) {
            flowLayout = new FlowLayout(FlowLayout.CENTER, 40, 0);
            this.setLayout(flowLayout);
            this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
            this.setBackground(GUI.colorTerciario);

            label = new JLabel(descripcion, SwingConstants.CENTER);
            label.setFont(new Font(GUI.fuente, Font.PLAIN, SIZE));
            label.setVerticalAlignment(JLabel.CENTER);
            label.setVerticalTextPosition(JLabel.CENTER);
            label.setHorizontalAlignment(JLabel.CENTER);
            label.setHorizontalTextPosition(JLabel.CENTER);
            label.setPreferredSize(new Dimension(WIDTH - 5, HEIGHT));

            area = new JTextArea();
            area.setFont(new Font(GUI.fuente, Font.PLAIN, SIZE));
            area.setText(descripcion);
            area.setWrapStyleWord(true);
            area.setLineWrap(true);
            area.setEditable(false);
            area.setBackground(GUI.colorTerciario);
            area.setPreferredSize(new Dimension(280, 95));

            date = new JLabel(fecha);
            date.setFont(new Font(GUI.fuente, Font.PLAIN, SIZE));
            date.setOpaque(true);
            date.setBackground(GUI.colorCuaternario);
            date.setPreferredSize(new Dimension(90, 30));
            date.setHorizontalAlignment(JLabel.CENTER);
            date.setVerticalAlignment(JLabel.CENTER);

            recuperar = new JButton(new ImageIcon("imagenes/refresh.png"));
            recuperar.setBackground(null);
            recuperar.setBorder(null);
            recuperar.addActionListener(e -> {
                Organizador.recuperarPendiente(area.getText());
                actualizarPaneles();
                GUI.task.actualizarPaneles();
            });

            descartar = new JButton(new ImageIcon("imagenes/close.png"));
            descartar.setBackground(null);
            descartar.setBorder(null);
            descartar.addActionListener(e -> {
                Organizador.eliminarPermanente(area.getText());
                actualizarPaneles();
            });

            desplegado = false;
            this.add(label);
            this.addMouseListener(this);
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            if (desplegado) {
                flowLayout.setVgap(0);
                this.removeAll();
                this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
                this.add(label);
            }
            else {
                flowLayout.setVgap(10);
                this.setPreferredSize(new Dimension(WIDTH, HEIGHT + 118));
                this.remove(label);
                this.add(area);
                this.add(date);
                this.add(recuperar);
                this.add(descartar);
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