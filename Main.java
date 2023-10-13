/*
    __  ___           __    __ 
   /  |/  /  ____ _  / /_  / /_
  / /|_/ /  / __ `/ / __/ / __/
 / /  / /  / /_/ / / /_  / /_  
/_/  /_/   \__,_/  \__/  \__/  
*/

/*#README
Arboles - Trabajo

*Los pasos que YO sigo para ejecutar el proyecto*
1. El proyecto está ubicado en una carpeta llamada prueba, el archivo se llama Main
2. Yo uso Visual Studio Code para programar en Java, descargar todas las extensiones de Java
3. Teniendo todo en orden, simplemente darle a ejecutar, o Run Java y el programa se ejecuta
4. Para cualquier otro entorno solo sería copiar y pegar el codigo omitiendo la primera linea (o especificando su package)
y debería funcionar

Pd: Implementé la función para que el arbol se balancee
intenté imprimir el arbol pero no pude
*/



package prueba; // Esta es la carpeta donde se encuentra el archivo

import java.util.Scanner; 

public class Main {
    
    static class NodoArbol {
        int valor;
        int altura;
        NodoArbol izquierdo;
        NodoArbol derecho;

        NodoArbol(int valor) {
            this.valor = valor;
            this.izquierdo = null;
            this.derecho = null;
            this.altura = 1; // Se inicializa la altura en 1
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in); // Se crea una instancia de Scanner pa la entrada del usuario
        NodoArbol arbol = null; // Inicializa el árbol como nulo

        // Menu
        while (true) {
            System.out.println("-----Arboles-----");
            System.out.println("----- Menú -----");
            System.out.println("1. Insertar nodos");
            System.out.println("2. Recorrido PostOrder");
            System.out.println("3. Recorrido PreOrder");
            System.out.println("4. Recorrido InOrder");
            System.out.println("5. Número de hojas");
            System.out.println("6. Altura");
            System.out.println("7. Si es completo");
            System.out.println("8. Número de nodos");
            System.out.println("X. Salir del menú");

            System.out.print("Seleccione una opción: ");
            String opcion = scanner.next(); // Lee la opción seleccionada

            // el equals compara si opcion = 1 y devuelve true
            if (opcion.equals("1")) {
                arbol = insertarNodos(scanner, arbol);
            } else if (opcion.equals("2")) {
                recorrerPostOrder(arbol);
                System.out.println();
            } else if (opcion.equals("3")) {
                recorrerPreOrder(arbol);
                System.out.println();
            } else if (opcion.equals("4")) {
                recorrerInOrder(arbol);
                System.out.println();
            } else if (opcion.equals("5")) {
                System.out.println("Cantidad de hojas: " + contarHojas(arbol));
            } else if (opcion.equals("6")) {
                System.out.println("Altura: " + contarAltura(arbol));
            } else if (opcion.equals("7")) {
                System.out.println("El árbol es completo?: " + esCompleto(arbol));
            } else if (opcion.equals("8")) {
                System.out.println("Cantidad de nodos: " + contarNodos(arbol));
            } else if (opcion.equals("X")) {
                return; // Si el usuario selecciona X, el programa sale del bucle y termina
            } else {
                System.out.println("Opción no válida."); // Si el usuario selecciona una opción no válida, muestra un mensaje
            }
        }
    }

    // Método para insertar nodos en el árbol
    static NodoArbol insertarNodos(Scanner scanner, NodoArbol arbol) {
        // pide cantidad de nodos a insertar
        System.out.print("Inserte la cantidad de nodos (-1 para nodos indefinidos, 0 para árbol vacío): ");
        int numeroNodos = scanner.nextInt();

        // Condiciones
        if (numeroNodos == -1) {
            arbol = insertarNodosIndefinidos(scanner, arbol);
        } else if (numeroNodos == 0) {
            arbol = null; // Árbol vacío
        } else if (numeroNodos > 0) {
            arbol = insertarNodosDefinidos(scanner, arbol, numeroNodos);
        } else {
            System.out.println("Opción no válida.");
        }
        return arbol;
    }

    // Método para insertar nodos indefinidos en el árbol
    static NodoArbol insertarNodosIndefinidos(Scanner scanner, NodoArbol arbol) {
        while (true) {
            System.out.print("Inserte el valor del nodo (X para salir): ");
            String valorIngresado = scanner.next();
            if (valorIngresado.equals("X")) {
                break;
            } else if (valorIngresado.equals("-1")) {
                continue;
            } else {
                int valor = Integer.parseInt(valorIngresado); // Convierte el string en un entero
                arbol = insertar(arbol, valor);
            }
        }
        return arbol;
    }

    // Método para insertar nodos definidos en el árbol
    static NodoArbol insertarNodosDefinidos(Scanner scanner, NodoArbol arbol, int numeroNodos) {
        for (int i = 0; i < numeroNodos; i++) {
            System.out.print("Ingrese el valor del nodo " + (i + 1) + ": ");
            arbol = insertar(arbol, scanner.nextInt());
        }
        return arbol;
    }

    // Método para insertar un nuevo nodo en el árbol
    static NodoArbol insertar(NodoArbol raiz, int valor) {
        // Si la raíz es nula, crea un nuevo nodo y lo devuelve
        if (raiz == null) {
            return new NodoArbol(valor);
        }

        // Inserta el nuevo nodo en el lugar adecuado según el valor
        if (valor < raiz.valor) {
            raiz.izquierdo = insertar(raiz.izquierdo, valor);
        } else if (valor > raiz.valor) {
            raiz.derecho = insertar(raiz.derecho, valor);
        } else {
            return raiz; // No permite nodos duplicados
        }

        // Actualiza la altura y realiza rotaciones según sea necesario para balancear el árbol
        raiz.altura = 1 + Math.max(obtenerAltura(raiz.izquierdo), obtenerAltura(raiz.derecho));

        int balance = obtenerBalance(raiz);

        // Realiza rotaciones según el caso para mantener el balance del árbol
        if (balance > 1 && valor < raiz.izquierdo.valor) {
            return rotacionDerecha(raiz);
        }

        if (balance < -1 && valor > raiz.derecho.valor) {
            return rotacionIzquierda(raiz);
        }

        if (balance > 1 && valor > raiz.izquierdo.valor) {
            raiz.izquierdo = rotacionIzquierda(raiz.izquierdo);
            return rotacionDerecha(raiz);
        }

        if (balance < -1 && valor < raiz.derecho.valor) {
            raiz.derecho = rotacionDerecha(raiz.derecho);
            return rotacionIzquierda(raiz);
        }

        return raiz;
    }

    // Método para obtener la altura de un nodo en el árbol
    static int obtenerAltura(NodoArbol nodo) {
        if (nodo == null) {
            return 0;
        }
        return nodo.altura;
    }

    // Método para obtener el balance de un nodo en el árbol
    static int obtenerBalance(NodoArbol nodo) {
        if (nodo == null) {
            return 0;
        }
        return obtenerAltura(nodo.izquierdo) - obtenerAltura(nodo.derecho);
    }

    // Métodos para realizar rotaciones en el árbol
    static NodoArbol rotacionDerecha(NodoArbol y) {
        NodoArbol x = y.izquierdo;
        NodoArbol T2 = x.derecho;

        x.derecho = y;
        y.izquierdo = T2;

        y.altura = Math.max(obtenerAltura(y.izquierdo), obtenerAltura(y.derecho)) + 1;
        x.altura = Math.max(obtenerAltura(x.izquierdo), obtenerAltura(x.derecho)) + 1;

        return x;
    }

    static NodoArbol rotacionIzquierda(NodoArbol x) {
        NodoArbol y = x.derecho;
        NodoArbol T2 = y.izquierdo;

        y.izquierdo = x;
        x.derecho = T2;

        x.altura = Math.max(obtenerAltura(x.izquierdo), obtenerAltura(x.derecho)) + 1;
        y.altura = Math.max(obtenerAltura(y.izquierdo), obtenerAltura(y.derecho)) + 1;

        return y;
    }

    // Método para contar la cantidad de hojas en el árbol
    static int contarHojas(NodoArbol raiz) {
        if (raiz == null) {
            return 0;
        }
        if (raiz.izquierdo == null && raiz.derecho == null) {
            return 1; // Nodo hoja
        }
        int hojasIzquierdo = contarHojas(raiz.izquierdo);
        int hojasDerecho = contarHojas(raiz.derecho);
        return hojasIzquierdo + hojasDerecho;
    }

    // Método para contar la cantidad de nodos en el árbol
    static int contarNodos(NodoArbol raiz) {
        if (raiz == null) {
            return 0;
        }
        int nodosIzquierdo = contarNodos(raiz.izquierdo);
        int nodosDerecho = contarNodos(raiz.derecho);
        return 1 + nodosIzquierdo + nodosDerecho;
    }

    // Método para contar la altura del árbol
    static int contarAltura(NodoArbol raiz) {
        if (raiz == null) {
            return 0;
        }
        int alturaIzquierdo = contarAltura(raiz.izquierdo);
        int alturaDerecho = contarAltura(raiz.derecho);
        return 1 + Math.max(alturaIzquierdo, alturaDerecho);
    }

    static boolean esCompleto(NodoArbol raiz) {
        if (raiz == null) {
            return true;
        }
        int altura = contarAltura(raiz);
        return esCompletoRec(raiz, altura, 0);
    }

    // Método para verificar si el árbol es completo de manera recursiva
    static boolean esCompletoRec(NodoArbol raiz, int altura, int nivel) {
        if (raiz == null) {
            return nivel >= altura;
        }

        if (nivel >= altura) {
            return false;
        }

        return esCompletoRec(raiz.izquierdo, altura, nivel + 1) && esCompletoRec(raiz.derecho, altura, nivel + 1);
    }

    // Métodos para recorrer el árbol en los distintos órdenes
    static void recorrerPostOrder(NodoArbol raiz) {
        if (raiz == null) {
            return;
        }
        recorrerPostOrder(raiz.izquierdo);
        recorrerPostOrder(raiz.derecho);
        System.out.print(raiz.valor + " ");
    }

    static void recorrerPreOrder(NodoArbol raiz) {
        if (raiz == null) {
            return;
        }
        System.out.print(raiz.valor + " ");
        recorrerPreOrder(raiz.izquierdo);
        recorrerPreOrder(raiz.derecho);
    }

    static void recorrerInOrder(NodoArbol raiz) {
        if (raiz == null) {
            return;
        }
        recorrerInOrder(raiz.izquierdo);
        System.out.print(raiz.valor + " ");
        recorrerInOrder(raiz.derecho);
    }
}


//Fin
