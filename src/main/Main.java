package main;

import avalanche.AvalancheToInput;
import avalanche.AvalancheToKey;

public class Main {

	public static void main(String[] args) {
//		AvalancheToInput a1000 = new AvalancheToInput(1000);
//		System.out.println("Histograma a1000:");
//		a1000.printHistogram();
//		AvalancheToInput a100000 = new AvalancheToInput(100000);
//		System.out.println("Histograma a100000:");
//		a100000.printHistogram();
//		AvalancheToInput a1000000 = new AvalancheToInput(1000000);
//		System.out.println("Histograma a1000000:");
//		a1000000.printHistogram();
//		AvalancheToInput aExtreme = new AvalancheToInput(1000000000);
//		System.out.println("Histograma extreme:");
//		aExtreme.printHistogram();

		int samples = 2000;
		AvalancheToInput avalancheToInput = new AvalancheToInput(samples);
		System.out.println("Histograma avalanche to input:");
		avalancheToInput.printHistogram();

		AvalancheToKey avalancheToKey = new AvalancheToKey(samples);
		System.out.println("Histogram avalanche to key:");
		avalancheToKey.printHistogram();
	}

	public static void printBits(byte[] bytes){
		for (byte b : bytes){
			System.out.println(String.format("%8s", Integer.toBinaryString(b & 0xFF)).replace(' ', '0'));
		}
	}

}
