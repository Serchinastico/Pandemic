(ns pandemic.view.video_buffer
  (:require [clojure.string :as str]
            [lanterna.screen :as screen]
            [lanterna.terminal :as terminal]
            [pandemic.view.lanterna.drawille :as drawille]
            [pandemic.view.lanterna.extensions :as ext]))

(defn get-video-buffer
  [canvas-width canvas-height buffer-width buffer-height]
  "Returns a newly created video buffer"
  {:terminal (terminal/get-terminal :text {:cols buffer-width :rows buffer-height})
   :drawille (drawille/->canvas (* 2 buffer-width) (* 4 buffer-height))
   :buffers {:white (vec (repeat buffer-height (str/join (repeat buffer-width " "))))
             :red (vec (repeat buffer-height (str/join (repeat buffer-width " "))))
             :blue (vec (repeat buffer-height (str/join (repeat buffer-width " "))))
             :cyan (vec (repeat buffer-height (str/join (repeat buffer-width " "))))
             :green (vec (repeat buffer-height (str/join (repeat buffer-width " "))))
             :yellow (vec (repeat buffer-height (str/join (repeat buffer-width " "))))
             :default (vec (repeat buffer-height (str/join (repeat buffer-width " "))))}
   :canvas-x 0
   :canvas-y 0
   :canvas-width canvas-width
   :canvas-height canvas-height
   :buffer-width buffer-width
   :buffer-height buffer-height})

(defn start-buffer
  [video-buffer]
  (do (terminal/start (:terminal video-buffer))
      video-buffer))

(defn- put-char-in-string
  [string char i]
  (if (or (= char \space) (= char \⠀))
    string
    (str/join (assoc (vec string) i char))))

(defn put-char
  [video-buffer char x y & {:keys [color]
                            :or {color :white}}]
  (assoc-in
   video-buffer
   [:buffers color y]
   (put-char-in-string (get (color (:buffers video-buffer)) y) char x)))

(defn put-string
  [video-buffer string x y & {:keys [color]
                              :or {color :white}}]
  "Puts the given string in the buffer at position (x, y)
  Whitespaces are treated as non-changing characters"
  (let [row (get (color (:buffers video-buffer)) y)
        min-col x
        max-col (dec (min (count row) (+ (count string) min-col)))]
    (assoc-in
     video-buffer
     [:buffers color y]
     (str/join
      (map-indexed
       (fn [index item]
         (cond
           (< index min-col) item
           (<= min-col index max-col) (let [char (get string (- index min-col))]
                                        (if (or (= char \space) (= char \⠀)) item char))
           :else item))
       row)))))

(defn put-strings
  [video-buffer strings x y & {:keys [color]
                               :or {color :white}}]
  "Put a list of strings in the given coordinates as if they occupy a rectangle"
  (loop [video-buffer video-buffer
         strings strings
         row y]
    (if
     (or (= row (:buffer-height video-buffer))
         (empty? strings))
      video-buffer
      (recur (put-string video-buffer (first strings) x row :color color)
             (vec (drop 1 strings))
             (inc row)))))

(defn move-canvas
  [video-buffer x y]
  (-> video-buffer
      (assoc :canvas-x x)
      (assoc :canvas-y y)))

(defn translate-canvas
  [video-buffer dx dy]
  (-> video-buffer
      (assoc :canvas-x
             (min
              (max 0
                   (+ dx (:canvas-x video-buffer)))
              (- (:buffer-width video-buffer) (:canvas-width video-buffer))))
      (assoc :canvas-y
             (min
              (max 0
                   (+ dy (:canvas-y video-buffer)))
              (- (:buffer-height video-buffer) (:canvas-height video-buffer))))))

(defn- flush-color-buffer
  [video-buffer color]
  (let [min-row (:canvas-y video-buffer)
        max-row (+ min-row (:canvas-height video-buffer))
        indexed-rows (->>
                      (map-indexed vector (color (:buffers video-buffer)))
                      (filter (fn [[index _]] (<= min-row index max-row))))]
    (terminal/set-fg-color (:terminal video-buffer) color)
    (doseq [indexed-row indexed-rows]
      (let [min-col (:canvas-x video-buffer)
            max-col (+ min-col (:canvas-width video-buffer))
            row (first indexed-row)
            string (subs (second indexed-row) min-col max-col)]
        (doseq [[x char] (map-indexed vector string)]
          (when (not= char \space)
            (terminal/put-string (:terminal video-buffer) (str char) x (- row min-row))))))))

(defn flush-buffer
  [video-buffer]
  "Prints the contents of the buffer in the terminal"
  (doseq [row (range (:buffer-height video-buffer))]
    (terminal/put-string (:terminal video-buffer) (str/join (repeat (:buffer-width video-buffer) " ")) 0 row))
  (doseq [color [:white :cyan :red :blue :green :yellow :default]]
    (flush-color-buffer video-buffer color)))
