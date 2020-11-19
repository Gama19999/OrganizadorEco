package organizadorEco;

import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;
import java.util.ArrayList;
import javax.swing.*;


public class Calendario extends JPanel implements ActionListener {
    static final String[] mes = {"Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio",
            "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"};
    final String[] days = {"DOM", "LUN", "MAR", "MIE", "JUE", "VIE", "SAB"};
    JPanel panelCalendario;
    JPanel comboContainer;
    JComboBox<String> month;
    JComboBox<Integer> year;
    JPanel panelMes;
    JLabel[] labelDias;
    JButton[] dias;

    JButton presionado;
    LocalDate hoy;
    FlowLayout flowLayout;
    CardLayout cardLayout;
    PantallaDia pantallaDia;
    int y, m, d;

    Calendario() {
        flowLayout = new FlowLayout(FlowLayout.CENTER, TaskPanel.HGAP, TaskPanel.VGAP);
        cardLayout = new CardLayout();
        this.setLayout(cardLayout);
        this.setBackground(GUI.colorPrincipal);

        panelCalendario = new JPanel(flowLayout);
        panelCalendario.setBackground(GUI.colorPrincipal);
        panelMes = new JPanel();
        panelMes.setLayout(new GridLayout(0, 7, 5, 5));
        panelMes.setBackground(GUI.colorPrincipal);
        comboContainer = new JPanel();
        comboContainer.setBackground(GUI.colorPrincipal);
        comboContainer.setLayout(flowLayout);

        labelDias = new JLabel[7];
        for (int i = 0; i < 7; i++) {
            labelDias[i] = new JLabel(days[i], JLabel.CENTER);
            labelDias[i].setFont(new Font(GUI.fuente, Font.PLAIN, 12));
            labelDias[i].setForeground(Color.white);
            labelDias[i].setPreferredSize(new Dimension(40, 40));
        }

        month = new JComboBox<>(mes);
        month.setFont(new Font(GUI.fuente, Font.PLAIN, 14));
        month.setBackground(GUI.colorTerciario);
        month.addActionListener(this);

        Integer[] a = new Integer[10];
        for (int i = 0; i < a.length; i++) {
            a[i] = 2020 + i;
        }
        year = new JComboBox<>(a);
        year.setFont(new Font(GUI.fuente, Font.PLAIN, 14));
        year.setBackground(GUI.colorTerciario);
        year.addActionListener(this);

        dias = new JButton[31];
        for (int d = 0; d < 31; ++d) {
            dias[d] = new JButton(String.valueOf(d + 1));
            dias[d].setName(String.valueOf(d + 1));
            dias[d].setBorder(null);
            dias[d].setBackground(GUI.colorTerciario);
            dias[d].setPreferredSize(new Dimension(40, 40));
            dias[d].setFont(new Font(GUI.fuente, Font.PLAIN, 12));
            dias[d].addActionListener(this);
        }

        hoy = LocalDate.now();
        y = hoy.getYear();
        m = hoy.getMonthValue();
        LocalDate primerDia = LocalDate.of(y, m, 1);
        int diaSemana = primerDia.getDayOfWeek().getValue();
        int duracion = hoy.getMonth().length(hoy.isLeapYear());
        makeCalendar(duracion, diaSemana);

        month.setSelectedIndex(hoy.getMonthValue() - 1);
        year.setSelectedItem(hoy.getYear());
        comboContainer.add(month);
        comboContainer.add(year);

        panelCalendario.add(comboContainer);
        panelCalendario.add(panelMes);

        pantallaDia = new PantallaDia(hoy);
        this.add(pantallaDia, "dia");
        this.add(panelCalendario, "calendario");
        cardLayout.show(this, "calendario");
        revalidate();
        repaint();
    }

    public void actualizarPaneles() {
        pantallaDia.actualizarPaneles();
        actualizarBotones();
        revalidate();
        repaint();
    }

    private void makeCalendar(int duracion, int diaSemana) {
        panelMes.removeAll();
        for (JLabel label : labelDias) {
            panelMes.add(label);
        }

        if (diaSemana != 7) {
            for (int i = 0; i < diaSemana; i++) {
                JPanel dummy = new JPanel();
                dummy.setBackground(GUI.colorPrincipal);
                panelMes.add(dummy);
            }
        }

        for (int d = 0; d < duracion; ++d) {
            panelMes.add(dias[d]);
        }
        actualizarBotones();
        revalidate();
        repaint();
    }

    private void actualizarBotones() {
        for (JButton dia : dias) {
            dia.setBackground(GUI.colorTerciario);
        }

        for (Pendiente pend : Organizador.pendientes) {
            if (pend.getYear() == y && pend.getMonth() == m) {
                dias[pend.getDay() - 1].setBackground(GUI.colorCuaternario);
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        y = (int)year.getSelectedItem();
        m = month.getSelectedIndex() + 1;
        if (e.getSource() == year || e.getSource() == month) {
            LocalDate primerDia = LocalDate.of(y, m, 1);
            int diaSemana = primerDia.getDayOfWeek().getValue();
            int duracion = primerDia.getMonth().length(primerDia.isLeapYear());
            for (JButton dia : dias) {
                dia.setBorder(null);
            }
            makeCalendar(duracion, diaSemana);
        }
        else {
            presionado = (JButton)e.getSource();
            d = Integer.parseInt(presionado.getText());
            LocalDate dia = LocalDate.of(y, m, d);
            cardLayout.removeLayoutComponent(pantallaDia);
            pantallaDia = new PantallaDia(dia);
            this.add(pantallaDia, "dia");
            cardLayout.show(this, "dia");
            actualizarPaneles();
        }
        revalidate();
        repaint();
    }

    private class PantallaDia extends JPanel implements ActionListener {
        final ImageIcon undoImg = new ImageIcon("imagenes/undo.png");
        int pantallaActual;
        LocalDate localDate;
        JButton addOne;
        JButton regresar;
        JButton back;
        JButton forward;
        JLabel pagLabel;
        JLabel fechaLabel;
        JPanel escritura;
        JTextField campo;
        CardLayout pantallas;
        ArrayList<JPanel> paginas;
        ArrayList<PendientePanel> pendientes;

        PantallaDia(LocalDate localDate) {
            pantallas = new CardLayout();
            paginas = new ArrayList<>();
            pendientes = new ArrayList<>();
            this.setLayout(pantallas);
            this.setBackground(GUI.colorPrincipal);
            this.localDate = localDate;

            addOne = new JButton(TaskPanel.plusImg);
            addOne.setBackground(null);
            addOne.setBorder(null);
            addOne.addActionListener(this);

            regresar = new JButton(undoImg);
            regresar.setBackground(null);
            regresar.setBorder(null);
            regresar.addActionListener(this);

            fechaLabel = new JLabel(localDate.toString());
            fechaLabel.setFont(new Font(GUI.fuente, Font.PLAIN, 14));
            fechaLabel.setOpaque(true);
            fechaLabel.setBackground(GUI.colorCuaternario);
            fechaLabel.setPreferredSize(new Dimension(90, 30));
            fechaLabel.setHorizontalAlignment(JLabel.CENTER);
            fechaLabel.setVerticalAlignment(JLabel.CENTER);

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

        private void actualizarPaneles() {
            int numPagsAntes = paginas.size();
            this.removeAll();
            paginas.clear();
            pendientes.clear();

            for (Pendiente pend : Organizador.pendientes) {
                if (pend.getFecha().equals(localDate)) {
                    String desc = pend.getDescripcion();
                    int y = pend.getYear();
                    int m = pend.getMonth();
                    int d = pend.getDay();
                    PendientePanel pendPan = new PendientePanel(desc, y, m, d);
                    pendientes.add(pendPan);
                }
            }
            int numeroPendietes = pendientes.size();
            if (numeroPendietes == 0) {
                JPanel panel = new JPanel(flowLayout);
                panel.setBackground(GUI.colorPrincipal);
                panel.add(fechaLabel);
                panel.add(addOne);
                panel.add(regresar);
                paginas.add(panel);
                this.add(panel, "0");
                pantallas.show(this, "0");
                revalidate();
                repaint();
                return;
            }

            for (int i = 0; i < numeroPendietes; i += 5) {
                JPanel panel = new JPanel(flowLayout);
                panel.setBackground(GUI.colorPrincipal);
                paginas.add(panel);
            }

            int numPagsDespues = paginas.size();
            if (numPagsAntes > numPagsDespues && pantallaActual == numPagsAntes - 1 && pantallaActual > 0) {
                pantallaActual--;
            }

            int indice = 0;
            for (JPanel pagina : paginas) {
                for (int j = 0; j < 5 && indice < numeroPendietes; j++) {
                    pagina.add(pendientes.get(indice));
                    indice++;
                }
            }

            colocarBotones();
            for (int i = 0; i < paginas.size(); i++) {
                this.add(paginas.get(i), Integer.toString(i));
            }
            pantallas.show(this, Integer.toString(pantallaActual));
            revalidate();
            repaint();
        }

        private void colocarBotones() {
            paginas.get(pantallaActual).add(fechaLabel, 0);
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
            pagLabel.setText((pantallaActual + 1) + " / " + paginas.size());
            paginas.get(pantallaActual).add(regresar);
            paginas.get(pantallaActual).add(pagLabel);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == addOne) {
                paginas.get(pantallaActual).remove(addOne);
                paginas.get(pantallaActual).remove(back);
                paginas.get(pantallaActual).remove(pagLabel);
                paginas.get(pantallaActual).remove(regresar);
                escritura = new JPanel();
                escritura.setPreferredSize(new Dimension(PendientePanel.WIDTH, PendientePanel.HEIGHT));
                escritura.setBackground(GUI.colorTerciario);
                campo = new JTextField();
                campo.setPreferredSize(new Dimension(PendientePanel.WIDTH - 10, PendientePanel.HEIGHT - 10));
                campo.setFont(new Font(GUI.fuente, Font.PLAIN, 14));
                campo.addActionListener(f -> {
                    String texto = campo.getText();
                    Organizador.agregarPendiente(texto, localDate);
                    Calendario.this.actualizarPaneles();
                    GUI.task.actualizarPaneles();
                });
                escritura.add(campo);
                paginas.get(pantallaActual).add(escritura);
            }
            else if (e.getSource() == regresar) {
                Calendario.this.cardLayout.show(Calendario.this, "calendario");
                Calendario.this.actualizarBotones();
                revalidate();
                repaint();
            }
            else {
                if (e.getSource() == back) {
                    if (pantallaActual >= 0) {
                        pantallaActual--;
                    }
                }
                else if (e.getSource() == forward) {
                    if (pantallaActual <= paginas.size()) {
                        pantallaActual++;
                    }
                }
                colocarBotones();
                pantallas.show(this, Integer.toString(pantallaActual));
            }
            revalidate();
            repaint();
        }
    }
}
