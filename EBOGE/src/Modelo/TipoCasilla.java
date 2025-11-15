package Modelo;

public enum TipoCasilla {

    INICIO('I', "imagenes/CasillaInicio.png"),
    FINAL('F', "imagenes/CasillaFinal.png"),
    PROPIA('P', "imagenes/CasillaPropia.png"),
    BLANCO('B', "imagenes/CasillaBlanco.png"),
    MAESTRA('M', "imagenes/CasillaMaestra.png"),
    TRAMPA('T', "imagenes/CasillaTrampa.png"),
    GLOBAL('G', "imagenes/CasillaGlobal.png"),
    NORMAL('N', "imagenes/CasillaNormal.png");

    private final char letra;
    private final String rutaImagen;

     private TipoCasilla(char letra, String rutaImagen) {
        this.letra = letra;
        this.rutaImagen = rutaImagen;
    }

    public char getSimbolo() {
        return letra;
    }

    public String getRutaImagen() {
        return rutaImagen;
    }

}

