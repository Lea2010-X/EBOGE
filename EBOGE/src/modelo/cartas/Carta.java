package modelo.cartas;

public abstract class Carta {
	private String nombre;
	private TipoCarta tipo;
	private String imagenRuta;
	
	public Carta(String nombre, TipoCarta tipo, String imagenRuta) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.imagenRuta = imagenRuta;
    }

    public String getNombre() { return nombre; }
    public TipoCarta getTipo() { return tipo; }
    public String getImagenRuta() { return imagenRuta; }
	
}
