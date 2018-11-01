public class A3MorrowLuke {


    public static void main(String[] args) {

    }
}

/*
 */
class Deck
{
    private final int NUM_SHUFFLES = 52;
    private final int STANDARD_DECK_SIZE = 52;

    Card[] localDeck;

    public Deck()
    {
        make(STANDARD_DECK_SIZE);
        shuffle();
    }

    private void make(int size)
    {
       localDeck = new Card[size];
        for(int i = 0; i<localDeck.length; i++)
        {localDeck[i]=new Card(i);}
    }

    private void shuffle()
    {
        for(int i = 0; i<NUM_SHUFFLES; i++)
            swap(i, (int)(STANDARD_DECK_SIZE*Math.random()));
    }

    private void swap(int a, int b)
    {
        Card temp = localDeck[a];
        localDeck[a]=localDeck[b];
        localDeck[b]=temp;
    }

    public void dealHands(Hand a, Hand b)
    {
        for(int i = 0; i<localDeck.length; i++)
        {

            if(i/(localDeck.length/2) == 0)
                a.enter(localDeck[i]);
            else
                b.enter(localDeck[i]);
        }
    }
}


/*
 */
class Card
{
    private String[] SUIT_TRANSLATION = {"S", "D", "H", "C"};
    private String[] VALUE_TRANSLATION = {"A","2","3","4","5","6","7","8","9","10","J","Q","K"};
    private final int SUIT_SIZE = 13;
    private final int NUM_SUITS = 4;

    private String suit;
    private int value;


    public Card(int cardNum)
    {
        suit = SUIT_TRANSLATION[cardNum/NUM_SUITS];
        value = cardNum%SUIT_SIZE +1;//+1 b/c mod start at 0, and 0 isn't a card
    }


    public int getValue() {
        return value;
    }

    /*A chunk used to compare the values of cards */
    public boolean isGreaterThan(Card a)
    {return value > a.getValue();}
    public boolean isLessThan(Card a)
    {return value < a.getValue();}
    public boolean isEqualTo(Card a)
    {return value == a.getValue();}

    @Override
    public String toString() {
        return "{"+VALUE_TRANSLATION[value] +":"+ suit+"}";
    }
}



/*
 */
class Hand
{
    public static final int HAND_SIZE = 33;
    private int start;
    private int end;
    Card[] queue;


    public Hand()
    {
            queue = new Card[HAND_SIZE];
            start=0;
            end=start;
    }

    public void enter(Card a)
    {
        queue[end] = a;
        end = (end++)%queue.length;
    }

    public Card leave()
    {
        Card storage = queue[start];

        queue[start]=null;//remove the item
        start = (start++)%queue.length;//point at the next in line

        return storage;
    }


}
