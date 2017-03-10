(ns pandemic.domain.model.player_card
  (:require [clojure.spec :as s]))

(s/def ::draw-pile (s/coll-of keyword?))
(s/def ::discard-pile (s/coll-of keyword?))
(s/def ::player-cards (s/keys :req [::draw-pile ::discard-pile]))

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

(defn split-in-groups
  "Splits the given cards in groups of the same size. If there are cards
  remaining then they are added to the groups until there are no more
  cards"
  [items groups-count]
  (let [items-per-group (quot (count items) groups-count)
        remaining-items-count (rem (count items) groups-count)
        items-groups (partition items-per-group items)
        remaining-items (concat (take-last remaining-items-count items) (repeat (- groups-count remaining-items-count) nil))]
    (map (fn [items item]
           (if (nil? item)
             items
             (conj items item)))
         items-groups
         remaining-items)))

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
        cards-groups (split-in-groups draw-pile epidemic-cards-count)
        cards-groups-with-epidemics (map #(conj % :epidemic) cards-groups)
        shuffled-cards-groups-with-epidemics (map #(shuffle %) cards-groups-with-epidemics)
        new-draw-pile (flatten shuffled-cards-groups-with-epidemics)
        new-player-cards (assoc (get-in game [:player-cards]) :draw-pile new-draw-pile)]
    (assoc game :player-cards new-player-cards)))
