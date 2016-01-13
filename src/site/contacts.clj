(ns site.contacts
  (:require [site.styles :refer [s-var vh style undecorate mbox pbox] :as s]
            [site.navigation :refer [navigation]]
            [site.widgets :refer [grid  splash paralax] :as w]
            [site.data :refer [data i idata]]
            [garden.units :refer [px px*]]
            [site.font :as font]))




(defn contacts [req]
  [:div#contacts-page
   (style [:#contacts-page
           [:#contacts (s/&padding 2 nil 8 nil)]
           [:.phone {:color "white"} (s/&unstyle-links)]])
   (navigation {:color :inverse})
   (w/call-to-action
    {:title (idata :text :contacts)
     :moto [:span (idata :text :contacts-subtitle)
            [:br]
            [:br]
            "email:"
            [:a {:href (str "mailto:" (data :contacts :mailto)) :style "color: white;"}
             " " (data :contacts :mailto)]
            (for [office (data :contacts :offices)]
              [:span
               [:br]
               "phone: " [:a.phone {:href (str "tel:" (i office :phone))} (i office :phone)]])]})

   [:div#contacts.container
    (apply grid
           (for [office (data :contacts :offices)]
             [[:h2 (i office :country) ", " (i office :city)]
              [:p  (i office :address)]
              [:iframe {:width (str (* s/vh 30) "px")
                        :height (str (* s/vh 15) "px")
                        :frameborder "0"
                        :style "border:0"
                        :src (:gmap office)
                        :allowfullscreen true}]]))]
   ])
