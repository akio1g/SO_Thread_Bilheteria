package controller;

import java.util.Random;
import java.util.concurrent.Semaphore;

public class threadBilheteria extends Thread {

	private int id;
	static int ingressos = 100;
	private int ingComprado;
	private Semaphore semaforo;
	private volatile boolean fim = false;

	public threadBilheteria(int id, Semaphore semaforo) {
		this.id = id;
		this.semaforo = semaforo;
	}

	@Override
	public void run() {
		while (!fim) {
			ingComprado = randomizar(4, 1);
			loginSistema();
			processoCompra();
			try {
				semaforo.acquire();
				validacaoCompra();
			} catch (InterruptedException e) {
				e.printStackTrace();
			} finally {
				semaforo.release();
			}
		}
	}

	public void loginSistema() {
		int tempo = randomizar(2000, 500);
		if (tempo > 1000) {
			System.out.println("ID#:" + id + " Tempo esgotado");
			fim = true;

		} else {
			System.out.println("ID#:" + id + " realizando login");
		}
		try {
			sleep(tempo);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void processoCompra() {
		int tempo = randomizar(3000, 1000);
		if (tempo > 2500) {
			System.out.println("ID#" + id + " Tempo de sessao  estourado");
			fim = true;
		} else {
			System.out.println("ID#" + id + " Realizando compra...");
		}
	}

	public void validacaoCompra() {
		if (ingressos >= ingComprado) {
			ingressos -= ingComprado;
			System.out.println("ID#" + id + " Ingressos vendidos para o usuario: " + ingComprado);
			System.out.println("ID#" + id + " Ingressos ainda disponiveis: " + ingressos);
		} else {
			System.out.println("ID#" + id + " Ingressos indisponiveis, fim da sessao");
			fim = true;
		}
	}

	public int randomizar(int tMax, int tMin) {
		Random r = new Random();
		int tempo = r.nextInt(tMax);
		while (tempo < tMin) {
			tempo = r.nextInt(tMax);
		}
		return tempo;
	}
}
