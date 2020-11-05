package stats;

public class Histogram {
	
	// Lista para los valores del efecto avalancha
	private int[] histogram;
	private int muestras;
	private double media;
	private double desviacion;


	public Histogram(int n, int muestras){
		histogram = new int[n];
		this.muestras = muestras;
	}

	public int[] getHistogram() {
		return histogram;
	}
	

	// A�ade valor de efecto avalancha
	public void addData(int data){
		histogram[data]++;
	}
	

	// Muestra por pantalla los valores del efecto avalancha
	public void print() {
		for (int i = 0; i < histogram.length; i++){
			System.out.println(histogram[i]);
		}
		media();
		System.out.println("Media: " + getMedia());
		desviacion();
		System.out.println("Desviacion: " + getDesviacion());
	}

	public void media(){
		double res = 0;
		for (int i = 0; i < histogram.length; i++){
			double suma = i * histogram[i];
			res = res + suma;
		}
		media = res / muestras;
	}

	public void desviacion(){
		double res;
		double suma = 0;
		for (int i = 0; i < histogram.length; i++){
			suma = suma + (Math.pow(i - media, 2) * histogram[i]);
		}
		res = Math.sqrt(suma / (muestras - 1));
		desviacion = res;
	}

	public double getMedia() {
		return media;
	}

	public double getDesviacion() {
		return desviacion;
	}
}
