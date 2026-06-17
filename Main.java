public class Main {
     public static void main(String[] args) {
       
        String rutaCamiones = "Camiones.csv";
        String rutaPaquetes = "Paquetes.csv";

       Servicios servicios = new Servicios(rutaCamiones, rutaPaquetes);

        System.out.println("Paquetes con alimentos cargados: " + servicios.servicio2(true).size());
        System.out.println("Paquetes sin alimentos cargados: " + servicios.servicio2(false).size());


        Solucion solucion1 = new Solucion();

        solucion1.ejecutarBacktracking(servicios.getPaquetes(), servicios.getCamiones());

        solucion1.ejecutarGreedy(servicios.getPaquetes(), servicios.getCamiones());

        //Backtracking
        System.out.println("Técnica: Backtracking");
        System.out.println("Solución obtenida (Distribución óptima):");
        for (Camion c : solucion1.getMejorDistribucion()) {
            System.out.println("  -> Camión ID " + c.getId_camion() + " (Patente: " + c.getPatente() + ") lleva " + c.getPaquetes().size() + " paquetes.");
        }
        System.out.println("Peso que quedó sin asignar: " + solucion1.getMejorPesoNoAsignado() + " kg.");
        System.out.println("Métrica de costo (Cantidad de estados generados): " + solucion1.getEstadosGenerados());


        //Greedy
        System.out.println("Técnica: Greedy");
        System.out.println("Solución obtenida:");
        for (Camion c : solucion1.getDistribucionGreedy()) {
            System.out.println("  -> Camión ID " + c.getId_camion() + " (Patente: " + c.getPatente() + ") lleva " + c.getPaquetes().size() + " paquetes.");
        }
        System.out.println("Peso que quedó en el piso (sin asignar): " + solucion1.getPesoNoAsignadoGreedy() + " kg.");
        System.out.println("Métrica de costo (Cantidad de candidatos considerados): " + solucion1.getCandidatosConsiderados());
    }
}
