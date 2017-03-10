(ns pandemic.domain.model.infection_card
    (:require [clojure.spec :as s]))

(s/def ::draw-pile (s/coll-of keyword?))
(s/def ::discard-pile (s/coll-of keyword?))
(s/def ::infection-cards (s/keys :req [::draw-pile ::discard-pile]))

(def infection-cards
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

(defn reveal-cards
  "Pops N cards from the draw pile and puts them in the discard pile.
  Returns a vector of the form {:game: updated game :cards revealed cards}"
  [game cards-count]
  (let [infection-cards (:infection-cards game)
        draw-pile (:draw-pile infection-cards)
        revealed-cards (vec (take cards-count draw-pile))
        new-draw-pile (vec (drop cards-count draw-pile))
        new-infection-cards (assoc infection-cards :draw-pile new-draw-pile)
        new-game (assoc game :infection-cards new-infection-cards)]
    {:game new-game :cards revealed-cards}))

(defn initial-infection-cards
  "Creates the infection cards separated in the draw and discard piles"
  []
  {:draw-pile (shuffle infection-cards)
   :discard-pile []})
