import java.util.ArrayList;
public class Camion{
    private int id_camion;
    private String patente;
    private boolean esta_refrigerado;
    private int capacidad_kg;
    private int capacidadUsadaCamion;
    private ArrayList<Paquete> paquetes;
    
    public Camion(int id_camion, String patente, boolean esta_refrigerado, int capacidad_kg) {
        this.id_camion = id_camion;
        this.patente = patente;
        this.esta_refrigerado = esta_refrigerado;
        this.capacidad_kg = capacidad_kg;
        this.capacidadUsadaCamion = 0; 
        this.paquetes = new ArrayList<>();
    }

    public boolean puedeAgregar(Paquete p) {
        if (this.capacidadUsadaCamion + p.getPeso_kg() > this.capacidad_kg) {
            return false;
        }
        if (p.isContiene_alimentos() && !this.esta_refrigerado) {
            return false;
        }

        return true;
    }

    public void agregarPaquete(Paquete p) {
        if (puedeAgregar(p)) {
            this.paquetes.add(p);
            this.capacidadUsadaCamion += p.getPeso_kg();
        }
    }

    public void quitarUltimoPaquete() {
        if (!this.paquetes.isEmpty()) {
            Paquete p = this.paquetes.remove(this.paquetes.size() - 1);
            this.capacidadUsadaCamion -= p.getPeso_kg();
        }
    }

    public int getId_camion() {
        return id_camion;
    }

    public String getPatente() {
        return patente;
    }

    public boolean isEsta_refrigerado() {
        return esta_refrigerado;
    }

    public int getCapacidad_kg() {
        return capacidad_kg;
    }
    public int getcapacidadUsadaCamion() {
        return capacidadUsadaCamion;
    }

    public ArrayList<Paquete> getPaquetes() {
        return new ArrayList<>(paquetes);
    }   
}