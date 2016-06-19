(ns pandemic.view.lanterna.extensions
  (:require [clojure.string :as str]
            [lanterna.screen :as screen]
            [pandemic.view.lanterna.drawille :as drawille]))

; http://rosettacode.org/wiki/Bitmap/Bresenham%27s_line_algorithm#Clojure
(defn line
  "Draw a line from (x1, y1) to (x2, y2) using Bresenham's algorithm"
  [screen [x1 y1] [x2 y2]]
  (let [dist-x (Math/abs (- x1 x2))
        dist-y (Math/abs (- y1 y2))
        steep (> dist-y dist-x)]
    (let [[x1 y1 x2 y2] (if steep [y1 x1 y2 x2] [x1 y1 x2 y2])]
      (let [[x1 y1 x2 y2] (if (> x1 x2) [x2 y2 x1 y1] [x1 y1 x2 y2])]
        (let [delta-x (- x2 x1)
              delta-y (Math/abs (- y1 y2))
              y-step (if (< y1 y2) 1 -1)]
          (let [plot (if steep
                         #(screen/put-string screen (int %2) (int %1) "#")
                         #(screen/put-string screen (int %1) (int %2) "#"))]
            (loop [x x1
                   y y1
                   error (Math/floor (/ delta-x 2)) ]
              (plot x y)
              (if (< x x2)
                ; Rather then rebind error, test that it is less than delta-y rather than zero
                (if (< error delta-y)
                  (recur (inc x) (+ y y-step) (+ error (- delta-x delta-y)))
                  (recur (inc x) y            (- error delta-y)))))))))))

(defn rect
  [screen [x y] [width height]]
  (line screen [x y] [(+ x width) y])
  (line screen [(+ x width) y] [(+ x width) (+ y height)])
  (line screen [(+ x width) (+ y height)] [x (+ y height)])
  (line screen [x (+ y height)] [x y]))

(defn box
  [screen [x y] text])
