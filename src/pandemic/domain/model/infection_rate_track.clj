(ns pandemic.domain.model.infection_rate_track
  (:require [clojure.spec :as s]))

(s/def ::current-track-index int?)
(s/def ::infection-rate int?)
(s/def ::track (s/keys :req [::infection-rate]))
(s/def ::infection-rate_track (s/keys :req [::current-track-index ::track]))

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
