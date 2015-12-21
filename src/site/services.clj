(ns site.services
  (:require [site.styles :refer [s-var vh style undecorate &margin &padding &border &text &middle] :as s]
            [site.navigation :refer [navigation]]
            [site.widgets :refer [grid  splash paralax tags] :as w]
            [site.data :refer [data find-by-id]]
            [site.formats :refer [load-text]]
            [garden.units :refer [px px* vh*]]
            [site.font :as font]))

(defn service-view [service]
  [:div.service.container
   (style
    [:.service (&margin 1 nil) (&padding 1 nil 3 nil)
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
    [:h1 (w/fa-icon (:icon service)) "&nbsp;" (service :title)]]
   [:br]
   [:p (:post service)]
   [:br]
   [:h4 (:steps-statement service)]
   [:br]
   (for [[num step] (map (fn [x y] [x y]) (range 1 10) (service :steps))]
     [:div.list-row
      [:div.list-item num]
      [:p.list-desc step]])])

(defn service [req])

(defn services [req]
  [:div
   (paralax
    28
    [:div
     (navigation {:color :inverse})
     (splash {:title (data :text :partnership)
              :moto  (data :text :partnership-subtitle)})]
    [:div
     (interpose [:hr] (map service-view (data :services)))
     (splash {:title (data :text :partnership-target)
              :moto  (data :text :partnership-subtitle)})])])

