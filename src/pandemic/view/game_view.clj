(ns pandemic.view.game_view
  (:require [clojure.string :as str]
            [lanterna.screen :as screen]
            [pandemic.view.lanterna.extensions :as ext]))

(def screen (screen/get-screen :text))

(defn render-cities
  [cities]
  (screen/start screen)
  (screen/put-string screen 10 10 "PANDEMIC")
  (ext/rect screen [0 0] [10 10])
  (screen/redraw screen)
  (screen/get-key-blocking screen)
  (screen/stop screen))

  ; (println "CITIES:"))
  ; (loop [cities cities]
  ;   (let [city (second (first cities))
  ;         more-cities (drop 1 cities)]
  ;     (when-not (nil? city)
  ;       (println "\t"
  ;                (:name city)
  ;                (if (:research-station city) "⌂" "")
  ;                (:disease-cubes-count city)
  ;                (if (empty? (:players city)) "" (str "[" (str/join ", " (:players city)) "]")))
  ;       (recur more-cities)))))

(defn render
  "Renders the state of the game"
  [game]
  (render-cities (:cities game)))
