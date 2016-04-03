(ns pandemic.domain.model.infection_card)

(def infection-cards
  [:algiers :atlanta :baghdad :bangkok
   :beijing :bogota :buenos-aires :cairo
   :chennai :chicago :delhi :essen
   :ho-chi-minh-city :hong-kong :istanbul :jakarta
   :johannesburg :karachi :khartoum :kinshasa
   :kolkata :lagos :lima :london
   :los-angeles :madrid :manila :mexico-city
   :miami :milan :montreal :moscow
   :mumbai :new-york :osaka :paris :riyadh
   :san-francisco :santiago :sao-paulo :seoul
   :shangai :st-petersburg :sydney :taipei
   :tehran :tokyo :washington])


(defn initial-infection-cards
  "Creates the infection cards separated in the draw and discard piles"
  []
  {:draw-pile (shuffle infection-cards)
   :discard-pile []})
