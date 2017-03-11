(ns pandemic.domain.model.role
  (:require [clojure.spec :as s]))

(s/def ::roles (s/coll-of keyword?))

(def roles [:contingency-planner
            :dispatcher
            :medic
            :operations-expert
            :quarantine-specialist
            :researcher
            :scientist])
