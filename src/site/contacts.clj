(ns site.contacts
  (:require [site.styles :refer [s-var vh style undecorate mbox pbox]]
            [site.navigation :refer [navigation]]
            [site.widgets :refer [grid  splash]]
            [site.data :refer [data]]
            [garden.units :refer [px px*]]
            [site.font :as font]))

(defn contacts [req]
  [:div
   (style
    [:#contacts
     (mbox 4 nil 6 nil)])

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
                      "phone: " (:phone office)])]})

   [:div#contacts.container
    (apply grid
           (for [office (data :contacts :offices)]
             [[:h3 (:city office) " " [:small (:country office) ": " (:phone office)]]
              [:iframe {:width (px* 8 vh) :height (px* 8 vh) :frameborder "0" :style "border:0" :src (:gmap office) :allowfullscreen true}]
              [:p (:address office)]
              ]))]])
