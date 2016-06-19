(ns pandemic.domain.model.player
  (:require [pandemic.domain.model.player_card :as player_card]))

(def colors [:black :blue :pink :white])

(defn initial-player
  "Creates a new player"
  [name color]
  {:name name
   :color color
   :role nil
   :player-cards []})

(defn draw-cards
  "Draws n cards for the given player"
  [game player-index cards-count]
  (let [[game drawn-cards] (player_card/draw-cards game cards-count)
        players (:players game)
        player (get players player-index)
        new-player (assoc player :player-cards drawn-cards)
        new-players (assoc players player-index new-player)]
    (assoc game :players new-players)))
