(ns site.widgets
  (:require [garden.units :refer [px px*]]
            [site.styles :refer [s-var style vh]]))

(defn grid [& columns]
  (let [cnt (/ 12 (count columns))]
    (into [:div.row]
          (for [c columns] (into [:div {:class (str "col-md-" cnt)}] c)))))

(defn splash [opts]
  (let [defaults {:color :inverse :typescale :medium}
        props (merge defaults opts)
        typescale (s-var :typescale (:typescale props))
        palette (s-var :color (:color props))]
    [:div
     (style
      [:#splash
       (merge (:text palette)
              {:padding-top (px* 6 vh)
               :padding-bottom (px* 5 vh)})
       [:.splash-header     (merge (:h1 typescale)
                                   {:text-align "center"
                                    :margin "0 auto"
                                    :max-width "20em"})
        [:em (merge {:font-style "normal"} (:em palette))]]
       [:.splash-sub-header (merge (:h4 typescale)
                                   {:padding-top (px* 0.5  vh)
                                    :margin-left "auto"
                                    :max-width "40em"
                                    :text-align "center"
                                    :margin-right "auto"})]])
     [:div#splash
      [:div.container-fluid
       [:h1.splash-header    (:title opts)]
       [:p.splash-sub-header (:moto opts)]]]]))
