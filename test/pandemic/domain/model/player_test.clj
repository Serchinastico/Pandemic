(ns pandemic.domain.model.player-test
  (:require [clojure.test :refer :all]
            [clojure.test.check :refer :all]
            [clojure.test.check.generators :as gen]
            [clojure.test.check.properties :as prop]
            [clojure.test.check.clojure-test :refer :all]
            [pandemic.domain.model.player :refer :all]))

(def tests-count 1000)

; (def player (gen/hash-map
;              :name gen/string
;              :color (gen/elements [])
;              :role gen/symbol
;              :player-cards (gen/list-distinct (gen/elements '(1 2 3)))))
