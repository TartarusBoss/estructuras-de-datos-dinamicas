using System;

class Nodo
{
    public int valor;
    public Nodo? proximo;

    public Nodo(int valor)
    {
        this.valor = valor;
        this.proximo = null;
    }

    public static Nodo AgregarNodoALista(Nodo lista, Nodo valorIngresado)
    {
        if (lista == null)
        {
            return valorIngresado; // Si la lista está vacía, el nuevo nodo se convierte en la lista.
        }
        if (valorIngresado.valor == lista.valor)
        {
            Console.WriteLine("No se puede agregar el elemento, ya se encuentra en los nodos");
            return lista; // El nodo ya está en la lista, no hacemos cambios.
        }

        // Si el valor del nuevo nodo es menor que el valor del primer nodo de la lista.
        if (valorIngresado.valor < lista.valor)
        {
            valorIngresado.proximo = lista;
            return valorIngresado; // El nuevo nodo se convierte en el nuevo inicio de la lista.
        }

        Nodo actual = lista;
        while (actual.proximo != null && valorIngresado.valor > actual.proximo.valor)
        {
            actual = actual.proximo;
        }

        // Insertar el nuevo nodo después del nodo actual.
        valorIngresado.proximo = actual.proximo;
        actual.proximo = valorIngresado;

        return lista; // Devuelve la lista con el nuevo nodo insertado.
    }
}

class Program
{
    static void Main(string[] args)
    {
        // Crear una lista enlazada existente
        Nodo lista = new Nodo(10);
        lista = Nodo.AgregarNodoALista(lista, new Nodo(2));
        lista = Nodo.AgregarNodoALista(lista, new Nodo(5));
        lista = Nodo.AgregarNodoALista(lista, new Nodo(7));
        lista = Nodo.AgregarNodoALista(lista, new Nodo(3));

        // Agregar un nuevo nodo a la lista
        Nodo nuevoNodo = new Nodo(6);
        lista = Nodo.AgregarNodoALista(lista, nuevoNodo);

        // Imprimir la lista actualizada
        ImprimirLista(lista);
    }

    static void ImprimirLista(Nodo lista)
    {
        Nodo actual = lista;
        while (actual != null)
        {
            Console.Write(actual.valor + " -> ");
            actual = actual.proximo;
        }
        Console.WriteLine("null");
    }
}
