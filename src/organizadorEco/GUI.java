package organizadorEco;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class GUI implements WindowListener {
    JFrame frame;
    JPanel header;
    JLabel titulo;
    JPanel footer;
    JButton hechos;
    JButton calendario;
    JButton home;
    JButton basura;
    JButton config;
    JPanel principal;

    static String fuente = "Montserrat";
    static String tituloStr = "Just do that.";

    static Color colorPrincipal = new Color(0x27AE60);
    static Color colorSecundario = new Color(0x7FEE71);
    static Color colorTerciario = new Color(0xAFF478);
    static Color colorCuaternario = new Color(0xFCF678);

    public static TaskPanel task;
    public static DonePanel done;
    public static DeletedPanel deleted;
    public static Calendario calendar;
    ConfigPanel settings;


    public GUI() {
    	//Rectangulo de la aplicación
        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(350, 600);
        frame.setTitle("Organizador");
        frame.setLayout(new BorderLayout(0, 0));
        frame.setLocationRelativeTo(null);
        frame.addWindowListener(this);
        frame.setIconImage(new ImageIcon("imagenes/checkbox.png").getImage());

        //Panel Principal
        principal = new JPanel();
        principal.setBackground(colorPrincipal);
        CardLayout pantallas = new CardLayout();
        principal.setLayout(pantallas);

        // Haciendo las cartas
        task = new TaskPanel();
        done = new DonePanel();
        deleted = new DeletedPanel();
        settings = new ConfigPanel();
        calendar = new Calendario();
        principal.add(task, "task");
        principal.add(done, "done");
        principal.add(deleted, "deleted");
        principal.add(settings, "settings");
        principal.add(calendar, "calendar");
        pantallas.show(principal, "task");

        //Recuadro que contiene el titulo
        header = new JPanel();
        header.setBackground(colorSecundario);
        header.setPreferredSize(new Dimension(350, 60));
        header.setLayout(new BorderLayout(0, 0));

        //Texto del titulo
        titulo = new JLabel(tituloStr);
        titulo.setFont(new Font(fuente, Font.BOLD, 20));
        titulo.setVerticalAlignment(JLabel.CENTER);
        titulo.setHorizontalAlignment(JLabel.CENTER);
        titulo.setForeground(Color.white);
        header.add(titulo);

        //Recuadro que contiene los botones en la parte de abajo
        footer = new JPanel();
        footer.setBackground(colorSecundario);
        footer.setPreferredSize(new Dimension(350, 60));
        footer.setLayout(new FlowLayout(FlowLayout.CENTER, 30, 15));

        //Creación de los "Botones"
        hechos = new JButton(new ImageIcon("imagenes/checkbox.png"));
        hechos.setBackground(null);
        hechos.setBorder(null);
        hechos.addActionListener(e -> pantallas.show(principal, "done"));

        //Calendari0
        calendario = new JButton();
        calendario.setIcon(new ImageIcon("imagenes/calendar.png"));
        calendario.setBackground(null);
        calendario.setBorder(null);
        calendario.addActionListener(e -> {});

        home = new JButton();
        home.setIcon(new ImageIcon("imagenes/home.png"));
        home.setBackground(null);
        home.setBorder(null);

        calendario = new JButton(new ImageIcon("imagenes/calendar.png"));
        calendario.setBackground(null);
        calendario.setBorder(null);
        calendario.addActionListener(e -> pantallas.show(principal, "calendar"));
        
        home = new JButton(new ImageIcon("imagenes/home.png"));
        home.setBackground(null);
        home.setBorder(null);
        home.addActionListener(e -> pantallas.show(principal, "task"));

        basura = new JButton(new ImageIcon("imagenes/trash.png"));
        basura.setBackground(null);
        basura.setBorder(null);
        basura.addActionListener(e -> pantallas.show(principal, "deleted"));

        config = new JButton(new ImageIcon("imagenes/gear.png"));
        config.setBackground(null);
        config.setBorder(null);
        config.addActionListener(e -> pantallas.show(principal, "settings"));

        JButton[] imagenes = {hechos, calendario, home, basura, config};
        //Adicion de los botones al footer
        for (JButton imagen : imagenes) {
           footer.add(imagen);
        }

        //Adición de los elementos al frame
        frame.add(principal, BorderLayout.CENTER);
        frame.add(header, BorderLayout.NORTH);
        frame.add(footer, BorderLayout.SOUTH);
        frame.setVisible(true);
    }

    @Override
    public void windowOpened(WindowEvent e) { }

    @Override
    public void windowClosing(WindowEvent e) {
        Organizador.escribirArchivos();
        ConfigPanel.guardarConfiguracion();
        frame.dispose();
    }

    @Override
    public void windowClosed(WindowEvent e) { }

    @Override
    public void windowIconified(WindowEvent e) { }

    @Override
    public void windowDeiconified(WindowEvent e) { }

    @Override
    public void windowActivated(WindowEvent e) { }

    @Override
    public void windowDeactivated(WindowEvent e) { }
}
