(ns site.widgets
  (:require [garden.units :refer [px px*]]
            [site.styles :refer [s-var style vh &pbox &center-block &center-text] :as s]))

(defn grid [& columns]
  (let [cnt (/ 12 (or (count columns) 4))]
    (into [:div.row]
          (for [c columns] (into [:div {:class (str "col-md-" cnt)}] c)))))


(defn splash [opts]
  [:div#splash
   (style
    [:#splash
     (s/s-var :color :inverse :text)
     (s/&padding 5 0 4 0)

     [:.splash-header
      (s/&text :center :h1 300)
      (s/&center-block "20em")]

     [:.splash-sub-header
      (s/&text :center :h4)
      (s/&padding 1 nil)
      (s/&center-block "40em")]])

   [:div.container-fluid
    [:h1.splash-header    (:title opts)]
    [:p.splash-sub-header (:moto opts)]]])

(defn paralax [height layer-1 layer-2]
  [:div.paralax
   (style [:.paralax
           {:position "relative"}
           [:.paralaxed {:top 0 :position "fixed" :width "100%" :z-index -5}]
           [:.push (s/&margin height nil nil nil)]
           [:.paralaxing {:background {:color "white"}}]])
   [:div.paralaxed layer-1]
   [:div.push]
   [:div.paralaxing layer-2]])

(defn tags [tags & [opts]]
  [:div.tags
   (style
    [:.tag
     {:border-radius ".25em"
      :color "#888"
      :background-color "#fafafa"}
     (s/&font-scale 0.75 0.75)
     (s/&text :center :nowrap 300)
     (s/&margin 0 0.25 0.25 0)
     (s/&padding 0.5)
     (s/&inline)
     (s/&border)])
   (->> tags
        (map (fn [t] [:div.tag t]))
        (interpose (if (and opts (:inline opts)) "" [:br])))])

(defn fa-icon [x] [:i.fa {:class (str "fa-" (name (or x "ups")))}])
