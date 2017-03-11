(ns pandemic.domain.model.turn
  (:require [clojure.spec :as s]))

(s/def ::current-player-index keyword?)
(s/def ::actions-performed int?)
(s/def ::phase keyword?)
(s/def ::turn (s/keys :req [::current-player-index ::actions-performed ::phase]))

(def phases [:player-actions :card-drawing :cities-infection])

(defn take-action
  "Performs a user action"
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
