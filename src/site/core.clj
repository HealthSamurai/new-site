(ns site.core
  (:require [clojure.string :as str]
            [hiccup.core :refer [html]]
            [hiccup.page :as hp]
            [site.font :as font]
            [garden.color :as c]
            [garden.core :refer [css]]
            [garden.units :as u]
            [garden.stylesheet :as ss]
            [site.data :refer [strings]]))

(defn data [& ks]
  (get-in strings ks))

(def MENU
  [{:href "/products" :text  (get-in strings [:text :products])}
   {:href "/projects" :text  (get-in strings [:text :projects])}
   {:href "/contacts" :text (get-in strings [:text :contacts])}])


(def PRODUCTS
  [{:href "/products/fhirbase" :text "Fhirbase"}
   {:href "/products/aidbox" :text "Aidbox"}
   {:href "/products/hl7mapper" :text "HL7 Mapper"}
   {:href "/products/formstamp" :text "FormStamp"}
   {:href "/products/foodtaster" :text "FoodTaster"}])

(def CONTACTS
  )

(defn style [cnt]
  [:style {:type "text/css"} (css cnt)])

(def vh 18)

(def style-vars
  {:color {:main       {:text {:color "#333356"
                               :background-color "white"}
                        :em   {:color "#a23835"
                               :background-color "white"}}
           :inverse    {:text {:color "white"
                               :background-color "#a23835"} 
                        :em   {:color "white"
                               :background-color "#a23835"}}
           :inverse-ex {:text {:color "white"
                               :background-color "rgba(46,48,58,0.96)"}
                        :em   {:color "white"
                               :background-color "rgba(46,48,58,0.96)"}}}
   :typescale {:large {:h1 {:font-size "41px"  :font-weight "600"  :line-height (u/px* vh 3)}
                       :h2 {:font-size "24px"  :font-weight "bold" :line-height "1.5em"}
                       :h3 {:font-size  "21px" :font-weight "300"  :line-height "1.5em"}
                       :h4 {:font-size  "21px" :font-weight "600" :line-height (u/px* 1.5 vh)}
                       :p  {:font-size "18px"  :line-height (u/px* vh 1.5)}
                       :small {:font-size "16px"}}
               :medium {:h1 {:font-size "41px"  :line-height "1.3em" :font-weight "normal"}
                        :h2 {:font-size "24px"  :font-weight "bold"  :line-height "1.5em"}
                        :h3 {:font-size  "18px" :font-weight "bold"  :line-height "1.5em"}
                        :h4 {:font-size  "18px"}
                        :p  {:font-size "18px"}
                        :small {:font-size "16px"}}}})

(defn s-var [& ks] (get-in style-vars ks))

(defn undecorate []
  [:& {:text-decoration "none"}
   [:&:hover {:text-decoration "none"}]])

(defn navigation [opts]
  (let [props (merge {:color :inverse :items MENU} opts)
        palette (s-var :color (:color props))]
   [:div#navigation
    (style
     [:#navigation
      (merge (:em palette) {:padding "0 0 20px"})
      [:span.brand
       [:a
        (merge (s-var :color :inverse :em)
               {:text-align "center"
                :width "80px"
                :height "72px"
                :display "inline-block"})
        [:i (merge (s-var :color :inverse :em)
                   {:padding-top "15px"
                    :text-align "center"
                    :text-decoration "none"
                    :font-size (u/px* 2 vh)
                    :line-height "1em"
                    :margin {:left "auto" :right "auto"}})]]]
      [:ul {:margin-top "18px"
            :display "inline-block"
            :height (u/px* 2 vh)
            :margin-bottom 0
            :float "right"}
       [:li {:display "inline-block"
             :margin-left "20px"}
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
      (for [x (:items props)]
        [:li [:a {:href (:href x)} (:text x)]])]]]))

(defn grid [& columns]
  (let [cnt (/ 12 (count columns))]
    (into [:div.row]
          (for [c columns] (into [:div {:class (str "col-md-" cnt)}] c)))))

(defn footer []
  (let [palette (s-var :color :inverse-ex)]
    [:div#footer
    (style
     [:div#footer (merge (:text palette) {:padding {:top "80px" :bottom "80x"}})
      [:a {:color "#ddd"}]])
    [:div.container
     (grid [[:h4 (data :text :services)]
            [:ul.list-unstyled
             (for [x (:services strings)]
               [:li [:a {:href (:href x)} (:text x)]])]]

           [[:h4 (data :text  :products)]
            [:ul.list-unstyled
             (for [x (data :products)]
               [:li
                [:a {:href (get-in x [:links 0 :link])} (:title x)]])]]
           [[:h4 (data :text  :education)]
            [:ul.list-unstyled
             (for [x (:education strings)]
               [:li [:a {:href (:href x)} (:text x)]])]]

           [[:h4 (data :text  :contacts)]
            [:ul.list-unstyled
             (for [x (data :contacts)]
               [:li
                [:a {:href (:href x)} (:text x)]])]])]]))

(defn layout [cnt]
  [:html
   [:head
    [:meta {:charset "utf-8"}]
    [:meta {:http-equiv "X-UA-Compatible"
            :content "IE=edge"}]
    [:title "Health Samurai"]
    [:meta {:name "desription" :content ""}]
    [:meta {:name "viewport" :content "width=device-width, initial-scale=1.0"}]
    [:title (:title strings)]

    (hp/include-css
        "//maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css"
        "https://fonts.googleapis.com/css?family=Exo+2:400,100,100italic,200,200italic,300,300italic,400italic,500,900italic,500italic,600,600italic,700,700italic,800,800italic,900"
        "//maxcdn.bootstrapcdn.com/font-awesome/4.3.0/css/font-awesome.min.css")

    (style (ss/at-font-face font/font-face))
    (style
     [:body {:padding "0"
             :margin "0"
             :font-family "'Exo 2', sans-serif"
             :font-size "18px"
             :font-weight "300"
             :color "#333356"
             :letter-spacing "1px"}
      font/garden-font
      [:.col-md-4 {:padding-left 0 :padding-right 0}]
      ])]
   [:body [:div cnt] (footer)]])


(defn product [data]
  [:div.product.row.list-item
   (style
    [:.product  {:padding-top "80px"
                 :padding-bottom "80px"}
     [:.hs-icon {:font-size "144px"
                 :text-align "center"}]
     [:.custom-label {:font-weight "300"
                      :margin-right "4px"
                      :margin-bottom "4px"
                      :padding "10px"
                      :display "inline-block"
                      :border "1px solid #ddd"
                      :color "#888"
                      :background-color "#fafafa"}]
     [:i.add_links {:font-size "36px"
                    :padding-left "20px"
                    :vertical-align "middle"
                    :color "#888"}
      [:&:hover {:color (s-var :color :main)}]]
     [:a.btn.gotosite {:border "1px solid #a23835"
                       :border-radius "none !important"
                       :color (s-var :color :main)}
      [:&:hover {:background-color (s-var :color :main)
                 :color "white"}]]
     [:a.item-product {:margin-right "10px"
                       :border "1px solid transparent"
                       :text-decoration "none"
                       :color "#333356"
                       :display "block"}]
     [:h1 {:text-align "center" :color "white"}]])
   [:div.col-md-3
    [:i.hs-icon {:class (font/fontello-icon-name (:id data))}]
    [:br]
    (for [label (:labels data)] [:span [:br] [:span.label.custom-label label]])]
   [:div.col-md-8
    [:h3 (str (:title data) " ")
     (when (:open-source data) [:small "Open Source"])]
    [:span.product-slogan (:slogan data) [:br]]
    [:br]
    [:p (:description data)]
    [:br]
    [:strong "Особенности Fhirbase"]
    [:ul
     (for [feature (:features data)]
       [:li feature])]
    [:br]
    (when (:links data)
      (for [link (:links data)]
        (case (:type link)
          :direct [:a.btn.btn-default.gotosite {:href (:link link) :target "_blank"} "Сайт продукта"]
          :github [:a {:href (:link link) :target "_blank"}
                   [:i.fa.fa-github.add_links]]
          :google-group [:a {:href (:link link) :target "_blank"}
                         [:i.fa.fa-comments-o.add_links]]
          "")))]])

(defn dumn-block [opts]
  (let [props (merge {} opts)
        palette (s-var :color (:color props))]
    [:div.dumn
     (style [:.dumn {:min-height "300px"}])
     [:div.container
      [:div.dumn-block [:h3 "Dumn"]]]]))

(defn splash [opts]
  (let [defaults {:color :inverse :typescale :medium}
        props (merge defaults opts)
        typescale (s-var :typescale (:typescale props))
        palette (s-var :color (:color props))]
    [:div
     (navigation {:color (:color props)})
     (style
      [:#splash
       (merge (:text palette) {:padding-top (u/px* 6 vh) :padding-bottom (u/px* 5 vh)})
       [:.splash-header     (merge (:h1 typescale) {:text-align "center"})
        [:em (merge {:font-style "normal"} (:em palette))]]
       [:.splash-sub-header (merge (:h4 typescale)
                                   {:padding-top (u/px* 0.5  vh) 
                                    :margin-left "auto"
                                    :max-width "40em"
                                    :text-align "center"
                                    :margin-right "auto"})]])
    [:div#splash
     [:div.container-fluid
      [:h1.splash-header    (:title opts)]
      [:p.splash-sub-header (:moto opts)]]]]))



(defn product-list [opts]
  (let [defaults {:color :inverse :typescale :medium}
        props (merge defaults opts)
        typescale (s-var :typescale (:typescale props))]
    [:div#product-list
     (style [:#product-list
             [:h1 (:h1 typescale)]])
     [:hr]
     [:div.container
      [:h3 "Продукты"]]]))

(defn index [req]
  [:div
   (splash {:title  [:span "Мы " [:em  "знаем как"] " создавать медицинские информационные системы будущего"]
            :moto   [:span {:style "opacity:0.8;"} "Эксперты в Health IT. Разрабатываем для клиентов на основе наших технологичных продуктов и стандарта HL7 FHIR."]
            :color :main
            :typescale :large})
   (product-list  {})
   (dumn-block {})])

(defn products [req]
  [:div#products
   (style
    [:#products
     [:a.item-product {:margin-right "10px"
                       :border "1px solid transparent"
                       :text-decoration "none"
                       :color "#333356"
                       :display "block"}]
     [:h1 {:text-align "center" :color "white"}]])
   (splash {:title  "Наши продукты" :moto "Мы разрабатываем технологичные продукты на основе HL7 FHIR."})
   [:div.container
    (for [prod (:products strings)] [:div (product prod) [:hr]])]
   [:div [:h1 (get-in strings [:text :products])]]])

(defn projects [req]
  [:div
   (splash {:title  "Projects" :moto "looking for"})
   (dumn-block {})])

(defn contacts [req]
  [:div
   (splash {:title  "Contacts" :moto "contacts"})
   (dumn-block {})])
