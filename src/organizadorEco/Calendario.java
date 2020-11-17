package organizadorEco;

import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;
import javax.swing.*;
import javax.swing.border.Border;


public class Calendario extends JPanel implements ActionListener {
    static final String[] mes = {"Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio",
            "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"};
    final String[] days = {"DOM", "LUN", "MAR", "MIE", "JUE", "VIE", "SAB"};
    boolean mesVista = true;

    JLabel[] labelDias;
    JPanel panelMes;
    JPanel comboContainer;
    JButton[] dias;
    JComboBox<String> month;
    JComboBox<Integer> year;
    LocalDate hoy;

    JButton addOne;
    JButton regresar;
    JButton presionado;
    JLabel fecha;
    JPanel escritura;
    JTextField campo;
    Border borde;

    int y, m, d;

    Calendario() {
        this.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));
        this.setBackground(GUI.colorPrincipal);

        panelMes = new JPanel();
        panelMes.setLayout(new GridLayout(0, 7, 5, 5));
        panelMes.setBackground(GUI.colorPrincipal);

        comboContainer = new JPanel();
        comboContainer.setBackground(GUI.colorPrincipal);
        comboContainer.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));

        addOne = new JButton();
        addOne.setIcon(new ImageIcon("imagenes/plus.png"));
        addOne.setBackground(null);
        addOne.setBorder(null);
        addOne.addActionListener(this);

        regresar = new JButton();
        regresar.setIcon(new ImageIcon("imagenes/undo.png"));
        regresar.setBackground(null);
        regresar.setBorder(null);
        regresar.addActionListener(this);

        campo = new JTextField();
        campo.setPreferredSize(new Dimension(290, 30));
        campo.setFont(new Font(GUI.fuente, Font.PLAIN, 14));
        campo.addActionListener(f -> {
            String texto = campo.getText();
            Organizador.agregarPendiente(texto, y, m, d);
            actualizarPaneles();
            GUI.task.actualizarPaneles();
        });

        escritura = new JPanel();
        escritura.setPreferredSize(new Dimension(300, 40));
        escritura.setBackground(GUI.colorTerciario);
        escritura.add(campo);

        labelDias = new JLabel[7];
        for (int i = 0; i < 7; i++) {
            labelDias[i] = new JLabel(days[i], JLabel.CENTER);
            labelDias[i].setFont(new Font(GUI.fuente, Font.PLAIN, 12));
            labelDias[i].setPreferredSize(new Dimension(35, 20));
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

        borde = BorderFactory.createLineBorder(GUI.colorCuaternario, 3, true);
        dias = new JButton[31];
        for (int d = 0; d < 31; ++d) {
            dias[d] = new JButton(String.valueOf(d + 1));
            dias[d].setName(String.valueOf(d + 1));
            dias[d].setBorder(null);
            dias[d].setBackground(GUI.colorSecundario);
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

        fecha = new JLabel();
        fecha.setFont(new Font(GUI.fuente, Font.PLAIN, 14));
        fecha.setText(hoy.toString());
        fecha.setOpaque(true);
        fecha.setBackground(GUI.colorCuaternario);
        fecha.setPreferredSize(new Dimension(90, 30));
        fecha.setHorizontalAlignment(JLabel.CENTER);
        fecha.setVerticalAlignment(JLabel.CENTER);

        makeCalendar(duracion, diaSemana);
        month.setSelectedIndex(hoy.getMonthValue() - 1);
        year.setSelectedItem(hoy.getYear());

        comboContainer.add(month);
        comboContainer.add(year);
        this.add(comboContainer);
        this.add(panelMes);
        revalidate();
        repaint();
    }

    public void actualizarPaneles() {
        if (!mesVista) {
            this.removeAll();
            LocalDate localDate = LocalDate.of(y, m, d);
            fecha.setText(localDate.toString());
            this.add(fecha);
            for (Pendiente pend : Organizador.pendientes) {
                if (pend.getFecha().equals(localDate)) {
                    String desc = pend.getDescripcion();
                    PendientePanel pendPan = new PendientePanel(desc, y, m, d);
                    this.add(pendPan);
                }
            }
            this.add(addOne);
            this.add(regresar);
        }
        else {
            actualizarBordes();
        }
        revalidate();
        repaint();
    }

    private void actualizarBordes() {
        for (JButton dia : dias) {
            dia.setBorder(null);
        }

        for (Pendiente pend : Organizador.pendientes) {
            if (pend.getYear() == y && pend.getMonth() == m) {
                dias[pend.getDay() - 1].setBorder(borde);
            }
        }
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
        actualizarBordes();
        revalidate();
        repaint();
    }

    public void regresarAlCalendario() {
        mesVista = true;
        this.removeAll();
        this.add(comboContainer);
        this.add(panelMes);
        actualizarBordes();
        revalidate();
        repaint();
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

        else if (e.getSource() == addOne) {
            this.remove(addOne);
            this.remove(regresar);
            this.add(escritura);
        }

        else if (e.getSource() == regresar) {
            regresarAlCalendario();
        }

        else {
            mesVista = false;
            presionado = (JButton)e.getSource();
            d = Integer.parseInt(presionado.getText());
            actualizarPaneles();
        }
        revalidate();
        repaint();
    }
}
