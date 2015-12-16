(ns site.products
  (:require [site.styles :refer [s-var vh style undecorate]]
            [site.navigation :refer [navigation]]
            [site.widgets :refer [grid  splash]]
            [site.data :refer [data]]
            [garden.units :refer [px px*]]
            [site.font :as font]))

(defn fa-link [href icon]
  [:a {:href href :target "_blank"}
   [:i.fa {:class (str "fa-" (name icon))}]])

(defn product [product]
  (let [props {:lightestgray "#fafafa"
               :lightgray "#ddd"
               :gray "#888"
               :red (s-var :color :main :em :color)
               :white (s-var :color :main :em :background-color)
               :dark (s-var :color :main :text :color)
               :icon-links-size (px* vh 2)}]
    [:div.product.row
     (style
      [:.product  {:padding {:top (px* vh 4.5) :bottom (px* vh 4)}}
       [:.hs-icon {:font-size (px* vh 8)
                   :text {:align "center"}
                   :display "block"
                   :margin {:bottom (px vh) :top (px* 2 vh)}}]

       [:.tag {:font-weight "300"
               :color (:gray props)
               :text {:align "center"}}]

       [:.fa {:font-size (px* 2 vh) 
              :line-height (px* 2 vh)
              :padding-left (px* 1 vh)
              :vertical-align "middle"
              :color (:gray props)}
        [:&:hover {:color (:dark props)}]]
       [:h1 {:text-align "center"
             :color (:white props)}]])

     [:div.col-md-3
      (font/icon (:id product))
      (for [label (product :labels)]
        [:div.tag label])]

     [:div.col-md-9
      [:a {:id (:id product) :href (str "#" (:id product))}
       [:h3 (str (product :title) " ")
        (when (:open-source product) [:small "Open Source"])]]
      [:p (:slogan product)]
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
  [:div#products
   (navigation {:color :inverse})
   (splash {:title  "Наши продукты"
            :moto "Мы разрабатываем технологичные продукты на основе HL7 FHIR."})

   [:div.container
    (for [prod (data :products)]
      [:div (product prod) [:hr]])]

   (splash {:title "Готовы к сотрудничеству?"
            :moto  "Если у вас остались какие либо вопросы, хотите увидеть демо наших решений или обсудить с нами ваш проект - оставьте запрос и мы свяжемся с вами."})])
