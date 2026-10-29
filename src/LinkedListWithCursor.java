import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class LinkedListWithCursor<T> {
    //Importante: No cambiar ni los nombres ni la visibilidad
    Node<T> left;
    Node<T> right;
    int sizeLeft;
    int sizeRight;

    public LinkedListWithCursor() {
        left = null;
        right = null;
        sizeLeft = 0;
        sizeRight = 0;
    }


    public LinkedListWithCursor(Collection<T> col, int index) {
        left = null;
        right = null;
        sizeLeft = 0;
        sizeRight = 0;

        // Indice del puntero fuera de rango
        if (index < 0 || index > col.size()) {
            throw new IndexOutOfBoundsException();
        }

        // Caso de Collection vacia
        if (col.isEmpty()) {
            return;
        }

        // Caso general
        Iterator<T> iter = col.iterator();
            // Primer nodo izquierda
        if (index > 0) {
            left = new Node<>(iter.next(), null);
            sizeLeft++;
        }
            // Izquierda
        for (int i = 1; i < index; i++) {
            left = new Node<>(iter.next(),left);
            sizeLeft++;
        }

            // Primer nodo derecha
        if (index < col.size()) {
            right = new Node<>(iter.next(), null);
            sizeRight++;
        }
            // Derecha
        Node<T> lastRight = right;
        for (int i = index + 1; i < col.size(); i++) {
            lastRight.next = new Node<>(iter.next(),null);
            lastRight = lastRight.next;
            sizeRight++;
        }
    }


    public int index() {
        return sizeLeft;
    }


    public int size() {
        return sizeLeft + sizeRight;
    }


    public T current() {
        if (right == null)
            return null;

        return right.data;
    }


    public void add(T item) {
        left = new Node<>(item, left);
        sizeLeft++;
    }


    public T remove() {
        if (right == null) {
            throw new NoSuchElementException();
        }
        T aux = right.data;
        right = right.next;
        sizeRight--;
        return aux;
    }


    public T next() {
        if (right == null) {
            throw new NoSuchElementException();
        }
        Node<T> aux = right;
        right = aux.next;
        aux.next = left;
        left = aux;
        sizeRight--;
        sizeLeft++;
        return aux.data;
    }


    public T previous() {
        if (left == null) {
            throw new NoSuchElementException();
        }
        Node<T> aux = left;
        left = aux.next;
        aux.next = right;
        right = aux;
        sizeRight++;
        sizeLeft--;
        return aux.data;
    }


    public T moveTo(int index) {
       if (index < 0 || index > size()) {
           throw new IndexOutOfBoundsException();
       }
       int desplazamiento = index - sizeLeft;
       if (desplazamiento < 0) {
           desplazamiento = - desplazamiento;
           for (int i = 0; i < desplazamiento; i++) {
               previous();
           }
       }
       else {
           for (int i = 0; i < desplazamiento; i++) {
               next();
           }
       }
       if (right == null) {
           return null;
       }
       return right.data;
    }

    /**
     * Devuelve la lista con el formato [izquierda][*derecha], por ejemplo
     * "[l, u][*n, a]", "[][*s, o]", "[s, o][*]" o "[][*]".
     *  NO LO MODIFIQUES.
     */
    @Override
    public String toString() {
        // La cadena izquierda está en orden inverso: se copia a un array al revés
        Object[] leftItems = new Object[sizeLeft];
        Node<T> n = left;
        for (int i = sizeLeft - 1; i >= 0 && n != null; i--) {
            leftItems[i] = n.data;
            n = n.next;
        }
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < leftItems.length; i++) {
            if (i > 0) sb.append(", ");
            sb.append(leftItems[i]);
        }
        sb.append("][*");
        n = right;
        boolean first = true;
        while (n != null) {
            if (!first) sb.append(", ");
            sb.append(n.data);
            first = false;
            n = n.next;
        }
        return sb.append("]").toString();
    }
}
