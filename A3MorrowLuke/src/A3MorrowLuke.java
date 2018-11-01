public class A3MorrowLuke {


    public static void main(String[] args) {

    }
}

class Deck{

    public final int SPADES = 0, DIAMONDS = 1, HEARTS = 2, CLUBS =3;
    public final int ACE = 1, JACK = 11, QUEEN = 12, KING = 13;
    private final int NUM_SHUFFLES = 52;
    private final int STANDARD_DECK_SIZE = 52;
    private final int SUIT_SIZE = 13;
    private final int NUM_SUITS = 4;

    int[] localDeck;

    public Deck()
    {
        make(STANDARD_DECK_SIZE);
        shuffle();
    }

    private void make(int size)
    {
       localDeck = new int[size];
        for(int i: localDeck)
        {localDeck[i]=i+1;}
    }

    private void shuffle()
    {
        for(int i = 0; i<NUM_SHUFFLES; i++)
            swap(i, (int)(STANDARD_DECK_SIZE*Math.random()));
    }

    private void swap(int a, int b)
    {
        int temp = localDeck[a];
        localDeck[a]=localDeck[b];
        localDeck[b]=temp;
    }

    public int getSuit(int a)
    { return a/SUIT_SIZE;}

    public int getValue(int a)
    { return a%NUM_SUITS +1;} //+1 b/c mod start at 0, and 0 isn't a card

    public void dealHands(Hand a, Hand b)
    {
        for(int i = 0; i<localDeck.length; i++)
        {

            if(i/Hand.HAND_SIZE == 0)
                a.enter(localDeck[i]);
            else
                b.enter(localDeck[i]);
        }
    }
}

class Hand
{
    public static final int HAND_SIZE = 33;
    private int start;
    private int end;
    int[] queue;


    public Hand()
    {
            queue = new int[HAND_SIZE];
            start=0;
            end=HAND_SIZE-1;//-1 b/c we want the last position of the array
    }

    public void enter(int a)
    {
        assert(a<52 && a>=0);
        end = a;
        end = (end++)%queue.length;
    }

    public int leave()
    {
        int storage = queue[start];
        queue[start]=-1;//remove the item
        start = (start++)%queue.length;//point at the next in line
        return storage;
    }


}
