(ns site.widgets
  (:require [garden.units :refer [px px*]]
            [site.styles :refer [s-var style vh &pbox &center-block &center-text &mbox]]))

(defn grid [& columns]
  (let [cnt (/ 12 (count columns))]
    (into [:div.row]
          (for [c columns] (into [:div {:class (str "col-md-" cnt)}] c)))))


(defn splash [{ts :typescale pl :color :as opts}]
  (let [typescale (s-var :typescale (or ts :medium))
        palette   (s-var :color (or pl :inverse))]
    [:div
     (style
      [:#splash
       (palette :text)
       (&pbox 4 0 4 0)
       
       [:.splash-header
        (typescale :h1)
        (&center-text)
        (&center-block "20em")
        [:em (palette :em)]]

       [:.splash-sub-header
        (typescale :h4)
        (&pbox 0.5 nil nil nil)
        (&center-block "40em")
        (&center-text)]])

     [:div#splash
      [:div.container-fluid
       [:h1.splash-header    (:title opts)]
       [:p.splash-sub-header (:moto opts)]]]]))

(defn paralax [height layer-1 layer-2]
  [:div.paralax
   (style [:.paralax
           {:position "relative"}
           [:.paralaxed {:top 0 :position "fixed" :width "100%" :z-index -5}]
           [:.push (&mbox height nil nil nil)]
           [:.paralaxing {:background {:color "white"}}]])
   [:div.paralaxed layer-1]
   [:div.push]
   [:div.paralaxing layer-2]])
