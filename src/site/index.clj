(ns site.index
  (:require [site.styles :refer [style vh s-var undecorate] :as s]
            [garden.units :refer [px px*]]
            [site.widgets :refer [splash grid] :as w]
            [site.formats :as fmt]
            [site.data :refer [data i idata]]
            [site.font :as font]
            [site.navigation :refer [navigation]]))

(defn block-header [href key]
  [:div.row.block-header
   (style [:.block-header (s/&margin 4 nil 2 nil)
           [:a (s/&unstyle-links)]])
   [:a {:href href} [:h3 (idata :text key) "..."]]])

(defn product-list [opts]
  (let [defaults {:color :inverse :typescale :medium}
        props (merge defaults opts)
        palette (s-var :color :main)
        typescale (s-var :typescale (:typescale props))]
    [:div#product-list
     (style [:#product-list
             (s/&border :top)
             [:h1 (:h1 typescale)]
             [:.logo {:display "block"
                      :position "relative"
                      :overflow "hidden"
                      :padding (s/vh* 1) 
                      :height (s/vh* 12)
                      :margin   {:right (px 10) :bottom (s/vh* 1)}
                      :color    (s-var :color :main :muted :color)
                      :border   "1px solid #ddd"}
              (undecorate)
              [:&:hover (:selection palette)]
              [:.hs-icon {:display "block"
                          :position "absolute"
                          :bottom (s/vh* 1)
                          :right "-50px"}
               (s/&text :right)
               (s/&font-scale 10 10)]
              [:h2 (merge (:h1 typescale)
                          {:text-align "left"
                           :position "absolute"
                           :left (s/vh* 1)
                           :bottom (s/vh* 1)
                           :color    (s/color :selection)
                           :line-height (s/vh* 1)})
               (s/&text 500)]]
             [:p.desc (s/&padding 0.5 0.5)]])
     [:div.container
      (block-header "/products" :products)
      (apply grid
             (for [p (take 3 (data :products))]
               [[:a.logo {:href (str "/products#" (p :id))}
                 [:i.hs-icon {:class (font/fontello-icon-name (p :id))}]
                 [:h2 (i p :title)]]
                [:p.desc  (i p :motto)]]))]]))


(defn project-list [opts]
  (let [defaults {:color :inverse :typescale :medium}
        props (merge defaults opts)
        palette (s-var :color :main)
        typescale (s-var :typescale (:typescale props))]
    [:div#project-list
     (style [:#project-list
             [:h1 (:h1 typescale)]
             [:.list-item (merge (:text palette)
                                 {:display "block"
                                  :border  {:left  {:width (px 3) :style "solid" :color "#ddd"}}})
              (s/&padding nil 2)
              (s/&margin 0 nil 1 nil)
              (undecorate)
              [:&:hover (:selection palette)
               [:p (:text palette)]]]])
     [:div.container
      (block-header "/projects" :projects)
      (for [p (data :projects)]
        [:a.list-item.row {:href (str "/projects#" (:id p))}
         [:h3 (i p :title)]
         [:p  (i p :desc)]])]]))

(defn training-list [opts]
  (let [defaults {:color :inverse :typescale :medium}
        props (merge defaults opts)
        palette (s-var :color :main)
        typescale (s-var :typescale (:typescale props))]
    [:div#training-list
     (style [:#training-list
             [:h1 (:h1 typescale)]
             [:.logo {:display "block"
                      :position "relative"
                      :overflow "hidden"
                      :min-height (s/vh* 12)
                      :color    (s-var :color :main :muted :color)}
              (s/&border)
              (s/&margin nil 1 1 nil)
              (undecorate)
              [:&:hover (:selection palette)]
              [:.hs-icon {:font-size (s/vh* 8)
                          :display "block"
                          :position "absolute"
                          :bottom (s/vh* 1)
                          :right "-50px"}
               (s/&text :right)]
              [:h2 (merge (:h3 typescale)
                          {:text-align "left"
                           :position "absolute"
                           :bottom (s/vh* 0.5)
                           :left (s/vh* 1)
                           :color (s-var :color :main :selection :color)
                           :line-height (px* 1 vh)})]]
             [:p.desc (s/&margin nil 0.5)]])
     [:div.container
      (block-header "/trainings" :trainings)
      (apply grid
             (for [p (take 3 (data :trainings))]
               [[:a.logo {:href (str "/trainings#" (:id p))}
                 [:i.hs-icon {:class (font/fontello-icon-name :fhirbase)}]
                 [:h2 (i p :title)]]
                [:p.desc  (i p :abstract)]]))]]))


(defn partnerhsip-list [opts]
  [:div#partnership
   (style [:#partnership
           [:.items-list
            [:a.list-item
             {:display "block"
              :vertical-align "middle"
              :border  {:width (px 3)}}
             (s/&unstyle-links)
             (s/&padding 1)
             (s/&border :top)
             [:&:hover
              (s-var :color :main :selection)
              [:p {:color (s-var :color :main :text :color)}]
              [:.fa {:color (s-var :color :main :selection :color)}]]
             [:.fa {:float "left"
                    :display "inline-block"
                    :color "#888" 
                    :width "auto"
                    :vertical-align "middle"
                    :line-height (s/vh* 4)
                    :font-size (s/vh* 3)}
              (s/&margin nil 2 nil nil)]
             [:.desc
              {:display "inline-block"
               :vertical-align "middle"}]]]])
   [:div.container
    (block-header "/services" :partnership)
    [:div.items-list
     (for [x (data :services)]
       [:a.row.list-item {:href (str "/services#" (:id x))}
        (w/fa-icon (or (:icon x) "rocket"))
        [:div.desc
         [:h4 (i x :title)]
         [:p  (i x :description)]]])]]])

(defn promo []
  [:div
   (style
    [:#promo
     (s/&padding 8 0 15 0)

     [:.promo-header
      (s/&font-scale 2.3 3)
      (s/&text :center 600)
      (s/&center-block "20em")
      [:em {:color (s/color :selection)}]]

     [:.promo-sub-header
      {:opacity 0.88}
      (s/&padding 1 nil)
      (s/&font-scale 1.1 2)
      (s/&center-block "35em")
      (s/&text :center 600)]])
   [:div#promo
    [:div.container-fluid
     [:h1.promo-header [:span (fmt/markdown (idata :text :promo) )]]
     [:p.promo-sub-header [:span (idata :text :promo-subtitle)]]]]])

(defn index [req]
  [:div#index
   (navigation {:color :main})
   (style [:#index [:em {:font-style "normal"}]])
   (promo)
   (product-list  {})
   (project-list  {})
   (training-list  {})
   (partnerhsip-list {})
   [:br]
   [:br]
   [:br]
   [:br]])
