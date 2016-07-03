/* Exercicio 1.3.35 - Random queue (Algs 4)
 * Nome: Gabriel de Russo e Carmo
 * N USP: 9298041
 * Data: 20/03/2016
 * OBS: Compilado com 'javac-algs4' */
import edu.princeton.cs.algs4.*;

public class RandomQueue<Item> {
    private Item[] queue;
    private int front, back;
    
    /* Instancia uma nova fila com 4 espacos */
    public RandomQueue () {
        queue = (Item[]) new Object[4];
        front = back = 0;
    }

    /* Devolve true se a fila esta vazia */
    public boolean isEmpty () {
        return front == back;
    }

    /* Enfileira um item. Pode dobrar a fila se necessario */
    public void enqueue (Item item) {
        queue[back] = item;
        back = (back + 1) % queue.length;
        if (back == front) resize (2 * queue.length);
    }

    /* Desenfileira um item aleatorio */
    public Item dequeue () {
        Item ret, aux;
        int p = randomIndex ();
        aux = queue[front]; queue[front] = queue[p]; queue[p] = aux;
        ret = queue[front];
        front = (front + 1) % queue.length;
        return ret;
    }

    /* Retorna algum elemento da fila, sem remocao */
    public Item sample () {
        int p = randomIndex ();
        return queue[p];
    }

    /* Auxiliar para redimensionar a fila para tamanho n, mantendo sua estrutura */
    private void resize (int n) {
        Item[] copy = (Item[]) new Object[n];
        for (int i = front; i < queue.length; i++)
            copy[i - front] = queue[i];
        for (int i = 0; i < back; i++)
            copy[i + queue.length - front] = queue[i];
        front = 0;
        back = queue.length;
        queue = copy;
    } 
    
    /* Retorna um indice aleatorio que contem um elemento da fila */
    private int randomIndex () {
        if (back > front) 
            return StdRandom.uniform (front, back);
        return (StdRandom.uniform (0, queue.length - front + back) + front) % queue.length;
    }
}

