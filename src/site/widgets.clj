(ns site.widgets
  (:require [garden.units :refer [px px*]]
            [site.data :as d]
            [site.styles :refer [s-var style vh &pbox &center-block &center-text] :as s]))

(defn grid [& columns]
  (let [cnt (/ 12 (or (count columns) 4))]
    (into [:div.row]
          (for [c columns] (into [:div {:class (str "col-md-" cnt)}] c)))))


(defn splash [opts]
  [:div#splash
   (style
    [:#splash
     ;;(s/s-var :color :inverse :text)
     {:color "white"
      :overflow "hidden"
      :position "relative"
      :background-color (s/clr :red)}

     (s/&padding 6 0 6 0)

     [:svg {:fill "none"
            :position "absolute"
            :top 0
            :stroke "#922323"
            :stroke-width 1.5
            :stroke-miterlimit 10}]

     [:.splash-body {:position "relative"}]
     [:.splash-header
      (s/&text :center :h1 300)
      (s/&center-block "20em")]

     [:.action
      {:border "2px solid white"
       :display "inline-block"
       :padding {:left "1em" :right "1em"}
       :cursor "pointer"
       :line-height (s/vh* 4)
       :border-radius (s/vh* 2)
       :color "white"}
      (s/&text :center :h2 600)
      (s/&unstyle-links)
      [:&:hover
       {:background "white"
        :color (s/s-var :color :main :selection :color)}]]

     [:.splash-sub-header
      (s/&text :center :h4)
      (s/&padding 1 nil)
      (s/&center-block "40em")]])

   [:div.container-fluid
    (s/svg "bg-2")
    [:div.splash-body
     [:h1.splash-header    (:title opts)]
     [:div.splash-sub-header (:moto opts)]]]])

(defn form [form-id]
  [:form.back-form
   {:name "insightly_web_to_contact"
    :id form-id
    :action "https://vlinnih2.insight.ly/WebToContact/Create"
    :method "post"}
   (s/style
    [:.back-form
     {:display "none"}
     [:.action {:background "transparent" :min-width "8em"}]
     [:input :textarea
      {:background "rgba(0,0,0,0.15)"
       :border-radius 0
       :box-shadow "none"
       :color "white"
       :border "none"}
      [:&:focus
       {:box-shadow "none"
        :background-color "rgba(0,0,0,0.30)"}]]
     [:label {:display "block" :text-align "left" :font-size (s/vh* 0.5)}
      (s/&text :left :small 300)
      (s/&margin 1 0 0 0)
      (s/&padding 0 0 0 0)]])
   [:h3 "Contact Us!"]
   [:input.form-control {:type "hidden", :name "formId", :value "vRF+af9kWTUbLEEsJ9CzBw=="}]
   [:label {:for "insightly_firstName"} "First Name: "]
   [:input.form-control {:id "insightly_firstName", :name "FirstName", :type "text" :require true}]
   [:label {:for "insightly_lastName"} "Last Name: "]
   [:input.form-control {:id "insightly_lastName", :name "LastName", :type "text"}]
   [:label {:for "insightly_organization"} "Organization: "]
   [:input.form-control {:id "insightly_organization", :name "Organization", :type "text"}]
   [:label {:for "insightly_role"} "Title: "]
   [:input.form-control {:id "insightly_role", :name "Role", :type "text"}]
   [:input.form-control {:type "hidden", :name "emails[0].Label", :value "Work"}]
   [:label {:for "email[0]_Value"} "Email*: "]
   [:input.form-control {:id "emails[0]_Value", :name "emails[0].Value", :type "text" :required true}]
   [:input.form-control {:type "hidden", :name "phones[0].Label", :value "Work"}]
   [:label {:for "phones[0]_Value"} "Phone (Work): "]
   [:input.form-control {:id "phones[0]_Value", :name "phones[0].Value", :type "text"}]
   [:label {:for "insightly_background"} "Question*: "]
   [:textarea.form-control {:id "insightly_background", :name "background" :style "height: 300px"}]
   [:br]
   [:input.action {:type "submit", :value (d/idata :text :send-form)}]])

(defn $show [id] (str  "$('#" id "').show();"))
(defn $hide [id] (str  "$('#" id "').hide();"))

(defn call-to-action [opts]
  (let [splash-id (gensym)
        form-id (gensym)]
    (splash
     (update-in opts [:moto]
                (fn [moto]
                  [:div
                   moto
                   [:br]
                   [:br]
                   (form form-id)
                   [:a.action {:id splash-id :onClick (str ($hide splash-id) ($show form-id)) :href "javascript:void"}
                    "Contact us!"]])))))

(defn paralax [height layer-1 layer-2]
  [:div.paralax
   (style [:.paralax
           {:position "relative"}
           [:.paralaxed {:top 0 :position "fixed" :width "100%" :z-index -5}]
           [:.push (s/&margin height nil nil nil)]
           [:.paralaxing {:background {:color "white"}}]])
   [:div.paralaxed layer-1]
   [:div.push]
   [:div.paralaxing layer-2]])

(defn tags [tags & [opts]]
  [:div.tags
   (style
    [:.tag
     {:border-radius ".25em"
      :color "#888"
      :background-color "#fafafa"}
     (s/&font-scale 0.75 0.75)
     (s/&text :center :nowrap 300)
     (s/&margin 0 0.25 0.25 0)
     (s/&padding 0.5)
     (s/&inline)
     (s/&border)])
   (->> tags
        (map (fn [t] [:div.tag t]))
        (interpose (if (and opts (:inline opts)) "" [:br])))])

(defn fa-icon [x] [:i.fa {:class (str "fa-" (name (or x "ups")))}])
