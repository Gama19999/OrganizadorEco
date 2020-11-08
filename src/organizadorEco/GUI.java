package organizadorEco;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

public class GUI {

    JFrame frame;
    JPanel header;
    String tituloStr;
    JLabel titulo;
    JPanel footer;
    JButton hechos;
    JButton calendario;
    JButton home;
    JButton basura;
    JButton config;
    JPanel principal;
    String fuente;

    public GUI() {

    	//Rectangulo de la aplicaci�n
        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(350, 600);
        frame.setTitle("Organizador");
        frame.getContentPane().setBackground(new Color(0x27AE60));
        frame.setLayout(new BorderLayout(0, 0));
        frame.setLocationRelativeTo(null);

        //Recuadro que contiene el titulo
        header = new JPanel();
        header.setBackground(new Color(0xB0FFA3));
        header.setPreferredSize(new Dimension(350, 60));
        header.setLayout(new BorderLayout(0, 0));

        // Fuente
        fuente = "Montserrat";

        //Texto del titulo
        tituloStr = "Just do that.";
        titulo = new JLabel(tituloStr);
        titulo.setFont(new Font(fuente, Font.BOLD, 20));
        titulo.setVerticalAlignment(JLabel.CENTER);
        titulo.setHorizontalAlignment(JLabel.CENTER);
        titulo.setForeground(Color.white);
        header.add(titulo);

        //Recuadro que contiene los botones en la parte de abajo
        footer = new JPanel();
        footer.setBackground(new Color(0x7EF36B));
        footer.setPreferredSize(new Dimension(350, 60));
        footer.setLayout(new FlowLayout(FlowLayout.CENTER, 30, 15));

        //Creaci�n de los "Botones"
        hechos = new JButton();
        hechos.setIcon(new ImageIcon("imagenes/checkbox.png"));
        hechos.setBackground(null);
        hechos.setBorder(null);
        hechos.addActionListener(e -> System.out.println("Hola, esta es la accion del boton de hechos ao"));
        
        calendario = new JButton();
        calendario.setIcon(new ImageIcon("imagenes/calendar.png"));
        calendario.setBackground(null);
        calendario.setBorder(null);
        calendario.addActionListener(e -> System.out.println("Hola, esta es la accion del boton de calendario ao x2"));
        
        home = new JButton();
        home.setIcon(new ImageIcon("imagenes/home.png"));
        home.setBackground(null);
        home.setBorder(null);
        home.addActionListener(e -> System.out.println("Hola, esta es la accion del boton de home ao x3"));
        
        basura = new JButton();
        basura.setIcon(new ImageIcon("imagenes/trash.png"));
        basura.setBackground(null);
        basura.setBorder(null);
        basura.addActionListener(e -> System.out.println("Hola, esta es la accion del boton de basura ao x4"));
        
        config = new JButton();
        config.setIcon(new ImageIcon("imagenes/gear.png"));
        config.setBackground(null);
        config.setBorder(null);
        config.addActionListener(e -> System.out.println("Hola, esta es la accion del boton de configuarcion ao x10000"));

        JButton[] imagenes = {hechos, calendario, home, basura, config};

        //Adicion de los botones al footer
        for (JButton imagen : imagenes) {
           footer.add(imagen);
        }

        //Panel Principal
        principal = new JPanel();
        principal.setBackground(new Color(0x27AE60));
        principal.setPreferredSize(new Dimension(300, 100));
        principal.setLayout(new CardLayout());

        // Panel tasks
        TaskPanel task = new TaskPanel();

        // Adici�n de las "cartas" principal
        principal.add(task);

        //Adici�n de los elementos al frame
        frame.add(header, BorderLayout.NORTH);
        frame.add(footer, BorderLayout.SOUTH);
        frame.add(principal, BorderLayout.CENTER);
        frame.setVisible(true);
    }

    private class TaskPanel extends JPanel implements ActionListener {
        ArrayList<PendientePanel> paneles;
        JButton addOne;
        JPanel escritura;
        JTextField campo;

        TaskPanel() {
            this.setBackground(new Color(0x27AE6A));
            this.setSize(new Dimension(200, 100));
            this.setOpaque(false);
            this.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));
            paneles = new ArrayList<>();
            for (int i = 0; i < Organizador.pendientes.size(); i++) {
                String desc = Organizador.pendientes.get(i).getDescripcion();
                paneles.add(new PendientePanel(desc, i));
            }
            addOne = new JButton();
            addOne.setIcon(new ImageIcon("imagenes/plus.png"));
            addOne.setBackground(null);
            addOne.setBorder(null);
            addOne.addActionListener(this);
            this.add(addOne);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            this.remove(addOne);
            escritura = new JPanel();
            escritura.setPreferredSize(new Dimension(300, 50));
            escritura.setBackground(new Color(0xAFF478));
            campo = new JTextField();
            campo.setPreferredSize(new Dimension(280, 40));
            campo.setFont(new Font(fuente, Font.PLAIN, 12));
            campo.addActionListener(f -> {
                String texto = campo.getText();
                Organizador.agregarPendiente(texto);
                int indice = Organizador.pendientes.size() - 1;
                PendientePanel pendiente = new PendientePanel(texto, indice);
                paneles.add(pendiente);
                actualizarPaneles();
            });
            escritura.add(campo);
            this.add(escritura);
            revalidate();
            repaint();
        }

        private void actualizarPaneles() {
            this.removeAll();
            for (JPanel panel : paneles) {
                this.add(panel);
            }
            this.add(addOne);
            revalidate();
            repaint();
        }

        private class PendientePanel extends JPanel implements MouseListener {
            final int WIDTH = 300;
            final int HEIGHT = 50;
            final int HGAP = 12;
            final int VGAP = 15;
            final int COLS = 17;
            boolean clickeado = false;
            JLabel label;
            JTextArea area;
            int indice;

            PendientePanel(String descripcion, int indice) {
                this.indice = indice;
                this.setLayout(new FlowLayout(FlowLayout.LEFT, HGAP, VGAP));
                this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
                this.setBackground(new Color(0xAFF478));
                label = new JLabel(descripcion, SwingConstants.LEFT);
                label.setVerticalAlignment(SwingConstants.CENTER);
                label.setFont(new Font(fuente, Font.PLAIN, 15));
                label.setPreferredSize(new Dimension(250, 20));
                area = new JTextArea();
                area.setFont(new Font(fuente, Font.PLAIN, 15));
                // area.setBackground(new Color(0xAFF478));
                area.setText(descripcion);
                area.setColumns(COLS);
                area.setWrapStyleWord(true);
                area.setLineWrap(true);
                this.add(label);
                this.add(area);
                this.addMouseListener(this);
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                clickeado = !clickeado;
                if (clickeado) {
                    this.remove(label);
                    this.add(area);
                    this.setPreferredSize(new Dimension(WIDTH, HEIGHT + area.getHeight()));
                } else {
                    this.remove(area);
                    this.add(label);
                    this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
                    label.setText(area.getText());
                    Organizador.modificarPendiente(area.getText(), indice);
                }
                revalidate();
                repaint();
            }

            @Override
            public void mousePressed(MouseEvent e) {
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }
        }
    }
}
