package organizadorEco;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class PendientePanel extends JPanel implements MouseListener, ItemListener {
    final int WIDTH = 300;
    final int HEIGHT = 40;
    final int SIZE = 15;
    int day, month, year;
    FlowLayout flowLayout;
    JLabel label;
    JTextArea area;
    JButton eliminar;
    JButton confirmar;
    JButton guardar;
    JComboBox<Integer> days;
    JComboBox<String> months;
    JComboBox<Integer> years;

    public PendientePanel(String descripcion, int a, int m, int d) {
        flowLayout = new FlowLayout(FlowLayout.CENTER, 32, 0);
        this.setLayout(flowLayout);
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        this.setBackground(GUI.colorTerciario);

        days = new JComboBox<>();
        months = new JComboBox<>();
        years = new JComboBox<>();
        for (int i = 1; i <= 31; i++) {
            if (i <= 12) months.addItem(Calendario.mes[i - 1]);
            if (i <= 10) years.addItem((2019 + i));
            days.addItem(i);
        }
        days.addItemListener(this);
        months.addItemListener(this);
        years.addItemListener(this);

        day = d;
        month = m;
        year = a;

        days.setSelectedItem(day);
        months.setSelectedItem(Calendario.mes[month - 1]);
        years.setSelectedItem(year);

        days.setFont(new Font(GUI.fuente, Font.PLAIN, 12));
        months.setFont(new Font(GUI.fuente, Font.PLAIN, 12));
        years.setFont(new Font(GUI.fuente, Font.PLAIN, 12));

        days.setBackground(GUI.colorCuaternario);
        months.setBackground(GUI.colorCuaternario);
        years.setBackground(GUI.colorCuaternario);

        label = new JLabel(descripcion, SwingConstants.CENTER);
        label.setFont(new Font(GUI.fuente, Font.PLAIN, SIZE));
        label.setVerticalAlignment(JLabel.CENTER);
        label.setVerticalTextPosition((JLabel.CENTER));
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setHorizontalTextPosition(JLabel.CENTER);
        label.setPreferredSize(new Dimension(WIDTH - 5, HEIGHT));

        area = new JTextArea();
        area.setFont(new Font(GUI.fuente, Font.PLAIN, SIZE));
        area.setText(descripcion);
        area.setWrapStyleWord(true);
        area.setLineWrap(true);
        area.setPreferredSize(new Dimension(WIDTH, 95));

        eliminar = new JButton();
        ImageIcon remove = new ImageIcon("imagenes/remove.png");
        eliminar.setIcon(remove);
        eliminar.setBackground(null);
        eliminar.setBorder(null);
        eliminar.addActionListener(e -> {
            Organizador.modificarPendiente(descripcion, area.getText(), year, month, day);
            GUI.deleted.borrar(area.getText());
            GUI.task.actualizarPaneles();
            GUI.calendar.actualizarPaneles();
        });

        confirmar = new JButton();
        ImageIcon checkmark = new ImageIcon("imagenes/checkmark.png");
        confirmar.setIcon(checkmark);
        confirmar.setBackground(null);
        confirmar.setBorder(null);
        confirmar.addActionListener(e -> {
            Organizador.modificarPendiente(descripcion, area.getText(), year, month, day);
            GUI.done.agregar(area.getText());
            GUI.task.actualizarPaneles();
            GUI.calendar.actualizarPaneles();
        });

        guardar = new JButton();
        ImageIcon save = new ImageIcon("imagenes/save.png");
        guardar.setIcon(save);
        guardar.setBackground(null);
        guardar.setBorder(null);
        guardar.addActionListener(e -> {
            this.removeAll();
            this.add(label);
            this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
            label.setText(area.getText());
            Organizador.modificarPendiente(descripcion, area.getText(), year, month, day);
            GUI.task.actualizarPaneles();
            GUI.calendar.actualizarPaneles();
            revalidate();
            repaint();
        });

        this.add(label);
        this.addMouseListener(this);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT + 150));
        flowLayout.setVgap(10);
        this.remove(label);
        this.add(area);
        this.add(days);
        this.add(months);
        this.add(years);
        this.add(confirmar);
        this.add(eliminar);
        this.add(guardar);
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

    @Override
    public void itemStateChanged(ItemEvent e) {
        if (e.getSource() == this.days) {
            this.day = (Integer)this.days.getSelectedItem();
        }
        if (e.getSource() == this.months) {
            this.month = this.months.getSelectedIndex() + 1;
        }
        if (e.getSource() == this.years) {
            this.year = (Integer)this.years.getSelectedItem();
        }
    }
}
