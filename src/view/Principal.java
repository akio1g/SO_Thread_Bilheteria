package view;

import java.util.concurrent.Semaphore;

import controller.threadBilheteria;

public class Principal {

	public static void main(String[] args) {
		Semaphore semaforo = new Semaphore(1);
		for(int id = 1;id <= 300; id++) {
			Thread t = new threadBilheteria(id,semaforo);
			t.start();
		}
	}

}
