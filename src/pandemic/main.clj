(ns pandemic.main
  (:require [pandemic.domain.api.set_up_game :refer :all]
            [pandemic.view.game_view :refer :all])
  (:gen-class))

(defn -main
  [& args]
  (let [player-one (player-configuration "Sergio" :pink)
        player-two (player-configuration "Carlos" :black)
        player-three (player-configuration "Pablo" :blue)
        player-four (player-configuration "Dani" :white)
        configuration (configuration [player-one player-two player-three player-four] :normal)
        game (create-game configuration)]
    (render game)))
