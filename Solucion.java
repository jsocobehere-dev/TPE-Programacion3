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
            if (puedeAsignar(paqueteActual, camion)) {
                camion.getPaquetes().add(paqueteActual);
                camion.setcapacidadUsadaCamion(camion.getcapacidadUsadaCamion() + paqueteActual.getPeso_kg());
                backtracking(indicePaquete + 1, paquetes, camiones, pesoNoAsignadoAcumulado);

                camion.getPaquetes().remove(camion.getPaquetes().size() - 1);
                camion.setcapacidadUsadaCamion(camion.getcapacidadUsadaCamion() - paqueteActual.getPeso_kg());
              }
        }

        backtracking(indicePaquete + 1, paquetes, camiones, pesoNoAsignadoAcumulado + paqueteActual.getPeso_kg());
    }

    //GREEDY 
    /*ESTRATEGIA:se ordenan los paquetes de mayor a menor peso, asi se ubican primero los de mayor peso 
                para asegurar que tengan su lugar y optimizar el espacio en los camiones.
                Factibilidad: Cada paquete se asigna al primer camión que tenga espacio libre y cumpla las reglas.
    COMPLEJIDAD:O(P^2 + P * C). P^2 ya que ordenamos por Selección y P*C un bucle que recorre todos los paquetes (P) y,
                por cada uno, un bucle interno que recorre los camiones (C) buscando el primero donde encaje.
    */

    public void ejecutarGreedy(List<Paquete> paquetes, List<Camion> camiones) {
        this.distribucionGreedy = clonarCamiones(camiones);
        this.candidatosConsiderados = 0;
        this.pesoNoAsignadoGreedy = 0;

        List<Paquete> copiaPaquetes = new ArrayList<>(paquetes);

        ordenarPaquetes(copiaPaquetes);

        for (Paquete paquete : copiaPaquetes) {
            boolean asignado = false;

            for (int i = 0; i < distribucionGreedy.size() && !asignado; i++) {
            Camion camion = distribucionGreedy.get(i);

            this.candidatosConsiderados++;

                if (puedeAsignar(paquete, camion)) {
                    camion.getPaquetes().add(paquete);
                    camion.setcapacidadUsadaCamion(camion.getcapacidadUsadaCamion() + paquete.getPeso_kg());
                    actualizarCamionesDisponibles(camion);
                    asignado = true;
                }
            }
            if (!asignado) {
                this.pesoNoAsignadoGreedy += paquete.getPeso_kg();
                }

        }
    }

    private void ordenarPaquetes(List<Paquete>copiaPaquetes) {
        for (int i = 0; i < copiaPaquetes.size() - 1; i++) {
            int indiceMaximo = i;
            for (int j = i + 1; j < copiaPaquetes.size(); j++) {
                if (copiaPaquetes.get(j).getPeso_kg() > copiaPaquetes.get(indiceMaximo).getPeso_kg()) {
                    indiceMaximo = j;
                }
            }
            Paquete temp = copiaPaquetes.get(indiceMaximo);
            copiaPaquetes.set(indiceMaximo, copiaPaquetes.get(i));
            copiaPaquetes.set(i, temp);
        }
    }

    //COMPARTIDOS
    private boolean puedeAsignar(Paquete p, Camion c) {
        if ((c.getcapacidadUsadaCamion() + p.getPeso_kg())> c.getCapacidad_kg()) {
            return false;
        }
        if (p.isContiene_alimentos() && !c.isEsta_refrigerado()) {
            return false;
        }
        return true;
    }

    private void actualizarCamionesDisponibles(Camion camion) {
        if (camion.getcapacidadUsadaCamion() == camion.getCapacidad_kg()) {
            camionesDisponibles.remove(camion);
        }
    }

    private List<Camion> clonarCamiones(List<Camion> originales) {
        List<Camion> copias = new ArrayList<>();
        for (Camion c : originales) {
            Camion copia = new Camion(c.getId_camion(), c.getPatente(), c.isEsta_refrigerado(), c.getCapacidad_kg(), c.getcapacidadUsadaCamion());
            copia.getPaquetes().addAll(c.getPaquetes());
            copias.add(copia);
        }
        return copias;
    }

    private void asignarCamiones(List<Camion> camiones) {
        camionesDisponibles.clear();
        for (Camion camion : camiones) {
            camionesDisponibles.put(camion, camion.getcapacidadUsadaCamion());
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

