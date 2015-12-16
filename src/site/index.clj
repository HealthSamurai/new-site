(ns site.index
  (:require [site.styles :refer [style vh s-var undecorate]]
            [garden.units :refer [px px*]]
            [site.widgets :refer [splash grid]]
            [site.data :refer [data]]
            [site.font :as font]
            [site.navigation :refer [navigation]]))

(defn block-header [href key]
  [:div.row.block-header
   (style [:.block-header {:margin-bottom (px* 2 vh)}])
   [:a {:href href} [:h3 (data :text key) "..."]]])

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
                      :color    (get-in palette [:text :color])
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
                           :line-height (px* 1 vh)})]]])
     [:div.container
      (block-header "/products" :products)
      (apply grid
             (for [p (take 3 (data :products))]
               [[:a.logo {:href (str "/products#" (:id p))}
                 [:i.hs-icon {:class (font/fontello-icon-name (:id p))}]
                 [:h2 (:title p)]]
                 [:p  (:slogan p)]]))]]))



(defn project-list [opts]
  (let [defaults {:color :inverse :typescale :medium}
        props (merge defaults opts)
        palette (s-var :color :main)
        typescale (s-var :typescale (:typescale props))]
    [:div#project-list
     (style [:#project-list
             {:border-top "1px solid #ddd"
              :padding {:top    (px* 2 vh)
                        :bottom (px* 3 vh)}}
             [:.block-header {:margin-bottom (px* 2 vh)}]
             [:h1 (:h1 typescale)]
             [:.list-item (merge (:text palette)
                                 {:display "block"
                                  :padding {:left "40px" :right "40px"}
                                  :margin  {:bottom (px vh)}
                                  :border  {:left  {:width (px 3)
                                                    :style "solid"
                                                    :color "#ddd"}}})
              (undecorate)
              [:&:hover (:selection palette)
               [:p (:text palette)]]]])
     [:div.container
      (block-header "/projects" :projects)
      (for [p (data :projects)]
        [:a.list-item.row
         [:h3 (:title p)]
         [:p  (:desc p)]])]]))

(defn training-list [opts]
  (let [defaults {:color :inverse :typescale :medium}
        props (merge defaults opts)
        palette (s-var :color :main)
        typescale (s-var :typescale (:typescale props))]
    [:div#training-list
     (style [:#training-list
             {:border-top "1px solid #ddd"
              :padding {:top    (px* 2 vh)
                        :bottom (px* 3 vh)}}
             [:.block-header {:margin-bottom (px* 2 vh)}]
             [:h1 (:h1 typescale)]
             [:.logo {:display "block"
                      :position "relative"
                      :overflow "hidden"
                      :min-height (px* 12 vh)
                      :padding  (px vh)
                      :margin   {:right (px 10) :bottom (px vh)}
                      :color    (get-in palette [:text :color])
                      :border   "1px solid #ddd"}
              (undecorate)
              [:&:hover (:selection palette)]
              [:.hs-icon {:font-size (px* 8 vh)
                          :text-align "right"
                          :display "block"
                          :position "relative"
                          :right "-64px"}]
              [:h2 (merge (:h3 typescale)
                          {:text-align "left"
                           :line-height (px* 1 vh)})]]
             [:p.desc {:margin {:right (px 10)}}]])
     [:div.container
      (block-header "/trainings" :trainings)
      (apply grid
             (for [p (take 3 (data :trainings))]
               [[:a.logo {:href (str "/trainings#" (:id p))}
                 [:i.hs-icon {:class (font/fontello-icon-name :fhirbase)}]
                 [:h2 (:title p)]]
                 [:p.desc  (:desc p)]]))]]))

(defn index [req]
  [:div
   (navigation {:color :main})
   (splash {:title  [:span "Мы " [:em  "знаем как"] " создавать медицинские информационные системы будущего"]
            :moto   [:span {:style "opacity:0.8;"} "Эксперты в Health IT. Разрабатываем для клиентов на основе наших технологичных продуктов и стандарта HL7 FHIR."]
            :color :main
            :typescale :large})
   (product-list  {})
   (project-list  {})
   (training-list  {})])
