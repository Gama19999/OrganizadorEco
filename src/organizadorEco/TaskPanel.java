package organizadorEco;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class TaskPanel extends JPanel implements ActionListener {
    static final int HGAP = 15;
    static final int VGAP = 15;
    final ImageIcon plusImg = new ImageIcon("imagenes/plus.png");
    final ImageIcon backImg = new ImageIcon("imagenes/back.png");
    final ImageIcon forwardImg = new ImageIcon("imagenes/forward.png");
    int pantallaActual;
    JButton addOne;
    JButton back;
    JButton forward;
    JPanel escritura;
    JTextField campo;
    CardLayout cardLayout;
    FlowLayout flowLayout;
    ArrayList<JPanel> paginas;

    TaskPanel() {
        cardLayout = new CardLayout();
        flowLayout = new FlowLayout(FlowLayout.CENTER, HGAP, VGAP);
        paginas = new ArrayList<>();
        this.setBackground(GUI.colorPrincipal);
        this.setLayout(cardLayout);
        addOne = new JButton(plusImg);
        addOne.setBackground(null);
        addOne.setBorder(null);
        addOne.addActionListener(this);

        back = new JButton(backImg);
        back.setBackground(null);
        back.setBorder(null);
        back.addActionListener(this);

        forward = new JButton(forwardImg);
        forward.setBackground(null);
        forward.setBorder(null);
        forward.addActionListener(this);

        pantallaActual = 0;
        actualizarPaneles();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addOne) {
            paginas.get(pantallaActual).remove(addOne);
            paginas.get(pantallaActual).remove(back);
            escritura = new JPanel();
            escritura.setPreferredSize(new Dimension(PendientePanel.WIDTH, PendientePanel.HEIGHT));
            escritura.setBackground(GUI.colorTerciario);
            campo = new JTextField();
            campo.setPreferredSize(new Dimension(PendientePanel.WIDTH - 10, PendientePanel.HEIGHT - 10));
            campo.setFont(new Font(GUI.fuente, Font.PLAIN, 14));
            campo.addActionListener(f -> {
                String texto = campo.getText();
                Organizador.agregarPendiente(texto);
                actualizarPaneles();
                GUI.calendar.actualizarPaneles();
            });
            escritura.add(campo);
            paginas.get(pantallaActual).add(escritura);
        }
        else {
            if (e.getSource() == back) {
                pantallaActual--;
            }
            if (e.getSource() == forward) {
                pantallaActual++;
            }
            colocarBotones();
            cardLayout.show(this, Integer.toString(pantallaActual));
        }
        revalidate();
        repaint();
    }

    public void actualizarPaneles() {
        int numPagsAntes = paginas.size();
        this.removeAll();
        paginas.clear();
        int numeroPendietes = Organizador.pendientes.size();
        if (numeroPendietes == 0) {
            JPanel panel = new JPanel(flowLayout);
            panel.setBackground(GUI.colorPrincipal);
            panel.add(addOne);
            paginas.add(panel);
            this.add(panel, "0");
            cardLayout.show(this, "0");
            return;
        }

        for (int i = 0; i < numeroPendietes; i += 5) {
            JPanel panel = new JPanel(flowLayout);
            panel.setBackground(GUI.colorPrincipal);
            paginas.add(panel);
        }

        int numPagsDespues = paginas.size();
        if (numPagsAntes > numPagsDespues) {
            pantallaActual--;
        }

        int indice = 0;
        for (JPanel pagina : paginas) {
            for (int j = 0; j < 5 && indice < numeroPendietes; j++) {
                String texto = Organizador.pendientes.get(indice).getDescripcion();
                int a = Organizador.pendientes.get(indice).getYear();
                int m = Organizador.pendientes.get(indice).getMonth();
                int d = Organizador.pendientes.get(indice).getDay();
                PendientePanel pendPan = new PendientePanel(texto, a, m, d);
                pagina.add(pendPan);
                indice++;
            }
        }

        colocarBotones();

        for (int i = 0; i < paginas.size(); i++) {
            this.add(paginas.get(i), Integer.toString(i));
        }
        cardLayout.show(this, Integer.toString(pantallaActual));
        revalidate();
        repaint();
    }

    private void colocarBotones() {
        if (pantallaActual == 0) {
            if (paginas.size() > 1) {
                paginas.get(0).add(forward);
            }
            else {
                paginas.get(0).add(addOne);
            }
        }
        else if (pantallaActual == paginas.size() - 1) {
            paginas.get(pantallaActual).add(back);
            paginas.get(pantallaActual).add(addOne);
        }
        else {
            paginas.get(pantallaActual).add(back);
            paginas.get(pantallaActual).add(forward);
        }
    }
}