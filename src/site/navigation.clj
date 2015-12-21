(ns site.navigation
  (:require [site.data :refer [data i idata]]
            [site.styles :refer [s-var style vh undecorate pbox mbox] :as s]
            [site.widgets :refer [grid]]
            [site.font :refer [icon]]
            [garden.units :refer [px px*]]))


(defn navigation [opts]
  (let [props (merge {:color :inverse} opts)
        palette (s-var :color (:color props))]
    [:div#navigation
     (style
      [:#navigation
       (:em palette)
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
        [:li {:display "inline-block"}
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
       (for [x (data :menu)]
         [:li [:a {:href (:href x)} (i x :title)]])]]]))


(defn footer []
  (let [palette (s-var :color :inverse-ex)]
    [:div#footer
    (style
     [:div#footer
      (:text palette)
      (s/&padding 4 nil 0 nil)
      [:a {:color "#ddd"}]
      [:.footer-line {:background "rgba(46,48,58,1)"}
       (s/&text :center)
       (s/&margin 2 0 0 0)
       (s/&padding 1 0)]])
    [:div.container
     (grid
      [[:a {:href "/products"} [:h4 (idata :text  :products)]]
       [:ul.list-unstyled
        (for [x (data :products)]
          [:li [:a {:href (str "/products#" (:id x))} (i x :title)]])]]

      [[:a {:href "/services"} [:h4 (idata :text  :development)]]
       [:ul.list-unstyled
        (for [x (data :services)]
          [:li [:a {:href (:href x)} (i x :title)]])]]

      
      [[:a {:href "/trainings"} [:h4 (idata :text  :education)]]
       [:ul.list-unstyled
        (for [x (data :trainings)]
          [:li [:a {:href (:href x)} (i x :title)]])]]

      [[:a {:href "/contacts"} [:h4 (idata :text  :contacts)]]
       [:ul.list-unstyled
        [:li [:a {:href (str "mailto:" (data :contacts :mailto))}
              (data :contacts :mailto)]]
        (for [x (data :contacts :offices)]
          [:li
           [:a (i x :country) ": " (i x :phone)]])]])]
     [:div.footer-line
      [:div.container-fluid
       [:b  " © 2015 HealthSamurai" (icon :samurai)]]]]))
