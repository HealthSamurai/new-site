(ns site.trainings
  (:require [site.styles :refer [s-var vh style undecorate mbox pbox]]
            [site.navigation :refer [navigation]]
            [site.widgets :refer [grid  splash]]
            [site.data :refer [data]]
            [garden.units :refer [px px*]]
            [site.font :as font]))

(defn training [training]
  (let [typescale (s-var :typescale :medium)
        palette (s-var :color :main)]
    [:div.training.row
     (style
      [:.training 
       [:.header (undecorate)]
       [:.hs-icon {:font-size (px* vh 8)
                   :text {:align "right"}
                   :position "relative"
                   :right "-61px"
                   :color "gray"
                   :display "block"}
        (mbox 4 nil 1 nil)]

       [:.tag (merge (:small typescale)
                     {:text {:align "right"}
                      :color "gray"
                      :font {:weight "bold"}})]
       [:.left
        {:positoin "relative" :overflow "hidden"}
        (pbox 2 nil 2 nil)]
       [:.column {:border {:left {:color "#ddd" :style "solid" :width "1px"}}
                  :padding {:left (px 40)}}
        (pbox 2 nil 2 nil)]

       [:.fa (merge  (:h2 typescale)
              {:font-size (px* 2 vh)
               :padding-left (px* 1 vh)
               :vertical-align "middle"})]
       [:h1 {:text-align "center"}]])

     [:div.col-md-3.left
      (font/icon (:id training))
      (for [label (training :labels)]
        [:div.tag label])]

     [:div.col-md-8.column
      [:a.header {:id (:id training) :href (str "#" (:id training))}
       [:h2 (str (training :title) " ")
        (when (:open-source training) [:small "Open Source"])]]
      [:p (:slogan training)]
      [:br]
      [:p (:description training)]
      ]]))

(defn trainings [req]
  [:div
   (navigation {:color :inverse})
   (splash {:title  "Trainins" :moto "looking for"})

   (for [tr (data :trainings)]
     [:pre (pr-str tr)])])
