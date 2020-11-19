package organizadorEco;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

public class DeletedPanel extends JPanel implements ActionListener {
    int pantallaActual;
    FlowLayout flowLayout;
    CardLayout cardLayout;
    ArrayList<JPanel> paginas;
    JButton back;
    JButton forward;
    JLabel pagLabel;

    DeletedPanel() {
        flowLayout = new FlowLayout(FlowLayout.CENTER, TaskPanel.HGAP, TaskPanel.VGAP);
        cardLayout = new CardLayout();
        paginas = new ArrayList<>();
        this.setBackground(GUI.colorPrincipal);
        this.setLayout(cardLayout);
        back = new JButton(TaskPanel.backImg);
        back.setBackground(null);
        back.setBorder(null);
        back.addActionListener(this);

        forward = new JButton(TaskPanel.forwardImg);
        forward.setBackground(null);
        forward.setBorder(null);
        forward.addActionListener(this);

        pagLabel = new JLabel("1/1");
        pagLabel.setFont(new Font(GUI.fuente, Font.PLAIN, 14));
        pagLabel.setForeground(Color.white);
        pagLabel.setPreferredSize(new Dimension(PendientePanel.WIDTH, 32));
        pagLabel.setHorizontalAlignment(JLabel.CENTER);
        pagLabel.setVerticalAlignment(JLabel.CENTER);
        actualizarPaneles();
    }

    public void agregarEliminados(String desc) {
        Organizador.eliminarPendiente(desc);
        actualizarPaneles();
    }

    public void actualizarPaneles() {
        int numPagsAntes = paginas.size();
        this.removeAll();
        paginas.clear();
        int numeroEliminados = Organizador.eliminados.size();

        if (numeroEliminados == 0) {
            JPanel panel = new JPanel(flowLayout);
            panel.setBackground(GUI.colorPrincipal);
            paginas.add(panel);
            this.add(panel, "0");
            cardLayout.show(this, "0");
            return;
        }

        for (int i = 0; i < numeroEliminados; i += 6) {
            JPanel panel = new JPanel(flowLayout);
            panel.setBackground(GUI.colorPrincipal);
            paginas.add(panel);
        }

        int numPagsDespues = paginas.size();
        if (numPagsAntes > numPagsDespues && pantallaActual == numPagsDespues - 1) {
            pantallaActual--;
        }

        int indice = 0;
        for (JPanel pagina : paginas) {
            for (int j = 0; j < 6 && indice < numeroEliminados; j++) {
                String texto = Organizador.eliminados.get(indice).getDescripcion();
                String fecha = Organizador.eliminados.get(indice).getFechaStr();
                PendienteDeleted pend = new PendienteDeleted(texto, fecha);
                pagina.add(pend);
                indice++;
            }
        }

        for (int i = 0; i < paginas.size(); i++) {
            this.add(paginas.get(i), Integer.toString(i));
        }
        cardLayout.show(this, Integer.toString(pantallaActual));
        colocarBotones();
        revalidate();
        repaint();
    }

    private void colocarBotones() {
        if (paginas.size() > 1) {
            if (pantallaActual == 0) {
                paginas.get(0).add(forward);
            }
            else if (pantallaActual == paginas.size() - 1) {
                paginas.get(pantallaActual).add(back);
            }
            else {
                paginas.get(pantallaActual).add(back);
                paginas.get(pantallaActual).add(forward);
            }
        }
        pagLabel.setText((pantallaActual + 1) + " / " + paginas.size());
        paginas.get(pantallaActual).add(pagLabel);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == back) {
            if (pantallaActual >= 0) {
                pantallaActual--;
            }
        }
        else {
            if (pantallaActual <= paginas.size()) {
                pantallaActual++;
            }
        }
        colocarBotones();
        cardLayout.show(this, Integer.toString(pantallaActual));
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
            flowLayout = new FlowLayout(FlowLayout.CENTER, 30, 0);
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
            area.setPreferredSize(new Dimension(WIDTH - 20, 80));

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
                flowLayout.setVgap(7);
                this.setPreferredSize(new Dimension(WIDTH, HEIGHT + 95));
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