(ns pandemic.domain.action.move_player
  (:require [pandemic.domain.model.city :refer :all]
            [pandemic.domain.model.player :refer :all]))

(defn drive/ferry
  "Moves a player to an adjacent city"
  [game player-index from to]
  (-> game
      (remove-player from player-index)
      (put-player to player-index)))

(defn direct-flight
  "Moves a player by direct flight spending one city card with the same name
  as the city where the player is going"
  [game player-index from to]
  (-> game
      (discard-card player-index to)
      (remove-player from player-index)
      (put-player to player-index)))

(defn charter-flight
  "Moves a player by charter flight spending one city card with the same name
  as the city where the player is located"
  [game player-index from to]
  (-> game
      (discard-card player-index from)
      (remove-player from player-index)
      (put-player to player-index)))
