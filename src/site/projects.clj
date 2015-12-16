(ns site.projects
  (:require [site.navigation :refer [navigation]]
            [site.data :refer [data]]
            [garden.units :refer [px]]
            [site.styles :refer [style pbox s-var]]
            [site.widgets :refer [grid  splash]]))

(defn projects [req]
  [:div#projects
   (navigation {:color :inverse})

   (splash {:title (data :text :projects)
            :moto (data :text :projects-subtitle)})

   (style
    [:#projects
     [:.project
      {:border {:bottom {:color "#ddd" :style "solid" :width "1px"}}}
      (pbox 2 nil 4 nil)]
     [:.images {:width  (px 200)
                :height (px 200)}
      [:.image {:display "inline-block"
                :background-color "#eee"
                :cursor "pointer"
                :margin (px 2)
                :width (px 96)
                :height (px 96)}
       [:&:hover
        {:background-color (s-var :color :inverse :text :background-color)}]]]])

   (for [project (data :projects)]
     [:div.project
      [:div.container
       [:div.row
        [:div.col-md-9
         [:a {:href (str "/projects/" (:id project))}
          [:h3 (:title project)]]
         [:p (:desc project)]]
        [:div.col-md-3
         [:div.images
          [:div.image]
          [:div.image]
          [:div.image]
          [:div.image]]
         ]]]])])
