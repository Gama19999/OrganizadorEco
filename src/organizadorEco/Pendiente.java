package organizadorEco;

import java.time.LocalDate;

public class Pendiente {
    private String descripcion;
    private LocalDate fechaLimite;

    Pendiente(String descripcion) { this.descripcion = descripcion; }

    Pendiente(String descripcion, int a�o, int mes, int dia) {
        this.descripcion = descripcion;
        this.fechaLimite = LocalDate.of(a�o, mes, dia);
    }

    public String getDescripcion() {
        return descripcion;
    }

    public int getA�o() { return fechaLimite.getYear(); }

    public int getMes() { return fechaLimite.getMonthValue(); }

    public int getDia() { return fechaLimite.getDayOfMonth(); }

    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public void setFechaLimite(int a�o, int mes, int dia) { fechaLimite = LocalDate.of(a�o, mes, dia); }
}
