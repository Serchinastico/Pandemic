(ns pandemic.domain.model.player-card-test
  (:require [clojure.test :refer :all]
            [clojure.test.check :refer :all]
            [clojure.test.check.generators :as gen]
            [clojure.test.check.properties :as prop]
            [clojure.test.check.clojure-test :refer :all]
            [pandemic.domain.model.player_card :refer :all]))

(def tests-count 1000)

(defspec split-in-groups|groups-count-is-always-the-specified
  tests-count
  (prop/for-all [items (gen/not-empty (gen/list gen/int))
                 groups-count gen/s-pos-int]
                (= groups-count (count (split-in-groups items groups-count)))))

(defspec split-in-groups|all-items-are-present-in-groups
  tests-count
  (prop/for-all [items (gen/not-empty (gen/list gen/int))
                 groups-count gen/s-pos-int]
                (= (sort (flatten (split-in-groups items groups-count)))
                   (sort items))))

(defspec split-in-groups|groups-are-evenly-distributed
  tests-count
  (prop/for-all [items (gen/not-empty (gen/list gen/int))
                 groups-count gen/s-pos-int]
                (let [items-count (map count (split-in-groups items groups-count))
                      average-items-count (quot (count items) groups-count)]
                (every? #(or (= % average-items-count)
                             (= % (inc average-items-count)))
                        items-count))))

(defspec split-in-groups|groups-are-filled-left-to-right
  tests-count
  (prop/for-all [items (gen/not-empty (gen/list gen/int))
                 groups-count gen/s-pos-int]
                (let [items-count (map count (split-in-groups items groups-count))]
                  (= items-count (reverse (sort items-count))))))
