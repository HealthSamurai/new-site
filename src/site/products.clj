(ns site.products
  (:require [site.styles :refer [s-var vh style undecorate pbox mbox]]
            [site.navigation :refer [navigation]]
            [site.widgets :refer [grid  splash paralax]]
            [site.data :refer [data]]
            [garden.units :refer [px px*]]
            [site.font :as font]))

(defn fa-link [href icon]
  [:a {:href href :target "_blank"}
   [:i.fa {:class (str "fa-" (name icon))}]])


(defn product [product]
  (let [typescale (s-var :typescale :medium)
        palette (s-var :color :main)]
    [:div.product.row
     (style
      [:.product 
       [:.header (undecorate)]
       [:.hs-icon {:font-size (px* vh 8)
                   :text {:align "right"}
                   :position "relative"
                   :right "-61px"
                   :color "gray"
                   :display "block"}
        (mbox 4 nil 1 nil)]

       [:.tag (merge (:small typescale)
                     {:text {:align "right"}
                      :color "gray"
                      :font {:weight "bold"}})]
       [:.left
        {:positoin "relative" :overflow "hidden"}
        (pbox 2 nil 2 nil)]
       [:.column {:border {:left {:color "#ddd" :style "solid" :width "1px"}}
                  :padding {:left (px 40)}}
        (pbox 2 nil 2 nil)]

       [:.fa (merge  (:h2 typescale)
              {:font-size (px* 2 vh)
               :padding-left (px* 1 vh)
               :vertical-align "middle"})]
       [:h1 {:text-align "center"}]])

     [:div.col-md-3.left
      (font/icon (:id product))
      (for [label (product :labels)]
        [:div.tag label])]

     [:div.col-md-8.column
      [:a.header {:id (:id product) :href (str "#" (:id product))}
       [:h2 (str (product :title) " ")
        (when (:open-source product) [:small "Open Source"])]]
      [:p (:slogan product)]
      [:br]
      [:p (:description product)]
      [:h4 (str "Особенности " (:title product))]
      [:ul (for [f (product :features)] [:li f])]
      (when (:links product)
        (for [link (:links product)]
          (case (:type link)
            :direct       [:a.btn.btn-default.gotosite {:href (:link link) :target "_blank"} "Сайт продукта"]
            :github       (fa-link (:link link) :github)
            :google-group (fa-link (:link link) :comments-o))))]]))


(defn products [req]
  [:div
   (paralax 20
    [:div
     (navigation {:color :inverse})
     (splash {:title  "Наши продукты"
              :moto "Мы разрабатываем технологичные продукты на основе HL7 FHIR."})]
    [:div#products
     [:div.container (map product (data :products))]
     (splash {:title "Готовы к сотрудничеству?"
              :moto  "Если у вас остались какие либо вопросы, хотите увидеть демо наших решений или обсудить с нами ваш проект - оставьте запрос и мы свяжемся с вами."})])])
