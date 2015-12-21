(ns site.services
  (:require [site.styles :refer [s-var vh style undecorate &margin &padding &border &text &middle] :as s]
            [site.navigation :refer [navigation]]
            [site.widgets :refer [grid  splash paralax tags]]
            [site.data :refer [data find-by-id]]
            [site.formats :refer [load-text]]
            [garden.units :refer [px px* vh*]]
            [site.font :as font]))

(defn service-view [service]
  [:div.service.container
   (style
    [:.service (&margin 1 nil) (&padding 1 nil 2 nil)
     [:.header (s/&unstyle-links) (&margin 0.5 nil)]
     [:ol
      [(keyword "li::marker") {:width (s/vh* 2)
                               :color "red"}]]])
   [:a.header {:id (str "/services/#" (:id service))}
    [:h2 (service :title)]]
   [:p (:post service)]])

(defn service [req])

(defn services [req]
  [:div
   (paralax
    28
    [:div
     (navigation {:color :inverse})
     (splash {:title (data :text :partnership)
              :moto  (data :text :partnership-subtitle)})]
    (interpose [:hr] (map service-view (data :services))))])

