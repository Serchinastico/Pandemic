(ns pandemic.domain.action.move_player
  (:require [pandemic.domain.model.city :refer :all]))

(defn move-player
  "Moves a player to an adjacent city"
  [game player from to]
  (-> game
      (remove-player from player)
      (put-player to player)))
