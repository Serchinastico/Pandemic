(ns pandemic.view.game_view
  (:require [clojure.string :as str]
            [lanterna.screen :as screen]
            [lanterna.terminal :as terminal]
            [pandemic.view.lanterna.drawille :as drawille]
            [pandemic.view.lanterna.extensions :as ext]))

(def width 130)
(def height 40)
; (def screen (screen/get-screen :text {:cols 110 :rows 40 :font-size 40}))
(def terminal (terminal/get-terminal :text {:cols width :rows height}))
(def drawille-canvas (drawille/->canvas
                      (* 2 width)
                      (* 4 height)))

(def canvas (vec (repeat height (str/join (repeat width " ")))))

(defn put-line
    [canvas line y]
    (let [row (get canvas y)]
      (map #(if (= \space %2) %1 %2) row line)))

(def city-coordinates
  {:san-francisco [11 11]
   :chicago [22 10]
   :montreal [30 10]
   :new-york [36 11]
   :atlanta [25 13]
   :washington [33 13]
   :los-angeles [13 15]
   :mexico-city [19 17]
   :miami [28 16]
   :bogota [26 20]
   :lima [26 24]
   :santiago [29 29]
   :buenos-aires [36 28]
   :sao-paulo [40 26]
   :london [56 8]
   :essen [62 8]
   :st-petersburg [70 7]
   :madrid [56 12]
   :paris [59 10]
   :milan [62 11]
   :algiers [60 13]
   :istanbul [64 12]
   :moscow [68 11]
   :cairo [68 14]
   :baghdad [74 14]
   :tehran [80 12]
   :riyadh [76 16]
   :karachi [10 10]
   :delhi [10 10]
   :mumbai [10 10]
   :chennai [10 10]
   :kolkata [10 10]
   :lagos [10 10]
   :khartoum [10 10]
   :kinshasa [10 10]
   :johannesburg [10 10]
   :beijing [10 10]
   :seoul [10 10]
   :shangai [10 10]
   :tokyo [110 10]
   :bangkok [10 10]
   :hong-kong [10 10]
   :taipei [10 10]
   :osaka [110 13]
   :jakarta [10 10]
   :ho-chi-minh-city [10 10]
   :manila [10 10]
   :sydney [10 10]})

(defn coordinate->drawille
  [[x y]]
  [(* 2 x) (* 4 y)])

(defn city->coordinates
  [city-name]
  [(first (get city-coordinates city-name))
   (second (get city-coordinates city-name))])

(defn routes-from-city
  [[city-name city]]
  (let [from (city->coordinates city-name)]
    (map #(let [to (city->coordinates %)]
            {:from from :to to})
         (:neighbors city))))

(defn get-routes
  [cities]
  (flatten
   (map routes-from-city cities)))

(defn render-cities
  [cities]
  (terminal/start terminal)
  (ext/rect terminal [0 0] [130 35])
  (terminal/put-string terminal "⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢀⣀⡀⢀⣀⠀⢠⡤⠤⠖⢒⣒⡶⣾⣿⠧⠔⠒⠚⠋⠉⠉⠉⠉⠉⠙⢒⡶⠄⠀⠀⠀⠀⠀⣀⣤⣤⣤⠀⠀⠀⠀⠠⡤⠠⡶⠤⠀⠀⠀⠀⠀⠀⠀⢠⣤⣀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀" 2 3)
  (terminal/put-string terminal "⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣀⣀⣀⣺⢽⣾⠿⠙⢾⣏⣟⣺⣽⣦⠔⠉⠛⠿⠛⢲⡀⠀⠀⠀⠀⠀⠀⠀⠀⢀⡤⠃⠀⠀⠀⠀⠀⠀⠀⠑⠴⠉⠃⠀⠀⠀⠀⠀⠀⠀⠀⣠⡶⠶⠓⠀⣀⠀⡀⣠⠴⠚⠚⠉⠉⠉⢩⣦⣀⣀⣀⣀⣀⠀⢰⣶⣀⡀⣀⡀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀" 2 4)
  (terminal/put-string terminal "⠀⠀⠀⠀⠀⠀⠀⢀⡠⠔⠲⠴⠒⠒⠦⡤⠴⠤⠤⠤⠼⣟⠲⣤⣅⣩⣏⣤⡞⢯⢘⣟⡥⣄⠀⠙⢢⣀⠀⠀⢸⡄⠀⠀⠀⠀⠀⣀⡤⠤⠎⠀⠀⠀⠀⠀⠀⠀⠀⠀⢀⡤⠴⠒⠒⠲⠤⠤⢤⡀⣀⣜⣾⣄⡤⠤⢼⡅⡯⡉⠁⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠈⠙⠙⠛⠋⠉⠉⠉⠑⠒⠒⠤⠤⠴⢶⠶⠦⠤⢤⣀⣀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀" 2 5)
  (terminal/put-string terminal "⠀⠀⠀⢰⣒⠒⠒⠉⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠈⠉⠁⠀⠀⢉⡤⠤⢮⠿⣁⣤⣟⢁⣸⠗⠊⠁⠀⡏⠀⢀⡤⠤⠒⠋⠁⠰⠶⠶⠶⠀⠀⠀⠀⠀⠀⠀⣀⠞⠁⣀⢶⠆⠀⠀⢲⡶⠟⠛⠉⠀⠀⠀⢠⠊⠀⠓⠃⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢾⠛⠂⠀⠀⠀⠀⠀⠀⠀⠀⠀" 2 6)
  (terminal/put-string terminal "⠀⠀⣔⡒⠋⣠⠤⠤⠖⠒⣲⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢀⠔⠋⠀⠀⢀⡼⠁⠙⣦⣹⣇⠀⠀⠀⠀⠳⠤⠊⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢀⡀⠀⠰⣉⣡⡄⠀⡇⢹⡴⠶⠀⠀⠀⠀⠀⠀⠀⠀⠀⠘⣧⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢀⣀⣀⣀⣸⠗⢹⠄⡖⠒⠋⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀" 2 7)
  (terminal/put-string terminal "⣠⠔⠋⠉⠉⠀⠀⠀⠀⠐⣇⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠈⠓⠦⣄⢠⠴⠃⠀⠀⠀⠀⠸⡄⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢀⡤⡧⣷⠀⠀⢸⣹⣯⣴⣡⠿⠉⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣿⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢧⣀⡈⡁⠀⠀⠫⣄⢸⠂⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀" 2 8)
  (terminal/put-string terminal "⠀⠀⠀⠀⠀⠀⠀⠀⠀⠘⣻⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠰⠧⠋⠀⠀⠀⣠⣤⠤⡴⣏⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠺⠒⡿⣤⣿⠜⠉⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⡠⠶⠤⠟⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠘⢻⣦⡀⠀⠈⢿⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀" 2 9)
  (terminal/put-string terminal "⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣸⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢀⣤⣘⠷⠚⠒⠚⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠺⣍⠁⠀⠀⣀⣠⡀⠀⠀⠀⠀⠀⣠⡀⣀⠀⠀⠀⢻⡆⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢸⠙⡧⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀" 2 10)
  (terminal/put-string terminal "⠀⠀⠀⠀⠀⠀⠀⠀⠀⡴⠁⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣠⠔⠁⠈⠁⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢰⠛⠒⠋⣠⠔⠚⡍⠻⣞⣲⡄⢀⣀⣼⡡⠭⣍⣉⡟⠓⠚⠂⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢀⠀⠀⢀⡤⠼⠀⠰⡷⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀" 2 11)
  (terminal/put-string terminal "⠀⠀⠀⠀⠀⠀⠀⠀⠘⡄⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢀⡴⠚⠁⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢯⣤⣄⡴⣃⣠⡤⣟⠀⠼⠋⠢⣜⡾⣄⣀⣀⡀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠻⢷⠳⡌⢣⡀⠀⢠⠟⡆⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀" 2 12)
  (terminal/put-string terminal "⠀⠀⠀⠀⠀⠀⠀⠀⠀⢹⡀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢀⠴⠋⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢀⡖⠉⠉⠉⠁⠀⠀⠸⢄⣀⡀⢀⣈⡙⠋⠁⠀⡽⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠈⢢⡘⠖⢁⢴⡾⠞⠁⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀" 2 13)
  (terminal/put-string terminal "⠀⠀⠀⠀⠀⠀⠀⠀⠀⢸⣿⡆⠀⠀⠀⠀⢀⡤⠤⠤⠚⠓⠲⡇⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⡸⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠉⠋⠀⠈⠛⠉⠉⣧⠀⠀⠀⠀⠀⡖⢄⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⡇⠀⠈⠉⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀" 2 14)
  (terminal/put-string terminal "⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢻⡜⣄⠀⠀⢠⡏⠀⠀⠀⠀⠀⠘⠟⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⡤⠊⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠈⢣⡣⡀⠀⠀⠀⠈⠢⠽⢳⠦⣄⡤⢤⡀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⡰⠃⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀" 2 15)
  (terminal/put-string terminal "⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠈⠋⠘⡆⠀⠈⡇⠀⢀⢤⠀⠙⠛⠳⠦⣄⣀⡀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢸⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠱⣵⡀⠀⠀⠀⠀⠀⢀⡵⠀⠀⠀⠑⠲⡆⠀⠀⠀⠀⢠⠒⠖⣆⠀⠀⠀⠀⢠⣶⣴⠒⠋⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀" 2 16)
  (terminal/put-string terminal "⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠓⠦⢤⣉⡉⠁⢎⢤⡀⠀⠘⠃⠉⠉⠁⠛⠐⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠸⡄⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠈⢻⡄⠀⠀⢀⡴⠋⠀⠀⠀⠀⠀⠀⠳⡄⠀⢠⠔⠁⠀⠀⠻⡤⣄⠀⠀⠀⠻⡁⠀⠀⠀⢺⡗⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀" 2 17)
  (terminal/put-string terminal "⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠓⠦⢄⡠⡇⠀⠀⢀⠤⠦⡤⠤⢤⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⡇⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠳⠶⠾⠥⡄⠀⠀⠀⠀⠀⠀⠀⠀⢸⠀⢹⠀⠀⠀⠀⠀⠀⠸⣠⠦⡀⠀⣹⠀⠀⠀⣀⢿⣿⡄⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀" 2 18)
  (terminal/put-string terminal "⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠉⠛⠶⡶⠃⠀⠀⠀⠀⠀⠓⠤⢤⡀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠹⡀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⡠⠊⠀⠀⠀⠀⠀⠀⠀⠀⠀⠘⠒⡟⡆⠀⠀⠀⠀⠀⡀⢸⢆⠘⠋⠀⠀⠀⢐⡋⢨⣿⣳⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀" 2 19)
  (terminal/put-string terminal "⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⡇⠀⠀⠀⠀⠀⠀⠀⠀⠀⢹⡀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠙⠲⠤⠖⠚⠉⠉⠓⢢⡀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢀⠞⠁⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠈⠀⠀⠀⠀⠀⠀⠻⣬⣆⢣⠀⠀⢀⣠⠋⡟⠀⢈⡁⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀" 2 20)
  (terminal/put-string terminal "⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢀⠞⠁⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠙⠲⣄⣀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢳⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢠⠏⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠸⢤⠿⣄⣴⡉⠀⢰⢃⣾⡿⠃⢿⢠⣦⡀⣀⣀⠀⠀⠀⢀⠀⠀⠀⠀⠀⠀⠀" 2 21)
  (terminal/put-string terminal "⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢸⡀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠉⠉⠓⡄⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠸⡄⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢠⠃⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠈⢦⠏⠁⠉⠙⠊⠠⡿⡷⠀⠛⠁⠙⠟⢄⢸⠙⢢⣀⣽⡶⠀⠀⠀⠀⠀⠀" 2 22)
  (terminal/put-string terminal "⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠳⡄⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢀⡇⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠑⡄⠀⠀⠀⠀⠀⠀⠀⠀⠀⠘⡆⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠚⢚⡚⠉⢷⠍⠀⠀⠙⠖⠀⠀⠀" 2 23)
  (terminal/put-string terminal "⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠘⣄⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢠⠎⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢠⠃⠀⠀⠀⠀⠀⠀⠀⠀⠀⢀⠇⠀⢀⣼⠂⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢀⢤⣠⠎⠉⣱⠀⢸⢹⡀⠀⠀⠀⠀⠀⠀⠀⢠⡀" 2 24)
  (terminal/put-string terminal "⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠈⠉⢲⡀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢸⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⡏⠀⠀⠀⠀⠀⠀⠀⠀⠀⡰⠋⠀⡎⠉⣸⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣀⣠⠎⠀⠁⠀⠀⠈⠓⠚⠀⢇⠀⠀⠀⠀⠀⠀⠀⠈⠛" 2 25)
  (terminal/put-string terminal "⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⡇⠀⠀⠀⠀⠀⠀⠀⠀⢀⣀⠜⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠱⡀⠀⠀⠀⠀⠀⠀⠀⢸⠁⠀⠀⣇⠀⡇⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣠⠊⠁⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠘⣆⠀⠀⠀⠀⠀⠀⠈⠀" 2 26)
  (terminal/put-string terminal "⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣷⠀⠀⠀⠀⠀⠀⠀⢸⠉⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠹⡄⠀⠀⠀⠀⠀⢠⠞⠀⠀⠀⠸⠜⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⡎⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠈⡆⠀⠀⠀⠀⠀⠀⠀" 2 27)
  (terminal/put-string terminal "⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢹⠀⠀⠀⠀⠀⠀⢀⡇⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣇⠀⠀⠀⠀⢀⡞⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⡇⠀⢀⣀⡤⢤⣀⠀⠀⠀⠀⠀⠀⠀⢀⡴⠃⠀⠀⠀⠀⠀⠀⠀" 2 28)
  (terminal/put-string terminal "⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢸⠀⠀⠀⠀⠀⠶⡚⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠘⠦⠴⠶⠒⠉⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠧⠞⠉⠀⠀⠀⠈⠳⢤⠀⠀⠀⠀⡰⠋⠀⠀⠀⠀⠀⠀⢀⡶⡄" 2 29)
  (terminal/put-string terminal "⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢸⡀⠀⢀⡤⠎⠛⠁⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢙⣲⡖⠚⠁⠀⠀⠀⠀⠀⠀⢀⣴⡡⠟" 2 30)
  (terminal/put-string terminal "⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠈⡇⠀⢈⠆⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠸⠜⠁⠀⠀⠀⠀⠀⢀⣠⠖⠋⠁⠀⠀" 2 31)
  (terminal/put-string terminal "⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢳⠀⠈⣢⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠛⠁⠀⠀⠀⠀⠀" 2 32)
  (terminal/put-string terminal "⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠈⠳⢤⡘⢄⡀⠺⠃⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀" 2 33)

  (doseq [[key city] cities]
    (let [color (:color city)
          [x y] (get city-coordinates key)]
      (terminal/set-fg-color terminal color)
      (terminal/put-string terminal "●" x y)
      (terminal/set-fg-color terminal :cyan)))

  ; (let [routes (get-routes cities)
  ;       canvas-updated
  ;       (reduce
  ;        (fn [canvas route] (let [x 1]
  ;                             (drawille/line canvas (coordinate->drawille (:from route)) (coordinate->drawille (:to route)))))
  ;        drawille-canvas
  ;        routes)
  ;       lines (str/split (drawille/canvas->str canvas-updated) #"\n")]
  ;   (terminal/put-string terminal (drawille/canvas->str canvas-updated) 0 0))

  (loop []
    (let [key (terminal/get-key-blocking terminal)]
      (case key
        :up (terminal/move-cursor terminal 50 1)
        :left (terminal/move-cursor terminal 1 20)
        :right (terminal/move-cursor terminal 100 20)
        :down (terminal/move-cursor terminal 50 40)
        (System/exit 0))
      (recur)))

  (terminal/get-key-blocking terminal)
  (terminal/stop terminal))

(defn render
  "Renders the state of the game"
  [game]
  (render-cities (:cities game)))
