(ns pandemic.domain.api.move_player
  (:require [pandemic.domain.model.city :refer :all]
            [pandemic.domain.model.player :refer :all]
            [pandemic.domain.model.turn :refer :all]))

(defn- is-adjacent
  "Returns whether a city is adjacent to other"
  [game to from]
  (contains? (get-in game [:cities to :neighbors]) from))

(defn- player-city
  "Returns the city where the player is located"
  [game player-index]
  (:name (some #(contains? (:players %) player-index)
               (:cities game))))

(defn drive/ferry
  "Moves a player to an adjacent city"
  [game player-index to]
  (let [from (player-city player-index)]
    (assert (can-take-action game))
    (assert (is-adjacent from to)
            (str "The two cities (" from "," to ") have to be adjacent when using drive/ferry"))
    (-> game
        (remove-player from player-index)
        (put-player to player-index)
        (next-action))))

(defn direct-flight
  "Moves a player by direct flight spending one city card with the same name
  as the city where the player is going"
  [game player-index to]
  (let [from (player-city player-index)]
    (assert (can-take-action game))
    (assert (contains? (get-in game [:players player-index :player-cards] to))
            (str "The user needs a card with the city he is going (" to ") when using direct flight"))
    (-> game
        (discard-card player-index to)
        (remove-player from player-index)
        (put-player to player-index)
        (next-action))))

(defn charter-flight
  "Moves a player by charter flight spending one city card with the same name
  as the city where the player is located"
  [game player-index to]
  (let [from (player-city player-index)]
    (assert (can-take-action game))
    (assert (contains? (get-in game [:players player-index :player-cards] from))
            (str "The user needs a card with the city he is currently located (" from ") when using charter flight"))
    (-> game
        (discard-card player-index from)
        (remove-player from player-index)
        (put-player to player-index)
        (next-action))))

(defn shuttle-flight
  "Moves a player by shuttle flight from a city with a research station to any
  other city with a research station"
  (let [from (player-city player-index)]
    [game player-index to]
    (assert (can-take-action game))
    (assert (and (get-in game [:cities from :research-station?]) (get-in game [:cities to :research-station?]))
            (str "There needs to be a research station in both cities (" from ", " to ") when using shuttle flight"))
    (-> game
        (remove-player from player-index)
        (put-player to player-index)
        (next-action))))

(defn build-research-station
  "Creates a research station in the city where the player is located"
  [game player-index]
  (let [city (player-city player-index)]
    (assert (can-take-action game))
    (assert (contains? (get-in game [:players player-index :player-cards] city))
            (str "The player needs to have the city card (" city ") in hand when building a research station"))
    (-> game
        (discard-card player-index city)
        (put-research-station city)
        (next-action))))
