(ns pandemic.domain.model.infection_rate_track)

(defn initial-infection-rate-track
  "Creates the initial rate track"
  []
  {:current-track-index 0
   :track [{:infection-rate 2}
           {:infection-rate 2}
           {:infection-rate 2}
           {:infection-rate 3}
           {:infection-rate 3}
           {:infection-rate 4}
           {:infection-rate 4}]})
