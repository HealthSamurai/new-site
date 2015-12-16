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
  [{:href "/products" :title  (get-in strings [:text :products])}
   {:href "/projects" :title  (get-in strings [:text :projects])}
   {:href "/trainings" :title  (get-in strings [:text :trainings])}
   {:href "/contacts" :title (get-in strings [:text :contacts])}])


(def PRODUCTS
  [{:href "/products/fhirbase" :title "Fhirbase"}
   {:href "/products/aidbox" :title "Aidbox"}
   {:href "/products/hl7mapper" :title "HL7 Mapper"}
   {:href "/products/formstamp" :title "FormStamp"}
   {:href "/products/foodtaster" :title "FoodTaster"}])


(defn style [cnt]
  [:style {:type "text/css"} (css cnt)])

(def vh 18)

(def style-vars
  {:color {:main       {:text      {:color "#333356"}
                        :selection {:color "#a23835"
                                    :border-color "#a23835"
                                    :cursor "pointer"
                                    :background-color "#f5f5f5"}
                        :em   {:color "#a23835"}}
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

(defn grid [& columns]
  (let [cnt (/ 12 (count columns))]
    (into [:div.row]
          (for [c columns] (into [:div {:class (str "col-md-" cnt)}] c)))))


(defn navigation [opts]
  (let [props (merge {:color :inverse :items MENU} opts)
        palette (s-var :color (:color props))]
    [:div#navigation
     (style
      [:#navigation
       (merge (:em palette) {:padding "0 0 20px"})
       [:span.brand
        [:a (merge (s-var :color :inverse :em)
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
       [:ul {:margin-top (u/px* vh) 
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
         [:li [:a {:href (:href x)} (:title x)]])]]]))


(defn footer []
  (let [palette (s-var :color :inverse-ex)]
    [:div#footer
    (style
     [:div#footer (merge (:text palette) {:padding {:top (u/px* 4 vh) :bottom (u/px* 4 vh)}})
      [:a {:color "#ddd"}]])
    [:div.container
     (grid [[:h4 (data :text :services)]
            [:ul.list-unstyled
             (for [x (:services strings)]
               [:li [:a {:href (:href x)} (:title x)]])]]

           [[:h4 (data :text  :products)]
            [:ul.list-unstyled
             (for [x (data :products)]
               [:li
                [:a {:href (get-in x [:links 0 :link])} (:title x)]])]]
           [[:h4 (data :text  :education)]
            [:ul.list-unstyled
             (for [x (:education strings)]
               [:li [:a {:href (:href x)} (:title x)]])]]

           [[:h4 (data :text  :contacts)]
            [:ul.list-unstyled
             (for [x (data :contacts)]
               [:li
                [:a {:href (:href x)} (:title x)]])]])]]))

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
        "/bootstrap.min.css"
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
  (let [props {:lightestgray "#fafafa"
               :lightgray "#ddd"
               :gray "#888"
               :red (s-var :color :main :em :color)
               :white (s-var :color :main :em :background-color)
               :dark (s-var :color :main :text :color)
               :icon-links-size (u/px* vh 2)}]
    [:div.product.row.list-item
     (style
      [:.product  {:padding-top (u/px* vh 4.5)
                   :padding-bottom (u/px* vh 4.5)}
       [:.hs-icon {:font-size (u/px* vh 8)
                   :text-align "left"
                   :margin-left 0}]
       [:.custom-label {:font-weight "300"
                        :margin-right (u/px* vh 1/6)
                        :margin-bottom (u/px* vh 1/6)
                        :padding (u/px* vh 0.5)
                        :display "inline-block"
                        :border (str "1px solid " (:lightgray props))
                        :color (:gray props)
                        :background-color (:lightestgray props)}]
       [:i.add_links {:font-size (:icon-links-size props)
                      :padding-left (u/px* vh 1)
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
     ;; Markup
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
      [:strong (str "Особенности " (:title data))]
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
            "")))]]))

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
       (merge (:text palette)
              {:padding-top (u/px* 6 vh)
               :padding-bottom (u/px* 5 vh)})
       [:.splash-header     (merge (:h1 typescale)
                                   {:text-align "center"
                                    :margin "0 auto"
                                    :max-width "20em"})
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
        palette (s-var :color :main)
        typescale (s-var :typescale (:typescale props))]
    [:div#product-list
     (style [:#product-list
             {:border-top "1px solid #ddd"
              :padding {:top    (u/px* 2 vh)
                        :bottom (u/px* 3 vh)}}
             [:.block-header {:margin-bottom (u/px* 2 vh)}]
             [:h1 (:h1 typescale)]
             [:.logo {:display "block"
                      :position "relative"
                      :overflow "hidden"
                      :padding  (u/px vh)
                      :margin   {:right (u/px 10) :bottom (u/px vh)}
                      :color    (get-in palette [:text :color])
                      :border   "1px solid #ddd"}
              (undecorate)
              [:&:hover (:selection palette)]
              [:.hs-icon {:font-size (u/px* 8 vh)
                          :text-align "right"
                          :display "block"
                          :position "relative"
                          :right "-64px"}]
              [:h2 (merge (:h1 typescale)
                          {:text-align "left"
                           :line-height (u/px* 1 vh)})]]])
     [:div.container
      [:div.row.block-header
       [:h3 "Продукты " [:small "Все продукты (5)"]]]
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
              :padding {:top    (u/px* 2 vh)
                        :bottom (u/px* 3 vh)}}
             [:.block-header {:margin-bottom (u/px* 2 vh)}]
             [:h1 (:h1 typescale)]
             [:.list-item (merge (:text palette)
                                 {:display "block"
                                  :padding {:left "40px" :right "40px"}
                                  :margin  {:bottom (u/px vh)}
                                  :border  {:left  {:width (u/px 6)
                                                    :style "solid"
                                                    :color "#ddd"}}})
              (undecorate)
              [:&:hover (:selection palette)
               [:p (:text palette)]]]])
     [:div.container
      [:div.row.block-header
       [:h3 (data :text :projects) [:small " (5)"]]]
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
              :padding {:top    (u/px* 2 vh)
                        :bottom (u/px* 3 vh)}}
             [:.block-header {:margin-bottom (u/px* 2 vh)}]
             [:h1 (:h1 typescale)]
             [:.logo {:display "block"
                      :position "relative"
                      :overflow "hidden"
                      :min-height (u/px* 12 vh)
                      :padding  (u/px vh)
                      :margin   {:right (u/px 10) :bottom (u/px vh)}
                      :color    (get-in palette [:text :color])
                      :border   "1px solid #ddd"}
              (undecorate)
              [:&:hover (:selection palette)]
              [:.hs-icon {:font-size (u/px* 8 vh)
                          :text-align "right"
                          :display "block"
                          :position "relative"
                          :right "-64px"}]
              [:h2 (merge (:h3 typescale)
                          {:text-align "left"
                           :line-height (u/px* 1 vh)})]]
             [:p.desc {:margin {:right (u/px 10)}}]])
     [:div.container
      [:div.row.block-header
       [:h3 "Тренинги " [:small " (5)"]]]
      (apply grid
             (for [p (take 3 (data :trainings))]
               [[:a.logo {:href (str "/trainings#" (:id p))}
                 [:i.hs-icon {:class (font/fontello-icon-name :fhirbase)}]
                 [:h2 (:title p)]]
                 [:p.desc  (:desc p)]]))]]))

(defn index [req]
  [:div
   (style
    [:.meta {:height (u/px* 16 vh)}])
   (splash {:title  [:span "Мы " [:em  "знаем как"] " создавать медицинские информационные системы будущего"]
            :moto   [:span {:style "opacity:0.8;"} "Эксперты в Health IT. Разрабатываем для клиентов на основе наших технологичных продуктов и стандарта HL7 FHIR."]
            :color :main
            :typescale :large})
   (product-list  {})
   (project-list  {})
   (training-list  {})])

(defn products [req]
  [:div#products
   (style
    [:#products

     [:h1 {:text-align "center" :color "white"}]])
   (splash {:title  "Наши продукты" :moto "Мы разрабатываем технологичные продукты на основе HL7 FHIR."})
   [:div.container
    (for [prod (:products strings)] [:div (product prod) [:hr]])]
   (splash {:title  "Готовы к сотрудничеству?"
            :moto "Если у вас остались какие либо вопросы, хотите увидеть демо наших решений или обсудить с нами ваш проект - оставьте запрос и мы свяжемся с вами."})
   [:div [:h1 (get-in strings [:text :products])]]])

(defn projects [req]
  [:div
   (splash {:title  "Projects" :moto "looking for"})
   (dumn-block {})])

(defn contacts [req]
  [:div
   (splash {:title  "Contacts" :moto "contacts"})
   (dumn-block {})])
