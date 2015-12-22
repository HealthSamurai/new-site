(ns site.navigation
  (:require [site.data :refer [data i idata]]
            [site.styles :refer [s-var style vh undecorate pbox mbox] :as s]
            [site.widgets :refer [grid]]
            [site.font :refer [icon]]))


(defn navigation [opts]
  (let [props (merge {:color :inverse} opts)
        palette (s-var :color (:color props))]
    [:div#navigation
     (style
      [:#navigation
       (:em palette)
       [:a.brand (s-var :color :inverse :em)
        (s/&unstyle-links)
        (s/&inline)
        (s/&text :center)
        (s/&padding 0 0.7)
        [:i
         (s/&inline)
         (s-var :color :inverse :em)
         (s/&font-scale 1.8 4)
         (s/&center-block)]]
       [:ul {:margin-bottom 0 :float "right"}
        (s/&inline)
        [:li (s/&inline)
         [:a {:color (get-in palette [:em :color])}
          (s/&font-scale 1 3)
          (s/&padding 0.5 1)
          (s/&inline)
          (undecorate)
          [:&:hover {:color (get-in palette [:em :color])
                     :border-color (get-in palette [:em :color])}]]]]])
     [:div.container
      [:a.brand {:href "/"} (icon :samurai)]
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
