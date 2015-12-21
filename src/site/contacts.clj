(ns site.contacts
  (:require [site.styles :refer [s-var vh style undecorate mbox pbox] :as s]
            [site.navigation :refer [navigation]]
            [site.widgets :refer [grid  splash paralax]]
            [site.data :refer [data]]
            [garden.units :refer [px px*]]
            [site.font :as font]))

(defn contacts [req]
  [:div
   (style
    [:#contacts (s/&padding 2 nil 8 nil)])
   (paralax
    28
    [:div
     (navigation {:color :inverse})
     (splash {:title  (data :text :contacts)
              :moto [:span (data :text :contacts-subtitle)
                     [:br]
                     [:br]
                     "email:"
                     [:a {:href (str "mailto:" (data :contacts :mailto)) :style "color: white;"}
                      " " (data :contacts :mailto)]
                     (for [office (data :contacts :offices)]
                       [:span
                        [:br]
                        "phone: " (:phone office)])]})]

    [:div#contacts.container
     (apply grid
            (for [office (data :contacts :offices)]
              [[:h2 (:city office)]
               [:p (:address office)]
               [:iframe {:width (str (* s/vh 30) "px")
                         :height (str (* s/vh 15) "px")
                         :frameborder "0"
                         :style "border:0"
                         :src (:gmap office)
                         :allowfullscreen true}]
               [:br]
               [:p (:country office) ": " (:phone office)]
               ]))])])
