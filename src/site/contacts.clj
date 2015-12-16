(ns site.contacts
  (:require [site.styles :refer [s-var vh style undecorate]]
            [site.navigation :refer [navigation]]
            [site.widgets :refer [grid  splash]]
            [site.data :refer [data]]
            [garden.units :refer [px px*]]
            [site.font :as font]))

(defn contacts [req]
  [:div
   (navigation {:color :inverse})
   (splash {:title  "Contacts" :moto "contacts"})])
