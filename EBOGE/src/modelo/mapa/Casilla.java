package modelo.mapa;

public class Casilla {
	TipoCasilla tipo;
	int indice;
	
	public  Casilla(TipoCasilla tipo, int indice)
	{
		this.indice=indice;
		this.tipo=tipo;
	}
	
    public TipoCasilla getTipo() {
        return tipo;
    }

    public void setTipo(TipoCasilla tipo) {
        this.tipo = tipo;
    }

    public int getIndice() {
        return indice;
    }

    public void setIndice(int indice) {
        this.indice = indice;
    }

}
