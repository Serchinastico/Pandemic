(ns pandemic.domain.model.city
    (:require [clojure.spec :as s]))

(s/def ::name keyword?)
(s/def ::color keyword?)
(s/def ::neighbors (s/coll-of ::name))
(s/def ::disease-cubes-count int?)
(s/def ::research-station? boolean?)
(s/def ::players (s/coll-of int?))
(s/def ::city (s/keys :req [::name ::color ::neighbors ::disease-cubes-count ::research-station? ::players]))

(defn initial-city
  "Creates an empty new state"
  [name color neighbors]
  {:name name
   :color color
   :neighbors neighbors
   :disease-cubes-count 0
   :research-station? false
   :players (set [])})

(defn put-research-station
  "Puts a research station in the given city"
  [game city-name]
  (assoc-in game [:cities city-name :research-station?] true))

(defn put-player
  "Puts a player in the given city"
  [game city-name player-index]
  (assoc-in game [:cities city-name :players] (conj (:players city-name) player-index)))

(defn remove-player
  "Moves out a player from the given city"
  [game city-name player-index]
  (assoc-in game [:cities city-name :players] (disj (:players city-name) player-index)))

(defn put-disease-cubes
  "Puts N disease cubes in the given city"
  [game city-name cubes-count]
  (loop [game game
         cubes-count cubes-count
         pending-cities #{city-name}
         processed-cities #{}]
    (if (nil? pending-cities)
      game
      (let [city-name (first pending-cities)
            old-city (get-in game [:cities city-name])
            new-disease-cubes-count (+ cubes-count (:disease-cubes-count old-city))
            new-game (assoc-in game
                               [:cities city-name :disease-cubes-count]
                               new-disease-cubes-count)]
        (if (> new-disease-cubes-count 3)
          (let [neighbors (get-in new-game [:cities city-name :neighbors])
                new-pending-cities (into (drop 1 pending-cities) neighbors)
                new-processed-cities (into processed-cities [city-name])]
            (recur new-game 1 new-pending-cities new-processed-cities))
          new-game)))))

(defn initial-cities
  "Create all the cities in the board game in their initial state"
  []
  {:san-francisco (initial-city "San Francisco" :blue [:tokyo :manila :los-angeles :chicago])
   :chicago (initial-city "Chicago" :blue [:san-francisco :los-angeles :mexico-city :atlanta :montreal])
   :montreal (initial-city "Montreal" :blue [:chicago :washington :new-york])
   :new-york (initial-city "New York" :blue [:montreal :washington :madrid :london])
   :atlanta (initial-city "Atlanta" :blue [:chicago :miami :washington])
   :washington (initial-city "Washington" :blue [:montreal :atlanta :miami :new-york])
   :los-angeles (initial-city "Los Angeles" :yellow [:san-francisco :sydney :mexico-city :chicago])
   :mexico-city (initial-city "Mexico City" :yellow [:los-angeles :lima :bogota :miami :chicago])
   :miami (initial-city "Miami" :yellow [:atlanta :mexico-city :bogota :washington])
   :bogota (initial-city "Bogota" :yellow [:mexico-city :lima :buenos-aires :sao-paulo :miami])
   :lima (initial-city "Lima" :yellow [:mexico-city :santiago :bogota])
   :santiago (initial-city "Santiago" :yellow [:lima])
   :buenos-aires (initial-city "Buenos Aires" :yellow [:bogota :sao-paulo])
   :sao-paulo (initial-city "Sao Paulo" :yellow [:bogota :buenos-aires :lagos :madrid])
   :london (initial-city "London" :blue [:new-york :madrid :paris :essen])
   :essen (initial-city "Essen" :blue [:london :paris :milan :st-petersburg])
   :st-petersburg (initial-city "St. Petersburg" :blue [:essen :istanbul :moscow])
   :madrid (initial-city "Madrid" :blue [:new-york :sao-paulo :algiers :paris :london])
   :paris (initial-city "Paris" :blue [:london :madrid :algiers :milan :essen])
   :milan (initial-city "Milan" :blue [:essen :paris :istanbul])
   :algiers (initial-city "Algiers" :green [:paris :madrid :cairo :istanbul])
   :istanbul (initial-city "Istanbul" :green [:milan :algiers :cairo :baghdad :moscow :st-petersburg])
   :moscow (initial-city "Moscow" :green [:st-petersburg :istanbul :tehran])
   :cairo (initial-city "Cairo" :green [:algiers :khartoum :riyadh :baghdad :istanbul])
   :baghdad (initial-city "Baghdad" :green [:istanbul :cairo :riyadh :karachi :tehran])
   :tehran (initial-city "Tehran" :green [:moscow :baghdad :karachi :delhi])
   :riyadh (initial-city "Riyadh" :green [:baghdad :cairo :karachi])
   :karachi (initial-city "Karachi" :green [:tehran :baghdad :riyadh :mumbai :delhi])
   :delhi (initial-city "Delhi" :green [:tehran :karachi :mumbai :chennai :kolkata])
   :mumbai (initial-city "Mumbai" :green [:karachi :chennai :delhi])
   :chennai (initial-city "Chennai" :green [:delhi :mumbai :jakarta :bangkok :kolkata])
   :kolkata (initial-city "Kolkata" :green [:delhi :chennai :bangkok :hong-kong])
   :lagos (initial-city "Lagos" :yellow [:sao-paulo :kinshasa :khartoum])
   :khartoum (initial-city "Khartoum" :yellow [:lagos :kinshasa :johannesburg])
   :kinshasa (initial-city "Kinshasa" :yellow [:lagos :johannesburg :khartoum])
   :johannesburg (initial-city "Johannesburg" :yellow [:kinshasa :khartoum])
   :beijing (initial-city "Beijing" :red [:shangai :seoul])
   :seoul (initial-city "Seoul" :red [:beijing :shangai :tokyo])
   :shangai (initial-city "Shangai" :red [:beijing :hong-kong :taipei :tokyo :seoul])
   :tokyo (initial-city "Tokyo" :red [:seoul :shangai :osaka :san-francisco])
   :bangkok (initial-city "Bangkok" :red [:kolkata :chennai :jakarta :ho-chi-minh-city :hong-kong])
   :hong-kong (initial-city "Hong Kong" :red [:shangai :kolkata :bangkok :ho-chi-minh-city :manila :taipei])
   :taipei (initial-city "Taipei" :red [:shangai :hong-kong :manila :osaka])
   :osaka (initial-city "Osaka" :red [:tokyo :taipei])
   :jakarta (initial-city "Jakarta" :red [:chennai :sydney :ho-chi-minh-city :bangkok])
   :ho-chi-minh-city (initial-city "Ho Chi Minh City" :red [:hong-kong :bangkok :jakarta :manila])
   :manila (initial-city "Manila" :red [:taipei :hong-kong :ho-chi-minh-city :sydney :san-francisco])
   :sydney (initial-city "Sydney" :red [:manila :jakarta :los-angeles])})
