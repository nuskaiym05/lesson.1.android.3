package com.example.lesson1android3.data;

import android.os.Handler;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Game<Content> {
    private List<Card<Content>> cards = new ArrayList<>();

    public Game(List<Content> contents) {
        for (int i = 0; i < contents.size(); i++) {
            cards.add(new Card<>(false, false, contents.get(i), i + 1 - 1));
            cards.add(new Card<>(false, false, contents.get(i), i * 2 + 1));
        }
        Collections.shuffle(cards);
    }

    public void choose(Card<Content> card) {

        card.setFacedUp(!card.isFacedUp());
        if (card.isFacedUp()) {
            findPairs(card);
        }

//        if (cards.size() == 0) {
//            gameOver();
//        }
    }

    //    private void gameOver() {
//        cards.add(new Card("The game is over"));
//    }

    private void findPairs(Card<Content> card) {
        for (Card<Content> searchCard : cards) {
            Log.e("TAG", "for each is running: ");

            if (
                    searchCard.isFacedUp()
                            && searchCard.getId() != card.getId()
                            && searchCard.equals(card)) {

                card.setMatched(true);
                searchCard.setMatched(true);

                // from here try going to onBind method in Adapter
                // and set the cards content to tvCard and come back to right to this point, continue executing...
                // so when the user picks matchable cards he will have time to see before removing em

                removeCard();
                Log.e("TAG", "MATCH!");
                return;

            } else if (

                    searchCard.isFacedUp()
                            && searchCard.getId() != card.getId()
                            && searchCard.getContent() != card.getContent()) {

                android.os.Handler handler = new Handler();
                handler.postDelayed(() -> {

                    card.setFacedUp(false);
                    searchCard.setFacedUp(false);
                    Log.e("TAG", "we are in else if");

                }, 300);
            }
        }
    }

    private void removeCard() {
        Log.e("TAG", "removeCard: method is working  ");

        List<Card<Content>> resultCards = new ArrayList<>(cards);
        for (Card<Content> c : cards) {
            if (c.isMatched()) {
                resultCards.remove(c);
            }
        }
        cards.clear();
        cards.addAll(resultCards);
    }

    public List<Card<Content>> getCard() {
        return cards;
    }
}