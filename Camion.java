import java.util.ArrayList;
public class Camion{
    private int id_camion;
    private String patente;
    private boolean esta_refrigerado;
    private int capacidad_kg;
    private ArrayList<Paquete> paquetes;
    
    public Camion(int id_camion, String patente, boolean esta_refrigerado, int capacidad_kg) {
        this.id_camion = id_camion;
        this.patente = patente;
        this.esta_refrigerado = esta_refrigerado;
        this.capacidad_kg = capacidad_kg;
        this.paquetes = new ArrayList<>();
    }

    public int getId_camion() {
        return id_camion;
    }

    public void setId_camion(int id_camion) {
        this.id_camion = id_camion;
    }

    public String getPatente() {
        return patente;
    }

    public void setPatente(String patente) {
        this.patente = patente;
    }

    public boolean isEsta_refrigerado() {
        return esta_refrigerado;
    }

    public void setEsta_refrigerado(boolean esta_refrigerado) {
        this.esta_refrigerado = esta_refrigerado;
    }

    public int getCapacidad_kg() {
        return capacidad_kg;
    }

    public void setCapacidad_kg(int capacidad_kg) {
        this.capacidad_kg = capacidad_kg;
    }

    public ArrayList<Paquete> getPaquetes() {
        return paquetes;
    }   
}