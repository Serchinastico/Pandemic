(ns pandemic.domain.model.game
  (:require [pandemic.domain.model.city :refer :all]
            [pandemic.domain.model.cure :refer :all]
            [pandemic.domain.model.infection_card :refer :all]
            [pandemic.domain.model.infection_rate_track :refer :all]
            [pandemic.domain.model.player :refer [initial-player]]
            [pandemic.domain.model.player_card :refer :all]
            [pandemic.domain.model.role :refer :all]
            [pandemic.domain.model.turn :refer :all]
            [clojure.spec :as s]))

(s/def ::game (s/keys :req [::cities
                            ::players
                            ::cures
                            ::infection-cards
                            ::player-cards
                            ::outbreak-indicator
                            ::infection_rate_track
                            ::turn]))

(defn initial-game
  "Creates a new game"
  []
  {:cities (initial-cities)
   :players []
   :cures (initial-cures)
   :infection-cards (initial-infection-cards)
   :player-cards (initial-player-cards)
   :outbreak-indicator 0
   :infection-rate-track (initial-infection-rate-track)
   :turn (initial-turn)})
