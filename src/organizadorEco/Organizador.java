package organizadorEco;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

public abstract class Organizador {
    static ArrayList<Pendiente> pendientes = new ArrayList<>();
    static ArrayList<Pendiente> realizados = new ArrayList<>();
    static ArrayList<Pendiente> eliminados = new ArrayList<>();
    private static final File pendientesArchivo = new File("pendientes.txt");
    private static final File realizadosArchivo = new File("realizados.txt");
    private static final File eliminadosArchivo = new File("eliminados.txt");
    private static final File[] listas = {pendientesArchivo, realizadosArchivo, eliminadosArchivo};
    private static final ArrayList<Pendiente>[] arrayLists = new ArrayList[]{pendientes, realizados, eliminados};

    public static void leerArchivos() {
        for (int i = 0; i < arrayLists.length; i++) {
            try {
                listas[i].createNewFile();
                FileReader leerArchivo = new FileReader(listas[i]);
                int data = leerArchivo.read();
                while (data != -1) {
                    StringBuilder desc = new StringBuilder();
                    while (data != '\t' && data != -1) {
                        desc.append((char) data);
                        data = leerArchivo.read();
                    }
                    data = leerArchivo.read();
                    StringBuilder fechaStr = new StringBuilder();
                    while (data != '\n' && data != '\r' && data != -1) {
                        fechaStr.append((char) data);
                        data = leerArchivo.read();
                    }
                    LocalDate fecha = LocalDate.parse(fechaStr.toString());
                    Pendiente pend = new Pendiente(desc.toString(), fecha);
                    arrayLists[i].add(pend);
                    data = leerArchivo.read();
                }
                leerArchivo.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void escribirArchivos() {
        for (int i = 0; i < arrayLists.length; i++) {
            try {
                FileWriter escribirArchivo = new FileWriter(listas[i]);
                for (Pendiente pend : arrayLists[i]) {
                    escribirArchivo.append(pend.getDescripcion() + '\t' + pend.getFechaStr() + '\n');
                }
                escribirArchivo.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void agregarPendiente(String descripcion) {
        Pendiente pendiente = new Pendiente(descripcion);
        pendientes.add(pendiente);
    }

    public static void agregarPendiente(String desc, int year, int month, int day) {
        Pendiente pend = new Pendiente(desc, year, month, day);
        pendientes.add(pend);
    }

    public static void modificarPendiente(String viejaDesc, String nuevaDesc) {
        for (Pendiente pendiente : pendientes) {
            if (pendiente.getDescripcion().equals(viejaDesc)) {
                pendiente.setDescripcion(nuevaDesc);
            }
        }
    }

    public static void modificarPendiente(String viejaDesc, String nuevaDesc, int a, int m, int d) {
        for (Pendiente pendiente : pendientes) {
            if (pendiente.getDescripcion().equals(viejaDesc)) {
                pendiente.setDescripcion(nuevaDesc);
                pendiente.setFechaLimite(a, m, d);
            }
        }
    }

    public static void marcarCompletado(String descripcion) {
        for (int i = 0; i < pendientes.size(); i++) {
            Pendiente pend = pendientes.get(i);
            if (pend.getDescripcion().equals(descripcion)) {
                pendientes.remove(pend);
                realizados.add(pend);
                break;
            }
        }
    }

    public static void marcarNoCompletado(String descripcion) {
        for (int i = 0; i < realizados.size(); i++) {
            Pendiente pend = realizados.get(i);
            if (pend.getDescripcion().equals(descripcion)) {
                realizados.remove(pend);
                pendientes.add(pend);
                break;
            }
        }
    }

    public static void eliminarPendiente(String descripcion) {
        for (int i = 0; i < pendientes.size(); i++) {
            Pendiente pend = pendientes.get(i);
            if (pend.getDescripcion().equals(descripcion)) {
                pendientes.remove(pend);
                eliminados.add(pend);
                break;
            }
        }
    }

    public static void eliminarPermanente(String descripcion) {
        for (int i = 0; i < eliminados.size(); i++) {
            Pendiente pend = eliminados.get(i);
            if (pend.getDescripcion().equals(descripcion)) {
                eliminados.remove(pend);
                break;
            }
        }
    }

    public static void recuperarPendiente(String descripcion) {
        for (int i = 0; i < eliminados.size(); i++) {
            Pendiente pend = eliminados.get(i);
            if (pend.getDescripcion().equals(descripcion)) {
                eliminados.remove(pend);
                pendientes.add(pend);
                break;
            }
        }
    }

    public static void reestablecer() {
        for (ArrayList arrayList : arrayLists) {
            arrayList.clear();
        }
        for (File archivo : listas) {
            archivo.delete();
        }
    }
}
