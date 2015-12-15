(ns site.core
  (:require [clojure.string :as str]
            [hiccup.core :refer [html]]
            [hiccup.page :as hp]
            [ring.middleware.resource :as rmr]
            [site.font :as font]
            [garden.color :as c]
            [garden.core :refer [css]]
            [garden.units :as u]
            [garden.stylesheet :as ss]
            [site.data :refer [strings]]
            [org.httpkit.server :as srv]
            [route-map.core :as rt]))

(defn http [hic]
  {:body    (html hic)
   :headers {"Content-Type" "text/html; charset=utf-8"}
   :status  200})


(def MENU
  [{:href "/products" :text  (get-in strings [:text :products])}
   {:href "/projects" :text  (get-in strings [:text :projects])}
   {:href "/contancts" :text (get-in strings [:text :contacts])}])


(def PRODUCTS
  [{:href "/products/fhirbase" :text "Fhirbase"}
   {:href "/products/aidbox" :text "Aidbox"}
   {:href "/products/hl7mapper" :text "HL7 Mapper"}
   {:href "/products/formstamp" :text "FormStamp"}
   {:href "/products/foodtaster" :text "FoodTaster"}])

(def CONTACTS
  [{:href "/" :text "USA: +1 (818) 731-12-79"}
   {:href "/" :text "Russia: +7 (812) 919-00-25"}
   {:href "mailto:hello@health-samurai.io" :text "mailto:hello@health-samurai.io"}])

(defn style [cnt]
  [:style {:type "text/css"} (css cnt)])


(def style-vars
  {:color {
           :main "#a23835"
           :inverse "white"
           :normal "#444"}
   :background-color {:inverse "#a23835"
                      :main "white"}})

(defn s-var [& ks]
  (get-in style-vars ks))

(defn undecorate []
  [:& {:text-decoration "none"}
   [:&:hover {:text-decoration "none"}]])

(defn navigation [color]
  [:div#navigation
   (style
    [:#navigation
     {:background {:color (s-var :background-color color)}
      :padding "15px 0 20px"
      :color (s-var :background-color color)}
     [:span.nav-title {:text-align "center"
                       :width "80px"
                       :height "68px"
                       :color (s-var :color color)
                       :background-color (s-var :background-color color)}
      [:i {:padding-top "15px"
           :text-align "center"
           :color "white"
           :text-decoration "none"
           :font-size "32px"
           :margin {:left "auto" :right "auto"}}]]
     [:ul {:display "inline-block" :margin-bottom 0 :float "right"}
      [:li {:margin-left "20px" :margin-top "8px"}
       [:a {:color (s-var :color color)
            :padding "10px 20px"
            :border "1px solid transparent"}
        (undecorate)
        [:&:hover {:color (s-var :color color)
                   :text-decoration "none"
                   :border-color (s-var :color color)}]]]]])
   [:div.container
    [:span.nav-title
     [:a {:href "/"} [:i.hs-icon.icon-samurai]]]
    [:ul.list-inline
     (for [x MENU]
       [:li [:a {:href (:href x)} (:text x)]])]]])

(defn footer []
  [:div#footer
   (style
    [:div#footer {:padding {:top "80px" :bottom "80x"}
                  :background-color "rgba(46,48,58,0.96)"
                  :color "white"}
     [:a {:color "#ddd"}]])

   [:div.container.footer
    [:div.row
     [:div.col-md-3
      [:h4 (get-in strings [:text :services])]
      [:ul.list-unstyled
       (for [x (:services strings)]
         [:li [:a {:href (:href x)} (:text x)]])]]
     [:div.col-md-3
      [:h4 (get-in strings [:text  :products])]
      [:ul.list-unstyled
       (for [x (:products strings)]
         [:li
          [:a {:href (get-in x [:links 0 :link])} (:title x)]])]]
     [:div.col-md-3
      [:h4 (get-in strings [:text  :education])]
      [:ul.list-unstyled
       (for [x (:education strings)]
         [:li [:a {:href (:href x)} (:text x)]])]]
     [:div.col-md-3
      [:h4 (get-in strings [:text  :contacts])]
      [:ul.list-unstyled
       (for [x CONTACTS]
         [:li
          [:a {:href (:href x)} (:text x)]])]]]]])


(defn inversed []
  [:& {:background-color "#a23835" :color "white"}])

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
      [:.sub-title {:padding-top "160px"
                    :padding-bottom "100px"}
       (inversed)
       [:span.sub-moto {:font-size "41px"
                        :line-height "1.3em"
                        :display "block"}]
       [:span.sub-desc {:font-size "18px"
                        :padding-top "24px"
                        :margin-left "auto"
                        :margin-right "auto"
                        :line-height "1.5em"
                        :display "block"}]]])]
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

(defn splash [title moto]
  [:div
   (navigation :inverse)
   (style
    [:#splash
     [:a.item-product {:margin-right "10px"
                       :border "1px solid transparent"
                       :text-decoration "none"
                       :color "#333356"
                       :display "block"}]
     [:h1 {:text-align "center" :color "white"}]])
   [:div#splash
    [:div.container-fluid.sub-title.text-center
     [:span.sub-moto title]
     [:span.sub-desc moto]]]])

(defn products [req]
  [:div
   (navigation :inverse)
   (style
    [:#products
     [:a.item-product {:margin-right "10px"
                       :border "1px solid transparent"
                       :text-decoration "none"
                       :color "#333356"
                       :display "block"}]
     [:h1 {:text-align "center" :color "white"}]])

   [:div#products
    [:div.container-fluid.sub-title.text-center
     [:span.sub-moto "Наши продукты"]
     [:span.sub-desc "Мы разрабатываем технологичные продукты на основе HL7 FHIR."]]
    [:div.container
     (for [prod (:products strings)]
       [:div (product prod) [:hr]])]
    [:div [:h1 (get-in strings [:text :products])]]]])

(defn index [req]
  [:div (splash "Health Samurai" "looking for")])

(defn projects [req]
  [:div (splash "Projects" "looking for")])

(defn page [{{id :id} :params}]
  (http [:h3 "Page" (str id)]))

(def routes
  {:GET #'index
   "about"    {:GET  #'index}
   "products" {:GET #'products}
   "projects" {:GET #'projects}
   "pages"    {:id #(range 10)
               [:id] {:GET #'page}}})

(defn dispatch [{uri :uri meth :request-method :as req}]
  (if-let [mtch (rt/match [meth uri] routes)]
    (http (layout ((:match mtch) (update req :params merge (:params mtch)))))
    {:body "ups" :method 404}))

(def app (-> #'dispatch
             (rmr/wrap-resource "public")))

(defn start []
  (def stop (srv/run-server #'app {:port 8081})))

(comment
  (stop)
  (start))

