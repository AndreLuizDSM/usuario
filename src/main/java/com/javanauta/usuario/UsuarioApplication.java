package com.javanauta.usuario;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

//interface Matematica{
//	int calcular(int a,int b);
//}

@SpringBootApplication
@EnableFeignClients
public class UsuarioApplication {

	public static void main(String[] args) {
		SpringApplication.run(UsuarioApplication.class, args);


//		Matematica matematicaExemplo1 = new Matematica() {
//			@Override
//			public int calcular(int a, int b) {
//				return a + b;
//			}
//		};
//
//		Matematica matematicaExemplo2 = (a, b) -> a * b;
//		Matematica matematicaExemplo3 = new Matematica() {
//			@Override
//			public int calcular(int a, int b) {
//				return a * b;
//			}
//		};
//		System.out.println("Exemplo sem lambda " + matematicaExemplo1.calcular(5, 10));
//		System.out.println("Exemplo com lambda " + matematicaExemplo2.calcular(5, 10));

	}
}
