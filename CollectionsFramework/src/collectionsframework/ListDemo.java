package collectionsframework;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;


/**
 * Order is important when checking for equality in lists.
 *
 * @author skorkmaz
 */
public class ListDemo {

    private static <E> void printCollection(Collection<E> c) {
        for (E e : c) {
            System.out.println("e = " + e);
        }
    }

    public static void main(String[] args) {
        List<String> c = new ArrayList<>(Arrays.asList("z", "y", "i"));
        List<?> d = new ArrayList<>(Arrays.asList("z", "y", "i"));
        List<?> e = new ArrayList<>(Arrays.asList("i", "z", "y"));
        System.out.println("c.equals(c) = " + c.equals(c));
        System.out.println("d.equals(c) = " + d.equals(c));
        System.out.println("e.equals(noDups) = " + e.equals(c));
        System.out.println("Sorted c:");
        c.sort(String::compareTo);
        printCollection(c);

        List<?> deck = new ArrayList<>(c);
        List<?> hand = dealHand(deck, 2);
        System.out.println("dealt hand: ");
        printCollection(hand);
        System.out.println("remaining deck: ");
        printCollection(deck);
    }

    /**
     * Deal a hand from a deck. That is, it returns a new List (the "hand") containing the specified number of elements taken from the end of the specified List (the
     * "deck"). The elements returned in the hand are removed from the deck.
     */
    public static <E> List<E> dealHand(List<E> deck, int n) {
        int deckSize = deck.size();
        List<E> handView = deck.subList(deckSize - n, deckSize);
        List<E> hand = new ArrayList<>(handView);
        handView.clear();
        return hand;
    }

}
