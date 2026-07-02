import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Servicios { 
    private List <Camion> camiones = new ArrayList<>();
    private List <Paquete> paquetes= new ArrayList<>();
    
    private Map<String, Paquete> paquetesPorCodigo = new HashMap<>();
    private List<Paquete> paquetesConAlimentos = new ArrayList<>();
    private List<Paquete> paquetesSinAlimentos = new ArrayList<>();
    
    /* Complejidad Temporal del Constructor: O(P + C)
        Donde P es la cantidad total de paquetes y C la cantidad de camiones.
        Leer los archivos e insertar los elementos en ArrayList y HashMap requiere tiempo lineal respecto al tamaño de la entrada.
    */
    public Servicios(String rutaCamiones, String rutaPaquetes) {
        cargarPaquetes(rutaPaquetes);
        cargarCamiones(rutaCamiones);
    }

    private void cargarPaquetes(String ruta){
        try (BufferedReader br = new BufferedReader(new FileReader(ruta))) {
            int total = Integer.parseInt(br.readLine().trim());
            
            for (int i = 0; i < total; i++) {
                String linea = br.readLine();
                String[] partes = linea.split(";");
                
                int id = Integer.parseInt(partes[0]);
                String codigo = partes[1];
                int peso = Integer.parseInt(partes[2]);
                boolean contieneAlimentos = partes[3].equals("1");
                int urgencia = Integer.parseInt(partes[4]);
                
                Paquete p = new Paquete(id, codigo, peso, contieneAlimentos, urgencia);
                paquetes.add(p);

                paquetesPorCodigo.put(codigo, p);
                
                if (contieneAlimentos) {
                    paquetesConAlimentos.add(p);
                } else {
                    paquetesSinAlimentos.add(p);
                }

            }
        } catch (IOException e) {
            System.out.println("Error al leer el archivo de paquetes: " + e.getMessage());
        }
    }
    
    private void cargarCamiones(String ruta){
        try (BufferedReader br = new BufferedReader(new FileReader(ruta))) {
                int total = Integer.parseInt(br.readLine().trim());
                
                for (int i = 0; i < total; i++) {
                    String linea = br.readLine();
                    String[] partes = linea.split(";");
                    
                    int id = Integer.parseInt(partes[0]);
                    String patente = partes[1];
                    boolean refrigerado = partes[2].equals("1"); 
                    int capacidad = Integer.parseInt(partes[3]);
                    
                    Camion c = new Camion(id, patente, refrigerado, capacidad);
                    camiones.add(c);
                }
            } catch (IOException e) {
                System.out.println("Error al leer el archivo de camiones: " + e.getMessage());
            }
        
        }

    /*Complejidad: O(1).
     Al utilizar un HashMap, el acceso al paquete por su clave (código) es directo.*/
    public Paquete servicio1(String codigoPaquete) {
       return paquetesPorCodigo.get(codigoPaquete);
     }

    /*Complejidad: O(1).
     * Retorna directamente la referencia a la lista precalculada según el booleano.*/
    public List<Paquete> servicio2(boolean contieneAlimentos) {
     if (contieneAlimentos) {
        return paquetesConAlimentos;
    } else {
        return paquetesSinAlimentos;
    }
    }

    /* Complejidad temporal: O(P)
        Donde P es la cantidad total de paquetes.
        Se recorre una única vez la lista de paquetes y para cada uno se verifica si su nivel de urgencia pertenece al rango solicitado.
    */
    public List<Paquete> servicio3(int urgenciaMinima, int urgenciaMaxima) { 
       List<Paquete> aux= new ArrayList<>();
       for (Paquete paquete : paquetes) {
            if (paquete.getNivel_urgencia() >= urgenciaMinima && paquete.getNivel_urgencia() <= urgenciaMaxima) {
                aux.add(paquete);
            }
       }
       return aux;
    }

    public List<Camion> getCamiones() {
        List<Camion> aux= new ArrayList<>();
        aux.addAll(camiones);
        return aux;
    }
    public List<Paquete> getPaquetes() {
    List<Paquete> aux= new ArrayList<>();
        aux.addAll(paquetes);
        return aux;    
    }

}
