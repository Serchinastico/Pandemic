(ns pandemic.domain.model.cure)

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
