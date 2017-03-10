(ns pandemic.view.game_view
  (:require [clojure.string :as str]
            [lanterna.screen :as screen]
            [lanterna.terminal :as terminal]
            [pandemic.view.video_buffer :as buffer]
            [pandemic.view.lanterna.drawille :as drawille]
            [pandemic.view.lanterna.extensions :as ext]))

(def canvas-width 130)
(def canvas-height 40)
(def buffer-width 240)
(def buffer-height 60)

(def buffer (buffer/get-video-buffer canvas-width canvas-height buffer-width buffer-height))
(def drawille-canvas (drawille/->canvas
                      (* 2 buffer-width)
                      (* 4 buffer-height)))

(def city-coordinates
  {:san-francisco [18 20]
   :chicago [35 15]
   :montreal [55 15]
   :new-york [68 16]
   :atlanta [40 22]
   :washington [60 21]
   :los-angeles [20 25]
   :mexico-city [28 28]
   :miami [45 27]
   :bogota [0 0]
   :lima [0 0]
   :santiago [0 0]
   :buenos-aires [0 0]
   :sao-paulo [0 0]
   :london [0 0]
   :essen [0 0]
   :st-petersburg [0 0]
   :madrid [0 0]
   :paris [0 0]
   :milan [0 0]
   :algiers [0 0]
   :istanbul [0 0]
   :moscow [0 0]
   :cairo [0 0]
   :baghdad [0 0]
   :tehran [0 0]
   :riyadh [0 0]
   :karachi [0 0]
   :delhi [0 0]
   :mumbai [0 0]
   :chennai [0 0]
   :kolkata [0 0]
   :lagos [0 0]
   :khartoum [0 0]
   :kinshasa [0 0]
   :johannesburg [0 0]
   :beijing [0 0]
   :seoul [0 0]
   :shangai [0 0]
   :tokyo [0 0]
   :bangkok [0 0]
   :hong-kong [0 0]
   :taipei [0 0]
   :osaka [0 0]
   :jakarta [0 0]
   :ho-chi-minh-city [0 0]
   :manila [0 0]
   :sydney [0 0]})

(defn coordinate->drawille
  [[x y] [offset-x offset-y]]
  [(+ offset-x (* 2 x)) (+ offset-y (* 4 y))])

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
  (let [map (str/split (slurp "map.txt") #"\n")
        b1 buffer
        b2 (buffer/start-buffer b1)
        b3 (buffer/put-strings b2 map 2 3)
        canvas-updated (reduce
                        (fn [canvas route]
                          (-> canvas
                              (drawille/line (coordinate->drawille (:from route) [2 3]) (coordinate->drawille (:to route) [2 3]))))
                        drawille-canvas
                        (get-routes cities))
        b4 (buffer/put-strings b3 (str/split (drawille/canvas->str canvas-updated) #"\n") 0 0 :color :cyan)
        b5 (reduce (fn [buffer [key city]]
                     (let [color (:color city)
                           [x y] (get city-coordinates key)]
                       (buffer/put-strings buffer ["⣴⣿⣷⡄" "⠙⠿⠟⠁"] x y :color color)))
                   b4
                   cities)]
    (buffer/flush-buffer b5)

    (loop [video-buffer b5]
      (let [key (terminal/get-key-blocking (:terminal video-buffer))
            new-video-buffer (case key
                               :up (buffer/translate-canvas video-buffer 0 -5)
                               :left (buffer/translate-canvas video-buffer -10 0)
                               :right (buffer/translate-canvas video-buffer 10 0)
                               :down (buffer/translate-canvas video-buffer 0 5)
                               (System/exit 0))]
        (buffer/flush-buffer new-video-buffer)
        (recur new-video-buffer)))))

(defn render
  "Renders the state of the game"
  [game]
  (render-cities (:cities game)))
