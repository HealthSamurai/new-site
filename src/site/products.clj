(ns site.products
  (:require [site.styles :refer [s-var vh style undecorate pbox mbox] :as s]
            [site.navigation :refer [navigation]]
            [site.widgets :refer [grid  splash paralax] :as w]
            [site.data :refer [data i idata]]
            [garden.units :refer [px px*]]
            [site.font :as font]))

(defn fa-link [href icon]
  [:a {:href href :target "_blank"}
   (style
    [:.product-link
     [:i
      {:font-size "36px"
       :vertical-align "middle"
       :color "#333356"}]
     [:&:hover {:color "#a23835"}]])
   (w/fa-icon icon)])

(defn features-list [product]
  [:div.features-list
   (style
    [:.features-list
     [:h4 (s/&margin 2 nil nil nil)]
     [:.features
      {:position "relative"
       :left "-41px"
       :list-style-type "square"}
      (s/&margin nil nil 1 nil)]])
   [:h4 (i product :title)]
   [:ul.features
    (for [f (i product :features)]
      [:li f])]])

(defn links-list [product]
  [:div.links-list
   (style
    [:.links-list
     [:.fa {:font-size (s/vh* 2)
            :padding-left (s/vh* 1)
            :vertical-align "middle"}]
     [:.direct-link
      {:border "1px solid #a23835"
       :border-radius "none"
       :color "#a23835"}
      [:&:hover {:background-color "#a23835" :color "white"}]]])

   (for [[key link] (:links product)]
     (case key
       :direct       [:a.btn.btn-default.direct-link {:href link :target "_blank"} (pr-str "TODO" link)]
       :github       (fa-link link :github)
       :chat         (fa-link link :comments-o)))])

(defn product-view [product]
  [:div.product.row
   (style
    [:.product
     [:.header (s/undecorate)]
     [:.hs-icon {:font-size (s/vh* 8)
                 :color (s/color :text)
                 :display "block"
                 :margin-left 0}
      (s/&margin 1.5 nil 1 nil)]

     [:.left {:position "relative" :overflow "hidden"}
      (s/&padding 2 nil)]
     [:h1 (s/&text :center)]])

   [:div.col-md-2.left
    (font/icon (:logo product))
    (w/tags (product :tags))]

   [:div.col-md-8.column
    [:a.header {:id (:id product) :href (str "#" (:id product))}
     [:h2 (str (i product :title) " ")
      [:small (i product :type)]]]
    [:p (i product :motto)]
    [:br]
    [:p (i product :description)]
    (features-list product)
    (links-list product)]])

(defn products [req]
  [:div
   (navigation {:color :inverse})
   (splash {:title  (idata :text :products)
            :moto   (idata :text :products-subtitle)})
   [:div#products
    [:div.container (map product-view (data :products))]
    (splash {:title (idata :text :products-target) 
             :moto (idata :text :products-subtarget)})]])
