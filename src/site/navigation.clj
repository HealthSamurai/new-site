(ns site.navigation
  (:require [site.data :refer [data]]
            [site.styles :refer [s-var style vh undecorate]]
            [site.widgets :refer [grid]]
            [garden.units :refer [px px*]]))

(def MENU
  [{:href "/products" :title  (data :text :products)}
   {:href "/projects" :title  (data :text :projects)}
   {:href "/trainings" :title (data :text :trainings)}
   {:href "/contacts" :title  (data :text :contacts)}])


(defn navigation [opts]
  (let [props (merge {:color :inverse :items MENU} opts)
        palette (s-var :color (:color props))]
    [:div#navigation
     (style
      [:#navigation (:em palette)
       [:span.brand
        [:a (merge (s-var :color :inverse :em)
                {:text-align "center"
                 :width "80px"
                 :height "72px"
                 :display "inline-block"})
         [:i (merge (s-var :color :inverse :em)
                    {:padding-top "15px"
                     :text-align "center"
                     :text-decoration "none"
                     :font-size (px* 2 vh)
                     :line-height "1em"
                     :margin {:left "auto" :right "auto"}})]]]
       [:ul {:margin-top (px* vh) 
             :display "inline-block"
             :height (px* 2 vh)
             :margin-bottom 0
             :float "right"}
        [:li {:display "inline-block"
              :margin-left "20px"}
         [:a {:display "inline-block"
              :color (get-in palette [:em :color])
              :padding "9px 20px"
              :border "1px solid transparent"}
          (undecorate)
          [:&:hover {:color (get-in palette [:em :color])
                     :border-color (get-in palette [:em :color])}]]]]])
     [:div.container
      [:span.brand
       [:a {:href "/"} [:i.hs-icon.icon-samurai]]]
      [:ul.list-inline
       (for [x (:items props)]
         [:li [:a {:href (:href x)} (:title x)]])]]]))


(defn footer []
  (let [palette (s-var :color :inverse-ex)]
    [:div#footer
    (style
     [:div#footer
      (merge (:text palette)
             {:padding {:top    (px* 4 vh)
                        :bottom (px* 6 vh)}})
      [:a {:color "#ddd"}]])
    [:div.container
     (grid [[:h4 (data :text :services)]
            [:ul.list-unstyled
             (for [x (data :services)]
               [:li [:a {:href (:href x)} (:title x)]])]]

           [[:h4 (data :text  :products)]
            [:ul.list-unstyled
             (for [x (data :products)]
               [:li
                [:a {:href (get-in x [:links 0 :link])} (:title x)]])]]
           [[:h4 (data :text  :education)]
            [:ul.list-unstyled
             (for [x (data :education)]
               [:li [:a {:href (:href x)} (:title x)]])]]

           [[:h4 (data :text  :contacts)]
            [:ul.list-unstyled
             (for [x (data :contacts)]
               [:li
                [:a {:href (:href x)} (:title x)]])]])]]))
