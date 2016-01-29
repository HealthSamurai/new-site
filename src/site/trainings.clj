(ns site.trainings
  (:require [site.styles :refer [s-var vh style undecorate &margin &padding &border &text &middle] :as s]
            [site.navigation :refer [navigation]]
            [site.widgets :refer [grid  splash paralax tags] :as w]
            [site.data :refer [data find-by-id i idata md]]
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
      [:.logo {:width (vh* 20)} (&text :right) (&margin 1 nil)
       [:.svg {:margin-right (s/vh* 1) :margin-bottom (s/vh* 2)}
        [:path {:stroke "#333356" :opacity 0.6 :stroke-width 3 :fill "none"}]]]
      [:h1 (&text :center)]])

    [:div.col-md-3.left
     (when-let [img (:icon training)] [:div.logo [:div.svg (s/svg img)]])
     (tags (or (training :tags) []))]
    [:div.col-md-8.column
     #_[:a.header {:href (str "/trainings/" (:id training))}]
     [:h3 {:id (:id training)} (i training :title)]
     [:br]
     [:p (i training :desc)]
     [:br]
     (when (:plan training)
       [:a {:href (url "trainings" (:id training))} (idata :text :more-info)])]]])

(defn trainings [req]
  [:div
   (navigation {:color :inverse})
   (splash {:title (idata :text :trainings)
            :moto  (idata :text :trainings-subtitle)})
   [:div.container (interpose [:hr] (map training-view (data :trainings)))]
   (w/call-to-action
    {:title (idata :text :products-target)
     :moto (idata :text :products-subtarget)})])

(defn training [{{id :id} :params :as req}]
  (let [training (find-by-id id :trainings)]
    [:div#training
     (style
      [:#training
       [:.logo {:width (vh* 20)} (&text :right) (&margin 1 nil)
        [:.svg {:margin-right (s/vh* 1) :margin-bottom (s/vh* 2)}
         [:path {:stroke "#333356" :opacity 0.6 :stroke-width 3 :fill "none"}]]]])
     (navigation {})
     [:br]
     [:div.container
      [:div.row
       [:div.col-md-3.left
        (when-let [img (:icon training)] [:div.logo [:div.svg (s/svg img)]])
        (tags (or (training :tags) []))]
       [:div.col-md-8
        [:h1 (i training :title)]
        [:br]
        [:p (i training :desc)]
        [:hr]
        (when (:plan training) [:p (md (:plan training))])]]
      [:br]
      [:br]
      [:br]
      ]]))
