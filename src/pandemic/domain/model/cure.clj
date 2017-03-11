(ns pandemic.domain.model.cure
  (:require [clojure.spec :as s]))

(s/def ::discovered? boolean?)
(s/def ::eradicated? boolean?)
(s/def ::cure (s/keys :req [::discovered? ::eradicated?]))

(defn initial-cure
  "Creates a new cure"
  []
  {:discovered? false
   :eradicated? false})

(defn initial-cures
  "Creates the initial cures"
  []
  {:yellow (initial-cure)}
  {:red (initial-cure)}
  {:blue (initial-cure)}
  {:black (initial-cure)})
