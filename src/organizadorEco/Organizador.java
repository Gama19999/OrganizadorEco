package organizadorEco;

import java.util.ArrayList;

public abstract class Organizador {
    static ArrayList<Pendiente> pendientes = new ArrayList<>();
    static ArrayList<Pendiente> realizados = new ArrayList<>();
    static ArrayList<Pendiente> eliminados = new ArrayList<>();

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

    public static void modificarPendiente(String viejaDesc, String nuevaDesc, int year, int month, int day) {
        for (Pendiente pendiente : pendientes) {
            if (pendiente.getDescripcion().equals(viejaDesc)) {
                pendiente.setDescripcion(nuevaDesc);
                pendiente.setFechaLimite(year, month, day);
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
}
