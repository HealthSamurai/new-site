(ns site.trainings
  (:require [site.styles :refer [s-var vh style undecorate &margin &padding &border &text &middle] :as s]
            [site.navigation :refer [navigation]]
            [site.widgets :refer [grid  splash paralax tags]]
            [site.data :refer [data find-by-id i idata]]
            [site.routes :refer [url asset-path]]
            [site.formats :refer [load-text]]
            [garden.units :refer [px px* vh*]]
            [site.font :as font]))

(defn training-view [training]
  [:div.training
   [:div.row
    (style
     [:.training (&margin 3 nil) (&padding 1 nil 2 nil)
      [:.header (s/&unstyle-links)]
      [:.logo {:width (vh* 20)} (&text :right) (&margin 1 nil)]
      [:h1 (&text :center)]])

    [:div.col-md-3.left
     (when-let [img (:img training)] [:img.logo {:src (asset-path img)}])
     (tags (or (training :tags) []))]
    [:div.col-md-8.column
     #_[:a.header {:href (str "/trainings/" (:id training))}]
     [:h3 {:id (:id training)} (i training :title)]
     [:br]
     [:p (i training :desc)]
     [:br]
     [:a {:href (url "trainings" (:id training))} (idata :text :more-info)]]]])

(defn trainings [req]
  [:div
   (navigation {:color :inverse})
   (splash {:title (idata :text :trainings)
            :moto  (idata :text :trainings-subtitle)})
   [:div.container (interpose [:hr] (map training-view (data :trainings)))]])

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
