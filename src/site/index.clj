(ns site.index
  (:require [site.styles :refer [style vh s-var undecorate] :as s]
            [garden.units :refer [px px*]]
            [site.widgets :refer [splash grid] :as w]
            [site.data :refer [data i idata]]
            [site.font :as font]
            [site.navigation :refer [navigation]]))

(defn block-header [href key]
  [:div.row.block-header
   (style [:.block-header {:margin-bottom (px* 2 vh)}
           [:a {:color "inherit"}]])
   [:a {:href href} [:h3 (idata :text key) "..."]]])

(defn product-list [opts]
  (let [defaults {:color :inverse :typescale :medium}
        props (merge defaults opts)
        palette (s-var :color :main)
        typescale (s-var :typescale (:typescale props))]
    [:div#product-list
     (style [:#product-list
             {:border-top "1px solid #ddd"
              :padding {:top    (px* 2 vh)
                        :bottom (px* 3 vh)}}
             [:h1 (:h1 typescale)]
             [:.logo {:display "block"
                      :position "relative"
                      :overflow "hidden"
                      :padding  (px vh)
                      :margin   {:right (px 10) :bottom (px vh)}
                      :color    (s-var :color :main :muted :color)
                      :border   "1px solid #ddd"}
              (undecorate)
              [:&:hover (:selection palette)]
              [:.hs-icon {:font-size (px* 8 vh)
                          :text-align "right"
                          :display "block"
                          :position "relative"
                          :right "-64px"}]
              [:h2 (merge (:h1 typescale)
                          {:text-align "left"
                           :color    (s-var :color :main :selection :color)
                           :line-height (px* 1 vh)})]]])
     [:div.container
      (block-header "/products" :products)
      (apply grid
             (for [p (take 3 (data :products))]
               [[:a.logo {:href (str "/products#" (p :id))}
                 [:i.hs-icon {:class (font/fontello-icon-name (p :id))}]
                 [:h2 (i p :title)]]
                [:p  (i p :motto)]]))]]))



(defn project-list [opts]
  (let [defaults {:color :inverse :typescale :medium}
        props (merge defaults opts)
        palette (s-var :color :main)
        typescale (s-var :typescale (:typescale props))]
    [:div#project-list
     (style [:#project-list
             (s/&border :top)
             (s/&padding 2 nil 3 nil)
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
             (s/&padding 2 nil 3 nil)
             (s/&border :top)
             [:h1 (:h1 typescale)]
             [:.logo {:display "block"
                      :position "relative"
                      :overflow "hidden"
                      :min-height (px* 12 vh)
                      :padding  (px vh)
                      :color    (s-var :color :main :muted :color)}
              (s/&border)
              (s/&margin nil 1 1 nil)
              (undecorate)
              [:&:hover (:selection palette)]
              [:.hs-icon {:font-size (s/vh* 8)
                          :display "block"
                          :position "relative"
                          :right "-64px"}
               (s/&text :right)]
              [:h2 (merge (:h3 typescale)
                          {:text-align "left"
                           :color (s-var :color :main :selection :color)
                           :line-height (px* 1 vh)})]]
             [:p.desc (s/&margin nil 1  1 nil)]])
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
           (s/&padding 2 nil 3 nil)
           (s/&border :top)
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

(defn index [req]
  [:div#index
   (navigation {:color :main})
   (style [:#index [:em {:font-style "normal"}]])
   (splash {:title  [:span "Мы " [:em  "знаем как"] " создавать медицинские информационные системы будущего"]
            :moto   [:span {:style "opacity:0.8;"} "Эксперты в Health IT. Разрабатываем для клиентов на основе наших технологичных продуктов и стандарта HL7 FHIR."]
            :color :main
            :typescale :large})
   (product-list  {})
   (project-list  {})
   (training-list  {})
   (partnerhsip-list {})])
