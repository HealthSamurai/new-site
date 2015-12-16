(ns site.products
  (:require [site.styles :refer [s-var vh style undecorate]]
            [site.navigation :refer [navigation]]
            [site.widgets :refer [grid  splash]]
            [site.data :refer [data]]
            [garden.units :refer [px px*]]
            [site.font :as font]))

(defn fa-link [href icon]
  [:a {:href href :target "_blank"} [:i.fa.add_link {:class (str ".fa-" (name icon))}]])

(defn product [data]
  (let [props {:lightestgray "#fafafa"
               :lightgray "#ddd"
               :gray "#888"
               :red (s-var :color :main :em :color)
               :white (s-var :color :main :em :background-color)
               :dark (s-var :color :main :text :color)
               :icon-links-size (px* vh 2)}]
    [:div.product.row.list-item
     (style
      [:.product  {:padding-top (px* vh 4.5)
                   :padding-bottom (px* vh 4.5)}
       [:.hs-icon {:font-size (px* vh 8)
                   :text-align "left"
                   :margin-left 0}]
       [:.custom-label {:font-weight "300"
                        :margin-right (px* vh 1/6)
                        :margin-bottom (px* vh 1/6)
                        :padding (px* vh 0.5)
                        :display "inline-block"
                        :border (str "1px solid " (:lightgray props))
                        :color (:gray props)
                        :background-color (:lightestgray props)}]
       [:i.add_links {:font-size (:icon-links-size props)
                      :padding-left (px* vh 1)
                      :vertical-align "middle"
                      :color (:gray props)}
        [:&:hover {:color (:dark props)}]]
       [:a.btn.gotosite {:border (str "1px solid " (:red props))
                         :border-radius "none !important"
                         :color (:red props)}
        [:&:hover {:background-color (:red props)
                   :color (:white props)}]]
       [:h1 {:text-align "center"
             :color (:white props)}]])
     [:div.col-md-3
      [:i.hs-icon {:class (font/fontello-icon-name (:id data))}]
      (for [label (:labels data)] [:span [:br] [:span.label.custom-label label]])]
     [:div.col-md-8
      [:h3 (str (:title data) " ") (when (:open-source data) [:small "Open Source"])]
      [:span.product-slogan (:slogan data) [:br]]
      [:p (:description data)]
      [:strong (str "Особенности " (:title data))]
      [:ul (for [feature (:features data)] [:li feature])]
      (when (:links data)
        (for [link (:links data)]
          (case (:type link)
            :direct       [:a.btn.btn-default.gotosite {:href (:link link) :target "_blank"} "Сайт продукта"]
            :github       (fa-link (:link link) :github)
            :google-group (fa-link (:link link) :comments-o))))]]))


(defn products [req]
  [:div#products
   (style
    [:#products
     [:h1 {:text-align "center" :color "white"}]])
   (navigation {:color :inverse})
   (splash {:title  "Наши продукты" :moto "Мы разрабатываем технологичные продукты на основе HL7 FHIR."})
   [:div.container
    (for [prod (data :products)] [:div (product prod) [:hr]])]
   (splash {:title "Готовы к сотрудничеству?"
            :moto  [:span
                    [:span "Если у вас остались какие либо вопросы, хотите увидеть демо наших решений или обсудить с нами ваш проект - оставьте запрос и мы свяжемся с вами."]
                    [:br]
                    [:button.btn "Try now"]]})])
