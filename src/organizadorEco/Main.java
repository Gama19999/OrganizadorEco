package organizadorEco;

public class Main {
    public static void main(String[] args) {
        Organizador.leerArchivos();
        ConfigPanel.aplicarConfiguracion();
        new GUI();
    }
}
