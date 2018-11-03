/*
 */
public class A3MorrowLuke {
    private static Game mainGame;

    public static void main(String[] args) {
        mainGame=new Game();
        mainGame.playGame();

        System.out.println("\nProgram ended normally.");
    }


}

/*This holds all of the mechanics for the game and runs the game
    first it initializes the players and the deck then loads up the
    players' hands.
    when playGame is called it runs a while loop which plays rounds until
    one player runs out of cards.
 */
class Game
{

    /*tags used for returning who won the war
    */
    private static final int P1 = 1;
    private static final int P2 = 2;
    private static final int TIE = 0;//in the case that both players run out of cards

    private final int NUM_FLIPPED_PER_ROUND = 2;//number of cards flipped total per comparison
    private Hand[] players;


    public Game()
    {
        /*initialize the game items
        */
        players = new Hand[2];
        Deck mainDeck = new Deck();
        players[0] = new Hand();
        players[1] = new Hand();

        mainDeck.dealHands(players[0],players[1]);
    }

    /*the program spends most of its time in this method, it keeps running until
        one of the players runs out of cards. Also prints out an appropriate message
        at the end of the game.
    */
    public void playGame()
    {

        while(!players[0].isEmpty() && !players[1].isEmpty()) {
            doRound();
        }

        /*win or loss conditions and notifications for the user.
         */
        if(!players[0].isEmpty()) {
            System.out.println("Player 2 ran out of cards! Player 1 wins!!");
        }
        else{
            System.out.println("Player 1 ran out of cards! Player 2 wins!!");
        }
    }

    /*plays one round of war, if the cards are of equal weight, calls doWar.
    */
    private void doRound()
    {
        Card[] cards = flipCards(players,NUM_FLIPPED_PER_ROUND);

        if(cards[0].isGreaterThan(cards[1])) {
            players[0].enter(cards);
            System.out.println(cards[0]+" vs "+cards[1] + "\t Player 1 wins!");
        }
        else if(cards[0].isLessThan(cards[1])) {
            players[1].enter(cards);
            System.out.println(cards[0]+" vs "+cards[1] + "\t Player 2 wins!");
        }
        else {
            System.out.println(cards[0]+" vs "+cards[1] + " It is War!");

            int warResults = doWar();

            if(warResults==P1) {
                players[0].enter(cards);

            }else if(warResults==P2) {
                players[1].enter(cards);

            }
        }
    }


    /*flips cards either from many players(the first method) or just one(the second).
        the first is meant for flipping cards at the beginning of a round or a war.
        the second is meant for unloading the ante.
    */
    private Card[] flipCards(Hand[] a, int numToBeFlipped)
    {
        Card[] flippedCards = new Card[numToBeFlipped];

        for(int i = 0; i<numToBeFlipped; i++) {
            flippedCards[i] = a[i%a.length].leave();
        }

        return flippedCards;
    }
    private Card[] flipCards(Hand a, int numToBeFlipped)
    {
        Card[] flippedCards = new Card[numToBeFlipped];

        for(int i = 0; i<numToBeFlipped; i++) {
            flippedCards[i] = a.leave();
        }

        return flippedCards;
    }


    /*flips 4 cards and adds them to ante, then flips 2 more and compares them
        whose ever weighs more wins all of the cards.
    */
    private int doWar() {
        final int NUM_IN_ANTE = 4;
        Hand ante = new Hand();
        Card[] determinants = flipCards(players,NUM_FLIPPED_PER_ROUND);

        //load the revealed cards into ante
        ante.enter(flipCards(players,NUM_IN_ANTE));


        /* In case the war turns up the end of the deck for a player making determinate null
         */
        if (determinants[0] != null && determinants[1] == null) {
            return P1;
        }
        else if(determinants[0] == null && determinants[1]!= null) {
            return P2;
        }
        else if(determinants[0] == null && determinants[1]== null){
            return TIE;
        }


        /*compare the two determinants and choose the winner
         */
        if (determinants[0].isGreaterThan(determinants[1])) {//player 1 wins
            players[0].enter(determinants);
            players[0].enter(flipCards(ante,NUM_IN_ANTE));
            System.out.println("Player 1 takes it home with a "+determinants[0]+" to beat out Player 2's"+determinants[1]);
            return P1;

        } else if (determinants[0].isLessThan(determinants[1])) {//player 2 wins
            players[1].enter(determinants);
            players[1].enter(flipCards(ante,NUM_IN_ANTE));
            System.out.println("Player 2 takes it home with a "+determinants[1]+" to beat out Player 1's"+determinants[0]);
            return P2;

        } else {//if war occurs again
            System.out.println(determinants[0]+" vs "+determinants[1] + " It is War! ...again... Thrilling!");
            int warResults = doWar();

            if (warResults == P1) {
                players[0].enter(determinants);
                players[0].enter(flipCards(ante,NUM_IN_ANTE));
                return P1;
            } else if (warResults == P2) {
                players[1].enter(determinants);
                players[1].enter(flipCards(ante,NUM_IN_ANTE));
                return P2;
            } else
                return TIE;//one or both players run out of cards
        }
    }
}

/*This class is a representation of a deck of cards.

    To ensure that the hands do not have duplicate cards,
    this class makes an array of card items, and assigns each a
    number from 0 to 52(DEFAULT) then shuffles them.

    In the method call(dealHands) we splits the cards
    evenly into two provided hands for use in the game.
 */
class Deck
{

    private final int STANDARD_DECK_SIZE = 52;
    private Card[] localDeck;

    public Deck()
    {
        make(STANDARD_DECK_SIZE);
        shuffle();
    }


    /*fills the empty Card array with new cards
        handed the parameter of their position to ensure
        no duplicate cards
    */
    private void make(int size)
    {
       localDeck = new Card[size];
       for(int i = 0; i<localDeck.length; i++) {
           localDeck[i]=new Card(i);
       }
    }


    /*takes an array of Card items and swaps them with
        a randomly chosen item in the deck.
    */
    private void shuffle()
    {
        final int NUM_SHUFFLES = 52;

        for(int i = 0; i<NUM_SHUFFLES; i++) {
            swap(i, (int) (STANDARD_DECK_SIZE * Math.random()));
        }
    }


    /*swaps two card items in a card array.
        only used in shuffle.
    */
    private void swap(int a, int b)
    {
        Card temp = localDeck[a];
        localDeck[a]=localDeck[b];
        localDeck[b]=temp;
    }


    /*takes two hands and fills their queues with
        their half of the randomized deck.
    */
    public void dealHands(Hand a, Hand b)
    {
        for(int i = 0; i<localDeck.length; i++) {
            if(i/(localDeck.length/2) == 0) {
                a.enter(localDeck[i]);
            } else {
                b.enter(localDeck[i]);
            }
        }
    }
}


/*This class is used to store the information about the card
    in an easy to use way which allows for comparisons and a simple
    toString.
    Every number from 1-52 has a unique card, the constructor
    splits the card into a weight and a suit.
 */
class Card
{
    /*a sort of make-shift dictionary in which number values are converted
        into string values appropriate for cards
    */
    private String[] SUIT_TRANSLATION = {"S", "D", "H", "C"};
    private String[] VALUE_TRANSLATION = {"A","2","3","4","5","6","7","8","9","10","J","Q","K"};
    private final int SUIT_SIZE = 13;

    private int suit;
    private int weight;


    public Card(int cardNum)
    {
        suit = cardNum/SUIT_SIZE;
        weight = cardNum%SUIT_SIZE +1;//+1 b/c mod start at 0, and 0 isn't a card
    }

    /*used to compare the values of cards
    */
    public boolean isGreaterThan(Card a)
    {return weight > a.weight;}
    public boolean isLessThan(Card a)
    {return weight < a.weight;}

    @Override
    public String toString() {//weight -1 because the enum starts with ace being at 0
        return "{"+VALUE_TRANSLATION[weight -1] +":"+ SUIT_TRANSLATION[suit]+"}";
    }
}



/*This class holds a queue that is used to store
    each player's hand(one player per instance).
    It being a queue, it has functions for entering,
    leaving, and checking if the queue is empty
 */
class Hand
{

    /*the most items that can be in a hand at once is 52
        (and that being when a player has won).*/
    private static final int HAND_SIZE = 52;

    private int start;
    private int end;
    private Card[] queue;

    public Hand()
    {
        queue = new Card[HAND_SIZE];
        start=0;
        end=start;
    }

    /*adds an item to the end of the queue, in this case
        the item is a Card.
    */
    public void enter(Card a)
    {
        queue[end] = a;//set the end position to be the new item
        end = (end+1)%queue.length;//move the end position by one.
    }

    public void enter(Card[] a)
    {
        for(Card i:a) {
            queue[end] = i;//set the end position to be the new item
            end = (end + 1) % queue.length;//move the end position by one.
        }
    }

    /*removes and returns the first item from the queue,
        in this case the item is a Card.
    */
    public Card leave()
    {
        Card storage = queue[start];//store the item the caller wants

        queue[start]=null;//remove the item from the queue
        start = (start+1)%queue.length;//point at the next in line

        return storage;//give the requested item to the caller
    }

    /*checks if the list is empty by seeing if there is
        an item at the front of the queue.
    */
    public boolean isEmpty()
    {return queue[start] == null;}

}
