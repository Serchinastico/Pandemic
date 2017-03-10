(ns pandemic.domain.model.player
  (:require [pandemic.domain.model.player_card :as player_card]
            [clojure.spec :as s]))

(s/def ::name string?)
(s/def ::color keyword?)
(s/def ::role keyword?)
(s/def ::player (s/keys :req [::name ::color ::role ::player-cards]))

(def colors [:black :blue :pink :white])

(defn initial-player
  "Creates a new player"
  [name color]
  {:name name
   :color color
   :role nil
   :player-cards (set [])})

(defn discard-card
  "Discards the selected card from the players hand"
  [game player-index card-name]
  (let [players (:players game)
        player (get players player-index)]
    (disj (:player-cards player) card-name)))

(defn draw-cards
  "Draws n cards for the given player"
  [game player-index cards-count]
  (let [[game drawn-cards] (player_card/draw-cards game cards-count)
        players (:players game)
        player (get players player-index)
        new-player (assoc player :player-cards drawn-cards)
        new-players (assoc players player-index new-player)]
    (assoc game :players new-players)))
