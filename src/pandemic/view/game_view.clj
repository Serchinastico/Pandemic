(ns pandemic.view.game_view
  (:require [clojure.string :as str]
            [lanterna.screen :as screen]
            [lanterna.terminal :as terminal]
            [pandemic.view.lanterna.extensions :as ext]))

; (def screen (screen/get-screen :text {:cols 110 :rows 40 :font-size 40}))
(def terminal (terminal/get-terminal :text {:cols 120 :rows 40 :font-size 40}))

(defn render-cities
  [cities]
  ; (let [canvas (drawille/->canvas 100 100)
  ;       canvas-2 (drawille/line canvas [0 0] [99 99])
  ;       canvas-str (drawille/canvas->str canvas-2)]
  ;   ()

    ; (println canvas-str)])
  (terminal/start terminal)
  (ext/rect terminal [0 0] [124 34])
  (terminal/put-string terminal "" 2 1)
  (terminal/put-string terminal "" 2 2)
  (terminal/put-string terminal "⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣀⠀⠀⢠⣤⡤⠄⢀⣀⣀⣀⣀⣀⣤⣤⣤⣤⣀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀" 2 3)
  (terminal/put-string terminal "⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣀⡀⠀⠀⠀⠀⠀⢀⠠⠤⠴⠖⠋⠁⠐⠒⠛⢿⣿⣿⣿⣿⣿⣿⣿⡿⠁⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣀⣤⣤⣤⣤⣤⡄⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀" 2 4)
  (terminal/put-string terminal "⠀⠀⠀⠀⠀⠀⠀⠀⠀⣠⣤⣤⣤⣤⣤⣤⣤⣤⣤⣤⣤⣍⣀⠻⢛⣊⠀⣌⣦⢀⡺⠊⠙⢷⣦⡀⠀⠀⠀⣸⣿⣿⣿⣿⠿⠿⠛⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢀⣤⣤⣤⣤⣄⣀⠀⠀⣀⣀⣀⣀⣈⣧⠐⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣶⣶⣾⣿⣿⣷⣤⣤⣤⣤⣤⣤⣤⣤⣄⣀⣀⠀⠀⠀⠀⠀⠀⠀⠀⠀" 2 5)
  (terminal/put-string terminal "⠀⠀⠀⠀⠀⠀⣠⣭⣿⣿⣿⣿⣿⣿⣾⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⠿⠛⠋⠂⢀⣒⠒⠟⠏⠁⠀⠀⠸⣿⡿⠋⠉⠀⠀⠀⠐⠒⠚⠀⠀⠀⠀⠀⢀⣤⢺⡾⠋⣼⣿⣽⣤⣴⣾⣿⣿⣿⣿⣿⣿⣿⣾⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⢿⣿⣿⣭⠉⠁⠀⠀⠀⠀⠀⠀⠀" 2 6)
  (terminal/put-string terminal "⠀⠀⠀⠀⢀⠺⠛⠋⠉⠉⠉⠉⠺⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣯⣅⠀⠀⠀⣰⣿⣿⣧⣶⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣄⠀⠀⠙⡛⢺⠟⠀⡘⣏⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⠉⠉⠉⠁⠀⢰⣯⡁⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀" 2 7)
  (terminal/put-string terminal "⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠸⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣀⣼⣿⣿⣿⣿⣿⠶⠂⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠰⠎⣸⡤⢀⣤⣷⣶⣶⣶⣯⣿⣿⣽⢿⣿⣿⣿⡿⠿⠿⠿⢿⣿⣾⣿⣽⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣶⣦⡠⡀⠀⠀⠙⠇⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀" 2 8)
  (terminal/put-string terminal "⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣩⣾⣿⣿⣿⣿⣿⣿⣿⣿⣿⣛⡉⠿⣿⣿⣿⣿⣭⡆⠀⠀⠒⠂⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠲⣾⣿⣟⣿⣿⣯⣾⣿⣿⣻⠿⡿⢟⣿⣿⣿⠿⢿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣽⣿⣷⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⠈⠄⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀" 2 9)
  (terminal/put-string terminal "⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢠⣾⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣡⣿⣰⣯⣿⠟⠁⠈⠁⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢐⣶⣶⡟⠛⠛⡍⠳⣝⢫⡯⣿⡃⣀⣀⣈⣉⡿⣿⡀⠻⢏⣻⣿⣿⣿⣟⣿⣿⣯⣿⣿⣿⣿⣯⣿⣿⣿⣿⣿⣿⣿⣿⡿⣿⣿⡛⠋⠀⠈⠒⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀" 2 10)
  (terminal/put-string terminal "⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢸⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⡿⠛⠁⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠘⠿⠟⢁⣁⣀⣁⢀⠈⠀⠙⠀⠹⠿⠿⢿⣿⣿⣾⣷⣄⣸⣽⣿⢿⣽⣰⣿⣟⡿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣦⡌⠘⢢⡄⠀⢀⣼⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀" 2 11)
  (terminal/put-string terminal "⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠈⢻⠿⣟⣿⢿⣿⣿⣿⣿⣿⣿⣿⡿⠋⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢀⣼⣿⣿⣿⣿⣿⢟⣤⣄⡀⣠⣤⣀⣀⣀⣴⢿⣿⣿⣟⣿⣿⣿⣿⣿⣿⣿⣿⣿⢿⣿⢿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣧⠀⠀⠀⠈⠂⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀" 2 12)
  (terminal/put-string terminal "⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠘⣆⢿⣿⣿⣿⡿⠃⠀⠀⠀⠀⠇⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⡰⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣇⣿⣿⣯⡉⢿⣿⣿⣾⣄⡙⠿⢿⣿⣿⣿⣿⣿⣿⣿⣾⣿⣻⣽⣿⣿⡽⣿⣿⣿⣿⣿⣿⣿⠇⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀" 2 13)
  (terminal/put-string terminal "⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠈⠈⣿⣿⣿⡀⠀⠀⡀⠀⠐⠢⣀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣾⣧⣿⣿⣿⣿⣿⣿⣿⣻⣿⣿⣿⡿⣿⣿⣿⣿⣧⡈⢿⣿⣿⣿⣧⣾⡷⠀⠀⠀⠙⠟⣿⣿⣿⣿⣿⠟⠫⣿⣿⡿⣿⣿⠻⠿⠟⠋⠀⠁⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀" 2 14)
  (terminal/put-string terminal "⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠈⠙⠻⠷⠶⣾⣃⡀⠀⠀⠂⠈⠉⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣿⣿⣿⣿⣿⣿⣿⣷⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣷⡌⢻⣟⣯⡿⠞⠁⠀⠀⠀⠀⠀⠸⣿⣿⠟⠁⠀⠀⠀⠿⢿⣿⣿⣄⠃⠀⠀⠀⣰⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀" 2 15)
  (terminal/put-string terminal "⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠉⠻⣿⠀⠀⢀⣀⣀⣀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢟⣿⣿⣿⣿⣿⣿⣿⣿⣿⡿⣻⣿⣿⣿⣿⣿⣿⣯⣿⣿⢌⣋⣁⡀⠀⠀⠀⠀⠀⠀⠀⠀⢻⣿⠀⠀⠀⠀⠀⠀⢰⠙⠿⣿⠇⠀⠀⠀⡈⠀⠄⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀" 2 16)
  (terminal/put-string terminal "⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠈⠘⠱⣾⣿⣻⢿⣿⣗⣄⣀⣀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠙⢿⣿⣿⡿⠿⠿⣿⣿⣽⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣷⣟⡟⠁⠀⠀⠀⠀⠀⠀⠀⠀⠈⠀⠀⠀⠀⠀⠀⠀⠈⢢⠀⠁⠀⠀⠀⣈⠀⠐⠖⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀" 2 17)
  (terminal/put-string terminal "⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣰⣿⣿⣿⣼⣿⣿⣷⣿⣿⣆⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠁⠀⠀⠀⠀⠀⢸⣿⣯⣟⣿⣿⣿⣿⣿⢿⣷⣶⡾⠋⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠑⣌⠳⠀⠀⣤⣺⣾⢀⢀⡀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀" 2 18)
  (terminal/put-string terminal "⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢸⣟⣿⣯⣿⣿⣿⣿⣿⣿⣿⣿⣶⣦⣤⣄⡀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠘⢿⣿⣿⣿⣿⣿⣿⣶⣾⣿⠏⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠈⢷⡄⠀⠛⠛⠋⠜⠆⠀⠠⠀⠸⢠⣦⣄⡀⠀⠀⠄⠀⠀⠀⠀" 2 19)
  (terminal/put-string terminal "⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠈⢿⣿⡿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⠆⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠈⣿⣿⣿⣿⣿⣿⣽⣿⣿⡄⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠐⠂⠒⠤⠀⠠⠀⡀⠀⠀⠀⠀⠸⠿⠛⢇⠈⠀⠀⠀⠀⠀" 2 20)
  (terminal/put-string terminal "⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢻⣿⣿⣿⣮⣻⣿⣿⣿⣿⣿⣿⣿⣿⠁⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢀⣿⣿⣿⣷⣯⣿⣿⣿⣿⡆⠀⠀⣠⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⡀⠀⣤⡤⠀⠀⣤⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀" 2 21)
  (terminal/put-string terminal "⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠉⠻⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢻⣿⣿⣿⣿⣿⣷⡿⡿⠋⠁⠀⣾⡏⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢀⣴⣿⣿⣿⣿⣦⣴⣿⡀⠀⠀⠀⠀⠀⠀⠀⠀⠀" 2 22)
  (terminal/put-string terminal "⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣿⣭⣵⣿⢿⣿⣿⣿⠟⠋⠁⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠈⣿⣿⣿⣿⣿⣿⣿⡇⠀⠀⠰⣿⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢠⣴⣾⣿⣿⣿⣿⣿⣿⣿⣿⣿⣷⣄⠀⠀⠀⠀⠀⠀⠀⠀" 2 23)
  (terminal/put-string terminal "⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣧⣿⣿⣿⣿⣿⣿⡿⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠘⣿⣿⣿⣿⣿⠅⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢸⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⠀⠀⠀⠀⠀⠀⠀⠀" 2 24)
  (terminal/put-string terminal "⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣿⣿⣿⣿⣿⠷⡟⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠹⠿⠿⠟⠁⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣼⠿⠿⠟⠛⠛⠻⢿⣿⣿⣿⣿⠟⠁⠀⠀⠀⠀⠀⠀⠀⠀" 2 25)
  (terminal/put-string terminal "⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢿⢿⣿⣿⡿⠗⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠛⠛⠛⠁⠀⠀⠀⠀⠀⠀⠀⡠⠀⠀" 2 26)
  (terminal/put-string terminal "⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠈⣿⣿⡇⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢀⡠⠄⠈⠀⠀⠀" 2 27)
  (terminal/put-string terminal "⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠙⢿⡇⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠈⠉⠀⠀⠀⠀⠀⠀" 2 28)
  (terminal/put-string terminal "⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠈⠡⠄⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀" 2 29)
  (terminal/set-fg-color terminal :blue)
  (ext/line terminal [20 9] [53 10] "·")
  (terminal/set-fg-color terminal :red)
  (terminal/put-string terminal "●" 53 10)
  (terminal/set-fg-color terminal :yellow)
  (terminal/put-string terminal "●" 20 9)
  (terminal/set-fg-color terminal :green)
  (terminal/put-string terminal "●" 28 11)

  (terminal/get-key-blocking terminal)
  (terminal/stop terminal))
  ; (screen/start screen)
  ; (screen/put-string screen 10 10 "PANDEMIC")
  ; (ext/rect screen [0 0] [100 39])
  ; (ext/rect screen [1 1] [77 37])
  ; (screen/redraw screen)
  ; (screen/get-key-blocking screen)
  ; (screen/stop screen))

  ; (println "CITIES:")
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
