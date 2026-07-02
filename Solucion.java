import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Solucion {
    private int mejorPesoNoAsignado;
    private List<Camion> mejorDistribucion;
    private int estadosGenerados;
    private HashMap<Camion, Integer> camionesDisponibles; // Para almacenar resultados de subproblemas en Backtracking
    private int pesoNoAsignadoGreedy;
    private List<Camion> distribucionGreedy;
    private int candidatosConsiderados;
    
    public Solucion() {
        this.mejorPesoNoAsignado = Integer.MAX_VALUE;
        this.mejorDistribucion =  new ArrayList<>();
        this.estadosGenerados = 0;
        this.camionesDisponibles = new HashMap<>();
        this.pesoNoAsignadoGreedy = 0;
        this.distribucionGreedy = new ArrayList<>();
        this.candidatosConsiderados = 0;
    }

//BACKTRACKING
/*ESTRATEGIA:Para cada paquete, el algoritmo evalúa dos alternativas: 
            asignarlo secuencialmente en cada camión disponible que cumpla las restricciones 
            o dejarlo sin asignar, acumulando su peso.
            Para minimizar el costo computacional si el peso acumulado sin asignar supera el mejor peso no asignado la rama se descarta 
  COMPLEJIDAD:O(C^P)Para el primer paquete, el algoritmo tiene C opciones de camiones disponibles más la opción de no asignarlo. 
              Así sucesivamente para cada uno de los P paquetes. 
    */

    public Solucion ejecutarBacktracking(List<Paquete> paquetes, List<Camion> camiones) {
        List<Camion> camionesSimulacion = clonarCamiones(camiones);
        asignarCamiones(camionesSimulacion);
        this.estadosGenerados = 0;
        this.mejorPesoNoAsignado = Integer.MAX_VALUE;
        backtracking(0, paquetes, camionesSimulacion,0);

        return this;
    }

    private void backtracking(int indicePaquete, List<Paquete> paquetes, List<Camion> camiones, int pesoNoAsignadoAcumulado) {
        this.estadosGenerados++; 
       if (pesoNoAsignadoAcumulado >= mejorPesoNoAsignado) {
            return;
        }
        if (indicePaquete == paquetes.size()) {
            mejorPesoNoAsignado = pesoNoAsignadoAcumulado;
            mejorDistribucion = clonarCamiones(camiones);
            
            return;
        }

        Paquete paqueteActual = paquetes.get(indicePaquete);

        for (Camion camion : camiones) {
            if (camion.puedeAgregar(paqueteActual)) {
                camion.agregarPaquete(paqueteActual);
                backtracking(indicePaquete + 1, paquetes, camiones, pesoNoAsignadoAcumulado);

                camion.quitarUltimoPaquete();
              }
        }

        backtracking(indicePaquete + 1, paquetes, camiones, pesoNoAsignadoAcumulado + paqueteActual.getPeso_kg());
    }

    //GREEDY 
    /*ESTRATEGIA:se ordenan los paquetes de mayor a menor peso, asi se ubican primero los de mayor peso 
                para asegurar que tengan su lugar y optimizar el espacio en los camiones.
                Factibilidad: Cada paquete se asigna al primer camión que tenga espacio libre y cumpla las reglas.
    COMPLEJIDAD: O(P log P + P * C)
            O(P log P) por ordenar los paquetes con sort.
            O(P * C) porque por cada paquete se pueden recorrer los camiones.
    */

    public void ejecutarGreedy(List<Paquete> paquetes, List<Camion> camiones) {
        this.distribucionGreedy = clonarCamiones(camiones);
        asignarCamiones(this.distribucionGreedy);

        this.candidatosConsiderados = 0;
        this.pesoNoAsignadoGreedy = 0;

        List<Paquete> copiaPaquetes = new ArrayList<>(paquetes);

        copiaPaquetes.sort((p1, p2) -> p2.getPeso_kg() - p1.getPeso_kg());

        for (Paquete paquete : copiaPaquetes) {
            boolean asignado = false;

        for (Camion camion : distribucionGreedy) {
            if (!asignado) {
                this.candidatosConsiderados++;

                if (camion.puedeAgregar(paquete)) {
                    camion.agregarPaquete(paquete);
                    actualizarCamionesDisponibles(camion);
                    asignado = true;
                }
            }
        }

        if (!asignado) {
            this.pesoNoAsignadoGreedy += paquete.getPeso_kg();
        }
    }
    }

    //COMPARTIDOS
    private void actualizarCamionesDisponibles(Camion camion) {
        if (camion.getcapacidadUsadaCamion() == camion.getCapacidad_kg()) {
            camionesDisponibles.remove(camion);
        }
    }

    private List<Camion> clonarCamiones(List<Camion> originales) {
        List<Camion> copias = new ArrayList<>();

        for (Camion c : originales) {
            Camion copia = new Camion( c.getId_camion(), c.getPatente(), c.isEsta_refrigerado(), c.getCapacidad_kg());

            for (Paquete p : c.getPaquetes()) {
                copia.agregarPaquete(p);
            }

            copias.add(copia);
        }

        return copias;
    }

    private void asignarCamiones(List<Camion> camiones) {
        camionesDisponibles.clear();
        for (Camion camion : camiones) {
            if (camion.getcapacidadUsadaCamion() < camion.getCapacidad_kg()) {
            camionesDisponibles.put(camion, camion.getcapacidadUsadaCamion());
            }
        }
    }

    public int getMejorPesoNoAsignado() {
        return mejorPesoNoAsignado;
    }

    public List<Camion> getMejorDistribucion() {
        return mejorDistribucion;
    }

    public int getEstadosGenerados() {
        return estadosGenerados;
    }
    public int getPesoNoAsignadoGreedy() { 
        return pesoNoAsignadoGreedy; 
    }
    public List<Camion> getDistribucionGreedy() {
        return distribucionGreedy; 
    }
    public int getCandidatosConsiderados() { 
        return candidatosConsiderados; 
    }
}

