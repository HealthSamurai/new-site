(ns site.trainings
  (:require [site.styles :refer [s-var vh style undecorate &margin &padding &border &text &middle] :as s]
            [site.navigation :refer [navigation]]
            [site.widgets :refer [grid  splash paralax tags]]
            [site.data :refer [data find-by-id]]
            [site.formats :refer [load-text]]
            [garden.units :refer [px px* vh*]]
            [site.font :as font]))

(defn training-view [training]
  [:div.training.container
   [:div.row
    (style
     [:.training (&margin 1 nil) (&padding 1 nil 3 nil)
      [:.header (s/&unstyle-links)]
      [:.left   (&text :right)]
      [:.logo {:width (vh* 10)} (&text :right) (&margin 1 nil)]
      [:h1 (&text :center)]])

    [:div.col-md-2.left
     (when-let [img (:img training)] [:img.logo {:src img}])
     (tags (or (training :tags) []))]
    [:div.col-md-8.column
     [:a.header {:href (str "/trainings/" (:id training))}
      [:h2 (training :title)]]
     [:br]
     [:p (:desc training)]
     [:a {:href (str "/trainings/" (:id training))} "Узнать подробнее..."]]]])

(defn trainings [req]
  [:div
   (paralax
    28
    [:div
     (navigation {:color :inverse})
     (splash {:title (data :text :trainings)
              :moto  (data :text :trainings-subtitle)})]
    (interpose [:hr] (map training-view (data :trainings))))])

(defn training [{{id :id} :params :as req}]
  (let [training (find-by-id id :trainings)]
    [:div
     (navigation {})
     [:br]
     [:div.container
      [:h1 (:title training)]
      [:br]
      [:p (str "resources/trainings/" (:plan training))]
      [:p (load-text (str "trainings/" (:plan training)))]
      [:br][:br][:br]]]))
