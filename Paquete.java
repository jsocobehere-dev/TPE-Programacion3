public class Paquete {
    private int id_paquete;
    private String codigo_paquete;
    private int peso_kg;
    private boolean contiene_alimentos;
    private int nivel_urgencia;
    
    public Paquete(int id_paquete, String codigo_paquete, int peso_kg, boolean contiene_alimentos, int nivel_urgencia) {
        this.id_paquete = id_paquete;
        this.codigo_paquete = codigo_paquete;
        this.peso_kg = peso_kg;
        this.contiene_alimentos = contiene_alimentos;
        this.nivel_urgencia = nivel_urgencia;
    }

    public int getId_paquete() {
        return id_paquete;
    }

    public String getCodigo_paquete() {
        return codigo_paquete;
    }

    public int getPeso_kg() {
        return peso_kg;
    }

    public boolean isContiene_alimentos() {
        return contiene_alimentos;
    }

    public void setContiene_alimentos(boolean contiene_alimentos) {
        this.contiene_alimentos = contiene_alimentos;
    }

    public int getNivel_urgencia() {
        return nivel_urgencia;
    }

    public void setNivel_urgencia(int nivel_urgencia) {
        this.nivel_urgencia = nivel_urgencia;
    }

}