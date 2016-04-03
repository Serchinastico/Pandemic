(ns pandemic.domain.action.set_up_game
  (:require [pandemic.domain.model.city :refer :all]
            [pandemic.domain.model.cure :refer :all]
            [pandemic.domain.model.game :refer :all]
            [pandemic.domain.model.infection_card :refer :all]
            [pandemic.domain.model.infection_rate_track :refer :all]
            [pandemic.domain.model.player :refer :all]
            [pandemic.domain.model.player_card :as player_card [shuffle_epidemic_cards]]
            [pandemic.domain.model.role :refer :all]
            [pandemic.domain.model.turn :refer :all]))

(defn player-configuration
  "Creates the configuration for a single player"
  [name color]
  {:name name
   :color color})

(defn configuration
  "Creates a configuration to start a new game"
  [players difficulty]
  {:players players
   :difficulty difficulty})

(defn create-players
  "Create the initial players for a new game"
  [game configuration]
  (assoc game :players
        (into [] (map #(initial-player (:name %) (:color %)) (:players configuration)))))

(defn deal-roles
  "Deal roles between all the players"
  [game configuration]
  (let [roles (take (count (:players configuration)) roles)]
    (assoc game :players
          (into [] (map #(assoc %1 :role %2) (:players game) roles)))))

(defn place-initial-research-station
  "Places the initial research station in Atlanta"
  [game]
  (put-research-station game :atlanta))

(defn deal-player-cards
  "Deal player cards to each player.
    For a 4 players game -> 2 cards each
    For a 3 players game -> 3 cards each
    For a 2 players game -> 4 cards each"
  [game]
  (let [players (:players game)
        players-count (count players)
        cards-per-player (get [4 3 2] (- players-count 2))]
    (reduce #(draw-cards %1 %2 cards-per-player) game (range players-count))))

(defn shuffle-epidemic-cards
  "Shuffle the epidemic cards into the player cards"
  [game configuration]
  (let [difficulty (:difficulty configuration)
        epidemic-cards-count (difficulty {:introductory 4 :normal 5 :heroic 6})]
    (player_card/shuffle-epidemic-cards game epidemic-cards-count)))

(defn set-up-game
  "Configures the game with the initial state"
  [game configuration]
  (let [game-step-0 (create-players game configuration)
        game-step-1 (deal-roles game-step-0 configuration)
        game-step-2 (place-initial-research-station game-step-1)
        game-step-3 (deal-player-cards game-step-2)
        game-step-4 (shuffle-epidemic-cards game-step-3 configuration)]
  game-step-3))

(def game (initial-game))
(def player-one (player-configuration "Sergio" :pink))
(def player-two (player-configuration "Carlos" :black))
(def player-three (player-configuration "Pablo" :blue))
(def player-four (player-configuration "Dani" :white))
(def configuration (configuration [player-one player-two player-three player-four] :normal))

(def game-step-0 (create-players game configuration))
(def game-step-1 (deal-roles game-step-0 configuration))
(def game-step-2 (place-initial-research-station game-step-1))
(def game-step-3 (deal-player-cards game-step-2))
(:players game-step-3)
