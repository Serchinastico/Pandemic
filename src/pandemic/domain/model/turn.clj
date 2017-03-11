(ns pandemic.domain.model.turn
  (:require [clojure.spec :as s]))

(s/def ::current-player-index keyword?)
(s/def ::actions-performed int?)
(s/def ::phase keyword?)
(s/def ::turn (s/keys :req [::current-player-index ::actions-performed ::phase]))

(def phases [:player-actions :card-drawing :cities-infection])

(defn can-take-next-action
  "Returns whether the current player can take another action"
  [game]
  (< (get-in game [:turn :actions-performed]) 4))

(defn next-action
  "Updates the turn when a user has taken an action"
  [game]
  (update-in game [:turn :actions-performed] inc))

(defn next-phase
  "Finishes the current phase and starts the next one"
  [game]
  (let [current-phase-index (.indexOf phases (get-in game [:turn :phase]))]
    (nth phases (mod (inc current-phase-index) (count phases)))))

(defn initial-turn
  "Creates the initial turn state"
  []
  {:current-player-index nil
   :actions-performed 0
   :phase nil})
