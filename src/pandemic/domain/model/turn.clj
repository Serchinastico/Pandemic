(ns pandemic.domain.model.turn)

(defn initial-turn
  "Creates the initial turn state"
  []
  {:current-player nil
   :phase nil})
