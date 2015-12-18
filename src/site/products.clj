(ns site.products
  (:require [site.styles :refer [s-var vh style undecorate pbox mbox]]
            [site.navigation :refer [navigation]]
            [site.widgets :refer [grid  splash paralax]]
            [site.data :refer [data]]
            [garden.units :refer [px px*]]
            [site.font :as font]))

(defn fa-link [href icon]
  [:a {:href href :target "_blank"}
   (style
    [:.product-link
     {:font-size "36px"
      :vertical-align "middle"
      :color "#333356"}
     [:&:hover {:color "#a23835"}]])
   [:i.fa.product-link {:class (str "fa-" (name icon))}]])

(defn product-view [product]
  (let [typescale (s-var :typescale :medium)
        palette (s-var :color :main)]
    [:div.product.row
     (style
      [:.product
       [:.header (undecorate)]
       [:.hs-icon {:font-size (px* vh 8)
                   :text {:align "left"}
                   ;; :position "initial"
                   ;; :right "-61px"
                   :color (get-in palette [:text :color])
                   :display "block"
                   :margin-left 0}
        (mbox 1.5 nil 1 nil)]

       [:.tag
        (merge
         (:small typescale)
         {:font {:weight "300"
                 :size "75%"}
          :background-color (get-in palette [:selection :background-color])
          :border-radius ".25em"
          :border "1px solid #ddd"
          :color "#888"
          :display "inline-block"
          :line-height "1"
          :margin-bottom "4px"
          :margin-right "4px"
          :padding "10px"
          :text-align "center"
          :vertical-align "baseline"
          :white-space "nowrap"})]
       [:.left
        {:position "relative" :overflow "hidden"}
        (pbox 2 nil 2 nil)]
       [:.column {
                  :padding {:left (px 40)}}
        (pbox 2 nil 2 nil)]

       [:.fa (merge  (:h2 typescale)
                     {:font-size (px* 2 vh)
                      :padding-left (px* 1 vh)
                      :vertical-align "middle"})]
       [:.features
        (mbox 2 nil nil nil)]
       [:.features-list
        {:position "relative"
         :left "-41px"
         :list-style-type "square"}
         (mbox nil nil 1 nil)]
       [:.direct-link
        {:border "1px solid #a23835"
         :border-radius "none"
         :color "#a23835"}
        [:&:hover {:background-color "#a23835"
                   :color "white"}]]
       [:h1 {:text-align "center"}]])

     [:div.col-md-2.left
      (font/icon (:logo product))
      (for [label (product :tags)]
        [:div
         [:span.tag.label label]
         [:br]])]

     [:div.col-md-8.column
      [:a.header {:id (:id product) :href (str "#" (:id product))}
       [:h2 (str (:title product) " ")
        (when (= (:type product) "opensource") [:small "Open Source"])]]
      [:p (:motto product)]
      [:br]
      [:p (:description product)]
      [:h4.features (str "Особенности " (:title product))]
      [:ul.features-list (for [f (product :features)] [:li f])]
      (for [link (:links product)]
        (let [key (first link)
              link (last link)]
          (case key
            :direct       [:a.btn.btn-default.direct-link {:href link :target "_blank"} "Сайт продукта"]
            :github       (fa-link link :github)
            :chat (fa-link link :comments-o)
            "")))]]))

(defn products [req]
  [:div
   (paralax 20
            [:div
             (navigation {:color :inverse})
             (splash {:title  "Наши продукты"
                      :moto "Мы разрабатываем технологичные продукты на основе HL7 FHIR."})]
            [:div#products
             [:div.container (map product-view (data :products))]
             (splash {:title "Готовы к сотрудничеству?"
                      :moto  "Если у вас остались какие либо вопросы, хотите увидеть демо наших решений или обсудить с нами ваш проект - оставьте запрос и мы свяжемся с вами."})])])
