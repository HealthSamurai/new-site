(ns site.widgets
  (:require [garden.units :refer [px px*]]
            [site.styles :refer [s-var style vh &pbox &center-block &center-text &mbox] :as s]))

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
       (&pbox 8 0 8 0)

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
           [:.push (s/&margin height nil nil nil)]
           [:.paralaxing {:background {:color "white"}}]])
   [:div.paralaxed layer-1]
   [:div.push]
   [:div.paralaxing layer-2]])

(defn tags [tags]
  [:div.tags
   (style
    [:.tag
     (merge
      {:font {:weight "300" :size "75%"}
       :border-radius ".25em"
       :display "inline-block"
       :line-height (s/vh* 1)
       :padding (s/vh* 0.5)
       :margin-bottom "4px"
       :margin-right "4px"
       :text-align "center"
       :vertical-align "baseline"
       :white-space "nowrap"})
     (s/&text :right)
     [:& (s-var :typescale :medium :small)]
     [:& (s-var :color :main :muted-selection)]
     (s/&border)])
   (interpose [:br] (for [label tags] [:div.tag label]))])
