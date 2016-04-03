(ns pandemic.domain.model.player_card)

(def player-cards
  [:algiers :atlanta :baghdad :bangkok
   :beijing :bogota :buenos-aires :cairo
   :chennai :chicago :delhi :essen
   :ho-chi-minh-city :hong-kong :istanbul :jakarta
   :johannesburg :karachi :khartoum :kinshasa
   :kolkata :lagos :lima :london
   :los-angeles :madrid :manila :mexico-city
   :miami :milan :montreal :moscow
   :mumbai :new-york :osaka :paris :riyadh
   :san-francisco :santiago :sao-paulo :seoul
   :shangai :st-petersburg :sydney :taipei
   :tehran :tokyo :washington])

(defn initial-player-cards
  "Creates the player cards separated in the draw and discard piles.
  It does not include special events nor epidemic cards"
  []
  {:draw-pile (shuffle player-cards)
   :discard-pile []})

(defn draw-cards
  "Pops N cards from the draw pile.
  Returns a vector of the form [updated game, popped cards]"
  [game cards-count]
  (let [player-cards (:player-cards game)
        draw-pile (:draw-pile player-cards)
        drawn-cards (vec (take cards-count draw-pile))
        new-draw-pile (vec (drop cards-count draw-pile))
        new-player-cards (assoc player-cards :draw-pile new-draw-pile)
        new-game (assoc game :player-cards new-player-cards)]
    [new-game drawn-cards]))

(defn shuffle-epidemic-cards
  "Introduces and shuffles N epidemic cards in the player cards"
  [game epidemic-cards-count]
  (let [draw-pile (get-in game [:player-cards :draw-pile])
        cards-in-each-group (quot (count draw-pile) epidemic-cards-count)
        remaining-cards-count (rem (count draw-pile) epidemic-cards-count)
        cards-groups (partition cards-in-each-group
                                cards-in-each-group
                                (repeat remaining-cards-count nil)
                                draw-pile)
        remaining-cards (nth cards-groups remaining-cards-count)
        cc (map (fn [cards card]
                  (if (nil? %2)
                    %1
                    (conj %2 %2)))
                cards-groups
                remaining-cards)]))
