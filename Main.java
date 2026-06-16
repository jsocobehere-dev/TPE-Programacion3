public class Main {
     public static void main(String[] args) {
       
        String pathCamiones = "Camiones.csv";
        String pathPaquetes = "Paquetes.csv";

       Servicios servicios = new Servicios(pathCamiones, pathPaquetes);

        System.out.println("Paquetes con alimentos cargados: " + servicios.servicio2(true).size());
        System.out.println("Paquetes sin alimentos cargados: " + servicios.servicio2(false).size());


        Solucion solucionador = new Solucion();

        solucionador.ejecutarBacktracking(servicios.getPaquetes(), servicios.getCamiones());

        solucionador.ejecutarGreedy(servicios.getPaquetes(), servicios.getCamiones());

        //Backtracking
        System.out.println("Técnica: Backtracking");
        System.out.println("Solución obtenida (Distribución óptima):");
        for (Camion c : solucionador.getMejorDistribucion()) {
            System.out.println("  -> Camión ID " + c.getId_camion() + " (Patente: " + c.getPatente() + ") lleva " + c.getPaquetes().size() + " paquetes.");
        }
        System.out.println("Peso que quedó sin asignar: " + solucionador.getMejorPesoNoAsignado() + " kg.");
        System.out.println("Métrica de costo (Cantidad de estados generados): " + solucionador.getEstadosGenerados());


        //Greedy
        System.out.println("Técnica: Greedy");
        System.out.println("Solución obtenida:");
        for (Camion c : solucionador.getDistribucionGreedy()) {
            System.out.println("  -> Camión ID " + c.getId_camion() + " (Patente: " + c.getPatente() + ") lleva " + c.getPaquetes().size() + " paquetes.");
        }
        System.out.println("Peso que quedó en el piso (sin asignar): " + solucionador.getPesoNoAsignadoGreedy() + " kg.");
        System.out.println("Métrica de costo (Cantidad de candidatos considerados): " + solucionador.getCandidatosConsiderados());
    }
}
