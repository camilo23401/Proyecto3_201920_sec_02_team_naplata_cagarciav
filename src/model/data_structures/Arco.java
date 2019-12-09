package model.data_structures;



public class Arco<R> implements  Comparable <Arco<R>> {

	
		double costo;
		double costo2;
		double costo3;
		R origen;
		R destino;
		public Arco(double pCosto, double pCosto2, double pCosto3,R pDestino,R pOrigen) {
			costo=pCosto;
			costo2 = pCosto2;
			costo3 = pCosto3;
			destino=pDestino;
           origen=pOrigen;
		}

		public double getCosto() {
			return costo;
		}
		public double getCosto2()
		{
			return costo2;
		}
		public double getCosto3()
		{
			return costo3;
		}
		public void setCosto(double costo) {
			this.costo = costo;
		}

		public R getDestino() {
			return destino;
		}
	    public R getOrigen(){
	    	return origen;
	    }

		@Override
		public int compareTo(Arco<R> o) {
			// TODO Auto-generated method stub
			return 0;
		}
		public R other(R vertex) {
	        if      (vertex == origen) return destino;
	        else if (vertex == destino) return origen;
	        else throw new IllegalArgumentException("Illegal endpoint");
	    }

	}


