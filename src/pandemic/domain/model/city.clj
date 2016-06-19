(ns pandemic.domain.model.city)

(defn initial-city
  "Creates an empty new state"
  [name neighbors]
  {:name name
   :neighbors neighbors
   :disease-cubes-count 0
   :research-station? false
   :players []})

(defn put-research-station
  "Puts a research station in the given city"
  ([game city]
     (assoc-in game [:cities city :research-station?] true)))

(defn put-disease-cubes
  "Puts N disease cubes in the given city"
  [game city cubes-count]
  (loop [game game
         cubes-count cubes-count
         pending-cities #{city}
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
  {:san-francisco (initial-city "San Francisco" [:tokyo :manila :los-angeles :chicago])
   :chicago (initial-city "Chicago" [:san-francisco :los-angeles :mexico-city :atlanta :montreal])
   :montreal (initial-city "Montreal" [:chicago :washington :new-york])
   :new-york (initial-city "New York" [:montreal :washington :madrid :london])
   :atlanta (initial-city "Atlanta" [:chicago :miami :washington])
   :washington (initial-city "Washington" [:montreal :atlanta :miami :new-york])
   :los-angeles (initial-city "Los Angeles" [:san-francisco :sidney :mexico-city :chicago])
   :mexico-city (initial-city "Mexico City" [:los-angeles :lima :bogota :miami :chicago])
   :miami (initial-city "Miami" [:atlanta :mexico-city :bogota :washington])
   :bogota (initial-city "Bogota" [:mexico-city :lima :buenos-aires :sao-paulo :miami])
   :lima (initial-city "Lima" [:mexico-city :santiago :bogota])
   :santiago (initial-city "Santiago" [:lima])
   :buenos-aires (initial-city "Buenos Aires" [:bogota :sao-paulo])
   :sao-paulo (initial-city "Sao Paulo" [:bogota :buenos-aires :lagos :madrid])
   :london (initial-city "London" [:new-york :madrid :paris :essen])
   :essen (initial-city "Essen" [:london :paris :milan :st-petersburg])
   :st-petersburg (initial-city "St. Petersburg" [:essen :istanbul :moscow])
   :madrid (initial-city "Madrid" [:new-york :sao-paulo :algiers :paris :london])
   :paris (initial-city "Paris" [:london :madrid :algiers :milan :essen])
   :milan (initial-city "Milan" [:essen :paris :istanbul])
   :algiers (initial-city "Algiers" [:paris :madrid :cairo :istanbul])
   :istanbul (initial-city "Istanbul" [:milan :algiers :cairo :baghdad :moscow :st-petersburg])
   :moscow (initial-city "Moscow" [:st-petersburg :istanbul :tehran])
   :cairo (initial-city "Cairo" [:algiers :khartoum :riyadh :baghdad :istanbul])
   :baghdad (initial-city "Baghdad" [:istanbul :cairo :riyadh :karachi :tehran])
   :tehran (initial-city "Tehran" [:moscow :baghdad :karachi :delhi])
   :riyadh (initial-city "Riyadh" [:baghdad :cairo :karachi])
   :karachi (initial-city "Karachi" [:tehran :baghdad :riyadh :mumbai :delhi])
   :delhi (initial-city "Delhi" [:tehran :karachi :mumbai :chennai :kolkata])
   :mumbai (initial-city "Mumbai" [:karachi :chennai :delhi])
   :chennai (initial-city "Chennai" [:delhi :mumbai :jakarta :bangkok :kolkata])
   :kolkata (initial-city "Kolkata" [:delhi :chennai :bangkok :hong-kong])
   :lagos (initial-city "Lagos" [:sao-paulo :kinshasa :khartoum])
   :khartoum (initial-city "Khartoum" [:lagos :kinshasa :johannesburg])
   :kinshasa (initial-city "Kinshasa" [:lagos :johannesburg :khartoum])
   :johannesburg (initial-city "Johannesburg" [:kinshasa :khartoum])
   :beijing (initial-city "Beijing" [:shangai :seoul])
   :seoul (initial-city "Seoul" [:beijing :shangai :tokyo])
   :shangai (initial-city "Shangai" [:beijing :hong-kong :taipei :tokyo :seoul])
   :tokyo (initial-city "Tokyo" [:seoul :shangai :osaka :san-francisco])
   :bangkok (initial-city "Bangkok" [:kolkata :chennai :jakarta :ho-chi-minh-city :hong-kong])
   :hong-kong (initial-city "Hong Kong" [:shangai :kolkata :bangkok :ho-chi-minh-city :manila :taipei])
   :taipei (initial-city "Taipei" [:shangai :hong-kong :manila :osaka])
   :osaka (initial-city "Osaka" [:tokyo :taipei])
   :jakarta (initial-city "Jakarta" [:chennai :sydney :ho-chi-minh-city :bangkok])
   :ho-chi-minh-city (initial-city "Ho Chi Minh City" [:hong-kong :bangkok :jakarta :manila])
   :manila (initial-city "Manila" [:taipei :hong-kong :ho-chi-minh-city :sydney :san-francisco])
   :sydney (initial-city "Sydney" [:manila :jakarta :los-angeles])})
