package organizadorEco;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class GUI implements WindowListener, MouseListener {
    JFrame frame;
    JPanel header;
    JLabel titulo;
    JPanel footer;
    JButton hechos;
    JButton calendario;
    JButton home;
    JButton basura;
    JButton config;
    JButton[] botones;
    JPanel principal;

    String[] strings;
    static String fuente = "Montserrat";
    static String tituloStr = "Just do that.";
    static int tema = 0;

    static Color colorPrincipal = new Color(0x27AE60);
    static Color colorSecundario = new Color(0x7FEE71);
    static Color colorTerciario = new Color(0xAFF478);
    static Color colorCuaternario = new Color(0xFCF678);

    public static TaskPanel task;
    public static DonePanel done;
    public static DeletedPanel deleted;
    public static Calendario calendar;
    ConfigPanel settings;
    CardLayout pantallas;

    static File conf = new File("config.txt");

    public static ImageIcon[] imagenes;
    public static ImageIcon checkboxImg = new ImageIcon("imagenes/checkbox.png");
    public static ImageIcon calendarImg = new ImageIcon("imagenes/calendar.png");
    public static ImageIcon homeImg = new ImageIcon("imagenes/home.png");
    public static ImageIcon trashImg = new ImageIcon("imagenes/trash.png");
    public static ImageIcon gearImg = new ImageIcon("imagenes/gear.png");
    ImageIcon checkboxWhite;
    ImageIcon calendarWhite;
    ImageIcon homeWhite;
    ImageIcon trashWhite;
    ImageIcon gearWhite;

    public GUI() {
        imagenes = new ImageIcon[]{checkboxImg, calendarImg, homeImg, trashImg, gearImg};
        // pintarBlanco();
        aplicarConfiguracion();

    	//Rectangulo de la aplicación
        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(350, 600);
        frame.setTitle("Organizador");
        frame.setLayout(new BorderLayout(0, 0));
        frame.setLocationRelativeTo(null);
        frame.addWindowListener(this);
        frame.setIconImage(checkboxImg.getImage());
        frame.setResizable(false);

        //Panel Principal
        pantallas = new CardLayout();
        principal = new JPanel();
        principal.setBackground(colorPrincipal);
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
        header.addMouseListener(this);

        //Texto del titulo
        titulo = new JLabel(tituloStr);
        titulo.setFont(new Font(fuente, Font.BOLD, 20));
        titulo.setVerticalAlignment(JLabel.CENTER);
        titulo.setHorizontalAlignment(JLabel.CENTER);
        titulo.setVerticalTextPosition(JLabel.CENTER);
        titulo.setVerticalAlignment(JLabel.CENTER);
        titulo.setForeground(Color.white);
        header.add(titulo, BorderLayout.CENTER);

        //Recuadro que contiene los botones en la parte de abajo
        footer = new JPanel();
        footer.setBackground(colorSecundario);
        footer.setPreferredSize(new Dimension(350, 60));
        footer.setLayout(new FlowLayout(FlowLayout.CENTER, 30, 15));

        //Creación de los "Botones"
        hechos = new JButton(checkboxImg);
        hechos.setBackground(null);
        hechos.setBorder(null);
        hechos.addActionListener(e -> {
            pantallas.show(principal, "done");
            for (int i = 0; i < botones.length; i++) {
                botones[i].setIcon(imagenes[i]);
            }
            hechos.setIcon(checkboxWhite);
        });

        calendario = new JButton(calendarImg);
        calendario.setBackground(null);
        calendario.setBorder(null);
        calendario.addActionListener(e -> {
            pantallas.show(principal, "calendar");
            for (int i = 0; i < botones.length; i++) {
                botones[i].setIcon(imagenes[i]);
            }
            calendario.setIcon(calendarWhite);
        });
        
        home = new JButton(homeWhite);
        home.setBackground(null);
        home.setBorder(null);
        home.addActionListener(e -> {
            pantallas.show(principal, "task");
            for (int i = 0; i < botones.length; i++) {
                botones[i].setIcon(imagenes[i]);
            }
            home.setIcon(homeWhite);
        });

        basura = new JButton(trashImg);
        basura.setBackground(null);
        basura.setBorder(null);
        basura.addActionListener(e -> {
            pantallas.show(principal, "deleted");
            for (int i = 0; i < botones.length; i++) {
                botones[i].setIcon(imagenes[i]);
            }
            basura.setIcon(trashWhite);
        });

        config = new JButton(gearImg);
        config.setBackground(null);
        config.setBorder(null);
        config.addActionListener(e -> {
            pantallas.show(principal, "settings");
            for (int i = 0; i < botones.length; i++) {
                botones[i].setIcon(imagenes[i]);
            }
            config.setIcon(gearWhite);
        });

        botones = new JButton[]{hechos, calendario, home, basura, config};
        //Adicion de los botones al footer
        for (JButton imagen : botones) {
           footer.add(imagen);
        }

        //Adición de los elementos al frame
        frame.add(principal, BorderLayout.CENTER);
        frame.add(header, BorderLayout.NORTH);
        frame.add(footer, BorderLayout.SOUTH);
        frame.setVisible(true);
    }

    public void aplicarConfiguracion() {
        strings = new String[3];
        try {
            if (conf.createNewFile()) {
                fuente = "Montserrat";
                tituloStr = "Just do that.";
                tema = 0;
                return;
            }
            FileReader lector = new FileReader(conf);
            int data = lector.read();
            for (int i = 0; i < strings.length; i++) {
                String temp = "";
                while (data != '\n' && data != '\r' && data != -1) {
                    temp += (char) data;
                    data = lector.read();
                }
                strings[i] = temp;
                data = lector.read();
            }
            lector.close();
            fuente = strings[0];
            tituloStr = strings[1];
            tema = Integer.parseInt(strings[2]);

            switch (tema) {
                default -> {
                    colorPrincipal = new Color(0x27AE60);
                    colorSecundario = new Color(0x7FEE71);
                    colorTerciario = new Color(0xAFF478);
                    colorCuaternario = new Color(0xFFF26B);
                    pintarBlanco();
                }
                case 1 -> {
                    colorPrincipal = new Color(0x3A4292);
                    colorSecundario = new Color(0x6068C2);
                    colorTerciario = new Color(0x7489DC);
                    colorCuaternario = new Color(0xEF70FF);
                    pintarBlanco();
                    pintarIconos();
                }
                case 2 -> {
                    colorPrincipal = new Color(0x101010);
                    colorSecundario = new Color(0x686868);
                    colorTerciario = new Color(0x909090);
                    colorCuaternario = new Color(0xDCDCDC);
                    pintarBlanco();
                    pintarIconos();
                }
            }
        }
        catch (IOException e) {
            e.printStackTrace();
            fuente = "Montserrat";
            tituloStr = "Just do that.";
            tema = 0;
        }
    }

    private void pintarBlanco() {
        ImageIcon[] imagenesBlanco = {checkboxImg, calendarImg, homeImg, trashImg, gearImg};
        for (int i = 0; i < imagenesBlanco.length; i++) {
            BufferedImage bufferedImage = new BufferedImage(
                    imagenesBlanco[i].getIconWidth(),
                    imagenesBlanco[i].getIconHeight(),
                    BufferedImage.TYPE_INT_RGB
            );
            Graphics g = bufferedImage.createGraphics();
            imagenesBlanco[i].paintIcon(null, g, 0, 0);
            g.dispose();
            for (int x = 0; x < bufferedImage.getWidth(); x++) {
                for (int y = 0; y < bufferedImage.getHeight(); y++) {
                    Color color = new Color(bufferedImage.getRGB(x, y));
                    int green = color.getGreen();
                    if (green != 0) {
                        bufferedImage.setRGB(x, y, Color.white.getRGB());
                    }
                    else {
                        bufferedImage.setRGB(x, y, colorSecundario.getRGB());
                    }
                }
            }
            imagenesBlanco[i] = new ImageIcon(bufferedImage);
        }
        checkboxWhite = imagenesBlanco[0];
        calendarWhite = imagenesBlanco[1];
        homeWhite = imagenesBlanco[2];
        trashWhite = imagenesBlanco[3];
        gearWhite = imagenesBlanco[4];
    }

    private void pintarIconos() {
        for (int i = 0; i < imagenes.length; i++) {
            BufferedImage iconoTema = new BufferedImage(
                    imagenes[i].getIconWidth(),
                    imagenes[i].getIconHeight(),
                    BufferedImage.TYPE_INT_RGB
            );
            Graphics g = iconoTema.createGraphics();
            imagenes[i].paintIcon(null, g, 0, 0);
            g.dispose();
            for (int x = 0; x < iconoTema.getWidth(); x++) {
                for (int y = 0; y < iconoTema.getHeight(); y++) {
                    Color color = new Color(iconoTema.getRGB(x, y));
                    int green = color.getGreen();
                    if (green != 0) {
                        iconoTema.setRGB(x, y, colorPrincipal.getRGB());
                    }
                    else {
                        iconoTema.setRGB(x, y, colorSecundario.getRGB());
                    }
                }
            }
            imagenes[i] = new ImageIcon(iconoTema);
        }
        checkboxImg = imagenes[0];
        calendarImg = imagenes[1];
        homeImg = imagenes[2];
        trashImg = imagenes[3];
        gearImg = imagenes[4];
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

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() == header) {
            pantallas.show(principal, "task");
        }
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
