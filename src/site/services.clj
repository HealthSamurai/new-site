(ns site.services
  (:require [site.styles :refer [s-var vh style undecorate &margin &padding &border &text &middle] :as s]
            [site.navigation :refer [navigation]]
            [site.widgets :refer [grid  splash paralax tags] :as w]
            [site.data :refer [data find-by-id i idata]]
            [site.formats :refer [load-text]]
            [garden.units :refer [px px* vh*]]
            [site.font :as font]))

(defn service-view [service]
  [:div.service
   (style
    [:.service (&margin 3 nil) (&padding 1 nil 3 nil)
     [:.header (s/&unstyle-links) (&margin 0.5 nil)
      [:.fa {:color (s/s-var :color :main :selection :color)}]]
     [:.list-row {:position "relative"
                  :overflow "hidden"
                  :width "30em"}
      [:.list-item {:float "left"
                    :width (s/vh* 3)
                    :color (s/s-var :color :main :muted :color)
                    :line-height (s/vh* 3)
                    :font-size (s/vh* 3)}]
      [:.list-desc {:margin-left (s/vh* 3.1)
                    :padding-top (s/vh* 0.5)}]]])
   [:a.header {:id (str "#" (:id service))}
    [:h3 (i service :title)]]
   [:br]
   [:p (i service :post)]
   [:br]
   [:h4 (i service :steps-statement)]
   (for [[num step] (map (fn [x y] [x y]) (range 1 10) (i service :steps))]
     [:div.list-row
      [:div.list-item num]
      [:p.list-desc step]])])

(defn service [req])

(defn services [req]
  [:div
   (navigation {:color :inverse})
   (splash {:title (idata :text :partnership)
            :moto  (idata :text :partnership-subtitle)})
   [:div.container
    (interpose [:hr] (map service-view (data :services)))]
   (splash {:title (idata :text :partnership-target)
            :moto  (idata :text :partnership-subtitle)})])

