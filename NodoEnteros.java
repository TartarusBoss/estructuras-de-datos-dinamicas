
package prueba;

public class NodoEnteros {

    int valor;
    NodoEnteros hijoIzquierdo;
    NodoEnteros hijoDerecho;

    boolean esHoja() {
        return hijoIzquierdo == null && hijoDerecho == null;
    }

    void recorrerPreOrder() {
        System.out.println(valor); 
        if (hijoIzquierdo != null) {
            hijoIzquierdo.recorrerPreOrder();
        }
        if (hijoDerecho != null) {
            hijoDerecho.recorrerPreOrder();
        }
    }
    void recorrerInOrder() {
        if (hijoIzquierdo != null) {
            hijoIzquierdo.recorrerInOrder();
        }
        System.out.println(valor);
        if (hijoDerecho != null) {
            hijoDerecho.recorrerInOrder();
        }    
    }
}