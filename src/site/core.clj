(ns site.core
  (:require [clojure.string :as str]
            [hiccup.core :refer [html]]
            [garden.core :refer [css]]
            [garden.units :as u]
            [ring.middleware.resource :as rmr]
            [garden.color :as c]
            [garden.stylesheet :as ss]
            [org.httpkit.server :as srv]
            [route-map.core :as rt]))

(defn http [hic]
  {:body    (html hic)
   :headers {"Content-Type" "text/html; charset=utf-8"}
   :status 200})

(def strings
  {:title "Health Samurai"
   :products "Продукты"
   :education "Обучение"
   :projects "Проекты"
   :services "Услуги"
   :contacts "Контакты"})

(def MENU
  [{:href "/products" :text (:products strings)}
   {:href "/projects" :text (:projects strings)}
   {:href "/contancts" :text (:contacts strings)}])

(def SERVICES
  [{:href "/services" :text "Запуск и обучение"}
   {:href "/services" :text "Запуск и разработка"}
   {:href "/services" :text "Коммерческая поддержка"}])

(def PRODUCTS
  [{:href "/products/fhirbase" :text "Fhirbase"}
   {:href "/products/aidbox" :text "Aidbox"}
   {:href "/products/hl7mapper" :text "HL7 Mapper"}
   {:href "/products/formstamp" :text "FormStamp"}
   {:href "/products/foodtaster" :text "FoodTaster"}])

(def EDUCATION
  [{:href "/education" :text "Введение в HL7 FHIR"}
   {:href "/education" :text "HL7 FHIR для продвинутых"}])

(def CONTACTS
  [{:href "/" :text "USA: +1 (818) 731-12-79"}
   {:href "/" :text "Russia: +7 (812) 919-00-25"}
   {:href "mailto:hello@health-samurai.io" :text "mailto:hello@health-samurai.io"}])

(defn style [cnt]
  [:style {:type "text/css"} (css cnt)])


(def style-vars
  {:color {:main "#a23835"
           :normal "#444"}})

(defn s-var [& ks]
  (get-in style-vars ks))

(defn navigation [color]
  [:div#navigation

   (style
    [:#navigation
     {:background {:color (if (= :inverse color)  (s-var :color :main) "transparent")}
      ;; :line-height "40px"
      :padding "15px 0 20px"
      :color (if (= :inverse color) "white" (s-var :color :main))}
     [:span.nav-title {:position "fixed"
                       :top 0
                       :width "80px"
                       :height "68px"
                       :background-color "#a23835"
                       :text-align "center"}
      [:i {:padding-top "15px"
           :text-align "center"
           :color "white"
           :text-decoration "none"
           :font-size "32px"
           :margin-left "auto"
           :margin-right "auto"}]]
     [:ul {:display "inline-block"
           :margin-bottom 0
           :float "right"}]
     [:li {:margin-right "20px"
           :margin-top "8px"}
      [:a {;;:color "#a23835" ;; TODO: uncomment me, remove next white color
           :color "white"
           :padding "10px 20px"
           :border "1px solid transparent"
           :background-color "transparent"}
       [:&:hover {:color "#a23835"
                  :text-decoration "none"
                  :border "1px solid #a23835"}]]
      [:&:last-child {:margin-right 0}]
      [:&:hover {:color "#a23835"}
       [:a {color "#a23835"}]]
      ]
     ])

   [:div.container
    [:span.nav-title
     [:a {:href="#/"}
      [:i.hs-icon.icon-samurai] #_(:title strings)]]
    [:ul.list-inline
     (for [x menu]
       [:li [:a {:href (:href x)} (:text x)]])]]])

#_(defn navigation [color]
  [:div#navigation

   (style
    [:#navigation
     {:background {:color (if (= :inverse color)  (s-var :color :main) "white")}
      :line-height "40px"
      :padding "0 30px"
      :color (if (= :inverse color) "white" (s-var :color :main))}
     [:.brand {:display "inline-block"}
      [:a {:color "inherit" :display "inline-block" :padding 5}]]
     [:.menu {:display "inline-block" :float "right"}
      [:.menu-item {:display "inline-block"}
       [:a {:color "inherit" :display "inline-block" :padding 5}]]]])

   [:div.brand
    [:a {:href "/"} [:i.hs-icon.icon-samurai] "Brand"]]
   [:div.menu
    (for [x menu]
      [:div.menu-item [:a {:href (:href x)} (:text x)]])]])

(defn footer []
  [:div#footer
   (style
    [:div#footer {:padding-top "80px"
                  :padding-bottom "80px"
                  :background-color "rgba(46,48,58,0.96)"
                  :color "white"}
     [:a {:color "#ddd"}]])
   [:div.container.footer
    [:div.row
     [:div.col-md-3
      [:h4 (:services strings)]
      [:ul.list-unstyled
       (for [x SERVICES]
         [:li
          [:a {:href (:href x)} (:text x)]])]]
     [:div.col-md-3
      [:h4 (:products strings)]
      [:ul.list-unstyled
       (for [x PRODUCTS]
         [:li
          [:a {:href (:href x)} (:text x)]])]]
     [:div.col-md-3
      [:h4 (:education strings)]
      [:ul.list-unstyled
       (for [x EDUCATION]
         [:li
          [:a {:href (:href x)} (:text x)]])]]
     [:div.col-md-3
      [:h4 (:contacts strings)]
      [:ul.list-unstyled
       (for [x CONTACTS]
         [:li
          [:a {:href (:href x)} (:text x)]])]]
     ]]])

(defn layout [cnt]
  [:html
   [:head
    
    [:meta {:charset "utf-8"}]
    [:meta {:http-equiv "X-UA-Compatible"
            :content "IE=edge"}]
    [:title "Health Samurai"]
    [:meta {:name "desription"
            :content ""}]
    [:meta {:name "viewport"
            :content "width=device-width, initial-scale=1.0"}]
    [:title (:title strings)]
    [:link {:rel "stylesheet"
            :href "//maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css" 
            :type "text/css"}]
    (style
     [:body {:padding "0"
             :margin "0"
             :font-family "'Exo 2', sans-serif"
             :font-size "18px"
             :font-weight "300"
             :color "#333356"
             :letter-spacing "1px"}])
    [:link {:rel "stylesheet"
            :href "https://fonts.googleapis.com/css?family=Exo+2:400,100,100italic,200,200italic,300,300italic,400italic,500,900italic,500italic,600,600italic,700,700italic,800,800italic,900"
            :type "text/css"}]
    ]

   [:body
    [:div cnt]
    (footer)]])

(defn products [req]
  [:div
   (navigation :inverse)

   (style
    [:#products {:padding "100px 50px"
                 :background {:color (s-var :color :main)}}
     [:h1 {:text-align "center" :color "white"}]])

   [:div#products
    [:h1 "Helllllooooo11111111111111111"]
    [:div [:h1 (:products strings)]]
    [:div.projects [:h4 "Projects"]]]])

(defn index [req]
  [:div (navigation :main)])

(defn projects [req]
  [:div])

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

(defn generate [{path :path params :params :as req} routes]
  (println path)
  (for [[k v] routes]
    (cond
      (= :GET k) (let [uri (str/join "/" path)]
                   (println "-> " uri ":")
                   (println (dispatch {:uri uri :request-method k})))
      (vector? k) (let [k (first k)
                        gen (get routes k)]
                    (doseq [pv (gen)]
                      (generate {:path (conj path pv)
                                 :params (assoc params k pv)}
                                v)))
      (keyword? k) "nop"
      :else (generate {:path (conj path k) :params params} v))))

(generate [] routes)
